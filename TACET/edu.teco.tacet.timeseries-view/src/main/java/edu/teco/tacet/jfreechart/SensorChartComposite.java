package edu.teco.tacet.jfreechart;

import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.Observable;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.RectangleInsets;
import org.osgi.service.prefs.Preferences;

import edu.teco.tacet.observer.IViewComponent;
import edu.teco.tacet.time.TrackController;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.State;
import edu.teco.tacet.track.StateObservable;
import edu.teco.tacet.track.TrackManager;

public class SensorChartComposite extends ChartComposite implements KeyListener, IViewComponent{
	
	private TrackController controller;
	private SensorDataTrackView view;
	private State state;
	private TrackResourcesReal track;
	private String name;
	private boolean wasNumerical;
	private String timestampFormat;
	public SensorChartComposite(Composite comp, int style, JFreeChart chart,
			boolean properties, boolean save, boolean print, boolean zoom,
			boolean tooltips,
			SensorDataTrackView view, TrackResourcesReal track, String name) {
		super(comp, style, chart, properties, save, print, zoom, tooltips);
		this.view = view;
		addSWTListener(this);
		state = StateObservable.getInstance().getState();
		controller = new TrackController(this);
		controller.addObserver(this);
		this.track = track;
		this.name = name;
		
		Preferences preferences = ConfigurationScope.INSTANCE
				.getNode("edu.teco.tacet.preferences");
		Preferences sub1 = preferences.node("ui1");
		wasNumerical = sub1.getBoolean("numerical", false);
		timestampFormat = sub1.get("timestampformat", "yyyy-MM-dd HH:mm:ss");
	}

	@Override
	public void mouseDown(MouseEvent e) {
		controller.mouseDown(e);
	}

