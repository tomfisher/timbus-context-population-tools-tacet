/* 
 * Copyright 2013-2014 TECO - Karlsruhe Institute of Technology.
 * 
 * This file is part of TACET.
 * 
 * TACET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TACET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TACET.  If not, see <http://www.gnu.org/licenses/>.
 */

package squirrel.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import squirrel.controller.TrackController;
import squirrel.model.ModelFacade;
import squirrel.util.Range;

@SuppressWarnings("serial")
public abstract class TrackView extends JPanel {
    protected ModelFacade model;
    protected String name;
    protected int index;
    protected int timeLinePos = 0;
    protected boolean isSelectionActive;
    protected Range selection = new Range(0, 0);
    protected Range visibleExcerpt;
    protected double pixelTimeRatio = 1.0d;

    protected boolean paintName = true;

    protected Color clrSelection = new Color(0, 0, 255, 100);
    protected Color clrTimeLine = Color.RED;
    protected Color clrNoData = new Color(255, 0, 0, 100);
    protected Color clrName = new Color(0, 102, 0);

    protected TrackView(ModelFacade model, int index, Range startRange) {
        this.model = model;
        this.index = index;
        this.visibleExcerpt = startRange;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        long maxTime = model.getEndTimeStamp();
        if (visibleExcerpt.getEnd() > maxTime) {
            if (maxTime < visibleExcerpt.getStart()) { // the whole vE is out of data
                maxTime = visibleExcerpt.getStart();
            }
            g.setColor(clrNoData);
            g.fillRect(translateTimestampToPixel(maxTime), 0,
                    translateTimestampToPixel(visibleExcerpt.getEnd()) - translateTimestampToPixel(maxTime),
                    super.getHeight());
        }

        if (isSelectionActive) {
            g.setColor(clrSelection);
            g.fillRect(translateTimestampToPixel(selection.getStart()), 0,
                    translateTimestampToPixel(selection.getEnd()) - translateTimestampToPixel(selection.getStart()), super.getHeight());
        }
        if (paintName) {
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g.setColor(clrName);
            g.drawString(this.name, 3, 10);
        }
        g.setColor(clrTimeLine);
        g.drawLine(timeLinePos, 0, timeLinePos, super.getHeight());
    }

    /**
     * Given a pixel {@code x} compute corresponding timestamp.
     *
     * @param x an x-value contained in this component.
     * @return return the corresponding timestamp.
     * @see #translateTimestampToPixel(int)
     */
    public abstract long translatePixelToTimestamp(int x);

    /**
     * Given a {@code timestamp} compute the corresponding pixel in this
     * component.
     *
     * @param timestamp that is to be translated
     * @return return the corresponding x value in pixel
     * @see #translatePixelToTimestamp(int)
     */
    public abstract int translateTimestampToPixel(long timestamp);

    /**
     * Given a {@code distance} in pixel and the current {@code pixelTimeRatio},
     * compute the distance in timeunits.
     * @param distance a distance given in pixel
     * @return the distance in timeunits
     * @see #getPixelTimeRatio()
     */
    public long translatePixelToTimestampDistance(int distance) {
        return (long) (distance / pixelTimeRatio);
    }

    /**
     * Given a {@code distance} in timeunits and the current {@code
     * pixelTimeRatio}, compute the distance in pixels.
     * @param distance a distance given in timeunits
     * @return the distance in pixel
     * @see #getPixelTimeRatio()
     */
    public int translateTimestampToPixelDistance(long distance) {
        return (int) (distance * pixelTimeRatio);
    }

    /**
     * Set this components visibleExcerpt to {@code range}.
     * <p>
     * The {@code visibleExcerpt} of this instance defines which excerpt of the
     * timeline is currently displayed by this instance.  {@code
     * range.getStart()} and {@code range.getEnd()} determine the first and the
     * last timestamp that is currently displayed.
     *
     * @param range the range to become the new {@code  visibleExcerpt}.
     */
    public void setVisibleExcerpt(Range range) {
        this.visibleExcerpt = range;
        repaint();
    }

    /**
     * Returns this components {@code visibleExcerpt}.
     *
     * @return this components {@code visibleExcerpt}.
     * @see #setVisibleExcerpt(Range)
     */
    public Range getVisibleExcerpt() {
        return this.visibleExcerpt;
    }

    public void setCurrentTime(long time) {
        this.timeLinePos = translateTimestampToPixel(time);
        repaint();
    }

    public void cancelSelection() {
        this.isSelectionActive = false;
        repaint();
    }

    public void setSelection(Range selectedExcerpt) {
        this.isSelectionActive = true;
        this.selection = selectedExcerpt;
        repaint();
    }

    public Range getSelection() {
        return this.selection;
    }

    public boolean isSelectionActive() {
        return this.isSelectionActive;
    }

    /**
     * Returns the current pixelTimeRatio.
     * @return the current pixelTimeRatio.
     * @see #setPixelTimeRatio(double)
     */
    public double getPixelTimeRatio() {
        return pixelTimeRatio;
    }

    /**
     * This ratio controls how many pixels are painted for every time unit. If
     * it's value is > 1, then more than one pixel is used to represent a single
     * time unit. A value < has the exact opposite effect, more than one units
     * of time are represented by one pixel. The last case is a value equal to
     * one which leads to one time unit being represented by exactly one pixel.
     */
    public void setPixelTimeRatio(double pixelTimeRatio) {
        this.pixelTimeRatio = pixelTimeRatio;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
