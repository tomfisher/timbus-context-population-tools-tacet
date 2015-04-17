package edu.teco.tacet.writer.csv;

import edu.teco.tacet.track.Range;

public class CsvExportDataSource {

	private Range globalRange;
	private String seperator = ";";
	private String filePath = "test.csv";
	private String lineSeparator = "\n";
	public CsvExportDataSource(Range globalRange, String seperator,
			String filePath, String lineSeparator) {
		super();
		this.globalRange = globalRange;
		this.seperator = seperator;
		this.filePath = filePath;
		this.lineSeparator = lineSeparator;
	}
	public Range getGlobalRange() {
		return globalRange;
	}
	public void setGlobalRange(Range globalRange) {
		this.globalRange = globalRange;
	}
	public String getSeperator() {
		return seperator;
	}
	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getLineSeparator() {
		return lineSeparator;
	}
	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}
	
}
