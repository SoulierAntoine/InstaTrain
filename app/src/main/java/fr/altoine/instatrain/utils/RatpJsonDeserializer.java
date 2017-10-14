package fr.altoine.instatrain.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fr.altoine.instatrain.models.Line;
import fr.altoine.instatrain.models.Traffic;
import fr.altoine.instatrain.models.Transport;
import fr.altoine.instatrain.net.ResponseApi;


/**
 * RatpJsonDeserializer - InstaTrain
 * Created by Antoine on 09/10/2017.
 */

public class RatpJsonDeserializer implements JsonDeserializer<ResponseApi.Result> {

    /*
     * This method check if what's being deserialized is either an element named "metros", "rers" or "tramways"
     * It checks for each type of transport if it is a line or a traffic and add it to the result object
     */
    @Override
    public ResponseApi.Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject resultObject = json.getAsJsonObject();
        Gson g = new Gson();
        ResponseApi.Result result = g.fromJson(json, ResponseApi.Result.class);

        List<Transport> transportTypes = new ArrayList<>();
        for (Transport t : Transport.values()) {
            if (resultObject.has(t.toString()))
                transportTypes.add(t);
        }
        if (transportTypes.size() == 0)
            return result;

        Traffic traffics = new Traffic();
        Line lines = new Line();

        // TODO: check if with genericity, there's a clever way of achieving this
        for (Transport transportType : transportTypes) {
            switch (transportType) {
                case METROS:
                    List<Traffic.MetroTraffic> metroTraffics = g.fromJson(resultObject.get(transportType.toString()), new TypeToken<List<Traffic.MetroTraffic>>() {}.getType());
                    List<Line.MetroLine> metroLines = g.fromJson(resultObject.get(transportType.toString()), new TypeToken<List<Line.MetroLine>>() {}.getType());

                    if (metroTraffics.size() > 0 && metroTraffics.get(0).getLine() != null)
                        traffics.setMetroTraffics(metroTraffics);
                    if (metroLines.size() > 0 && metroLines.get(0).getCode() != null)
                        lines.setMetroLines(metroLines);
                    break;
                case RERS:
                    List<Traffic.RerTraffic> rerTraffics = g.fromJson(resultObject.get(transportType.toString()), new TypeToken<List<Traffic.RerTraffic>>() {}.getType());
                    List<Line.RerLine> rerLines = g.fromJson(resultObject.get(transportType.toString()), new TypeToken<List<Line.RerLine>>() {}.getType());

                    if (rerTraffics.size() > 0 && rerTraffics.get(0).getLine() != null)
                        traffics.setRerTraffics(rerTraffics);
                    if (rerLines.size() > 0 && rerLines.get(0).getCode() != null)
                        lines.setRerLines(rerLines);
                    break;
                case TRAMWAYS:
                    List<Traffic.TramwayTraffic> tramwayTraffics = g.fromJson(resultObject.get(transportType.toString()), new TypeToken<List<Traffic.TramwayTraffic>>() {}.getType());
                    List<Line.TramwayLine> tramwayLines = g.fromJson(resultObject.get(transportType.toString()), new TypeToken<List<Line.TramwayLine>>() {}.getType());

                    if (tramwayTraffics.size() > 0 && tramwayTraffics.get(0).getLine() != null)
                        traffics.setTramwayTraffics(tramwayTraffics);
                    if (tramwayLines.size() > 0 && tramwayLines.get(0).getCode() != null)
                        lines.setTramwayLines(tramwayLines);
                    break;
                default:
                    return null;
            }
        }

        if (traffics.getMetroTraffics() != null || traffics.getRerTraffics() != null || traffics.getTramwayTraffics() != null)
            result.setTraffics(traffics);
        if (lines.getMetroLines() != null || lines.getRerLines() != null || lines.getTramwayLines() != null)
            result.setLines(lines);

        return result;
    }
}
