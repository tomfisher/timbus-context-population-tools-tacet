package edu.teco.tacet.importWizard.db;


import net.miginfocom.swt.MigLayout;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.teco.tacet.controller.Controller;
import edu.teco.tacet.meta.DbDatasource;

public class DbPageOne extends WizardPage {
	private WizardPage nextPage;

	private Label lUsername;
	private Label lPassword;
	private Label lHost;
	private Label lPort;
	private Label lSid;
	private Label lDriver;
	private Label lConnection;

	private Text tUsername;
	private Text tPassword;
	private Text tHost;
	private Text tPort;
	private Text tSid;
	private Button tConnection;
	private Combo tDriver;
	private MigLayout layout;
	
	private Controller controller;
	private Composite container;
	
	private boolean passNextPage;
	
	

	DbDatasource dataSource;

	public DbPageOne(String pageName, WizardPage nextPage,
			Controller controller, DbDatasource datasource) {
		super(pageName);
		this.dataSource = datasource;
		this.nextPage = nextPage;
		this.controller = controller;
		this.passNextPage = false;
		setTitle("Connection Details");
		setDescription("Connection is established. You can move to the next page.");
	}

	@Override
	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);

		int x_size = container.getSize().x;
		setControl(container);
		layout = new MigLayout("", // Layout Constraints
				"[]50[grow]", // Column constraints
				"[][][][][][]");// Row constraints
		container.setLayout(layout);

		lUsername = new Label(container, SWT.None);
		lUsername.setText("Username:");
		FontData[] fD = lUsername.getFont().getFontData();
		fD[0].setHeight(x_size);
		lUsername.setFont(new Font(parent.getDisplay(), fD[0]));

		tUsername = new Text(container, SWT.NONE);
		tUsername.setLayoutData("wrap,growx");
		tUsername.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				getWizard().getContainer().updateButtons();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		tUsername.setText("gb_dbbe1");

		lPassword = new Label(container, SWT.None);
		lPassword.setText("Password:");
		lPassword.setFont(new Font(parent.getDisplay(), fD[0]));

		tPassword = new Text(container, SWT.NONE);
		tPassword.setLayoutData("wrap,growx");
		tPassword.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				getWizard().getContainer().updateButtons();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		tPassword.setText("gb_dbbe1");

		lHost = new Label(container, SWT.None);
		lHost.setText("Host:");
		lHost.setFont(new Font(parent.getDisplay(), fD[0]));

		tHost = new Text(container, SWT.NONE);
		tHost.setLayoutData("wrap,growx");
		tHost.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				getWizard().getContainer().updateButtons();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		tHost.setText("134.191.240.68");

		lPort = new Label(container, SWT.None);
		lPort.setText("Port:");
		lPort.setFont(new Font(parent.getDisplay(), fD[0]));

		tPort = new Text(container, SWT.NONE);
		tPort.setLayoutData("wrap,growx");
		tPort.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				getWizard().getContainer().updateButtons();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		tPort.setText("11521");

		lSid = new Label(container, SWT.None);
		lSid.setText("SID:");
		lSid.setFont(new Font(parent.getDisplay(), fD[0]));

		tSid = new Text(container, SWT.NONE);
		tSid.setLayoutData("wrap,growx");
		tSid.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				getWizard().getContainer().updateButtons();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		tSid.setText("NOACHI");

		lDriver = new Label(container, SWT.None);
		lDriver.setText("Connection Driver:");
		lDriver.setFont(new Font(parent.getDisplay(), fD[0]));

		
		tDriver = new Combo(container, SWT.READ_ONLY);
		tDriver.setLayoutData("wrap,growx");
		String[] items = {"oracle.jdbc.OracleDriver","com.mysql.jdbc.Driver"};
		tDriver.setItems(items);
		
		lConnection = new Label(container, SWT.None);
		lConnection.setText("Test Connection:");
		lConnection.setFont(new Font(parent.getDisplay(), fD[0]));
		
		tConnection = new Button(container, SWT.NONE);
		tConnection.setLayoutData("wrap,growx");
		tConnection.addSelectionListener(new SelectionListener() {

		      public void widgetSelected(SelectionEvent event) {
		    	  getWizard().getContainer().updateButtons();
					if (controller.testConnection(tUsername.getText(),
					tPassword.getText(), tHost.getText(),
					tPort.getText(), tSid.getText(),
					tDriver.getText())) {
						passNextPage = true;
					}
		      }

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		tConnection.setText("Test");

	}

	public void setDbdataSource() {
		this.dataSource.setName("DataBaseSource");
		this.dataSource.setLogin(tUsername.getText());
		this.dataSource.setPassword(tPassword.getText());
		this.dataSource.setLocation(tHost.getText() + ":" + tPort.getText());
		this.dataSource.setSid(tSid.getText());

	}

	@Override
	public boolean canFlipToNextPage() {
		if(!this.passNextPage){
			setMessage("Please provide connection details to enable the establishment of a database connection. After that click the " +
					"test button below to test the connection", DialogPage.ERROR);
			return false;
		}
		setMessage("Connection is established. You can move to the next page.");
		this.setDbdataSource();
		this.controller.setDataSource(dataSource);
		return true;
	}

	@Override
	public IWizardPage getNextPage() {
		return nextPage;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		if(this.passNextPage){
			return true;
		} else {
		return false;
		}
	}
	
	

}
