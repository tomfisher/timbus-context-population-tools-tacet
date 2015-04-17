package edu.teco.tacet.writers.csv;

import java.io.IOException;

import org.junit.Test;

import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.SourceIdGenerator;
import edu.teco.tacet.track.Track;
import edu.teco.tacet.track.TrackIdGenerator;
import edu.teco.tacet.track.TrackManager;
import edu.teco.tacet.writer.csv.CsvExportDataSource;
import edu.teco.tacet.writer.csv.CsvWriter;
import static edu.teco.tacet.util.collection.IterableAdditions.*;

public class CsvWriterTest {

    class IdGen implements SourceIdGenerator, TrackIdGenerator {
        long id = 0;

        @Override
        public long generateTrackId() {
            return id++;
        }

        @Override
        public long generateSourceId() {
            return id++;
        }
    }
    
    @Test
    public void test1() {
        IdGen idGen = new IdGen();
        TrackManager.initialise(MetaFactory.eINSTANCE.createProject(),
            idGen, idGen, (Reader[]) null);
        TrackManager tm = TrackManager.getInstance();
        long track1 = tm.createSensorTrackFrom(
            iterable(new Datum(1, 0), new Datum(2, 0), new Datum(3, 0)), "sensor1");
        long track2 = tm.createAnnotationTrackFrom(
            iterable(Annotation.createDummy(new Range(0,2))), "annotation1");
        
        CsvWriter writer = new CsvWriter(new CsvExportDataSource(new Range(0,10), ";", "text.csv", "\n"));
        try {
            writer.export(iterable(tm.getSensorTrack(track1)),
                iterable(tm.getAnnotationTrack(track2)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
