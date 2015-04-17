package edu.teco.tacet.rdf.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.RdfTimeseries;
import edu.teco.tacet.meta.TimeseriesType;
import edu.teco.tacet.rdf.RdfReader;
import edu.teco.tacet.rdf.RdfReaderFactory;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.util.collection.IterableAdditions;

public class RdfReaderTest {

    Reader reader;
    Datum[] expSensorData;
    Annotation[] expAnnotations;

    @Before
    public void setUp() throws Exception {
        RdfDatasource datasource = MetaFactory.eINSTANCE.createRdfDatasource();
        String fileName = new File("../../testdata/AltoLindoso_Annotated_small.rdf").toString();
        datasource.setFileName(fileName);
        datasource.setTimestampFormat("yyyy-MM-dd'T'HH:mm:ss");
        datasource.setModel(RdfReader.readModelFromFile(fileName, ""));
        datasource.setRootResourceUri("http://localhost:2020/resource/Sensor/40632");

        String ns = "http://timbus.teco.edu/ontologies/DSOs/sensors.owl#";
        RdfTimeseries sensorTrack1 =
            createTimeseries("Relative Resistance", "RelativeResistance", TimeseriesType.SENSOR, 0,
                prefix(ns, new String[][] {
                    new String[] { // identifier path
                    "hasReading", "hasRawReading", "hasQuantity", "hasQuantityType", "hasType"
                    }, new String[] { // timestamp path
                    "hasReading", "hasDate"
                    }, new String[] { // value path
                    "hasReading", "hasRawReading", "hasQuantity", "hasValue"
                    } }));
        datasource.getTimeseries().add(sensorTrack1);

        RdfTimeseries annotationTrack1 = createTimeseries("Behaviour", "Behaviour",
            TimeseriesType.ANNOTATION, 1, prefix(ns, new String[][] {
                new String[] { // identifier path
                "hasReading", "hasAnnotation", "hasName"
                }, new String[] { // timestamp path
                "hasReading", "hasDate"
                }, new String[] { // value path
                "hasReading", "hasAnnotation", "hasDescription"
                }
            }));
        datasource.getTimeseries().add(annotationTrack1);

        reader = new RdfReaderFactory().getReaderFor("", datasource);

        final DateTimeFormatter fmt =
            DateTimeFormat.forPattern(datasource.getTimestampFormat());

        expSensorData = new Datum[] {
            new Datum(fmt.parseMillis("2007-12-05T00:00:00"), 102.99),
            new Datum(fmt.parseMillis("2008-01-29T00:00:00"), 103.17),
            new Datum(fmt.parseMillis("2008-03-26T15:12:00"), 103.01),
            new Datum(fmt.parseMillis("2008-06-05T10:15:53"), 103.02),
            new Datum(fmt.parseMillis("2008-07-31T10:59:00"), 102.99),
            new Datum(fmt.parseMillis("2008-09-25T09:58:00"), 102.93),
            new Datum(fmt.parseMillis("2008-11-25T15:18:00"), 103),
            new Datum(fmt.parseMillis("2009-01-22T15:58:56"), 103.09),
            new Datum(fmt.parseMillis("2009-02-19T15:25:00"), 103.07),
            new Datum(fmt.parseMillis("2009-04-16T15:18:37"), 103.06),
            new Datum(fmt.parseMillis("2009-06-12T14:22:12"), 103.04),
            new Datum(fmt.parseMillis("2009-08-06T10:05:00"), 102.95),
            new Datum(fmt.parseMillis("2009-10-01T14:52:09"), 102.94),
            new Datum(fmt.parseMillis("2009-11-26T16:23:00"), 102.98),
            new Datum(fmt.parseMillis("2010-01-21T12:05:00"), 103.07),
            new Datum(fmt.parseMillis("2010-03-17T16:13:00"), 103.08),
            new Datum(fmt.parseMillis("2010-05-20T14:58:23"), 103.01),
            new Datum(fmt.parseMillis("2010-07-14T16:24:03"), 103),
            new Datum(fmt.parseMillis("2010-09-09T14:54:57"), 102.95),
            new Datum(fmt.parseMillis("2010-11-05T12:31:10"), 102.96),
            new Datum(fmt.parseMillis("2011-01-06T15:38:33"), 103.05),
            new Datum(fmt.parseMillis("2011-03-03T15:01:00"), 103.09),
            new Datum(fmt.parseMillis("2011-04-28T10:52:58"), 103.07),
            new Datum(fmt.parseMillis("2011-06-22T14:40:20"), 103.03),
            new Datum(fmt.parseMillis("2011-08-18T16:23:43"), 102.98),
            new Datum(fmt.parseMillis("2011-11-22T16:11:17"), 103),
            new Datum(fmt.parseMillis("2012-01-19T12:21:00"), 103.06),
            new Datum(fmt.parseMillis("2012-03-15T14:46:00"), 103.1),
            new Datum(fmt.parseMillis("2012-05-10T14:47:06"), 103.09),
            new Datum(fmt.parseMillis("2012-07-05T15:20:00"), 103.06),
            new Datum(fmt.parseMillis("2012-08-29T14:47:00"), 103.03),
            new Datum(fmt.parseMillis("2012-10-25T15:23:33"), 102.93),
            new Datum(fmt.parseMillis("2012-12-17T15:38:00"), 103.05),
            new Datum(fmt.parseMillis("2013-03-05T15:53:00"), 103.04),
            new Datum(fmt.parseMillis("2013-05-02T16:00:41"), 103.06),
            new Datum(fmt.parseMillis("2013-06-25T15:48:01"), 103.05),
            new Datum(fmt.parseMillis("2013-08-22T16:59:00"), 102.98),
            new Datum(fmt.parseMillis("2013-10-16T15:56:36"), 102.96),
            new Datum(fmt.parseMillis("2013-12-10T15:40:00"), 103.05),
            new Datum(fmt.parseMillis("2014-02-05T15:32:23"), 103.03),
            new Datum(fmt.parseMillis("2014-04-02T12:15:53"), 103.05),
        };

        expAnnotations = new Annotation[] {
            new Annotation(new Range(fmt.parseMillis("2007-12-05T00:00:00"),
                fmt.parseMillis("2008-01-29T00:00:00") + 1), "normal"),

            new Annotation(new Range(fmt.parseMillis("2008-06-05T10:15:53"),
                fmt.parseMillis("2008-07-31T10:59:00") + 1), "normal"),

            new Annotation(Range.point(fmt.parseMillis("2008-09-25T09:58:00")), "abnormal"),

            new Annotation(new Range(fmt.parseMillis("2008-11-25T15:18:00"),
                fmt.parseMillis("2009-01-22T15:58:56") + 1), "normal"),

            new Annotation(Range.point(fmt.parseMillis("2010-09-09T14:54:57")), "normal"),

            new Annotation(Range.point(fmt.parseMillis("2011-03-03T15:01:00")), "abnormal"),

            new Annotation(Range.point(fmt.parseMillis("2013-05-02T16:00:41")), "normal"),
        };
    }

