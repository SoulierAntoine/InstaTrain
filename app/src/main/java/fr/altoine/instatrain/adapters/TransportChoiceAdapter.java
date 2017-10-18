package fr.altoine.instatrain.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import fr.altoine.instatrain.models.Line;
import fr.altoine.instatrain.models.Transport;
import fr.altoine.instatrain.net.ResponseApi;


/**
 * TransportChoiceAdapter - InstaTrain
 * Created by Antoine on 06/10/2017.
 */

public class TransportChoiceAdapter<String> extends ArrayAdapter {

    private ResponseApi mResponseApi;

    public TransportChoiceAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public void setResponseApi(ResponseApi responseApi) {
        mResponseApi = responseApi;
    }

    public Line getLine(Transport transport, int position) {
        switch (transport) {
            case METROS:
                return mResponseApi.getResult().getLines().getMetroLines().get(position);
            case RERS:
                return mResponseApi.getResult().getLines().getRerLines().get(position);
            case TRAMWAYS:
                return mResponseApi.getResult().getLines().getTramwayLines().get(position);
            default:
                return null;
        }
    }
}
