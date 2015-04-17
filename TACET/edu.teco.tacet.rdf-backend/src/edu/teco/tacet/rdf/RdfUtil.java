package edu.teco.tacet.rdf;

import static edu.teco.tacet.util.collection.IterableAdditions.map;
import static edu.teco.tacet.util.collection.IterableAdditions.removeDuplicates;
import static edu.teco.tacet.util.collection.IterableAdditions.toIterable;
import static edu.teco.tacet.util.collection.IterableAdditions.toList;
import static edu.teco.tacet.util.collection.ListAdditions.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.RdfTimeseries;
import edu.teco.tacet.meta.nongen.DatasourceUtil;
import edu.teco.tacet.track.MergeStrategy;
import edu.teco.tacet.track.NoConflictMerge;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.Track;
import edu.teco.tacet.util.function.F1;

public class RdfUtil {
    
    public static RDFNode getSubject(RDFNode node, List<Property> pathToObject) {
        return getSubjectRec(node, pathToObject, 0);
    }

    private static RDFNode getSubjectRec(RDFNode node, List<Property> pathToObject, int currentPos) {
        StmtIterator children = node.asResource().listProperties(pathToObject.get(currentPos));
        if (pathToObject.size() - 1 == currentPos &&
            node.isResource() &&
            children.hasNext()) {
            return node;
        } else {
            for (Statement stmt = null; children.hasNext(); stmt = children.next()) {
                RDFNode ret = getSubjectRec(stmt.getObject(), pathToObject, currentPos + 1);
                if (ret != null) {
                    return ret;
                }
            }
            return null;
        }
    }
    
    public static RDFNode getObject(RDFNode node, List<Property> pathToObject) {
        return getObjectRec(node, pathToObject, 0);
    }

    private static RDFNode getObjectRec(RDFNode node, List<Property> pathToObject, int currentPos) {
        if (pathToObject.size() == currentPos) {
            return node;
        } else {
            StmtIterator children = node.asResource().listProperties(pathToObject.get(currentPos));
            for (Statement stmt : toIterable(children)) {
                RDFNode ret = getObjectRec(stmt.getObject(), pathToObject, currentPos + 1);
                if (ret != null) {
                    return ret;
                }
            }
            return null;
        }
    }
    
    public static <T> List<T> greatestCommonPrefix(List<List<T>> paths) {
        List<T> currentGCP = new ArrayList<>(paths.get(0));
        for (int i = 1; i < paths.size(); i++) {
            List<T> currentPath = paths.get(i);
            for (int j = 0; j < currentGCP.size() && j < currentPath.size(); j++) {
                if (!currentGCP.get(j).equals(currentPath.get(j))) {
                    currentGCP.subList(j, currentGCP.size()).clear();
                    break;
                }
            }
        }
        return currentGCP;
    }

    public static List<RDFNode> getAllSubjects(RDFNode startNode, List<Property> pathToObjects) {
        return getAllSubjectsRec(startNode, pathToObjects, 0);
    }

    private static List<RDFNode> getAllSubjectsRec(
        RDFNode currentNode, List<Property> pathToObjects, int currentPos) {
        List<RDFNode> objects = new ArrayList<>();
        StmtIterator children =
            currentNode.asResource().listProperties(pathToObjects.get(currentPos));
        if (pathToObjects.size() - 1 == currentPos &&
            currentNode.isResource() &&
            children.hasNext()) {
            objects.add(currentNode);
        } else {
            for (Statement stmt : toIterable(children)) {
                objects.addAll(getAllSubjectsRec(stmt.getObject(), pathToObjects, currentPos + 1));
            }
        }
        return objects;
    }

    public static List<RDFNode> getAllObjects(RDFNode startNode, List<Property> pathToObjects) {
        return getAllObjectsRec(startNode, pathToObjects, 0);
    }

    private static List<RDFNode> getAllObjectsRec(
        RDFNode startNode, List<Property> pathToObjects, int currentPos) {
        List<RDFNode> objects = new ArrayList<>();
        StmtIterator children =
            startNode.asResource().listProperties(pathToObjects.get(currentPos));
        if (pathToObjects.size() - 1 == currentPos) {
            for (Statement stmt : toIterable(children)) {
                objects.add(stmt.getObject());
            }
        } else {
            for (Statement stmt : toIterable(children)) {
                objects.addAll(getAllObjectsRec(stmt.getObject(), pathToObjects, currentPos + 1));
            }
        }
        return objects;
    }

    public static <T> Iterable<String> getReadingUris(Iterable<Track<? extends T>> tracks,
        final F1<T, String> toUri, final Range range) {

        F1<Track<? extends T>, Iterable<? extends T>> extractRange =
            new F1<Track<? extends T>, Iterable<? extends T>>() {
                @Override
                public Iterable<? extends T> apply(Track<? extends T> a) {
                    return a.getData(range);
                }
            };

        MergeStrategy<T> merge = new NoConflictMerge<>(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return toUri.apply(o1).compareTo(toUri.apply(o2));
            }
        });

        @SuppressWarnings("unchecked")
        List<String> uris = (List<String>)
            toList(map(toUri, merge.merge(map(extractRange, toList(tracks)))));

        Collections.sort(uris);

        @SuppressWarnings("unchecked")
        Iterable<String> uniqueUris = (Iterable<String>) removeDuplicates(uris);

        return uniqueUris;
    }

    public static RDFNode getValueSubjectFor(String identifierValue, RDFNode commonAncestor, 
        List<Property> valuePath, List<Property> identifierPath) {
        for (RDFNode valueSubject : getAllSubjects(commonAncestor, valuePath)) {
            RDFNode identifier = getObject(valueSubject, identifierPath);
            if ((identifier.isLiteral() &&
                    identifierValue.equals(identifier.asLiteral().getValue()) ||
                (identifier.isResource() &&
                    identifierValue.equals(identifier.asResource().getURI())))) {
                return valueSubject;
            }
        }
        return null;
    }

    public static List<Property> stringPathToPropertyPath(List<String> stringPath,
        final Model model) {
        return map(new F1<String, Property>() {
            @Override
            public Property apply(String pathElem) {
                return model.getProperty(pathElem);
            }
        }, stringPath);
    }

    public static RdfTimeseries getRdfTimeseries(Datasource datasource, long id) {
        return (RdfTimeseries) DatasourceUtil.getTimeseries(datasource, id);
    }
}
