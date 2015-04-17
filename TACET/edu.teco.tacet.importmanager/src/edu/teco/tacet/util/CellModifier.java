package edu.teco.tacet.util;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Item;


import edu.teco.tacet.util.TableEntry.ColumnType;

public class CellModifier implements ICellModifier{
	private Viewer viewer;
	private TablePage table;
	  public CellModifier(Viewer viewer, TablePage tablePage) {
	    this.viewer = viewer;
	    this.table = tablePage;
	  }

	  /**
	   * Returns whether the property can be modified
	   * 
	   * @param element
	   *            the element
	   * @param property
	   *            the property
	   * @return boolean
	   */
	  public boolean canModify(Object element, String property) {
	    // Allow editing of all values
	    return true;
	  }

	  /**
	   * Returns the value for the property
	   * 
	   * @param element
	   *            the element
	   * @param property
	   *            the property
	   * @return Object
	   */
	  public Object getValue(Object element, String property) {
	    TableEntry entry = (TableEntry) element;
	    if (table.NAME.equals(property))
	      return entry.getName();
	    else if (table.TYPE.equals(property))
	      return entry.getType().ordinal();
	    else
	      return null;
	  }

	  /**
	   * Modifies the element
	   * 
	   * @param element
	   *            the element
	   * @param property
	   *            the property
	   * @param value
	   *            the value
	   */
	  public void modify(Object element, String property, Object value) {
		    if (element instanceof Item)
		      element = ((Item) element).getData();

		    TableEntry entry = (TableEntry) element;
		    if (table.NAME.equals(property))
		      entry.setName((String) value);
		    else if (table.TYPE.equals(property))
		      entry.setType(ColumnType.values()[(Integer) value]);
		    
		    // Force the viewer to refresh
		    viewer.refresh();
		    table.validTable();
	  }
	   
}
