package fr.altoine.instatrain.utils;

/**
 * Transport - Instatrain
 * Created by Antoine on 11/10/2017.
 */

public enum Transport {
    METROS("metros"),
    RERS("rers"),
    TRAMWAYS("tramways");

    private String mLabel;
    public String getLabel() { return mLabel; }
    Transport(String label) { mLabel = label; }

    @Override
    public String toString() { return mLabel; }
}
