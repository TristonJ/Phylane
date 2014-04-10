package com.lvadt.phylane;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


//This is going to be the activity that leads the user through the tutorial
//Maybe.
public class Tutorial extends Activity implements OnTouchListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String mesg = "Setting Default Info";
		String title = "No Tutorial Yet";
		Player player = new Player("Triston", "TFlier", 100, this);
		MessagePopup.displayMessage(title, mesg, this);
		Intent i = new Intent(Tutorial.this, HomeScreen.class);
		startActivity(i);
		this.finish();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
}