    private RdfTimeseries createTimeseries(String name, String idValue, TimeseriesType type,
        long id, String[][] paths) {
        
        RdfTimeseries series = MetaFactory.eINSTANCE.createRdfTimeseries();
        series.setName(name);
        series.setId(id);
        series.setType(type);
        series.setIdentifierValue(idValue);
        series.getIdentifierPath().addAll(Arrays.asList(paths[0]));
        series.getTimestampPath().addAll(Arrays.asList(paths[1]));
        series.getValuePath().addAll(Arrays.asList(paths[2]));

        return series;
    }

    private String[][] prefix(String prefix, String[]... strings) {
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length; j++) {
                strings[i][j] = prefix + strings[i][j];
            }
        }
        return strings;
    }

    @Test
    public void test1() {
        Datum[] results =
            IterableAdditions.toList(reader.readSensorData(0, reader.getCoveredRange(0), 1))
                .toArray(new Datum[0]);
        myArrayEquals(expSensorData, results);
    }

    @Test
    public void test2() {
        Annotation[] actuals =
            IterableAdditions.toList(reader.readAnnotations(1, reader.getCoveredRange(1))).toArray(
                new Annotation[0]);
        myArrayEquals(expAnnotations, actuals);
    }

    private void myArrayEquals(Object[] expecteds, Object[] actuals) {
        if (expecteds.length != actuals.length) {
            fail("length differ: expected: " + expecteds.length + ", actual: " + actuals.length);
        }
        for (int i = 0; i < expecteds.length; i++) {
            if (!expecteds[i].equals(actuals[i])) {
                fail("Differ at " + i + ": expected: " + expecteds[i] + ", actual: " + actuals[i]);
            }
        }
    }

}
