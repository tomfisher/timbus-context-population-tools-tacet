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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import squirrel.model.Annotation;
import squirrel.model.FloatingpointAnnotation;
import squirrel.model.IntegerAnnotation;
import squirrel.model.LabelAnnotation;
import squirrel.model.ModelFacade;
import squirrel.model.TrainAnnotation;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.util.Range;

/**
 * An instance of this class represents a single track of annotations.
 */
@SuppressWarnings("serial")
public class AnnotationTrackView extends TrackView {

    /**
     * The height of the room between the annotation and this component's
     * border.
     */
    private int insets = 2;
    /** The shape of the round corners. */
    private int arcWidth = 7;
    private int arcHeight = 7;

    /**
     * The color of this component's background. Background is anything that is
     * not covered by an annotation.
     */
    private Color clrBackground = super.getBackground();
    /** The color of an annotaition's background. */
    private Color clrAnnotationBackground = Color.LIGHT_GRAY;
    /** The color of an annotation's foreground. */
    private Color clrAnnotationForeground = Color.BLACK;
    /** The color used for the line surrounding an annotation. */
    private Color clrContour = Color.BLACK;

    private int annotationIndex;

    /**
     * Constructs a new instance of this class that displays the annotations
     * from {@code model} that fall into the range between {@code rangeStart}
     * and {@code rangeEnd}.
     * @param model the instance that is used to obtain annotations
     * @param index the track's index this instance draws
     * @param startRange the range to be initially displayed by this instance.
     */
    public AnnotationTrackView(ModelFacade model, int index, Range startRange) {
        super(model, index, startRange);
        DataSource<? extends DataColumn> source = model.getCurrentDataSource();
        this.name = source.getDataColumn(index).getName();
        super.setBackground(clrBackground);
        setFocusable(true);
        this.annotationIndex = source.toAnnotationIndex(index);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Use the extended capabilities of the Graphics2D instance
        Graphics2D g2D = (Graphics2D) g;
        // Make use of antialiasing to smoothen the curves
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int height = super.getHeight() - 2 * insets;
        int vs = translateTimestampToPixel(visibleExcerpt.getStart());
        int ve = translateTimestampToPixel(visibleExcerpt.getEnd());
        for (Annotation annotation : model.getAnnotations(visibleExcerpt, annotationIndex)) {
            int as = translateTimestampToPixel(annotation.getStart());
            int ae = translateTimestampToPixel(annotation.getEnd());
            int de = ve - ae;
            int ds = vs - as;
            int x = as - vs;
            if (x < 0) x = 0;
            int y = insets;
            int visibleWidth = computeVisibleWidth(annotation, ds, de);

            // the middle part of the annotation
            g2D.setColor(clrAnnotationBackground);
            g2D.fillRect(x + arcWidth, y + 1, visibleWidth - 2 * arcWidth, height);
            g2D.fillRect(x, y + arcHeight, arcWidth, height - 2 * arcHeight);
            g2D.fillRect(x + visibleWidth - arcWidth, y + arcHeight, arcWidth, height - 2 * arcHeight);
            g2D.setColor(clrContour);
            g2D.drawLine(x + arcWidth, y, x + visibleWidth - arcWidth, y);
            g2D.drawLine(x + arcWidth, y + height, x + visibleWidth - arcWidth, y + height);
            if (de > 0) { // round on the right
                g2D.setColor(clrAnnotationBackground);
                g2D.fillArc(x + visibleWidth - 2 * arcWidth, y, 2 * arcWidth, 2 * arcHeight, 0, 90);
                g2D.fillArc(x + visibleWidth - 2 * arcWidth, y + height - 2 * arcHeight, 2 * arcWidth, 2 * arcHeight, 0, -90);
                g2D.setColor(clrContour);
                g2D.drawArc(x + visibleWidth - 2 * arcWidth, y, 2 * arcWidth, 2 * arcHeight, 0, 90);
                g2D.drawArc(x + visibleWidth - 2 * arcWidth, y + height - 2 * arcHeight, 2 * arcWidth, 2 * arcHeight, 0, -90);
                g2D.drawLine(x + visibleWidth, y + arcHeight, x + visibleWidth, y + height - arcHeight);
            }
            if (ds < 0) { // round on the left
                g2D.setColor(clrAnnotationBackground);
                g2D.fillArc(x, y, 2 * arcWidth, 2 * arcHeight, 90, 90);
                g2D.fillArc(x, y + height - 2 * arcHeight, 2 * arcWidth, 2 * arcHeight, -90, -90);
                g2D.setColor(clrContour);
                g2D.drawArc(x, y, 2 * arcWidth, 2 * arcHeight, 90, 90);
                g2D.drawArc(x, y + height - 2 * arcHeight, 2 * arcWidth, 2 * arcHeight, -90, -90);
                g2D.drawLine(x, y + arcHeight, x, y + height - arcHeight);
            }
            if (de < 0) { // cut-off on the right
                g2D.setColor(clrContour);
                g2D.drawLine(x + visibleWidth - arcWidth, y, x + visibleWidth, y);
                g2D.drawLine(x + visibleWidth - arcWidth, y + height, x + visibleWidth, y + height);
                g2D.setColor(clrAnnotationBackground);
                g2D.fillRect(x + visibleWidth - arcWidth, y + 1, arcWidth + 1, height - 1);
            }
            if (ds > 0) { // cut-off on the left
                g2D.setColor(clrContour);
                g2D.drawLine(x, y, x + arcWidth, y);
                g2D.drawLine(x, y + height, x + arcHeight, y + height);
                g2D.setColor(clrAnnotationBackground);
                g2D.fillRect(x, y + 1, arcWidth, height - 1);
            }
            g2D.setColor(clrAnnotationForeground);
            // Center the text to be drawn into the current rectangle.
            drawCenteredString(g2D, makePrintable(annotation), height, x, visibleWidth);
        }
    }

