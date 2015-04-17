package edu.teco.tacet.ui.project.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import edu.teco.tacet.meta.Datasource;

/**
 * Filters tree items of type Datasource. If the mode is KEEP matching items make it into the tree,
 * if it is REMOVE they are left out. Items that are not a subtype of Datasource stay in the tree.
 *
 * @param <T> the exact type to be filtered.
 */
public class DatasourceTypeViewerFilter<T extends Datasource> extends ViewerFilter {
    private Class<T> type;
    private Mode mode;

    public DatasourceTypeViewerFilter(Class<T> type, Mode mode) {
        this.type = type;
        this.mode = mode;
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (!(element instanceof Datasource) ||
            (element instanceof Datasource && ((Datasource) element).isIsInMemory())) {
            return true;
        }
    
        boolean matches =
            (parentElement instanceof Datasource && type.isInstance(parentElement)) ||
                (element instanceof Datasource && type.isInstance(element));
    
        return mode == Mode.KEEP ? matches : !matches;
    }

}
