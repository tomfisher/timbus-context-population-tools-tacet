package edu.teco.tacet.rdf;

import static edu.teco.tacet.rdf.RdfUtil.getObject;
import static edu.teco.tacet.rdf.RdfUtil.getReadingUris;
import static edu.teco.tacet.rdf.RdfUtil.getValueSubjectFor;
import static edu.teco.tacet.util.collection.CList.cList;
import static edu.teco.tacet.util.collection.IterableAdditions.iterable;
import static edu.teco.tacet.util.collection.IterableAdditions.toIterable;
import static edu.teco.tacet.util.collection.IterableAdditions.toList;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.ontology.Ontology;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Track;
import edu.teco.tacet.util.collection.CList;
import edu.teco.tacet.util.function.F1;
import edu.teco.tacet.writers.Writer;

public class RdfWriter implements Writer {

    private final RdfWriterInfo writerInfo;
    
    private final String rdfTypeUri = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";

    public RdfWriter(RdfWriterInfo writerInfo) {
        this.writerInfo = writerInfo;
    }

    @Override
    public Job createJob(final Iterable<Track<? extends Datum>> sensorTracks,
        final Iterable<Track<? extends Annotation>> annotationTracks) {

        Job job = new Job(writerInfo.getJobName()) {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                
                monitor.beginTask(getName(), 2);
                
                // Create a Model we use to store our data. It has no reasoner attached.
                OntModel targetModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
                OntModel sourceModel = (OntModel) writerInfo.getModel();
                Map<String, String> nsPrefixMap = sourceModel.getNsPrefixMap();
                targetModel.setNsPrefixes(nsPrefixMap);

                // copy ontology + imports into target model
                OntModel baseModel = ModelFactory.createOntologyModel(
                    OntModelSpec.OWL_MEM, sourceModel.getBaseModel());
                
                List<Ontology> onts = toList(toIterable(baseModel.listOntologies()));
                if (onts.size() > 1) {
                    System.out.println("WARNING: OWL Export found more than one ontology - only exporting the first one.");
                }
                Ontology targetOntology = targetModel.createOntology(onts.get(0).getURI());
                
                for (OntResource imp : toIterable(onts.get(0).listImports())) {
                    targetOntology.addImport(imp);
                }
                
                // copy tracks to target model
                for (Track<? extends Datum> track : sensorTracks) {
                    insertSensorTrack(targetModel, track);
                }
                for (Track<? extends Annotation> track : annotationTracks) {
                    insertNewAnnotationTrack(targetModel, track, sensorTracks);
                }
                
                monitor.worked(1);
                
                BufferedOutputStream outStream = null;
                try {
                    outStream = new BufferedOutputStream(
                        new FileOutputStream(writerInfo.getFileName() + ".exported"));
                } catch (IOException e) {
                    e.printStackTrace();
                    return new Status(IStatus.ERROR, "edu.teco.tacet.rdfbackend", "", e);
                }
                
                RDFDataMgr.write(outStream, targetModel, RDFFormat.RDFXML);
      
                monitor.worked(1);
                
                return Status.OK_STATUS;
            }
        };
        
