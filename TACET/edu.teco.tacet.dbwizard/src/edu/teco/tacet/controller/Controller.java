package edu.teco.tacet.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import edu.teco.tacet.dbbackend.DtbaseReader;
import edu.teco.tacet.meta.DbDatasource;
import edu.teco.tacet.track.Range;


public class Controller {
	private static Connection connection = null;
	private static PreparedStatement preparedStatement = null;
	@SuppressWarnings("unused")
	private PreparedStatement statement;
	@SuppressWarnings("unused")
	private ResultSet resultSet = null;


	
	private DbDatasource dataSource;
	private DtbaseReader reader;
	


	public Controller(DbDatasource dataSource) {
		this.dataSource = dataSource;
//		dataSource.setCoveredRangeStart(0);
//		dataSource.setCoveredRangeEnd(1000);
		this.reader = new DtbaseReader(dataSource);
	}
	
	public void setDataSource(DbDatasource dataSource){
		this.dataSource = dataSource;
	}

	public boolean testConnection(String username, String password,
			String host, String port, String sid, String driver) {
		Range range = new Range(0, 1000);
		if (reader.initialiseReader(host + ":" + port, sid, username, password, range, driver) == 0) return false;
		return true;
		
	}

	public String[] getColumnValues(String query) {
		dataSource.setQuery(query);
		reader.setDataSource(dataSource);
		return  this.reader.getColumnNames();
		
		
	}
	
	
	public String[] getColumnTypes(String query) {
		dataSource.setQuery(query);
		reader.setDataSource(dataSource);
		return  this.reader.getColumnTypes();
		
		
	}
	
	public boolean testQuery(String query){
		return reader.testQuery(query);
	}


//	private long getMin(String name) throws SQLException {
//		long min = Long.MIN_VALUE;
//	//	if (testConnection(username, password, host, port, sid)) {
////			statement = connection.createStatement();
////			resultSet = statement.executeQuery("select min(" + name
////					+ ") from )" + query + ")");
////			min = resultSet.getLong(1);
//		}
//		return min;
//	}
//
//	private long getMax(String name) throws SQLException {
//		long max = Long.MAX_VALUE;
//		//if (testConnection(username, password, host, port, sid)) {
//			statement = connection.prepareStatement("");
//			resultSet = statement.executeQuery("select max(" + name
//					+ ") from )" + query + ")");
//			max = resultSet.getLong(1);
//		}
//		return max;
//	}

	public void testPerparedStatement(String perparedQuery, String timestampName)
			throws SQLException {
	//	long min = getMin(timestampName);
	//	long max = getMax(timestampName);
		preparedStatement = connection.prepareStatement(perparedQuery);
		//preparedStatement.setString(1, String.valueOf(min));
	//	preparedStatement.setString(2, String.valueOf(max));
		resultSet = preparedStatement.executeQuery();
		
	}

	
}
