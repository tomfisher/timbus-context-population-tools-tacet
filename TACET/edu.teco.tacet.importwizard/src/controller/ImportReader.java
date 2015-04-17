package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import edu.teco.tacet.util.TableEntry;

public class ImportReader {

    private boolean header = false;
    private String[] collectionOfPossibelFormats = { "yyyy-MM-dd HH:mm:ss",
            "HH:mm:ss", "yyyy-MM-dd", "hh:mm:ss:S", "MM/dd/yyyy",
            "MM/dd/yyyy HH:mm:ss", "HH:mm:ss", "yyyy-MM-dd HH:mm:ss",
            "dd-MM-yyyy", "yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd HH:mm:ss.S","yyyy-MM-dd HH:mm" };
    private String[] collectionOfPossibleSeperators = { ";", ",", "\t", "-",
            ".", ":" };
    private String lineString = "";
    private String guessTimestampFormat = "";
    private String filePath;
    private String headerSeparator = "";
    private int posGuessTimestamp;
    private String elementSeparator = "";
    private TableEntry.ColumnType[] type;
    private String[] headerLine;

    public ImportReader(String path, boolean header, String headerSeperator) {
        this.filePath = path;
        this.header = header;
        this.headerSeparator = headerSeperator;
    }

    public String getLine() {
        return headerSeparator + "\n" + lineString;
    }

    private BufferedReader createReader() {
        BufferedReader bReader = null;
        try {
            System.out.println(filePath);
            bReader = new BufferedReader(new FileReader(new File(filePath)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bReader;
    }

    public void analyseLine() throws IOException {
        BufferedReader bReader = createReader();

        if (header) {
            analyseHeader(bReader.readLine());
        }

        String line;
        boolean readMoreLines = false;
        int count = 0;
        do {
            line = bReader.readLine();
            if (line != null) {
                readMoreLines = analyseDataLine(line, count != 0);
            }
            count++;
        } while (count <= 10 && readMoreLines);
        
        for (int i = 0; i < type.length; i++) {
            if (type[i] == null) {
                type[i] = TableEntry.ColumnType.ANNOTATION;
            }
        }
        bReader.close();
    }

    public int getLineSeparator(){
        BufferedReader bReader = createReader();
        int ch;
        char end1 = '\n';
        char end2 = '\r';
        try {
            while((ch=bReader.read())!=-1){
                if((char)ch==end1){
                    ch=bReader.read();
                    if((char)ch==end2){
                        return 0; //"\\n\\r"
                    } else {
                        return 1; //"\\n"
                    }
                }else if ((char)ch==end2){
                    ch=bReader.read();
                    if((char)ch==end1){
                        return 2; //"\\r\\n";
                    } else {
                        return 3; //"\\r";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private void analyseHeader(String headerLine) {
        headerSeparator = guessSeparator(headerLine);
        this.headerLine = headerLine.split(headerSeparator);
        System.out.println("HeaderSeparator ist :" + headerSeparator);

    }

    public String[] getHeaderLine() {
        return headerLine;
    }

    public void setHeaderLine(String[] headerLine) {
        this.headerLine = headerLine;
    }

    private boolean analyseDataLine(String dataLine, boolean guessOnly) {
        if (!guessOnly) {
            lineString = dataLine;
            elementSeparator = guessSeparator(dataLine);
            System.out.println("LineSeparator ist: " + elementSeparator);
        }

        String row[] = splitCorrect(dataLine, elementSeparator);
        if (!guessOnly) {
            guessTimestampFormat = guessTimeStampFormat(row);
            System.out.println("Format ist: " + guessTimestampFormat);
        }
        return guessTypes(row);
    }

    private String[] splitCorrect(String dataLine, String elementSeperator) {
        while (dataLine.matches(".*" + elementSeperator + elementSeperator + ".*")) {
            dataLine = dataLine.replaceAll(elementSeperator + elementSeperator,
                    elementSeperator + " " + elementSeperator);
        }
        // For now we only support single letter separators and "-quotes.
        String row[] = splitWithQuoting(dataLine, elementSeperator.charAt(0), '"');
        if (dataLine.endsWith(elementSeperator)) {
            String rowEnd[] = Arrays.copyOf(row, row.length + 1);
            rowEnd[row.length] = "" ;
            row = rowEnd;
        }
        return row;
    }
    
    /**
     * Split the given string at separator taking into account quoting.
     * 
     * @param toSplit the string to be split
     * @param separator the separator to split at
     * @param quote the character that can be used to quote a field
     * @return return the splitted string as {@link String.split(String)} does, but take into
     *         account the quoting: If the separator is in between two quotation chars it is
     *         ignored by the splitting algorithm. Thus "a,\"b,c\",d" is split into ["a", "b,c" "d"]
     *         with " being the quoting string and "," being the separator.
     */
    private String[] splitWithQuoting(String toSplit, char separator, 
        char quote) {
        boolean insideQuotes = false;
        int lastValidSep = -1;
        List<String> splits = new ArrayList<>();
        for (int i = 0; i < toSplit.length(); i++) {
            if (toSplit.charAt(i) == quote) {
                insideQuotes = !insideQuotes;
            } else if (toSplit.charAt(i) == separator && !insideQuotes) {
                // only take valid strings
                if (i - lastValidSep > 0) {
                    // take the chars between the last and this separator (both exclusive)
                    splits.add(toSplit.substring(lastValidSep + 1, i));
                }
                lastValidSep = i;
            }
        }
        String rest = toSplit.substring(lastValidSep + 1, toSplit.length());
        if (rest.length() > 0) {
            splits.add(rest);
        }
        return splits.toArray(new String[0]);
    }

    private boolean guessTypes(String[] row) {
        if (type == null) {
            type = new TableEntry.ColumnType[row.length];
        }
        for (int i = 0; i < type.length; i++) {
            if (i == posGuessTimestamp) {
                type[i] = TableEntry.ColumnType.TIMESTAMP;
            } else {
                if (!row[i].trim().isEmpty()) {
                    try {
                        Float.parseFloat(row[i]);
                        type[i] = TableEntry.ColumnType.SENSOR;
                    } catch (NumberFormatException ex) {
                        type[i] = TableEntry.ColumnType.ANNOTATION;
                    }
                }
            }
        }
        for (TableEntry.ColumnType entry : type) {
            if (entry == null) {
                return true;
            }
        }
        return false;
    }

    public TableEntry.ColumnType[] getType() {
        return type;
    }

    public void setType(TableEntry.ColumnType[] type) {
        this.type = type;
    }

    private String guessTimeStampFormat(String[] row) {
        for (int i = 0; i < row.length; i++) {
            for (String format : collectionOfPossibelFormats) {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .append(DateTimeFormat.forPattern(format).getParser())
                .toFormatter().withZone(DateTimeZone.UTC);
                try {
                    formatter.parseDateTime(row[i]);

                } catch (IllegalArgumentException e) {
                    continue;
                }

                return format;
            }
        }
        return "";
    }

    private String guessSeparator(String line) {
        if (headerSeparator.length() != 0
                && line.split(headerSeparator).length != 1) {
            return headerSeparator;
        }
        for (String sep : collectionOfPossibleSeperators) {
            if (line.split(sep).length != 1) {
                return sep;
            }
        }
        return "";
    }

    public String getGuessTimestampFormat() {
        return guessTimestampFormat;
    }

    public String getHeaderSeperator() {
        return headerSeparator;
    }

    public int getPosGuessTimestamp() {
        return posGuessTimestamp;
    }

    public String getElementSeparator() {
        return elementSeparator;
    }

}
