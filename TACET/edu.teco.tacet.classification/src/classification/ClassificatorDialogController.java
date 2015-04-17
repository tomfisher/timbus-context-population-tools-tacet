package classification;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.AnnotationTrack;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.ReadableSensorTrack;
import edu.teco.tacet.track.TrackManager;

public class ClassificatorDialogController {
	private ClassificatorDialog dialog;
	private List<AnnotationTrack> annotationTracks;
	private List<ReadableSensorTrack> sensorTracks;
	private Range fullRange;

	public ClassificatorDialogController(Shell parentShell,
			Iterable<ReadableSensorTrack> sensorTracks, Iterable<AnnotationTrack> annotationTracks) {
		LinkedList<String> sensorNames = new LinkedList<>();
		LinkedList<String> annotationNames = new LinkedList<>();
		ArrayList<Range> coveredRanges = new ArrayList<>();
		this.sensorTracks = new ArrayList<>();
		this.annotationTracks = new ArrayList<>();
		
		for(ReadableSensorTrack t : sensorTracks) {
			sensorNames.add(t.getMetaData().getName());
			this.sensorTracks.add(t);
			coveredRanges.add(t.getCoveredRange());
		}
		
		for(AnnotationTrack t : annotationTracks) {
			annotationNames.add(t.getMetaData().getName());
			this.annotationTracks.add(t);
		}
		
		this.dialog = new ClassificatorDialog(parentShell, sensorNames,
				annotationNames, coveredRanges);
	}

	public void classify() {
		dialog.create();
		if (dialog.open() == Window.OK) {
			List<AnnotationTrack> selectedAnnotationTracks = new ArrayList<>();
			List<ReadableSensorTrack> selectedSensorTracks = new ArrayList<>();
			
			AnnotationTrack classTrack = null;
			classTrack = annotationTracks.get(dialog.getClassIdx());

			for(int i : dialog.getAnnotationIdxs()) {
				if(classTrack.getId() != annotationTracks.get(i).getId())
					selectedAnnotationTracks.add(annotationTracks.get(i));
			}
			for(int i : dialog.getSensorIdxs()) {
				if(classTrack.getId() != sensorTracks.get(i).getId())
				selectedSensorTracks.add(sensorTracks.get(i));
			}
			
	        fullRange = TrackManager.getInstance().getGlobalRange();
	        InstancesAdapter adapter = new InstancesAdapter(classTrack, selectedAnnotationTracks, selectedSensorTracks, fullRange);
			FilteredClassifier trainedClassifier = trainClassifier(adapter);
			Instances instancesToClassify = adapter
					.getInstanceToClassify();

			try {
				addClassifiedAnnotations(trainedClassifier,
						instancesToClassify, (AnnotationTrack) classTrack);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	private void addClassifiedAnnotations(FilteredClassifier trainedClassifier,
			Instances instancesToClassify, AnnotationTrack classTrack) throws Exception {
		List<Annotation> annotations = new ArrayList<>();
		Annotation prevAnnotation = null;
		for (int i = 0; i < instancesToClassify.numInstances(); i++) {
            double pred = trainedClassifier.classifyInstance(instancesToClassify.instance(i));
            String prediction = instancesToClassify.classAttribute().value((int) pred);

            if (!prediction.equals("?")) {
                long timeStamp =(long) instancesToClassify.get(i).value(instancesToClassify.attribute("Timestamp"));
                Range newRange = new Range(timeStamp, timeStamp + 1);
                Annotation annotation = new Annotation(newRange, prediction);
                if(prevAnnotation != null) {
                	if (prevAnnotation.getLabel().equals(prediction)) {
                		prevAnnotation.setEnd(timeStamp + 1);
                	} else {
                		annotations.add(annotation);
                		prevAnnotation = annotation;
                	}
                } else {
                	if (annotation != null) {
                		annotations.add(annotation);
                		prevAnnotation = annotation;
                	}
                }
            } else {
            	prevAnnotation = null;
            	System.out.println("no prediction");
            }
        }
		for(Annotation a: annotations) {
			classTrack.deleteData(a.getRange());
			classTrack.insertData(a);
		}
		for(Annotation a: classTrack.getData(fullRange))
			System.out.println(a);
	}
	
	public FilteredClassifier trainClassifier(InstancesAdapter adapter) {
		
        Instances trainingInstances = adapter.getTrainingsInstace(fullRange);
        Classifier classifier = null;
		Class<?> cl;
		try {
			cl = Class.forName(dialog.getSelectedClassifier());
			Constructor<?> ctor = cl.getConstructor();
			classifier = (Classifier) ctor.newInstance();
			System.out.println(cl);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
        FilteredClassifier filteredClassifier = new FilteredClassifier();
        Remove rm = new Remove();
        rm.setAttributeIndices("1");
        filteredClassifier.setFilter(rm);
        filteredClassifier.setClassifier(classifier);
        try {
			filteredClassifier.buildClassifier(trainingInstances);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return filteredClassifier;
    }
}
