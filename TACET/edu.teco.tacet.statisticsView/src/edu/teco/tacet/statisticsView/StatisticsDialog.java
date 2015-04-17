package edu.teco.tacet.statisticsView;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Track;
import static edu.teco.tacet.statisticsCalc.Calculation.*;

public class StatisticsDialog extends TitleAreaDialog {

    private StatisticsView currentView;
    private Runnable computeStatistics;
    private String name;

    public StatisticsDialog(Shell parentShell) {
        super(parentShell);

        super.setHelpAvailable(false);
    }

    @Override
    public void create() {
        super.create();
        setTitle("Statistics for track \"" + name + "\"");
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        if (currentView != null) {
            Composite root = (Composite) super.createDialogArea(parent);
            Composite content = currentView.createView(root);
            computeStatistics.run();
            content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            return content;
        } else {
            throw new IllegalStateException("The currentView must be set");
        }
    }

    private void computeAndSetSensorStatistics(Track<Datum> track, SensorStatisticsView view) {
        StatisticsContext ctx = contextualize(track);
        view.setTxtMean(Double.toString(mean(ctx)));
        view.setTxtMinimum(Double.toString(min(ctx)));
        view.setTxtMaximum(Double.toString(max(ctx)));
        view.setTxtMedian(Double.toString(median(ctx)));
        view.setTxtStandardDeviation(Double.toString(standardDeviation(ctx)));
        double[] mode = mode(ctx);
        view.setTxtMode((mode.length > 1 ? "(first mode) " : "") + Double.toString(mode[0]));
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, "Close", true);
    }

    public void setSensorTrack(final Track<Datum> track) {
        this.currentView = new SensorStatisticsView();
        this.name = track.getMetaData().getName();
        this.computeStatistics = new Runnable() {
            @Override
            public void run() {
                computeAndSetSensorStatistics(track, (SensorStatisticsView) currentView);
            }
        };
    }

    public void setAnnoTrack(Track<Annotation> track) {
        this.currentView = new AnnotationStatisticsView();
        this.name = track.getMetaData().getName();
        this.computeStatistics = new Runnable() {
            @Override
            public void run() {}
        };
    }
}
