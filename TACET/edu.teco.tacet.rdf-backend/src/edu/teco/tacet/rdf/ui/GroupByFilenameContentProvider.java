package edu.teco.tacet.rdf.ui;

import static edu.teco.tacet.util.collection.IterableAdditions.toArray;
import static edu.teco.tacet.util.collection.IterableAdditions.zipWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.util.collection.MutableTuple;

public class GroupByFilenameContentProvider implements ITreeContentProvider {

    private ITreeContentProvider wrappedProvider;

    public GroupByFilenameContentProvider(ITreeContentProvider wrappedProvider) {
        this.wrappedProvider = wrappedProvider;
    }

    @Override
    public void dispose() {
        this.wrappedProvider.dispose();
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, final Object newInput) {
        this.wrappedProvider.inputChanged(viewer, oldInput, newInput);
    }

    private Map<String, List<Object>> groupElements(Object input) {
        // Maps filenames to groups and datasources.
        Map<String, List<Object>> groupedInput = new HashMap<>();
        Object[] elements = wrappedProvider.getElements(input);
        for (Object element : elements) {

            String filename = null;
            if (element instanceof RdfDatasource) {
                filename = ((RdfDatasource) element).getFileName();
            } else if (element instanceof Group) {
                Group group = (Group) element;
                // Get some series' source to figure out the filename. This works, because we
                // expect input to be of the form RdfGroupFilter defines.
                for (Timeseries series : group.getTimeseries()) {
                    // This excludes annotation series from in memory datasources.
                    if (series.getDatasource() instanceof RdfDatasource) {
                        filename = ((RdfDatasource) series.getDatasource()).getFileName();
                        break;
                    }
                }
            }

            if (filename == null) {
                throw new IllegalArgumentException(
                    "Input must adhere to the rules defined by RdfGroupFilter.");
            }

            List<Object> groupedElements = groupedInput.get(filename);
            if (groupedElements == null) {
                groupedElements = new ArrayList<>();
                groupedInput.put(filename, groupedElements);
            }
            groupedElements.add(element);
        }

        return groupedInput;
    }

    @Override
    public Object[] getElements(Object inputElement) {
        // We use the whole tuples (instead of just the keys) as top level elements, so we can
        // change the filename of all groups and datasources belonging to the same file when editing
        // the filenames in the root nodes.
        Map<String, List<Object>> groupedElements = groupElements(inputElement);
        return toArray(zipWith(MutableTuple.<String, List<Object>>mutableTuple(),
                               groupedElements.keySet(), groupedElements.values()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof MutableTuple) {
            return ((MutableTuple<String, List<Object>>) parentElement).getSecond().toArray();
        }
        return wrappedProvider.getChildren(parentElement);
    }

    @Override
    public Object getParent(Object element) {
        return element instanceof MutableTuple ? null : wrappedProvider.getParent(element);
    }

    @Override
    public boolean hasChildren(Object element) {
        return element instanceof MutableTuple ? true : wrappedProvider.hasChildren(element);
    }

}
