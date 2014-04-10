package com.lvadt.phylane;

import com.lvadt.phylane.Objects.Engine;
import com.lvadt.phylane.Objects.Material;
import com.lvadt.phylane.Objects.Size;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

//This is the "HomeScreen" of the app.
//The plane with the start button, customize, ext,
//is here.
public class HomeScreen extends Activity implements OnTouchListener, OnClickListener{

	//Layout stuff
	SurfaceView svMain;
	ImageButton ibMissions;
	ImageButton ibStart;
	//This is the player object that will be passed around classes
	//from here
	private static Plane plane;
	private static Player player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.homescreen);
		//Load the players data
		player = new Player(this);
		//Load new plane
		plane = new Plane(Engine.ONE, Material.ASH, Size.SMALL);
		//Set up the layout stuff
		svMain = (SurfaceView) findViewById(R.id.svMain);
		ibMissions = (ImageButton) findViewById(R.id.ibMissions);
		ibStart = (ImageButton) findViewById(R.id.ibTakeoff);
		svMain.setOnTouchListener(this);
		ibMissions.setOnClickListener(this);
		ibStart.setOnClickListener(this);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		//player.saveData(HomeScreen.this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//Get x and y
		float x = event.getX();
		float y = event.getY();
		//Test what area of the screen the user touched
		//When I add intents for each section, make sure to add a put extra for
		//both the item type, and the player reference
		
		//Right quadrant
		if(x < v.getWidth() && x > 960){
			Intent i = new Intent(HomeScreen.this, Store.class);
			i.putExtra("type", 0);
			startActivity(i);
		}
		//Second to right quadrant
		else if (x < 960 && x > 640){
			Intent i = new Intent(HomeScreen.this, Store.class);
			i.putExtra("type", 3);
			startActivity(i);
		}
		//Second in quadrant
		else if (x < 640 && x > 320){
			Intent i = new Intent(HomeScreen.this, Store.class);
			i.putExtra("type", 2);
			startActivity(i);
		}
		//First quadrant
		else{
			Intent i = new Intent(HomeScreen.this, Store.class);
			i.putExtra("type", 1);
			startActivity(i);
		}
		
		return false;
	}
	
	static Plane getPlane(){
		return plane;
	}
	
	static Player getPlayer(){
		return player;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ibMissions:
			//Display Current mission
			MessagePopup.displayMessage(player.getMission().title, player.getMission().description, this);
			break;
		case R.id.ibTakeoff:
			//Start take off sequence
			Intent i = new Intent(HomeScreen.this, Fly.class);
			//i.putExtra("plane", plane);
			startActivity(i);
			
			break;
		}
		
	}
	
	

}