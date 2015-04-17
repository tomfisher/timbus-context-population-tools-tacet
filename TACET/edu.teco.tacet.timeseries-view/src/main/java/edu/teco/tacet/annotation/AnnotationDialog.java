package edu.teco.tacet.annotation;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.osgi.service.prefs.Preferences;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Range;

public class AnnotationDialog extends Dialog {

	private AnnotationPart annotationPart;
	private AnnotationTrackController annotationController;
	
	private Annotation oldAnnotation;
	private Annotation copyAnnotation;
	private Boolean editMode = false;
	private int oldTrackNumber;
	
	private int selectedTrack = 0;
	
	private Text nameTextfield;
	private Combo nameSuggestionsComboBox;
	private Combo tracksComboBox;
	private Text startTimeTextfield;
	private Text endTimeTextfield;
	private Text descriptionTextarea;
	
	private boolean timeModifyListenerActive = true;
	
	protected AnnotationDialog(Shell parentShell, AnnotationPart annotationView, AnnotationTrackController annotationController) {
		super(parentShell);
		this.annotationPart = annotationView;
		this.annotationController = annotationController;
	}
	
	private TrackView getCurrentTrackView() {
		return annotationPart.tracks.get(selectedTrack);
	}
	
	private void updatePreviewRange() {
		if (!editMode) { // TODO figure out a way to live preview changes to existing annotations without saving
			annotationController.setSelectionRange(copyAnnotation.getRange());
		}
	}
	
	public void setAnnotation(Annotation annotation) {
		this.copyAnnotation = annotation.deepCopy();
		editMode = true;
		oldAnnotation = annotation;
	}
	
	public void setSelectedRange(Range selectedRange) {
		if(copyAnnotation == null) {
			copyAnnotation = new Annotation(selectedRange, ""); 
		}
		if(!editMode) {
			copyAnnotation.setRange(selectedRange);
		}
	}
	
	public void setSelectedTrack(int selectedTrack) {
		this.selectedTrack = selectedTrack;
	}
	
