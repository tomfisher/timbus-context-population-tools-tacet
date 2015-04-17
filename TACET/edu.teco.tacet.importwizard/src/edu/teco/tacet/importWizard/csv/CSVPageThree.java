package edu.teco.tacet.importWizard.csv;

import edu.teco.tacet.util.TablePage;

public class CSVPageThree extends TablePage{

	public CSVPageThree(String pageName) {
		super(pageName);		
	}

	@Override
	public boolean canFlipToNextPage() {
		return false;
	}

	@Override
	protected void changePageForSpecifiedImport() {
		this.addButton.setVisible(false);
		this.addButton.setEnabled(false);
		this.removeButton.setVisible(false);
		this.removeButton.setEnabled(false);
		
	}
}
