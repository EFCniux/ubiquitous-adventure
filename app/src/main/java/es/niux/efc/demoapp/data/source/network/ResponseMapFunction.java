package es.niux.efc.demoapp.data.source.network;

import android.support.annotation.NonNull;

import es.niux.efc.demoapp.data.exception.NetworkException;
import io.reactivex.functions.Function;
import retrofit2.Response;

public class ResponseMapFunction<T> implements Function<Response<T>, T> {
    @Override
    public final T apply(Response<T> tResponse) throws Exception {
        if (tResponse.isSuccessful() && tResponse.body() != null) {
            return tResponse.body();
        } else {
            return onUnsuccessful(tResponse);
        }
    }

    protected T onUnsuccessful(@NonNull Response<T> tResponse) throws Exception {
        throw new NetworkException(tResponse.raw());
    }
}
