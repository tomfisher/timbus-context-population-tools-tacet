package edu.teco.tacet.importWizard.csv;

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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import controller.ImportReader;
import edu.teco.tacet.meta.nongen.Unit;
import edu.teco.tacet.util.TablePage;

public class CSVPageTwo extends WizardPage implements Listener {
	private Text timeStampFormat;
	private Button radioButton[];
	private Text elementSeparator;
	private Combo lineSeparator;
	private boolean enableNext;
	private GridData gridData;
	private boolean flagElementSeparator;
	private Composite container;
	private ImportReader reader = null;
	private boolean selected[] = {false,false,false};
	private boolean isCreatControl = false;
	private TablePage pageThree;
	private boolean flagTimeStampFormat;
	private Label ltimestampFormat;
	private Combo combo;
	private GridLayout layout;

	public CSVPageTwo(TablePage pageThree) {
		super("File specification");
		setTitle("File specification");
		setDescription("Please specify the file.");
		this.pageThree = pageThree;
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		// ElementSeparator
		Label lelementSeparator = new Label(container, SWT.NONE);
		lelementSeparator.setText("Element separator:");
		
		initElementSeparator();
		// TimestampFormat
		gridData = new GridData();
		gridData.verticalSpan = 0;
		gridData.horizontalSpan = 1;
		lelementSeparator.setLayoutData(gridData);
		gridData = new GridData();
		gridData.verticalSpan = 0;
		gridData.horizontalSpan = 2;
		elementSeparator.setLayoutData(gridData);		
		
		Label llineSeparator = new Label(container, SWT.NONE);
		llineSeparator.setText("Line separator:");
		iniLineSeparator();
		gridData = new GridData();
		gridData.verticalSpan = 1;
		gridData.horizontalSpan = 1;
		llineSeparator.setLayoutData(gridData);
		gridData = new GridData();
		gridData.verticalSpan = 1;
		gridData.horizontalSpan = 2;
		lineSeparator.setLayoutData(gridData);

		radioButton = new Button[3];
		radioButton[0] = new Button(container, SWT.RADIO);
		radioButton[0].setText("SimpleDateFormat, e.g. yyyy-MM-dd");
		radioButton[1] = new Button(container, SWT.RADIO);
		radioButton[1].setText("Numerical Timestamp, e.g. 1ms,2ms,...");
		radioButton[2] = new Button(container, SWT.RADIO);
		radioButton[2].setText("UnixTimestamp (Starting 1970 (86400), unit:minutes");
		
		initCheckbock();
		gridData = new GridData();
		gridData.verticalSpan = 0;
		gridData.horizontalSpan = 1;
		
		ltimestampFormat = new Label(container, SWT.NONE);
		ltimestampFormat.setText("Timestamp format:");
		initTimeStampFormat();
		combo = new Combo(container, SWT.READ_ONLY);
		combo.setLayoutData(gridData);
		initComboBox();
		setControl(container);
		canFlipToNextPage(false);
		isCreatControl = true;
		setReaderValues();
		
	}

	private void initComboBox() {
		combo.setItems(Unit.getPossibleValues());
		combo.select(0);		
	}

	private void initCheckbock() {
		radioButton[0].addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				timeStampFormat.setEnabled(true);
				combo.setEnabled(false);
				flagTimeStampFormat = !timeStampFormat.getText().isEmpty();
				ltimestampFormat.setText("Timestamp format:");
				
				selected[0] = true;
				selected[1] = false;
				selected[2] = false;
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		radioButton[1].addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				combo.setEnabled(false);
				timeStampFormat.setEnabled(false);
				ltimestampFormat.setText("Not needed:");
				flagTimeStampFormat = true;
				selected[1] = true;
				selected[2] = false;
				selected[0] = false;
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		radioButton[2].addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				combo.setEnabled(true);
				timeStampFormat.setEnabled(false);
				ltimestampFormat.setText("Unit:");
				selected[2] = true;
				selected[0] = false;
				selected[1] = false;
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
	}

	public Unit getUnit(){
		return Unit.valueOf(combo.getText().toUpperCase());
	}
	
	public void setReader(ImportReader reader) {
		this.reader = reader;
		if (isCreatControl) {
			setReaderValues();
		}
	}

	private void setReaderValues() {
		if(reader==null){
			return;
		}
		flagTimeStampFormat = true;
		elementSeparator.setText(reader.getElementSeparator());
		flagElementSeparator = true;		
		lineSeparator.select(reader.getLineSeparator());
		timeStampFormat.setText(reader.getGuessTimestampFormat());
		combo.setEnabled(false);
		if (reader.getGuessTimestampFormat().isEmpty()) {
			selected[0] = false;
			selected[1] = true;
			radioButton[0].setSelection(false);
			radioButton[1].setSelection(true);
			timeStampFormat.setEnabled(false);
		} else {
			selected[0] = true;
			selected[1] = false;
			radioButton[0].setSelection(true);
			radioButton[1].setSelection(false);
			timeStampFormat.setEnabled(true);
		}
		checkPageComplete();

		pageThree.setTableValues(reader.getHeaderLine(), reader.getType());
	}

	private void initTimeStampFormat() {
		timeStampFormat = new Text(container, SWT.BORDER | SWT.SINGLE);
		timeStampFormat.setText("");

		if (reader != null) {
			timeStampFormat.setText(reader.getGuessTimestampFormat());
		}
		timeStampFormat.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {				
				flagTimeStampFormat = !timeStampFormat.getText().isEmpty();
				//container.pack();
				checkPageComplete();
			}
		});
	}

	private void initElementSeparator() {		
		elementSeparator = new Text(container, SWT.BORDER | SWT.SINGLE);
		elementSeparator.setText("");
		elementSeparator.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (elementSeparator.getText().isEmpty()
						&& elementSeparator.getText().length() == 1) {
					flagElementSeparator = false;
				} else if (elementSeparator.getText().length() != 1) {
					setMessage("elementSeparator must only one character long",
							DialogPage.ERROR);

					flagElementSeparator = false;
				} else {
					setMessage(null, DialogPage.ERROR);
					flagElementSeparator = true;
				}
				checkPageComplete();
			}
		});
		
		
		
	}

	private void iniLineSeparator() {
		lineSeparator = new Combo (container, SWT.READ_ONLY);
		lineSeparator.setItems (new String [] {"\\n\\r","\\n", "\\r\\n","\\r"});	

	}

	public String getElementSeparator() {
		return elementSeparator.getText();
	}

	public String getTimeStampFormat() {
		return timeStampFormat.getText();
	}

	private void checkPageComplete() {
		if (flagElementSeparator && flagTimeStampFormat) {
			canFlipToNextPage(true);
		} else {
			canFlipToNextPage(false);
		}
	}

	@Override
	public boolean canFlipToNextPage() {
		return enableNext;
	}

	public void canFlipToNextPage(boolean flip) {
		enableNext = flip;
		getWizard().getContainer().updateButtons();
		if (enableNext) {
			// TODO set the types an names for next page
		}
	}

	@Override
	public boolean isPageComplete() {
		if (pageThree.isPageComplete()) {
			return true;
		}
		return false;
	}

	@Override
	public IWizardPage getNextPage() {
		return pageThree;
	}

    @Override
    public void handleEvent(Event event) {}

	public boolean isTreatTimestampAsMillis() {
		return selected[1];
	}

	public boolean isStartFrom1970() {
		return selected[2];
	}
	
	public String getLineSeperator(){
		String[] sep = new String [] {"\n\r","\n", "\r\n","\r"};
		return sep[lineSeparator.getSelectionIndex()];
	}
	
	
	
	
}