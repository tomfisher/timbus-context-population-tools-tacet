package edu.teco.tacet.ui.project;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

import edu.teco.tacet.ui.project.UniqueContentProvider.Unique;

public class UniqueLabelProvider extends BaseLabelProvider implements ILabelProvider {

    private ILabelProvider delegateProvider;

    public UniqueLabelProvider(ILabelProvider delegateProvider) {
        this.delegateProvider = delegateProvider;
    }

    @Override
    public Image getImage(Object element) {
        if (element instanceof Unique) {
            return delegateProvider.getImage(((Unique) element).value);
        }
        return delegateProvider.getImage(element);
    }

    @Override
    public String getText(Object element) {
        if (element instanceof Unique) {
            String text = delegateProvider.getText(((Unique) element).value);
            return text;
        }
        String text = delegateProvider.getText(element);
        return text;
    }

}
