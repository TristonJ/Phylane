package com.lvadt.phylane.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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

    Msg mess;

    public class Msg extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(Message)
                    .setTitle(Title)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            return builder.create();
        }
    }

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
