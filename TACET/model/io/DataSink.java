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

public class DataSink<T extends DataColumn> {

    private DataSource<T> dataSource;
    private Type type;
    private URL location;

    public enum Type {
        CSV, BINARY
    }

    public DataSink(DataSource<T> dataSource, Type type, URL location) {
        super();
        this.dataSource = dataSource;
        this.type = type;
        this.location = location;
    }

    public DataSource<T> getDataSource() {
        return dataSource;
    }

    public Type getType() {
        return type;
    }

    public URL getLocation() {
        return location;
    }



}
