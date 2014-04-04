package com.lvadt.phylane;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

//May eventually change to a OnTouchListener depending on how the menu
//turns out
public class MainMenu extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		ImageView s = (ImageView) findViewById(R.id.ivStart);
		ImageView o = (ImageView) findViewById(R.id.ivOption);
		ImageView q = (ImageView) findViewById(R.id.ivQuit);
		s.setOnClickListener(this);
		o.setOnClickListener(this);
		q.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ivStart:
			SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			boolean tutorial = getPrefs.getBoolean("tut_enable", true);
			if(tutorial){
				//If the tutorial is enabled, start it, disable the preference
				getPrefs.edit().putBoolean("tut_enable", false);
				getPrefs.edit().apply();
				MainMenu.this.finish();
				Intent i = new Intent(MainMenu.this, Tutorial.class);
				startActivity(i);
			}
			else{
				//Later this should directly start the game where the
				//User left off
				StartGame();
				MainMenu.this.finish();
			}
			break;
		case R.id.ivOption:
			Intent i = new Intent(MainMenu.this, Prefs.class);
			startActivity(i);
			break;
		case R.id.ivQuit:
			super.finish();
			break;
		}
	}

	public void StartGame(){
		Intent i = new Intent(MainMenu.this, HomeScreen.class);
		startActivity(i);
	}
}
