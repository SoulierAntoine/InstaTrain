package fr.altoine.instatrain;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import fr.altoine.instatrain.listeners.NoConnectionListener;
import fr.altoine.instatrain.listeners.RetryActionListener;
import fr.altoine.instatrain.loader.Callback;
import fr.altoine.instatrain.loader.RetrofitLoader;
import fr.altoine.instatrain.loader.RetrofitLoaderManager;
import fr.altoine.instatrain.net.Traffic;
import fr.altoine.instatrain.utils.Constants;
import fr.altoine.instatrain.utils.RetrofitFactory;
import retrofit2.Call;
import retrofit2.Response;


/**
 * TrafficFragment - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */

public class TrafficFragment extends Fragment implements
        RetryActionListener,
        Callback<Traffic> {



    // Constants ----------------------------------------------------------------------------------

    private final String TAG = TrafficFragment.class.getSimpleName();
    private NoConnectionListener mNoConnectionListener;



    // Network ------------------------------------------------------------------------------------

    private IMetroService mMetroService;



    // UI Components ------------------------------------------------------------------------------

    private RecyclerView mListTraffic;
    private ProgressBar mLoading;



    // Default constructor ------------------------------------------------------------------------

    public TrafficFragment() {}



    // Callback listener --------------------------------------------------------------------------

    @Override
    public void onFailure(Exception ex) {
        Log.v(TAG, ex.getMessage());
    }

    /**
     * Implementation of the success {@link Callback} method.
     * @param result containing the current traffic.
     */
    @Override
    public void onSuccess(Traffic result) {
        mLoading.setVisibility(View.INVISIBLE);

        if (result != null) {
            mNoConnectionListener.hideNoConnection();
            mListTraffic.setVisibility(View.VISIBLE);
            // TODO: display stuff
        }
        else {
            mNoConnectionListener.showNoConnection();
        }
    }

    @Override
    public void retryAction(String method, Object... args) {
        Method m;
        try {
            m = TrafficFragment.class.getDeclaredMethod(method);
            // No need to check if the method is accessible for we call it from the internal object
            m.invoke(this, args);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    // Fragment lifecycle -------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_traffic, container, false);

        mLoading = (ProgressBar) rootView.findViewById(R.id.pb_traffic_loading);

        TrafficAdapter adapter = new TrafficAdapter(rootView.getContext());

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);

        mListTraffic = (RecyclerView) rootView.findViewById(R.id.rv_traffic);
        mListTraffic.setLayoutManager(layoutManager);
        mListTraffic.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMetroService = RetrofitFactory.getApi(IMetroService.class);

        loadTraffic();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mNoConnectionListener = (NoConnectionListener) context;
        } catch (ClassCastException castException) {
            throw new InstantiationException("The activity that use this fragment must implement 'NoConnectionListener'.", castException);
        }
    }



    // Fragment's logic ---------------------------------------------------------------------------

    private void loadTraffic() {
        RetrofitLoaderManager.init(getLoaderManager(), Constants.Loader.ID_TRAFFIC_LOADER, new TrafficLoader(getActivity(), mMetroService), this);
        showLoading();
    }

    private void showLoading() {
        mNoConnectionListener.hideNoConnection();
        mLoading.setVisibility(View.VISIBLE);
        mListTraffic.setVisibility(View.INVISIBLE);
    }

    private void hideLoading() {
        mLoading.setVisibility(View.INVISIBLE);
        mListTraffic.setVisibility(View.VISIBLE);
    }

    private static class TrafficLoader extends RetrofitLoader<Traffic, IMetroService> {
        private final String TAG = TrafficLoader.class.getSimpleName();

        TrafficLoader(Context context, IMetroService service) {
            super(context, service);
        }


        /**
         * Called by the abstract class {@link RetrofitLoader} and perform Retrofit request.
         * @param service instance.
         * @return current traffic.
         */
        @Override
        public Traffic call(IMetroService service) {
            Call<Traffic> request = service.getTraffic();
            Response<Traffic> response = null;
            try {
                response = request.execute();
            } catch (IOException e) {
                Log.v(TAG, "Exception in call method : no internet connection.");
            }

            if (response != null && response.isSuccessful())
                return response.body();
            else
                return null;
        }
    }
}
