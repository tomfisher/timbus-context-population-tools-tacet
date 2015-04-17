package edu.teco.tacet.meta.nongen;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.RdfTimeseries;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;

public class FactoryUtil {
    private static MetaFactory factory = MetaFactory.eINSTANCE;
    
    public static <T extends Timeseries> T setTimeseries(
        T series, String name, long id, TimeseriesType type) {
        series.setName(name);
        series.setId(id);
        series.setType(type);
        return series;
    }
    
    public static <T extends RdfTimeseries> T setRdfTimeseries(T series,
        Collection<String> valuePath, Collection<String> timestampPath, Collection<String> identifierPath,
        String identfierValue, Map<Object, Object> mapping) {
        series.getValuePath().addAll(valuePath);
        series.getTimestampPath().addAll(timestampPath);
        series.getIdentifierPath().addAll(identifierPath);
        series.setIdentifierValue(identfierValue);
        series.setMapping(mapping);
        return series;
    }

    @SafeVarargs
    public static <T extends Datasource, U extends Timeseries> T setDatasource(
        T source, String name, long id, boolean isInMemory,
        Map<Object, Object> metadata, U... timeseries) {
        source.setName(name);
        source.setId(id);
        source.setIsInMemory(isInMemory);
        source.setMetadata(metadata);
        source.getTimeseries().addAll(Arrays.asList(timeseries));
        return source;
    }

    public static <T extends Datasource> T setDatasource(T source, String name, long id,
        boolean isInMemory, Timeseries... timeseries) {
        return setDatasource(source, name, id, isInMemory, null, timeseries);
    }

    @SafeVarargs
    public static <T extends Datasource, U extends Timeseries> T setDatasource(
        T source, String name, long id, U... timeseries) {
        return setDatasource(source, name, id, false, null, timeseries);
    }

    public static <T extends Datasource> T setDatasource(T source, String name, long id) {
        return setDatasource(source, name, id, false, null, new Timeseries[] {});
    }

    public static <T extends Datasource> T setDatasource(T source, long id) {
        return setDatasource(source, "no name", id, false, null, new Timeseries[] {});
    }
    
    public static <T extends RdfDatasource> T setRdfDatasource(T source, String resolveUri,
        String fileName, String rootResourceUri, Model model, String timestampFormat) {
        source.setResolveUri(resolveUri);
        source.setFileName(fileName);
        source.setRootResourceUri(rootResourceUri);
        source.setModel(model);
        source.setTimestampFormat(timestampFormat);
        return source;
    }
    
    
    
    public static Group createGroup(long id, String name, Timeseries... timeseries) {
        Group group = factory.createGroup();
        group.setId(id);
        group.setName(name);
        group.getTimeseries().addAll(Arrays.asList(timeseries));
        return group;
    }
}
