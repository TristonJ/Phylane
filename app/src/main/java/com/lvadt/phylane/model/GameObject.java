package com.lvadt.phylane.model;

public class GameObject {

    //REAL Name, as in enum name value
    public String rName;
	String name;
	String description;
	double weight;
	double power;
	double density;
	double volume;
    double strength;
	int price;
	int id;
	
	//N = Object Name, wvd = Object Weight, Volume, or Density, p = Price, i = Id
	GameObject(String n, double wvd, int p, int i){
		name = n;
		weight = wvd;
		volume = wvd;
		density = wvd;
		price = p;
		id = i;
	}

    GameObject(String n, double wvd, int p, int i, double str){
        name = n;
        weight = wvd;
        volume = wvd;
        density = wvd;
        price = p;
        id = i;
        strength = str;
    }
	
	//Engine constructor, w = engine weight, pow = engine power
	GameObject(String n, double w, double pow, int p, int i){
		name = n;
		weight = w;
		power = pow;
		price = p;
		id = i;
	}

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getWeight(){
        return weight;
    }

    public double getPower(){
        return power;
    }

    public double getDensity(){
        return density;
    }

    public double getVolume(){
        return volume;
    }

    public int getPrice(){
        return price;
    }

    public int getId(){
        return id;
    }
}
