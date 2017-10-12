package fr.altoine.instatrain.net;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * ITrafficService - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */
public interface ITrafficService {
    @GET("traffic")
    Call<ResponseTraffic> getTraffic();

    @GET("traffic/metros/8")
    Call<ResponseApi> getTrafficc();
}
