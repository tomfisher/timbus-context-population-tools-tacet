package classification.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import classification.ClassificatorDialogController;
import edu.teco.tacet.track.TrackManager;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 *
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ClassificatorHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ClassificatorHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 *
	 * @return
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		TrackManager tm = TrackManager.getInstance();
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        ClassificatorDialogController dialogController =
                new ClassificatorDialogController(
                    window.getShell(),
                    tm.getAllSensorTracks(),
                    tm.getAllAnnotationTracks());
		dialogController.classify();
		return null;
	}
}
