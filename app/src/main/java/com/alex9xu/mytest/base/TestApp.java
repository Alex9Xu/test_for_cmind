package com.alex9xu.mytest.base;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.alex9xu.mytest.utils.LogHelper;
import com.alex9xu.mytest.utils.NetConnectUtil;

import java.lang.ref.WeakReference;

/**
 * Created by Alex9Xu@hotmail.com on 2016/10/25
 */

// This is Base Application
public class TestApp extends Application {
    private static final String TAG = "TestApp";
    private static WeakReference<TestApp> mInstanceRef;

    private boolean mIsHaveNetwork;
    private boolean mIsConnectWifi;
    // Listen to network status
    private BroadcastReceiver netReceiver;

    public static Context mAppContext;
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;

    /**
     * Get Application Instance
     */
    public static TestApp getInstance() {
        if (mInstanceRef.get() == null) {
            LogHelper.e(TAG, "TestApp instance null");
            throw new IllegalStateException();
        }
        return mInstanceRef.get();
    }

    /**
     * get Application Context
     */
    public static Context getAppContext() {
        return getInstance().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogHelper.d(TAG, "onCreate start");
        mAppContext = getApplicationContext();
        mInstanceRef = new WeakReference<>(this);

        new Thread(){
            @Override
            public void run(){
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                initScreenSize();
                receiverNetWork();
            }
        }.start();

        LogHelper.d(TAG, "onCreate end");
    }

    private void receiverNetWork() {
        if (netReceiver == null) {
            netReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                        mIsHaveNetwork = NetConnectUtil.isNetworkConnected(getAppContext());
                        mIsConnectWifi = NetConnectUtil.isWifiConnect(getAppContext());
                        LogHelper.d(TAG, "Network: " + mIsHaveNetwork + ", Wifi: " + mIsConnectWifi);
                    }
                }
            };

            /** Listen to network change */
            IntentFilter filter = new IntentFilter();
            filter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netReceiver, filter);
        }
    }

    public void initScreenSize() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        LogHelper.d(TAG, "mScreenWidth = " + mScreenWidth + ", mScreenHeight = " + mScreenHeight);
    }

    /**
     * get screen width in px
     * @return
     */
    public int getScreenWidth() {
        return mScreenWidth;
    }

    /**
     * get screen height in px
     * @return
     */
    public int getScreenHeight() {
        return mScreenHeight;
    }

    public boolean isHaveNetWork() {
        return mIsHaveNetwork;
    }

    public boolean isConnectWifi() {
        return mIsConnectWifi;
    }

}


