package edu.teco.tacet.ui.project;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * An instance of this class uniquifies the output from another {@link ITreeContentProvider}. This
 * is accomplished by wrapping every element in an instance of {@link Unique}. By using the
 * decorator pattern this class is compatible to every implementation of
 * {@link ITreeContentProvider}.
 * <p>
 * 
 * This class is useful for situations in which elements displayed in a tree are not unique and you
 * want to use a {@link CheckboxTreeViewer}. Said tree viewer relies on the uniqueness of its
 * elements in order to function properly. Decorating your content provider in question with an
 * instance of this class before passing it to the tree viewer will allow you and the viewer to
 * distinguish items in the tree by their placement.
 */
public class UniqueContentProvider implements ITreeContentProvider {

    public static class Unique {
        public final Object value;

        private Unique parent;
        private Unique[] children;

        private Unique(Object value, Unique parent, Unique[] children) {
            this.value = value;
            this.parent = parent;
            this.children = children;
        }

        /**
         * The parent of this instance.
         * @return the parent or {@code null} if it has none.
         */
        public Unique getParent() {
            return parent;
        }

        /**
         * The children of this instance.
         * @return the children or {@code null} if it has none.
         */
        public Unique[] getChildren() {
            return children;
        }
    }

    private Unique[] buildTrees(Object input) {
        Unique[] trees = wrapElements(providerDelegate.getElements(input), null);
        for (Unique tree : trees) {
            buildTree(tree);
        }
        return trees;
    }

    private Unique buildTree(Unique parentElement) {
        if (providerDelegate.hasChildren(parentElement.value)) {
            parentElement.children =
                wrapElements(providerDelegate.getChildren(parentElement.value), parentElement);
            for (Unique child : parentElement.children) {
                buildTree(child);
            }
        }
        return parentElement;
    }

    private Unique[] wrapElements(Object[] elements, Unique parent) {
        Unique[] wrappedElements = new Unique[elements.length];
        for (int i = 0; i < elements.length; i++) {
            wrappedElements[i] = new Unique(elements[i], parent, null);
        }
        return wrappedElements;
    }

    private ITreeContentProvider providerDelegate;
    private Unique[] trees;

    public UniqueContentProvider(ITreeContentProvider providerDelegate) {
        this.providerDelegate = providerDelegate;
    }

    @Override
    public void dispose() {
        providerDelegate.dispose();
        trees = null;
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (newInput != null && newInput != oldInput) {
            providerDelegate.inputChanged(viewer, oldInput, newInput);

            // When the input changes we traverse the whole input and build a new tree of Unique
            // instances from it. This makes every item in this tree unique.
            this.trees = buildTrees(newInput);
        }
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return trees;
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        return ((Unique) parentElement).children;
    }

    @Override
    public Object getParent(Object element) {
        return ((Unique) element).parent;
    }

    @Override
    public boolean hasChildren(Object element) {
        return ((Unique) element).children != null;
    }
}
