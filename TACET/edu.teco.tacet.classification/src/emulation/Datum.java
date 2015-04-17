package emulation;

public class Datum {
	
	private String trackID;
	private double value;
	
	public Datum(long trackID, double value) {
		this.trackID = Long.toString(trackID);
		this.value = value;
	}
	
	public String getTrackID() {
		return trackID;
	}
	
	public double getValue() {
		return value;
	}
}
