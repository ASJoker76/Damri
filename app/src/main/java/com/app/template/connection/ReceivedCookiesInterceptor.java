package com.app.template.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.app.template.connection.App;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {

    private static final String TAG = "ceshi";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        Log.i(TAG, "intercept: "+originalResponse.headers().toString());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            HashSet<String> cookie = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookie.add(header);
            }
            SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("cookieData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putStringSet("cookie", cookie);
            editor.commit();
        }

        return originalResponse;
    }
}
