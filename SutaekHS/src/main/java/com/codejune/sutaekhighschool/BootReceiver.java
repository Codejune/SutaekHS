package com.codejune.sutaekhighschool;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver{
    NotificationManager notimgr;

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils utils = new Utils(context);
        if(intent.getBooleanExtra("bool", false) || utils.checkNewNotice()){

            Intent i = new Intent(context, Notices_Parents.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            notimgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            String title = intent.getStringExtra("title");
            int line = intent.getIntExtra("line", 1);
            Log.d("BootRec", title);

            if (line == 1) {
                builder.setTicker("새로운 가정통신문이 있습니다")
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle(title)
                        .setContentText("새로운 가정통신문 확인하러 가기")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setAutoCancel(true)
                        .setNumber(line)
                        .setContentInfo("수택고등학교");
                notimgr.notify(1, builder.build());
            } else if (line >= 2) {
                builder.setTicker("새로운 가정통신문이 있습니다")
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle("새로운 " + line + "개의 가정통신문이 있습니다.")
                        .setContentText("새로운 가정통신문 확인하러 가기")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setAutoCancel(true)
                        .setNumber(line)
                        .setContentInfo("수택고등학교");
                notimgr.notify(1, builder.build());
            }
        }
    }
}

