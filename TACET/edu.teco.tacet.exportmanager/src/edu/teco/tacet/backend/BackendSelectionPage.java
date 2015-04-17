package edu.teco.tacet.backend;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class BackendSelectionPage extends WizardPage {

    private BackendManager backendManager;
    private ListViewer backendList;

    public BackendSelectionPage(BackendManager backendManager) {
        super("Select Import Backend");
        this.backendManager = backendManager;
    }

    private void fillBackendList() {
        Iterable<String> readerTypes = backendManager.getAvailableBackends();
        System.out.println("BackendSelectionPage: " + readerTypes);

        IStructuredContentProvider cp = new IStructuredContentProvider() {
            @Override
            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

            @Override
            public void dispose() {}

            @SuppressWarnings("unchecked")
            @Override
            public Object[] getElements(Object inputElement) {
                List<String> list = new ArrayList<>();
                for (String readerType : (Iterable<String>) inputElement) {
                    list.add(readerType);
                }
                return list.toArray();
            }
        };

        backendList.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                getWizard().getContainer().updateButtons();
            }
        });

        backendList.addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(DoubleClickEvent event) {
                getWizard().getContainer().showPage(getNextPage());
            }
        });

        backendList.setContentProvider(cp);
        backendList.setInput(readerTypes);

        if (backendList.getList().getItemCount() < 1) {
            setMessage("No Backends found.", ERROR);
        } else {
            setTitle("Backend selection");
            setDescription("Please select a backend to import data from.");
        }
        backendList.refresh();
    }

    @Override
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        FillLayout layout = new FillLayout(SWT.VERTICAL);
        root.setLayout(layout);
        this.backendList = new ListViewer(root, SWT.SINGLE);
        backendList.getList().setParent(root);
        fillBackendList();
        setControl(root);
    }

    @Override
    public IWizardPage getNextPage() {
        return backendManager.getPagesFor(getSelectedBackend()).iterator().next();
    }

    @Override
    public boolean canFlipToNextPage() {
        return backendList.getList().getSelectionCount() == 1;
    }

    @Override
    public boolean isPageComplete() {
        return backendList.getList().getSelectionCount() == 1;
    }

    /**
     * Returns the currently selected backend. The returned String is one of the values returned by
     * {@link BackendManager#getAvailableBackends()}.
     * 
     * @return the currently selected backend or {@code null} if none is selected.
     */
    public String getSelectedBackend() {
        String[] ret = backendList.getList().getSelection();
        return ret.length == 0 ? null : ret[0];
    }
}
