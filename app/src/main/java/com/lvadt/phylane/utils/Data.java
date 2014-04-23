package com.lvadt.phylane.utils;

import android.content.Context;
import android.util.Log;

import com.lvadt.phylane.model.Objects;
import com.lvadt.phylane.model.Player;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Triston on 4/10/2014.
 * This class is meant to handle the loading/saving of data
 * Including things such as player inventory
 */
public class Data {

    final static int fileLines = 10; //The number of lines needed in the file
    final static String fileName = "player.txt"; //Filename to save data to
    //Save a byte as the line separator
    final static byte[] entr = System.getProperty("line.separator").getBytes();

    //Save players data to file
    static public void SavePlayer(Context context, Player player) {
        Log.i("Player", "Saving Data");
        FileOutputStream fos = null;
        try {
            //Write the basic data to the file
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.flush();
            fos.write(player.getPlaneName().getBytes());
            fos.write(entr);
            fos.write(player.getPlaneName().getBytes());
            fos.write(entr);
            fos.write(String.valueOf(player.getMoney()).getBytes());
            fos.write(entr);
            fos.write(player.getMission().name().getBytes());
            fos.write(entr);

            //Write all of the players inventory
            for(int i = 0; i < player.getEngines().size(); i++){
                fos.write(player.getEngines().get(i).name().getBytes());
                fos.write(",".getBytes());
            }
            fos.write(entr);
            for(int i = 0; i < player.getMaterials().size(); i++){
                fos.write(player.getMaterials().get(i).name().getBytes());
                fos.write(",".getBytes());
            }
            fos.write(entr);
            for(int i = 0; i < player.getSizes().size(); i++){
                fos.write(player.getSizes().get(i).name().getBytes());
                fos.write(",".getBytes());
            }
            fos.write(entr);
            for(int i = 0; i < player.getSpecials().size(); i++){
                fos.write(player.getSpecials().get(i).name().getBytes());
                fos.write(",".getBytes());
            }
            fos.write(entr);

            //Save equipped items
            fos.write(player.getCurEngine().name().getBytes());
            fos.write(",".getBytes());
            fos.write(player.getCurMaterial().name().getBytes());
            fos.write(",".getBytes());
            fos.write(player.getCurSize().name().getBytes());
            fos.write(",".getBytes());
            fos.write(entr);
            for(int i = 0; i < player.getCurSpecials().size(); i++){
                fos.write(player.getCurSpecials().get(i).name().getBytes());
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

    //Load players data from default file
    static public void LoadPlayer(Context context, Player player){
        //Temporary data storage stuff
        String[] fileData = new String[fileLines];
        String[] es;
        String[] ms;
        String[] ss;
        String[] sps;
        String[] cur = new String[3];
        String[] curS;
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
            curS = new String[fileData[9].length() - fileData[9].replace(",", ",").length() + 1];

            //Load item data
            es = fileData[4].split(",");
            ms = fileData[5].split(",");
            ss = fileData[6].split(",");
            sps = fileData[7].split(",");

            //Equipped items
            cur = fileData[8].split(",");
            curS = fileData[9].split(",");

            //Load basic values such as name
            player.setPlayerName(fileData[0]);
            player.setPlaneName(fileData[1]);
            player.setMoney(Integer.parseInt(fileData[2]));
            player.setMission(Objects.Missions.valueOf(fileData[3]));

            //Load the players inventory
            for(int i = 0; i < es.length; i++){
                player.getEngines().add(Objects.Engine.valueOf(es[i]));
            }
            for(int i = 0; i < ms.length; i++){
                player.getMaterials().add(Objects.Material.valueOf(ms[i]));
            }
            for(int i = 0; i < ss.length; i++){
                player.getSizes().add(Objects.Size.valueOf(ss[i]));
            }
            for(int i = 0; i < sps.length; i++){
                player.getSpecials().add(Objects.Special.valueOf(sps[i]));
            }

            player.equip(Objects.Engine.valueOf(cur[0]));
            player.equip(Objects.Material.valueOf(cur[1]));
            player.equip(Objects.Size.valueOf(cur[2]));
            //New array of specials equipped
            List<Objects.Special> tempSp = new ArrayList<Objects.Special>();
            for(int i = 0; i < curS.length; i++){
                tempSp.add(Objects.Special.valueOf(curS[i]));
            }
            player.equip(tempSp);

            fis.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
