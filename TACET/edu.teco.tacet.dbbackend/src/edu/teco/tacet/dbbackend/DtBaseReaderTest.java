package edu.teco.tacet.dbbackend;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.teco.tacet.meta.Column;
import edu.teco.tacet.meta.DbColumnDescription;
import edu.teco.tacet.meta.DbDatasource;
import edu.teco.tacet.meta.MetaFactory;

import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;

public class DtBaseReaderTest {
	private DbDatasource dataSource;
	private ArrayList<Datum> data1;
	@SuppressWarnings("unused")
	private ArrayList<Annotation> data2;
	
	@Before
	public void init(){
		data1 = new ArrayList<>();
		data2 = new ArrayList<>();
		
		dataSource = MetaFactory.eINSTANCE.createDbDatasource();

		dataSource.setLocation("134.191.240.68:11521");
		dataSource.setLogin("gb_dbbe1");
		dataSource.setPassword("gb_dbbe1");
		dataSource.setSid("NOACHI");
		dataSource.setCoveredRangeStart(0);
		dataSource.setCoveredRangeEnd(1000);
		dataSource.setQuery("Select To_Char(Res.Datares, 'yyyy') Ano, To_Char(Res.Datares, 'mm') Mes,"
						+ " To_Char(Res.Datares, 'dd') Dia, To_Char(Res.Datares, 'HH24') Hora, To_Char(Res.Datares, 'MI')"
						+ " Minuto,GBIQ.DIADOANO(res.datares) diadoano, niv.nivmed nivel,GBIQ.CosDiaAno(res.datares) cosd,"
						+ " GBIQ.SinDiaAno(res.datares) send, gbiq.hExp(niv.nivmed, o.id, 1) h, gbiq.tExp(res.datares, to_date(/*'"
						+ "periodoInicio.ToString(GBGlobal.DATE_FORMAT)"
						+ "', '"
						+ "GBGlobal.DATE_FORMAT"
						+ "'*/'1999-01-01', 'yyyy-mm-dd'), 1) t, Extreal, "
						+ "Temperatura from /*"
						+ "resNome"
						+ "*/ extresistenciares res, /*"
						+ "instrNome"
						+ "*/  extresistencia instr, /*"
						+ "grupoNome"
						+ "*/ extresistenciagrupo grp,  niveissimple niv, obra o  Where Instr.Id = Res.Instrfixo And  Res.Datares >= To_Date(/*'"
						+ "periodoInicio.ToString(GBGlobal.DATE_FORMAT)"
						+ "', '"
						+ "GBGlobal.DATE_FORMAT"
						+ "'*/ '1999-01-01', 'yyyy-mm-dd') And Res.Datares <= To_Date(/*'"
						+ "periodoFim.ToString(GBGlobal.DATE_FORMAT)"
						+ "', '"
						+ "GBGlobal.DATE_FORMAT"
						+ "'*/ '2014-01-01', 'yyyy-mm-dd') And instr.id = /*"
						+ "ite.Value"
						+ "*/ 40660 and instr.grupoid = grp.id and grp.obra = /*"
						+ "obra*/ 284 and trunc(res.datares) = trunc(niv.datares) and niv.obra = grp.obra and o.id = grp.obra order by res.datares");
		dataSource.setJdbcDriver("oracle.jdbc.OracleDriver");
		
		DbColumnDescription dbColumnDescription_1 = MetaFactory.eINSTANCE.createDbColumnDescription();
		dbColumnDescription_1.setTimeseriesId(Column.SENSOR_DATA_VALUE);
		
		DbColumnDescription dbColumnDescription_2 = MetaFactory.eINSTANCE.createDbColumnDescription();
		dbColumnDescription_2.setTimeseriesId(Column.ANNOTATION_VALUE);
		
		dataSource.getColumnDescriptions().add(dbColumnDescription_1);
		dataSource.getColumnDescriptions().add(dbColumnDescription_2);
	}

	@Test
	public void test() {
		

		DtbaseReader dtbaseReader = new DtbaseReader(dataSource);
		dtbaseReader.testQuery(dataSource.getQuery());
		String columns[] = dtbaseReader.getColumnNames();
		for ( String column : columns){
			DbColumnDescription dbcd = MetaFactory.eINSTANCE.createDbColumnDescription();
			dbcd.setAttributeName(column);
			dbcd.setTimeseriesId(0);
			dataSource.getColumnDescriptions().add(dbcd);
		}
		Range range = new Range(dataSource.getCoveredRangeStart(),
				dataSource.getCoveredRangeEnd());
		data1 = (ArrayList<Datum>) dtbaseReader.readSensorData(0, range, 1);
		Assert.assertNotNull(data1);
		
		data2 = (ArrayList<Annotation>) dtbaseReader.readAnnotations(1,range);
		Assert.assertNotNull(data1);
	}
}
