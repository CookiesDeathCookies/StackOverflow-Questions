package com.example.azotx.stackoverflowquestions.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkHelper {
    // SO - StackOverflow

    private static final String BASE_SO_URL = "https://api.stackexchange.com/2.2/questions";

    static final String START_DATE_PARAM = "fromdate";

    static final String ORDER_PARAM = "order";
    private static final String order = "desc";

    static final String SORT_PARAM = "sort";
    private static final String sort = "creation";

    static final String TAG_PARAM = "tagged";
    private static final String tag = "android";

    static final String SITE_PARAM = "site";
    private static final String site = "stackoverflow";

    public static URL buildUrl(long startTime) {
        // Строим URI по параметрам и уже известным значениям
        Uri builtUri = Uri.parse(BASE_SO_URL).buildUpon()
                .appendQueryParameter(START_DATE_PARAM, String.valueOf(startTime))
                .appendQueryParameter(ORDER_PARAM, order)
                .appendQueryParameter(SORT_PARAM, sort)
                .appendQueryParameter(TAG_PARAM, tag)
                .appendQueryParameter(SITE_PARAM, site)
                .build();

        // Пытаемся на основе URI получить URL
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");  // Небольшая уличная магия

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
