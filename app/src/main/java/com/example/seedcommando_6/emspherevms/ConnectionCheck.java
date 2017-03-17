package com.example.seedcommando_6.emspherevms;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by seedcommando_1 on 3/7/2017.
 */

public class ConnectionCheck
{
    public static boolean isConnectedToServer(String url, int timeout) {
        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }
    }
    public static boolean isNetworkAvailable(ConnectivityManager cm)
    {
        //ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }
        return false;
    }
}
