package edu.teco.tacet.rdf;

import static edu.teco.tacet.rdf.RdfUtil.getRdfTimeseries;
import static edu.teco.tacet.rdf.RdfUtil.stringPathToPropertyPath;
import static edu.teco.tacet.rdf.RdfUtil.greatestCommonPrefix;
import static edu.teco.tacet.util.collection.ListAdditions.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.RdfTimeseries;

public class RdfWriterInfo {
    
    private class TrackInfo {
        List<Property> valuePath;
        List<Property> identifierPath;
        List<Property> greatestCommonPrefix;
        List<Property> timestampPath;
        RDFNode commonAncestor;
    }
    
    private Model model;
    private RdfDatasource datasource;
    private Map<Long, TrackInfo> trackInfos = new HashMap<>();
    
    public RdfWriterInfo(RdfDatasource datasource) {
        this.datasource = datasource;
        this.model = datasource.getModel();
    }

    public String getIdentifierValue(long trackId) {
        return getRdfTimeseries(datasource, trackId).getIdentifierValue();
    }

    public String getRootResourceUri() {
        return datasource.getRootResourceUri();
    }

    public List<Property> getValuePath(long id) {
        return getTrackInfo(id).valuePath;
    }
    
    public RDFNode getCommonAncestor(long id) {
        return getTrackInfo(id).commonAncestor;
    }

    public List<Property> getIdentifierPath(long trackId) {
        return getTrackInfo(trackId).identifierPath;
    }

    public List<Property> getGreatestCommonPrefix(long id) {
        return getTrackInfo(id).greatestCommonPrefix;
    }

    public Model getModel() {
        return model;
    }

    public String getFileName() {
        return datasource.getFileName();
    }

    public String getJobName() {
        return "RDF Export";
    }

    private TrackInfo getTrackInfo(long id) {
        TrackInfo trackInfo = trackInfos.get(id);
        if (trackInfo == null) {
            trackInfo = createTrackInfo(id);
        }
        return trackInfo;
    }

    private TrackInfo createTrackInfo(long id) {
        TrackInfo info = new TrackInfo();
        RdfTimeseries rdfTimeseries = getRdfTimeseries(datasource, id);
        info.valuePath = stringPathToPropertyPath(rdfTimeseries.getValuePath(), model);
        info.identifierPath = stringPathToPropertyPath(rdfTimeseries.getIdentifierPath(), model);
        info.timestampPath = stringPathToPropertyPath(rdfTimeseries.getTimestampPath(), model);
        List<List<Property>> paths = list(info.valuePath, info.identifierPath, info.timestampPath);
        info.greatestCommonPrefix = greatestCommonPrefix(paths);
        
        // remove gcp from the paths
        for (List<Property> path : paths) {
            path.subList(0, info.greatestCommonPrefix.size()).clear();
        }
        // identifierPath is sibling or child of valuePath
        List<Property> gcp =
            greatestCommonPrefix(Arrays.asList(info.valuePath, info.identifierPath));
        info.identifierPath.subList(0, gcp.size()).clear();
        
        return info;
    }

    public List<Property> getTimestampPath(long id) {
        return getTrackInfo(id).timestampPath;
    }

}
