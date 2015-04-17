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

package edu.teco.tacet.annotation;

import edu.teco.tacet.timeseriesview.ITrackDisplayer;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Range;



public interface AnnotationModel extends ITrackDisplayer {

    public Iterable<? extends Annotation> getAnnotations(Range range, int trackNo);

    public void deleteAnnotations(Range range, int trackNo);

    public boolean insertAnnotation(Annotation annot, int trackNo);

    public Annotation getAnnotation(long timestamp, int trackNo);

    public boolean canInsertAt(Range range, int trackNo);

    public boolean hasOverlap(Range range, int trackNo);

    public boolean replace(Annotation anno, int trackNo);

	public int getNoTracks();
	
	public String getTrackName(int trackNo);
	
	public String[] getNameSuggestions(int trackNo);
	
	public long getTrackId(int trackNo);
	
	public boolean hasTracksToShow();
	
}
