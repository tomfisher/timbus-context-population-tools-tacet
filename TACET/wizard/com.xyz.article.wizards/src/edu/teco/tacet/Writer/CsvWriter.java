package edu.teco.tacet.Writer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.teco.tacet.exportManager.CSVExportPageProvider;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.Track;

public class CsvWriter {


	private String seperator = ""; 
	private String filePath = "";
	private ArrayList<String> ordering = new ArrayList<String>();
	CSVExportPageProvider csvProvider;

	
	public CsvWriter(CSVExportPageProvider csvProvider){
		this.csvProvider = csvProvider;
		seperator = csvProvider.getPageOne().getSeperator();
		filePath = csvProvider.getPageOne().getNewPath();
		this.ordering = csvProvider.getPageTwo().getTableEntries();
		this.writeCsv(filePath);
	}

	public void writeCsv(String fileName){
		try
		{
		    FileWriter writer = new FileWriter(fileName + "test" + ".csv");
		    for ( int i = 0 ; i < ordering.size(); i++){
		    	for ( @SuppressWarnings("rawtypes") Track track : csvProvider.annotTracks )
		    		if( ordering.get(i).equals(track.getId())){
		    				
		    				writer.append(track.getData(new Range(0, 10000)).toString());
		    				writer.append(seperator);
		    			
		    			break;
		    		}
		    	}
		    for ( int i = 0 ; i < ordering.size(); i++){
		    	for ( @SuppressWarnings("rawtypes") Track track : csvProvider.sensorTracks )
		    		if( ordering.get(i).equals(track.getId())){
		    				
		    				writer.append(track.getData(new Range(0, 10000)).toString());
		    				writer.append(seperator);
		    			
		    			break;
		    		}
		    	} 
		    writer.flush();
		    writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
	}
}
