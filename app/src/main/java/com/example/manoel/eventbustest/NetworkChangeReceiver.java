package com.example.manoel.eventbustest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import de.greenrobot.event.EventBus;

/**
 * Created by manoel on 07/12/15.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    EventBus bus = EventBus.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        //should check null because in air plan mode it will be null
        // Post the event
        bus.post(new NetworkEvent(String.valueOf(netInfo != null && netInfo.isConnected())));
    }
}
