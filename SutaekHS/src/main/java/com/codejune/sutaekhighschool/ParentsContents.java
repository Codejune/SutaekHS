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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.gc.materialdesign.views.ProgressBarCircularIndetermininate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;


public class ParentsContents extends ActionBarActivity {

    TextView tvTitle, tvDate, tvAuthor, tvContents, tvFile;
    CardView cvFile;
    String cons = "", filename = "";
    private NoticeOpenTask noticeTask;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_contents);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ProgressBarCircularIndetermininate progressBar = (ProgressBarCircularIndetermininate) findViewById(R.id.progressBar);
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
            progressBar.setVisibility(View.VISIBLE);
            tvTitle = (TextView) findViewById(R.id.tv_title);
            tvDate = (TextView) findViewById(R.id.tv_date);
            tvAuthor = (TextView) findViewById(R.id.tv_author);
            tvContents = (TextView) findViewById(R.id.tv_contents);
            tvFile = (TextView) findViewById(R.id.tv_file);
            cvFile = (CardView) findViewById(R.id.card_file);
            Intent in = getIntent();
            tvTitle.setText(in.getStringExtra("title"));
            tvDate.setText("등록일 : " +in.getStringExtra("date"));
            tvAuthor.setText("작성자 : " +in.getStringExtra("author"));
            Log.d("CONTENT", in.getStringExtra("URL"));
            noticeTask = new NoticeOpenTask();
            noticeTask.execute(in.getStringExtra("URL"), "", "");
            progressBar.setVisibility(View.GONE);
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
                    Elements rawfile = doc.select("#bbsWrap div table tbody tr:eq(5) td a");

                    for (Element el : rawcontents) {
                        String con = el.text();
                        Log.d("rawcontents", con);
                        con = con.trim();
                        Log.d("CONS", con);
                        cons = cons + con + "\n";
                    }

                    for (Element el : rawfile) {
                        String filedata = "http://www.sutaek.hs.kr/" + el.attr("href") + "<br>";
                        String filetitle = el.text();
                        filename = filename + "<a href=\"" + filedata + "\">" + filetitle + "</a>";
                        Log.d("CONS2", filedata);
                        Log.d("CONS2", filename);
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
            Log.d("Postdata", cons);
            if (!filename.equals("")) {
                cvFile.setVisibility(View.VISIBLE);
                tvFile.setText(Html.fromHtml(filename));
                tvFile.setMovementMethod(LinkMovementMethod.getInstance());
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
                Intent intent = new Intent(ParentsContents.this,
                        WebViewActivityParent.class);
                intent.putExtra("URL", in.getStringExtra("URL"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
