package com.codejune.sutaekhighschool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class Notices_Parents extends Activity {

    private ProgressDialog progressDialog;
    private ArrayList<String> titlearray;
    private ArrayList<String> titleherfarray;
    private ArrayAdapter<String> adapter;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_notices_parents);
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
                            progressDialog = ProgressDialog.show(
                                    Notices_Parents.this, "", loading, true);
                        }
                    });

                    // Task

                    // Notices URL
                    try {
                        titlearray = new ArrayList<String>();
                        titleherfarray = new ArrayList<String>();
                        Document doc = Jsoup
                                .connect(
                                        "http://www.sutaek.hs.kr/main.php?menugrp=020500&master=bbs&act=list&master_sid=4")
                                .get();
                        Elements rawdata = doc.select(".listbody a"); // Get
                        // contents
                        // from
                        // tags,"a"
                        // which
                        // are
                        // in
                        // the
                        // class,"listbody"
                        String titlestring = rawdata.toString();
                        Log.i("Notices", "Parsed Strings" + titlestring);

                        for (Element el : rawdata) {
                            String titlherfedata = el.attr("href");
                            String titledata = el.attr("title");
                            titleherfarray.add("http://www.sutaek.hs.kr/"
                                    + titlherfedata); // add value to ArrayList
                            titlearray.add(titledata); // add value to ArrayList
                        }
                        Log.i("Notices", "Parsed Link Array Strings"
                                + titleherfarray);
                        Log.i("Notices", "Parsed Array Strings" + titlearray);

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                    mHandler.post(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            // UI Task
                            adapter = new ArrayAdapter<String>(
                                    Notices_Parents.this,
                                    android.R.layout.simple_list_item_1,
                                    titlearray);
                            listview.setAdapter(adapter);
                            listview.setOnItemClickListener(GoToWebPage);
                            handler.sendEmptyMessage(0);
                        }
                    });

                }
            }.start();

        }
    }

    private AdapterView.OnItemClickListener GoToWebPage = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View clickedView,
                                int pos, long id) {
            String herfitem = titleherfarray.get(pos);
            Intent gotowebpage = new Intent(Intent.ACTION_VIEW);
            gotowebpage.setData(Uri.parse(herfitem));
            startActivity(gotowebpage);
        }
    };

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
}