package edu.teco.tacet.meta.nongen;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.teco.tacet.meta.Project;

public class IOManager {
    public void saveProjectDescription(Project project) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();
        String fileName = project.getIdentifier();
        URI fileURI = URI.createFileURI(new File(fileName + ".xml").getAbsolutePath());
        Resource poResource = resourceSet.createResource(fileURI);
        // We might have to add all the other items (Datasouce, ...) too.
        poResource.getContents().add(project);
        poResource.save(null);
    }

    public Project loadProjectDescription(URI fileURI) {
        ResourceSet resourceSet = new ResourceSetImpl();
        Resource poResource = resourceSet.getResource(fileURI, true);
        return (Project) poResource.getContents().get(0);
    }
}
