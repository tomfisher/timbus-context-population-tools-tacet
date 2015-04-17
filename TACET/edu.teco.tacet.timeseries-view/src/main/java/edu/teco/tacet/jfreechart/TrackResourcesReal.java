package edu.teco.tacet.jfreechart;

import java.util.LinkedList;

import org.eclipse.swt.widgets.Display;
import org.jfree.data.general.Series;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.ReadableSensorTrack;

public class TrackResourcesReal {
	private XYSeries series = new XYSeries("Series");
	private XYSeriesCollection dataset = new XYSeriesCollection();
	private Display display;
	private long time = 0;
	private SensorChartComposite view;
	private long resolution = 1;
	private LinkedList<Range> ranges;
	private ReadableSensorTrack track;
	private long start;
	private long end;
	private boolean flagStart = true;
	public TrackResourcesReal(Display display,
			ReadableSensorTrack track) {	
		
		this.display = display;
		this.track = track;
	}
	
	public void setChart(SensorChartComposite view){
		this.view = view;
		creatDataSet();
	}

	private void creatDataSet() {
		updateDataSet();
		dataset.addSeries(series);

	}

	public XYSeriesCollection getDataSet() {
		return dataset;
	}

	public void redraw() {
		display.asyncExec(new Runnable() {
			public void run() {
				series.fireSeriesChanged();
			}
		});
	}

	public void updateInJob() {
		// NO syncExec because the view will freeze
		display.asyncExec(new Runnable() {
			public void run() {
				updateDataSet();
			}
		});
	}

	private void updateDataSet() {
		series.setNotify(false);
		if (time > 0) {
			series.clear();
		}
		Iterable<? extends Datum> values = getData(view.getState().getVisibleRange());
		flagStart = true;
		if (values.iterator().hasNext()) {
			for (Datum tu : values) {
				if(flagStart){
					flagStart = false;
					if(start!=view.getState().getVisibleRange().getStart()){
						series.addOrUpdate(view.getState().getVisibleRange().getStart(), 0);
					}
				}
				series.addOrUpdate(tu.timestamp, tu.value);				
			}
		}
		if(end!=view.getState().getVisibleRange().getEnd()){
			series.addOrUpdate(view.getState().getVisibleRange().getEnd(), 0);
		}
		if(flagStart){
			insertFackeData();
		}
		series.setNotify(true);		
	}

	private void insertFackeData() {
		series.addOrUpdate(view.getState().getVisibleRange().getStart(), 0);
		series.addOrUpdate(view.getState().getVisibleRange().getEnd(), 0);		
	}

	public void timeChanged(long timeStamp) {
		time = timeStamp;
	}

	public Iterable<? extends Datum> getData(Range range) {
		return track.getData(range);
	}

	public Range getCoveredRange(){
		return track.getCoveredRange();
	}
	
	
	
	public void setResolution(long resolution) {
		this.resolution = resolution;
		track.setResolution(resolution);
	}

}
