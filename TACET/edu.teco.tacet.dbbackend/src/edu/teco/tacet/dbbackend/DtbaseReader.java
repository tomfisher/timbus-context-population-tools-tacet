package edu.teco.tacet.dbbackend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



import edu.teco.tacet.meta.DbColumnDescription;
import edu.teco.tacet.meta.DbDatasource;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;

public class DtbaseReader implements Reader {

	private Connection connection = null;
	static PreparedStatement statement = null;
	DbDatasource dbDatasource;
	ResultSet resultSet = null;

	/**
	 * @param args
	 */
	public DtbaseReader(DbDatasource dbDatasource) {
		this.dbDatasource = dbDatasource;
	}

	public int initialiseReader(String url, String sid, String user,
			String password, Range range, String driver) {
		try {

			Class.forName(driver);

		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
		try {
			String text = "";
			if (driver.contains("mysql")) {
				text = "jdbc:" + "mysql" + "://" + url;
			} else if (driver.contains("oracle")) {
				text = "jdbc:" + "oracle:thin:@" + "//" + url + "/" + sid;
			}
			connection = DriverManager.getConnection(text, user, password);
		} catch (SQLException se) {

			se.printStackTrace();
			return 0;
		}
		return 1;
	}

	private void freeResources(ResultSet resultSet, Statement statement,
			String message) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException ex) {
				System.out.println(message + ex.getMessage());
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ex) {
				System.out.println(message + ex.getMessage());
			}
		}
	}

	@Override
	public Iterable<Datum> readSensorData(long trackId, Range range,
			long resolution) {
		ArrayList<Datum> sensorData = new ArrayList<Datum>();
		try {

			// Allocate the Resources for the query
			statement = connection.prepareStatement(dbDatasource.getQuery());
			resultSet = statement.executeQuery();

			String attributeName = "";
			for (DbColumnDescription colDesc : dbDatasource
					.getColumnDescriptions()) {
				if (colDesc.getTimeseriesId() == trackId) {
					attributeName = colDesc.getAttributeName();
				}
			}

			int columnIndex = 0;
			long timeStamp = this.getTimestamp(range, resolution);
			while (resultSet.next()) {
				if (timeStamp == resultSet.getLong(columnIndex)) {
					timeStamp = this.getTimestamp(range, resolution);
					double value = Double.valueOf(resultSet
							.getString(attributeName));
					Datum datum = new Datum(timeStamp, value);
					sensorData.add(datum);
				}
				columnIndex++;
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			freeResources(resultSet, statement, ex.getMessage());
		}

		return sensorData;
	}

	@Override
	public Iterable<Annotation> readAnnotations(long trackId, Range range) {
		ArrayList<Annotation> annotationData = new ArrayList<Annotation>();
		try {

			// Allocate the Resources for the query
			statement = connection.prepareStatement(dbDatasource.getQuery());
			resultSet = statement.executeQuery();

			String attributeName = "";
			for (DbColumnDescription colDesc : dbDatasource
					.getColumnDescriptions()) {
				if (colDesc.getTimeseriesId() == trackId) {
					attributeName = colDesc.getAttributeName();
				}
			}

			while (resultSet.next()) {

				long startRange = dbDatasource.getCoveredRangeStart();
				long endRange = dbDatasource.getCoveredRangeStart();
				Range range_1 = new Range(startRange, endRange);
				String label = resultSet.getString(attributeName);
				Annotation annotation = new Annotation(range_1, label);
				annotationData.add(annotation);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			freeResources(resultSet, statement, ex.getMessage());
		}

		return annotationData;
	}

	@Override
	public long getSourceId() {
		return this.dbDatasource.getId();
	}

	@Override
	public double getAverageDistance(long trackId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Range getCoveredRange(long trackId) {
		Range range = null;
		long startRange = dbDatasource.getCoveredRangeStart();
		long endRange = dbDatasource.getCoveredRangeEnd();
		range = new Range(startRange, endRange);
		return range;
	}

	protected long getTimestamp(Range range, long resolution) {
		long milliseconds = range.getStart();
		milliseconds = milliseconds + resolution;

		return milliseconds;

	}

	public String[] getColumnNames() {
		String[] columns = null;
		try {
			statement = connection.prepareStatement(dbDatasource.getQuery());
			ResultSet rs = statement.executeQuery();

			if (rs != null) {
				java.sql.ResultSetMetaData rsmd = rs.getMetaData();
				columns = new String[rsmd.getColumnCount()];
				for (int i = 1; i < (rsmd.getColumnCount()); i++) {
					columns[i] = rsmd.getColumnName(i);
				}
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			freeResources(resultSet, statement, ex.getMessage());
		}
		return columns;
	}
	
	
	public String[] getColumnTypes() {
		String[] columns = null;
		try {
			statement = connection.prepareStatement(dbDatasource.getQuery());
			ResultSet rs = statement.executeQuery();

			if (rs != null) {
				java.sql.ResultSetMetaData rsmd = rs.getMetaData();
				columns = new String[rsmd.getColumnCount()];
				for (int i = 1; i < (rsmd.getColumnCount()); i++) {
					columns[i] = rsmd.getColumnTypeName(i);
				}
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			freeResources(resultSet, statement, ex.getMessage());
		}
		return columns;
	}

	public void setDataSource(DbDatasource dbDatasource) {
		this.dbDatasource = dbDatasource;
	}

	public boolean testQuery(String query) {
		try {
			statement = connection.prepareStatement(query);
			@SuppressWarnings("unused")
			ResultSet rs = statement.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
