package com.codejune.sutaekhighschool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;


public class Notices extends ActionBarActivity {

    private ArrayList<String> titlearray;
    private ArrayList<String> titleherfarray;
    private ArrayList<String> authorarray;
    private ArrayList<String> datearray;
    private PostListAdapter adapter;
    private SwipeRefreshLayout SRL;
    ListView listview;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            listview = (ListView) findViewById(R.id.listView);
            SRL = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
            SRL.setColorSchemeColors(Color.rgb(231, 76, 60),
                    Color.rgb(46, 204, 113), Color.rgb(41, 128, 185),
                    Color.rgb(241, 196, 15));
            SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    networkTask();
                }
            });
            networkTask();
        }

    }

    private AdapterView.OnItemClickListener GoToWebPage = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View clickedView,
                                int pos, long id) {
            String herfitem = titleherfarray.get(pos);
            Intent intent = new Intent(Notices.this,
                    WebViewActivityNotices.class);
            intent.putExtra("URL", herfitem);
            startActivity(intent);
        }
    };

    // Method for get list of notices
    private void networkTask() {
        final Handler mHandler = new Handler();
        new Thread() {

            public void run() {

                mHandler.post(new Runnable() {

                    public void run() {
                        SRL.setRefreshing(true);
                    }
                });

                // Task

                // Notices URL
                try {
                    titlearray = new ArrayList<String>();
                    titleherfarray = new ArrayList<String>();
                    authorarray = new ArrayList<String>();
                    datearray = new ArrayList<String>();
                    // 파싱할 페이지 URL
                    Document doc = Jsoup
                            .connect(
                                    "http://www.sutaek.hs.kr/main.php?menugrp=020101&master=bbs&act=list&master_sid=1")
                            .get();
                    Elements rawmaindata = doc.select(".listbody a");
                    // Get
                    // contents
                    // from
                    // tags,"a"
                    // which
                    // are
                    // in
                    // the
                    // class,"listbody"
                    Elements rawauthordata = doc.select("td:eq(3)"); 
                    // 작성자 이름 
                    // 얻기 -
                    // 3번째
                    // td셀
                    // 에서 얻기
                    Elements rawdatedata = doc.select("td:eq(4)"); 
                    // 작성 날자 얻기 -
                    // 4번째 td셀
                    // 에서 얻기
                    String titlestring = rawmaindata.toString();
                    Log.i("Notices", "Parsed Strings" + titlestring);

                    // 파싱할 데이터로 배열 생성
                    for (Element el : rawmaindata) {
                        String titlherfedata = el.attr("href");
                        String titledata = el.attr("title");
                        titleherfarray.add("http://www.sutaek.hs.kr/"
                                + titlherfedata); // add value to ArrayList
                        titlearray.add(titledata); // add value to ArrayList
                    }
                    Log.i("Notices", "Parsed Link Array Strings"
                            + titleherfarray);
                    Log.i("Notices", "Parsed Array Strings" + titlearray);

                    for (Element el : rawauthordata) {
                        String authordata = el.text();
                        Log.d("Author", el.text());
                        authorarray.add(authordata);
                    }
                    for (Element el : rawdatedata) {
                        String datedata = el.text();
                        Log.d("Date", el.text());
                        datearray.add(datedata);
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                }

                mHandler.post(new Runnable() {
                    public void run() {
                        // UI Task
                        // 배열로 어뎁터 설정
                        adapter = new PostListAdapter(Notices.this, titlearray,
                                datearray, authorarray);
                        listview.setAdapter(adapter);
                        listview.setOnItemClickListener(GoToWebPage);
                        handler.sendEmptyMessage(0);
                        SRL.setRefreshing(false);

                        Toast toast = Toast.makeText(getApplicationContext(),
                                getString(R.string.notices_info),
                                Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

            }
        }.start();
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

}
