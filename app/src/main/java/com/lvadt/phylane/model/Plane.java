package com.lvadt.phylane.model;

import android.graphics.Rect;

import com.lvadt.phylane.graphics.Sprite;
import com.lvadt.phylane.model.Objects.Engine;
import com.lvadt.phylane.model.Objects.Material;
import com.lvadt.phylane.model.Objects.Size;
import com.lvadt.phylane.model.Objects.Special;

public class Plane{

	
	private Engine engine;
	private Material material;
	private Size size;
	private Special[] specials;
	static private Sprite sprite;
	
	//Bounding box
	Rect box;
	
	//Physics Stuff, so no specific getters/setters because it would just be tedious and purposeless
	public float x;									//The x and y positions of the plane on the screen
	public float y;
	public float lastX;
	public float lastY;
	public float velX, velY;							//The velocity in the x and y directions
	public double thrust; 								//Force of the planes forward direction
	public double angle = 11 * (Math.PI / 180); 		//In radians angle of flight
	public double lift; 								//Planes actual upward force - its weight
	public double weight;
	
	
	public Plane(Engine e, Material m, Size s){
		engine = e;
		material = m;
		size = s;
		//Just in case any specials are not defined
		specials = new Special[]{Special.NONE};
	}
	
	public void setSprite(Sprite s){
		sprite = s;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public void setEngine(Engine e){
		engine = e;
	}
	
	public Engine getEngine(){
		return engine;
	}
	
	public void setMaterial(Material m){
		material = m;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public void setSize(Size s){
		size = s;
	}
	
	public Size getSize(){
		return size;
	}
	
	public void setSpecials(Special[] s){
		specials = s;
	}
	
	public Special[] getSpecials(){
		return specials;
	}
	
}
