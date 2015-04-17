package edu.teco.tacet.util;

public class TableEntry {
    public enum ColumnType { TIMESTAMP, SENSOR, ANNOTATION }
    
	private String name = "";
	private int typeIndex;
    private ColumnType type;

    public TableEntry(ColumnType type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

	public String getName() {
        return name;
    }

    public void setName(String name) {
		this.name = name;
	}
}
