package edu.teco.tacet.importWizard.db;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

import edu.teco.tacet.controller.Controller;
import edu.teco.tacet.meta.DbDatasource;
import edu.teco.tacet.util.ContentProvider;

public class DbPageThree extends WizardPage {
	private WizardPage nextPage;
	private Controller controller;
	private DbDatasource dataSource;
	private TableViewer tv;
	private Composite containerTable;

	private ArrayList<String> tableColumns = new ArrayList<String>();
	private ArrayList<String> tableColumnTypes = new ArrayList<String>();

	public DbPageThree(String pageName, WizardPage nextPage,
			Controller controller, DbDatasource dataSource) {
		super(pageName);
		setTitle("Columns from query request.");
		this.nextPage = nextPage;
		this.controller = controller;
		this.dataSource = dataSource;
	}

	@Override
	public IWizardPage getNextPage() {
		return nextPage;
	}

	public enum Types {
		SENSOR, ANNOTATION, TIMESTAMP;
	}

	public class Input {

		private String column;
		private String types;
		private Types type;
		private boolean isSelected;

		public Input(boolean isSelected, String label, String data) {
			this.column = label;
			this.types = data;
			this.isSelected = isSelected;
		}

		public String getColumn() {
			return column;
		}

		public String getColumnsType() {
			return types;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setType(Types type) {
			this.type = type;
		}

		public Types getType() {
			return this.type;
		}

	}

	public final class ExampleEditingSupport extends EditingSupport {

		private ComboBoxViewerCellEditor cellEditor = null;

		private ExampleEditingSupport(ColumnViewer viewer) {
			super(viewer);
			cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer()
					.getControl(), SWT.READ_ONLY);
			cellEditor.setLabelProvider(new LabelProvider());
			cellEditor.setContentProvider(new ArrayContentProvider());
			cellEditor.setInput(Types.values());
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return cellEditor;
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			if (element instanceof Input) {
				Input data = (Input) element;
				return data.getType();
			}
			return null;
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (element instanceof Input && value instanceof Types) {
				Input data = (Input) element;
				Types newValue = (Types) value;
				/* only set new value if it differs from old one */
				if (!data.getType().equals(newValue)) {
					data.setType(newValue);
				}
			}
		}

	}

	public final class TableLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public String getColumnText(Object element, int columnIndex) {
			Input data = (Input) element;
			switch (columnIndex) {
			case 0:
				return data.getColumn();
			case 1:
				return data.getColumnsType();
			default:
				return "";
			}
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return (columnIndex == 0) ? // COMPLETED_COLUMN?
			getImage(((Input) element).isSelected())
					: null;
		}

	}

	protected void initTable(Composite parent) {
		tv = new TableViewer(parent, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION);
		tableColumns.clear();
		tv.setContentProvider(new ContentProvider());
		tv.setLabelProvider(new TableLabelProvider());
		String[] columns = controller.getColumnValues(dataSource.getQuery());
		for (String column : columns) {
			if (column != null)
				tableColumns.add(column);
		}

		String[] types = controller.getColumnTypes(dataSource.getQuery());
		for (String type : types) {
			if (type != null)
				tableColumnTypes.add(type);
		}
		ArrayList<Input> typeEntries = new ArrayList<Input>();
		for (int i = 0; i < tableColumns.size(); i++) {
			if (tableColumnTypes.get(i) == "NUMERIC") {
				typeEntries.add(new Input(false, tableColumns.get(i), Types.SENSOR
						.name()));
			} else if (tableColumnTypes.get(i) == "STRING") {
				typeEntries.add(new Input(false, tableColumns.get(i),
						Types.ANNOTATION.name()));
			} else {
				typeEntries.add(new Input(false, tableColumns.get(i),
						Types.TIMESTAMP.name()));
			}
		}

		Table table = tv.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		// new TableColumn(table, SWT.CENTER).setText("check");
		// new TableColumn(table, SWT.CENTER).setText("columns");
		TableViewerColumn valueColumn = new TableViewerColumn(tv, SWT.NONE);
		valueColumn.getColumn().setText("columns");
		TableViewerColumn valueColumn_1 = new TableViewerColumn(tv, SWT.NONE);
		valueColumn_1.getColumn().setText("types");
		// new TableColumn(table, SWT.CENTER).setText("types");
		EditingSupport exampleEditingSupport = new ExampleEditingSupport(
				valueColumn.getViewer());
		valueColumn_1.setEditingSupport(exampleEditingSupport);
		for (int i = 0, n = table.getColumnCount(); i < n; i++) {
			table.getColumn(i).pack();
		}

		tv.setInput(typeEntries);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	@Override
	public void createControl(Composite parent) {
		containerTable = new Composite(parent, SWT.NONE);
		setControl(containerTable);
		GridLayout grid = new GridLayout(1, false);
		Label te = new Label(containerTable, SWT.NONE);
		te.setText("The Query Data with Types.");
		containerTable.setLayout(grid);
		initTable(containerTable);
		setPageComplete(true);

		containerTable.pack();
	}

	public boolean isPageComplete() {
		return true;
	}

}
