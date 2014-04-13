package com.lvadt.phylane.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Triston on 4/12/2014.
 * Handles the time between two activities
 */
public class LoadScreen extends Activity implements Runnable{

    boolean loading = true;
    Context context;
    Class<?> target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String classname;
        classname = getIntent().getStringExtra("class");
        try {
            target = Class.forName(classname);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while(loading){
            //Do whatever while loading
            break;
        }
        Intent i = new Intent(LoadScreen.this, target);
        startActivity(i);
        LoadScreen.this.finish();
    }
}
