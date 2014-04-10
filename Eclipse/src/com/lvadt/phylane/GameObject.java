package com.lvadt.phylane;

public class GameObject {

	String name;
	String description;
	double weight;
	double power;
	double density;
	double volume;
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
	
	//Engine constructor, w = engine weight, pow = engine power
	GameObject(String n, double w, double pow, int p, int i){
		name = n;
		weight = w;
		power = pow;
		price = p;
		id = i;
	}
}
