package com.codejune.sutaekhighschool;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.codejune.sutaekhighschool.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Schedule extends ActionBarActivity {
    ConnectivityManager cManager;
    NetworkInfo mobile;
    NetworkInfo wifi;
    private ProgressDialog progressDialog;
    private ArrayList<String> dayarray;
    private ArrayList<String> schedulearray;
    private ListCalendarAdapter adapter;

    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {

        }
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_schedule);
        final ListView listview = (ListView) findViewById(R.id.listView);

        // 통신 체크
        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mobile.isConnected() || wifi.isConnected()){}
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.network_connection_warning), Toast.LENGTH_LONG);
            finish();
        }

        final Handler mHandler = new Handler();
        new Thread()
        {

            public void run()
            {

                mHandler.post(new Runnable(){

                    public void run()
                    {
                        String loading = getString(R.string.loading);
                        progressDialog = ProgressDialog.show(Schedule.this,"",loading,true);
                    }
                });

                //Task

                //Notices URL
                try {
                    schedulearray = new ArrayList<String>();
                    dayarray = new ArrayList<String>();
                    Document doc = Jsoup.connect("http://www.sutaek.hs.kr/main.php?menugrp=020500&master=diary&act=list&master_sid=1").get();
                    Elements rawdaydata = doc.select(".listDay"); //Get contents from the class,"listDay

                    // 날짜 공백 밀림 버그 수정
                    if (dayarray.toString() == "" ) {
                        dayarray.clear();
                    }

                    // ListView에 추가
                    for (Element el : rawdaydata) {
                        String daydata = el.text();
                        dayarray.add(daydata); // add value to ArrayList
                    }
                    Log.d("Schedule","Parsed Day Array" + dayarray);

                    Elements rawscheduledata = doc.select(".listData"); //Get contents from tags,"a" which are in the class,"ellipsis"
                    for (Element el : rawscheduledata) {
                        String scheduledata = el.text();
                        schedulearray.add(scheduledata); // add value to ArrayList
                    }
                    Log.d("Schedule","Parsed Schedule Array" + schedulearray);

                } catch (IOException e) {
                    e.printStackTrace();
                }


                mHandler.post(new Runnable()
                {
                    public void run()
                    {
                        progressDialog.dismiss();
                        //UI Task
                        adapter = new ListCalendarAdapter(Schedule.this, dayarray, schedulearray);
                        listview.setAdapter(adapter);

                        handler.sendEmptyMessage(0);
                    }
                });

            }
        }.start();

    }
}
