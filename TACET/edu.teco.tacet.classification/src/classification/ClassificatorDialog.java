package classification;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import edu.teco.tacet.track.Range;

public class ClassificatorDialog extends TitleAreaDialog {
	
	private String selectedClassifier = "";

	private static ClassifierTypeMeta[] nominalClassifiers = {new ClassifierTypeMeta("Bayes", new ClassifierMeta[]{new ClassifierMeta("Bayes Net", "Bayes", true, false), new ClassifierMeta("Naive Bayes", "Bayes", true, false), new ClassifierMeta("Naive Bayes Multinomial", "Bayes", true, false), new ClassifierMeta("Naive Bayes Multinominal Text", "Bayes", true, false), new ClassifierMeta("Naive Bayes Multinomial Updateable", "Bayes", true, false), new ClassifierMeta("Naive Bayes Updateable", "Bayes", true, false)}),
		new ClassifierTypeMeta("Functions", new ClassifierMeta[]{new ClassifierMeta("Logistic", "Functions", true, false), new ClassifierMeta("Multilayer Perceptron", "Functions", true, true), new ClassifierMeta("SGD", "Functions", true, false), new ClassifierMeta("SGD Text", "Functions", true, false), new ClassifierMeta("Simple Logistic", "Functions", true, false), new ClassifierMeta("SMO", "Functions", true, false), new ClassifierMeta("Voted Perceptron", "Functions", true, false)}),
		new ClassifierTypeMeta("Lazy", new ClassifierMeta[]{new ClassifierMeta("IBk", "Lazy", true, true), new ClassifierMeta("KStar", "Lazy", true, true), new ClassifierMeta("LWL", "Lazy", true, true)}),
		new ClassifierTypeMeta("Meta", new ClassifierMeta[]{new ClassifierMeta("Ada Boost M1", "Meta", true, false), new ClassifierMeta("Attribute Selected Classifier", "Meta", true, true), new ClassifierMeta("Bagging", "Meta", true, true), new ClassifierMeta("Classification via Regression", "Meta", true, false), new ClassifierMeta("Cost Sensitive Classifier", "Meta", true, false), new ClassifierMeta("CV Parameter Selection", "Meta", true, true), /*new ClassifierMeta("FilteredClassifier", "Meta", true, true),*/ new ClassifierMeta("Logit Boost", "Meta", true, false), new ClassifierMeta("MultiClass Classifier", "Meta", true, false), new ClassifierMeta("MultiClass Classifier Updateable", "Meta", true, false), new ClassifierMeta("Multi Scheme", "Meta", true, true), /*new ClassifierMeta("Random Committee", "Meta", true, true),*/ new ClassifierMeta("Random Subspace", "Meta", true, true), new ClassifierMeta("Stacking", "Meta", true, true), new ClassifierMeta("Vote", "Meta", true, true)}),
		new ClassifierTypeMeta("Misc", new ClassifierMeta[]{new ClassifierMeta("Input Mapped Classifier", "Misc", true, true), /*new ClassifierMeta("Serialized Classifier", "Misc", true, true)*/}),
		new ClassifierTypeMeta("Rules", new ClassifierMeta[]{new ClassifierMeta("Decision Table", "Rules", true, true), new ClassifierMeta("JRip", "Rules", true, false), new ClassifierMeta("OneR", "Rules", true, false), new ClassifierMeta("Part", "Rules", true, false), new ClassifierMeta("ZeroR", "Rules", true, true)}),
		new ClassifierTypeMeta("Trees", new ClassifierMeta[]{new ClassifierMeta("Decision Stump", "Trees", true, true), new ClassifierMeta("Hoeffding Tree", "Trees", true, false), new ClassifierMeta("J48", "Trees", true, false), new ClassifierMeta("LMT", "Trees", true, false), new ClassifierMeta("Random Forest", "Trees", true, false), new ClassifierMeta("Random Tree", "Trees", true, false), new ClassifierMeta("REP Tree", "Trees", true, true)})};
	
	private Font font;
	
	private List<String> annotationTracks;
	private List<String> sensorTracks;
	
	private Combo classSelectCombo;
	private static Tree classifiersTree;
	private int classIdx;
	private CheckboxTableViewer annotationTableViewer;
	private CheckboxTableViewer sensorTableViewer;
	private List<Integer> annotationIdxs;
	private List<Integer> sensorIdxs;
	private String classifierType;
	private String classifierName;
	

