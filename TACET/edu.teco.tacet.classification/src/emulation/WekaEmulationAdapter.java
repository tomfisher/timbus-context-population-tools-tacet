package emulation;

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

public class WekaEmulationAdapter {

	private ReadableSensorTrack classTrack;
	private List<AnnotationTrack> annotationTracks;
	private List<ReadableSensorTrack> sensorTracks;
	ArrayList<Attribute> attributes;

	public WekaEmulationAdapter(ReadableSensorTrack classTrack,
			List<AnnotationTrack> annotationTracks,
			List<ReadableSensorTrack> sensorTracks) {
		this.classTrack = classTrack;
		this.annotationTracks = annotationTracks;
		this.sensorTracks = sensorTracks;
		this.attributes = convertToAttributes(classTrack, annotationTracks, sensorTracks);
	}

	public Instances getTrainingsInstace(Range range) {
		Instances instances = new Instances("Training", attributes, 0);
		instances.setClassIndex(1);
		LinkedList<Iterator<? extends Datum>> sensorIterators = new LinkedList<>();
		LinkedList<Iterator<? extends Annotation>> annotationIterators = new LinkedList<>();
		for(ReadableSensorTrack t: sensorTracks) {
			Iterable<? extends Datum> track = t.getData(range);
			sensorIterators.add(track.iterator());
		}
		
		for(AnnotationTrack t: annotationTracks) {
			Iterable<? extends Annotation> track = t.getData(range);
			annotationIterators.add(track.iterator());
		}
		
		Annotation currentAnnotations = null;
		Datum currentData = null;
		
		Iterable<? extends Datum> classData = classTrack.getData(range);
		for (Datum d : classData) {
			long timeStamp = d.timestamp;
			Instance instance = new DenseInstance(attributes.size());
			instance.setDataset(instances);
			int idx = 0;
			instance.setValue(idx, timeStamp);
			idx++;
			instance.setValue(idx, d.value);
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
					if (currentAnnotations.getRange().contains(timeStamp)) {
						String label = currentAnnotations.getLabel();
						instance.setValue(idx, label);
					}
					currentAnnotations = null;
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
					currentData = null;
					idx++;
				}
			}

			instances.add(instance);
		}
		return instances;
	}
	
	public Instances getInstanceToClassify(Range range) {
		Instances instances = new Instances("Classification", attributes, 0);
		instances.setClassIndex(1);
		
		Iterable<? extends Datum> classData = classTrack.getData(range);
		LinkedList<Iterator<? extends Datum>> sensorIterators = new LinkedList<>();
		LinkedList<Iterator<? extends Annotation>> annotationIterators = new LinkedList<>();
		for(ReadableSensorTrack t: sensorTracks) {
			Iterable<? extends Datum> track = t.getData(range);
			sensorIterators.add(track.iterator());
		}
		
		for(AnnotationTrack t: annotationTracks) {
			Iterable<? extends Annotation> track = t.getData(range);
			annotationIterators.add(track.iterator());
		}
		
		//long timeStamp = getNextTimeStamp(fullRange.getStart() - 1);
		
		Annotation currentAnnotations = null;
		Datum currentData = null;
		
		for (Datum d : classData) {
			long timeStamp = d.timestamp;
			Instance instance = new DenseInstance(attributes.size());
			instance.setDataset(instances);
			int idx = 0;
			instance.setValue(idx, timeStamp);
			idx++;
			instance.setValue(idx, 0.0);
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
					if (currentAnnotations.getRange().contains(timeStamp)) {
						String label = currentAnnotations.getLabel();
						instance.setValue(idx, label);
					}
					currentAnnotations = null;
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
					currentData = null;
					idx++;
				}
			}

			instances.add(instance);
		}
		return instances;
	}
	
	private ArrayList<Attribute> convertToAttributes(ReadableSensorTrack classTrack, List<AnnotationTrack> selectedAnnotationTracks, List<ReadableSensorTrack> selectedSensorTracks) {
		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(new Attribute("Timestamp"));
		attributes.add(new Attribute(String.valueOf(classTrack.getId())));

		for (AnnotationTrack at : selectedAnnotationTracks) {
			UniqueDataDecorator<Annotation> utd = new UniqueDataDecorator<>(at, Annotation.LABEL_COMPARATOR);
			utd.force();
			Set<Annotation> uniques =utd.getUniqueData();
			List<String> values = new LinkedList<>();
			for(Annotation a: uniques)
				values.add(a.getLabel());
			attributes.add(new Attribute(String.valueOf(at.getId()), values));
		}
		for (ReadableSensorTrack st : selectedSensorTracks) {
			attributes.add(new Attribute(String.valueOf(st.getId())));
		}
		return attributes;
	}
}
