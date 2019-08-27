package com.zza.stardust.app.ui.OkHttp;

import com.zza.library.utils.LogUtil;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long startTime = System.nanoTime();
        LogUtil.d(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long endTime = System.nanoTime();
        LogUtil.d(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (endTime - startTime) / 1e6d, response.headers()));
       return response;
    }
}
