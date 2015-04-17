package edu.teco.tacet.readers.csv;

import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.meta.CSVDatasource;
import edu.teco.tacet.meta.Column;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CSVReader implements Reader {

	private CSVDatasource source;
	private BufferedRandomAccessFile braf;
	private long offset;
	private static Logger log = Logger.getAnonymousLogger();
	
	private double averageDistance = Double.NaN;
	
	public CSVReader(CSVDatasource source) {
		this.source = source;
		initialiseReader();
	}

	@Override
	public Iterable<Datum> readSensorData(long trackId, Range range,
			long resolution) {
		//TODO find out best factor to switch from dense to sparse reading

		if (range.getEnd() < getStartTimestamp(trackId)) return new ArrayList<>();
		if (range.getStart() > getEndTimestamp(trackId)) return new ArrayList<>();
		if (range.getStart() < getStartTimestamp(trackId)) range = range.setStart(getStartTimestamp(trackId));
		if (range.getEnd() > getEndTimestamp(trackId)) range = range.setEnd(getEndTimestamp(trackId)+1);

		if (resolution < 10*getAverageDistance(trackId)) return readSensorDataDense(trackId, range, resolution);
		return readSensorDataSparse(trackId, range, resolution);
	}

	private Iterable<Datum> readSensorDataSparse(long trackId, Range range,
			long resolution) {
		//TODO should i really put everything in one list?
		int col = getColumnFromTrackId(trackId);
		ArrayList<Datum> list = new ArrayList<>();
		long pos = getAddressInFile(range.getStart());
		long tempTS = range.getStart();
		while (tempTS < range.getEnd()) {
			pos = getAddressInFile(tempTS);
			long ts = parseTimestamp(pos);
			ArrayList<String> as = readLine();
			try{	
				list.add(new Datum(ts, Double.parseDouble(as.get(col))));
			} catch(NumberFormatException e) {
				
			}
			tempTS += resolution;
		}

		return list;
	}

	private Iterable<Datum> readSensorDataDense(long trackId, Range range,
			long resolution) {
		//TODO should i really put everything in one list?
		int col = getColumnFromTrackId(trackId);
		ArrayList<Datum> list = new ArrayList<>();
		//System.out.println(pos);
		long prevTs = -1;
		long maxTempTS = parseTimestamp(getAddressInFile(range.getStart()));
		Long ts = null;
		do {
			if (ts != null) {
				prevTs = ts;
			}
			ArrayList<String> as = readLine();
			ts = parseTimestamp(as);
			if (ts >= maxTempTS) {
				try{	
					list.add(new Datum(ts, Double.parseDouble(as.get(col))));
				} catch(NumberFormatException e) {
					
				}
				maxTempTS += resolution;
			}
		} while (ts < range.getEnd() && prevTs != ts);

		return list;
	}

	@Override
	public Iterable<Annotation> readAnnotations(long trackId, Range range) {
		// TODO should i really put everything in one list?

		if (range.getEnd() < getStartTimestamp(trackId)) return new ArrayList<>();
		if (range.getStart() > getEndTimestamp(trackId)) return new ArrayList<>();
		if (range.getStart() < getStartTimestamp(trackId)) range = range.setStart(getStartTimestamp(trackId));
		if (range.getEnd() > getEndTimestamp(trackId)) range = range.setEnd(getEndTimestamp(trackId));

		int col = getColumnFromTrackId(trackId);
		ArrayList<Annotation> list = new ArrayList<>();
		long pos = getAddressInFile(range.getStart());
		long ts = parseTimestamp(pos);
		ArrayList<String> as = readLine();
		String annot = as.get(col);
		long startAnnotTS = ts;
		long endAnnotTS = ts;
		while (ts < range.getEnd()) {
			as = readLine();
			ts = parseTimestamp(as);

			if (annot.equals(as.get(col))) {
				endAnnotTS = ts;
			}
			else {
				if (annot.length() > 0) list.add(new Annotation(new Range(startAnnotTS, endAnnotTS), annot));
				annot = as.get(col);
				startAnnotTS = ts;
				endAnnotTS = ts;
			}
		}
		if (endAnnotTS == ts) list.add(new Annotation(new Range(startAnnotTS, endAnnotTS), annot));

		return list;
	}

	@Override
	public long getSourceId() {
		return source.getId();
	}

	@Override
	public double getAverageDistance(long trackId) {
		// check whether we already computed this value
		if (Double.isNaN(this.averageDistance)) {
			int n = 1000;
			long tstart = getStartTimestamp(trackId);
			for (int i = 0; i <= n; ++i) {
				ArrayList<String> as = readLine();
				if (as.size() == 0 || as.get(0).equals("")) {
					braf.back(2);
					break;
				}
			}
			long ts = parseTimestamp(braf.getCurrentPointer());
			this.averageDistance = (BigInteger.valueOf(ts).subtract(BigInteger.valueOf(tstart)))
				.divide(BigInteger.valueOf(n+1)).longValue();
		}
		return this.averageDistance;
	}

	private long getStartTimestamp(long trackId) {
		return parseTimestamp(offset);
	}

	private long getEndTimestamp(long trackId) {
		return parseTimestamp(braf.getFileLength() - 1);
	}

	@Override
    public Range getCoveredRange(long trackId) {
		return new Range(getStartTimestamp(trackId), getEndTimestamp(trackId));
    }

    private void initialiseReader() {
        try {
            braf = new BufferedRandomAccessFile(new RandomAccessFile(new File(source.getFilePath()), "r"));

            int lines = source.getNoOfLinesToSkip();
            braf.gotoBeginning();
            int i = 0;
            int j = 0;
            offset = 0;
            while(j < lines) {
            	if (braf.next() == source.getLineSeparator().codePointAt(i)) {
            		++i;
            		if (i == source.getLineSeparator().length()) { ++j; i = 0; }
            	}
            	else i = 0;
            	++offset;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private long getAddressInFile(long timestamp) {
		if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp may not be smaller than 0");
        }
        // This is a binary search
        long begByte = offset;
        long endByte = braf.getFileLength();
        while (true) {
            // the byte we are currently looking at
            long cursorByte = (endByte + begByte) / 2;

            long parsedTS = parseTimestamp(cursorByte);
            if (parsedTS < timestamp) {
                begByte = cursorByte + 1;
            } else if (parsedTS > timestamp) {
                endByte = cursorByte;
            } else {
                return cursorByte;
            }

            if (begByte == endByte) {
                return cursorByte +1;
            }
        }
	}

	private long parseTimestamp(ArrayList<String> cols) {
		if(cols.isEmpty()){
			return 0;
		}
		
		if (cols.size() < source.getColumnDescriptions().size()) {
			log.warning("ColumnDescription wrong size (" + cols.size() + " instead of " 
			        + source.getColumnDescriptions().size() + "): " + cols.toString());			
		} else {
			for (int i = 0; i < source.getColumnDescriptions().size(); ++i) {
				if (source.getColumnDescriptions().get(i).getColumnType() == Column.TIMESTAMP) {
					if (!cols.get(i).isEmpty()) {
						if(source.isTreatTimestampAsMillis()){
							return (long)Long.parseLong(cols.get(i));
						} else if(source.isIsStartFrom1970()){
							return (long)(Long.parseLong(cols.get(i))*source.getTimeUnit().getMultiplier());
						} else {
							DateTimeFormatter fmt = DateTimeFormat.forPattern(source.getTimestampFormat());
							return fmt.parseMillis(cols.get(i));
						}
					}
				}
			}
		}
		return 0;
	}

	private long parseTimestamp() {
		ArrayList<String> cols = readLine();
		return parseTimestamp(cols);
	}

	private long parseTimestamp(long cursorByte) {
		braf.gotoPosition(cursorByte);
		long ts = parseTimestamp();
		braf.gotoPosition(cursorByte);
        return ts;
	}

	private ArrayList<String> readLine() {
		ArrayList<String> columns = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		goToBeginnigOfLine();
		int c = braf.next();
		boolean quoted = false;

		while (true) {
			if (c == '"') quoted = !quoted;
            if (!quoted && c == source.getElementSeparator()) {
            	columns.add(sb.toString());
            	sb = new StringBuilder();
            }
            else if ((!quoted && checkEOL((char) c)) || c==-2 ) {
            	columns.add(sb.toString());
            	return columns;
            }
            else if (c == -1) {
            	return columns;
            }
            else if (c != '"') sb.append((char)c);

            c = braf.next();
		}
	}

	private boolean checkEOL(char c) {
		int i = 0;
		String ls = source.getLineSeparator();
		while (i < ls.length() && c == ls.charAt(i)) {
			c = (char)braf.next();
			++i;
		}
		if (i == ls.length()) return true;
		braf.back(i);
		return false;
	}

	private void goToBeginnigOfLine() {
		if (braf.getCurrentPointer() == 0) return;
		int i = source.getLineSeparator().length()-1;
		int c = braf.prev();
		while(c != -1 && i >= 0) {
			if (c == source.getLineSeparator().charAt(i)) --i;
			else i = source.getLineSeparator().length()-1;
			c = braf.prev();
		}
		if (c != -1) braf.skip(source.getLineSeparator().length()+1);
	}

	private int getColumnFromTrackId(long trackId) {
		for (int i = 0; i < source.getColumnDescriptions().size(); ++i) {
			if (source.getColumnDescriptions().get(i).getTimeseriesId() == trackId) return i;
		}
		throw new IllegalArgumentException("trackId not available");
	}

}
