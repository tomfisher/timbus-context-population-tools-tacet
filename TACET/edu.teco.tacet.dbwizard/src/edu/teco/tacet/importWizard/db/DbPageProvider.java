package edu.teco.tacet.importWizard.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;

import edu.teco.tacet.controller.Controller;
import edu.teco.tacet.importmanager.ImportPageProvider;
import edu.teco.tacet.meta.DbDatasource;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.track.SourceIdGenerator;
import edu.teco.tacet.track.TrackIdGenerator;

public class DbPageProvider implements ImportPageProvider {
	private DbPageOne one;
	private DbPageTwo two;
	private DbPageThree three;
	private DbDatasource dataSource;
	private Controller controller;
	
	List<IWizardPage> pages = new ArrayList<>(3);
	
	public DbPageProvider() {
	    dataSource = MetaFactory.eINSTANCE.createDbDatasource();
        controller = new Controller(dataSource);
        
        
        three = new DbPageThree("Specifie Column Types",null, controller,dataSource); 
        two = new DbPageTwo("Specifie sql statement", three, controller, dataSource);
        one = new DbPageOne("Specifie Database connection", two, controller, dataSource);
        pages.add(one);
        pages.add(two);
        pages.add(three);
	}
	
	@Override
	public Iterable<IWizardPage> getWizardPages(IWizard wizard) {
        for (IWizardPage page : pages) {
            page.setWizard(wizard);
        }
        return pages;
	}

	@Override
	public Iterable<? extends Object> getConfigurations(TrackIdGenerator trackIdGen,
			SourceIdGenerator sourceIdGen, Project currentProject) {
		
			currentProject.getDatasources().add(dataSource);
			return Arrays.asList(new Object[] { dataSource });
		
	     
	}
    
}
