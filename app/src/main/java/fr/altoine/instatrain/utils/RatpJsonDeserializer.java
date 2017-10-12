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

import fr.altoine.instatrain.models.Traffic;
import fr.altoine.instatrain.net.ResponseApi;

/**
 * RatpJsonDeserializer - InstaTrain
 * Created by Antoine on 09/10/2017.
 */

public class RatpJsonDeserializer implements JsonDeserializer<ResponseApi.Result> {

    @Override
    public ResponseApi.Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject resultObject = json.getAsJsonObject();
        Gson g = new Gson();

        ResponseApi.Result result = g.fromJson(json, ResponseApi.Result.class);
        List<Traffic> traffics = null;

        String transportType = null;
        for (TransportType t : TransportType.values()) {
            if (resultObject.has(t.toString()))
                transportType = t.getLabel();
        }

        // If it's a list, just parse that from the JSON
        if (resultObject.has(transportType)) {
            if (resultObject.get(transportType).isJsonArray()) {
                traffics = g.fromJson(resultObject.get(transportType), new TypeToken<List<Traffic>>() {
                }.getType());
            } else {
                // Otherwise, parse the single traffic and add it to the list
                Traffic traffic = g.fromJson(resultObject.get(transportType), Traffic.class);
                traffics = new ArrayList<>();
                traffics.add(traffic);
            }
        }

        result.setTraffic(traffics);
        return result;
    }

    private enum TransportType {
        METROS("metros"),
        RERS("rers"),
        TRAMWAYS("tramways");

        private String mLabel;
        public String getLabel() { return mLabel; }
        TransportType(String label) { mLabel = label; }

        @Override
        public String toString() { return mLabel; }
    }
}