	public ClassificatorDialog(Shell parentShell, List<String> sensorTracks, List<String> annotationTracks, List<Range> coveredRanges) {
		super(parentShell);
		this.sensorTracks = sensorTracks;
		this.annotationTracks = annotationTracks;
		annotationIdxs = new LinkedList<>();
		sensorIdxs = new LinkedList<>();
		this.setHelpAvailable(false);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Classificator Settings");
		setMessage(
				"The classifier predicts annotation labels in a selected annotation track based on available sensor data and annotations.",
				IMessageProvider.INFORMATION);
			
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(layout);

		FontData fontData = parent.getFont().getFontData()[0];
		font = new Font(parent.getDisplay(), new FontData(fontData.getName(), fontData
			    .getHeight(), SWT.BOLD));
		
		createClassTrackSelection(container);
		createAttributeTrackSelection(container);
		createClassifierSelection(container);

		return area;
	}
	
	private void createClassifierSelection(Composite container) {
		Group selectClassifierGrp = new Group(container, SWT.NONE);
		selectClassifierGrp.setFont(font);
		selectClassifierGrp.setText("Select Classifier");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		selectClassifierGrp.setLayoutData(gridData);
		selectClassifierGrp.setLayout(new GridLayout(2, false));
		
		Label selectedClassifierLbl = new Label(selectClassifierGrp, SWT.NONE);
		selectedClassifierLbl.setText("Selected Classifier:");
		
		final Text selectedClassifierTxt = new Text(selectClassifierGrp, SWT.NONE | SWT.READ_ONLY | SWT.BORDER);
		selectedClassifierTxt.setText("");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		selectedClassifierTxt.setLayoutData(gridData);
		
		classifiersTree = new Tree(selectClassifierGrp, SWT.V_SCROLL | SWT.BORDER);
		initClassifiers();
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		classifiersTree.setLayoutData(gridData);
		
		classifiersTree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				selectedClassifier = "";
				TreeItem[] selection = classifiersTree.getSelection();
				if (selection[0].getParentItem() instanceof TreeItem) {
					selectedClassifier = selection[0].getParentItem().getText()
							+ " - " + selection[0].getText();
					selectedClassifierTxt.setText(selectedClassifier);
					classifierType = selection[0].getParentItem().getText().toLowerCase();
					classifierName = selection[0].getText().replace(" ", "");
				} else {
					classifiersTree.deselectAll();
					selectedClassifierTxt.setText("");
				}
			}
		});
	}
	
	private void createClassTrackSelection(Composite container) {
		Group selectClassTrackGrp = new Group(container, SWT.NONE);
		selectClassTrackGrp.setFont(font);
		selectClassTrackGrp.setText("Select Class Track");
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		selectClassTrackGrp.setLayoutData(gridData);
		selectClassTrackGrp.setLayout(new GridLayout(4, false));
		
		classSelectCombo = new Combo(selectClassTrackGrp, SWT.READ_ONLY);
		gridData = new GridData();
		gridData.horizontalSpan = 4;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		classSelectCombo.setLayoutData(gridData);
		
    	for(String t : annotationTracks) {
    		classSelectCombo.add(t);
		}
	}
	
	private void createAttributeTrackSelection(Composite container) {
		Group selectTrackGrp = new Group(container, SWT.NONE);
		selectTrackGrp.setFont(font);
		selectTrackGrp.setText("Select Attribute Tracks");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		selectTrackGrp.setLayoutData(gridData);
		selectTrackGrp.setLayout(new GridLayout(3, false));
		
		Label annotationTracksLbl = new Label(selectTrackGrp, SWT.NONE);
		annotationTracksLbl.setText("Annotation Tracks:");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		annotationTracksLbl.setLayoutData(gridData);
		
		Button allAnnotationsBtn = new Button(selectTrackGrp, SWT.NONE);
		allAnnotationsBtn.setText("All");
		
		Button noneAnnotationsBtn = new Button(selectTrackGrp, SWT.NONE);
		noneAnnotationsBtn.setText("None");
		
		annotationTableViewer = CheckboxTableViewer.newCheckList(selectTrackGrp, SWT.MULTI | SWT.H_SCROLL
	    	      | SWT.V_SCROLL | SWT.BORDER);
		Table annotationTable = annotationTableViewer.getTable();
		annotationTable.setLinesVisible(false);
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		annotationTable.setLayoutData(gridData);
		annotationTableViewer.setContentProvider(new ArrayContentProvider());
		annotationTableViewer.setInput(annotationTracks);

		allAnnotationsBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				annotationTableViewer.setAllChecked(true);
			}
		});
		
		noneAnnotationsBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				annotationTableViewer.setAllChecked(false);
			}
		});
		
		Label sensorTracksLbl = new Label(selectTrackGrp, SWT.NONE);
		sensorTracksLbl.setText("Sensor Tracks:");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		sensorTracksLbl.setLayoutData(gridData);
		
		Button allSensorsBtn = new Button(selectTrackGrp, SWT.NONE);
		allSensorsBtn.setText("All");
		
		Button noneSensorsBtn = new Button(selectTrackGrp, SWT.NONE);
		noneSensorsBtn.setText("None");

		sensorTableViewer = CheckboxTableViewer.newCheckList(selectTrackGrp, SWT.MULTI | SWT.H_SCROLL
	    	      | SWT.V_SCROLL | SWT.BORDER);
		Table sensorTable = sensorTableViewer.getTable();
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		sensorTable.setLayoutData(gridData);
		sensorTableViewer.setContentProvider(new ArrayContentProvider());
		sensorTableViewer.setInput(sensorTracks);

		allSensorsBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				sensorTableViewer.setAllChecked(true);
			}
		});
		
		noneSensorsBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				sensorTableViewer.setAllChecked(false);
			}
		});

	}

	@Override
	protected boolean isResizable() {
		return true;
	}
	
	@Override
	   protected void createButtonsForButtonBar(Composite parent) {
	    super.createButtonsForButtonBar(parent);

	    Button ok = getButton(IDialogConstants.OK_ID);
		ok.setText("Classify");
	    setButtonLayoutData(ok);

	    Button cancel = getButton(IDialogConstants.CANCEL_ID);
	    cancel.setText("Cancel");
	    setButtonLayoutData(cancel);
	 }

	@Override
	protected void okPressed() {
		for (int i = 0; i < annotationTableViewer.getTable().getItemCount(); i++) {
			if (annotationTableViewer.getChecked(annotationTableViewer
					.getElementAt(i)))
				annotationIdxs.add(i);
		}
		for (int i = 0; i < sensorTableViewer.getTable().getItemCount(); i++) {
			if (sensorTableViewer.getChecked(sensorTableViewer
					.getElementAt(i)))
				sensorIdxs.add(i);
		}
		if (validate()) {
			classIdx = classSelectCombo.getSelectionIndex();
			super.okPressed();
		} else {
			annotationIdxs = new LinkedList<>();
			sensorIdxs = new LinkedList<>();
		}
	}
	
	private void initClassifiers() {
		for (int i = 0; i < nominalClassifiers.length; i++) {
			TreeItem item = new TreeItem(classifiersTree, SWT.NONE);
			item.setText(nominalClassifiers[i].getName());
			for (ClassifierMeta c : nominalClassifiers[i].getClassifiers()) {
				TreeItem child = new TreeItem(item, SWT.NONE);
				child.setText(c.getName());
			}
		}
	}
	
	private boolean validate() {
		if (classSelectCombo.getSelectionIndex() == -1) {
			this.setMessage("Please select a class track!", DialogPage.WARNING);
				return false;
		}
		if (selectedClassifier.length() == 0) {
			this.setMessage("Please select a classifier!", DialogPage.WARNING);
				return false;
		}
		if (annotationIdxs.size() == 0 && sensorIdxs.size() == 0) {
			this.setMessage("Please select at least one attribute Track!", DialogPage.WARNING);
				return false;
		}
		return true;
	}
	
	public int getClassIdx() {
		return classIdx;
	}
	
	public String getSelectedClassifier() {
		if(classifierName.equals("MultilinearRegression"))
			return classifierName;
		return "weka.classifiers." + classifierType + "." + classifierName;
	}
	
	public List<Integer> getAnnotationIdxs() {
		return annotationIdxs;
	}
	
	public List<Integer> getSensorIdxs() {
		return sensorIdxs;
	}
}
