package edu.teco.tacet.wizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Class representing the first page of the wizard
 */

public class ExportPageOne extends WizardPage
{
    private Text text1;
    private Text text2;

    private Composite container;
    private boolean enableNext;
    private String newPath;
    private String seperator;
    
    private IWizardPage nextPage;

    public ExportPageOne(IWizardPage nextPage) {
        super("First Page");
        setTitle("First Page");
        setDescription("Choose Seperator and the Path where to save the.csv");
        this.enableNext = false;
        this.nextPage = nextPage;
    }

    @Override
    public void createControl(Composite parent) {

        container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 3;

        // setPageComplete(false);
        Label label2 = new Label(container, SWT.NONE | SWT.SINGLE);
        label2.setText("Set Path");
        text2 = new Text(container, SWT.BORDER | SWT.SINGLE);
        Button button1 = new Button(container, SWT.NONE);

        button1.setText("Browse");
        button1.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event e) {
                DirectoryDialog dirDialog = new DirectoryDialog(getShell());
                dirDialog.setText("Select the directory where to Save the .csv");
                newPath = dirDialog.open();
                if (newPath != null) {
                    text2.setText(newPath);
                    setPageComplete(true);
                }
            }
        });
        Label label1 = new Label(container, SWT.NONE);
        label1.setText("Choose Seperator");
        text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
        text1.setText("");
        text1.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if (!text1.getText().isEmpty()) {
                    seperator = text1.getText();
                    setPageComplete(true);
                }
            }
        });

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        text1.setLayoutData(gd);
        text2.setLayoutData(gd);
        // required to avoid an error in the system
        setControl(container);
    }

    public String getText1() {
        return text1.getText();
    }

    @Override
    public boolean canFlipToNextPage() {
        if (newPath != null && seperator != null) {
            this.newPath = text2.getText();
            this.enableNext = true;
        }
        return this.enableNext;
    }

    public boolean isEnableNext() {
        return this.enableNext;
    }

    public void setEnableNext(boolean enableNext) {
        this.enableNext = enableNext;
    }

    public Text getText2() {
        return text2;
    }

    public void setText2(Text text2) {
        this.text2 = text2;
    }

    public IWizardPage getNextPage() {
        return nextPage;
    }

    public String getSeperator() {
        return this.seperator;
    }

    public String getNewPath() {
        return newPath;
    }

}
