package edu.teco.tacet.ui.project.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import edu.teco.tacet.meta.Datasource;

/**
 * Removes empty Datasources that are in memory and passes everything else.
 */
public class EmptyInMemoryDatasourceFilter extends ViewerFilter {

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (element instanceof Datasource &&
            ((Datasource) element).isIsInMemory() &&
            ((Datasource) element).getTimeseries().isEmpty()) {
            return false;
        }
        return true;
    }

}
