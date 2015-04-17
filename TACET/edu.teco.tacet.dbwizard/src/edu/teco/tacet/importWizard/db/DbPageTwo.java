package edu.teco.tacet.importWizard.db;

import net.miginfocom.swt.MigLayout;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.teco.tacet.controller.Controller;
import edu.teco.tacet.meta.DbDatasource;

public class DbPageTwo extends WizardPage {
	private WizardPage nextPage;
	private Controller controller;
	private StyledText sqlPanel;
	private Label lsql;
	private MigLayout layout;
	private Composite container;
	private DbDatasource dataSource;
	private Label lConnection;
	private Button tConnection;
	private boolean passNextPage;

	public DbPageTwo(String pageName, WizardPage nextPage,
			Controller controller, DbDatasource dataSource) {
		super(pageName);
		this.controller = controller;
		this.nextPage = nextPage;
		this.dataSource = dataSource;
		setTitle("Query Details");
		setDescription("Please enter the query you want to execute. Click the Test Button below to test the query.");
	}

	@Override
	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		setControl(container);
		int x_size = container.getSize().x;

		layout = new MigLayout("", "[][]", "[grow]");

		container.setLayout(layout);

		lsql = new Label(container, SWT.NONE);
		lsql.setText("Statement:");
		FontData[] fD = lsql.getFont().getFontData();
		fD[0].setHeight(x_size);
		lsql.setFont(new Font(parent.getDisplay(), fD[0]));

		sqlPanel = new StyledText(container, SWT.MULTI);
		sqlPanel.setEditable(true);
		sqlPanel.setText("");
		sqlPanel.setLayoutData("growx,growy,wrap");
		sqlPanel.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				getWizard().getContainer().updateButtons();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		sqlPanel.setText("Select To_Char(Res.Datares, 'yyyy') Ano, To_Char(Res.Datares, 'mm') Mes, To_Char(Res.Datares, 'dd') Dia, To_Char(Res.Datares, 'HH24') Hora, To_Char(Res.Datares, 'MI') Minuto,GBIQ.DIADOANO(res.datares) diadoano, niv.nivmed nivel,GBIQ.CosDiaAno(res.datares) cosd, GBIQ.SinDiaAno(res.datares) send, gbiq.hExp(niv.nivmed, o.id, 1) h, gbiq.tExp(res.datares, to_date(/*'periodoInicio.ToString(GBGlobal.DATE_FORMAT)', 'GBGlobal.DATE_FORMAT'*/'1999-01-01', 'yyyy-mm-dd'), 1) t, Extreal, Temperatura from /*resNome*/ extresistenciares res, /*instrNome*/  extresistencia instr, /*grupoNome*/ extresistenciagrupo grp,  niveissimple niv, obra o  Where Instr.Id = Res.Instrfixo And  Res.Datares >= To_Date(/*'periodoInicio.ToString(GBGlobal.DATE_FORMAT)', 'GBGlobal.DATE_FORMAT'*/ '1999-01-01', 'yyyy-mm-dd') And Res.Datares <= To_Date(/*'periodoFim.ToString(GBGlobal.DATE_FORMAT)', 'GBGlobal.DATE_FORMAT'*/ '2014-01-01', 'yyyy-mm-dd') And instr.id = /*ite.Value*/ 40660 and instr.grupoid = grp.id and grp.obra = /*obra*/ 284 and trunc(res.datares) = trunc(niv.datares) and niv.obra = grp.obra and o.id = grp.obra order by res.datares");

		lConnection = new Label(container, SWT.None);
		lConnection.setText("Test Query:");
		lConnection.setFont(new Font(parent.getDisplay(), fD[0]));

		tConnection = new Button(container, SWT.NONE);
		// tConnection.setLayoutData("wrap,growx");
		tConnection.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent event) {
				getWizard().getContainer().updateButtons();
				if (controller.testQuery(sqlPanel.getText())) {
					passNextPage = true;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		tConnection.setText("Test");
	}
	
	private void setDataSource(String query){
		for (int i = 0; i < controller.getColumnValues(query).length; i++){
			this.dataSource.setTimestampAttributeName(controller.getColumnValues(query)[i]);
		}
	}

	public boolean canFlipToNextPage() {
		// if (sqlPanel.getText() == "") {
		// return false;
		// }
		// if (!controller.testQuery(sqlPanel.getText())) {
		// return false;
		// }
		if (!this.passNextPage) {
			setMessage(
					"Please enter the query you want to execute. Click the Test Button below to test the query.",
					DialogPage.ERROR);
			return false;
		}
		setMessage("The query works. Click the next button to see the result of your query.");
		this.dataSource.setQuery(sqlPanel.getText());
		this.setDataSource(sqlPanel.getText());
		this.controller.setDataSource(dataSource);
		return true;

	}

	@Override
	public IWizardPage getNextPage() {
		return nextPage;
	}

	public boolean isPageComplete() {
		if(this.passNextPage){
			return true;
		} else {
		return false;
		}
	}
}
