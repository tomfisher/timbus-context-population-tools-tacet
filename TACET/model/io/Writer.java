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
