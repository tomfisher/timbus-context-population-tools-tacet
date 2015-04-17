package classification.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.teco.tacet.track.TrackManager;
import emulation.EmulatorDialogController;



public class EmulatorHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public EmulatorHandler() {
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
        EmulatorDialogController dialogController =
                new EmulatorDialogController(
                    window.getShell(),
                    tm.getAllSensorTracks(),
                    tm.getAllAnnotationTracks());
		dialogController.emulate();
		return null;
	}
}
