package edu.teco.tacet.export;

import java.util.ArrayList;

import net.miginfocom.swt.MigLayout;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;
import edu.teco.tacet.ui.project.ProjectUi;
import edu.teco.tacet.ui.project.UniqueContentProvider;
import edu.teco.tacet.ui.project.UniqueContentProvider.Unique;
import edu.teco.tacet.ui.project.UniqueLabelProvider;

public class TrackSelectionPage extends WizardPage {

    private Label lblAvailableTracks;
    private ProjectUi projectUi;
    private CheckboxTreeViewer treeViewer;
    private Project currentProject;
    private IWizardPage nextPage;
    private boolean useDefaultCheckStateListener;

    public TrackSelectionPage(IWizardPage nextPage, Project currentProject) {
        this(nextPage, currentProject, true);
    }

    public TrackSelectionPage(IWizardPage nextPage, Project currentProject,
        boolean useDefaultCheckStateListener) {
        super("Track Selection Page"); // I don't know what the name is good for.
        setTitle("Chose the Tracks you want to export.");
        this.nextPage = nextPage;
        this.currentProject = currentProject;
        this.useDefaultCheckStateListener = useDefaultCheckStateListener;
    }

    @Override
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        setControl(root);
        root.setLayout(new MigLayout("fill", "[fill]", "[][grow]"));

        lblAvailableTracks = new Label(root, SWT.LEFT);
        lblAvailableTracks.setText("Available Tracks");

        ProjectUi.Builder builder = new ProjectUi.Builder(root, SWT.BORDER, currentProject);

        treeViewer = new CheckboxTreeViewer(
            builder.getRootComposite(), SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

        treeViewer.setLabelProvider(new UniqueLabelProvider(
            new ProjectUi.TreeLabelProvider()));

        treeViewer.setContentProvider(new UniqueContentProvider(
            new ProjectUi.TreeContentProvider(currentProject)));

        if (useDefaultCheckStateListener) {
            treeViewer.addCheckStateListener(new DefaultCheckStateListener());
        }

        projectUi = builder.setUseGivenLabelProvider(true).setViewer(treeViewer).build();

        projectUi.setLayoutData("newline, grow");

        onCreateControl(parent);
    }

    public class DefaultCheckStateListener implements ICheckStateListener {

        @Override
        public final void checkStateChanged(CheckStateChangedEvent event) {
            Unique unique = (Unique) event.getElement();
            if (unique.value instanceof Datasource) {
                processDatasource(unique, event.getChecked());
            } else if (unique.value instanceof Group) {
                processGroup(unique, event.getChecked());
            } else if (unique.value instanceof Timeseries) {
                processTimeseries(unique, event.getChecked());
            }
            getWizard().getContainer().updateButtons();
        }

        protected void processDatasource(Unique unique, boolean checked) {
            for (Unique child : unique.getChildren()) {
                treeViewer.setChecked(child, checked);
            }
        }

        protected void processGroup(Unique unique, boolean checked) {
            processDatasource(unique, checked);
        }

        protected void processTimeseries(Unique unique, boolean checked) {
            if (checked && !treeViewer.getChecked(unique.getParent())) {
                boolean allSeriesAreChecked = true;
                for (Unique child : unique.getParent().getChildren()) {
                    if (!treeViewer.getChecked(child)) {
                        allSeriesAreChecked = false;
                        break;
                    }
                }
                if (allSeriesAreChecked) {
                    treeViewer.setChecked(unique.getParent(), true);
                }
            } else if (!checked && treeViewer.getChecked(unique.getParent())) {
                treeViewer.setChecked(unique.getParent(), false);
            }
        }
    }

    protected void onCreateControl(Composite parent) {}

    public Iterable<Long> getSelectedSensorTracks() {
        return getSelectedTracks(TimeseriesType.SENSOR);
    }

    public Iterable<Long> getSelectedAnnotationTracks() {
        return getSelectedTracks(TimeseriesType.ANNOTATION);
    }

    private Iterable<Long> getSelectedTracks(TimeseriesType type) {
        Object[] checkedElements = treeViewer.getCheckedElements();
        ArrayList<Long> ret = new ArrayList<>(checkedElements.length);
        for (Object element : checkedElements) {
            if (((Unique) element).value instanceof Timeseries) {
                Timeseries series = (Timeseries) ((Unique) element).value;
                if (series.getType() == type) {
                    ret.add(series.getId());
                }
            }
        }
        ret.trimToSize();
        return ret;
    }

    @Override
    public boolean canFlipToNextPage() {
        return treeViewer != null &&
            (getSelectedAnnotationTracks().iterator().hasNext() ||
                (getSelectedSensorTracks().iterator().hasNext()));
    }
    
    @Override
    public boolean isPageComplete() {
        return canFlipToNextPage();
    }
    
    @Override
    public IWizardPage getNextPage() {
        return nextPage;
    }

    public void setNextPage(IWizardPage nextPage) {
        this.nextPage = nextPage;
    }

    protected CheckboxTreeViewer getProjectViewer() {
        return treeViewer;
    }
}
