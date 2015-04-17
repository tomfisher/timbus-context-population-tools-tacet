package edu.teco.tacet.meta.nongen;

import java.util.ArrayList;
import java.util.List;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;

public class ProjectUtil {
    public static void removeDatasource(Project project, long id) {
        for (Datasource source : project.getDatasources()) {
            if (source.getId() == id) {
                project.getDatasources().remove(source);
            }
        }
    }
    
    public static void removeGroup(Project project, long id) {
        for (Group group : project.getGroups()) {
            if (group.getId() == id) {
                project.getDatasources().remove(group);
            }
        }
    }
    
    public static boolean containsDatasource(Project project, long id) {
        for (Datasource ds : project.getDatasources()) {
            if (ds.getId() == id) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsGroup(Project project, long id) {
        for (Group group : project.getGroups()) {
            if (group.getId() == id) {
                return true;
            }
        }
        return false;
    }
    
    public static Iterable<Long> getTimeseriesIds(Project project, TimeseriesType timeseriesType) {
        List<Long> ret = new ArrayList<>();
        for (Datasource ds : project.getDatasources()) {
            for (Timeseries series : ds.getTimeseries()) {
                if (series.getType() == timeseriesType) {
                    ret.add(series.getId());
                }
            }
        }
        return ret;
    }
    
    public static Iterable<Long> getTimeseriesIds(Project project) {
        List<Long> ret = new ArrayList<>();
        for (Datasource ds : project.getDatasources()) {
            for (Timeseries series : ds.getTimeseries()) {
                ret.add(series.getId());
            }
        }
        return ret;
    }

    public static Timeseries getTimeseries(Project project, long id) {
        for (Datasource ds : project.getDatasources()) {
            for (Timeseries series : ds.getTimeseries()) {
            	if (series.getId() == id)
            		return series;
            }
        }
        return null;
    }

    public static boolean containsTimeseries(Project project, long seriesId) {
        for (Datasource source : project.getDatasources()) {
            if (DatasourceUtil.containsTimeseries(source, seriesId)) {
                return true;
            }
        }
        for (Group group : project.getGroups()) {
            if (containsTimeseries(group, seriesId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsTimeseries(Group group, long seriesId) {
        for (Timeseries series : group.getTimeseries()) {
            if (series.getId() == seriesId) {
                return true;
            }
        }
        return false;
    }
}
