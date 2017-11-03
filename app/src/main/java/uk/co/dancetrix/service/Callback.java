package uk.co.dancetrix.service;

public interface Callback<R, E extends Exception> {

    void onSuccess(R response);

    void onError(E exception);

}
