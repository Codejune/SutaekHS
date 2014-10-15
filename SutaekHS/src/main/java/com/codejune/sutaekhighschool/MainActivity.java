package com.codejune.sutaekhighschool;

import com.gc.materialdesign.views.ButtonFloatSmall;
import com.gc.materialdesign.widgets.Dialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;


public class MainActivity extends Activity {

    int backgroundColor = Color.parseColor("#ffED674D");
    ButtonFloatSmall buttonSelectColor;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.notice).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,Notices.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        findViewById(R.id.notice_parent).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,Notices_Parents.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        findViewById(R.id.meal).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,Meal.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        findViewById(R.id.schedule).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,Schedule.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        findViewById(R.id.schoolinfo).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,Schoolinfo.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        findViewById(R.id.schoolintro).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,Schoolintro.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        findViewById(R.id.appinfo).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,Appinfo.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
    }

    // 하드웨어 뒤로가기버튼 이벤트 설정.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            // 하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:

                Dialog dialog = new Dialog(MainActivity.this, "Exit", "?????????");
                dialog.setOnAcceptButtonClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        android.os.Process.killProcess(android.os.Process.myPid());

                    }
                });
                dialog.setOnCancelButtonClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
                dialog.show();


            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
