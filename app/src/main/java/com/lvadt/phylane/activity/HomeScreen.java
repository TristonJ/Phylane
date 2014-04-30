package com.lvadt.phylane.activity;

import com.lvadt.phylane.utils.Data;
import com.lvadt.phylane.model.Objects.Engine;
import com.lvadt.phylane.model.Objects.Material;
import com.lvadt.phylane.model.Objects.Size;
import com.lvadt.phylane.model.Plane;
import com.lvadt.phylane.model.Player;
import com.lvadt.phylane.R;
import com.lvadt.phylane.utils.Sound;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;

//This is the "HomeScreen" of the app.
//The plane with the start button, customize, ext,
//is here.
public class HomeScreen extends Activity implements OnClickListener{

	//Layout stuff
	//SurfaceView svMain;
	ImageButton ibMissions;
	ImageButton ibStart;
    ImageView ivE;
    ImageView ivM;
    ImageView ivS;
    ImageView ivSP;

    MediaPlayer mp = null;

    //The screen size the menu was designed for
    private static float defX = 1280;
    private static float defY = 720;

    //Ratio to make sure part clicks are in the right area
    float ratioX, ratioY;

	//This is the player object that will be passed around classes
	//from here
	private static Plane plane;
	private static Player player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.homescreen);

		//Load the players data
		player = new Player(this);

		//Load new plane
		plane = new Plane(player.getCurEngine(), player.getCurMaterial(), player.getCurSize());

		//Set up the layout stuff
		//svMain = (SurfaceView) findViewById(R.id.svMain);
		ibMissions = (ImageButton) findViewById(R.id.ibMissions);
		ibStart = (ImageButton) findViewById(R.id.ibTakeoff);
		//svMain.setOnTouchListener(this);
		ibMissions.setOnClickListener(this);
		ibStart.setOnClickListener(this);

        ivE = (ImageView) findViewById(R.id.ivEngine);
        ivM = (ImageView) findViewById(R.id.ivMaterial);
        ivS = (ImageView) findViewById(R.id.ivSize);
        ivSP = (ImageView) findViewById(R.id.ivSpecial);

        ivE.setImageResource(player.getCurEngine().getId());
        ivM.setImageResource(player.getCurMaterial().getId());
        ivS.setImageResource(player.getCurSize().getId());
        ivSP.setImageResource(player.getCurSpecials().get(0).getId());

        ivE.setOnClickListener(this);
        ivM.setOnClickListener(this);
        ivS.setOnClickListener(this);
        ivSP.setOnClickListener(this);

        //Set screen ratios
        ratioX = this.getResources().getDisplayMetrics().widthPixels / defX;
        ratioY = this.getResources().getDisplayMetrics().heightPixels / defY;

        mp = MediaPlayer.create(this, R.raw.maintheme);
        mp.setLooping(true);
        mp.start();

	}
	
	@Override
	protected void onResume() {
		super.onResume();
        ivE.setImageResource(player.getCurEngine().getId());
        ivM.setImageResource(player.getCurMaterial().getId());
        ivS.setImageResource(player.getCurSize().getId());
        ivSP.setImageResource(player.getCurSpecials().get(0).getId());
	}

	@Override
	protected void onPause() {
		super.onPause();
        //Data.SavePlayer(HomeScreen.this, player);
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Data.SavePlayer(HomeScreen.this, player);
    }
	
	public static Plane getPlane(){
		return plane;
	}
	
	public static Player getPlayer(){
		return player;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ibMissions:
			//Display Current mission
			MessagePopup.displayMessage(player.getMission().title, player.getMission().description, this);
			break;
		case R.id.ibTakeoff:
			//Start take off sequence
            Log.i("Test", plane.getMaterial().getName());
			Intent i = new Intent(HomeScreen.this, LoadScreen.class);
			i.putExtra("class", "com.lvadt.phylane.activity.Fly");
			startActivity(i);
            break;
        case R.id.ivEngine:
            Intent e = new Intent(HomeScreen.this, Store.class);
            e.putExtra("type", 0);
            startActivity(e);
            break;
        case R.id.ivMaterial:
            Intent m = new Intent(HomeScreen.this, Store.class);
            m.putExtra("type", 1);
            startActivity(m);
			break;
        case R.id.ivSize:
            Intent s = new Intent(HomeScreen.this, Store.class);
            s.putExtra("type", 2);
            startActivity(s);
            break;
        case R.id.ivSpecial:
            Intent z = new Intent(HomeScreen.this, Store.class);
            z.putExtra("type", 3);
            startActivity(z);
            break;
		}
		
	}
	
	

}
