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

package squirrel.model;

import squirrel.model.io.Reader;
import squirrel.util.Range;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

public interface AnnotationModel  {

    public Iterable<Annotation> getAnnotations(Range range, int track );

    public void deleteAnnotations(Range range, int track);

    public boolean insertAnnotation(Annotation annot, int track);

    public Annotation getAnnotation(long timestamp, int track);

    public boolean canInsertAt(Range range, int track);

    public boolean hasOverlap(Range range, int track);

    public boolean replace(Annotation anno, int track);

    public void setReader(Reader reader);

    public void updateAnnotations(InstancesAdapter classifierInsatnce, InstancesAdapter instancesToClassify, FilteredClassifier filteredClassifier) throws Exception;

}
