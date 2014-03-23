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
