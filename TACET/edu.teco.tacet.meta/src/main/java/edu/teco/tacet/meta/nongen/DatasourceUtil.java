package edu.teco.tacet.meta.nongen;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.Timeseries;

public class DatasourceUtil {

    public static boolean containsTimeseries(Datasource source, long seriesId) {
        for (Timeseries series : source.getTimeseries()) {
            if (series.getId() == seriesId) {
                return true;
            }
        }
        return false;
    }
    
    
    public static Timeseries getTimeseries(Datasource source, long seriesId) {
        for (Timeseries series : source.getTimeseries()) {
            if (series.getId() == seriesId) {
                return series;
            }
        }
        return null;
    }
}
