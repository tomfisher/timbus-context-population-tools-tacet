package edu.teco.tacet.rdf.ui;

import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import edu.teco.tacet.util.collection.MutableTuple;

public class GroupByFilenameLabelProvider implements ILabelProvider {

    private ILabelProvider wrappedProvider;
    
    public GroupByFilenameLabelProvider(ILabelProvider iLabelProvider) {
        this.wrappedProvider = iLabelProvider;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getText(Object element) {
        if (element instanceof MutableTuple) {
            return ((MutableTuple<String, List<Object>>) element).getFirst();
        }
        return wrappedProvider.getText(element);
    }

    @Override
    public void addListener(ILabelProviderListener listener) {
        wrappedProvider.addListener(listener);        
    }

    @Override
    public void dispose() {
        wrappedProvider.dispose();        
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return wrappedProvider.isLabelProperty(element, property);
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
        wrappedProvider.removeListener(listener);        
    }

    @Override
    public Image getImage(Object element) {
        return wrappedProvider.getImage(element);
    }

}
