package com.lvadt.phylane.activity;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.lvadt.phylane.R;

//This class will handle displaying all of the 
//bubble popups in the app using a simple dialog.
//This is (Hopefully) only temporary
public class MessagePopup{
	
	public static void displayMessage(String title, String msg, Context c){
		Dialog dialog = new Dialog(c);
		dialog.setContentView(R.layout.popup);
		TextView Message = (TextView) dialog.findViewById(R.id.tvPopupMessage);
		TextView Title = (TextView) dialog.findViewById(R.id.tvPopupTitle);
		Message.setText(msg);
		Title.setText(title);
		
		dialog.show();
		dialog.dismiss();
	}
}
