package fr.altoine.instatrain.loader;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

/**
 * RetrofitLoaderManager - InstaTrain
 * Created by soulierantoine on 07/08/2017
 */
public class RetrofitLoaderManager {

    public static <R, S> void init(
            final LoaderManager manager,
            final int loaderId,
            final RetrofitLoader<R, S> loader,
            final Callback<R> callback) {

        /*
         * We use restartLoader() method instead of initLoader() so we can request data multiple times.
         * The previously used Loader will be discarded.
         * See: https://stackoverflow.com/a/22400717
         */
        manager.restartLoader(loaderId, Bundle.EMPTY, new LoaderManager.LoaderCallbacks<Response<R>>() {


            /**
             * First method called when initializing loader, if it already exists, it'll be re-used.
             * @param id ID of the loader, got from {@link fr.altoine.instatrain.utils.Constants}.
             * @param args A key value pair.
             * @return Return the generated loader.
             */
            @Override
            public Loader<Response<R>> onCreateLoader(int id, Bundle args) {
                return loader;
            }


            /**
             * Called when the loader is done with its task (network call, fetching data from database...).
             * @param loader The loader in charge of the time-consuming task.
             * @param data The response once the task is done.
             */
            @Override
            public void onLoadFinished(Loader<Response<R>> loader, Response<R> data) {
                if (data.hasError()) {
                    callback.onFailure(data.getException());
                } else {
                    callback.onSuccess(data.getResult());
                }
            }


            @Override
            public void onLoaderReset(Loader<Response<R>> loader) {}
        });
    }
}
