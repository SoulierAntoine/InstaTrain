package fr.altoine.instatrain.models;

/**
<<<<<<< HEAD
 * Destination - InstaTrain
 * Created by Antoine on 02/10/2017.
 */

public class Destination extends Station {
    private Ways mWay;

    public Ways getWay() {
        return mWay;
    }

    private enum Ways {
        A("A"),
        R("R");

        private String mLabel;
        Ways(String label) { mLabel = label; }

        @Override
        public String toString() {
            return mLabel;
        }
    }
}
