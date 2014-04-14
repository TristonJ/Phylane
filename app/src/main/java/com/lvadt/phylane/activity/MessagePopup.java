package com.lvadt.phylane.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvadt.phylane.R;


public class MessagePopup extends Activity implements View.OnClickListener{

    static String Message;
    static String Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rlPopup);
        TextView message = (TextView) findViewById(R.id.tvPopupMessage);
        TextView title = (TextView) findViewById(R.id.tvPopupTitle);
        rl.setOnClickListener(this);
        message.setText(Message);
        title.setText(Title);
    }

    public static void displayMessage(String title, String msg, Context c){
        Message = msg;
        Title = title;

        Intent i = new Intent(c, MessagePopup.class);
        c.startActivity(i);
	}

    @Override
    public void onClick(View v) {
        MessagePopup.this.finish();
    }
}
