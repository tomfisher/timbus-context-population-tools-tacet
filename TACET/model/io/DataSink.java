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
