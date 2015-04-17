package emulation;

import java.util.ArrayList;

import net.sourceforge.openforecast.DataPoint;

public class TacetDataPoint implements DataPoint {

	private long timestamp;
	private double dependentValue;
	private ArrayList<Datum> independentValues;
	
	public TacetDataPoint(long timestamp, double dependentValue) {
		this.timestamp = timestamp;
		this.dependentValue = (double)dependentValue;
		independentValues = new ArrayList<Datum>();
	}
	
	public TacetDataPoint() {
		independentValues = new ArrayList<Datum>();
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	@Override
	public boolean equals(DataPoint arg0) {
		return false;
	}

	@Override
	public double getDependentValue() {
		return dependentValue;
	}

	@Override
	public double getIndependentValue(String arg0) {
		for(Datum d: independentValues) {
			if (d.getTrackID().equals(arg0)) {
				return d.getValue();
			}
		}
		return 0;
	}

	@Override
	public String[] getIndependentVariableNames() {
		String[] names = new String[independentValues.size()];
		int i = 0;
		for(Datum d : independentValues) {
			names[i] = d.getTrackID();
			i++;
		}
		return names;
	}

	@Override
	public void setDependentValue(double arg0) {
		dependentValue = arg0;		
	}

	@Override
	public void setIndependentValue(String arg0, double arg1) {
		Datum independentValue = new Datum(Long.parseLong(arg0), arg1);
		independentValues.add(independentValue);
	}

}
