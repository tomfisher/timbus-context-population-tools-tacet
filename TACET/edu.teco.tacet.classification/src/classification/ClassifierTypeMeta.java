package classification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassifierTypeMeta {
	private String name;
	private List<ClassifierMeta> classifiers;
	private boolean hasNominal = false;
	private boolean hasNumerical = false;

	public ClassifierTypeMeta(String name) {
		this.name = name;
		this.classifiers = new ArrayList<>();
	}
	
	public ClassifierTypeMeta(String name, ClassifierMeta[] classifiers) {
		this.name = name;
		this.classifiers = Arrays.asList(classifiers);
		for(int i = 0; i < classifiers.length; i++) {
			if (classifiers[i].isNominal()) {
				hasNominal = true;
			}
			if (classifiers[i].isNumerical()) {
				hasNumerical = true;
			}
		}
	}

	public String getName() {
		return name;
	}
	
	public void addClassifier(ClassifierMeta classifierMeta) {
		this.classifiers.add(classifierMeta);
		if (classifierMeta.isNominal())
			hasNominal = true;
		if (classifierMeta.isNumerical())
			hasNumerical = true;
	}

	public List<ClassifierMeta> getClassifiers() {
		return classifiers;
	}

	public boolean hasNominal() {
		return hasNominal;
	}

	public boolean hasNumerical() {
		return hasNumerical;
	}
}
