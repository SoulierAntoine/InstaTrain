package fr.altoine.instatrain.models;

import com.google.gson.annotations.SerializedName;

import fr.altoine.instatrain.utils.Ways;

/**
 * Destination - InstaTrain
 * Created by Antoine on 02/10/2017.
 */

public class Destination extends Station {
    @SerializedName("way")
    private Ways mWay;
    public Ways getWay() {
        return mWay;
    }
}
