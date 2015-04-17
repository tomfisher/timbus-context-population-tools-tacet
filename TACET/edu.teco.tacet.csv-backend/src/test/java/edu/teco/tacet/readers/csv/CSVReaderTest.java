package edu.teco.tacet.readers.csv;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import edu.teco.tacet.meta.CSVDatasource;
import edu.teco.tacet.meta.Column;
import edu.teco.tacet.meta.ColumnDescription;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;

public class CSVReaderTest {
	
	private final String file = "testcsvreader.txt";
	private CSVDatasource source;
	private ArrayList<Datum> data1;
	private ArrayList<Datum> data2;
	private final int N = 10000;
	private final DateTime start = new DateTime(0);
	
	private final String file2 = "TACET_example.csv";
	private CSVDatasource source2;
	
	private final String file3 = "TACET_example mit label.csv";
	private CSVDatasource source3;
	
	private final String file4 = "TACET_GB_Vergleich.csv";
	@Test
	public void test4(){
		RandomAccessFile ra = null;
		try {
			ra = new RandomAccessFile(new File(file4), "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			long l = ra.length();
			ra.seek(l-2);
			String out="";
			while(out!=null){
				out = ra.readLine();
				System.out.println(out);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
	}

	
	@Before
	public void init() {
		source = MetaFactory.eINSTANCE.createCSVDatasource();
		source.setElementSeparator(',');
		source.setId(42);
		source.setFilePath(file);
		source.setLineSeparator("\n");
		source.setTimestampFormat("yyyy-MM-dd HH:mm:ss.S");
		source.setNoOfLinesToSkip(0);
		ColumnDescription cd0 = MetaFactory.eINSTANCE.createColumnDescription();
		cd0.setColumnType(Column.TIMESTAMP);
		ColumnDescription cd1 = MetaFactory.eINSTANCE.createColumnDescription();
		cd1.setColumnType(Column.SENSOR_DATA);
		cd1.setTimeseriesId(1);
		ColumnDescription cd2 = MetaFactory.eINSTANCE.createColumnDescription();
		cd2.setColumnType(Column.SENSOR_DATA);
		cd2.setTimeseriesId(2);
		source.getColumnDescriptions().add(cd0);
		source.getColumnDescriptions().add(cd1);
		source.getColumnDescriptions().add(cd2);
		
		fill();
		Writer fw = null;

		try {
			fw = new FileWriter(file);
			DateTime dt = new DateTime();
			DateTimeFormatter fmt = DateTimeFormat.forPattern(source.getTimestampFormat());
			for (int i = 0; i < N; ++i) {
				dt = new DateTime(data1.get(i).timestamp);
				fw.write("\"");
				fw.write(fmt.print(dt));
				fw.write("\"");
				fw.write(source.getElementSeparator());
				fw.write(String.valueOf(data1.get(i).value));
				fw.write(source.getElementSeparator());
				fw.write(String.valueOf(data1.get(i).value));
				fw.write(source.getLineSeparator());
			}
			fw.flush();
		}
		catch ( IOException e ) {
			System.err.println( "Konnte Datei nicht erstellen" );
		}
	}
	
	private void fill() {
		DateTime dt = start;
		data1 = new ArrayList<>();
		data2 = new ArrayList<>();
		for (int i = 0; i < N; ++i) {
			data1.add(new Datum(dt.getMillis(), 0.1 * i));
			data2.add(new Datum(dt.getMillis(), 1-0.1*i));
			dt = dt.plus(2000);
		}
	}
	
	@Before
	public void init2() {
		source2 = MetaFactory.eINSTANCE.createCSVDatasource();
		source2.setElementSeparator(';');
		source2.setId(21);
		source2.setFilePath(file2);
		source2.setLineSeparator("\n");
		source2.setTimestampFormat("yyyy-MM-dd HH:mm:ss.S");
		source2.setNoOfLinesToSkip(1);
		ColumnDescription cd0 = MetaFactory.eINSTANCE.createColumnDescription();
		cd0.setColumnType(Column.TIMESTAMP);
		ColumnDescription cd1 = MetaFactory.eINSTANCE.createColumnDescription();
		cd1.setColumnType(Column.SENSOR_DATA);
		cd1.setTimeseriesId(3);
		ColumnDescription cd2 = MetaFactory.eINSTANCE.createColumnDescription();
		cd2.setColumnType(Column.SENSOR_DATA);
		cd2.setTimeseriesId(4);
		source2.getColumnDescriptions().add(cd0);
		source2.getColumnDescriptions().add(cd1);
		source2.getColumnDescriptions().add(cd2);
	}
	
	@Before
	public void init3() {
		source3 = MetaFactory.eINSTANCE.createCSVDatasource();
		source3.setElementSeparator(';');
		source3.setId(21);
		source3.setFilePath(file3);
		source3.setLineSeparator("\n");
		source3.setTimestampFormat("yyyy-MM-dd HH:mm:ss.S");
		source3.setNoOfLinesToSkip(1);
		ColumnDescription cd0 = MetaFactory.eINSTANCE.createColumnDescription();
		cd0.setColumnType(Column.TIMESTAMP);
		ColumnDescription cd1 = MetaFactory.eINSTANCE.createColumnDescription();
		cd1.setColumnType(Column.SENSOR_DATA);
		cd1.setTimeseriesId(5);
		ColumnDescription cd2 = MetaFactory.eINSTANCE.createColumnDescription();
		cd2.setColumnType(Column.SENSOR_DATA);
		cd2.setTimeseriesId(6);
		ColumnDescription cd3 = MetaFactory.eINSTANCE.createColumnDescription();
		cd3.setColumnType(Column.ANNOTATION);
		cd3.setTimeseriesId(7);
		source3.getColumnDescriptions().add(cd0);
		source3.getColumnDescriptions().add(cd1);
		source3.getColumnDescriptions().add(cd2);
		source3.getColumnDescriptions().add(cd3);
	}
	
	@Test
	public void test1() {
		CSVReader csvr = new CSVReader(source);
		long startTS = csvr.getCoveredRange(1).getStart();
		assertTrue(String.valueOf(startTS) + " " + String.valueOf(start.getMillis()), 
				startTS == start.getMillis());
		assertTrue(String.valueOf(csvr.getAverageDistance(1)), csvr.getAverageDistance(1) == 2000);
		int i = 0;
		for (Datum d : csvr.readSensorData(1, new Range(start.getMillis(), start.plus(2000*N).getMillis()), 1)) {
			assertTrue("@"+i+": ("+d.timestamp+","+d.value+") is not (" +data1.get(i).timestamp+","+data1.get(i).value+")",
					d.timestamp == data1.get(i).timestamp && Math.abs(d.value - data1.get(i).value) < 0.001);
			++i;
		}
		i = 0;
		for (Datum d : csvr.readSensorData(1, new Range(start.getMillis(), start.plus(2000*N).getMillis()), 4000)) {
			assertTrue("@"+i+": ("+d.timestamp+","+d.value+") is not (" +data1.get(i).timestamp+","+data1.get(i).value+")",
					d.timestamp == data1.get(i).timestamp && Math.abs(d.value - data1.get(i).value) < 0.001);
			i += 2;
		}
		i = 0;
		for (Datum d : csvr.readSensorData(1, new Range(start.getMillis(), start.plus(2000*N).getMillis()), 20*2000)) {
			assertTrue("@"+i+": ("+d.timestamp+","+d.value+") is not (" +data1.get(i).timestamp+","+data1.get(i).value+")",
					d.timestamp == data1.get(i).timestamp && Math.abs(d.value - data1.get(i).value) < 0.001);
			i += 20;
		}
		i = 0;
		for (Datum d : csvr.readSensorData(1, new Range(start.getMillis(), start.plus(2000*N).getMillis()), 1000*2000)) {
			assertTrue("@"+i+": ("+d.timestamp+","+d.value+") is not (" +data1.get(i).timestamp+","+data1.get(i).value+")",
					d.timestamp == data1.get(i).timestamp && Math.abs(d.value - data1.get(i).value) < 0.001);
			i += 1000;
		}
		i = 0;
		for (Datum d : csvr.readSensorData(1, new Range(start.minusMinutes(2).getMillis(), start.minusMinutes(1).getMillis()), 1)) {
			++i;
		}
		assertTrue("shouldn't contain data", i == 0);
		i = 0;
		for (Datum d : csvr.readSensorData(1, new Range(start.plus(2000*N).getMillis(), start.plus(3000*N).getMillis()), 1)) {
			++i;
		}
		assertTrue("shouldn't contain data", i == 0);
		i = 0;
		for (Datum d : csvr.readSensorData(1, new Range(start.minusMinutes(2).getMillis(), start.plus(2000*N).getMillis()), 1)) {
			assertTrue("@"+i+": ("+d.timestamp+","+d.value+") is not (" +data1.get(i).timestamp+","+data1.get(i).value+")",
					d.timestamp == data1.get(i).timestamp && Math.abs(d.value - data1.get(i).value) < 0.001);
			++i;
		}
		i = 0;
		for (Datum d : csvr.readSensorData(1, new Range(start.getMillis(), start.plus(10000*N).getMillis()), 1)) {
			assertTrue("@"+i+": ("+d.timestamp+","+d.value+") is not (" +data1.get(i).timestamp+","+data1.get(i).value+")",
					d.timestamp == data1.get(i).timestamp && Math.abs(d.value - data1.get(i).value) < 0.001);
			++i;
		}
	}
	
	@Test
	public void test2() {
		CSVReader csvr = new CSVReader(source2);
		Range coveredRange = csvr.getCoveredRange(3);
		long startTS = coveredRange.getStart();
		long endTS = coveredRange.getEnd();
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern(source2.getTimestampFormat());
		assertTrue(String.valueOf(startTS) + " " + String.valueOf(fmt.parseMillis("1970-01-20 00:00:00.0")), 
				startTS == fmt.parseMillis("1970-01-20 00:00:00.0"));
		assertTrue(String.valueOf(endTS) + " " + String.valueOf(fmt.parseMillis("2005-07-01 00:00:00.0")), 
				endTS == fmt.parseMillis("2005-07-01 00:00:00.0"));
		assertTrue(String.valueOf(csvr.getAverageDistance(3)), csvr.getAverageDistance(3) > 1000000000L);
		assertTrue(String.valueOf(csvr.getAverageDistance(3)), csvr.getAverageDistance(3) < 4000000000L);
	}
	
	@Test
	public void test3() {
		CSVReader csvr = new CSVReader(source3);
	    Range coveredRange = csvr.getCoveredRange(3);
	    long startTS = coveredRange.getStart();
	    long endTS = coveredRange.getEnd();
		
	    DateTimeFormatter fmt = DateTimeFormat.forPattern(source3.getTimestampFormat());
		assertTrue(String.valueOf(startTS) + " " + String.valueOf(fmt.parseMillis("1970-01-20 00:00:00.0")), 
				startTS == fmt.parseMillis("1970-01-20 00:00:00.0"));
		assertTrue(String.valueOf(endTS) + " " + String.valueOf(fmt.parseMillis("2005-07-01 00:00:00.0")), 
				endTS == fmt.parseMillis("2005-07-01 00:00:00.0"));
		
		int i = 0;
		for (Annotation a : csvr.readAnnotations(7, new Range(0, Long.MAX_VALUE))) {
			if (i == 0) assertTrue(a.getLabel(), a.getLabel().equals("testlabel"));
			if (i == 1) assertTrue(a.getLabel(), a.getLabel().equals("bla"));
			if (i == 1) assertTrue(a.getStart() == fmt.parseMillis("1975-01-10 00:00:00.0"));
			if (i == 1) assertTrue(a.getEnd() == fmt.parseMillis("1975-02-01 00:00:00.0"));
			if (i == 2) assertTrue(a.getLabel(), a.getLabel().equals("testlabel"));
			++i;
		}
		assertTrue(String.valueOf(i), i == 3);
	}
}
