package classification;

public class ClassifierMeta {
	private String name;
	private String type;
	private boolean nominal;
	private boolean numerical;
	
	public ClassifierMeta(String name, String type, boolean nominal,
			boolean numerical) {
		this.name = name;
		this.type = type;
		this.nominal = nominal;
		this.numerical = numerical;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public boolean isNominal() {
		return nominal;
	}

	public boolean isNumerical() {
		return numerical;
	}
	
}
