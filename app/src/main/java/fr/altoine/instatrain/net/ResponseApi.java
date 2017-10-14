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


    /**
         [result] =>
             - /traffic contient 3 listes (1 / moyen de transport), chacune a pour nom le moyen de transport et contient une liste de Traffic
             - /lines contient 3 listes (1 / moyen de transport), chacune a pour nom le moyen de transport et contient une liste de Line

             - /traffic/{m|r|t} : a pour nom le moyen de transport, contient une liste de Traffic
             - /lines/{m|r|t} : a pour nom le moyen de transport, contient une liste de Line


             - /stations/{m|r|t}/{line} a pour nom "stations", contient une liste de Station
             - /destinations/{m|r|t}/{line} a pour nom "destinations", contient une liste de Destination
             - /schedules/{m|r|t}/{line}/{station}/{way} a pour nom "schedules", contient une liste de Schedule
     */

    public class Result {
        @SerializedName("message")
        private String mMessage;
        public String getMessage() { return mMessage; }

        private Traffic mTraffics;
        public Traffic getTraffics() { return mTraffics; }
        public void setTraffics(Traffic traffics) { mTraffics = traffics; }

        private Line mLines;
        public Line getLines() { return mLines; }
        public void setLines(Line lines) { mLines = lines; }

        @SerializedName("stations")
        private List<Station> mStation;
        public List<Station> getStation() { return mStation; }

        @SerializedName("destinations")
        private List<Destination> mDestinations;
        public List<Destination> getDestinations() { return mDestinations; }

        @SerializedName("schedules")
        private List<Schedule> mSchedule;
        public List<Schedule> getSchedule() { return mSchedule; }
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
