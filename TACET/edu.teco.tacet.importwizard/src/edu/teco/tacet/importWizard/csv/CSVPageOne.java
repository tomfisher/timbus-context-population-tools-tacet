package edu.teco.tacet.importWizard.csv;

import java.io.IOException;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import controller.ImportReader;
import edu.teco.tacet.util.TablePage;

public class CSVPageOne extends WizardPage implements Listener {
	private Text tPath;
	private Composite container;
	private Label lSelect;
	private Button bBrowser;
	private Label lHeader;
	private Button bCheckBox;
	private Text tHeaderRegex;
	private boolean selected = false;
	private Label lElSeparator;
	private Label lEmpty;
	private boolean enableNext = false;
	CSVPageTwo pageTwo;
	TablePage pageThree;
	public CSVPageOne(CSVPageTwo pageTwo, TablePage pageThree) {
		super("File selection");
		setTitle("File selection");
		setDescription("Please select the CSV file to be imported.");
		this.pageTwo = pageTwo;
		this.pageThree = pageThree;
	}
	

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		lSelect = new Label(container, SWT.NONE | SWT.SINGLE);
		lSelect.setText("Select File");
		initPathView();

		initHeader(container);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		tPath.setLayoutData(gd);
		// Required to avoid an error in the system
		setControl(container);
		setPageComplete(false);

	}

	private void initPathView() {
		tPath = new Text(container, SWT.BORDER | SWT.SINGLE);
		tPath.setText("");
		tPath.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (tPath.getText().length() > 0) {
					canFlipToNextPage(true);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		bBrowser = new Button(container, SWT.NONE);
		bBrowser.setText("Browse");
		bBrowser.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(container.getShell(), SWT.OPEN);
				dlg.setText("Open");
				dlg.setFilterExtensions(new String[]{"*.csv"});
				String path = dlg.open();
				if (path != null) {
					tPath.setText(path);
					if (!tPath.getText().endsWith("csv")){
							
						setMessage("Not expected ending \"csv\"",
								DialogPage.WARNING);
					}
					
					canFlipToNextPage(true);
				}
			}
		});
	}

	@Override
	public IWizardPage getNextPage() {	
        if(enableNext){         
            try {
                ImportReader reader = new ImportReader(getPath(),isHeader(),getHeaderSeperator()); 
                reader.analyseLine();
                pageTwo.setReader(reader);
            } catch (IOException e) {
                setMessage("No valid Path", DialogPage.ERROR);
                System.out.println(e);
            }

        }
        
		return pageTwo;
	}


	private void initHeader(Composite parent) {
		lHeader = new Label(parent, SWT.NONE);
		lHeader.setText("Header");
		bCheckBox = new Button(parent, SWT.CHECK);
		lEmpty = new Label(parent, SWT.NONE);
		lEmpty.setVisible(false);
		lElSeparator = new Label(parent, SWT.NONE);
		lElSeparator.setText("Header Separator:");
		tHeaderRegex = new Text(parent, SWT.BORDER | SWT.SINGLE);
		tHeaderRegex.setText("");
		tHeaderRegex.setVisible(false);
		lElSeparator.setVisible(false);
		tHeaderRegex.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (tHeaderRegex.getText().equals("")) {
					canFlipToNextPage(false);
				} else {
					canFlipToNextPage(true);					
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		bCheckBox.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selected = !selected;
				if (selected == true) {
					tHeaderRegex.setVisible(true);
					lElSeparator.setVisible(true);
					tHeaderRegex.setText("");
					tHeaderRegex.setFocus();
					canFlipToNextPage(false);
				} else {
					tHeaderRegex.setVisible(false);
					lElSeparator.setVisible(false);
					tHeaderRegex.setText("");
					canFlipToNextPage(true);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
	}

	@Override
	public boolean canFlipToNextPage() {
		return enableNext;
	}

	public boolean isHeader(){
		return selected;
	}
	
	public String getHeaderSeperator(){
		return tHeaderRegex.getText();
	}
	
	public String getPath() {
		return tPath.getText();
	}

	public void canFlipToNextPage(boolean flip) {
		enableNext = flip;
		getWizard().getContainer().updateButtons();		
	}

	@Override
	public boolean isPageComplete() {		
		if (pageThree.isPageComplete()) {
			return true;
		}
		
		return false;
		
	}

	@Override
	public void handleEvent(Event event) {

	}
}
