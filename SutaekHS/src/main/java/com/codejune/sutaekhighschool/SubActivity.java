package com.codejune.sutaekhighschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SubActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        getActionBar().setDisplayShowHomeEnabled(false);

        findViewById(R.id.notice).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Notices.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.notice_parent).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Notices_Parents.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.meal).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(SubActivity.this, Meal.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.schedule).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Schedule.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.schoolinfo).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Schoolinfo.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.schoolintro).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Schoolintro.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.appinfo).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(SubActivity.this,
                                Appinfo.class);
                        startActivity(intent);
                    }
                });
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
