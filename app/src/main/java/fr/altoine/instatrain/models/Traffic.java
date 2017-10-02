package fr.altoine.instatrain.models;

/**
 * Traffic - InstaTrain
 * Created by Antoine on 30/09/2017.
 */

public class Traffic {
    private Line mLine;
    private Slug mSlug;
    private String mTitle;
    private String mMessage;

    public Line getLine() {
        return mLine;
    }

    public Slug getSlug() {
        return mSlug;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getMessage() {
        return mMessage;
    }

    private enum Slug {
        NORMAL("normal"),
        NORMAL_TRAV("normal_trav"),
        ALERTE("alerte"),
        CRITIQUE("critique");

        private String mLabel;
        Slug(String label) { mLabel = label; }

        @Override
        public String toString() {
            return mLabel;
        }
    }
}
