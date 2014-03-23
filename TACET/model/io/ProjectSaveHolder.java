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

package squirrel.model.io;

import java.io.Serializable;
import java.util.List;

import squirrel.model.Annotation;
import squirrel.model.AnnotationModelImpl;

public class ProjectSaveHolder implements Serializable{
    /**
     * Constant for serialization
     */
    private static final long serialVersionUID = 1L;
    private AnnotationModelImpl model;
    private DataSource dataSource;

    public ProjectSaveHolder(DataSource dataSource, AnnotationModelImpl model) {
        this.model = model;
        this.dataSource = dataSource;
    }

    public DataSource<DataColumn> getDataSource() {
        return dataSource;
    }

    public AnnotationModelImpl getAnnotations() {
        return this.model;
    }
}
