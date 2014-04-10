package com.lvadt.phylane;

import android.graphics.Rect;

import com.lvadt.phylane.Objects.Engine;
import com.lvadt.phylane.Objects.Material;
import com.lvadt.phylane.Objects.Size;
import com.lvadt.phylane.Objects.Special;

public class Plane{

	
	private Engine engine;
	private Material material;
	private Size size;
	private Special[] specials;
	static private Sprite sprite;
	
	//Bounding box
	Rect box;
	
	//Physics Stuff
	float x;									//The x and y positions of the plane on the screen 
	float y;									
	float lastX;
	float lastY;
	float velX, velY;							//The velocity in the x and y directions
	double thrust; 								//Force of the planes forward direction
	double angle = 11 * (Math.PI / 180); 		//In radians angle of flight
	double lift; 								//Planes actual upward force - its weight
	double weight;
	
	
	Plane(Engine e, Material m, Size s){
		engine = e;
		material = m;
		size = s;
		//Just in case any specials are not defined
		specials = new Special[]{Special.NONE};
	}
	
	void setSprite(Sprite s){
		sprite = s;
	}
	
	Sprite getSprite(){
		return sprite;
	}
	
	void setEngine(Engine e){
		engine = e;
	}
	
	Engine getEngine(){
		return engine;
	}
	
	void setMaterial(Material m){
		material = m;
	}
	
	Material getMaterial(){
		return material;
	}
	
	void setSize(Size s){
		size = s;
	}
	
	Size getSize(){
		return size;
	}
	
	void setSpecials(Special[] s){
		specials = s;
	}
	
	Special[] getSpecials(){
		return specials;
	}
	
}
