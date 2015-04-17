/*
 * Licensed Material - Property of IBM 
 * (C) Copyright IBM Corp. 2002 - All Rights Reserved. 
 */
 
package edu.teco.tacet.wizard;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import edu.teco.tacet.Writer.CsvWriter;

/**
 * Wizard class
 */
public class ExportWizard extends Wizard implements INewWizard
{
	// wizard pages
	ExportPageOne pageOne;
	ExportPageTwo pageTwo;
	ArrayList<String>testArray = new ArrayList<String>();
	String provider = "";
	
	
	
	
	// workbench selection when the wizard was started
	protected IStructuredSelection selection;
	

	
	// the workbench instance
	protected IWorkbench workbench;

	/**
	 * Constructor for Wizard.
	 */
	public ExportWizard() {
		super();

	}
	
	public void addPages()
	{
		

		
	}

	/**
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) 
	{
		this.workbench = workbench;
		this.selection = selection;
	}

	public boolean canFinish()
	{
		
		return true;
	}
	
	public boolean performFinish() 
	{
		@SuppressWarnings("unused")
		CsvWriter csv = new CsvWriter(null);
		MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
			"Tacet", "Created CSV");
		return true;
	}
}
