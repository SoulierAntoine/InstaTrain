package fr.altoine.instatrain.models;

import com.google.gson.annotations.SerializedName;

/**
 * Station - InstaTrain
 * Created by Antoine on 01/10/2017.
 */

public class Station {
    @SerializedName("slug")
    private String mSlug;
    public String getSlug() { return mSlug; }

    @SerializedName("name")
    private String mName;
    public String getName() {
        return mName;
    }
}
