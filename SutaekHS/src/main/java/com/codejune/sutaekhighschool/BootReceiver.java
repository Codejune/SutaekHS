package com.codejune.sutaekhighschool;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Codejune on 14. 11. 28..
 */

public class BootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Toast.makeText(context, "브로드케스트 테스트 성공 ! ", Toast.LENGTH_LONG).show();
        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            ComponentName comp = new ComponentName(context.getPackageName(),
                    MainActivity.class.getName());
            ComponentName service = context.startService(new Intent()
                    .setComponent(comp));
        }
    }
}

