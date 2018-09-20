package com.example.lnthe54.miniproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * @author lnthe54 on 9/20/2018
 * @project MiniProject
 */
public class InternetReceiver extends BroadcastReceiver {
    private static final String MESSAGE_OFF = "No connect internet";
    private static final String MESSAGE_ON = "Connected internet";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!checkInternet(context)) {
            Toast.makeText(context, MESSAGE_OFF, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, MESSAGE_ON, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        return serviceManager.isNetwork();
    }

    public class ServiceManager {
        Context context;

        public ServiceManager(Context base) {
            context = base;
        }

        public boolean isNetwork() {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}
