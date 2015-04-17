package edu.teco.tacet.rdf.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import net.miginfocom.swt.MigLayout;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.RdfTimeseries;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;
import static edu.teco.tacet.util.ui.UiUtil.*;

class TimeseriesIdentificationPage extends WizardPage {

    private Composite root;
    private Label lblRootResource;
    private Text txtRootResource;
    private Button btnRefresh;

    private Label lblRdfGraph, lblTimeseries;

    private TreeViewer treeViewer;

    private TableViewer tableViewer;
    private Button btnAdd, btnRemove;
    private Label lblValuePath, lblTimestampPath, lblIdentifierPath;
    private List lstValuePath, lstTimestampPath, lstIdentifierPath;

    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_TYPE = "Type";
    private static final String COLUMN_IDENTIFIER_VALUE = "Identifier value";
    private static final String[] COLUMN_PROPERTIES =
    { COLUMN_NAME, COLUMN_TYPE, COLUMN_IDENTIFIER_VALUE };

    private Label lblFormat;
    private Text txtFormat;

    private RdfDatasource datasource;
    private java.util.List<String> rootResources = new ArrayList<>();
    private Resource currentResource;

    private int defaultTsNameCounter = 0;

    protected TimeseriesIdentificationPage(RdfDatasource datasource) {
        super("Timeseries Identification");
        this.datasource = datasource;
        
        fillWithTestData(datasource);
    }

    private void fillWithTestData(RdfDatasource datasource) {
        String ns = "http://timbus.teco.edu/ontologies/DSOs/sensors.owl#";
        RdfTimeseries sensorTrack1 =
            createTimeseries("Relative Resistance", "RelativeResistance", TimeseriesType.SENSOR, 0,
                prefix(ns, new String[][] {
                    new String[] { // identifier path
                    "hasReading", "hasRawReading", "hasQuantity", "hasQuantityType", "hasType"
                    }, new String[] { // timestamp path
                    "hasReading", "hasDate"
                    }, new String[] { // value path
                    "hasReading", "hasRawReading", "hasQuantity", "hasValue"
                    } }));
        datasource.getTimeseries().add(sensorTrack1);

        RdfTimeseries annotationTrack1 = createTimeseries("Behaviour", "Behaviour",
            TimeseriesType.ANNOTATION, 1, prefix(ns, new String[][] {
                new String[] { // identifier path
                "hasReading", "hasAnnotation", "hasName"
                }, new String[] { // timestamp path
                "hasReading", "hasDate"
                }, new String[] { // value path
                "hasReading", "hasAnnotation", "hasDescription"
                }
            }));
        datasource.getTimeseries().add(annotationTrack1);
    }
    
