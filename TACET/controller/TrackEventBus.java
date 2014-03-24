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

package squirrel.controller;

import squirrel.util.Range;

/**
 * This class models some kind of EventBus. It keeps several
 * {@link TrackController} synchronized by dispatching an event called
 * on one of them to all the others.
 * <p>
 * It is possible to subscribe track controllers to groups, so certain events
 * can be published to a selected group of subscribers.
 */
public interface TrackEventBus {

    /**
     * Subscribe a {@link TrackController} to a given {@code group}.  This
     * {@code subscriber} will from then on be notified whenever someone
     * publishes an event to {@code group}.
     *
     * @param subscriber the instance to be subscribed.
     * @param group the group said instance is subscribed to.
     */
    void subscribe(TrackController subscriber, int group);

    /**
     * Publish a VisibleExcerptMoved event to group {@code group} with delta
     * {@code delta}.
     *
     * @param publisher the instance publishing this event.
     * @param group the group to publish to
     * @param delta the delta the excerpt was moved by
     */
    void publishVisibleExcerptMoved(TrackController trackController, int group, int delta);

    /**
     * Publish a ExcerptSelected event to group {@code group} with selected
     * excerpt {@code selection}.
     *
     * @param publisher the instance publishing this event.
     * @param group the group to publish to
     * @param selection the selected excerpt
     */
    void publishExcerptSelected(TrackController publisher, int group, Range selection);

    /**
     * Publish a SelectionCanceled event to group {@code group}.
     *
     * @param publisher the instance publishing this event.
     * @param group the group to publish to
     */
    void publishSelectionCanceled(TrackController publisher, int group);

    /**
     * Publish a ZoomStepChanged event to a group {@code group}.
     *
     * @param publisher the instance publishing this event.
     * @param group the group to publish to.
     * @param newPixelTimeRatio the new pixelTimeRatio.
     */
    void publishZoomStepChanged(TrackController publisher, int group, double newPixelTimeRatio);

}
