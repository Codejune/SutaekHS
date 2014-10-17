package com.codejune.sutaekhighschool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import toast.library.meal.MealLibrary;

public class Meal extends Activity {

    private ProgressDialog progressDialog;
    TextView lunchmon;
    TextView lunchtue;
    TextView lunchwed;
    TextView lunchthu;
    TextView lunchfri;
    TextView dinnermon;
    TextView dinnertue;
    TextView dinnerwed;
    TextView dinnerthu;
    TextView dinnerfri;
    String[] lmealkcal = new String[7];
    String[] dmealkcal = new String[7];
    String[] lunchstring = new String[7];
    String[] dinnerstring = new String[7];

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_meal);
        getActionBar().setDisplayShowHomeEnabled(false);

        if (!isNetworkConnected(this)) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_error)
                    .setTitle("네트워크 연결").setMessage("\n네트워크 연결 상태 확인 후 다시 시도해 주십시요\n")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        } else {

            lunchmon = (TextView) this.findViewById(R.id.lunchmon);
            lunchtue = (TextView) this.findViewById(R.id.lunchtue);
            lunchwed = (TextView) this.findViewById(R.id.lunchwed);
            lunchthu = (TextView) this.findViewById(R.id.lunchthu);
            lunchfri = (TextView) this.findViewById(R.id.lunchfri);
            dinnermon = (TextView) this.findViewById(R.id.dinnermon);
            dinnertue = (TextView) this.findViewById(R.id.dinnertue);
            dinnerwed = (TextView) this.findViewById(R.id.dinnerwed);
            dinnerthu = (TextView) this.findViewById(R.id.dinnerthu);
            dinnerfri = (TextView) this.findViewById(R.id.dinnerfri);
            //dinnertext = (TextView)this.findViewById(R.id.dinner);


            final Handler mHandler = new Handler();
            new Thread() {

                public void run() {
                    mHandler.post(new Runnable() {

                        public void run() {
                            String loading = getString(R.string.loading);
                            progressDialog = ProgressDialog.show(Meal.this, "", loading, true);
                        }
                    });
                    lunchstring = MealLibrary.getMealNew("goe.go.kr", "J100000656", "4", "04", "2"); //Get Lunch Menu Date
                    dinnerstring = MealLibrary.getMealNew("goe.go.kr", "J100000656", "4", "04", "3"); //Get Dinner Menu Date
                    lmealkcal = MealLibrary.getKcalNew("goe.go.kr", "J100000656", "4", "04", "2"); //Get Kcal Data
                    dmealkcal = MealLibrary.getKcalNew("goe.go.kr", "J100000656", "4", "04", "3"); //Get Kcal Data


                    mHandler.post(new Runnable() {
                        public void run() {

                            // Array로 순서화
                            TextView[] Lunch = {null, lunchmon, lunchtue, lunchwed, lunchthu, lunchfri};
                            TextView[] Dinner = {null, dinnermon, dinnertue, dinnerwed, dinnerthu, dinnerfri};
                            for (int i = 1; i <= 5; i++) {

                                Lunch[i].setText(lunchstring[i] + "\n" + lmealkcal[i] + "kcal");
                                Dinner[i].setText(dinnerstring[i] + "\n" + dmealkcal[i] + "kcal");

                            }
                            for (int i = 1; i <= 5; i++) {
                                if (lunchstring[i].equals(" ")) {
                                    Lunch[i].setText(getString(R.string.mealnone));
                                }
                                if (dinnerstring[i].equals(" ")) {
                                    Dinner[i].setText(getString(R.string.mealnone));

                                }

                            }
                            progressDialog.dismiss();
                        }

                    });
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }
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
