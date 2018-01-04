package uk.co.dancetrix.domain;

import java.io.Serializable;

public class ClassDetails implements Serializable {

    private final String id;
    private final String path;
    private final String name;
    private final String datesLocation;
    private final String descriptionLocation;
    private final boolean allowIndividualBookings;

    public ClassDetails(String id,
                        String path,
                        String name,
                        String datesLocation,
                        String descriptionLocation,
                        boolean allowIndividualBookings) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.datesLocation = datesLocation;
        this.descriptionLocation = descriptionLocation;
        this.allowIndividualBookings = allowIndividualBookings;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getDatesLocation() {
        return datesLocation;
    }

    public String getDescriptionLocation() {
        return descriptionLocation;
    }

    public boolean isAllowIndividualBookings() {
        return allowIndividualBookings;
    }

}
