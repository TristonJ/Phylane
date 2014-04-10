package com.lvadt.phylane;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

//Just a splash screen activity as the app starts
public class Splash extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread timer = new Thread(){
			// To pause on the splash screen for 5s
			public void run(){
				try{
					sleep(5000);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				finally{
					//Start the menu intent
					Intent i = new Intent(Splash.this, MainMenu.class);
					// Start next activity
					startActivity(i);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		//End the splash screen activity
		finish();
	}
	

}
