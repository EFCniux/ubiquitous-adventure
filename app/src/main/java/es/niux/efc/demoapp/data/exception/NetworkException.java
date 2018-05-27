package es.niux.efc.demoapp.data.exception;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Response;

public class NetworkException extends IOException {
    private final @Nullable Response response;

    public NetworkException(@Nullable Response response) {
        super(response != null ? "Code: " + response.code() : "");
        this.response = response;
    }

    public NetworkException(@Nullable Response response, Throwable cause) {
        super(cause);
        this.response = response;
    }

    public @Nullable Response getResponse() {
        return response;
    }
}
