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

package com.codejune.sutaekhighschool;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View notices = findViewById(R.id.notices);
        View schoolinfo = findViewById(R.id.schoolinfo);
        View appinfo = findViewById(R.id.info);
        View meal = findViewById(R.id.meal);
        View schedule = findViewById(R.id.schedule);
        View schoolintro = findViewById(R.id.schoolintro);
        View notices_parents = findViewById(R.id.notices_parents);

        if( !isNetworkConnected(this) ){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("네트워크 연결 오류").setMessage("네트워크 연결 상태 확인 후 다시 시도해 주십시요.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick( DialogInterface dialog, int which )
                        {
                            finish();
                        }
                    }).show();
        }


        notices.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notices.class);
                startActivity(intent);
            }
        });

        meal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Meal.class);
                startActivity(intent);
            }
        });

        schoolinfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Schoolinfo.class);
                startActivity(intent);
            }
        });

       schedule.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Schedule.class);
                startActivity(intent);
            }
        });

        appinfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Appinfo.class);
                startActivity(intent);
            }
        });

        schoolintro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Schoolintro.class);
                startActivity(intent);
            }
        });
        notices_parents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notices_Parents.class);
                startActivity(intent);
            }
        });

    }
    // 하드웨어 뒤로가기버튼 이벤트 설정.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            //하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:

                new AlertDialog.Builder(this)
                        .setTitle("어플리케이션 종료")
                        .setMessage("어플리케이션을 종료합니다")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 프로세스 종료.
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    //인터넷 연결 상태 체크
    public boolean isNetworkConnected(Context context){
        boolean isConnected = false;

        ConnectivityManager manager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobile.isConnected() || wifi.isConnected()){
            isConnected = true;
        }else{
            isConnected = false;
        }
        return isConnected;
    }


}
