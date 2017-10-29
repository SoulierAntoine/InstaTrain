package fr.altoine.instatrain.models;

import com.google.gson.annotations.SerializedName;

/**
 * Line - InstaTrain
 * Created by Antoine on 01/10/2017.
 */

public class Line {
    @SerializedName("code")
    private String mCode;
    public String getCode() { return mCode; }

    @SerializedName("name")
    private String mName;
    public String getName() { return mName; }

    @SerializedName("directions")
    private String mDirections;
    public String getDirections() { return mDirections; }

    @SerializedName("id")
    private String mId;
    public String getId() { return mId; }

    @Override
    public String toString() { return mCode; }
}
