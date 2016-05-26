package com.padeoe.testnet;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by padeoe on 2016/5/20.
 */
public class Test {
    public static int testNetwork(String URL) {
        HttpURLConnection urlConnection = null;
        try {
            java.net.URL url = new URL(URL);
            urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(false);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setUseCaches(false);
            urlConnection.getInputStream();
            urlConnection.setConnectTimeout(2000);
            urlConnection.setReadTimeout(2000);
            int httpResponseCode = urlConnection.getResponseCode();
            if (httpResponseCode == 200 && urlConnection.getContentLength() == 0) {
                httpResponseCode = 204;
            }
            return httpResponseCode;
        } catch (Exception e) {
            return -1;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    /**
     * 绑定wifi网络
     *
     * @param network
     */
    public static void bindNetWork(Network network) {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (network != null) {
                connectivityManager.bindProcessToNetwork(network);
            } else {
                connectivityManager.bindProcessToNetwork(getWifiNetwork());
            }
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                if (network != null) {
                    connectivityManager.setProcessDefaultNetwork(network);
                } else {
                    connectivityManager.setProcessDefaultNetwork(getWifiNetwork());
                }
            }
        }
    }

    public static void bindNetWork() {
        bindNetWork(null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Network getWifiNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        for (Network network : connectivityManager.getAllNetworks()) {
                System.out.println(connectivityManager.getNetworkInfo(network).toString());

        }
        for (Network network : connectivityManager.getAllNetworks()) {
            if (connectivityManager.getNetworkInfo(network).getType() == ConnectivityManager.TYPE_WIFI) {
                return network;
            }
        }
        for (Network network : connectivityManager.getAllNetworks()) {
            if (connectivityManager.getNetworkInfo(network).getType() == ConnectivityManager.TYPE_VPN) {
                return network;
            }
        }
        return null;
    }
}
