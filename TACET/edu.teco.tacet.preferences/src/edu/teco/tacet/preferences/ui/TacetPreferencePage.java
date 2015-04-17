package edu.teco.tacet.preferences.ui;

import org.osgi.service.prefs.Preferences;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
public class TacetPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private Label lTimestampformat;
	private Label lnumerical;
	private Text tTimestampformat;
	private Button checkbox;
	private boolean state = false;
	@Override
	protected Control createContents(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		lTimestampformat = new Label(parent, SWT.NONE);
		lTimestampformat.setText("Timestampformat:");
		tTimestampformat = new Text(parent, SWT.SINGLE);
		lnumerical = new Label(parent,SWT.NONE);
		lnumerical.setText("Numerical Timestamp:");
		checkbox = new Button(parent, SWT.CHECK);
		checkbox.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				state = !state;
				checkbox.setSelection(state);				
				if(state){
					tTimestampformat.setEnabled(false);
				} else {
					tTimestampformat.setEnabled(true);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
				
			}
		});
		
		return null;
	}

	@Override
	public void init(IWorkbench workbench) {
		
	}

	protected void performDefaults() {		
		tTimestampformat.setText("yyyy-MM-dd HH:mm:ss");				
	}

	/**
	 * Save the color preference to the preference store.
	 */
	public boolean performOk() {
		Preferences preferences = ConfigurationScope.INSTANCE.getNode("edu.teco.tacet.preferences");
		Preferences sub1 = preferences.node("ui1");
		sub1.put("timestampformat", tTimestampformat.getText());
		sub1.putBoolean("numerical", state);
		return true;
	}

}
