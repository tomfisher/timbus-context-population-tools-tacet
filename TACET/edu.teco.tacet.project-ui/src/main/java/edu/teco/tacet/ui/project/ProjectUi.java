package edu.teco.tacet.ui.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import edu.teco.tacet.meta.CSVDatasource;
import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.track.TrackManager;
import edu.teco.tacet.ui.project.ProjectUi.AbstractMenuItem.Placement;

public class ProjectUi extends Composite {
    
    public static class Builder {
        private ProjectUi projectUi;
        
        public Builder(Composite parent, int style, Project project) {
            this.projectUi = new ProjectUi(parent, style, project);
        }

        public ProjectUi build() {
            projectUi.initGui();
            return projectUi;
        }

        public Builder setViewer(TreeViewer viewer) {
            projectUi.viewer = viewer;
            return this;
        }
        
        public Builder setUseGivenLabelProvider(boolean flag) {
            projectUi.useGivenLabelProvider = flag;
            return this;
        }

        public Builder setUseDefaultMenuItems(boolean useDefaultMenuItems) {
            projectUi.useDefaultMenuItems = useDefaultMenuItems;
            return this;
        }
        
        public Composite getRootComposite() {
            return projectUi;
        }
    } 

    private TreeViewer viewer;
    private Tree tree;
    private boolean useDefaultMenuItems = true;
    private boolean useGivenLabelProvider = false;
    
    private Project currentInput;
    
    private List<AbstractMenuItem> menuItems = new ArrayList<>(10);

    private ProjectUi(Composite parent, int style, Project project) {
        super(parent, style);
        currentInput = project;
    }
    
    private void initGui() {
        this.setLayout(new FillLayout(SWT.HORIZONTAL));
        
        if (viewer == null) {
            tree = new Tree(this, SWT.MULTI);
            viewer = new TreeViewer(tree);
        } else {
            tree = viewer.getTree();
        }

        if (viewer.getContentProvider() == null) {
            viewer.setContentProvider(new TreeContentProvider(currentInput));
        }
        
        if (!useGivenLabelProvider) {
            viewer.setLabelProvider(new TreeLabelProvider());
        }

        tree.setMenu(new Menu(getParent()));
        tree.addMenuDetectListener(new MenuItemInitialiser());

        if (useDefaultMenuItems) {
            addMenuItem(new CreateTrackItem().at(Placement.TOP));
            addMenuItem(new RenameDatasourceItem());
            addMenuItem(new RenameTimeseriesItem());
            addMenuItem(new RenameGroupItem());
            addMenuItem(new RemoveGroupItem());
            addMenuItem(new AddToGroupItem());
            addMenuItem(new RemoveFromGroupItem());
            addMenuItem(new CreateGroupItem());
        }
        
        setProject(currentInput);
    }

    public void addMenuItem(AbstractMenuItem item) {
        int refIdx = menuItems.indexOf(item.reference);
        switch (item.placement) {
        case TOP:
            menuItems.add(0, item);
            break;
        case BOTTOM:
            menuItems.add(menuItems.size(), item);
            break;
        case BEFORE:
            menuItems.add(refIdx, item);
            break;
        case AFTER:
            menuItems.add(refIdx + 1, item);
            break;
        }
        item.setTree(tree);
    }

    public void removeMenuItem(AbstractMenuItem item) {
        menuItems.remove(item);
    }

    /**
     * Returns an unmodifiable list of all the menu items. They are in the same order as they are
     * displayed, e.g. the item at index 0 is displayed at the top.
     * 
     * @return an unmodifiable list of all the menu items.
     */
    public List<AbstractMenuItem> getMenuItems() {
        return Collections.unmodifiableList(menuItems);
    }

    /**
     * This class removes the old MenuDetectListeners and adds those collected in menuItems.
     */
    private class MenuItemInitialiser implements MenuDetectListener {

        List<AbstractMenuItem> oldItems = new ArrayList<>(10);

        @Override
        public void menuDetected(MenuDetectEvent e) {
            // This listener is always the first of all menu listeners, ergo we can create the new
            // Menu here.
            tree.setMenu(new Menu(getParent()));
            for (MenuDetectListener item : oldItems) {
                tree.removeMenuDetectListener(item);
            }
            for (MenuDetectListener item : menuItems) {
                tree.addMenuDetectListener(item);
            }
            oldItems.clear();
            oldItems.addAll(menuItems);
        }
    }