	public void setOldTrackNumber(int number) {
		this.oldTrackNumber = number;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		
		// Make a live preview of annotation 
		TrackView targetTrackView = annotationPart.tracks.get(oldTrackNumber);
		targetTrackView.setPreviewName(copyAnnotation.getLabel());

		// Name Label
		Label nameLabel = new Label(container, 0);
		nameLabel.setText("Name");
		// Name Textfield
		nameTextfield = new Text(container, SWT.BORDER);
		nameTextfield.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		nameTextfield.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				copyAnnotation.setLabel(nameTextfield.getText());
				getCurrentTrackView().setPreviewName(copyAnnotation.getLabel());
				annotationPart.redraw();
			}
		});
		this.nameTextfield.setText(copyAnnotation.getLabel());
		nameTextfield.setFocus();
		
		String[] suggestions = annotationController.getNameSuggestions(oldTrackNumber);
		new AutoCompleteField(nameTextfield, new TextContentAdapter(), suggestions);
		
		nameTextfield.addFocusListener(new FocusListener() {
		    @Override
			public void focusGained(FocusEvent e) {
		        Text t = (Text) e.widget;
		        t.selectAll();
		      }
			@Override
			public void focusLost(FocusEvent e) {
			}
		});
	
		makeSeperator(container);
		
		// Track Label
		Label trackLabel = new Label(container, 0);
		trackLabel.setText("Track");
		// Tracks ComboBox
		tracksComboBox = new Combo(container, SWT.READ_ONLY);
		for (String trackName : annotationController.getTrackNames()) {
			tracksComboBox.add(trackName);
		}
		tracksComboBox.select(oldTrackNumber);
		tracksComboBox.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// movePreview(selectedTrack, tracksComboBox.getSelectionIndex());
				selectedTrack = tracksComboBox.getSelectionIndex();
				nameSuggestionsComboBox.setItems(annotationController.getNameSuggestions(selectedTrack)); // TODO: Nullpointer
				annotationPart.redraw();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
		});
	
		makeSeperator(container);
	
		// Start Time Label
		Label startLabel = new Label(container, 0);
		startLabel.setText("Start");
		// Start Time Textfield
		startTimeTextfield = new Text(container, SWT.BORDER);
		startTimeTextfield.setText(translateInFormat(copyAnnotation.getStart()));
				
		startTimeTextfield.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		startTimeTextfield.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if(!timeModifyListenerActive || translateToTime(startTimeTextfield.getText())==Long.MIN_VALUE || !copyAnnotation.getRange().canSetStart(translateToTime(startTimeTextfield.getText()))) {
					return;
				}
				copyAnnotation.setStart(translateToTime(startTimeTextfield.getText()));
				updatePreviewRange();
				annotationPart.redraw();
			}
		});
	
		// End Time Label
		Label lblEnd = new Label(container, 0);
		lblEnd.setText("End");
		// End Time Textfield
		endTimeTextfield = new Text(container, SWT.BORDER);
		endTimeTextfield.setText(translateInFormat(copyAnnotation.getEnd()));
		endTimeTextfield.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		endTimeTextfield.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if(!timeModifyListenerActive || translateToTime(startTimeTextfield.getText())==Long.MIN_VALUE || !copyAnnotation.getRange().canSetEnd(translateToTime(endTimeTextfield.getText()))){
					return;
				}
				copyAnnotation.setEnd(translateToTime(endTimeTextfield.getText()));
				updatePreviewRange();
				annotationPart.redraw();
			}
		});

		// Description Label
		Label decriptionLabel = new Label(container, 0);
		decriptionLabel.setText("Description");
		// Description Textarea
		descriptionTextarea = new Text(container, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 5 * descriptionTextarea.getLineHeight();
		descriptionTextarea.setLayoutData(gridData);
		descriptionTextarea.setText(copyAnnotation.getDescription());
		descriptionTextarea.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				copyAnnotation.setDescription(descriptionTextarea.getText());
			}
		});
		
		makeSeperator(container);

		// Fit-Left Button
		Button fitLeftButton = new Button(container, 0);
		fitLeftButton.setText("Fit Left");
		fitLeftButton.setLayoutData(new GridData(SWT.DEFAULT, SWT.RIGHT, false, false, 1, 1));
		fitLeftButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				autoFitLeft();
			}
		});

		// Fit-Right Button
		Button fitRightButton = new Button(container, 0);
		fitRightButton.setText("Fit Right");
		fitRightButton.setLayoutData(new GridData(SWT.DEFAULT, SWT.RIGHT, false, false, 1, 1));
		fitRightButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				autoFitRight();
			}
		});
		
		makeSeperator(container);
	
		return container;
	}

	
	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		String submitText;
		if (editMode) {
			submitText = "Edit";
		} else {
			submitText = "Annotate";
		}
		createButton(parent, IDialogConstants.OK_ID, submitText, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		if (editMode) {
			Button btDelete = createButton(parent, IDialogConstants.NO_ID, "Delete", false);
			btDelete.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					deletePressed();
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		
		if (editMode) {
			newShell.setText("Edit Annotation");
		} else {
			newShell.setText("Create Annotation");
		}
	}

	@Override
	protected void okPressed() {
		boolean added = false;
		if (editMode) {
			added = this.annotationController.editAnnotation(copyAnnotation, oldAnnotation, selectedTrack, oldTrackNumber);
		} else {
			added = this.annotationController.insertAnnotation(copyAnnotation, selectedTrack); 
		}

		if (added) {
			annotationController.cancelSelection();
			super.okPressed();
		} else {
			askForAutoFit();
		}
		annotationPart.closeAnnotationDialog();
	}

	@Override
	protected void cancelPressed() {
		super.cancelPressed();
		annotationController.cancelSelection();
		annotationPart.closeAnnotationDialog();
	}
	
	@Override
	protected void handleShellCloseEvent() {
		super.handleShellCloseEvent();
		annotationController.cancelSelection();
		annotationPart.closeAnnotationDialog();
	}
	
	private void deletePressed() {
		annotationController.deleteAnnotations(copyAnnotation.getRange(), oldTrackNumber);
		annotationController.cancelSelection();
		super.okPressed();
		annotationPart.closeAnnotationDialog();
	}

	private void makeSeperator(Composite container) {
		Label separator = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
	}
	
	private long translateToTime(String timestamp) {
		Preferences preferences = ConfigurationScope.INSTANCE
				.getNode("edu.teco.tacet.preferences");
		Preferences sub1 = preferences.node("ui1");
		if (sub1.getBoolean("numerical", false)) {
			return Long.valueOf(timestamp);
		}
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.append(DateTimeFormat.forPattern(sub1.get("timestampformat", "yyyy-MM-dd HH:mm:ss")))
				.toFormatter().withZone(DateTimeZone.UTC);
		try {
			return formatter.parseDateTime(timestamp).getMillis();

		} catch (IllegalArgumentException e) {
			return Long.MIN_VALUE;
		}
	}
	
	private String translateInFormat(long timestamp) {
		Preferences preferences = ConfigurationScope.INSTANCE
				.getNode("edu.teco.tacet.preferences");
		Preferences sub1 = preferences.node("ui1");
		if (sub1.getBoolean("numerical", false)) {
			return String.valueOf(timestamp);
		}
		DateTimeFormatter fmt = DateTimeFormat.forPattern(sub1.get("timestampformat", "yyyy-MM-dd HH:mm:ss"));
		DateTime datetime = new DateTime(timestamp, DateTimeZone.UTC);
		String s = datetime.toString(fmt);
		
		return s;
	}
	
	private void askForAutoFit() {
		String messageText = "Error\n\nSelected range has overlap with existing annotation.\n" + "Fit automatically?";
		MessageDialog autoFit = new MessageDialog(this.getShell(), "Error", null, messageText,
				MessageDialog.ERROR, new String[] { "Yes", "No" }, 0);
		int fit = autoFit.open();
		if (fit == 0) { // yes is pressed
			Range aRange = annotationController.autoFitAnnotation(copyAnnotation.getRange(), selectedTrack);
			copyAnnotation.setRange(aRange);
			updatePreviewRange();
			
			timeModifyListenerActive = false;
			startTimeTextfield.setText(translateInFormat(copyAnnotation.getStart()));
			endTimeTextfield.setText(translateInFormat(copyAnnotation.getEnd()));
			timeModifyListenerActive = true;
		}
	}
	
	private void autoFitLeft() {
		Range range = annotationController.autoFillLeft(copyAnnotation.getRange(), selectedTrack);
		copyAnnotation.setRange(range);
		updatePreviewRange();
		
		timeModifyListenerActive = false;
		startTimeTextfield.setText(translateInFormat(copyAnnotation.getStart()));
		timeModifyListenerActive = true;
		
	}

	private void autoFitRight() {
		Range range = annotationController.autoFillRight(copyAnnotation.getRange(), selectedTrack);
		copyAnnotation.setRange(range);
		updatePreviewRange();

		timeModifyListenerActive = false;
		endTimeTextfield.setText(translateInFormat(copyAnnotation.getEnd()));
		timeModifyListenerActive = true;
	}
}