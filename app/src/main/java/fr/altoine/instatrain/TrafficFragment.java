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

import fr.altoine.instatrain.listeners.NoConnectionListener;
import fr.altoine.instatrain.listeners.RetryActionListener;
import fr.altoine.instatrain.loader.Callback;
import fr.altoine.instatrain.loader.RetrofitLoader;
import fr.altoine.instatrain.loader.RetrofitLoaderManager;
import fr.altoine.instatrain.net.ResponseTraffic;
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
        Callback<ResponseTraffic>,
        TrafficAdapter.TrafficAdapterOnClickHandler {



    // Constants ----------------------------------------------------------------------------------

    private final String TAG = TrafficFragment.class.getSimpleName();
    private NoConnectionListener mNoConnectionListener;



    // Network ------------------------------------------------------------------------------------

    private IMetroService mMetroService;



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
    public void onClick(ResponseTraffic.Result.Transports transport) {
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
    public void onSuccess(ResponseTraffic result) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (!mSwipeRefreshLayout.isEnabled())
            mSwipeRefreshLayout.setEnabled(true);
        mLoading.setVisibility(View.GONE);

        // Check if the fragment is attached to activity before getting Context from Activity
        if (result != null && isAdded()) {
            mNoConnectionListener.hideNoConnection();
            mListTraffic.setVisibility(View.VISIBLE);

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
        View rootView = inflater.inflate(R.layout.fragment_traffic, container, false);
        mLoading = (ProgressBar) rootView.findViewById(R.id.pb_traffic_loading);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_swipe_container);
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

        // TODO: change Interface: we're getting the whole traffic here, not just the metro
        mMetroService = RetrofitFactory.getApi(IMetroService.class);

        // Load recycle view but do not fill it with data as long as the network call is not finished
        mListTraffic = (RecyclerView) getActivity().findViewById(R.id.rv_traffic);
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
        mListTraffic.setVisibility(View.INVISIBLE);
        mSwipeRefreshLayout.setEnabled(false);
        mLoading.setVisibility(View.VISIBLE);
    }

    private static class TrafficLoader extends RetrofitLoader<ResponseTraffic, IMetroService> {
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
        public ResponseTraffic call(IMetroService service) {
            Call<ResponseTraffic> request = service.getTraffic();
            Response<ResponseTraffic> response = null;
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
