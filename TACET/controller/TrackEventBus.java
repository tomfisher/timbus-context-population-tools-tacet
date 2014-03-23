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
