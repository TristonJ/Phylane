package com.lvadt.phylane.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.lvadt.phylane.R;
import com.lvadt.phylane.graphics.GLRenderer;
import com.lvadt.phylane.model.Level;
import com.lvadt.phylane.Physics;
import com.lvadt.phylane.model.Plane;

//This is where the player is actually flying
public class Fly extends Activity implements Runnable, OnTouchListener{
	
	//Level
	Level level;
	
	//Graphics
	GLSurfaceView glsurface;
	GLRenderer glrenderer;
	Point size;
	
	//Phyiscs
	static Physics phyEngine;

	//Thread and update stuff	
	Thread t;
	Boolean pause = false;
	final double HZ = 30;								//The game update rate
	final double FPS = 60;								//The game render rate
	final double UPDATE_TIME = 1000000000 / HZ;			//Time between updates
	final double RENDER_TIME = 1000000000 / FPS;		//Time between renders
	double prevUpdateTime = System.nanoTime();			//The previous time was measured
	boolean listening = true;							//Tells the ontouchlistener to listen or not
	
	//Plane stuff
	Plane plane;
	
	//Touch 
	double maxAngle = -.7; 							//This is the maximum angle a user can reach
	double minAngle = .7; 							//Minimum angle the user can reach
	double angleChange = 0.5; 						//The angle changes radians every second
	Boolean updateAngle = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Get display Size
		Display display = getWindowManager().getDefaultDisplay();
		size = new Point();
		display.getSize(size);
		//Init level
		level = Level.RandomLevel(size, 5, 15, 1, 200);
		//Load opengl stuff
		glsurface = new GLSurfaceView(Fly.this);
		glrenderer = new GLRenderer(Fly.this, glsurface, level);
		glsurface.setRenderer(glrenderer);
		glsurface.setOnTouchListener(this);
		
		setContentView(glsurface);
        //Initialize variables
		init();
		
		phyEngine = new Physics();
		//Start Takeoff, plane goes through physics test
		String[] ret = {"a","b"};
		Boolean fly = phyEngine.TakeOff(plane, ret);
		TakeOff to = new TakeOff(fly);
		//The "to" object would normally be used
		if(!fly){
			Thread.currentThread().interrupt();
			this.finish();
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(!listening)
			return false;
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			updateAngle = true;
			break;
		case MotionEvent.ACTION_UP:
			updateAngle = false;
			plane.angle = 0;
			break;
		}
		if(updateAngle){
			if(plane.angle >= minAngle){
				plane.angle = minAngle;
			}
			//Checks when the last update was
			else{
				plane.angle = plane.angle + angleChange;
			}
		}
		else
			plane.angle = 0;
		if(glrenderer.getPlaneSprite() != null)
			glrenderer.getPlaneSprite().rot = (float) plane.angle;
		return true;
	}


	//Run and update
	@Override
	public void run() {
		while (!pause) {
			double curTime = System.nanoTime();
			double deltaTime = (curTime - prevUpdateTime)/UPDATE_TIME;
			if(glrenderer.getPlaneSprite() != null){
				phyEngine.update(plane, deltaTime);
				if(phyEngine.collision(size, plane, glrenderer.getPlaneSprite())){
					//Hit something, end level
					listening = false;
					pause = true;
					this.finish();
					break;
				}
				glrenderer.getPlaneSprite().x = plane.x;
				glrenderer.getPlaneSprite().y = plane.y;
                if(plane.x >= level.levelFinish){
                    //Player reached the end of the level
                    Intent i = new Intent(Fly.this, LevelComplete.class);
                    startActivity(i);
                    listening = false;
                    pause = true;
                    this.finish();
                }
			}
			prevUpdateTime = curTime;
		}
	}

	public void draw(){
		
	}
	
	private void init(){
		plane = HomeScreen.getPlane();
		plane.x = 0;
		plane.y = 0;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		glsurface.onPause();
		pause = true;
		pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		glsurface.onResume();
		pause = false;
		resume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		glrenderer.destroy();
	}

	private void pause(){
		while (true) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		t = null;
	}
	
	private void resume(){
		t = new Thread(this);
		t.start();
	}
	
	static public Physics getEngine(){
		return phyEngine;
	}
	
	class TakeOff implements Runnable{

		Boolean takeoff;
		Boolean running = true;
		TakeOff(Boolean t){
			takeoff = t;
		}
		
		@Override
		public void run() {
			//Do takeoff sequence
			if(takeoff){
				while(running){
					//No animations currently
					running = false;
				}
			}
			//Do crash sequence
			if(!takeoff){
				while(running){
					//No animations currently
					running = false;
				}
			}
		}
	}
}
