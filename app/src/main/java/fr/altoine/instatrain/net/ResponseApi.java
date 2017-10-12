package fr.altoine.instatrain.net;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import fr.altoine.instatrain.models.Destination;
import fr.altoine.instatrain.models.Line;
import fr.altoine.instatrain.models.Schedule;
import fr.altoine.instatrain.models.Station;
import fr.altoine.instatrain.models.Traffic;

/**
 * ResponseApi - InstaTrain
 * Created by Antoine on 06/10/2017.
 */

public class ResponseApi {
    @SerializedName("result")
    private Result mResult;
    public Result getResult() { return mResult; }

    @SerializedName("_metadata")
    private Metadata mMetada;
    public Metadata getMetada() { return mMetada; }

    public class Result {
        @SerializedName("message")
        private String mMessage;
        public String getMessage() { return mMessage; }

        @SerializedName(value="metros", alternate={"rers", "tramwyas"})
        private Line mLines;

//        @SerializedName(value="metros", alternate={"rers", "tramwyas"})
        private List<Traffic> mTraffic;
        public List<Traffic> getTraffic() { return mTraffic; }
        public void setTraffic(List<Traffic> traffic) { mTraffic = traffic; }

        @SerializedName("stations")
        private Station mStation;

        @SerializedName("destinations")
        private Station mDestinations;

        @SerializedName("schedules")
        private Schedule mSchedules;
    }

    private class Metadata {
        @SerializedName("call")
        private String mCall;
        public String getCall() { return mCall; }

        @SerializedName("date")
        private String mDate;
        public String getDate() { return mDate; }

        @SerializedName("version")
        private int mVersion;
        public int getVersion() { return mVersion; }
    }
}
