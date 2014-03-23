/*
 * Copyright 2013 TecO - Karlsruhe Institute of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package squirrel.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import squirrel.model.ModelFacade;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.util.Range;

@SuppressWarnings("serial")
public class SensorDataTrackView extends TrackView {

    public enum RangeAxisType {
        NUMBER, DATE;
    }

    private SensorDataFilterAdapter currentDataset;
    private JLabel lblNoData;

    private JFreeChart chart;
    public ChartPanel chartPanel;
    private RangeAxisType rangeAxisType;

    private SensorDataTrackView(ModelFacade model, int index, Range startRange,
            RangeAxisType rangeAxisType) {
        super(model, index, startRange);
        model.setFilterSize((int) (3 * startRange.getDistance()));
        DataSource<? extends DataColumn> source = model.getCurrentDataSource();
        this.name = source.getDataColumn(index).getName();
        this.rangeAxisType = rangeAxisType;
        this.currentDataset = new SensorDataFilterAdapter(model, source.toSensorIndex(index), startRange);
        this.setVisibleExcerpt(startRange);
        initGUI();
        chartPanel.setFocusable(true);
    }

    public SensorDataTrackView(ModelFacade model, int index, Range startRange) {
        this(model, index, startRange, RangeAxisType.NUMBER);
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.chart = createChart();
        this.chartPanel = createChartPanel(chart);
        this.add(chartPanel, BorderLayout.CENTER);
    }

    private JFreeChart createChart() {
        chart = ChartFactory.
            createXYLineChart(name,
                              "time",
                              "amplitude",
                              currentDataset,
                              PlotOrientation.VERTICAL,
                              false, // no legend
                              false, // no tooltips
                              false); // no urls
        chart.setTitle((String) null);
        chart.setAntiAlias(false);
        chart.setBorderVisible(false);
        chart.setBackgroundPaint(null);

        return chart;
    }

    private ChartPanel createChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDisplayToolTips(false);
        chartPanel.setMouseZoomable(false);
        chartPanel.setPopupMenu(null);
        XYPlot plot = chart.getXYPlot();
        plot.getRenderer().setBasePaint(Color.ORANGE);
        plot.getRenderer().setSeriesPaint(0, Color.ORANGE);
        ValueAxis rangeAxis = plot.getRangeAxis();
        ValueAxis domainAxis = plot.getDomainAxis();
        rangeAxis.setLowerMargin(0.02);
        rangeAxis.setUpperMargin(0.02);
        rangeAxis.setLabel(null);
        domainAxis.setLabel(null);
        domainAxis.setLowerMargin(0);
        domainAxis.setUpperMargin(0);
        chartPanel.setMinimumDrawWidth(0);
        chartPanel.setMaximumDrawWidth(2500);
        chartPanel.setMinimumDrawHeight(0);
        chartPanel.setMaximumDrawHeight(2500);
        return chartPanel;
    }


    Rectangle getPlotBounds() {
        Rectangle ret = new Rectangle(0, 0, 0, 0);
        EntityCollection ec = chartPanel.getChartRenderingInfo().getEntityCollection();
        if (ec.getEntities().size() > 1) {
            ret = (Rectangle) ec.getEntity(1).getArea();
        }
        return ret;
    }

    @Override
    public void setVisibleExcerpt(Range range) {
        super.setVisibleExcerpt(range);
        this.currentDataset.setVisibleExcerpt(range);
    }

    @Override
    public long translatePixelToTimestamp(int pixel) {
        Rectangle bounds = getPlotBounds();
        return (long) ((double) ((pixel - bounds.getX()) / bounds.getWidth() * visibleExcerpt.getDistance())) + visibleExcerpt.getStart();
    }

    @Override
    public int translateTimestampToPixel(long timestamp) {
        Rectangle bounds = getPlotBounds();
        long distance = visibleExcerpt.getDistance();
        return (int) ((double) (timestamp - visibleExcerpt.getStart()) / (double) distance * bounds.getWidth()) + (int) bounds.getX();
    }
}
