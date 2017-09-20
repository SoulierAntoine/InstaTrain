package fr.altoine.instatrain;

import fr.altoine.instatrain.net.ResponseTraffic;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * IMetroService - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */
public interface IMetroService {
    @GET("traffic")
    Call<ResponseTraffic> getTraffic();
}
