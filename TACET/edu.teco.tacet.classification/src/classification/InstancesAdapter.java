package classification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.AnnotationTrack;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.ReadableSensorTrack;
import edu.teco.tacet.track.UniqueDataDecorator;

public class InstancesAdapter {

	private AnnotationTrack classTrack;
	private List<AnnotationTrack> annotationTracks;
	private List<ReadableSensorTrack> sensorTracks;
	private Range fullRange;
	private ArrayList<Attribute> attributes;
	private Datum currentData[];

	public InstancesAdapter(AnnotationTrack classTrack,
			List<AnnotationTrack> annotationTracks,
			List<ReadableSensorTrack> sensorTracks, Range fullRange) {
		this.classTrack = classTrack;
		this.annotationTracks = annotationTracks;
		this.sensorTracks = sensorTracks;
		this.currentData = new Datum[sensorTracks.size()];
		this.fullRange = fullRange;
		this.attributes = convertToAttributes(classTrack, annotationTracks, sensorTracks);
	}

	public Instances getTrainingsInstace(Range range) {
		Instances instances = new Instances("Training", attributes, 0);
		instances.setClassIndex(1);
		LinkedList<Iterator<? extends Datum>> sensorIterators = new LinkedList<>();
		LinkedList<Iterator<? extends Annotation>> annotationIterators = new LinkedList<>();
		LinkedList<Iterator<? extends Datum>> sensorIteratorsTs = new LinkedList<>();
		for(ReadableSensorTrack t: sensorTracks) {
			Iterable<? extends Datum> track = t.getData(fullRange);
			sensorIterators.add(track.iterator());
			sensorIteratorsTs.add(track.iterator());
		}
		
		for(AnnotationTrack t: annotationTracks) {
			Iterable<? extends Annotation> track = t.getData(range);
			annotationIterators.add(track.iterator());
		}
		
		Annotation currentAnnotations = null;
		Datum currentData = null;
		
		Iterable<? extends Annotation> classAnnotations = classTrack.getData(fullRange);

		for (Annotation a : classAnnotations) {
			Range r = a.getRange();
			long timeStamp = getNextTimeStamp(r.getStart() - 1, sensorIteratorsTs);

			while (timeStamp < r.getEnd()) {
				Instance instance = new DenseInstance(attributes.size());
				instance.setDataset(instances);
				int idx = 0;
				instance.setValue(idx, timeStamp);
				idx++;
				instance.setValue(idx, a.getLabel());
				System.out.println(a.getLabel());
				idx++;
				for (Iterator<? extends Annotation> iter : annotationIterators) {
					if (iter.hasNext()) {
						if (currentAnnotations == null) {
							currentAnnotations = iter.next();
						}
						while (currentAnnotations.getRange().getEnd() < timeStamp) {
							if (iter.hasNext()) {
								currentAnnotations = iter.next();
							}
						}
						if (currentAnnotations.getRange()
								.contains(timeStamp)) {
							String label = currentAnnotations.getLabel();
							instance.setValue(idx, label);
						}
						idx++;
					}
				}

				for (Iterator<? extends Datum> iter : sensorIterators) {
					if (iter.hasNext()) {
						if (currentData == null) {
							currentData = iter.next();
						}
						while (currentData.timestamp < timeStamp) {
							if (iter.hasNext()) {
								currentData = iter.next();
							}
						}
						if (currentData.timestamp == timeStamp) {
							instance.setValue(idx, currentData.value);
						}
						idx++;
					}
				}

				instances.add(instance);
				timeStamp = getNextTimeStamp(timeStamp, sensorIteratorsTs);
			}
		}

		return instances;
	}
	
