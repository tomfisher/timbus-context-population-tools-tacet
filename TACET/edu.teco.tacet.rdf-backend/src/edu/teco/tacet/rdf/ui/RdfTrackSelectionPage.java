package edu.teco.tacet.rdf.ui;

import static edu.teco.tacet.util.collection.IterableAdditions.filter;
import static edu.teco.tacet.util.collection.IterableAdditions.map;
import static edu.teco.tacet.util.collection.IterableAdditions.toIterable;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Composite;

import edu.teco.tacet.export.TrackSelectionPage;
import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.ui.project.UniqueContentProvider.Unique;
import edu.teco.tacet.ui.project.filters.DatasourceTypeViewerFilter;
import edu.teco.tacet.ui.project.filters.EmptyInMemoryDatasourceFilter;
import edu.teco.tacet.ui.project.filters.Mode;
import edu.teco.tacet.ui.project.filters.UniqueViewerFilter;
import edu.teco.tacet.util.function.F1;

public class RdfTrackSelectionPage extends TrackSelectionPage {
    
    private Project exportProject;

    public RdfTrackSelectionPage(IWizardPage nextPage, Project currentProject, Project exportProject) {
        super(nextPage, currentProject, false);
        this.exportProject = exportProject;
        super.setTitle("RDF Export");
        super.setMessage("Select the datasources and/or groups you want to export.");
    }

    @Override
    protected void onCreateControl(Composite parent) {
        super.onCreateControl(parent);

        // Only show RDF Datasources
        getProjectViewer().addFilter(new UniqueViewerFilter(
            new DatasourceTypeViewerFilter<>(RdfDatasource.class, Mode.KEEP)));

        // Only show specific groups
        getProjectViewer().addFilter(new UniqueViewerFilter(new RdfGroupFilter()));

        // Do not show empty in memory datasources
        getProjectViewer().addFilter(new UniqueViewerFilter(new EmptyInMemoryDatasourceFilter()));
        
        // Use a different CheckStateListener that always operates on the Datasource / Group, even
        // if a Timeseries is selected.
        getProjectViewer().addCheckStateListener(new RdfCheckStateListener());

    }
    
    @Override
    public boolean canFlipToNextPage() {
        boolean canFlipToNextPage = super.canFlipToNextPage();
        if (canFlipToNextPage) {
            Project project = buildProjectFromSelectedItems();
            exportProject.getDatasources().clear();
            exportProject.getDatasources().addAll(project.getDatasources());
        }
        
        return canFlipToNextPage;
    }
    
    @Override
    public boolean isPageComplete() {
        return super.isPageComplete();
    }

    public Project buildProjectFromSelectedItems() {
        final F1<Object, Object> uniqueToValue = new F1<Object, Object> () {
            @Override
            public Object apply(Object unique) {
                return ((Unique) unique).value;
            }
        };
        
        final F1<Object, Boolean> isDatasourceOrGroup = new F1<Object, Boolean>() {
            @Override
            public Boolean apply(Object value) {
                return value instanceof Datasource || value instanceof Group;
            }
        };
    
        Iterable<? extends Object> sourcesAndGroups =
            filter(isDatasourceOrGroup,
                map(uniqueToValue, toIterable(getProjectViewer().getCheckedElements())));
    
        Project project = MetaFactory.eINSTANCE.createProject();
    
        for (Object value : sourcesAndGroups) {
            // If we encounter a Datasource just copy it over.
            if (value instanceof Datasource) {
                project.getDatasources().add(EcoreUtil.copy((Datasource) value));
            }
            // When we encounter a group, we take all its members and build a new RdfDatasource
            // which in turn is added to the project. This is done because the Group lacks
            // features we will later need, such as a filename.
            if (value instanceof Group) {
                Group group = (Group) value;
                RdfDatasource source = null;
    
                // At least one series belongs to a RdfDatasource that we use as a base for our new
                // datasource.
                for (Timeseries series : group.getTimeseries()) {
                    if (series.getDatasource() instanceof RdfDatasource) {
                        source = (RdfDatasource) EcoreUtil.copy(series.getDatasource());
                        source.getTimeseries().clear();
                        break;
                    }
                }
    
                // Copy all group members and put them into the new datasource
                source.getTimeseries().addAll(EcoreUtil.copyAll(group.getTimeseries()));
    
                project.getDatasources().add(source);
            }
        }
    
        return project;
    }

    class RdfCheckStateListener extends TrackSelectionPage.DefaultCheckStateListener {
        @Override
        protected void processTimeseries(Unique unique, boolean checked) {
            getProjectViewer().setSubtreeChecked(unique.getParent(), checked);
        }
    }

}
