package fr.altoine.instatrain.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Line - InstaTrain
 * Created by Antoine on 01/10/2017.
 */

public class Line {
    // TODO : tmp
    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmDirections(String mDirections) {
        this.mDirections = mDirections;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

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

    public class MetroLine extends Line {}
    private List<MetroLine> mMetroLines;
    public List<MetroLine> getMetroLines() { return mMetroLines; }
    public void setMetroLines(List<MetroLine> metroLines) { mMetroLines = metroLines; }

    public class RerLine extends Line {}
    private List<RerLine> mRerLines;
    public List<RerLine> getRerLines() { return mRerLines; }
    public void setRerLines(List<RerLine> rerLines) { mRerLines = rerLines; }

    public class TramwayLine extends Line {}
    private List<TramwayLine> mTramwayLines;
    public List<TramwayLine> getTramwayLines() { return mTramwayLines; }
    public void setTramwayLines(List<TramwayLine> tramwayLines) { mTramwayLines = tramwayLines; }
}