    @Override
    public Point computeSize(int wHint, int hHint, boolean changed) {
        return tree.computeSize(wHint, hHint, changed);
    }

    public static class TreeContentProvider implements ITreeContentProvider {
        
        private Project currentInput;
        private Adapter currentAdapter;
        
        public TreeContentProvider(Project currentInput) {
            this.currentInput = currentInput;
        }

        @Override
        public void inputChanged(final Viewer viewer, Object oldInput, Object newInput) {
            if (newInput != oldInput && newInput != null) {
                if (currentInput != null) {
                    currentInput.eAdapters().remove(currentAdapter);
                }
                this.currentInput = (Project) newInput;
                currentInput.eAdapters().add(currentAdapter = new EContentAdapter() {
                    @Override
                    public void notifyChanged(Notification notification) {
                        super.notifyChanged(notification);
                        if (notification.getEventType() == Notification.SET) {
                            ((TreeViewer) viewer).update(notification.getNotifier(), null);
                        } else {
                            ((TreeViewer) viewer).refresh();
                        }
                    }
                });
            }
        }

        @Override
        public void dispose() {
            currentInput.eAdapters().remove(currentAdapter);
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof Datasource) {
                return !((Datasource) element).getTimeseries().isEmpty();
            } else if (element instanceof Project) {
                return !((Project) element).getDatasources().isEmpty();
            } else if (element instanceof Group) {
                return !((Group) element).getTimeseries().isEmpty();
            }
            return false;
        }

        @Override
        public Object getParent(Object element) {
            if (element instanceof Project) {
                return null;
            } else if (element instanceof Datasource) {
                return currentInput;
            } else if (element instanceof Timeseries) {
                return ((Timeseries) element).getDatasource();
            } else if (element instanceof Group) {
                return ((Group) element).getTimeseries();
            }
            return null;
        }

