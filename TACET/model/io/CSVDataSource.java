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
