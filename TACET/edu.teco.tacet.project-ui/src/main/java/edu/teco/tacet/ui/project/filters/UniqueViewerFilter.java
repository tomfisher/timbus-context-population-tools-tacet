package edu.teco.tacet.ui.project.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import edu.teco.tacet.ui.project.UniqueContentProvider.Unique;

public class UniqueViewerFilter extends ViewerFilter {
    
    private ViewerFilter delegate;

    public UniqueViewerFilter(ViewerFilter delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        parentElement = unwrap(parentElement);
        element = unwrap(element);
        return delegate.select(viewer, parentElement, element);
    }
    
    private Object unwrap(Object obj) {
        if (obj instanceof Unique) {
            return ((Unique) obj).value;
        }
        return obj;
    }

}
