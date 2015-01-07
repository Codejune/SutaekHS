package com.codejune.sutaekhighschool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class NoticesContents extends ActionBarActivity {

    TextView tvTitle, tvDate, tvAuthor, tvContents, tvFile;
    CardView cvFile;
    String cons = "", filename = "";
    private NoticeOpenTask noticeTask;
    private ArrayList<String> namearray;
    private ArrayList<String> filearray;
    ListView listview;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_contents);
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
                            })
                    .show();
        } else {
            tvTitle = (TextView) findViewById(R.id.tv_title);
            tvDate = (TextView) findViewById(R.id.tv_date);
            tvAuthor = (TextView) findViewById(R.id.tv_author);
            tvContents = (TextView) findViewById(R.id.tv_contents);
            //tvFile = (TextView) findViewById(R.id.tv_file);
            cvFile = (CardView) findViewById(R.id.card_file);
            listview = (ListView) findViewById(R.id.listView);
            Intent in = getIntent();
            tvTitle.setText(in.getStringExtra("title"));
            tvDate.setText(in.getStringExtra("date"));
            tvAuthor.setText(in.getStringExtra("author"));
            Log.d("CONTENT", in.getStringExtra("URL"));
            noticeTask = new NoticeOpenTask();
            noticeTask.execute(in.getStringExtra("URL"), "", "");
        }

    }
/*
    // Method for get list of notices
    private void networkTask(final String URL) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
noticeTask = new NoticeOpenTask();
                        noticeTask.execute(URL, "", "");


                    }
                });
            }
        }).start();
    }*/

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


    // AsyncTask<Params,Progress,Result>
    private class NoticeOpenTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {

            if (!urls[0].equals("")) {

                // Notices URL
                try {
                    // 파싱할 페이지 URL
                    namearray = new ArrayList<String>();
                    filearray = new ArrayList<String>();

                    Document doc = Jsoup.connect(urls[0]).get();
                    Elements rawcontents = doc.select("#bbsWrap div table tbody tr:eq(4) td p");
                    Elements rawfile = doc.select("#bbsWrap div table tbody tr:eq(5) td");


                    for (Element el : rawcontents) {
                        String con = el.text();
                        Log.d("rawcontents", con);
                        con = con.trim();
                        Log.d("CONS", con);
                        cons = cons + con + "\n";
                        //cons = cons.replace("  ", "\n");
                    }

                    for (Element el : rawfile) {
                        String filedata = "http://www.sutaek.hs.kr/" + el.attr("href");
                        String filetitle = el.text();

                        filename = filename + "<a href=\"" + filedata + "\">" + filetitle + "</a>";
                        filearray.add(filedata);
                        namearray.add(filename);
                        //String con = el.text();
                        //con = con.trim();
                        Log.d("CONS2", filedata);
                        Log.d("CONS2", filename);

                        //cons = cons + con + "\n";
                    }

                    Log.d("CON", cons);
                    return cons;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return cons;
        }

        @Override
        protected void onPostExecute(String cons) {
            // TODO Auto-generated method stub
            super.onPostExecute(cons);
            if (cons.equals("") || cons == null || cons.equals(" ")) {
                tvContents.setText("<첨부파일 참조>");
            } else {
                tvContents.setText(cons);
            }
            
            if (!filename.equals("")) {
                cvFile.setVisibility(View.VISIBLE);
                tvFile.setText(Html.fromHtml(filename));
                listview.setOnItemClickListener(Download);
            }
        }
    }

    private AdapterView.OnItemClickListener Download = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View clickedView,
                                int pos, long id) {
            String file = filearray.get(pos);
            Intent intent = new Intent(NoticesContents.this,
                    WebViewActivityParent.class);
            intent.putExtra("URL", file);
            startActivity(intent);
        }
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_webview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.webview_menu:
                Intent in = getIntent();
                Intent intent = new Intent(NoticesContents.this,
                        WebViewActivityParent.class);
                intent.putExtra("URL", in.getStringExtra("URL"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
