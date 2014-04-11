package com.lvadt.phylane;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.lvadt.phylane.graphics.Sprite;
import com.lvadt.phylane.model.Plane;

public class Physics {
	
	List<Sprite> obj = new ArrayList<Sprite>();
	static double maxVel = 0.005;		//Max velocity for the plane
	static double takeOffAngle = .191986;
	static double gravity = 9.81;		//Gravity...
	static double airResistance = .03;	//A constant for now, represented with a % of the forward force
	static int lastScanned = 0;
	
	public void setObjects(List<Sprite> o){
		for(int i = 0; i < o.size(); i++){
			obj.add(o.get(i));
		}
	}
	//This enum will basically contain string values for fail takeoffs
	public enum Fail{
		LIFT ("LIFT"),
		THRUST ("TRUST");
		
		private final String type;
		Fail(String type){
			this.type = type;
		}
	}
	
	//Put this in its own thread WITH the draw. Call this first
	//dt is the time since last update. Used to calculate velocity
	public void update(Plane plane, double dt){
		
		plane.lastX = plane.x;
		plane.lastY = plane.y;
		
		//Set the planes thrust
		plane.thrust = (plane.getEngine().getPower() * Math.cos(plane.angle));
		plane.thrust -= (plane.thrust*airResistance);
		
		//Set the planes lift
		plane.lift = (plane.getEngine().getPower() * Math.sin(plane.angle));//-plane.weight;
		plane.lift -= plane.weight;
		
		//Calculate velocity x and y using time
		plane.velX = (float) (plane.thrust/(plane.weight/9.81));
		plane.velY = (float) (plane.lift/(plane.weight/9.81));
		if(plane.velX > maxVel){
			plane.velX = (float) maxVel;
		}
		else
			plane.velX = (float) (plane.velX*dt);
		if(plane.velY > maxVel*1.5){
			plane.velY =  (float) (maxVel*1.5);
		}
		else	
			plane.velY = (float) (plane.velY*dt);
		
		plane.x += plane.velX;
		plane.y += plane.velY*-1;
		
		//Make sure plane stays in screen bounds
		if(plane.y <= 0){
			plane.y = 0;
		}
	}
	
	public boolean collision(Point size, Plane plane, Sprite pSprite){
		if(plane.y > size.y){
			return true;
		}
		Rect p = new Rect();
		p.left = (int) plane.x;
		p.top = (int) plane.y;
		p.right = (int) (plane.x+pSprite.width);
		p.bottom = (int) (plane.y+pSprite.height);
		int pX = (int) plane.x;
		int pY = (int) plane.y;
		
		//Get objects for scan
		for(int i = lastScanned; i < obj.size(); i++){
			if(obj.get(i).x < plane.x - size.x/2){
				lastScanned = i;
			}
			else if(obj.get(i).x < plane.x + size.x/2){
				Rect o = new Rect();
				o.left = (int) obj.get(i).x;
				o.top = (int) obj.get(i).y;
				o.right = (int) (obj.get(i).x + obj.get(i).width);
				o.bottom = (int) (obj.get(i).y + obj.get(i).height);
				//Object seems to be intercepting
				if(Rect.intersects(p, o)){
					Rect cBounds = getCollisionBounds(p, o);
					//Check pixels
					for(int j = cBounds.left; j < cBounds.right; j++){
						for(int z = cBounds.top; z < cBounds.bottom; z++){
							int bmpPixel1 = obj.get(i).bmp.getPixel((int) (j-obj.get(i).x), (int) (z-obj.get(i).y));
							int bmpPixel2 = pSprite.bmp.getPixel((int) (j-pX), (int) (z-pY));
							if(isFilled(bmpPixel1)){
								if(isFilled(bmpPixel2)){
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private Rect getCollisionBounds(Rect a, Rect b){
		int left = (int) Math.max(a.left, b.left);
	    int top = (int) Math.max(a.top, b.top);
	    int right = (int) Math.min(a.right, b.right);
	    int bottom = (int) Math.min(a.bottom, b.bottom);
	    return new Rect(left, top, right, bottom);
	}
	private boolean isFilled(int px){
		if(px != Color.TRANSPARENT)
			return true;
		else
			return false;	
	}
	
	
	//This determines if the users plane will successfully take off
	//ret[] is essentially a return value
	public boolean TakeOff(Plane plane, String ret[]){
		
		plane.weight = getPlaneWeight(plane);
		
		//Calculate the planes upward force, check if it is enough, then get lift
		plane.lift = (plane.getEngine().getPower() * Math.sin(takeOffAngle));
		Log.i("Plane", "Weight: " + String.valueOf(plane.weight) + " Thrust: " + String.valueOf(plane.thrust) + " Lift: " + String.valueOf(plane.lift));
		if(plane.lift <= plane.weight){
			ret[0] = Fail.LIFT.type;
			return false;
		}
		plane.lift -= plane.weight;
		
		//Calculate the planes directional force
		plane.thrust = (plane.getEngine().getPower() * Math.cos(takeOffAngle));
		
		//Compensate for the % of drag
		plane.thrust -= (plane.thrust*airResistance);
		
		//Check if directional force is positive
		if(plane.thrust <= 0){
			ret[0] = Fail.THRUST.type;
			return false;
		}
		
		return true;
	}
	
	//This method gets the planes weight as a double
	public double getPlaneWeight(Plane plane){
		double weight = 0;
		
		//Engine weight + (Density*Volume*Gravity)
		weight = plane.getEngine().getWeight() + ((plane.getMaterial().getDensity()*plane.getSize().getVolume())*gravity);
		//Add in the weight of mission specific items
		for(int i = 0; i < plane.getSpecials().length; i++){
			weight += plane.getSpecials()[i].getWeight();
		}
		
		plane.weight = weight;
		return weight;
	}
	

}