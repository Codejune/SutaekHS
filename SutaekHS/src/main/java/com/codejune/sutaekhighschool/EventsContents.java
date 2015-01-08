
package com.codejune.sutaekhighschool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class EventsContents extends ActionBarActivity {
    TextView tvTitle, tvDate, tvAuthor, tvContents;
    ImageView tvImg1, tvImg2, tvImg3, tvImg4, tvImg5;
    CardView cvFile;
    String cons = "", filename1 = "", filename2 = "", filename3 = "", filename4 = "", filename5 = "";
    private NoticeOpenTask noticeTask;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_contents);
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
            tvImg1 = (ImageView) findViewById(R.id.tvimg1);
            tvImg2 = (ImageView) findViewById(R.id.tvimg2);
            tvImg3 = (ImageView) findViewById(R.id.tvimg3);
            tvImg4 = (ImageView) findViewById(R.id.tvimg4);
            tvImg5 = (ImageView) findViewById(R.id.tvimg5);
            cvFile = (CardView) findViewById(R.id.card_file);
            Intent in = getIntent();
            tvTitle.setText(in.getStringExtra("title"));
            tvDate.setText("등록일 : " +in.getStringExtra("date"));
            tvAuthor.setText("작성자 : " + in.getStringExtra("author"));
            Log.d("CONTENT", in.getStringExtra("URL"));
            noticeTask = new NoticeOpenTask();
            noticeTask.execute(in.getStringExtra("URL"), "", "");
        }
    }

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

    private class NoticeOpenTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {

            if (!urls[0].equals("")) {

                try {

                    Document doc = Jsoup.connect(urls[0]).get();
                    Elements rawcontents = doc.select("#bbsWrap div table tbody tr:eq(4) td p");
                    Elements rawimg1 = doc.select("#bbsWrap div table tbody tr:eq(5) td img");
                    Elements rawimg2 = doc.select("#bbsWrap div table tbody tr:eq(6) td img");
                    Elements rawimg3 = doc.select("#bbsWrap div table tbody tr:eq(7) td img");
                    Elements rawimg4 = doc.select("#bbsWrap div table tbody tr:eq(8) td img");
                    Elements rawimg5 = doc.select("#bbsWrap div table tbody tr:eq(9) td img");


                    for (Element el : rawcontents) {
                        String con = el.text();
                        Log.d("rawcontents", con);
                        con = con.trim();
                        Log.d("CONS", con);
                        cons = cons + con + "\n";
                    }

                    for (Element el : rawimg1) {
                        String imgdata1 = "http://www.sutaek.hs.kr/" + el.attr("src");
                        new ImageDownloader(tvImg1).execute(imgdata1);
                        filename1 = filename1 + "<img src=\"" + imgdata1 + "\">";
                        Log.d("ImgLink", imgdata1);
                    }
                    if (!rawimg2.equals("") || !rawimg2.equals(null)) {

                        for (Element el : rawimg2) {
                            String imgdata2 = "http://www.sutaek.hs.kr/" + el.attr("src");
                            new ImageDownloader(tvImg2).execute(imgdata2);
                            filename2 = filename2 + "<img src=\"" + imgdata2 + "\">";
                            Log.d("ImgLink", imgdata2);
                        }
                    }

                    if (!rawimg3.equals("") || !rawimg3.equals(null)) {
                        for (Element el : rawimg3) {
                            String imgdata3 = "http://www.sutaek.hs.kr/" + el.attr("src");
                            new ImageDownloader(tvImg3).execute(imgdata3);
                            filename3 = filename3 + "<img src=\"" + imgdata3 + "\">";

                            Log.d("ImgLink", imgdata3);
                        }
                    }

                    if (!rawimg4.equals("") || !rawimg4.equals(null)) {
                        for (Element el : rawimg4) {
                            String imgdata4 = "http://www.sutaek.hs.kr/" + el.attr("src");
                            new ImageDownloader(tvImg4).execute(imgdata4);
                            filename4 = filename4 + "<img src=\"" + imgdata4 + "\">";

                            Log.d("ImgLink", imgdata4);
                        }
                    }

                    if (!rawimg5.equals("") || !rawimg5.equals(null)) {
                        for (Element el : rawimg5) {
                            String imgdata5 = "http://www.sutaek.hs.kr/" + el.attr("src");
                            new ImageDownloader(tvImg5).execute(imgdata5);
                            filename5 = filename5 + "<img src=\"" + imgdata5 + "\">";
                            Log.d("ImgLink", imgdata5);
                        }
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
            super.onPostExecute(cons);
            if (cons.equals("") || cons == null || cons.equals(" ")) {
                tvContents.setText("<첨부파일 참조>");
            } else {
                tvContents.setText(cons);
            }
            if (!filename1.equals("")) {
                cvFile.setVisibility(View.VISIBLE);
            }
            if (!filename2.equals("")) {
                tvImg2.setVisibility(View.VISIBLE);
            }
            if (!filename3.equals("")) {
                tvImg3.setVisibility(View.VISIBLE);
            }
            if (!filename4.equals("")) {
                tvImg4.setVisibility(View.VISIBLE);
            }
            if (!filename5.equals("")) {
                tvImg5.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_webview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.webview_menu:
                Intent in = getIntent();
                Intent intent = new Intent(EventsContents.this,
                        WebViewActivityParent.class);
                intent.putExtra("URL", in.getStringExtra("URL"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}