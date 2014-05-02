package com.codejune.sutaekhighschool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.codejune.sutaekhighschool.R;

public class Schoolinfo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_schoolinfo);

        View maps_card = findViewById(R.id.maps_card);
        TextView homepage = (TextView)findViewById(R.id.homepage);

        maps_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri.parse(
                        "https://maps.google.com/maps?t=m&q=수택고등학교&output=classic"
                ));
                startActivity(src);
            }
        });

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri.parse(
                        "http://sutaek.hs.kr"
                ));
                startActivity(src);
            }
        });

    }





}
