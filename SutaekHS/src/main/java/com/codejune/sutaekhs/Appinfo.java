/*
 * Zion High School Application for Android
 * Copyright (C) 2013 Youngbin Han<sukso96100@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.codejune.sutaekhs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Appinfo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_appinfo);

        // Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        Boolean Toggle_Boolean = pref.getBoolean("toggledata", false);
        Boolean Toggle_Noti = pref.getBoolean("notitoggle", false);
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

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE); // Save UI State
        SharedPreferences.Editor editor = pref.edit(); // Load Editor
    }
    protected void onDestroy(){
        super.onDestroy();

    }
}