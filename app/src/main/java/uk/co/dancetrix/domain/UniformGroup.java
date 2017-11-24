package uk.co.dancetrix.domain;

import java.util.List;

public class UniformGroup {

    private String name;
    private List<UniformItem> items;

    public UniformGroup(String name, List<UniformItem> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<UniformItem> getItems() {
        return items;
    }

}
