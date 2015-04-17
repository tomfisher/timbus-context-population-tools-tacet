package edu.teco.tacet.statisticsView;

import net.miginfocom.swt.MigLayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SensorStatisticsView implements StatisticsView {
   
    private Text txtMean, txtMinimum, txtMaximum, txtStandardDeviation, txtMedian, txtMode;

    @Override
    public Composite createView(Composite parent) {
        Composite composite = new Composite(parent, SWT.BORDER);
        composite.setLayout(new MigLayout("wrap 2, fillx", "[][grow, fill]"));
        
        (new Label(composite, SWT.NONE)).setText("Mean");
        txtMean = new Text(composite, SWT.NONE);
        (new Label(composite, SWT.NONE)).setText("Minimum");
        txtMinimum = new Text(composite, SWT.NONE);
        (new Label(composite, SWT.NONE)).setText("Maximum");
        txtMaximum = new Text(composite, SWT.NONE);
        (new Label(composite, SWT.NONE)).setText("Standard deviation");
        txtStandardDeviation = new Text(composite, SWT.NONE);
        (new Label(composite, SWT.NONE)).setText("Median");
        txtMedian = new Text(composite, SWT.NONE);
        (new Label(composite, SWT.NONE)).setText("Mode");
        txtMode = new Text(composite, SWT.NONE);
        
        return composite;
    }

    public void setTxtMean(String txtMean) {
        this.txtMean.setText(txtMean);
    }

    public void setTxtMinimum(String txtMinimum) {
        this.txtMinimum.setText(txtMinimum);
    }

    public void setTxtMaximum(String txtMaximum) {
        this.txtMaximum.setText(txtMaximum);
    }

    public void setTxtStandardDeviation(String txtStandardDeviation) {
        this.txtStandardDeviation.setText(txtStandardDeviation);
    }

    public void setTxtMedian(String txtMedian) {
        this.txtMedian.setText(txtMedian);
    }

    public void setTxtMode(String txtMode) {
        this.txtMode.setText(txtMode);
    }

}
