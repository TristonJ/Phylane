package com.lvadt.phylane.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvadt.phylane.R;
import com.lvadt.phylane.model.LoadingScreens;

/**
 * Created by Triston on 4/12/2014.
 * Handles the time between two activities
 */
public class LoadScreen extends Activity implements Runnable{

    Class<?> target;
    TextView message;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loadscreen);

        image = (ImageView) findViewById(R.id.ivLoadingScreen);
        message = (TextView) findViewById(R.id.tvLoadFact);

        LoadingScreens.Screen s = LoadingScreens.randomLoadScreen();
        image.setImageResource(s.id);
        message.setText(s.message);
        if(s.color != -1){
            message.setTextColor(s.color);
        }

        String classname;
        classname = getIntent().getStringExtra("class");
        try {
            target = Class.forName(classname);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        //Thread that just loads image and message for now
        Thread t = new Thread(this);
        t.start();

    }

    @Override
    public void run() {
        synchronized(this){
            try {
                //this.wait(3000);
                Intent i = new Intent(LoadScreen.this, target);
                //Loads aren't currently that long
                startActivity(i);
                LoadScreen.this.finish();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
