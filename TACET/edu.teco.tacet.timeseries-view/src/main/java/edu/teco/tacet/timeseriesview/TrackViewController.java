package edu.teco.tacet.timeseriesview;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class TrackViewController implements ITrackViewController, IPerspectiveFactory {
	private ArrayList<ITrackDisplayer> viewSensor = new ArrayList<ITrackDisplayer>();
	private ArrayList<ITrackDisplayer> viewAnnoation = new ArrayList<ITrackDisplayer>();
	private ArrayList<ITrackDisplayer> viewMedia = new ArrayList<ITrackDisplayer>();

	private static String ID_SENSOR = "edu.teco.tacet.jfreechart.SensorDataTrackView";
	private static String ID_ANNOTATION = "edu.teco.tacet.annotation.AnnotationView";
	private static String ID_TIME = "edu.teco.tacet.timeview.TimeView";
	private static String ID_MEDIA = "edu.tedo.tacet.media.view.MediaView";
	
	@Override
	public void showTracks(String groupName, Iterable<Long> sensorTracks,
			Iterable<Long> annotationTracks, Iterable<Long> mediaTracks) {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(ID_TIME);
		
			// Check for focus or Show first for id and then for annotation
			focus(sensorTracks, viewSensor, ID_SENSOR, groupName);
			focus(annotationTracks, viewAnnoation, ID_ANNOTATION, groupName);
			focusMedia(mediaTracks, groupName);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
	}

	private void focus(Iterable<Long> tracks, ArrayList<ITrackDisplayer> views,
			String viewId, String groupName) throws PartInitException {
		//if no tracks to show
		if(tracks.iterator().hasNext()==false){
			return;
		}
		Set<Long> tempId = convertToSet(tracks);
		ITrackDisplayer avp = null;
		for (ITrackDisplayer iTrackDisplayer : views) {
			Set<Long> tempIdList = convertToSet(iTrackDisplayer
					.getVisibleTracks());
			if (tempId.equals(tempIdList)) {
				avp = (ITrackDisplayer) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage()
						.showView(viewId, iTrackDisplayer.getOwnID(), IWorkbenchPage.VIEW_CREATE);
				avp.showTracks(tempId);				
				avp.setDisplayerName(groupName);
				return;
			}
		}
		
		avp = (ITrackDisplayer) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.showView(viewId, "" + views.size() + 1, IWorkbenchPage.VIEW_CREATE);
		avp.setOwnID("" + views.size() + 1);
		avp.showTracks(tracks);
		avp.setDisplayerName(groupName);
		views.add(avp);

	}
	
	private void focusMedia(Iterable<Long> tracks, String groupName) throws PartInitException {
		if(tracks.iterator().hasNext()==false){
			return;
		}
		Set<Long> tempId = convertToSet(tracks);
		ITrackDisplayer avp = null;
		boolean found;
		for (Long l : tempId) {
			found = false;
			for (ITrackDisplayer iTrackDisplayer : viewMedia) {
				Set<Long> tempIdList = convertToSet(iTrackDisplayer
						.getVisibleTracks());
				if (tempIdList.contains(l)) {
					found = true;
					avp = (ITrackDisplayer) PlatformUI
							.getWorkbench()
							.getActiveWorkbenchWindow()
							.getActivePage()
							.showView(ID_MEDIA, iTrackDisplayer.getOwnID(),
									IWorkbenchPage.VIEW_CREATE);
					ArrayList<Long> list = new ArrayList<Long>();
					list.add(l);
					avp.showTracks(list);
					avp.setDisplayerName(groupName);
					break;
				}
			}
			if(!found) {
				avp = (ITrackDisplayer) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.showView(ID_MEDIA, "" + viewMedia.size() + 1, IWorkbenchPage.VIEW_CREATE);
				avp.setOwnID("" + viewMedia.size() + 1);
				ArrayList<Long> list = new ArrayList<Long>();
				list.add(l);
				avp.showTracks(list);
				avp.setDisplayerName(groupName);
				viewMedia.add(avp);
			}
		}
	}

	

	public Set<Long> convertToSet(Iterable<Long> iter) {
		Set<Long> list = new TreeSet<Long>();
		for (Long item : iter) {
			list.add(item);
		}
		return list;
	}

	@Override
	public void createInitialLayout(IPageLayout layout) {
		 	//TODO set the correct layout
			String editorArea = layout.getEditorArea();

	        // Place navigator and outline to left of
	        // editor area.
	        IFolderLayout top =
	                layout.createFolder("top", IPageLayout.TOP, (float) 0.26, editorArea);
	        top.addView(ID_SENSOR);
	        IFolderLayout middle =
	                layout.createFolder("buttom", IPageLayout.BOTTOM, (float) 0.26, editorArea);
	        middle.addView(ID_ANNOTATION);
	        
		
	}

}
