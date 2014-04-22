package com.lvadt.phylane.model;

import java.util.Random;

import android.graphics.Point;

public class Level {

    //Types
    //0-Ground
    //1-Ceiling
    //2-Midair
	private enum Objects{
		MOUNTAIN("mountains.png", 0),
        REVERSMOUNTAIN("mountains_reversed.png", 0),
        TESTCEIL("randomroofobject.png", 1);
	
		String filename;
        int type;

		Objects(String file, int t){
			filename = file;
            type = t;
		}
	}
	
	private enum Backgrounds{
		DEFAULT("fly_background.png");
		
		String filename;
		Backgrounds(String file){
			filename = file;
		}
	}
	
	private enum ParallaxEffects{
		DEFAULT("fly_parallax.png");
		
		String filename;
		ParallaxEffects(String file){
			filename = file;
		}
	}

    //All of these values public for rendering, could add getter/setters
	//All of the objects in the level
	public String[] filenames;
	//The position of every object
	public int[] objX, objY;
    //The point at which the level is over
    public int levelFinish;
	//Background image
	public String background;
	public String parallax = null;
	
	public static Level RandomLevel(Point size, int minObjects, int maxObjects, int Difficulty, int maxHeight){
		Random rand = new Random();
		int numberObj = rand.nextInt((maxObjects - minObjects)+1) + minObjects;
		int values = Objects.values().length;
		int pick, maxDist, minDist;
		
		String[] files = new String[numberObj];
		int[] x = new int[numberObj];
		int[] y = new int[numberObj];
		
		//Generate max distance between each object
		maxDist = 2500 / (Difficulty * 2);
		//Smallest distance
		minDist = 100;
		
		
		//Generate objects
		for(int i = 0; i < numberObj; i++){
			//Get object type
			pick = rand.nextInt(values);

            //Make sure the object is not too close to the player
            if(i > 0)
			    files[i] = Objects.values()[pick].filename;
            else
                files[i] = Objects.values()[0].filename;
			
			//Get position
			if(i > 0){
				x[i] = rand.nextInt((maxDist+x[i-1]) - (x[i-1]+minDist)) + (x[i-1]+minDist);
			}
			//Generate object height
            switch(Objects.values()[pick].type){
                case 0:
                    y[i] = size.y-rand.nextInt(maxHeight);
                    break;
                case 1:
                    //If it is too close to start
                    y[i] = -rand.nextInt(maxHeight);
                    break;
                case 2:
                    y[i] = rand.nextInt(size.y);
                    break;
            }
		}
        //Generate level end, 100 px past last obstacle
        int le = x[numberObj-1]+100;

		Level l = new Level();
		l.filenames = files;
		l.objX = x;
		l.objY = y;
		l.background = Backgrounds.DEFAULT.filename;
		l.parallax = ParallaxEffects.DEFAULT.filename;
        l.levelFinish = le;
		return l;
	}
}
