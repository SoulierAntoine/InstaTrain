package fr.altoine.instatrain.utils;

import java.util.HashMap;
import java.util.Map;

import fr.altoine.instatrain.net.ResponseTraffic;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitFactory - InstaTrain
 * Created by soulierantoine on 07/08/2017
 */
public class RetrofitFactory {
    private static Map<String, Object> mApiInstances = new HashMap<>();
    private static Retrofit mRetrofit;


    // Is executed right before any inner method is called in another part of the code.
    static {
        mRetrofit = getInstance();
    }

    /**
     * Use singleton pattern to get Retrofit instances, so that it can be shared application wise.
     * See: https://stackoverflow.com/a/21250503
     * @return An instance of Retrofit.
     */
    private static Retrofit getInstance() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.API_URL)
                    .build();
        }

        return mRetrofit;
    }

    /**
     * Get Retrofit API Instances (such as {@link ResponseTraffic}.
     * The instances are stored in a map.
     * And if one is required several times, it is only created once.
     * See: https://stackoverflow.com/a/25277104
     * @param apiClass the Class object representing the API instance.
     * @return Return an implementation of the API class created by Retrofit.
     */
    public static <T> T getApi(Class<T> apiClass) {
        T client;

        if ((client = (T) mApiInstances.get(apiClass.getCanonicalName())) != null)
            return client;

        if (mRetrofit == null)
            mRetrofit = getInstance();

        client = mRetrofit.create(apiClass);
        mApiInstances.put(apiClass.getCanonicalName(), client);
        return client;
    }
}
