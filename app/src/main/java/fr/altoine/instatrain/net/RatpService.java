package fr.altoine.instatrain.net;



import fr.altoine.instatrain.utils.Transport;
import fr.altoine.instatrain.utils.Ways;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * RatpService - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */
public interface RatpService {
    @GET("traffic")
    Observable<ResponseApi> getGlobalTraffic();
//    Call<ResponseApi> getGlobalTraffic();

    @GET("traffic/{transport}")
    Observable<ResponseApi> getTransportTraffic(@Path("transport") Transport transport);

    @GET("lines/{transport}")
    Observable<ResponseApi> getTransportLines(@Path("transport") Transport transport);

    @GET("stations/{transport}/{line}")
    Observable<ResponseApi> getStations(@Path("transport") Transport transport, @Path("line") String line);

    @GET("destinations/{transport}/{line}")
    Observable<ResponseApi> getDestinations(@Path("transport") Transport transport, @Path("line") String line);

    @GET("schedules/{transport}/{line}/{station}/{way}")
    Observable<ResponseApi> getSchedules(@Path("transport") Transport transport, @Path("line") String line, @Path("station") String station, @Path("way") Ways way);
}
