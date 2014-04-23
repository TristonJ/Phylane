package com.lvadt.phylane.model;

import java.util.ArrayList;
import java.util.List;

import com.lvadt.phylane.utils.Data;
import com.lvadt.phylane.model.Objects.Engine;
import com.lvadt.phylane.model.Objects.Material;
import com.lvadt.phylane.model.Objects.Size;
import com.lvadt.phylane.model.Objects.Special;

import android.content.Context;


//This class will handle everything about the player
public class Player{

	private String Name;
	private String PlaneName;
	private Objects.Missions currentMission;
	private int money;
	
	//The players inventory of unlocked items
	private List<Engine> engines = new ArrayList<Engine>();
	private List<Material> materials = new ArrayList<Material>();
	private List<Size> sizes = new ArrayList<Size>();
	private List<Special> specials = new ArrayList<Special>();

    private Engine currentEngine;
    private Material currentMaterial;
    private Size currentSize;
    private List<Special> currentSpecials = new ArrayList<Special>();
	
	//Overloaded constructors for just loading a previous game
	//and for starting a completely new one
	public Player(Context context){
		Data.LoadPlayer(context, this);
	}
	
	//Create new player completely
	public Player(String n, String pn, int startMoney, Context context){
		Name = n;
		PlaneName = pn;
		money = startMoney;
		currentMission = Objects.Missions.START;
		engines.add(Engine.ONE);
		materials.add(Material.ASH);
		sizes.add(Size.SMALL);
		specials.add(Special.NONE);
        currentEngine = Engine.ONE;
        currentMaterial = Material.ASH;
        currentSize = Size.SMALL;
        currentSpecials.add(Special.NONE);
		
		Data.SavePlayer(context, this);
	}
	
	//Buy functions for purchasing items
	public Boolean buy(Engine e){
		if(money < e.getPrice()){
			return false;
		}
		else{
			money -= e.getPrice();
			addItem(e);
			return true;
		}
	}
	
	public Boolean buy(Material m){
		if(money < m.getPrice()){
			return false;
		}
		else{
			money -= m.getPrice();
			addItem(m);
			return true;
		}
	}
	
	public Boolean buy(Size s){
		if(money < s.getPrice()){
			return false;
		}
		else{
			money -= s.getPrice();
			addItem(s);
			return true;
		}
	}
	
	public Boolean buy(Special s){
		if(money < s.getPrice()){
			return false;
		}
		else{
			money -= s.getPrice();
			addItem(s);
			return true;
		}
	}
	
	//Add a new item to player inventory
	public void addItem(Engine e){
		engines.add(e);
	}
	public void addItem(Material m){
		materials.add(m);
	}
	public void addItem(Size s){
		sizes.add(s);
	}
	public void addItem(Special sp){
		specials.add(sp);
	}
	
	//Test if player has given item
	public Boolean hasItem(Engine e){
		for(int i = 0; i < engines.size(); i++){
			if(e.equals(engines.get(i)))
				return true;
		}
		return false;
	}
	public Boolean hasItem(Material m){
		for(int i = 0; i < materials.size(); i++){
			if(m.equals(materials.get(i)))
				return true;
		}
		return false;
	}
	public Boolean hasItem(Size s){
		for(int i = 0; i < sizes.size(); i++){
			if(s.equals(sizes.get(i)))
				return true;
		}
		return false;
	}
	public Boolean hasItem(Special sp){
		for(int i = 0; i < specials.size(); i++){
			if(sp.equals(specials.get(i)))
				return true;
		}
		return false;
	}
	
	//Moving the player to their next mission
	public void missionComplete(){
		
	}
	
	//Getter / Setter Methods
	public String getPlayerName(){
		return Name;
	}
	
	public String getPlaneName(){
		return PlaneName;
	}
	
	public Objects.Missions getMission(){
		return currentMission;
	}
	
	public void setMission(Objects.Missions m){
		currentMission = m;
	}

    public void setPlayerName(String name){
        Name = name;
    }

    public void setPlaneName(String name){
        PlaneName = name;
    }
	
	public int getMoney(){
		return money;
	}
	
	public void setMoney(int m){
		money = m;
	}

	
	public List<Engine> getEngines(){
		return engines;
	}
	
	public List<Material> getMaterials(){
		return materials;
	}
	
	public List<Size> getSizes(){
		return sizes;
	}
	
	public List<Special> getSpecials(){
		return specials;
	}

    public void equip(Engine e){
        currentEngine = e;
    }
    public void equip(Material m){
        currentMaterial = m;
    }
    public void equip(Size s){
        currentSize = s;
    }
    public void equip(List<Special> s){
        currentSpecials = s;
    }

    public Engine getCurEngine(){
        return currentEngine;
    }
    public Material getCurMaterial(){
        return currentMaterial;
    }
    public Size getCurSize(){
        return currentSize;
    }
    public List<Special> getCurSpecials(){
        return currentSpecials;
    }
}
