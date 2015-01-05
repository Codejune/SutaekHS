package com.codejune.sutaekhighschool;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;

public class Appinfo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String app_ver = null;
        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView version = (TextView)findViewById(R.id.version);
        version.setText("Version " + app_ver);

        TextView src = (TextView)findViewById(R.id.src);
        src.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri.parse("http://github.com/codejune"));
                startActivity(src);
            }
        });

        TextView update = (TextView)findViewById(R.id.update);
        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(Intent.ACTION_VIEW);
                update.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.codejune.sutaekhighschool"));
                startActivity(update);
            }
        });

        TextView readme = (TextView)findViewById(R.id.readme);
        readme.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readme = new Intent(Appinfo.this, Doc_Readme.class);
                startActivity(readme);
            }
        });

        TextView contrubutors = (TextView)findViewById(R.id.contrubutors);
        contrubutors.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contrubutors = new Intent(Appinfo.this, Doc_Contributors.class);
                startActivity(contrubutors);
            }
        });
    }

    public void onStop(){
        super.onStop();


    }
    protected void onDestroy(){
        super.onDestroy();

    }

    public void notice(final View view) {
        new LicensesDialog.Builder(this).setNotices(R.raw.licenses).build()
                .show();
    }

    public void copying(final View view) {
        final String name = "Sutaek High School Application for Android";
        final String url = "http://kbj9704.blog.me";
        final String copyright = "Copyright (C) 2013 ByoungJune Kim<kbj9704@gmail.com>";
        final License license = new ApacheSoftwareLicense20();
        final Notice notice = new Notice(name, url, copyright, license);
        new LicensesDialog.Builder(this).setNotices(notice).build().show();
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