package fr.altoine.instatrain;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import fr.altoine.instatrain.adapters.TrafficAdapter;
import fr.altoine.instatrain.listeners.NoConnectionListener;
import fr.altoine.instatrain.listeners.RetryActionListener;
import fr.altoine.instatrain.loader.Callback;
import fr.altoine.instatrain.loader.RetrofitLoader;
import fr.altoine.instatrain.loader.RetrofitLoaderManager;
import fr.altoine.instatrain.models.Traffic;
import fr.altoine.instatrain.net.RatpService;
import fr.altoine.instatrain.net.ResponseApi;
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
        Callback<ResponseApi>,
        TrafficAdapter.TrafficAdapterOnClickHandler {



    // Constants ----------------------------------------------------------------------------------

    private final String TAG = TrafficFragment.class.getSimpleName();
    private NoConnectionListener mNoConnectionListener;



    // Network ------------------------------------------------------------------------------------

    private RatpService mTrafficService;



    // UI Components ------------------------------------------------------------------------------

    private RecyclerView mListTraffic;
    private ProgressBar mLoading;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    // Default constructor ------------------------------------------------------------------------

    public TrafficFragment() {}


    // Miscellaneous ------------------------------------------------------------------------------

    private TrafficAdapter mTrafficAdapter;



    // Listeners --------------------------------------------------------------------------


    // TODO: use string resources instead of string literal
    @Override
    public void onClick(Traffic transport) {
        new AlertDialog.Builder(getActivity())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setTitle(transport.getTitle() + " - ligne " + transport.getLine())
                .setMessage(transport.getMessage())
                // Set other dialog properties
                .create()
                .show();
    }

    @Override
    public void onFailure(Exception ex) {
        Log.v(TAG, ex.getMessage());
    }

    /**
     * Implementation of the success {@link Callback} method.
     * @param result containing the current traffic.
     */
    @Override
    public void onSuccess(ResponseApi result) {
        if (mLoading.getVisibility() == View.VISIBLE)
            mLoading.setVisibility(View.INVISIBLE);

        // Check if the fragment is attached to activity before getting Context from Activity
        if (result != null && isAdded()) {
            if (mTrafficAdapter != null) {
                mTrafficAdapter.reloadResponse(result);
            } else {
                // TODO: arbitrary chosen 5 columns, maybe change it according to the screen size
                GridLayoutManager layoutManager =
                        new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);

                mTrafficAdapter = new TrafficAdapter(getActivity(), result, this);
                mTrafficAdapter.shouldShowFooters(false);
                mTrafficAdapter.shouldShowHeadersForEmptySections(false);
                mTrafficAdapter.setLayoutManager(layoutManager);

                mListTraffic.setLayoutManager(layoutManager);
                mListTraffic.setAdapter(mTrafficAdapter);
            }

            if (mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(false);
            if (!mSwipeRefreshLayout.isEnabled())
                mSwipeRefreshLayout.setEnabled(true);

            mNoConnectionListener.hideNoConnection();

            if (mSwipeRefreshLayout.getVisibility() == View.INVISIBLE)
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        }
        else {
            mNoConnectionListener.showNoConnection();
        }
    }

    /**
     * Reload traffic
     * @param method name of the method to be called, for now, only one methid is called from MainActivity : loadTraffic
     * @param args args to be sent to the called method
     */
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
        Log.v(TAG, "2");
        View rootView = inflater.inflate(R.layout.fragment_traffic, container, false);
        mLoading = rootView.findViewById(R.id.pb_traffic_loading);

        // Load recycle view but do not attach an adapter as long as the network call is not finished
        mListTraffic = rootView.findViewById(R.id.rv_traffic);

        mSwipeRefreshLayout = rootView.findViewById(R.id.srl_swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadTraffic();
                }
            });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "3");
        // TODO: change Interface: we're getting the whole traffic here, not just the metro
        mTrafficService = RetrofitFactory.getApi(RatpService.class);
        loadTraffic();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "1");
        try {
            mNoConnectionListener = (NoConnectionListener) context;
        } catch (ClassCastException castException) {
            throw new InstantiationException("The activity that use this fragment must implement 'NoConnectionListener'.", castException);
        }
    }



    // Fragment's logic ---------------------------------------------------------------------------

    private void loadTraffic() {
        RetrofitLoaderManager.init(getLoaderManager(), Constants.Loader.ID_TRAFFIC_LOADER, new TrafficLoader(getActivity(), mTrafficService), this);
        showLoading();
    }

    private void showLoading() {
        mNoConnectionListener.hideNoConnection();

        if (mSwipeRefreshLayout.getVisibility() == View.VISIBLE)
            mSwipeRefreshLayout.setVisibility(View.INVISIBLE);
        if (mSwipeRefreshLayout.isEnabled())
            mSwipeRefreshLayout.setEnabled(false);
        if (mLoading.getVisibility() == View.INVISIBLE)
            mLoading.setVisibility(View.VISIBLE);
    }

    private static class TrafficLoader extends RetrofitLoader<ResponseApi, RatpService> {
        private final String TAG = TrafficLoader.class.getSimpleName();

    /**
     * Called by the abstract class {@link RetrofitLoader} and perform Retrofit request.
     * @param service instance.
     * @return current traffic.
     */
    @Override
    public ResponseApi call(RatpService service) {
        Call<ResponseApi> request = service.getGlobalTraffic();
        Response<ResponseApi> response = null;
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

        TrafficLoader(Context context, RatpService service) {
            super(context, service);
        }
    }
}
