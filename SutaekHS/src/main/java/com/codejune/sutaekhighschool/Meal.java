package com.codejune.sutaekhighschool;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import toast.library.meal.MealLibrary;

public class Meal extends ActionBarActivity {

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

        if (!isNetworkConnected(this)) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
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
                            String[] Day = {null, getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednsday), getString(R.string.thursday), getString(R.string.friday)};

                            for (int i = 1; i <= 5; i++) {

                                Lunch[i].setText(Day[i] + ":\n" + lunchstring[i] + "\n" + lmealkcal[i] + "kcal");
                                Dinner[i].setText(Day[i] + ":\n" + dinnerstring[i] + "\n" + dmealkcal[i] + "kcal");

                            }
                            for (int i = 1; i <= 5; i++) {
                                if (lunchstring[i].equals(" ")) {
                                    Lunch[i].setText(Day[i] + ":" + getString(R.string.mealnone));
                                }
                                if (dinnerstring[i].equals(" ")) {
                                    Dinner[i].setText(Day[i] + ":" + getString(R.string.mealnone));

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
}
