package edu.teco.tacet.util.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;

public class UiUtil {

    public static <T extends Control> T setLayoutData(T ctrl, Object data) {
        ctrl.setLayoutData(data);
        return ctrl;
    }

    public static <T extends Viewer> T setLayoutData(T viewer, Object data) {
        viewer.getControl().setLayoutData(data);
        return viewer;
    }

    public static <T extends Control> void setEnabled(boolean flag, Control... controls) {
        for (Control control : controls) {
            control.setEnabled(flag);
        }
    }

}
