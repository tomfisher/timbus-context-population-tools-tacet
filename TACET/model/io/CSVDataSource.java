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
import java.util.Collections;
import java.util.List;

import squirrel.model.AnnotationFactory;
import squirrel.model.io.CSVDataColumn.Encoding;

public class CSVDataSource extends DataSource<CSVDataColumn> {

    private String elemSeparator;
    private String lineSeparator;
    private Encoding encoding;
    private boolean header;
    private int headerLength;
    public CSVDataSource(URL location, Iterable<CSVDataColumn> dataColumns,
            List<String> mediaPaths, AnnotationFactory annotationFactory, String elemSeparator,
            String lineSeparator, Encoding encoding, boolean header, int headerLength) {
        super(location, dataColumns, mediaPaths, annotationFactory);
        this.elemSeparator = elemSeparator;
        this.lineSeparator = lineSeparator;
        this.encoding = encoding;
        this.header = header;
        this.headerLength = headerLength;
    }

    public CSVDataSource(URL location, List<String> mediaPaths,
            AnnotationFactory annotationFactory, String elemSeparator, String lineSeparator,
            Encoding encoding) {
        this(location, Collections.<CSVDataColumn> emptyList(), mediaPaths, annotationFactory,
                elemSeparator, lineSeparator, encoding, false,0);
    }



    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    public CSVDataSource() {
        super();
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public String getElemSeparator() {
        return elemSeparator;
    }

    public void setElemSeparator(String elemSeparator) {
        this.elemSeparator = elemSeparator;
    }

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }
}