    /**
     * Calculates the visible width of the @code annotation
     *
     * @param annotation - the annotation to calculate the width of
     * @param diffStart
     * @param diffEnd
     * @return
     */
    private int computeVisibleWidth(Annotation annotation, int diffStart, int diffEnd) {
        int visibleWidth = 0;
        if (diffStart < 0 && diffEnd > 0)
            visibleWidth = translateTimestampToPixel(annotation.getEnd()) -
                translateTimestampToPixel(annotation.getStart());
        else if (diffStart > 0 && diffEnd > 0)
            visibleWidth = translateTimestampToPixel(annotation.getEnd()) -
                translateTimestampToPixel(visibleExcerpt.getStart());
        else if (diffStart < 0 && diffEnd < 0)
            visibleWidth = translateTimestampToPixel(visibleExcerpt.getEnd()) -
                translateTimestampToPixel(annotation.getStart());
        else if (diffStart > 0 && diffEnd < 0)
            visibleWidth = translateTimestampToPixel(visibleExcerpt.getEnd()) -
                translateTimestampToPixel(visibleExcerpt.getStart());
        return visibleWidth;
    }

    /**
     * Draws the centred label of the annotation
     * @param g2D
     * @param label - Name of the annotation
     * @param height - height of an annotation track
     * @param start - x coordinate where the annotation starts
     * @param visibleWidth - width of the visible part of the anntotation
     */
    private void drawCenteredString(Graphics2D g2D, String label, int height, int start,
            int visibleWidth) {
        FontMetrics fm = g2D.getFontMetrics();
        int vStringInsets = insets + ((height - fm.getDescent()) / 2);
        int hStringInsets = (visibleWidth - fm.stringWidth(label)) / 2;
        g2D.drawString(label, start + hStringInsets, fm.getAscent() + vStringInsets - 5);
    }

    /**
     * Analyses the annotation an returns a lable depending of which type of annotation it is
     * @param annot - an annotation
     * @return label/name of the annotation which will be painted on the annotation box
     */
    private String makePrintable(Annotation annot) {
        if (annot instanceof IntegerAnnotation) {
            return String.valueOf(((IntegerAnnotation) annot).getValue());
        } else if (annot instanceof FloatingpointAnnotation) {
            return String.valueOf(((FloatingpointAnnotation) annot).getValue());
        } else if (annot instanceof LabelAnnotation) {
            return ((LabelAnnotation) annot).getLabel();
        } else if (annot instanceof TrainAnnotation) {
            return (annot.toValue());
        }
        return "-";
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%d:[%d-%d]", index, visibleExcerpt.getStart(),
                visibleExcerpt.getEnd());
    }

    @Override
    public long translatePixelToTimestamp(int x) {
        return (long) ((double) visibleExcerpt.getDistance() / this.getWidth() * x) + visibleExcerpt.getStart();
    }

    @Override
    public int translateTimestampToPixel(long timestamp) {
        return (int) ((double) getWidth() / visibleExcerpt.getDistance() * (timestamp - visibleExcerpt.getStart()));
    }
}
