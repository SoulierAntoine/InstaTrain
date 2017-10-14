package fr.altoine.instatrain.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Traffic - InstaTrain
 * Created by Antoine on 30/09/2017.
 */

public class Traffic {
    @SerializedName("line")
    private String mLine;
    public String getLine() { return mLine; }

    @SerializedName("slug")
    private String mSlug;
    public String getSlug() { return mSlug; }

    @SerializedName("title")
    private String mTitle;
    public String getTitle() {
        return mTitle;
    }

    @SerializedName("message")
    private String mMessage;
    public String getMessage() {
        return mMessage;
    }

    public List<Traffic> getAllTraffic() {
        List <Traffic> traffics = new ArrayList<>();
        if (mMetroTraffics != null && mMetroTraffics.size() > 0)
            traffics.addAll(mMetroTraffics);

        if (mRerTraffics != null && mRerTraffics.size() > 0)
            traffics.addAll(mRerTraffics);

        if (mTramwayTraffics != null && mTramwayTraffics.size() > 0)
            traffics.addAll(mTramwayTraffics);

        return traffics;
    }


    public class MetroTraffic extends Traffic {}

    private List<MetroTraffic> mMetroTraffics;
    public List<MetroTraffic> getMetroTraffics() { return mMetroTraffics; }
    public void setMetroTraffics(List<MetroTraffic> metroTraffics) { mMetroTraffics = metroTraffics; }


    public class RerTraffic extends Traffic {}

    private List<RerTraffic> mRerTraffics;
    public List<RerTraffic> getRerTraffics() { return mRerTraffics; }
    public void setRerTraffics(List<RerTraffic> rerTraffics) { mRerTraffics = rerTraffics; }


    public class TramwayTraffic extends Traffic {}

    private List<TramwayTraffic> mTramwayTraffics;
    public List<TramwayTraffic> getTramwayTraffics() { return mTramwayTraffics; }
    public void setTramwayTraffics(List<TramwayTraffic> tramwayTraffics) { mTramwayTraffics = tramwayTraffics; }
}
