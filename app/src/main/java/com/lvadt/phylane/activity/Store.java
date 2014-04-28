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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Store extends Activity implements OnClickListener {

	int select = 0;
	Boolean typeSet = false;
	
	int type;

	ImageView ivItem;
    ImageView ivNext;
    ImageView ivPrev;
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
                moveImage();
            }
            public void onSwipeRight() {
                nextItem();
                moveImage();
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
        case R.id.bEquip:
            equip();
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

    private void moveImage(){
        Animation au = AnimationUtils.loadAnimation(Store.this, R.anim.scaleup);
        Animation ad = AnimationUtils.loadAnimation(Store.this, R.anim.scaledown);
        switch(type){
        case 0:
            ivItem.setImageResource(Engine.values()[select].getId());
            ivNext.startAnimation(au);
            if(select+1 < Engine.values().length) {
                ivNext.setImageResource(Engine.values()[select + 1].getId());
                ivNext.startAnimation(ad);
            }
            else
                ivNext.setImageDrawable(null);
            if(select-1 >= 0){
                ivPrev.setImageResource(Engine.values()[select - 1].getId());
                ivNext.startAnimation(ad);
            }
            else
                ivPrev.setImageDrawable(null);
            break;
        case 1:
            ivItem.setImageResource(Material.values()[select].getId());
            ivNext.startAnimation(au);
            if(select+1 < Material.values().length) {
                ivNext.setImageResource(Material.values()[select + 1].getId());
                ivNext.startAnimation(ad);
            }
            else
                ivNext.setImageDrawable(null);
            if(select-1 >= 0){
                ivPrev.setImageResource(Material.values()[select - 1].getId());
                ivNext.startAnimation(ad);
            }
            else
                ivPrev.setImageDrawable(null);
            break;
        case 2:
            ivItem.setImageResource(Size.values()[select].getId());
            ivNext.startAnimation(au);
            if(select+1 < Size.values().length) {
                ivNext.setImageResource(Size.values()[select + 1].getId());
                ivNext.startAnimation(ad);
            }
            else
                ivNext.setImageDrawable(null);
            if(select-1 >= 0){
                ivPrev.setImageResource(Size.values()[select - 1].getId());
                ivNext.startAnimation(ad);
            }
            else
                ivPrev.setImageDrawable(null);
            break;
        case 3:
            ivItem.setImageResource(Special.values()[select].getId());
            ivNext.startAnimation(au);
            if(select+1 < Special.values().length) {
                ivNext.setImageResource(Special.values()[select + 1].getId());
                ivNext.startAnimation(ad);
            }
            else
                ivNext.setImageDrawable(null);
            if(select-1 >= 0){
                ivPrev.setImageResource(Special.values()[select - 1].getId());
                ivNext.startAnimation(ad);
            }
            else
                ivPrev.setImageDrawable(null);
            break;
        }
    }
	private void init(){
        ivItem = (ImageView) findViewById(R.id.ivItem);
        ivNext = (ImageView) findViewById(R.id.ivNextItem);
        ivPrev = (ImageView) findViewById(R.id.ivPrevItem);
		tvItem = (TextView) findViewById(R.id.tvItem);
		tvPrice = (TextView) findViewById(R.id.tvPrice);
		tvCurMoney = (TextView) findViewById(R.id.tvCurMoney);
		tvCurMoney.setText(String.valueOf(HomeScreen.getPlayer().getMoney()));
		ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		ImageView ivBuy = (ImageView) findViewById(R.id.ivBuy);
		ivBuy.setOnClickListener(this);
        Button equip = (Button) findViewById(R.id.bEquip);
        equip.setOnClickListener(this);
        moveImage();
	}

    private void equip(){
        switch(type){
            case 0:
                if(HomeScreen.getPlayer().hasItem(Engine.values()[select])) {
                    HomeScreen.getPlayer().equip(Engine.values()[select]);
                    HomeScreen.getPlane().setEngine(Engine.values()[select]);
                }
                else
                    MessagePopup.displayMessage("Unable to equip", "You don't have this item!", Store.this);
                break;
            case 1:
                if(HomeScreen.getPlayer().hasItem(Material.values()[select])) {
                    HomeScreen.getPlayer().equip(Material.values()[select]);
                    HomeScreen.getPlane().setMaterial(Material.values()[select]);
                }
                else
                    MessagePopup.displayMessage("Unable to equip", "You don't have this item!", Store.this);
                break;
            case 2:
                if(HomeScreen.getPlayer().hasItem(Size.values()[select])) {
                    HomeScreen.getPlayer().equip(Size.values()[select]);
                    HomeScreen.getPlane().setSize(Size.values()[select]);
                }
                else
                    MessagePopup.displayMessage("Unable to equip", "You don't have this item!", Store.this);
                break;
            case 3:
                //Do nothing for now. Needed support for specials
                //HomeScreen.getPlayer().equip(Special.values()[select]);
                break;
        }
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
