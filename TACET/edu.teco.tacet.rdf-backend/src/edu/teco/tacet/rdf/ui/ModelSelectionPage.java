package edu.teco.tacet.rdf.ui;

import java.io.File;
import java.util.ArrayList;

import net.miginfocom.swt.MigLayout;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Model;

import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.rdf.RdfReader;
import edu.teco.tacet.util.collection.ListAdditions;
import edu.teco.tacet.util.function.F1;
import static edu.teco.tacet.util.collection.IterableAdditions.toIterable;
import static edu.teco.tacet.util.collection.IterableAdditions.toList;
import static edu.teco.tacet.util.ui.UiUtil.setEnabled;
import static edu.teco.tacet.util.ui.UiUtil.setLayoutData;

public class ModelSelectionPage extends WizardPage implements IWizardPage {

    private RdfDatasource datasource;
    private java.util.List<String> rootResources = new ArrayList<>();

    private Label lblPath, lblDatasources;
    private Text txtPath, txtOwlClass;
    private Button btnBrowse, btnSelectAll, btnDeselectAll, btnApplyOwlClass;
    private Button chbOwlClass;
    private List lstDatasources;

    private TimeseriesIdentificationPage nextPage;

    protected ModelSelectionPage(RdfDatasource datasource) {
        super("Model Selection");
        this.datasource = datasource;
    }

    @Override
    public void createControl(final Composite parent) {
        setTitle("Selection of import sources");
        setMessage("Choose the model to import from.\n"
            + "Then, select the datasource(s) to be imported from said model.");

        final Composite root = new Composite(parent, SWT.NONE);
        MigLayout layout =
            new MigLayout("", "[fill][]", "[nogrid]u[nogrid]u[grow 0][top, grow]");
        root.setLayout(layout);
        setControl(root);

        lblPath = new Label(root, SWT.LEFT);
        lblPath.setText("Model:");

        txtPath =
            setLayoutData(new Text(root, SWT.BORDER | SWT.SINGLE), "push, grow, width 50:100");
        txtPath.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                getWizard().getContainer().updateButtons();
            }

            @Override
            public void keyPressed(KeyEvent e) {}
        });

        btnBrowse = setLayoutData(new Button(root, SWT.NONE), "right, wrap");
        btnBrowse.setText("Browse");
        btnBrowse.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(SelectionEvent e) {}

            public void widgetSelected(SelectionEvent e) {
                FileDialog dlg = new FileDialog(root.getShell(), SWT.OPEN);
                dlg.setText("Select a Model to import");
                dlg.setFilterExtensions(new String[] { "*.rdf;*.owl", "*.rdf", "*.owl", "*.*" });
                String path = dlg.open();
                if (path != null) {
                    txtPath.setText(path);
                    if (!txtPath.getText().toLowerCase().endsWith(".rdf") &&
                        !txtPath.getText().toLowerCase().endsWith(".owl")) {

                        setMessage("Expected a file with either \".rdf\" or \".owl\" extension.",
                            DialogPage.WARNING);
                    } else {
                        setMessage(null);
                    }
                    Model model = RdfReader.readModelFromFile(path,
                        datasource.getResolveUri());
                    datasource.setModel(model);
                    datasource.setFileName(path);
                    getWizard().getContainer().updateButtons();
                }
            }
        });

        SelectionListener selListener = new MySelectionListener(root);

        chbOwlClass = new Button(root, SWT.CHECK);
        chbOwlClass.setText("Use an OWL class:");
        chbOwlClass.addSelectionListener(selListener);
        txtOwlClass = setLayoutData(new Text(root, SWT.SINGLE | SWT.BORDER), "grow, width 20:100:");
        btnApplyOwlClass = setLayoutData(new Button(root, SWT.PUSH), "wrap");
        btnApplyOwlClass.setText("Refresh Model");
        btnApplyOwlClass.addSelectionListener(selListener);

        lblDatasources = setLayoutData(new Label(root, SWT.LEFT), "wrap");
        lblDatasources.setText("Available Datasources");

        lstDatasources =
            setLayoutData(new List(root, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER),
                "growy, width 50:100:1000, hmin 100, hmax 800");
        lstDatasources.addSelectionListener(selListener);
        btnSelectAll =
            setLayoutData(new Button(root, SWT.NONE), "flowy, split 2, top, growx, growy 0");
        btnSelectAll.setText("Select All");
        btnSelectAll.addSelectionListener(selListener);
        btnDeselectAll = setLayoutData(new Button(root, SWT.NONE), "top, growx, growy 0, wrap");
        btnDeselectAll.setText("Deselect All");
        btnDeselectAll.addSelectionListener(selListener);
        setEnabled(false, txtOwlClass, btnApplyOwlClass, lstDatasources, btnSelectAll,
            btnDeselectAll);
    }

    class MySelectionListener implements SelectionListener {
        private Composite root;

        public MySelectionListener(Composite root) {
            this.root = root;
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            if (e.getSource() == btnSelectAll) {
                lstDatasources.selectAll();
            } else if (e.getSource() == btnDeselectAll) {
                lstDatasources.deselectAll();
            } else if (e.getSource() == chbOwlClass) {
                setEnabled(chbOwlClass.getSelection(), txtOwlClass, btnApplyOwlClass,
                    lstDatasources, btnSelectAll, btnDeselectAll);
            } else if (e.getSource() == btnApplyOwlClass) {
                datasource.setModel(RdfReader.readOntologyModelFromFile(txtPath.getText(), null));
                updateAvailableRootResources();
                root.layout();
            }
            getWizard().getContainer().updateButtons();
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {}
    }

    private void updateAvailableRootResources() {
        OntClass owlClass = ((OntModel) datasource.getModel()).getOntClass(txtOwlClass.getText());
        if (owlClass == null) {
            setErrorMessage("Could not find OWL Class \"" + txtOwlClass.getText() + "\".");
            return;
        }

        // get all matching resources
        @SuppressWarnings("unchecked")// it's save because we only swap elements of the list
        java.util.List<OntResource> resources =
            (java.util.List<OntResource>) toList(toIterable(owlClass.listInstances(true)));
        
        if (resources.isEmpty()) {
            setErrorMessage("Could not find any instance of OWL Class \""
                + txtOwlClass.getText() + "\".");
            return;
        }
        
        setErrorMessage(null);
        lstDatasources.removeAll();
        
        // sort them
        ListAdditions.<OntResource, String> sort(resources, new F1<OntResource, String>() {
            @Override
            public String apply(OntResource t) {
                return t.getURI();
            }
        });

        for (OntResource ontResource : resources) {
            lstDatasources.add(ontResource.getURI());
        }
    }

    @Override
    public boolean canFlipToNextPage() {
        if (chbOwlClass.getSelection()) {
            rootResources.clear();
            for (int i : lstDatasources.getSelectionIndices()) {
                rootResources.add(lstDatasources.getItem(i));
            }
            nextPage.setRootResources(rootResources);
        }
        return isPageComplete();
    }

    public void setNextPage(TimeseriesIdentificationPage page) {
        this.nextPage = page;
    }

    @Override
    public IWizardPage getNextPage() {
        return this.nextPage;
    }

    @Override
    public boolean isPageComplete() {
        if (txtPath != null && chbOwlClass != null) {
            boolean fileExists = txtPath.getText().length() > 0 &&
                (new File(txtPath.getText()).exists());
            if (chbOwlClass.getSelection()) {
                return fileExists && lstDatasources.getSelectionCount() > 0;
            }
            return fileExists;
        }
        return false;
    }

}
