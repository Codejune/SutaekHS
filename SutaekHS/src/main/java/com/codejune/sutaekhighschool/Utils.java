package com.codejune.sutaekhighschool;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Utils {
    Context context = null;
    private boolean b = false;
    private static String URL = "http://www.sutaek.hs.kr/main.php?menugrp=020500&master=bbs&act=list&master_sid=4";
    String titledata = "";
    String num = "";
    boolean skipedboolean = false;
    int skip = 0;
    private ArrayList<String> linearray;

    public Utils(Context context) {
        this.context = context;
    }

    public boolean checkNewNotice() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                linearray = new ArrayList<String>();
                try {

                    Document doc = Jsoup.connect(URL).get();
                    Elements rawtitledata = doc.select(".listbody a");
                    Elements rawnumdata = doc.select("td:eq(0)");

                    for (Element el : rawnumdata) {
                        String deleteKey = "공지";
                        String linedata = el.text();

                        if ((linedata.equals(deleteKey)) || (linedata == null)) {
                            if (skipedboolean) {
                            } else {
                                skip++;
                            }
                        } else {
                            skipedboolean = true;
                            linearray.add(linedata);
                            break;
                        }
                    }

                    for(int index =0; index < linearray.size(); index++) {
                        num = linearray.get(index).toString();
                    }

                    titledata = rawtitledata.attr("title");                   
                    Log.d("Util", titledata);

                    SharedPreferences pref = context.getSharedPreferences("Sutaek", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();

                    Log.d("Util", num);
                    int intnum = Integer.parseInt(num);
                    edit.putInt("notice_num_svr", intnum).commit();

                    final int noticenum = pref.getInt("notice_num_svr", 0);
                    final int savednum = pref.getInt("notice_num", 0);
                    if (noticenum != 0) {
                        if (savednum == 0) {
                            edit.putInt("notice_num", noticenum).commit();
                            setTrue(false);
                        } else {
                            if (noticenum > savednum) {
                                int line = noticenum - savednum;
                                edit.putInt("notice_num", noticenum).commit();
                                Log.d("Utils", Integer.toString(noticenum));
                                setTrue(true);
                                Intent in = new Intent(context, BootReceiver.class);
                                in.putExtra("bool", true);
                                in.putExtra("title", titledata);
                                in.putExtra("line", line);
                                context.sendBroadcast(in);
                                return;
                            } else {
                                setTrue(false);
                            }
                        }
                    }
                } catch (IOException e) {
                    Log.d("Utils", "IOException");
                    e.printStackTrace();

                }


            }
        }).start();
        return b;

    }

    public void registerAlarm() {

        Intent intent = new Intent(context, BootReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
        am.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 2 * 60 * 60 * 1000, sender);
        //3 * 60 * 60 * 1000
    }

    private boolean setTrue(boolean b) {
        this.b = b;
        return this.b;
    }

}
