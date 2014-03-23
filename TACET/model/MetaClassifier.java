package squirrel.model;

public class MetaClassifier {

    private String name;
    private String className;
    private String type;

    public MetaClassifier(String name, String className, String type) {
        this.name = name;
        this.className = className;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getType() {
        return type;
    }
}
