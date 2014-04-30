package com.lvadt.phylane.model;

import java.util.ArrayList;
import java.util.List;

import com.lvadt.phylane.utils.Data;
import com.lvadt.phylane.model.Objects.Engine;
import com.lvadt.phylane.model.Objects.Material;
import com.lvadt.phylane.model.Objects.Size;
import com.lvadt.phylane.model.Objects.Special;
import com.lvadt.phylane.model.Objects.Types;

import android.content.Context;


//This class will handle everything about the player
public class Player{

	private String Name;
	private String PlaneName;
	private Objects.Missions currentMission;
	private int money;
	
	//The players inventory of unlocked items
	/*private List<Engine> engines = new ArrayList<Engine>();
	private List<Material> materials = new ArrayList<Material>();
	private List<Size> sizes = new ArrayList<Size>();
	private List<Special> specials = new ArrayList<Special>();*/

    private List<GameObject> engines = new ArrayList<GameObject>();
    private List<GameObject> materials = new ArrayList<GameObject>();
    private List<GameObject> sizes = new ArrayList<GameObject>();
    private List<GameObject> specials = new ArrayList<GameObject>();

    /*private Engine currentEngine;
    private Material currentMaterial;
    private Size currentSize;
    private List<Special> currentSpecials = new ArrayList<Special>();*/

    private GameObject currentEngine;
    private GameObject currentMaterial;
    private GameObject currentSize;
    private List<GameObject> currentSpecials = new ArrayList<GameObject>();
	
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
		engines.add(Engine.ONE.getObj());
		materials.add(Material.WOOD.getObj());
		sizes.add(Size.SMALL.getObj());
		specials.add(Special.NONE.getObj());
        currentEngine = Engine.ONE.getObj();
        currentMaterial = Material.WOOD.getObj();
        currentSize = Size.SMALL.getObj();
        currentSpecials.add(Special.NONE.getObj());
		
		Data.SavePlayer(context, this);
	}


    //Get the type and index of the item that the user is trying to buy
    public Boolean buy(GameObject o, Types t) {
        if(money < o.getPrice()){
            return false;
        }
        else{
            money -= o.getPrice();
            addITem(o, t);
            return true;
        }
    }

    public void addITem(GameObject o, Types t){
        switch(t){
            case ENGINE:
                engines.add(o);
            case MATERIAL:
                materials.add(o);
            case SIZE:
                sizes.add(o);
            case SPECIAL:
                specials.add(o);
        }
    }

    //Non specified type, takes longer to calculate
    public Boolean hasItem(GameObject o){
        for(int i = 0; i < engines.size(); i++){
            if(o.equals(engines.get(i)))
                return true;
        }
        for(int i = 0; i < materials.size(); i++){
            if(o.equals(materials.get(i)))
                return true;
        }
        for(int i = 0; i < sizes.size(); i++){
            if(o.equals(sizes.get(i)))
                return true;
        }
        for(int i = 0; i < specials.size(); i++){
            if(o.equals(specials.get(i)))
                return true;
        }
        return false;
    }

    //Specifing type requires less computation
    public Boolean hasItem(GameObject o, Types type) {
        switch (type) {
            case ENGINE:
                for (int i = 0; i < engines.size(); i++) {
                    if (o.equals(engines.get(i)))
                        return true;
                }
                break;
            case MATERIAL:
                for (int i = 0; i < materials.size(); i++) {
                    if (o.equals(materials.get(i)))
                        return true;
                }
                break;
            case SIZE:
                for (int i = 0; i < sizes.size(); i++) {
                    if (o.equals(sizes.get(i)))
                        return true;
                }
                break;
            case SPECIAL:
                for (int i = 0; i < specials.size(); i++) {
                    if (o.equals(specials.get(i)))
                        return true;
                }
                break;
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

	
	public List<GameObject> getEngines(){
		return engines;
	}
	
	public List<GameObject> getMaterials(){
		return materials;
	}
	
	public List<GameObject> getSizes(){
		return sizes;
	}
	
	public List<GameObject> getSpecials(){
		return specials;
	}

    //Any valid type
    public void equip(GameObject o, Types t){
        switch(t){
            case ENGINE:
                currentEngine = o;
                break;
            case MATERIAL:
                currentMaterial = o;
                break;
            case SIZE:
                currentSize = o;
                break;
            case SPECIAL:
                break;
            //   currentSpecials = Special.values()[x];
        }
    }

    //For specials
    public void equip(List<Special> s){
        List<GameObject> t = new ArrayList<GameObject>();
        for(int i = 0; i < s.size(); i++){
            t.add(s.get(i).getObj());
        }
        currentSpecials = t;
    }
    public GameObject getCurEngine(){
        return currentEngine;
    }
    public GameObject getCurMaterial(){
        return currentMaterial;
    }
    public GameObject getCurSize(){
        return currentSize;
    }
    public List<GameObject> getCurSpecials(){
        return currentSpecials;
    }
}
