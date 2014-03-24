/* 
 * Copyright 2013-2014 TECO - Karlsruhe Institute of Technology.
 * 
 * This file is part of TACET.
 * 
 * TACET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TACET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TACET.  If not, see <http://www.gnu.org/licenses/>.
 */
 
package squirrel.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.rmi.UnexpectedException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import squirrel.model.Annotation;
import squirrel.model.SensorDatum;
import squirrel.model.io.DataColumn.Unit;
import squirrel.util.Range;

/**
 * Reader to Read from CSV file
 * @author marc
 *
 */
public class CommaSeparatedValuesReader extends AbstractReader<CSVDataColumn> implements
    FileDictionary {
    private String valueSeparator;
    private String lineSeparator;
    private RandomAccessFile raf = null;
    private double average = -1.0;
    private double averageLineLength = 0;
    private double min = Double.MAX_VALUE;
    private double minLineLength = Double.MAX_VALUE;
    private long startTimestamp = -1;
    private long endTimestamp = -1;
    // (int) (0.01 * (raf.length() / (line.length() + lineSeparator.length())));
    static int percentForCalculation = 100;
    protected String replace;
    private boolean getAdressInit = false;
    private long size = -1;
    protected TimeConverter timeConverter;
    private int offset = 0;
    // this is set when parseTimestamp() is invoked.
    private long lastFilePosition = -1L;
    int count = 0;

    /**
     * Constructs csvReader with TimeConverter
     * @param dataSource contains descriptions for each column and the path of
     *            the source file
     */
    public CommaSeparatedValuesReader(CSVDataSource dataSource) {

        super(dataSource);
        if (count == 1)
            System.exit(1);
        count++;
        this.valueSeparator = dataSource.getElemSeparator();
        this.lineSeparator = dataSource.getLineSeparator();
        // For now we hardcode the timestamp to be in the first column
        this.replace = dataSource.getDataColumn(0).getCustomDateSeparator();
        if (dataSource.isHeader()) {
            initialiseReader();
            this.offset = dataSource.getHeaderLength() + 1;
            try {
                raf.seek(this.offset);
                int c = raf.read();
                if ('\n' == c) {
                    c = raf.read();
                    if ('\r' == c) {
                        offset += 2;
                    } else {
                        offset += 1;
                    }
                } else {
                    offset += 2;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        int counterUnits = 0;
        int posUnit = 0;
        for (int j = 0; j < dataSource.getNumberOfColumns(); j++) {
            if (dataSource.getDataColumn(j).getType().isTimeStamp()
                && !dataSource.getDataColumn(j).isDate()) {
                counterUnits += dataSource.getDataColumn(j).getCustomDateUnits().length;
            }
        }
        Unit[] u = new Unit[counterUnits];

        for (int j = 0; j < dataSource.getNumberOfColumns(); j++) {
            if (dataSource.getDataColumn(j).getType().isTimeStamp()
                && !dataSource.getDataColumn(j).isDate()) {
                for (int i = 0; i < dataSource.getDataColumn(j).getCustomDateUnits().length; i++) {
                    u[posUnit] = (dataSource.getDataColumn(j).getCustomDateUnits()[i]);
                    posUnit++;
                }
            }
        }

        this.timeConverter = new TimeConverter(u);

    }

    private void initialiseReader() {
        try {
            if (raf == null)
                raf = new RandomAccessFile(new File(dataSource.getLocation().toURI()), "r");

        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<SensorAnnotationTuple> readData(Range range) {
        List<SensorAnnotationTuple> annotationTuple =
            new ArrayList<SensorAnnotationTuple>((int) (range.getDistance() / min));
        if (range.getStart() > endTimeStamp() ||
            range.getEnd() < startTimeStamp()) {
            return annotationTuple;
        }
        if (range.getStart() < startTimeStamp()) {
            range = new Range(startTimeStamp(), range.getEnd());
        }
        if (range.getEnd() > endTimeStamp()) {
            range = new Range(range.getStart(), endTimeStamp());
        }

        long fileStart = getAddressInFile(range.getStart());

        String[] values;
        int counterDataColumns = 0;
        int countAnnotationColumns = 0;

        // Calculates the length of the arrays
        for (int i = 0; i < dataSource.getNumberOfColumns(); i++) {
            if (dataSource.getDataColumn(i).getType().isSensor()) {
                counterDataColumns++;
            } else if (dataSource.getDataColumn(i).getType().isOldAnnotation()) {
                countAnnotationColumns++;

            }
        }
        initialiseReader();
        try {
            StringBuilder sb = new StringBuilder();

            raf.seek(fileStart);
            boolean read = true;
            while (read) {
                FileChannel channel = raf.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                int N = channel.read(buffer);
                if (N <= 0)
                    read = false;
                buffer.flip();
                while (read && buffer.hasRemaining()) {
                    char c = (char) buffer.get();
                    if (c == '\"')
                        continue;
                    if (c == '\n') {

                        values = sb.toString().replaceAll("\\r", "").split(valueSeparator);
                        SensorAnnotationTuple sat =
                            parseRow(values, counterDataColumns, countAnnotationColumns);
                        if (sat.getSensorDatum().getTimestamp() >= range.getEnd()) {
                            read = false;
                        }
                        else
                            annotationTuple.add(sat);
                        sb = new StringBuilder();
                    }
                    else {
                        sb.append(c);
                    }
                }
            }

        } catch (IOException e) {
            // IO exeption vor reading next line or for cannot read file
            e.printStackTrace();
        }
        bufferTuple = annotationTuple;
        bufferRange = range;
        finalizeReader();
        return annotationTuple;
    }

    private void finalizeReader() {
        // try {
        // raf.close();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    private void initFileDictionary() throws IOException {
        long last = 0;
        long before = 0;
        String line = "";
        String[] row;
        int count = 1;

        int percent = percentForCalculation;

        for (int i = 0; i < percent && (line = raf.readLine()) != null; i++) {
            line = line.replaceAll("\"", "");
            row = line.split(valueSeparator);

            // if block for the minimum
            if (last == -1) {
                last = getTimestamp(row);
                before = last;
            } else if (min > getTimestamp(row) - last) {
                min = getTimestamp(row) - last;
                last = getTimestamp(row);
                count++;
            } else {
                last = getTimestamp(row);
            }

            if (minLineLength > line.length()) {
                minLineLength = line.length();
            }

            averageLineLength += line.length();

        }
        averageDistance();
        averageLineLength = averageLineLength / count;

    }

    /**
     * Given a {@code timestamp} lookup its address in this file and return it.
     * The address of a {@code timestamp} is the number of the byte on the start
     * of the line the given {@code timestamp} is located on.
     * <p>
     * If {@code timestamp} is bigger than the biggest timestamp in this file,
     * return a negative value. If the timestamp does not exist but is smaller
     * than the largest timestamp, take the next bigger timestamp to compute the
     * returned address.
     *
     * @param timestamp the positive timestamp whos address is to be computed.
     * @return the address of {@code timestamp} in bytes in this file.
     */
    @Override
    public long getAddressInFile(long timestamp) {
        if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp may not be smaller than 0");
        }
//        if (timestamp == startTimeStamp())
//            return offset;
        // This is a binary search
        initialiseReader();
        long begByte = 0+offset;
        long endByte = getFileLength();
        while (true) {
            // the byte we are currently looking at
            long cursorByte = (endByte + begByte) / 2;

            long parsedTS = parseTimestamp(cursorByte, raf);
            if (parsedTS < timestamp) {
                begByte = cursorByte + 1;
            } else if (parsedTS > timestamp) {
                endByte = cursorByte;
            } else {
                return this.lastFilePosition;
            }

            if (begByte == endByte) {
                return this.lastFilePosition;
            }
        }
    }

    /**
     * Parse and return the timestamp that is found on the line denoted by
     * {@code start}. {@code raf.getFilePointer()} will return the position of
     * the start of that line, after this method has been called. This method
     * also sets {@code lastFilePosition} to said position or to a negative
     * value, if an {@code IOException} occured.
     *
     * @param start the position in the file from where to start reading.
     * @param raf the random access file to read from.
     * @return the timestamp on the line denoted by {@code start} or a negative
     *         value if an {@code IOException} occured.
     */
    private long parseTimestamp(long start, RandomAccessFile raf) {
        long cursor = -1;
        String line = null;

        try {
            long startOfLine = goToBeginningOfLine(start, raf);
            raf.seek(startOfLine);
            line = raf.readLine();
            cursor = startOfLine;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        if (line == null)
            return -1;
        line = line.replaceAll("\"", "");
        // parsing
        String[] splitted = line.split(valueSeparator, dataSource.idxOfLastTimestamp() + 2);

        this.lastFilePosition = cursor;
        return getTimestamp(splitted);
    }

    /**
     * Search backwards for the first newline before {@code position} and return
     * the position in bytes after said newline.
     * <p>
     * After calling this method, {@code raf.getFilePointer()} will return the
     * same value as this method.
     * <p>
     * If {@code position} is {@code 0}, {@code 0} is returned and {@code raf}
     * will be at position {@code 0}.
     * <p>
     * If an {@code IOException} occurs, a negative value is returned.
     *
     * @param position the position from which to start searching.
     * @param raf the random access file to search in.
     */
    private long goToBeginningOfLine(long position, RandomAccessFile raf) throws IOException {
        int c = 1;
        position -= 2;
        while (position >= 0) {
            raf.seek(position);

            switch (c = raf.read()) {
            case -1:
            case '\n':
                return position + 1;
            case '\r':
                long cur = raf.getFilePointer();
                if ((raf.read()) != '\n') {
                    raf.seek(cur);
                } else {
                    return position + 2;
                }
                break;
            }
            position -= 1;
        }
        return 0;
    }

    private long getFileLength() {
        long length = -1;
        try {
            length = raf.getChannel().size();
        } catch (IOException ioe) {
            System.err.println("This Exception was caught and ignored: " + ioe);
        }
        return length;
    }

    @Override
    public double averageDistance() {
        try {
            if (average == -1) {
                initialiseReader();
                raf.seek(0 + offset);
                String line;
                String[] row;
                long last = -1;
                long before = 0;
                int count = 0;
                int percent = 10;

                percent = percentForCalculation;
                initialiseReader();
                for (int i = 0; i < percent && (line = raf.readLine()) != null; i++) {

                    line = line.replaceAll("\"", "");
                    row = line.split(valueSeparator);
                    if (last == -1) {
                        last = getTimestamp(row);
                        before = last;
                        average = 0;
                    } else {

                        last = getTimestamp(row);
                        average = average + last - before;
                        before = last;
                        count++;
                    }

                }
                average = average / count;
                finalizeReader();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return average;
    }

    @Override
    public long startTimeStamp() {
        if (startTimestamp != -1) {
            return startTimestamp;
        }
        initialiseReader();
        String line;
        try {
            raf.seek(0 + offset);
            line = raf.readLine();
            line = line.replaceAll("\"", "");
            long timestamp = getTimestamp(line.split(valueSeparator));
            finalizeReader();
            startTimestamp = timestamp;
            return timestamp;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    private long getFileSize() {
        if (size == -1) {
            try {
                size = raf.getChannel().size();
                return size;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    @Override
    public long endTimeStamp() {
        initialiseReader();
        return parseTimestamp(getFileSize() - 1, raf);
    }

    /**
     * Parse the values in the SensorAnnotationTuple
     * @param values the values of one line of the File
     * @param counterDataColumns how much DataColumns are there
     * @param countAnnotationColumns how much Annotations are there
     * @return the SensorAnnotationTuple for the Line
     */
    protected SensorAnnotationTuple parseRow(String[] values, int counterDataColumns,
        int countAnnotationColumns) {
        SensorAnnotationTuple aT = new SensorAnnotationTuple();
        SensorDatum sD = new SensorDatum();
        Annotation[] anno = new Annotation[countAnnotationColumns];
        double[] sensorValues = new double[counterDataColumns];
        int countS = 0;
        int countA = 0;

        sD.setTimestamp(getTimestamp(values));
        for (int i = 0; i < values.length && i < dataSource.getNumberOfColumns(); i++) {
            if (dataSource.getDataColumn(i).getType().isSensor()) {
                sensorValues[countS] = Double.parseDouble(values[i]);
                countS++;
            } else if (dataSource.getDataColumn(i).getType().isOldAnnotation()) {
                Range range = new Range(sD.getTimestamp(), sD.getTimestamp() + 1);
                String wyh = "asdf";
                anno[countA] = getAnnotation(range, values[i], dataSource.getDataColumn(i), countA);
                countA++;
            }
        }

        sD.setValues(sensorValues);
        aT.setAnnotation(anno);
        aT.setSensorDatum(sD);
        return aT;
    }

    /**
     * Returns the milliseconds of value
     * @param the row
     * @return milliseconds of value
     */
    protected long getTimestamp(String[] values) {
        long milliseconds = 0;
        boolean timeConvert = false;
        ArrayList<String[]> timeValues = new ArrayList<>();
        int countLength = 0;
        int countPos = 0;
        for (int i = 0; i < dataSource.getNumberOfColumns(); i++) {
            if (dataSource.getDataColumn(i).isDate()) {

                Date date;
                try {
                    date = dataSource.getDataColumn(i).getDateFormat().parse(values[i]);
                    milliseconds += date.getTime();
                    System.out.println(date.toString());
                    System.out.println(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else if (dataSource.getDataColumn(i).getType().isTimeStamp()) {
                timeConvert = true;
                String[] parts =
                    values[i].split(
                        dataSource.getDataColumn(i).getCustomDateSeparator());
                String s = dataSource.getDataColumn(i).getCustomDateSeparator();
                if (parts.length == 0) {
                    parts = new String[] { values[i] };

                }
                countLength += parts.length;
                timeValues.add(parts);

            }
        }
        String[] units = new String[countLength];

        if (timeConvert) {
            for (String[] string : timeValues) {
                for (int j = 0; j < string.length; j++) {
                    units[countPos] = string[j];
                    countPos++;
                }
            }
            milliseconds += timeConverter.getMilliseconds(units, 0);
        }

        return milliseconds;
    }
}
