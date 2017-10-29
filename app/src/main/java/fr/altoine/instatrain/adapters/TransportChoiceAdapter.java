package fr.altoine.instatrain.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

import fr.altoine.instatrain.models.Destination;
import fr.altoine.instatrain.models.Line;
import fr.altoine.instatrain.models.Station;
import fr.altoine.instatrain.net.ResponseApi;


/**
 * TransportChoiceAdapter - InstaTrain
 * Created by Antoine on 06/10/2017.
 */

public class TransportChoiceAdapter<T> extends ArrayAdapter {

    private ResponseApi mResponseApi;
    private List<T> mElements;
    public TransportChoiceAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public void setResponseApi(ResponseApi responseApi) {
        mResponseApi = responseApi;
    }
    public void setElements(List<T> elements) { mElements = elements; }

//    public T getElement(int position) {
    public Line getLine(int position){ return mResponseApi.getResult().getLines().get(position); }
    public Station getStation(int position){ return mResponseApi.getResult().getStations().get(position); }
    public Destination getDestination(int position){ return mResponseApi.getResult().getDestinations().get(position); }
        /* switch (transport) {
            case METROS:
                return mResponseApi.getResult().getLines().getMetroLines().get(position);
            case RERS:
                return mResponseApi.getResult().getLines().getRerLines().get(position);
            case TRAMWAYS:
                return mResponseApi.getResult().getLines().getTramwayLines().get(position);
            default:
                return null;
        } */

}
