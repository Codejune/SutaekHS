package com.codejune.sutaekhighschool;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class Appinfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_appinfo);

        //Get app version name from Manifest
        String app_ver = null;
        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        //Set app version name text
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

        TextView readme = (TextView)findViewById(R.id.readme);
        readme.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readme = new Intent(Appinfo.this, Doc_Readme.class);
                startActivity(readme);
            }
        });

        TextView notices = (TextView)findViewById(R.id.notices);
        notices.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notices = new Intent(Appinfo.this, Doc_Notices.class);
                startActivity(notices);
            }
        });

        TextView copying = (TextView)findViewById(R.id.copying);
        copying.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent copying = new Intent(Appinfo.this, Doc_Copying.class);
                startActivity(copying);
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
}