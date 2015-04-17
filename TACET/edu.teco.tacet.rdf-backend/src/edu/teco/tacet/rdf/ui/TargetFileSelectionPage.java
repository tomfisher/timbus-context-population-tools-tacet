package edu.teco.tacet.rdf.ui;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;

import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.ui.project.ProjectUi;
import edu.teco.tacet.util.collection.MutableTuple;

public class TargetFileSelectionPage extends WizardPage {

    private Project exportProject;
    private TreeViewer viewer;

    public TargetFileSelectionPage(Project exportProject) {
        super("Target File Selection Page");
        this.exportProject = exportProject;
    }

    @Override
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new FillLayout());
        setControl(root);
        ProjectUi projectUi = new ProjectUi.Builder(root, SWT.BORDER_SOLID, exportProject)
            .setUseDefaultMenuItems(false).build();
        viewer = projectUi.getViewer();
        viewer.setContentProvider(new GroupByFilenameContentProvider(
            (ITreeContentProvider) viewer.getContentProvider()));
        viewer.setLabelProvider(new GroupByFilenameLabelProvider(
            (ILabelProvider) viewer.getLabelProvider()));
        
        viewer.setColumnProperties(new String[] { "column0" });
        viewer.setCellEditors(new CellEditor[] { new TextCellEditor(viewer.getTree()) });
        viewer.setCellModifier(new CellModifier());
    }
    
    @Override
    public boolean isPageComplete() {
        return true;
    }

    private class CellModifier implements ICellModifier {

        
        @Override
        public void modify(Object element, String property, Object value) {
            if (element instanceof Item) {
                element = ((Item) element).getData();
            }
            
            @SuppressWarnings("unchecked")
            MutableTuple<String, List<Object>> tuple =
                ((MutableTuple<String, List<Object>>) element);
            
            for (Object o : tuple.getSecond()) {
                RdfDatasource rdfDs = (RdfDatasource) o;
                rdfDs.setFileName((String) value);
            }
            tuple.setFirst((String) value);
            viewer.update(tuple, null);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object getValue(Object element, String property) {
            return ((MutableTuple<String, List<Object>>) element).getFirst();
        }

        @Override
        public boolean canModify(Object element, String property) {
            return element instanceof MutableTuple;
        }
    }

}
