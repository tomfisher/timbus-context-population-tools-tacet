package edu.teco.tacet.jfreechart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.osgi.service.prefs.Preferences;

import edu.teco.tacet.timeseriesview.ITrackDisplayer;
import edu.teco.tacet.track.TrackManager;

public class SensorDataTrackView extends ViewPart implements ITrackDisplayer {

	private ArrayList<TrackResourcesReal> tracks = new ArrayList<TrackResourcesReal>();
	private ArrayList<SensorChartComposite> chartComposite = new ArrayList<SensorChartComposite>();
	private ArrayList<Long> ids = new ArrayList<Long>();
	private Composite aboveScrollright;
	private ScrolledComposite scrolledComposite;
	private String id;
	public static int NUMBER_CHARTS = 0;
	private boolean disposed = false;

	@Override
	public void createPartControl(Composite parent) {
		disposed = false;
		scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
				true, 1, 1));

		aboveScrollright = new Composite(scrolledComposite, SWT.NONE);
		aboveScrollright.setLayout(new GridLayout(1, false));
		aboveScrollright.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		scrolledComposite.setContent(aboveScrollright);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinSize(aboveScrollright.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
		
	}

	private JFreeChart createChart(XYDataset currentDataset) {
		JFreeChart chart;
		Preferences preferences = ConfigurationScope.INSTANCE
				.getNode("edu.teco.tacet.preferences");
		Preferences sub1 = preferences.node("ui1");
		if (sub1.getBoolean("numerical", false)) {
			chart = ChartFactory.createXYLineChart("Version 0.0.1", "time",
					"amplitude", currentDataset, PlotOrientation.VERTICAL,
					false, false, false);
		} else {
			chart = ChartFactory.createTimeSeriesChart("Version 0.0.1", "time",
					"amplitude", currentDataset, false, false, false);
		}

		chart.setTitle((String) null);
		chart.setAntiAlias(false);
		chart.setBorderVisible(false);
		chart.setBackgroundPaint(null);
		chart.setBackgroundPaint(ChartColor.white);
		
		return chart;
	}

	private void initChart(JFreeChart chart) {
		XYPlot plot = chart.getXYPlot();
		plot.getRenderer().setBasePaint(ChartColor.BLACK);
		plot.getRenderer().setSeriesPaint(0, ChartColor.BLACK);
		
		ValueAxis rangeAxis = plot.getRangeAxis();
		ValueAxis domainAxis = plot.getDomainAxis();
		rangeAxis.setLowerMargin(0.05);
		rangeAxis.setUpperMargin(0.05);
		rangeAxis.setLabel(null);
		domainAxis.setLabel(null);
		domainAxis.setLowerMargin(0);
		domainAxis.setUpperMargin(0);
		// In example but not in the original version maybe unnecessary
		plot.setAxisOffset(new RectangleInsets(10.0, 10.0, 10.0, 10.0));
		AxisSpace space = new AxisSpace();
	    space.setLeft(50);
	    plot.setFixedRangeAxisSpace(space);
		Preferences preferences = ConfigurationScope.INSTANCE
				.getNode("edu.teco.tacet.preferences");		
		Preferences sub1 = preferences.node("ui1");		
		if (!sub1.getBoolean("numerical", true)) {
			((DateAxis) domainAxis).setDateFormatOverride(new SimpleDateFormat(
					sub1.get("timestampformat", "yyyy-MM-dd HH:mm:ss")));
		}

		domainAxis.setAutoRange(true);
		// changeRange(0, 100, chart);
	}

	public void changeRange(double lower, double upper, JFreeChart chart) {
		if (chart != null) {
			chart.getXYPlot().getRangeAxis().setRange(lower, upper);
		}
	}

	@Override
	public void showTracks(Iterable<Long> id) {
		for (SensorChartComposite scc : chartComposite) {
			scc.dispose();
		}
		ids.clear();
		tracks = new ArrayList<TrackResourcesReal>();
		chartComposite = new ArrayList<SensorChartComposite>();
		TrackManager manager = TrackManager.getInstance();

		TrackResourcesReal dataSet;
		for (Long long1 : id) {
			dataSet = new TrackResourcesReal(Display.getCurrent(),
					manager.getSensorTrack(long1));
			tracks.add(dataSet);
			ids.add(long1);
		}
		// Initis the charts

		JFreeChart chart;
		SensorChartComposite tempChartComposite;
		int counter = 0;
		for (TrackResourcesReal track : tracks) {
			chart = createChart(track.getDataSet());

			initChart(chart);
			tempChartComposite = new SensorChartComposite(aboveScrollright,
					SWT.NONE, chart, false, false, false, false, false, this,
					track, TrackManager.getInstance()
							.getSensorTrack(ids.get(counter)).getMetaData()
							.getName());
			track.setChart(tempChartComposite);
			GridData data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.horizontalAlignment = SWT.FILL;
			data.heightHint = 30;
			tempChartComposite.setLayoutData(data);
			chartComposite.add(tempChartComposite);
			counter++;
		}

		FillLayout grid = new FillLayout(SWT.VERTICAL);
		aboveScrollright.setLayout(grid);

		// this.setPartName(partName)

	}

	@Override
	public void dispose() {
		disposed = true;
		// controller.setDisposed(true);
		for (SensorChartComposite scc : chartComposite) {
			scc.dispose();
		}
		super.dispose();
	}

	@Override
	public Iterable<Long> getVisibleTracks() {
		return ids;
	}

	@Override
	public void setFocus() {
		if (!TrackManager.isInitialised()) {
			return;
		}
	}

	@Override
	public void setOwnID(String id) {
		this.id = id;

	}

	@Override
	public String getOwnID() {
		return id;
	}

	@Override
	public void setDisplayerName(String name) {
		this.setPartName(name);
	}

}
