package edu.teco.tacet.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ExportPageMain extends WizardPage {

    private String item;
    private boolean enableNext = false;

    protected ExportPageMain(String pageName) {
        super(pageName);
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 1;
        Label label2 = new Label(container, SWT.NONE | SWT.SINGLE);
        label2.setText("Select Provider");

        final Combo combo = new Combo(container, SWT.READ_ONLY);
        String items[] = { "CSV", "SQL" };
        combo.setItems(items);
        combo.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {}

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (combo.getText().equals("CSV")) {
                    item = "CSV";
                }
            }
        });
        setControl(container);
    }

    @Override
    public boolean canFlipToNextPage() {
        if (item != null) {

            this.enableNext = true;
        }
        return this.enableNext;
    }

    public String getItem() {
        return item;
    }

}
