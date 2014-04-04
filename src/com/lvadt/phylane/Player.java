package com.lvadt.phylane;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.lvadt.phylane.Objects.Engine;
import com.lvadt.phylane.Objects.Material;
import com.lvadt.phylane.Objects.Size;
import com.lvadt.phylane.Objects.Special;

import android.content.Context;
import android.util.Log;


//This class will handle everything about the player,
//including saving his/her data
public class Player{
	
	int fileLines = 8; //The number of lines needed in the file
	String fileName = "player.txt"; //Filename to save data to
	//Save a byte as the line separator
	byte[] entr = System.getProperty("line.separator").getBytes();
	
	private String Name;
	private String PlaneName;
	private Objects.Missions currentMission;
	private int money;
	
	//The players inventory of unlocked items
	private List<Engine> engines = new ArrayList<Engine>();
	private List<Material> materials = new ArrayList<Material>();
	private List<Size> sizes = new ArrayList<Size>();
	private List<Special> specials = new ArrayList<Special>();
	
	//Overloaded constructors for just loading a previous game
	//and for starting a completely new one
	public Player(Context context){
		loadData(context);
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
		
		saveData(context);
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
	
	//Save players data to file
	public void saveData(Context context){
		FileOutputStream fos = null;
		String mon = String.valueOf(money);
		try {
			//Write the basic data to the file
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.flush();
			fos.write(Name.getBytes());
			fos.write(entr);
			fos.write(PlaneName.getBytes());
			fos.write(entr);
			fos.write(mon.getBytes());
			fos.write(entr);
			fos.write(currentMission.name().getBytes());
			fos.write(entr);
			
			//Write all of the players inventory
			for(int i = 0; i < engines.size(); i++){
				fos.write(engines.get(i).name().getBytes());
				fos.write(",".getBytes());
			}
			fos.write(entr);
			for(int i = 0; i < materials.size(); i++){
				fos.write(materials.get(i).name().getBytes());
				fos.write(",".getBytes());
			}
			fos.write(entr);
			for(int i = 0; i < sizes.size(); i++){
				fos.write(sizes.get(i).name().getBytes());
				fos.write(",".getBytes());
			}
			fos.write(entr);
			for(int i = 0; i < specials.size(); i++){
				fos.write(specials.get(i).name().getBytes());
				fos.write(",".getBytes());
			}
			
			fos.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Load players data
	public void loadData(Context context){
		//Temporary data storage stuff
		String[] fileData = new String[fileLines];
		String[] es;
		String[] ms;
		String[] ss;
		String[] sps;
		FileInputStream fis = null;
		
		try {
			//Get all the lines in the player data file
			fis = context.openFileInput(fileName);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			for(int i = 0; i < fileLines; i++){
				fileData[i] = bfr.readLine();
				Log.e("Loaded " + String.valueOf(i), fileData[i]);
			}
			
			//Set the length for each string array for player inventory
			es = new String[fileData[4].length() - fileData[4].replace(",", ",").length() + 1];
			ms = new String[fileData[5].length() - fileData[5].replace(",", ",").length() + 1];
			ss = new String[fileData[6].length() - fileData[6].replace(",", ",").length() + 1];
			sps = new String[fileData[7].length() - fileData[7].replace(",", ",").length() + 1];
			
			//Load item data
			es = fileData[4].split(",");
			ms = fileData[5].split(",");
			ss = fileData[6].split(",");
			sps = fileData[7].split(",");
			
			//Load basic values such as name
			Name = fileData[0];
			PlaneName = fileData[1];
			money = Integer.parseInt(fileData[2]);
			currentMission = Objects.Missions.valueOf(fileData[3]);
			
			//Load the players inventory
			for(int i = 0; i < es.length; i++){
				engines.add(Engine.valueOf(es[i]));
			}
			for(int i = 0; i < ms.length; i++){
				materials.add(Material.valueOf(ms[i]));
			}
			for(int i = 0; i < ss.length; i++){
				sizes.add(Size.valueOf(ss[i]));
			}
			for(int i = 0; i < sps.length; i++){
				specials.add(Special.valueOf(sps[i]));
			}
		
			fis.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Make the load/save methods in an async class
	
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
}
