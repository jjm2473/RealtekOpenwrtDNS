package com.jjm.rtkdns;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Scanner;

public class NetworkReceiver extends BroadcastReceiver implements Runnable {
    private static final String TAG = "RTK_DNS";
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conn =  (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == conn) {
            Log.e(TAG, "get ConnectivityManager failed!");
            return;
        }
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.isConnected()) {
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        String dns = null;
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(new String[]{"sh", "-c", "getprop \"`getprop net.change`\""});
            dns = new Scanner(process.getInputStream()).nextLine();
            process.waitFor();
        } catch (Exception e) {
            Log.e(TAG, "getprop error", e);
        }
        if (dns != null && !"".equals(dns)) {
            Log.i(TAG, "DNS is " + dns);
            try {
                Process process = runtime.exec(new String[]{"ubus", "call", "file", "write", "{\"path\":\"/etc/resolv.conf\", \"data\":\"\\nnameserver " + dns + "\\n\"}"});
                process.waitFor();
            } catch (Exception e) {
                Log.e(TAG, "ubus error", e);
            }
        } else {
            Log.e(TAG, "DNS is empty");
        }
    }
}
