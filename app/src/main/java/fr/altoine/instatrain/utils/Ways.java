package fr.altoine.instatrain.utils;

/**
 * Way - Instatrain
 * Created by Antoine on 11/10/2017.
 */

public enum Ways {
    A("A"),
    R("R");

    private String mLabel;
    Ways(String label) { mLabel = label; }

    @Override
    public String toString() {
        return mLabel;
    }
}