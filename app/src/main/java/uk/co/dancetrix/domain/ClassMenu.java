package uk.co.dancetrix.domain;

import java.util.List;

public class ClassMenu {

    private final String name;
    private final List<ClassMenu> children;
    private final ClassDetails classDetails;

    public ClassMenu(String name, List<ClassMenu> children) {
        this.name = name;
        this.children = children;
        this.classDetails = null;
    }

    public ClassMenu(String name, ClassDetails classDetails) {
        this.name = name;
        this.children = null;
        this.classDetails = classDetails;
    }

    public String getName() {
        return name;
    }

    public List<ClassMenu> getChildren() {
        return children;
    }

    public ClassDetails getClassDetails() {
        return classDetails;
    }

}
