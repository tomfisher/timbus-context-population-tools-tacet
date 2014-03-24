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
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;

import net.miginfocom.swing.MigLayout;

public class GroupView<T extends JComponent> extends JTabbedPane {
    private List<List<T>> elements = new ArrayList<List<T>>(5);

    private int pxComponentHeight = 40;
    private int groupSize;

    public GroupView () {
        this(5);
    }

    public GroupView(int groupSize) {
        super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        this.setBackground(Color.LIGHT_GRAY);
        this.groupSize = groupSize;
    }

    private void layoutElements(int groupNo) {
        JPanel panel = (JPanel) ((JScrollPane)getComponentAt(groupNo)).getViewport().getView();
        for (JComponent elem: elements.get(groupNo)) {
            panel.add(elem, "height " + pxComponentHeight + ", pushx, growx, wrap");
        }
    }

    public Iterable<T> getElements(int groupNo) {
        return elements.get(groupNo);
    }

    public void addElement(int groupNo,  T element) {
        if (elements.size() <= groupNo) {
            elements.add(new ArrayList<T>(5));
            MigLayout layout = new MigLayout("insets 5px");
            JScrollPane scrollPane =
                new JScrollPane(new SpecialScrollable(layout),
                                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            addTab(String.valueOf(groupNo), null, scrollPane, null);
        }
        elements.get(groupNo).add(element);
        layoutElements(groupNo);
    }

    /**
     * Add {@code element} to the last group, or create a new group, if the last
     * one is already full.
     *
     * @param element the element to be added.
     * @see #setGroupSize(int)
     */
    public void addElement(T element) {
        int lastGroupIdx = elements.size();
        if (lastGroupIdx == 0) { // the very first element
            addElement(0, element);
        } else if (elements.get(lastGroupIdx - 1).size() < groupSize) { // last group not full yet
            addElement(lastGroupIdx - 1, element);
        } else { // last group is full, create a new one
            addElement(lastGroupIdx, element);
        }
    }

    public void deleteElement(int groupNo, T element) {
        elements.get(groupNo).remove(element);
        if (elements.get(groupNo).size() == 0)
            elements.remove(groupNo);
        layoutElements(groupNo);
    }

    /**
     * Return the current groupSize.
     *
     * @return the current groupSize
     * @see #setGroupSize(int)
     */
    public int getGroupSize() {
        return groupSize;
    }

    /**
     * The {@code groupSize} determines how many elements can be added to the
     * same group, when addding elements by calling {@code addElement(T)}.
     *
     * @param groupSize the groupSize to set
     */
    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    /**
     * This is a Helperclass to make the elements display well in the {@code
     * JScrollPane}. {@code getScrollableTracksViewportWidth()} has to return
     * {@code true} in order to be fitted into the {@code JScrollPane}'s visible
     * portion. If we return {@code false} the elements are given endless room
     * to the right, which is something we do not want.  We want the elements'
     * width to conform to the {@code GroupView}'s width.
     */
    private class SpecialScrollable extends JPanel implements Scrollable {
        public SpecialScrollable(LayoutManager layout) {
            super(layout);
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return new Dimension(600, 600);
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 5;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 100;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }

    }

    public int getElementHeight() {
        return pxComponentHeight;
    }

    public void setElementHeight(int elementHeight) {
        this.pxComponentHeight = elementHeight;
    }
}
