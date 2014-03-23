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

import java.net.URL;
import java.util.List;

import squirrel.model.Annotation;
import squirrel.model.SensorDatum;

/**
 * The writer for the files (CSV/Binary/Database)
 * @author marc
 * @param <T>
 *
 */
public abstract class Writer<T extends DataColumn> {

    protected URL exportPath;
    protected DataSource<T> dataSource;

    /**
     * Constructs the Writer
     * @param exportPath the path where the export file should be saved
     * @param location the location of the original file
     * @param descriptions the descriptions of the columns
     * @param timeConverter the timeConverter to convert the timestamp
     * @param replace the separator for the columns
     * @param isDatum if it is date to parse with simpeldateformat
     */
    public Writer(URL exportPath, DataSource<T> dataSource) {
        this.exportPath = exportPath;
        this.dataSource = dataSource;
    }

    /**
     * Write all annotations and the original file together in one File
     * @param annotations list of the tracks and the tracks with an array ot the
     *            annotations
     */
    public abstract void writeExport(List<Annotation[]> annotations, SensorDatum[] sensrodatum);

    public abstract void initialiseWriter();


    public abstract void finalizeWriter();

}
