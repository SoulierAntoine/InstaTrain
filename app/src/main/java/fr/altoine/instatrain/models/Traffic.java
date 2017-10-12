package fr.altoine.instatrain.models;


import com.google.gson.annotations.SerializedName;

/**
 * Traffic - InstaTrain
 * Created by Antoine on 30/09/2017.
 */

public class Traffic {
    @SerializedName("line")
    private Line mLine;
    public String getTitle() {
        return mTitle;
    }

    @SerializedName("slug")
    private Slug mSlug;
    public Slug getSlug() {
        return mSlug;
    }

    @SerializedName("title")
    private String mTitle;
    public Line getLine() { return mLine; }

    @SerializedName("message")
    private String mMessage;
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
