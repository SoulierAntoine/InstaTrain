package fr.altoine.instatrain.loader;

/**
 * Callback - InstaTrain
 * Created by soulierantoine on 07/08/2017
 */
public interface Callback<D> {
    void onFailure(Exception ex);
    void onSuccess(D result);
}