	public Instances getInstanceToClassify() {
		Instances instances = new Instances("Classification", attributes, 0);
		instances.setClassIndex(1);
		
		LinkedList<Iterator<? extends Datum>> sensorIterators = new LinkedList<>();
		LinkedList<Iterator<? extends Annotation>> annotationIterators = new LinkedList<>();
		LinkedList<Iterator<? extends Datum>> sensorIteratorsTs = new LinkedList<>();
		for(ReadableSensorTrack t: sensorTracks) {
			Iterable<? extends Datum> track = t.getData(fullRange);
			sensorIterators.add(track.iterator());
			sensorIteratorsTs.add(track.iterator());
		}
		
		for(AnnotationTrack t: annotationTracks) {
			Iterable<? extends Annotation> track = t.getData(fullRange);
			annotationIterators.add(track.iterator());
		}
		Iterable<? extends Annotation> classAnnotations = classTrack.getData(fullRange);
		Iterator<? extends Annotation> classIter = classAnnotations.iterator();
		Annotation currentClassAnnot = null;
		Annotation currentAnnotations = null;
		Datum currentData = null;
		if(classIter.hasNext()) {
			currentClassAnnot = classIter.next();
		}
		
		long timeStamp = getNextTimeStamp(fullRange.getStart() - 1, sensorIteratorsTs);
			
		while (timeStamp < fullRange.getEnd()) {
			if(fullRange.contains(timeStamp)) {
				Instance instance = new DenseInstance(attributes.size());
				instance.setDataset(instances);
				int idx = 0;
				instance.setValue(idx, timeStamp);
				idx++;
				
				while (currentClassAnnot.getRange().getEnd() < timeStamp) {
					if(classIter.hasNext()) {
						currentClassAnnot = classIter.next();
					} else {
						break;
					}
				}
				if (currentClassAnnot.contains(timeStamp)) {
					String label = currentClassAnnot.getLabel();
					instance.setValue(idx, label);
				} else {
					instance.setValue(idx, instances.attribute(idx).indexOfValue("?"));
				}
				idx++;
				
				for (Iterator<? extends Annotation> iter : annotationIterators) {
					if (iter.hasNext()) {
						if (currentAnnotations == null) {
							currentAnnotations = iter.next();
						}
						while (currentAnnotations.getRange().getEnd() < timeStamp) {
							if (iter.hasNext()) {
								currentAnnotations = iter.next();
							}
						}
						if (currentAnnotations.getRange()
								.contains(timeStamp)) {
							String label = currentAnnotations.getLabel();
							instance.setValue(idx, label);
						}
						idx++;
					}
				}

				for (Iterator<? extends Datum> iter : sensorIterators) {
					if (iter.hasNext()) {
						if (currentData == null) {
							currentData = iter.next();
						}
						while (currentData.timestamp < timeStamp) {
							if (iter.hasNext()) {
								currentData = iter.next();
							}
						}
						if (currentData.timestamp == timeStamp) {
							instance.setValue(idx, currentData.value);
						}
						idx++;
					}
				}
				
				instances.add(instance);
				timeStamp = getNextTimeStamp(timeStamp, sensorIteratorsTs);
			} else {
				timeStamp = getNextTimeStamp(timeStamp, sensorIteratorsTs);
			}
		}
		return instances;
	}
	
	private ArrayList<Attribute> convertToAttributes(AnnotationTrack classTrack, List<AnnotationTrack> selectedAnnotationTracks, List<ReadableSensorTrack> selectedSensorTracks) {
		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(new Attribute("Timestamp"));
		UniqueDataDecorator<Annotation> utd = new UniqueDataDecorator<>(classTrack, Annotation.LABEL_COMPARATOR);
		utd.force();
		Set<Annotation> uniques =utd.getUniqueData();
		List<String> values = new LinkedList<>();
		for(Annotation a: uniques) {
			values.add(a.getLabel());
		}
		attributes.add(new Attribute(String.valueOf(classTrack.getId()), values));
		
		for (AnnotationTrack at : selectedAnnotationTracks) {
			utd = new UniqueDataDecorator<>(at, Annotation.LABEL_COMPARATOR);
			utd.force();
			uniques =utd.getUniqueData();
			values = new LinkedList<>();
			for(Annotation a: uniques)
				values.add(a.getLabel());
			attributes.add(new Attribute(String.valueOf(at.getId()), values));
		}
		for (ReadableSensorTrack st : selectedSensorTracks) {
			attributes.add(new Attribute(String.valueOf(st.getId())));
		}
		return attributes;
	}
	
	public long getNextTimeStamp(long timeStamp, LinkedList<Iterator<? extends Datum>> sensorIterators) {
		long nextTimeStamp = fullRange.getEnd();
		if(nextTimeStamp <= timeStamp + 1)
			return nextTimeStamp;
		for(int i = 0; i < sensorIterators.size(); i++) {
			if (currentData[i] == null) {
				if (sensorIterators.get(i).hasNext()) {
					currentData[i] = sensorIterators.get(i).next();
				}
			}
			while(currentData[i].timestamp <= timeStamp) {
				currentData[i] = sensorIterators.get(i).next();
			}
			if (currentData[i].timestamp < nextTimeStamp) {
				nextTimeStamp = currentData[i].timestamp;
			}
		}
		return nextTimeStamp;
	}
}