        @Override
        public Object[] getElements(Object inputElement) {
            List<Object> allElements = new ArrayList<>();
            // We do not check with instanceof because we do not know what
            // to do with an inputElement whose type is not Project. So for
            // now, we let it blow up!
            allElements.addAll(((Project) inputElement).getDatasources());
            allElements.addAll(((Project) inputElement).getGroups());
            return allElements.toArray();
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof Project) {
                return ((Project) parentElement).getDatasources().toArray();
            } else if (parentElement instanceof Datasource) {
                return ((Datasource) parentElement).getTimeseries().toArray();
            } else if (parentElement instanceof Group) {
                return ((Group) parentElement).getTimeseries().toArray();
            }
            return new Object[0];
        }
    }

    public static class TreeLabelProvider extends LabelProvider {
        @Override
        public String getText(Object element) {
            if (element instanceof CSVDatasource) {
                String ret = ((CSVDatasource) element).getFilePath();
                ret = ellideStringLeft(ret, 50);
                return ret;
            } else if (element instanceof Datasource) {
                return ((Datasource) element).getName();
            } else if (element instanceof Timeseries) {
                return ((Timeseries) element).getName();
            } else if (element instanceof Group) {
                return ((Group) element).getName();
            }
            return super.getText(element);
        }

        private String ellideStringLeft(String string, int length) {
            if (string.length() > length) {
                String ellider = "...";
                string = ellider.concat(
                    string.substring(string.length() - (length - ellider.length())));
            }
            return string;
        }
    }

    public static abstract class DefaultSelectionListener implements SelectionListener {
        @Override
        public void widgetDefaultSelected(SelectionEvent e) {}

    }

    public static abstract class AbstractMenuItem implements MenuDetectListener {
        public enum Placement {
            TOP, BOTTOM, BEFORE, AFTER;
        }

        private Placement placement = Placement.BOTTOM;
        private AbstractMenuItem reference;
        private Tree tree;

        @Override
        public void menuDetected(MenuDetectEvent e) {
            TreeItem item = tree.getItem(tree.toControl(e.x, e.y));
            
            if (item == null) {
                // there is nothing sensible to show here
                return;
            }
            Menu ctxMenu = tree.getMenu();
            createItem(ctxMenu, item, tree);
            tree.setMenu(ctxMenu);
        }

        protected abstract MenuItem createItem(Menu menu, TreeItem item, Tree tree);

        public AbstractMenuItem at(Placement placement, AbstractMenuItem reference) {
            this.reference = null;
            switch (placement) {
            case BEFORE:
            case AFTER:
                this.reference = reference;
            case TOP:
            case BOTTOM:
                this.placement = placement;
            }
            return this;
        }

        public AbstractMenuItem at(Placement placement) {
            return at(placement, null);
        }

        private void setTree(Tree tree) {
            this.tree = tree;
        }
    }

    public class CreateTrackItem extends AbstractMenuItem {
        @Override
        protected MenuItem createItem(Menu menu, TreeItem item, Tree tree) {
            if (item.getData() instanceof Datasource) {
                final Datasource source = (Datasource) item.getData();
                if (source.isIsInMemory()) {
                    MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
                    menuItem.setText("Create new Annotation Track ...");
                    menuItem.setData(source);
                    menuItem.addSelectionListener(new DefaultSelectionListener() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            InputDialog inputDialog =
                                new InputDialog(ProjectUi.this.getShell(),
                                    "Creating a new Annotation Track",
                                    "Please enter the Track Name:", null, null);
                            if (inputDialog.open() == InputDialog.CANCEL)
                                return;

                            TrackManager.getInstance()
                                .createAnnotationTrack(inputDialog.getValue());
                        }
                    });
                    return menuItem;
                }
            }
            return null;
        }

    }

    interface Renamable {
        void setName(String newName);

        String getName();
    }

    class RenamableItemSelectionListener extends DefaultSelectionListener {
        @Override
        public void widgetSelected(SelectionEvent e) {
            Renamable renamable = (Renamable) ((MenuItem) e.getSource()).getData();
            InputDialog inputDialog =
                new InputDialog(ProjectUi.this.getShell(), "Rename",
                    "Please enter the new name:", renamable.getName(), null);
            if (inputDialog.open() == InputDialog.CANCEL)
                return;
            if (inputDialog.getValue().length() > 0) {
                renamable.setName(inputDialog.getValue());
            }
            viewer.refresh();
        }
    }

    public class RenameDatasourceItem extends AbstractMenuItem {
        @Override
        protected MenuItem createItem(Menu menu, TreeItem item, Tree tree) {
            if (item.getData() instanceof Datasource) {
                final Datasource source = (Datasource) item.getData();

                MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
                menuItem.setText("Rename...");
                menuItem.setData(new Renamable() {
                    @Override
                    public void setName(String newName) {
                        source.setName(newName);
                    }

                    @Override
                    public String getName() {
                        return source.getName();
                    }
                });
                menuItem.addSelectionListener(new RenamableItemSelectionListener());
                return menuItem;
            }
            return null;
        }
    }

    public class RenameTimeseriesItem extends AbstractMenuItem {
        @Override
        protected MenuItem createItem(Menu menu, TreeItem item, Tree tree) {
            if (item.getData() instanceof Timeseries) {
                final Timeseries series = (Timeseries) item.getData();

                MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
                menuItem.setText("Rename...");
                menuItem.setData(new Renamable() {
                    @Override
                    public void setName(String newName) {
                        series.setName(newName);
                    }

                    @Override
                    public String getName() {
                        return series.getName();
                    }
                });
                menuItem.addSelectionListener(new RenamableItemSelectionListener());
                return menuItem;
            }
            return null;
        }
    }

    public class RenameGroupItem extends AbstractMenuItem {
        @Override
        protected MenuItem createItem(Menu menu, TreeItem item, Tree tree) {
            if (item.getData() instanceof Group) {
                final Group group = (Group) item.getData();

                MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
                menuItem.setText("Rename...");
                menuItem.setData(new Renamable() {
                    @Override
                    public void setName(String newName) {
                        group.setName(newName);
                    }

                    @Override
                    public String getName() {
                        return group.getName();
                    }
                });
                menuItem.addSelectionListener(new RenamableItemSelectionListener());
                return menuItem;
            }
            return null;
        }
    }

    class AddToGroupItem extends AbstractMenuItem {
        private Collection<MenuItem> groupsToAddTo = new ArrayList<>();

        @Override
        protected MenuItem createItem(final Menu menu, TreeItem treeItem, final Tree tree) {
            Object itemData = treeItem.getData();
            if (itemData instanceof Timeseries ||
                itemData instanceof Datasource ||
                itemData instanceof Group) {
                Menu subAddToGroup = new Menu(tree.getShell(), SWT.DROP_DOWN);
                MenuItem item = new MenuItem(menu, SWT.CASCADE);
                item.setMenu(subAddToGroup);
                item.setText("Add to group");
                item.setData(itemData);
                Project project = getProject();

                SelectionListener selListener = new DefaultSelectionListener() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        if (groupsToAddTo.contains((MenuItem) e.getSource())) {
                            Group group = (Group) ((MenuItem) e.getSource()).getData();
                            addToGroup(tree.getSelection(), group);
                        }
                    }
                };

                if (project.getGroups().size() > 0) {
                    groupsToAddTo.clear();
                    for (Group group : project.getGroups()) {
                        MenuItem groupItem = new MenuItem(subAddToGroup, SWT.PUSH);
                        groupsToAddTo.add(groupItem);
                        groupItem.setText(group.getName());
                        groupItem.setData(group);
                        groupItem.addSelectionListener(selListener);
                    }
                }
                MenuItem newGroupItem = new MenuItem(subAddToGroup, SWT.PUSH);
                newGroupItem.setText("New group ...");
                newGroupItem.addSelectionListener(new CreateGroupSelectionListener());

                return item;
            }
            return null;
        }

    }

    private Group addToGroup(TreeItem[] selection, Group group) {
        if (group == null) {
            InputDialog inputDialog =
                new InputDialog(ProjectUi.this.getShell(), "Creating a new Group",
                    "Please enter Group Name:", null, null);
            if (inputDialog.open() != InputDialog.CANCEL) {
                group = MetaFactory.eINSTANCE.createGroup();
                group.setId(TrackManager.getInstance().getSourceIdGenerator().generateSourceId());
                group.setName(inputDialog.getValue());
            } else {
                return null;
            }
        }
        for (TreeItem selItem : selection) {
            Object data = selItem.getData();
            if (data instanceof Timeseries) {
                group.getTimeseries().add((Timeseries) data);
            } else if (data instanceof Datasource) {
                group.getTimeseries().addAll(((Datasource) data).getTimeseries());
            } else if (data instanceof Group) {
                group.getTimeseries().addAll(((Group) data).getTimeseries());
            }
        }
        return group;
    }

    class RemoveFromGroupItem extends AbstractMenuItem {
        @Override
        protected MenuItem createItem(Menu menu, final TreeItem item, final Tree tree) {
            if (item.getData() instanceof Timeseries) {
                if (((Timeseries) item.getData()).getGroups().size() > 0) {
                    MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
                    menuItem.setText("Remove from group");
                    menuItem.addSelectionListener(new DefaultSelectionListener() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            for (TreeItem selItem : tree.getSelection()) {
                                if (selItem.getParentItem().getData() instanceof Group) {
                                    Group group = (Group) selItem.getParentItem().getData();
                                    group.getTimeseries().remove((Timeseries) item.getData());
                                }
                            }
                        }
                    });
                    return menuItem;
                }
            }
            return null;
        }

    }

    class RemoveGroupItem extends AbstractMenuItem {
        @Override
        protected MenuItem createItem(Menu menu, TreeItem item, final Tree tree) {
            if (item.getData() instanceof Group) {
                MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
                menuItem.setText("Remove group");
                menuItem.setData(item.getData());
                menuItem.addSelectionListener(new DefaultSelectionListener() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        for (TreeItem item : tree.getSelection()) {
                            if (item.getData() instanceof Group) {
                                getProject().getGroups().remove((Group) item.getData());
                            }
                        }
                    }
                });
            }
            return null;
        }
    }

    class CreateGroupItem extends AbstractMenuItem {
        @Override
        protected MenuItem createItem(Menu menu, TreeItem item, Tree tree) {
            if (tree.getSelection().length > 0) {
                MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
                menuItem.setText("Create new group ...");
                menuItem.addSelectionListener(new CreateGroupSelectionListener());
                return menuItem;
            }
            return null;
        }

    }

    class CreateGroupSelectionListener extends DefaultSelectionListener {
        public void widgetSelected(SelectionEvent e) {
            TreeItem[] selection = tree.getSelection();
            if (selection.length > 0) {
                Group group = addToGroup(selection, null);
                if (group != null) {
                    getProject().getGroups().add(group);
                }
            }
        }
    }

    public Project getProject() {
        return this.currentInput;
    }

    public void setProject(Project project) {
        this.currentInput = project;
        viewer.setInput(project);
    }

    public TreeViewer getViewer() {
        return viewer;
    }

    public Tree getTree() {
        return tree;
    }
}
