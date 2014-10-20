package com.codejune.sutaekhighschool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class Schedule extends Activity {

    private ProgressDialog progressDialog;
    private ArrayList<String> dayarray;
    private ArrayList<String> schedulearray;
    private ListCalendarAdapter adapter;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_schedule);
        getActionBar().setDisplayShowHomeEnabled(false);
        final ListView listview = (ListView) findViewById(R.id.listView);

        if (!isNetworkConnected(this)) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_error)
                    .setTitle("네트워크 연결")
                    .setMessage("\n네트워크 연결 상태 확인 후 다시 시도해 주십시요\n")
                    .setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            }).show();
        } else {

            final Handler mHandler = new Handler();
            new Thread() {

                public void run() {

                    mHandler.post(new Runnable() {

                        public void run() {
                            String loading = getString(R.string.loading);
                            progressDialog = ProgressDialog.show(Schedule.this,
                                    "", loading, true);
                        }
                    });

                    // Task
                    // Notices URL
                    try {
                        schedulearray = new ArrayList<String>();
                        dayarray = new ArrayList<String>();
                        Document doc = Jsoup
                                .connect(
                                        "http://www.sutaek.hs.kr/main.php?menugrp=020500&master=diary&act=list&master_sid=1")
                                .get();
                        Elements rawdaydata = doc.select(".listDay");

                        // 날짜 공백 밀림 버그 수정
                        if (dayarray.toString() == "") {
                            dayarray.clear();
                        }

                        // ListView에 추가
                        for (Element el : rawdaydata) {
                            String daydata = el.text();
                            dayarray.add(daydata); // add value to ArrayList
                        }
                        Log.d("Schedule", "Parsed Day Array" + dayarray);

                        Elements rawscheduledata = doc.select(".listData");
                        for (Element el : rawscheduledata) {
                            String scheduledata = el.text();
                            schedulearray.add(scheduledata); // add value to
                            // ArrayList
                        }
                        Log.d("Schedule", "Parsed Schedule Array"
                                + schedulearray);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mHandler.post(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            // UI Task
                            adapter = new ListCalendarAdapter(Schedule.this,
                                    dayarray, schedulearray);
                            listview.setAdapter(adapter);

                            handler.sendEmptyMessage(0);
                        }
                    });
                }
            }.start();
        }
    }

    // 인터넷 연결 상태 체크
    public boolean isNetworkConnected(Context context) {
        boolean isConnected = false;

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobile.isConnected() || wifi.isConnected()) {
            isConnected = true;
        } else {
            isConnected = false;
        }
        return isConnected;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
