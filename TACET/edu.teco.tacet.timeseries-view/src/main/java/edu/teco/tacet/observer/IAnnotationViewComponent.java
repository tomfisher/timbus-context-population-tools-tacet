package edu.teco.tacet.observer;

import java.util.HashSet;

import edu.teco.tacet.annotation.AnnotationDialog;
import edu.teco.tacet.annotation.AnnotationView;

public interface IAnnotationViewComponent extends IViewComponent {

	public AnnotationDialog getAnnotationDialog();
	
	public HashSet<AnnotationView> getSelectedAnnotations();

	public int getSelectedTrack();

}
