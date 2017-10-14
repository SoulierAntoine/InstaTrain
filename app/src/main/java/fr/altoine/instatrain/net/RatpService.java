package fr.altoine.instatrain.net;


import fr.altoine.instatrain.models.Transport;
import fr.altoine.instatrain.models.Ways;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * RatpService - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */
public interface RatpService {
    @GET("traffic")
    Call<ResponseApi> getGlobalTraffic();

    @GET("traffic/{transport}")
    Call<ResponseApi> getTransportTraffic(@Path("transport") Transport transport);

    @GET("lines/{transport}")
    Call<ResponseApi> getTransportLines(@Path("transport") Transport transport);

    @GET("stations/{transport}/{line}")
    Call<ResponseApi> getStations(@Path("transport") Transport transport, @Path("line") String line);

    @GET("destinations/{transport}/{line}")
    Call<ResponseApi> getDestinations(@Path("transport") Transport transport, @Path("line") String line);

    @GET("schedules/{transport}/{line}/{station}/{way}")
    Call<ResponseApi> getSchedule(@Path("transport") Transport transport, @Path("line") String line, @Path("station") String station, @Path("way") Ways way);
}
