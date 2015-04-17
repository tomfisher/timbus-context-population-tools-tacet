package edu.teco.tacet.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import edu.teco.tacet.util.TableEntry.ColumnType;

public abstract class TablePage extends WizardPage {

    public static final String NAME = "Name";
    public static final String TYPE = "Type";
    public static final String[] PROPS = { NAME, TYPE };

    private Composite containerTable;
    private Composite containerGrid;
    private TableViewer tv;
    private Composite containerAll;
    protected Button addButton;
    protected Button removeButton;
    private String[] headerLine;
    private ColumnType[] type2;
    private boolean isCreatControl = false;
    // The data model
    protected ArrayList<TableEntry> tableEntries;
    protected int size;

    public TablePage(String pageName) {
        super(pageName);
        tableEntries = new ArrayList<>();
        setPageComplete(false);
    }

    @Override
    public void createControl(Composite parent) {
        GridLayout grid = initLayouts(parent);
        initTable();

        grid.numColumns = 2;
        addButton = new Button(containerGrid, SWT.PUSH);
        addButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                TableEntry entry = new TableEntry(ColumnType.SENSOR, "");
                tableEntries.add(entry);
                tv.refresh();
                checkPageComplete();
            }
        });

        addButton.setText("Add");
        removeButton = new Button(containerGrid, SWT.PUSH);
        removeButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                int index = tv.getTable().getSelectionIndex();
                tableEntries.remove(index);
                tv.refresh();
                checkPageComplete();
            }
        });
        removeButton.setText("Remove");
        setPageComplete(false);
        setControl(containerAll);
        isCreatControl = true;
        setTable();
        changePageForSpecifiedImport();

    }

    protected void changePageForSpecifiedImport() {

    }

    protected void checkPageComplete() {
        validTable();
    }

    public void validTable() {
        if (tableEntries.size() != this.size) {
            setMessage(size + " entries expected",
                DialogPage.WARNING);
        } else {
            setMessage("clear", DialogPage.NONE);
        }
        int countTimestamps = 0;
        for (TableEntry entries : tableEntries) {
            if (entries.getType() == ColumnType.TIMESTAMP) {
                countTimestamps++;
            }
        }
        if (countTimestamps == 1) {
            setMessage("",
                DialogPage.NONE);
            setPageComplete(true);
            return;
        } else if (countTimestamps < 0) {
            setMessage("No entry with type TIMESTAMP",
                DialogPage.ERROR);
        } else {
            setMessage("More than one entry with type TIMESTAMP",
                DialogPage.ERROR);
        }
        setPageComplete(false);

    }

    private GridLayout initLayouts(Composite parent) {
        containerAll = new Composite(parent, SWT.NONE);
        containerTable = new Composite(containerAll, SWT.NONE);
        containerGrid = new Composite(containerAll, SWT.NONE);
        FillLayout layout = new FillLayout(SWT.VERTICAL);
        containerAll.setLayout(layout);
        FillLayout fillL = new FillLayout(SWT.VERTICAL);
        containerTable.setLayout(fillL);
        GridLayout grid = new GridLayout();
        containerGrid.setLayout(grid);
        grid.numColumns = 2;
        return grid;
    }

    private void initTable() {
        // Add the TableViewer
        tv = new TableViewer(containerTable, SWT.FULL_SELECTION);
        tv.setContentProvider(new ContentProvider());
        tv.setLabelProvider(new LabelProvider());
        tv.setInput(tableEntries);

        // Set up the table
        Table table = tv.getTable();

        new TableColumn(table, SWT.CENTER).setText(NAME);
        new TableColumn(table, SWT.CENTER).setText(TYPE);

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        // Create the cell editors
        CellEditor[] editors = new CellEditor[2];
        editors[0] = new TextCellEditor(table);
        String[] typesAsStrings = new String[ColumnType.values().length];
        for (int i = 0; i < ColumnType.values().length; i++) {
            typesAsStrings[i] = ColumnType.values()[i].toString();
        }
        editors[1] = new ComboBoxCellEditor(table, typesAsStrings,
            SWT.READ_ONLY);

        // Set the editors, cell modifier, and column properties
        tv.setColumnProperties(PROPS);
        tv.setCellModifier(new CellModifier(tv, this));
        tv.setCellEditors(editors);
        
        packTableColumns();
    }

    public void packTableColumns() {
        if (tv != null) {
            for (TableColumn tableColumn : tv.getTable().getColumns()) {
                tableColumn.pack();
            }
        }
    }

    public ArrayList<TableEntry> getTableEntries() {
        return tableEntries;
    }

    protected void setTable() {
        tableEntries.clear();
        System.out.println(Arrays.toString(headerLine));
        System.out.println(Arrays.toString(type2));
        if (headerLine == null || headerLine.length != type2.length) {
            return;
        }
        for (int i = 0; i < type2.length; i++) {
            TableEntry entry = new TableEntry(type2[i], headerLine[i]);
            if (type2[i] == ColumnType.TIMESTAMP) {
                setPageComplete(true);
            }
            tableEntries.add(entry);
        }
        tv.refresh();
        packTableColumns();
    }

    public void setTableValues(String[] headerLine, ColumnType[] type2) {
        this.headerLine = headerLine;
        size = type2.length;
        this.type2 = type2;
        if (isCreatControl) {
            setTable();
            validTable();
        }
        packTableColumns();
    }

}
