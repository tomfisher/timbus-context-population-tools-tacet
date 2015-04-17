package edu.teco.tacet.statisticsView;

import net.miginfocom.swt.MigLayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class AnnotationStatisticsView implements StatisticsView {

    @Override
    public Composite createView(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new MigLayout());
        (new Label(composite, SWT.NONE)).setText("This is not yet implemented.");
        return composite;
    }
    
}
