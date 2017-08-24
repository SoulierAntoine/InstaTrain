package fr.altoine.instatrain.listeners;

/**
 * Created by Antoine on 23/08/2017.
 */



public interface RetryActionListener {
    void retryAction(String method, Object... args);
}
