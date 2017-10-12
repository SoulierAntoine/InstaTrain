package fr.altoine.instatrain.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;



/**
 * TransportChoiceAdapter - InstaTrain
 * Created by Antoine on 06/10/2017.
 */

public class TransportChoiceAdapter extends ArrayAdapter {
    public TransportChoiceAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
}