        return job;
    }

    private void insertSensorTrack(Model model, Track<? extends Datum> track) {
        long trackId = track.getId();
        String identifierValue = writerInfo.getIdentifierValue(trackId);
        final Iterable<Property> additionalProperties = iterable(model.getProperty(rdfTypeUri));
        
        // get the root resource
        Model srcModel = writerInfo.getModel();
        Resource srcRoot = srcModel.getResource(writerInfo.getRootResourceUri());

        for (Datum datum : track.getData(track.getCoveredRange())) {
            Resource srcCommonAncestor = srcModel.getResource(datumToUri.apply(datum));
            // find and copy the value subject
            List<Property> valuePath = writerInfo.getValuePath(trackId);
            RDFNode srcValueSubject = getValueSubjectFor(identifierValue, srcCommonAncestor,
                valuePath, writerInfo.getIdentifierPath(trackId));
            copyResource(srcValueSubject.asResource(), model);
            // copy the commonAncestor and everything between it and the value subject 
            copyRDFNode(srcCommonAncestor, cList(valuePath.subList(0, valuePath.size() - 1)),
                srcValueSubject, model, additionalProperties);
            // copy everything between root and commonAncestor
            copyRDFNode(srcRoot, cList(writerInfo.getGreatestCommonPrefix(trackId)),
                srcCommonAncestor, model, additionalProperties);
            // copy the timestamp
            List<Property> timestampPath = writerInfo.getTimestampPath(trackId);
            RDFNode srcTimestampObject = getObject(srcCommonAncestor, timestampPath);
            copyRDFNode(srcCommonAncestor, cList(timestampPath), srcTimestampObject, model,
                additionalProperties);
        }
    }
    
    private RDFNode copyRDFNode(RDFNode start, CList<Property> path, RDFNode end, Model target,
        Iterable<Property> included) {
        if (start.isLiteral()) {
            return start.equals(end) ? start : null;
        }
        // start is a resource
        Resource resource = start.asResource();
        if (path.isEmpty()) {
            return resource.equals(end) ? target.createResource(resource.getURI()) : null;
        }
        if (resource.equals(end)) {
            return target.createResource(resource.getURI());
        }
        
        Iterator<Statement> children;
        if (path.getTail().isEmpty()) { // only 1 element left
            children = resource.getModel().listStatements(resource, path.getHead(), end);
        } else {
            children = resource.listProperties(path.getHead());
        }
        
        // copy additionally included children
        for (Property include : included) {
            for (Statement includedChild : toIterable(resource.listProperties(include))) {
                Resource subject = target.getResource(includedChild.getSubject().getURI());
                RDFNode object = includedChild.getObject();
                if (!object.isAnon()) { // Don't know how to copy anonymous nodes
                    if (object.canAs(Resource.class)) {
                        object = target.getResource(object.asResource().getURI());
                    }
                    target.add(subject, include, object);
                }
            }
        }
        
        // continue copying along the given path
        Resource targetResource = target.createResource(resource.getURI());
        for (Statement child : toIterable(children)) {
            RDFNode ret = copyRDFNode(child.getObject(), path.getTail(), end, target, included);
            if (ret != null) {
                targetResource.addProperty(path.getHead(), ret);
            }
        }
        return targetResource;
    }
    
    private Resource copyResource(Resource srcResource, Model target) {
        StmtIterator children = srcResource.listProperties();
        Resource targetResource = target.getResource(srcResource.getURI());
        for (Statement child : toIterable(children)) {
            RDFNode object = child.getObject();
            if (object.isLiteral()) {
                targetResource.addLiteral(child.getPredicate(), object.asLiteral().getValue());
            } else if (object.isResource()) {
                Resource targetChild = copyResource(object.asResource(), target);
                targetResource.addProperty(child.getPredicate(), targetChild);
            }
        }
        return targetResource;
    }
    
    private static final F1<Datum, String> datumToUri = new F1<Datum, String>() {
        @Override
        public String apply(Datum datum) {
            return ((RdfDatum) datum).uri;
        }
    };

    private void insertNewAnnotationTrack(Model model, Track<? extends Annotation> track,
        Iterable<Track<? extends Datum>> sensorTracks) {

        final String trackName = track.getMetaData().getName();
        final String ns = "http://timbus.teco.edu/ontologies/DSOs/sensors.owl#";
        final Property annotationProperty = model.getProperty(ns + "hasAnnotation");
        final Property nameProperty = model.getProperty(ns + "hasName");
        final Property labelProperty = model.getProperty(ns + "hasDescription");
        final Property descriptionProperty = model.getProperty(ns + "hasLongDescription");

        // for each Annotation a:
        for (Annotation annot : track.getData(track.getCoveredRange())) {
            // get all readings under root in a's range
            Iterable<String> readingUris =
                getReadingUris(sensorTracks, datumToUri, annot.getRange());

            // add a new identifier and value path
            // insert track name as identifier and annotation name as value
            for (String uri : readingUris) {
                Resource annotResource = model.getResource(uri + "/Annotation/" + trackName)
                    .addLiteral(nameProperty, trackName)
                    .addLiteral(labelProperty, annot.getLabel());
                if (annot.getDescription() != null || annot.getDescription().trim().length() > 0) {
                    annotResource.addLiteral(descriptionProperty, annot.getDescription());
                }
                model.getResource(uri).addProperty(annotationProperty, annotResource);
            }
        }
    }

}
