package com.maf.assignment.newsapp.network;

import com.maf.assignment.newsapp.BuildConfig;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ritesh on 04/09/2019.
 */

// Purpose of interceptor is to append the authorization header
// before sending the request to server.
public class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request()
                .newBuilder()
                .addHeader("authorization", BuildConfig.API_KEY)
                .build();

        return chain.proceed(request);
    }
}
