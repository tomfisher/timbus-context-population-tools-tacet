package edu.teco.tacet.ui.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.miginfocom.swt.MigLayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.MediaTimeseries;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;
import edu.teco.tacet.statisticsView.StatisticsDialog;
import edu.teco.tacet.timeseriesview.ITrackViewController;
import edu.teco.tacet.timeseriesview.TrackViewController;
import edu.teco.tacet.track.TrackManager;
import edu.teco.tacet.ui.project.ProjectUi.AbstractMenuItem;

public class ProjectView extends ViewPart {

    private ITrackViewController trackViewController = new TrackViewController();
    private Tree tree;

    private ProjectUi projectUi;

    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new MigLayout("fill", "[fill]", "[fill, grow 70][fill, grow 30]"));

        Project project;
        if (TrackManager.isInitialised()) {
            project = TrackManager.getInstance().getCurrentProject();
        } else {
            // create an empty Project as long as we don't have any data
            project = MetaFactory.eINSTANCE.createProject();
        }

        projectUi = new ProjectUi.Builder(parent, SWT.NONE, project)
            .setUseDefaultMenuItems(true).build();
        projectUi.addMenuItem(new StatisticsItem());
        projectUi.setLayoutData("wrap");
        tree = projectUi.getTree();

        tree.addListener(SWT.MouseDoubleClick, new TreeMouseListener());
    }

    static class StatisticsItem extends AbstractMenuItem {

        @Override
        protected MenuItem createItem(Menu menu, TreeItem item, Tree tree) {
            final Object data = item.getData();
            if (data instanceof Timeseries) {
                MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
                menuItem.setText("Show statistics ...");

                menuItem.addSelectionListener(new ProjectUi.DefaultSelectionListener() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        Timeseries series = (Timeseries) data;
                        TrackManager tm = TrackManager.getInstance();
                        Shell shell =
                            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
                        StatisticsDialog dialog = new StatisticsDialog(shell);
                        if (series.getType() == TimeseriesType.SENSOR) {
                            dialog.setSensorTrack(tm.getSensorTrack(series.getId()));
                        } else if (series.getType() == TimeseriesType.ANNOTATION) {
                            dialog.setAnnoTrack(tm.getAnnotationTrack(series.getId()));
                        }
                        dialog.open();
                    }

                });
                return menuItem;
            }
            return null;
        }
    }

    class TreeMouseListener implements Listener {

        @Override
        public void handleEvent(Event event) {
            TreeItem[] selection = tree.getSelection();

            if (selection.length == 1) {
                TreeItem item = tree.getSelection()[0];
                Collection<Timeseries> allSeries = null;
                String name = "";
                if (item.getData() instanceof Datasource) {
                    Datasource source = (Datasource) item.getData();
                    allSeries = getAllTimeseries(source);
                    name = source.getName();
                } else if (item.getData() instanceof Group) {
                    Group group = (Group) item.getData();
                    allSeries = getAllMembers(group);
                    name = group.getName();
                } else {
                    return;
                }
                List<Long> sensorSeries = new ArrayList<>();
                List<Long> annotationSeries = new ArrayList<>();
                List<Long> mediaSeries = new ArrayList<>();
                partition(allSeries, sensorSeries, annotationSeries, mediaSeries);

                trackViewController.showTracks(name, sensorSeries, annotationSeries, mediaSeries);
            }
        }
    }

    private Collection<Timeseries> getAllTimeseries(Datasource source) {
        List<Timeseries> ret = new ArrayList<>();
        for (Timeseries series : source.getTimeseries()) {
            ret.add(series);
        }
        return ret;
    }

    private Collection<Timeseries> getAllMembers(Group group) {
        List<Timeseries> ret = new ArrayList<>();
        for (Timeseries series : group.getTimeseries()) {
            ret.add(series);
        }
        return ret;
    }

    private void partition(Iterable<Timeseries> series,
        Collection<Long> sensor, Collection<Long> annotation, Collection<Long> media) {
        for (Timeseries ts : series) {
			if (ts instanceof MediaTimeseries) {
				media.add(ts.getId());
			} else {
				switch (ts.getType()) {
				case SENSOR:
					sensor.add(ts.getId());
					break;
				case ANNOTATION:
					annotation.add(ts.getId());
					break;
				default:
					break;
				}
			}
        }
    }

    @Override
    public void setFocus() {
        // I do not know if I need this.
    }

    public Project getProject() {
        return projectUi.getProject();
    }

    public void setProject(Project project) {
        projectUi.setProject(project);
    }

}
