package com.alex9xu.mytest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Alex9Xu@hotmail.com on 2016/10/25.
 */

public class NetConnectUtil {
    /**
     * Whether have network connected
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = null;
        try{
            ni = cm.getActiveNetworkInfo();
        } catch (Exception e) {
            return false;
        }

        return ni != null && (ni.isConnectedOrConnecting() || ni.isAvailable());
    }

    /**
     * Whether connect by Wifi
     */
    public static boolean isWifiConnect(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        return wifi == NetworkInfo.State.CONNECTED;
    }

}