    private String[][] prefix(String prefix, String[]... strings) {
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length; j++) {
                strings[i][j] = prefix + strings[i][j];
            }
        }
        return strings;
    }

    private RdfTimeseries createTimeseries(String name, String idValue, TimeseriesType type,
        long id, String[][] paths) {
        
        RdfTimeseries series = MetaFactory.eINSTANCE.createRdfTimeseries();
        series.setName(name);
        series.setId(id);
        series.setType(type);
        series.setIdentifierValue(idValue);
        series.getIdentifierPath().addAll(java.util.Arrays.asList(paths[0]));
        series.getTimestampPath().addAll(java.util.Arrays.asList(paths[1]));
        series.getValuePath().addAll(java.util.Arrays.asList(paths[2]));

        return series;
    }
    
    public void setRootResources(java.util.List<String> uris) {
        if (txtRootResource != null && uris.size() > 0) {
            txtRootResource.setText(uris.get(0));
        }
        this.rootResources = uris;
    }

    public java.util.List<String> getRootResources() {
        return rootResources;
    }
    
    public String getTimestampFormat() {
        return txtFormat.getText();
    }
    
    @Override
    public boolean canFlipToNextPage() {
        // TODO: remove - this is the last page.
        return super.canFlipToNextPage() && isPageComplete();
    }

    @Override
    public boolean isPageComplete() {
        return txtFormat != null &&
            txtRootResource != null &&
            tableViewer != null &&
            txtFormat.getText().length() > 0 &&
            txtRootResource.getText().length() > 0 &&
            tableViewer.getTable().getItems().length > 0 &&
            isTableCompletelyFilled();
    }

    private boolean isTableCompletelyFilled() {
        if (datasource == null) {
            return false;
        }
        boolean ret = false;
        for (Timeseries series : datasource.getTimeseries()) {
            RdfTimeseries rdfSeries = (RdfTimeseries) series;
            ret = rdfSeries.getName().length() > 0 &&
                rdfSeries.getType() != null &&
                rdfSeries.getTimestampPath().size() > 0 &&
                rdfSeries.getValuePath().size() > 0 &&
                rdfSeries.getIdentifierPath().size() > 0 &&
                rdfSeries.getIdentifierValue().length() > 0;
            if (!ret) {
                break;
            }
        }

        return ret;
    }

    @Override
    public void createControl(Composite parent) {
        setTitle("Identify the timeseries to be imported.");

        root = new Composite(parent, SWT.NONE);
        setControl(root);

        MigLayout migLayout = new MigLayout("wrap 3");
        migLayout.setColumnConstraints("[fill, grow 60]unrel[fill, grow 40][fill]");
        migLayout.setRowConstraints(
            "[nogrid]u[grow 0][fill, grow 25, top][fill, grow 25]"
                + "[fill, grow 25][fill, grow 25][grow 0]");
        root.setLayout(migLayout);

        lblRootResource = setLayoutData(new Label(root, SWT.LEFT), "left, gapright rel");
        lblRootResource.setText("Root resource:");
        txtRootResource = setLayoutData(new Text(root, SWT.SINGLE), "growx");
        if (!rootResources.isEmpty()) {
            txtRootResource.setText(rootResources.get(0));
        }
        btnRefresh = setLayoutData(new Button(root, SWT.CENTER), "right, wrap");
        btnRefresh.setText("Refresh Graph");

        lblRdfGraph = setLayoutData(new Label(root, SWT.LEFT), "left");
        lblRdfGraph.setText("RDF Graph");
        lblTimeseries = setLayoutData(new Label(root, SWT.LEFT), "left, wrap");
        lblTimeseries.setText("Timeseries Specification");

        treeViewer =
            setLayoutData(new TreeViewer(root, SWT.MULTI | SWT.BORDER),
                "left, spany 5, hmin 200, hmax 800");
        tableViewer = setLayoutData(
            new TableViewer(root, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION), "left");

        Composite compButtonBar = new Composite(root, SWT.NONE);
        compButtonBar.setLayout(new MigLayout("flowy, top"));
        btnAdd = setLayoutData(new Button(compButtonBar, SWT.PUSH | SWT.LEFT), "grow");
        btnAdd.setText("Add");
        btnRemove = setLayoutData(new Button(compButtonBar, SWT.PUSH | SWT.LEFT), "grow");
        btnRemove.setText("Remove");

        lblValuePath = setLayoutData(new Label(root, SWT.LEFT | SWT.BOLD),
            "skip 1, span, split 2, flowy, growy 0");
        lblValuePath.setText("Timeseries value path:");
        lstValuePath = setLayoutData(new List(root, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL),
            "wrap");
        lblTimestampPath = setLayoutData(new Label(root, SWT.LEFT | SWT.BOLD),
            "skip 1, span, split 2, flowy, growy 0");
        lblTimestampPath.setText("Timeseries timestamp path:");
        lstTimestampPath = setLayoutData(new List(root, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL),
            "wrap");
        lblIdentifierPath = setLayoutData(new Label(root, SWT.LEFT | SWT.BOLD),
            "skip 1, span, split 2, flowy, growy 0");
        lblIdentifierPath.setText("Timeseries identifier path:");
        lstIdentifierPath = setLayoutData(new List(root, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL),
            "wrap");

        lblFormat = setLayoutData(new Label(root, SWT.RIGHT), "skip 1, span, split 2");
        lblFormat.setText("Timestamp format:");
        txtFormat = setLayoutData(new Text(root, SWT.SINGLE), "growx, wrap");
        txtFormat.setText("yyyy-MM-dd'T'HH:mm:ss");

        treeViewer.setContentProvider(new RdfTreeContentProvider());
        treeViewer.setLabelProvider(new RdfTreeLabelProvider());
        treeViewer.getTree().addMenuDetectListener(new TreeMenuDetectListener(parent));

        tableViewer.setContentProvider(new RdfTableContentProvider());
        tableViewer.setLabelProvider(new RdfTableLabelProvider());
        tableViewer.setColumnProperties(COLUMN_PROPERTIES);
        tableViewer.setCellModifier(new RdfCellModifier());
        tableViewer.getTable().addSelectionListener(new TableSelectionListener());

        Table table = tableViewer.getTable();
        for (int i = 0, n = COLUMN_PROPERTIES.length; i < n; i++) {
            new TableColumn(table, SWT.LEFT).setText(COLUMN_PROPERTIES[i]);
            table.getColumn(i).pack();
        }
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        CellEditor[] editors = new CellEditor[3];
        editors[0] = new TextCellEditor(table);
        editors[2] = new TextCellEditor(table);

        String[] timeseriesTypes = new String[TimeseriesType.values().length];
        for (int i = 0; i < TimeseriesType.values().length; i++) {
            timeseriesTypes[i] = TimeseriesType.values()[i].toString();
        }
        editors[1] = new ComboBoxCellEditor(table, timeseriesTypes, SWT.READ_ONLY);
        tableViewer.setCellEditors(editors);
        tableViewer.setInput(datasource);

        btnRefresh.addSelectionListener(new ButtonSelectListener());

        btnAdd.addSelectionListener(new ButtonSelectListener());

        btnRemove.addSelectionListener(new ButtonSelectListener());

        txtFormat.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                getWizard().getContainer().updateButtons();
            }

            @Override
            public void keyPressed(KeyEvent e) {}
        });

        datasource.eAdapters().add(new EContentAdapter() {
            @Override
            public void notifyChanged(Notification notification) {
                super.notifyChanged(notification);
                // each time he datasource is modified, update the buttons
                getWizard().getContainer().updateButtons();
            }
        });
    }

    private void updateLists(RdfTimeseries series) {
        lstTimestampPath.removeAll();
        lstValuePath.removeAll();
        if (series != null) {
            lstTimestampPath.setItems(series.getTimestampPath().toArray(new String[0]));
            lstValuePath.setItems(series.getValuePath().toArray(new String[0]));
            lstIdentifierPath.setItems(series.getIdentifierPath().toArray(new String[0]));
        }
    }

    enum Type {
        VALUE, TIMESTAMP, IDENTIFIER, NEW_VALUE, NEW_TIMESTAMP, NEW_IDENTIFIER, IDENTIFIER_VALUE,
        NEW_IDENTIFIER_VALUE
    }
    
    class ButtonSelectListener implements SelectionListener {

        @Override
        public void widgetSelected(SelectionEvent e) {
            if (e.getSource() == btnAdd) {
                RdfTimeseries series = MetaFactory.eINSTANCE.createRdfTimeseries();
                datasource.getTimeseries().add(series);
                tableViewer.refresh();
            } else if (e.getSource() == btnRemove) {
                for (TableItem item : tableViewer.getTable().getSelection()) {
                    datasource.getTimeseries().remove((RdfTimeseries) item.getData());
                }
                tableViewer.refresh();
                updateLists(null);
            } else if (e.getSource() == btnRefresh) {
                Resource rootResource = ResourceFactory.createResource(txtRootResource.getText());
                if (datasource.getModel().containsResource(rootResource)) {
                    setErrorMessage(null);
                    currentResource = datasource.getModel().getResource(rootResource.getURI());
                    treeViewer.setInput(datasource.getModel());
                    treeViewer.refresh();
                } else {
                    setErrorMessage("The given root resource does not exist in this RDF graph.");
                }
            }
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {}
        
    }

    class ItemSelectListener implements SelectionListener {
        private Type type;
        private TreeItem treeItem;

        public ItemSelectListener(Type type, TreeItem treeItem) {
            this.type = type;
            this.treeItem = treeItem;
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            RdfTimeseries series = null;
            switch (type) {
            case NEW_VALUE:
            case NEW_TIMESTAMP:
            case NEW_IDENTIFIER:
            case NEW_IDENTIFIER_VALUE:
                series = MetaFactory.eINSTANCE.createRdfTimeseries();
                break;
            case VALUE:
            case TIMESTAMP:
            case IDENTIFIER:
            case IDENTIFIER_VALUE:
                series = (RdfTimeseries) ((MenuItem) e.getSource()).getData();
                break;
            }
            java.util.List<String> path = processPathFor(series);
            switch (type) {
            case VALUE:
            case NEW_VALUE:
                series.getValuePath().clear();
                series.getValuePath().addAll(path);
                break;
            case TIMESTAMP:
            case NEW_TIMESTAMP:
                series.getTimestampPath().clear();
                series.getTimestampPath().addAll(path);
                break;
            case IDENTIFIER:
            case NEW_IDENTIFIER:
                series.getIdentifierPath().clear();
                series.getIdentifierPath().addAll(path);
                break;
            case IDENTIFIER_VALUE:
            case NEW_IDENTIFIER_VALUE:
                series.setIdentifierValue(treeItem.getData().toString());
                break;
            }

            datasource.getTimeseries().add(series);
            tableViewer.refresh();
            treeViewer.refresh();
            updateLists(series);
            root.layout();
        }

        private java.util.List<String> processPathFor(RdfTimeseries series) {
            java.util.List<String> path = pathToString(getPathFor(treeItem));
            if (series.getName() == null || series.getName().length() == 0) {
                series.setName(createDefaultName());
            }
            return path;
        }

        private String createDefaultName() {
            return "Timeseries " + ++defaultTsNameCounter;
        }

        private java.util.List<String> pathToString(java.util.List<Object> path) {
            ArrayList<String> ret = new ArrayList<>();
            for (Object element : path) {
                ret.add(element.toString());
            }
            return ret;
        }

        private java.util.List<Object> getPathFor(TreeItem treeItem) {
            java.util.List<Object> path = new ArrayList<>();
            // collect all items on path
            do {
                Object data = treeItem.getData();
                // only add the properties, not the resources
                if (data instanceof Statement) {
                    path.add(((Statement) data).getPredicate());
                }
            } while ((treeItem = treeItem.getParentItem()) != null);
            // collected path elements are in the wrong order - reverse them
            Collections.reverse(path);
            return path;
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {}

    }

    class TreeMenuDetectListener implements MenuDetectListener {

        private Composite parent;

        public TreeMenuDetectListener(Composite parent) {
            this.parent = parent;
        }

        @Override
        public void menuDetected(MenuDetectEvent e) {
            Tree tree = treeViewer.getTree();
            Point eventLoc = tree.toControl(e.x, e.y);
            TreeItem treeItem = tree.getItem(eventLoc);
            if (treeItem == null) {
                return;
            }

            Menu ctxMenu = new Menu(parent);

            addMenuItemPath(ctxMenu, "use as timestamp for", "New Timeseries...", Type.TIMESTAMP,
                Type.NEW_TIMESTAMP, treeItem);
            addMenuItemPath(ctxMenu, "use as value for", "New Timeseries...", Type.VALUE,
                Type.NEW_VALUE, treeItem);
            addMenuItemPath(ctxMenu, "use as identifier for", "New Timeseries...", Type.IDENTIFIER,
                Type.NEW_IDENTIFIER, treeItem);
            addMenuItemPath(ctxMenu, "use as identifier value for", "New Timeseries...",
                Type.IDENTIFIER_VALUE, Type.NEW_IDENTIFIER_VALUE, treeItem);

            tree.setMenu(ctxMenu);
        }

        private void addMenuItemPath(Menu menu, String useAsText, String newTsText, Type type,
            Type newType, TreeItem treeItem) {
            Menu subMenu = new Menu(getShell(), SWT.DROP_DOWN);
            createMenuItem(menu, useAsText, SWT.CASCADE).setMenu(subMenu);
            for (Timeseries series : datasource.getTimeseries()) {
                MenuItem menuItem = createMenuItem(subMenu, series.getName(), SWT.PUSH);
                menuItem.addSelectionListener(new ItemSelectListener(type, treeItem));
                menuItem.setData(series);
            }
            createMenuItem(subMenu, newTsText, SWT.PUSH)
                .addSelectionListener(new ItemSelectListener(newType, treeItem));
        }

        private MenuItem createMenuItem(Menu menu, String text, int style) {
            MenuItem item = new MenuItem(menu, style);
            item.setText(text);
            return item;
        }
    }

    class RdfTreeContentProvider implements ITreeContentProvider {

        Model currentModel = null;

        @Override
        public void dispose() {}

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            if (newInput != oldInput && newInput != null) {
                currentModel = (Model) newInput;
            }
        }

        @Override
        public Object[] getElements(Object inputElement) {
            return stmtIterToStatementArray(currentResource.listProperties());
        }

        private Object[] stmtIterToStatementArray(StmtIterator iter) {
            // keep only one statement per predicate
            Set<Statement> elements = new TreeSet<>(new Comparator<Statement>() {
                @Override
                public int compare(Statement o1, Statement o2) {
                    return o1.getPredicate().getURI()
                        .compareTo(o2.getPredicate().getURI());
                }
            });
            while (iter.hasNext()) {
                elements.add(iter.next());
            }
            return elements.toArray();
        }

        private Object[] stmtIterToObjectArray(StmtIterator iter) {
            java.util.List<RDFNode> elements = new ArrayList<>();
            while (iter.hasNext()) {
                elements.add(iter.next().getObject());
            }
            return elements.toArray();
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof Statement) {
                Statement parent = (Statement) parentElement;
                StmtIterator iter = currentModel.listStatements(parent.getSubject(),
                    parent.getPredicate(), (RDFNode) null);
                return stmtIterToObjectArray(iter);
            } else if (parentElement instanceof RDFNode) {
                if (parentElement instanceof Resource) {
                    StmtIterator stmtIter = ((Resource) parentElement).listProperties();
                    return stmtIterToStatementArray(stmtIter);
                }
            }
            return null;
        }

        @Override
        public Object getParent(Object element) {
            if (element instanceof Statement) {
                Resource subject = ((Statement) element).getSubject();
                return subject;
            } else if (element instanceof RDFNode) {
                if (element instanceof Resource) {
                    Resource resource = (Resource) element;
                    // the root resource has no parent
                    if (resource.getURI().equals(rootResources.get(0))) {
                        return null;
                    }
                }
                // all statements with element as an object
                StmtIterator stmtsAsObject =
                    currentModel.listStatements(null, null, (RDFNode) element);
                // If there is more than one statement with element as an object, element's parent
                // is ambiguous and we return null
                if (stmtsAsObject.hasNext()) {
                    Statement ret = stmtsAsObject.next();
                    return stmtsAsObject.hasNext() ? null : ret;
                }
            }

            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof Statement) {
                return true;
            } else if (element instanceof Resource) {
                return ((Resource) element).listProperties().hasNext();
            } else if (element instanceof Literal) {
                return false;
            }
            return false;
        }

    }

    static class RdfTreeLabelProvider extends LabelProvider {

        @Override
        public String getText(Object element) {
            String label = "";
            if (element instanceof Statement) {
                label = ((Statement) element).getPredicate().toString();
            } else if (element instanceof Resource) {
                label = ((Resource) element).toString();
            } else if (element instanceof Literal) {
                label = ((Literal) element).getLexicalForm();
            }
            return label;
        }
    }

    class TableSelectionListener implements SelectionListener {

        @Override
        public void widgetSelected(SelectionEvent e) {
            RdfTimeseries series = (RdfTimeseries) ((TableItem) e.item).getData();
            updateLists(series);
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {}

    }

    static class RdfTableContentProvider implements IStructuredContentProvider {

        @Override
        public void dispose() {}

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

        @Override
        public Object[] getElements(Object inputElement) {
            return ((RdfDatasource) inputElement).getTimeseries().toArray(
                new RdfTimeseries[] {});
        }

    }

    static class RdfTableLabelProvider implements ITableLabelProvider {

        @Override
        public void addListener(ILabelProviderListener listener) {}

        @Override
        public void dispose() {}

        @Override
        public boolean isLabelProperty(Object element, String property) {
            return true;
        }

        @Override
        public void removeListener(ILabelProviderListener listener) {}

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {
            RdfTimeseries series = (RdfTimeseries) element;
            switch (columnIndex) {
            case 0:
                return series.getName();
            case 1:
                return series.getType().getName();
            case 2:
                return series.getIdentifierValue();
            }
            return "";
        }
    }

    class RdfCellModifier implements ICellModifier {

        @Override
        public boolean canModify(Object element, String property) {
            switch (property) {
            case COLUMN_NAME:
            case COLUMN_TYPE:
            case COLUMN_IDENTIFIER_VALUE:
                return true;
            }
            return false;
        }

        @Override
        public Object getValue(Object element, String property) {
            RdfTimeseries series = (RdfTimeseries) element;
            switch (property) {
            case COLUMN_NAME:
                return series.getName();
            case COLUMN_TYPE:
                return series.getType().ordinal();
            case COLUMN_IDENTIFIER_VALUE:
                return series.getIdentifierValue();
            default:
                return null;
            }
        }

        @Override
        public void modify(Object element, String property, Object value) {
            if (element instanceof Item) {
                element = ((Item) element).getData();
            }
            RdfTimeseries series = (RdfTimeseries) element;
            switch (property) {
            case COLUMN_NAME:
                series.setName((String) value);
                break;
            case COLUMN_TYPE:
                series.setType(TimeseriesType.values()[(Integer) value]);
                break;
            case COLUMN_IDENTIFIER_VALUE:
                series.setIdentifierValue((String) value);
                break;
            }
            tableViewer.refresh();
        }
    }
}
