package emulation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Forecaster;
import net.sourceforge.openforecast.ForecastingModel;
import net.sourceforge.openforecast.models.MultipleLinearRegressionModel;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.nongen.ProjectUtil;
import edu.teco.tacet.track.AnnotationTrack;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.ReadableSensorTrack;
import edu.teco.tacet.track.TrackManager;

public class EmulatorDialogController {
	private EmulatorDialog dialog;
	private List<AnnotationTrack> annotationTracks;
	private List<ReadableSensorTrack> sensorTracks;
	private Range fullRange;
	private Shell shell;

	public EmulatorDialogController(Shell parentShell,
			Iterable<ReadableSensorTrack> sensorTracks, Iterable<AnnotationTrack> annotationTracks) {
		this.shell = parentShell;
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
		
		this.dialog = new EmulatorDialog(parentShell, sensorNames,
				annotationNames, coveredRanges);
	}

	public void emulate() {
		dialog.create();
		if (dialog.open() == Window.OK) {
			List<AnnotationTrack> selectedAnnotationTracks = new ArrayList<>();
			List<ReadableSensorTrack> selectedSensorTracks = new ArrayList<>();
			
			ReadableSensorTrack classTrack = sensorTracks.get(dialog.getClassIdx());

			for(int i : dialog.getAnnotationIdxs()) {
				if(classTrack.getId() != annotationTracks.get(i).getId())
					selectedAnnotationTracks.add(annotationTracks.get(i));
			}
			for(int i : dialog.getSensorIdxs()) {
				if(classTrack.getId() != sensorTracks.get(i).getId()) {
					selectedSensorTracks.add(sensorTracks.get(i));
					System.out.println(sensorTracks.get(i).getMetaData().getName());
				}
			}
			
	        fullRange = TrackManager.getInstance().getGlobalRange();
	        WekaEmulationAdapter adapter = new WekaEmulationAdapter(classTrack, selectedAnnotationTracks, selectedSensorTracks);
			Range range = dialog.getRangeToEmulate();
	       
			if (!(dialog.getSelectedClassifier().equals("MultilinearRegression") || dialog.getSelectedClassifier().equals("Bestfit"))) {
				FilteredClassifier trainedClassifier = trainClassifier(adapter);
				Instances instancesToClassify = adapter
						.getInstanceToClassify(fullRange);

				try {
					addNewTrack(trainedClassifier, instancesToClassify,
							(ReadableSensorTrack) classTrack);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if(dialog.getSelectedClassifier().equals("MultilinearRegression")) {
				DataSet ds = fillDataSet(classTrack, selectedSensorTracks, range);
				MultipleLinearRegressionModel model = new MultipleLinearRegressionModel();
				model.init(ds);
				System.out.println("Adding new track");
				addNewTrack(ds, fullRange, classTrack, selectedSensorTracks, model);
			} else if(dialog.getSelectedClassifier().equals("Bestfit")) {
				DataSet ds = fillDataSet(classTrack, selectedSensorTracks, range);
				ForecastingModel model = Forecaster.getBestForecast(ds);
				model.init(ds);
				System.out.println("Adding new track");
				addNewTrack(ds, fullRange, classTrack, selectedSensorTracks, model);
			}
		}
	}
	
	private void addNewTrack(FilteredClassifier trainedClassifier,
			Instances instancesToClassify, ReadableSensorTrack classTrack) throws Exception {
		List<Datum> newData = new LinkedList<>();
		for (int i = 0; i < instancesToClassify.numInstances(); i++) {
			double pred = trainedClassifier.classifyInstance(instancesToClassify.instance(i));
			long timeStamp =(long) instancesToClassify.get(i).value(instancesToClassify.attribute("Timestamp"));
			Datum datum = new Datum(timeStamp, pred);
			newData.add(datum);
		}
		long emulId = TrackManager.getInstance().createSensorTrackFrom(newData, classTrack.getMetaData().getName() + "_emulated");
		createNewGroup(classTrack.getId(), emulId, classTrack.getMetaData().getName() + " emulated");
		calculateMeanSquareError(newData, classTrack.getData(fullRange));
	}
	
	private void addNewTrack(DataSet dataSet, Range range, ReadableSensorTrack classTrack, List<ReadableSensorTrack> sensorTracks, ForecastingModel model) {
		List<Datum> newData = new LinkedList<>();

		Iterable<? extends Datum> dependentVariable = classTrack.getData(range);

		LinkedList<Iterator<? extends Datum>> iterators = new LinkedList<>();
		Datum currentData = null;
		long[] trackIds = new long[sensorTracks.size()];
		int i = 0;
		for (ReadableSensorTrack t : sensorTracks) {
			Iterable<? extends Datum> track = t.getData(range);
			iterators.add(track.iterator());
			trackIds[i] = t.getId();
			i++;
		}
		int idx = 0;
		for (Datum d : dependentVariable) {
			TacetDataPoint dp = new TacetDataPoint(d.timestamp, d.value);
			i = 0;
			for (Iterator<? extends Datum> iter : iterators) {
				if (iter.hasNext()) {
					if (currentData == null) {
						currentData = iter.next();
					}
					while (currentData.timestamp < d.timestamp) {
						if (iter.hasNext()) {
							currentData = iter.next();
						}
					}
					if (currentData.timestamp == d.timestamp) {
						dp.setIndependentValue(Long.toString(trackIds[i]),
								currentData.value);
					}
					i++;
					currentData = null;
				}
			}
			double predValue = model.forecast(dp);
			Datum datum = new Datum(d.timestamp, predValue);
			newData.add(datum);
			idx++;
			System.out.println(datum);
		}
		System.out.println(idx);

		System.out.println("done");
		long emulId = TrackManager.getInstance().createSensorTrackFrom(newData, classTrack.getMetaData().getName() + "_emulated");
		createNewGroup(classTrack.getId(), emulId, classTrack.getMetaData().getName() + " emulated");
		calculateMeanSquareError(newData, dependentVariable);
	}

	public FilteredClassifier trainClassifier(WekaEmulationAdapter adapter) {
		
        Instances trainingInstances = adapter.getTrainingsInstace(dialog.getRangeToEmulate());
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
	
	private DataSet fillDataSet(ReadableSensorTrack classTrack,
			List<ReadableSensorTrack> sensorTracks, Range range) {
		DataSet dataSet = new DataSet();
		Iterable<? extends Datum> dependentVariable = classTrack.getData(range);
		LinkedList<Iterator<? extends Datum>> iterators = new LinkedList<>();
		Datum currentData = null;
		long[] trackIds = new long[sensorTracks.size()];
		int i = 0;
		for(ReadableSensorTrack t: sensorTracks) {
			Iterable<? extends Datum> track = t.getData(range);
			iterators.add(track.iterator());
			trackIds[i] = t.getId();
			i++;
		}
		int idx = 0;
		for(Datum d : dependentVariable) {
			idx++;
			TacetDataPoint dp = new TacetDataPoint(d.timestamp, d.value);
			i = 0;
			for(Iterator<? extends Datum> iter : iterators) {
				if (iter.hasNext()) {
					if (currentData == null) {
							currentData = iter.next();
					}
					while (currentData.timestamp < d.timestamp) {
						if (iter.hasNext()) {
							currentData = iter.next();
						}
					}
					if (currentData.timestamp == d.timestamp) {
						dp.setIndependentValue(Long.toString(trackIds[i]),
								currentData.value);
					}
					i++;
					currentData = null;
				}
			}
			dataSet.add(dp);
			
		}
		System.out.println(idx);
		return dataSet;
	}
	
	private void calculateMeanSquareError(List<? extends Datum> emulated, Iterable<? extends Datum> original) {
		double d = 0.0;
		Iterator<? extends Datum> iterO = original.iterator();
		Iterator<? extends Datum> iterE = emulated.iterator();
		int counter = 0;
		
		while(iterO.hasNext()) {
			d += Math.pow(iterO.next().value - iterE.next().value, 2);
			counter++;
		}
		
		d = d / counter;
		
		MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK);
		dialog.setText("Succesful emulation");
		dialog.setMessage("Succesful emulation with a mean square error: " + d);
		dialog.open();
	}
	
	private void createNewGroup(long orig, long emulated, String name) {
		Group group = MetaFactory.eINSTANCE.createGroup();
		group.setName(name);
		group.setId(TrackManager.getInstance().getSourceIdGenerator().generateSourceId());

		Project project = TrackManager.getInstance().getCurrentProject();

		Timeseries origSeries = ProjectUtil.getTimeseries(project, orig);
		Timeseries emulatedSeries = ProjectUtil.getTimeseries(project, emulated);

		group.getTimeseries().add(origSeries);
		group.getTimeseries().add(emulatedSeries);
		
		project.getGroups().add(group);
	}
}
