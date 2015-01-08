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
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class NoticesContents extends ActionBarActivity {

    TextView tvTitle, tvDate, tvAuthor, tvContents, tvFile1 ,tvFile2, tvFile3, tvFile4, tvFile5;
    CardView cvFile;
    String cons = "", filename1 = "", filename2 = "", filename3 = "", filename4 = "", filename5 = "";
    private NoticeOpenTask noticeTask;

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
            tvFile1 = (TextView) findViewById(R.id.tv_file1);
            tvFile2 = (TextView) findViewById(R.id.tv_file2);
            tvFile3 = (TextView) findViewById(R.id.tv_file3);
            tvFile4 = (TextView) findViewById(R.id.tv_file4);
            tvFile5 = (TextView) findViewById(R.id.tv_file5);
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
                    Elements rawfile1 = doc.select("#bbsWrap div table tbody tr:eq(5) td a");
                    Elements rawfile2 = doc.select("#bbsWrap div table tbody tr:eq(6) td a");
                    Elements rawfile3 = doc.select("#bbsWrap div table tbody tr:eq(7) td a");
                    Elements rawfile4 = doc.select("#bbsWrap div table tbody tr:eq(8) td a");
                    Elements rawfile5 = doc.select("#bbsWrap div table tbody tr:eq(9) td a");


                    for (Element el : rawcontents) {
                        String con = el.text();
                        Log.d("rawcontents", con);
                        con = con.trim();
                        Log.d("CONS", con);
                        cons = cons + con + "\n";
                    }

                    for (Element el : rawfile1) {
                        String filedata1 = "http://www.sutaek.hs.kr/" + el.attr("href") + "<br>";
                        String filetitle1 = el.text();
                        filename1 = filename1 + "<a href=\"" + filedata1 + "\">" + filetitle1 + "</a>";
                        Log.d("CONS2", filedata1);
                        Log.d("CONS2", filename1);
                    }
                    if (!rawfile2.equals("") || !rawfile2.equals(null)) {

                        for (Element el : rawfile2) {
                            String filedata2 = "http://www.sutaek.hs.kr/" + el.attr("href") + "<br>";
                            String filetitle2 = el.text();
                            filename2 = filename2 + "<a href=\"" + filedata2 + "\">" + filetitle2 + "</a>";
                            Log.d("CONS2", filedata2);
                            Log.d("CONS2", filename2);
                        }
                    }

                    if (!rawfile3.equals("") || !rawfile3.equals(null)) {
                        for (Element el : rawfile3) {
                            String filedata3 = "http://www.sutaek.hs.kr/" + el.attr("href") + "<br>";
                            String filetitle3 = el.text();
                            filename3 = filename3 + "<a href=\"" + filedata3 + "\">" + filetitle3 + "</a>";
                            Log.d("CONS2", filedata3);
                            Log.d("CONS2", filename3);
                        }
                    }

                    if (!rawfile4.equals("") || !rawfile4.equals(null)) {
                        for (Element el : rawfile4) {
                            String filedata4 = "http://www.sutaek.hs.kr/" + el.attr("href") + "<br>";
                            String filetitle4 = el.text();
                            filename3 = filename3 + "<a href=\"" + filedata4 + "\">" + filetitle4 + "</a>";
                            Log.d("CONS2", filedata4);
                            Log.d("CONS2", filename4);
                        }
                    }

                    if (!rawfile5.equals("") || !rawfile5.equals(null)) {
                        for (Element el : rawfile5) {
                            String filedata5 = "http://www.sutaek.hs.kr/" + el.attr("href") + "<br>";
                            String filetitle5 = el.text();
                            filename3 = filename3 + "<a href=\"" + filedata5 + "\">" + filetitle5 + "</a>";
                            Log.d("CONS2", filedata5);
                            Log.d("CONS2", filename5);
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
                tvFile1.setText(Html.fromHtml(filename1));
                tvFile1.setMovementMethod(LinkMovementMethod.getInstance());
            }
            if (!filename2.equals("")) {
                tvFile2.setVisibility(View.VISIBLE);
                tvFile2.setText(Html.fromHtml(filename2));
                tvFile2.setMovementMethod(LinkMovementMethod.getInstance());
            }
            if (!filename3.equals("")) {
                tvFile3.setVisibility(View.VISIBLE);
                tvFile3.setText(Html.fromHtml(filename3));
                tvFile3.setMovementMethod(LinkMovementMethod.getInstance());
            }
            if (!filename4.equals("")) {
                tvFile4.setVisibility(View.VISIBLE);
                tvFile4.setText(Html.fromHtml(filename4));
                tvFile4.setMovementMethod(LinkMovementMethod.getInstance());
            }
            if (!filename5.equals("")) {
                tvFile5.setVisibility(View.VISIBLE);
                tvFile5.setText(Html.fromHtml(filename5));
                tvFile5.setMovementMethod(LinkMovementMethod.getInstance());
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
