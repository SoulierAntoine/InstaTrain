package fr.altoine.instatrain.net;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import fr.altoine.instatrain.loader.RetrofitLoader;
import fr.altoine.instatrain.models.Transport;
import retrofit2.Call;
import retrofit2.Response;

/**
 * RatpLoader - InstaTrain
 * Created by Antoine on 17/10/2017.
 */

public class RatpLoader {
    // TODO: avoid creating 3 different classes that do the exact same thing (just the method is different)
    public static class StationLoader extends RetrofitLoader<ResponseApi, RatpService> {
        private final Transport mTransport;
        private final String mLine;

        public StationLoader(Context context, RatpService service, Transport transport, String line) {
            super(context, service);
            mTransport = transport;
            mLine = line;
        }

        @Override
        public ResponseApi call(RatpService service) {
            Call<ResponseApi> request = service.getStations(mTransport, mLine);
            Response<ResponseApi> response = null;
            try {
                response = request.execute();
            } catch (IOException e) {
                Log.v("ASOU", "Exception in call method : no internet connection.");
            }

            if (response != null && response.isSuccessful())
                return response.body();
            else
                return null;
        }
    }

    public static class LineLoader extends RetrofitLoader<ResponseApi, RatpService> {
        private final Transport mTransport;

        @Override
        public ResponseApi call(RatpService service) {
            Call<ResponseApi> request = service.getTransportLines(mTransport);
            Response<ResponseApi> response = null;
            try {
                response = request.execute();
            } catch (IOException e) {
                Log.v("ASOU", "Exception in call method : no internet connection.");
            }

            if (response != null && response.isSuccessful())
                return response.body();
            else
                return null;
        }

        public LineLoader(Context context, RatpService service, Transport t) {
            super(context, service);
            mTransport = t;
        }
    }

    public static class DestinationLoader extends RetrofitLoader<ResponseApi, RatpService> {
        private final Transport mTransport;
        private final String mLine;


        @Override
        public ResponseApi call(RatpService service) {
            Call<ResponseApi> request = service.getDestinations(mTransport, mLine);
            Response<ResponseApi> response = null;
            try {
                response = request.execute();
            } catch (IOException e) {
                Log.v("ASOU", "Exception in call method : no internet connection.");
            }

            if (response != null && response.isSuccessful())
                return response.body();
            else
                return null;
        }

        public DestinationLoader(Context context, RatpService service, Transport transport, String line) {
            super(context, service);
            mTransport = transport;
            mLine = line;
        }
    }
}
