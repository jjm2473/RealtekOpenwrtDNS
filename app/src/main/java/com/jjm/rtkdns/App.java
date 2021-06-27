package com.jjm.rtkdns;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public class App extends Application {
    private NetworkReceiver receiver = new NetworkReceiver();
    @Override
    public void onCreate() {
        super.onCreate();
        this.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}
