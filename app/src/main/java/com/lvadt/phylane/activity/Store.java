package com.lvadt.phylane.activity;

import com.lvadt.phylane.utils.OnSwipeTouchListener;
import com.lvadt.phylane.model.Objects.Engine;
import com.lvadt.phylane.model.Objects.Material;
import com.lvadt.phylane.model.Objects.Size;
import com.lvadt.phylane.model.Objects.Special;
import com.lvadt.phylane.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Store extends Activity implements OnClickListener {

	int select = 0;
	Boolean typeSet = false;
	
	int type;
	
	//No images right now
	//ImageView ivItem;
	TextView tvItem;
	TextView tvPrice;
	TextView tvCurMoney;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store);
		//Initialize all the layout stuff
		init();
		type = getIntent().getIntExtra("type", 0);
		
		tvItem.setText(getName());
		tvPrice.setText("Price: " + String.valueOf(getPrice()));

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rlStore);
        rl.setOnTouchListener(new OnSwipeTouchListener(){
            public void onSwipeLeft(){
                prevItem();
            }
            public void onSwipeRight() {
                nextItem();
            }
        });
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		//case R.id.ivItem:
		//	break;
		case R.id.ivBack:
			this.finish();
			break;
		case R.id.ivBuy:
			buy();
			break;
		}
	}
	
	private Boolean buy(){
		switch(type){
		case 0:
			if(HomeScreen.getPlayer().hasItem(Engine.values()[select]) ||
					!HomeScreen.getPlayer().buy(Engine.values()[select])){
				return false;
			}
			tvCurMoney.setText(String.valueOf(HomeScreen.getPlayer().getMoney()));
			MessagePopup.displayMessage("You bought " + getName(), "It cost " + String.valueOf(getPrice()), Store.this);
			return true;
		case 1:
			if(HomeScreen.getPlayer().hasItem(Material.values()[select]) || 
					!HomeScreen.getPlayer().buy(Material.values()[select])){
				return false;
			}
			tvCurMoney.setText(String.valueOf(HomeScreen.getPlayer().getMoney()));
			MessagePopup.displayMessage("You bought " + getName(), "It cost " + String.valueOf(getPrice()), Store.this);
			return true;
		case 2:
			if(HomeScreen.getPlayer().hasItem(Size.values()[select]) || 
					!HomeScreen.getPlayer().buy(Size.values()[select])){
				return false;
			}
			tvCurMoney.setText(String.valueOf(HomeScreen.getPlayer().getMoney()));
			MessagePopup.displayMessage("You bought " + getName(), "It cost " + String.valueOf(getPrice()), Store.this);
			return true;
		case 3:
			if(HomeScreen.getPlayer().hasItem(Special.values()[select]) || 
					!HomeScreen.getPlayer().buy(Special.values()[select])){
				return false;
			}
			tvCurMoney.setText(String.valueOf(HomeScreen.getPlayer().getMoney()));
			MessagePopup.displayMessage("You bought " + getName(), "It cost " + String.valueOf(getPrice()), Store.this);
			return true;
		}
		return false;
	}
	
	private void nextItem(){
		switch(type){
		case 0:
			select++;
			if(select >= Engine.values().length){
				select = 0;
			}	
			break;
		case 1:
			select++;
			if(select >= Material.values().length){
				select = 0;
			}
			break;
		case 2:
			select++;
			if(select >= Size.values().length){
				select = 0;
			}
			break;
		case 3:
			select++;
			if(select >= Special.values().length){
				select = 0;
			}
			break;
		}
		tvItem.setText(getName());
		tvPrice.setText("Price: " + String.valueOf(getPrice()));
	}
	
	private void prevItem(){
		switch(type){
		case 0:
			select--;
			if(select < 0){
				select = Engine.values().length-1;
			}
			break;
		case 1:
			select--;
			if(select < 0){
				select = Material.values().length-1;
			}
			break;
		case 2:
			select--;
			if(select < 0){
				select = Size.values().length-1;
			}
			break;
		case 3:
			select--;
			if(select < 0){
				select = Special.values().length-1;
			}
			break;
		}
		tvItem.setText(getName());
		tvPrice.setText("Price: " + String.valueOf(getPrice()));
	}
	private void init(){
		tvItem = (TextView) findViewById(R.id.tvItem);
		tvPrice = (TextView) findViewById(R.id.tvPrice);
		//ivItem = (ImageView) findViewById(R.id.//ivItem);
		tvCurMoney = (TextView) findViewById(R.id.tvCurMoney);
		tvCurMoney.setText(String.valueOf(HomeScreen.getPlayer().getMoney()));
		//ivItem.setOnClickListener(this);
		ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		ImageView ivBuy = (ImageView) findViewById(R.id.ivBuy);
		ivBuy.setOnClickListener(this);
	}

	public int getPrice(){
		switch(type){
		default:
			return 0;
		case 0:
			return Engine.values()[select].getPrice();
		case 1:
			return Material.values()[select].getPrice();
		case 2:
			return Size.values()[select].getPrice();
		case 3:
			return Special.values()[select].getPrice();
		}
	}
	
	public String getName(){
		switch(type){
		default:
			return "Fail";
		case 0:
			return Engine.values()[select].getName();
		case 1:
			return Material.values()[select].getName();
		case 2:
			return Size.values()[select].getName();
		case 3:
			return Special.values()[select].getName();
		}
		
	}
}
