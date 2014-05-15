package com.lvadt.phylane.model;

import android.graphics.Rect;

import com.lvadt.phylane.graphics.Sprite;
import com.lvadt.phylane.model.Objects.Engine;
import com.lvadt.phylane.model.Objects.Material;
import com.lvadt.phylane.model.Objects.Size;
import com.lvadt.phylane.model.Objects.Special;

import java.util.ArrayList;
import java.util.List;

public class Plane{

	
	private GameObject engine;
	private GameObject material;
	private GameObject size;
	private GameObject[] specials;
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

    /**
     * Create a new plane object
     * @param e its engine
     * @param m its material
     * @param s its size
     */
	public Plane(GameObject e, GameObject m, GameObject s){
		engine = e;
		material = m;
		size = s;
		//Just in case any specials are not defined
		specials = new GameObject[]{Special.NONE.getObj()};
	}

	public void setSprite(Sprite s){
		sprite = s;
	}

	public Sprite getSprite(){
		return sprite;
	}

    /**
     * "Equips" the specified object
     * @param o the object
     * @param t the objects type
     */
    public void set(GameObject o, Objects.Types t){
        switch(t){
            case ENGINE:
                engine = o;
            case MATERIAL:
                material = o;
            case SIZE:
                size = o;
            case SPECIAL:
                //   currentSpecials = Special.values()[x];
        }
    }

    /**
     * "Equips" an array of objects
     * @param o the objects
     * @param t their types
     */
    public void set(GameObject o[], Objects.Types t[]){
        List<GameObject> sp = new ArrayList<GameObject>();
        for(int i = 0; i < t.length; i++){
            switch(t[i]){
                case ENGINE:
                    engine = o[i];
                case MATERIAL:
                    material = o[i];
                case SIZE:
                    size = o[i];
                case SPECIAL:
                    sp.add(o[i]);
            }
        }
        //Convert specials to array
        specials = sp.toArray(new GameObject[sp.size()]);
        sp.clear();
    }
	
	public GameObject getEngine(){
		return engine;
	}
	
	public GameObject getMaterial(){
		return material;
	}
	
	public GameObject getSize(){
		return size;
	}
	
	public GameObject[] getSpecials(){
		return specials;
	}
	
}
