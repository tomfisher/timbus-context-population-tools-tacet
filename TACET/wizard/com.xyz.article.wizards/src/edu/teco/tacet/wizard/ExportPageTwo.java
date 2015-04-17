package edu.teco.tacet.wizard;

import java.util.ArrayList;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ExportPageTwo extends WizardPage  {


	private Composite containerTabel;
	private Composite containerGrid;
	private TableViewer tv;
	private Composite containerAll;
	private int index;

	public static final String NAME = "Name";
	public static final String[] PROPS = { NAME};

	// The data model
	private ArrayList<String> tableEntries = new ArrayList<String>();

	public ExportPageTwo() {
		super("PageTwo");
		
		for ( int i = 0; i < 10; i ++ ){
			this.tableEntries.add(String.valueOf(i));
		}
	}

	@Override
	public void createControl(Composite parent) {
		initLayouts(parent);
		initTable();	
		Button upButton = new Button(containerGrid, SWT.ARROW | SWT.UP);
		// Add a new person when the user clicks button
		upButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						if(index-1 >= 0){
							String temp = tableEntries.get(index);
							tableEntries.set(index , tableEntries.get(index-1));
							tableEntries.set(index -1, temp);
							index--;
							tv.refresh();
							}
				}
		});
		
		// addButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,
		// true, false));
		upButton.setText("Up");
		Button downButton = new Button(containerGrid, SWT.ARROW | SWT.DOWN);
		downButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if(index+1 < tableEntries.size()){
					String temp = tableEntries.get(index);
					tableEntries.set(index , tableEntries.get(index+1));
					tableEntries.set(index +1, temp);
					index++;
					tv.refresh();
					
					checkPageComplete();
					}
			}
		});
		downButton.setText("Down");
		setPageComplete(false);
		setControl(containerAll);
	}

	protected void checkPageComplete() {
		setPageComplete(true);
		
	}

	private GridLayout initLayouts(Composite parent) {
		containerAll = new Composite(parent, SWT.NONE);
		containerTabel = new Composite(containerAll, SWT.NONE);
		containerGrid = new Composite(containerAll, SWT.NONE);
		FillLayout layout = new FillLayout(SWT.VERTICAL);
		containerAll.setLayout(layout);
		FillLayout fillL = new FillLayout(SWT.VERTICAL);
		containerTabel.setLayout(fillL);
		GridLayout grid = new GridLayout();
		containerGrid.setLayout(grid);
		grid.numColumns = 2;
		
		return grid;
	}

	private void initTable() {
		// Add the TableViewer
		tv = new TableViewer(containerTabel, SWT.FULL_SELECTION);
		tv.setContentProvider(new ContentProvider());
		tv.setLabelProvider(new LabelProvider());
		tv.setInput(tableEntries);
		
		// Set up the table
		final Table table = tv.getTable();
		table.addListener(SWT.MouseDown, new Listener () {
			@Override
			public void handleEvent (Event event) {
			
				Point pt = new Point (event.x, event.y);
				TableItem temp = table.getItem(pt);
				index = table.indexOf(temp);
			}});

		new TableColumn(table, SWT.CENTER).setText(NAME);
		

		for (int i = 0, n = table.getColumnCount(); i < n; i++) {
			table.getColumn(i).pack();
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}

    public ArrayList<String> getTableEntries() {
        return tableEntries;
    }


	
	

}