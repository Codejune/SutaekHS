package com.codejune.sutaekhighschool;

import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Doc_Copying extends SherlockActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        TextView helloTxt = (TextView)findViewById(R.id.doc);
        getActionBar().setDisplayShowHomeEnabled(false);
        helloTxt.setText(readTxt());
    }
    private String readTxt(){
        InputStream inputStream = getResources().openRawResource(R.raw.copying);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString();
    }
}