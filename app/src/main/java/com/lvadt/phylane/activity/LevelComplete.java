package com.lvadt.phylane.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvadt.phylane.R;

public class LevelComplete extends Activity implements View.OnClickListener {

    //Logic variables
    private int messages;
    private int counter = 0;
    private String messageTitle[];
    private String messageBody[];
    private int messageId[];

    //Display variables
    TextView title;
    TextView msg;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelcomplete);

        RelativeLayout relativeLayout;
        relativeLayout = (RelativeLayout) findViewById(R.id.rlLevelComplete);
        relativeLayout.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tvLevelCompleteTitle);
        msg = (TextView) findViewById(R.id.tvLevelCompleteMessage);
        img = (ImageView) findViewById(R.id.ivLevelComplete);

        //For now only a level complete message
        messages = 1;
        messageTitle = new String[1];
        messageTitle[0] = "Level Complete!";
        messageBody = new String[1];
        messageBody[0] = "Congratulations, you completed a level!";
        messageId = new int[1];
        messageId[0] = -1;

        next();
    }

    @Override
    public void onClick(View v) {
        //No more messages to display
        if(!next()){
            this.finish();
        }
    }

    //Moves the user to the next message
    private boolean next(){
        if(counter == messages){
            return false;
        }
        else{
            title.setText(messageTitle[counter]);
            msg.setText(messageBody[counter]);
            if(messageId[counter] != -1){
                img.setImageResource(messageId[counter]);
            }
            else {
                img.setVisibility(View.GONE);
            }
            counter += 1;
            return true;
        }
    }
}
