package edu.teco.tacet.writer.csv;

import static edu.teco.tacet.util.collection.IterableAdditions.map;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.NoConflictMerge;
import edu.teco.tacet.track.Track;
import edu.teco.tacet.util.function.F1;
import edu.teco.tacet.writers.Writer;

public class CsvWriter implements Writer {

	private CsvExportDataSource dataSource;

	public CsvWriter(CsvExportDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void export(Iterable<? extends Track<? extends Datum>> sensorTracks,
			Iterable<? extends Track<? extends Annotation>> annotationTracks)
			throws IOException {
		// TODO
		Map<Long, Integer> orderSensor = new HashMap<>();
		int counter = 1;
		for (Track<? extends Datum> track : sensorTracks) {
			orderSensor.put(track.getId(), counter);
			counter++;
		}
		Map<Long, Integer> orderAnno = new HashMap<>();
		for (Track<? extends Annotation> track : annotationTracks) {
			orderAnno.put(track.getId(), counter);
			counter++;
		}
		FileWriter writer = new FileWriter(dataSource.getFilePath());

		NoConflictMerge<Tuple<Datum, Long>> merger = new NoConflictMerge<>(
				new Comparator<Tuple<Datum, Long>>() {
					@Override
					public int compare(Tuple<Datum, Long> o1,
							Tuple<Datum, Long> o2) {
						return Long.compare(o1.first.timestamp,
								o2.first.timestamp);
					}
				});

		List<Iterable<? extends Tuple<Datum, Long>>> toMerge = new ArrayList<>();
		// for each sensor track
		for (Track<? extends Datum> track : sensorTracks) {
			final long id = track.getId();
			// map applies the given function to each element in the given
			// iterable
            toMerge.add(map(new F1<Datum, Tuple<Datum, Long>>() {
                // this function takes a datum and wraps it in a tuple
                @Override
                public Tuple<Datum, Long> apply(Datum datum) {
                    return new Tuple<Datum, Long>(datum, id);
                }
            },
                // this is the iterable we map the function over
                track.getData(track.getCoveredRange())));
        }
        Map<Long, Iterable<? extends Annotation>> annoMap = new HashMap<>();
		for (Track<? extends Annotation> track : annotationTracks) {
			annoMap.put(track.getId(), track.getData(dataSource
					.getGlobalRange()));			
		}
		// all datums and their respective trackId
		Iterable<? extends Tuple<Datum, Long>> mergedTracks = merger
				.merge(toMerge);
		String[] line = new String[1 + orderSensor.size() + orderAnno.size()];
		long lastTimestamp = Long.MIN_VALUE;
		boolean first = true;
		for (Tuple<Datum, Long> tuple : mergedTracks) {
			if (first) {
				first = false;
				line = new String[1 + orderSensor.size() + orderAnno.size()];
				line[0] = "" + tuple.first.timestamp;
				lastTimestamp = tuple.first.timestamp;
			} else if (lastTimestamp != tuple.first.timestamp) {
				// Finished lined for Sensor, now for the Annotations
				
				
				for (Track<? extends Annotation> track : annotationTracks) {
					for (Annotation annotation : annoMap.get(track.getId())) {
						if (annotation.contains(lastTimestamp)) {
							line[orderAnno.get(track.getId())] = annotation
									.getLabel();
							break;
						}
					}
				}
				// Now write Array
				writer.append(arrayToLine(line, dataSource.getSeperator()));
				writer.append(dataSource.getLineSeparator());
			}
			line[orderSensor.get(tuple.second)] = "" + tuple.first.value;
		}

		writer.close();

	}

	private String arrayToLine(String[] line, String seperator) {
		String result = "";
		for (int i = 0; i < line.length; i++) {
			if (line[i] != null) {
				result += line[i];
			}
			if (i != line.length - 1) {
				result += seperator;
			}
		}
		return result;
	}

	@Override
	public Job createJob(final Iterable<Track<? extends Datum>> sensorTracks,
			final Iterable<Track<? extends Annotation>> annotationTracks) {
		Job j = new Job("Export-CSV") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					export(sensorTracks, annotationTracks);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return Status.OK_STATUS;
			}
		};
		return j;
	}
}
