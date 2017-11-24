package uk.co.dancetrix.domain;

import java.util.List;

public class UniformItem {

    private String key;
    private String name;
    private List<String> sizes;

    public UniformItem(String key, String name, List<String> sizes) {
        this.key = key;
        this.name = name;
        this.sizes = sizes;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public List<String> getSizes() {
        return sizes;
    }

}
