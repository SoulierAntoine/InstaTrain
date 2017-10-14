package fr.altoine.instatrain.models;

import com.google.gson.annotations.SerializedName;

/**
 * Schedule - InstaTrain
 * Created by Antoine on 02/10/2017.
 */

public class Schedule {
    @SerializedName("message")
    private String mMessage;
    public String getMessage() {
        return mMessage;
    }

    @SerializedName("destination")
    private String mDestination;
    public String getDestination() { return mDestination; }
}
