package com.lvadt.phylane.physics;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.lvadt.phylane.graphics.Sprite;
import com.lvadt.phylane.model.Plane;
import com.lvadt.phylane.model.WorldObject;

public class Physics {
	
	List<Sprite> objSpr = new ArrayList<Sprite>();
    List<WorldObject> objects = new ArrayList<WorldObject>();
    static double velScale = 7;         //What to scale the velocity down by
	static double takeOffAngle = .191986;
	static double gravity = 9.81;		//Gravity...
	static double airResistance = .03;	//A constant for now, represented with a % of the forward force
	static int lastScanned = 0;         //Records the last object needed to be checked for collision

    /**
     * Sets the objects for the physics engine
     * @param o list of sprite objects
     * @param wo a list of world objects
     */
	public void setObjects(List<Sprite> o, List<WorldObject> wo){
		for(int i = 0; i < o.size(); i++){
			objSpr.add(o.get(i));
		}
        for(int i = 0; i < wo.size(); i++){
            objects.add(wo.get(i));
        }
	}

    /**
     * An enum to contain return values for failed takeoffs
     */
	public enum Fail{
		LIFT ("LIFT"),
		THRUST ("TRUST");
		
		private final String type;
		Fail(String type){
			this.type = type;
		}
	}

    /**
     * Updates the plane and its flight
     * @param plane the plane object
     * @param dt the delta time
     */
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
		plane.velX = (float) (plane.thrust/(plane.weight/gravity));
		plane.velY = (float) (plane.lift/(plane.weight/gravity));

		plane.velX = (float) ((plane.velX*dt)/velScale);
		plane.velY = (float) ((plane.velY*dt)/(velScale*.35));

		plane.x += plane.velX;
		plane.y += plane.velY*-1;
		
		//Make sure plane stays in screen bounds
		if(plane.y <= 0){
			plane.y = 0;
		}
	}

    /**
     * Tests for collision between the plane and another object
     * @param size the screen size
     * @param plane the plane object
     * @param pSprite the sprite of the plane
     * @return returns true if there is a collision
     */
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
		for(int i = lastScanned; i < objects.size(); i++){
			if(objects.get(i).x < plane.x - size.x/2){
				lastScanned = i;
			}
			else if(objects.get(i).x < plane.x + size.x/2){
				Rect o = objects.get(i).bounds;

				//Object seems to be intercepting
				if(Rect.intersects(p, o)){
					Rect cBounds = getCollisionBounds(p, o);
					//Check pixels
					for(int j = cBounds.left; j < cBounds.right; j++){
						for(int z = cBounds.top; z < cBounds.bottom; z++){
							int bmpPixel1 = objSpr.get(objects.get(i).ref).bmp.getPixel((int) (j-objects.get(i).x),
                                    (int) (z-objects.get(i).y));
							int bmpPixel2 = pSprite.bmp.getPixel((int)(j-pX),(int)(z-pY));
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

    /**
     * Returns the bounds of colliding rects
     * @param a rect a
     * @param b rect b
     * @return the new collision rect
     */
	private Rect getCollisionBounds(Rect a, Rect b){
		int left = (int) Math.max(a.left, b.left);
	    int top = (int) Math.max(a.top, b.top);
	    int right = (int) Math.min(a.right, b.right);
	    int bottom = (int) Math.min(a.bottom, b.bottom);
	    return new Rect(left, top, right, bottom);
	}

    /**
     * Tests if a pixel is transparent
     * @param px the pixel
     * @return true if it is transparent
     */
	private boolean isFilled(int px){
		if(px != Color.TRANSPARENT)
			return true;
		else
			return false;	
	}


    /**
     * Determines if the plane will be able to successfully takeoff
     * @param plane The plane object
     * @param ret a return value for any failed takeoffs
     * @return true for a successful takeoff
     */
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

    /**
     * Returns the plane weight as a double
     * @param plane the plane object
     * @return the planes weight
     */
	public static double getPlaneWeight(Plane plane){
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

    /**
     * Returns the lift of the plane
     * @param plane the plane object
     * @return the lift
     */
    public static double getPlaneLift(Plane plane){
        return plane.getEngine().getPower() * Math.sin(takeOffAngle);
    }

    /**
     * Returns the planes thrust
     * @param plane the plane object
     * @return the thrust
     */
    public static double getPlaneThrust(Plane plane){
        double thrust = 0;
        thrust = (plane.getEngine().getPower() * Math.cos(takeOffAngle));
        thrust -= (plane.thrust*airResistance);
        if(thrust < 0)
            return 0;
        return thrust;
    }
	

}