	@Override
	public void mouseMove(MouseEvent e) {
		controller.mouseMove(e);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		controller.mouseUp(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		controller.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		controller.keyReleased(e);		
	}

	@Override
	public void paintControl(PaintEvent e) {
		super.paintControl(e);
		
		if(state.isRangeSelected()){
			e.gc.setBackground(new Color(e.display, new RGB(0, 0, 200)));
			e.gc.setAlpha(70);
			int start = translateTimestampToPixel(state.getSelectedRange()
					.getStart());
			int end = translateTimestampToPixel(state.getSelectedRange().getEnd());
			e.gc.fillRectangle(start, 0, end-start, e.height);
		}
		
//		if(state.getAnnotationDialogOpen()) {
//			Color clrEdit = new Color(e.display, 255, 0, 0);
//			e.gc.setBackground(clrEdit);
//			e.gc.setAlpha(100);
//			int editX = translateTimestampToPixel(state.getSelectedRange().getStart());
//			int editY = 0;
//			int editHeight = e.height;
//			int editWidth = translateTimestampToPixel(state.getSelectedRange().getEnd()) - editX;
//			e.gc.fillRectangle(editX, editY, editWidth, editHeight);
//
//			clrEdit.dispose();
//		}
		e.gc.setAlpha(255);
		Color clrContour = new Color(e.display, new RGB(0, 100, 0));
		e.gc.setForeground(clrContour);
		e.gc.setFont(new Font(e.gc.getDevice(), "Helvetica", 8, SWT.NORMAL));
		e.gc.drawText(name, 0,0, false);
		e.gc.setAlpha(255);
		clrContour = new Color(e.display, new RGB(211, 0, 0));
		e.gc.setForeground(clrContour);
		e.gc.setLineWidth(3);
		e.gc.drawLine(getTimeLinePos(), 0, getTimeLinePos(), e.height);
		markEmptyData(e);
	}

	private void markEmptyData(PaintEvent e) {
		e.gc.setAlpha(200);
		Color clrContour = new Color(e.display, new RGB(0, 0, 255));
		e.gc.setForeground(clrContour);
		Range rangeCoverd = track.getCoveredRange();
		Range rangeVisible = state.getVisibleRange();	
		if(rangeVisible.contains(rangeCoverd.getStart())&& rangeVisible.getStart() < rangeCoverd.getStart()){
			//mark start Range
			e.gc.fillRectangle(translateTimestampToPixel(rangeVisible.getStart()), 0, translateTimestampToPixel(rangeCoverd.getStart()), e.height);
		}
		if(rangeVisible.contains(rangeCoverd.getEnd()) && rangeCoverd.getEnd() < rangeVisible.getEnd()){
			//mark end Range
			e.gc.fillRectangle(translateTimestampToPixel(rangeCoverd.getEnd()), 0, translateTimestampToPixel(rangeVisible.getEnd()), e.height);
		}
		if(rangeVisible.getEnd()<rangeCoverd.getStart() || rangeVisible.getStart()>rangeCoverd.getEnd()){
			//mark All
			e.gc.fillRectangle(translateTimestampToPixel(rangeVisible.getStart()), 0, translateTimestampToPixel(rangeVisible.getEnd()), e.height);
		}
			
			
		
	}

	@Override
	public void dispose() {		
		super.dispose();
	}

	@Override
	public void update(Observable o, Object arg) {
		//Does not work
		updatePreferences();
		if (arg instanceof State) {
			setState((State) arg);			
		} else {
			System.out.println("Whats the Problem?");
		}		
	}

	private void updatePreferences() {
		Preferences preferences = ConfigurationScope.INSTANCE
				.getNode("edu.teco.tacet.preferences");
		Preferences sub1 = preferences.node("ui1");
		if (!wasNumerical && sub1.getBoolean("numerical", false)) {	
			wasNumerical = !wasNumerical;
			JFreeChart chart =
			ChartFactory.createXYLineChart("Version 0.0.1", "time",
					"amplitude", track.getDataSet(), PlotOrientation.VERTICAL,
					false, false, false);
			initChart(chart, true);
			super.setChart(chart);			
		} else if(!sub1.getBoolean("numerical", true) && wasNumerical){			
			wasNumerical = !wasNumerical;
			if(!sub1.get("timestampformat", "yyyy-MM-dd HH:mm:ss").equals(timestampFormat)){
				timestampFormat = sub1.get("timestampformat", "yyyy-MM-dd HH:mm:ss");
				JFreeChart chart =	ChartFactory.createTimeSeriesChart("Version 0.0.1", "time",
						"amplitude", track.getDataSet(), false, false, false);
				initChart(chart, false);
				super.setChart(chart);				
			}			
		}		
	}
	
	private void initChart(JFreeChart chart, boolean numerical) {
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
		if (!numerical) {
			((DateAxis) domainAxis).setDateFormatOverride(new SimpleDateFormat(
					timestampFormat));
		}
		domainAxis.setAutoRange(true);
		chart.setTitle((String) null);
		chart.setAntiAlias(false);
		chart.setBorderVisible(false);
		chart.setBackgroundPaint(null);
		chart.setBackgroundPaint(ChartColor.white);
		addSWTListener(this);
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void setState(State state) {
		this.state = state;	
		track.timeChanged(state.getTime());
		track.updateInJob();
		
	}

	public long translatePixelToTimestamp(int pixel) {
      Rectangle bounds = getPlotBounds();
      double rate = state.getVisibleRange().getDistance()
              / bounds.getWidth();
      long subTs = state.getVisibleRange().getStart();
      double out = ((double) rate * (pixel - (int) bounds.getX()))
          + subTs;      
      return (long)out;
  }

  @Override
  public int translateTimestampToPixel(long timestamp) {
      Rectangle bounds = getPlotBounds();
      double ratio = (double) bounds.getWidth() / state.getVisibleRange().getDistance();
      long subTs = timestamp - state.getVisibleRange().getStart();
      double pixel = (ratio * subTs) + bounds.getX();
      /**
       * For Testing
       */
//      System.out.println("ratio:"+ratio+"subTs:"+subTs+"pixel:"+
//      pixel+"\n timestamp:"+timestamp+"solution:"+(int)Math.round(pixel)+"" +
//      		"bounds:"+bounds.getWidth()+"distance"+state.getVisibleRange().
//      		getDistance()+"bounds.x"+bounds.getX());
//      if(timestamp!=translatePixelToTimestamp((int)pixel)){
//      	//System.out.println("Pixel calculation wrong?");      	
//      }
      return (int)Math.round(pixel);
  }

	@Override
	public int getTimeLinePos() {
		return translateTimestampToPixel(state.getTime());
		
	}
	
	public Range getGlobalRange(){
		return TrackManager.getInstance().getGlobalRange();
	}
	
	private Rectangle getPlotBounds() {
        Rectangle ret = new Rectangle();       
        EntityCollection ec = this.getChartRenderingInfo()
            .getEntityCollection();
        if (ec.getEntities().size() > 1
            && ec.getEntity(1).getArea() instanceof Rectangle) {
            ret = (Rectangle) ec.getEntity(1).getArea();
        }
        return ret;
    }
	
	/**
     * Only for Testing
     */
    public void getRectangels() {
        Rectangle ret = new Rectangle((int) 
            getChartRenderingInfo().getChartArea().getWidth(),
            (int) getChartRenderingInfo()
                .getChartArea().getHeight());
        System.out.println("-----------------------"+name+"-----------------------");
        System.out.println(ret.height + "," + ret.width + "," + ret.x);
        EntityCollection ec = getChartRenderingInfo()
            .getEntityCollection();
        for (int i = 0; i < ec.getEntityCount(); i++) {
            if (ec.getEntity(i).getArea() instanceof Rectangle) {
                Rectangle ret1 = (Rectangle) ec.getEntity(i).getArea();
                System.out.println(ret1.height + "," + ret1.width + ","
                    + ret1.x);
            }
            
        }

    }


	



}
