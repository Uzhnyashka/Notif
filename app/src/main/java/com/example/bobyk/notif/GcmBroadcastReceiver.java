package com.example.bobyk.notif;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by bobyk on 26/07/15.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(intent.setAction(MainActivity.MESSAGE_INTENT));
    }

}
