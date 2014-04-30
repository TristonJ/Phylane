package com.lvadt.phylane.activity;

import com.lvadt.phylane.model.GameObject;
import com.lvadt.phylane.model.Objects;
import com.lvadt.phylane.utils.OnSwipeTouchListener;
import com.lvadt.phylane.model.Objects.Engine;
import com.lvadt.phylane.model.Objects.Material;
import com.lvadt.phylane.model.Objects.Size;
import com.lvadt.phylane.model.Objects.Special;
import com.lvadt.phylane.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
		
		tvItem.setText(getObject().getName());
		tvPrice.setText("Price: " + String.valueOf(getObject().getPrice()));

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rlStore);

        moveImage();
        rl.setOnTouchListener(new OnSwipeTouchListener(){
            public void onSwipeLeft(){
                nextItem();
            }
            public void onSwipeRight() {
                prevItem();
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
        if(HomeScreen.getPlayer().hasItem(getObject(), getType()) ||
                !HomeScreen.getPlayer().buy(getObject(), getType())){
            return false;
        }
        tvCurMoney.setText(String.valueOf(HomeScreen.getPlayer().getMoney()));
        MessagePopup.displayMessage("You bought " + getObject().getName(), "It cost " +
        String.valueOf(getObject().getPrice()), Store.this);
        return true;
	}
	
	private void nextItem(){
        select++;
        if(select >= getLen())
            select = 0;
		tvItem.setText(getObject().getName());
		tvPrice.setText("Price: " + String.valueOf(getObject().getPrice()));
        moveImage();
	}
	
	private void prevItem(){
        select--;
        if(select < 0)
            select = getLen()-1;
		tvItem.setText(getObject().getName());
		tvPrice.setText("Price: " + String.valueOf(getObject().getPrice()));
        moveImage();
	}

    private void moveImage(){
        ivItem.setImageResource(getObject().getId());
        if(select+1 < getLen())
            ivNext.setImageResource(getObject(select + 1).getId());
        else
            ivNext.setImageDrawable(null);
        if(select-1 >= 0)
            ivPrev.setImageResource(getObject(select - 1).getId());
        else
            ivPrev.setImageDrawable(null);
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
        ivNext.setScaleX(.5f);
        ivNext.setScaleY(.5f);
        ivPrev.setScaleX(.5f);
        ivPrev.setScaleY(.5f);
        moveImage();
	}

    private void equip(){
        //Specials are currently unsupported
        if(HomeScreen.getPlayer().hasItem(getObject(), getType())){
            HomeScreen.getPlayer().equip(getObject(), getType());
            HomeScreen.getPlane().set(getObject(), getType());
        }
    }

    private GameObject getObject(){
        switch(type){
            case 0:
                return Engine.values()[select].getObj();
            case 1:
                return Material.values()[select].getObj();
            case 2:
                return Size.values()[select].getObj();
            case 3:
                return Special.values()[select].getObj();
            default:
                return Engine.values()[0].getObj();
        }
    }

    private GameObject getObject(int o){
        switch(type){
            case 0:
                return Engine.values()[o].getObj();
            case 1:
                return Material.values()[o].getObj();
            case 2:
                return Size.values()[o].getObj();
            case 3:
                return Special.values()[o].getObj();
            default:
                return Engine.values()[0].getObj();
        }
    }

    private int getLen(){
        switch(type){
            case 0:
                return Engine.values().length;
            case 1:
                return Material.values().length;
            case 2:
                return Size.values().length;
            case 3:
                return Special.values().length;
            default:
                return 0;
        }
    }

    private Objects.Types getType(){
        switch(type){
            case 0:
                return Objects.Types.ENGINE;
            case 1:
                return Objects.Types.MATERIAL;
            case 2:
                return Objects.Types.SIZE;
            case 3:
                return Objects.Types.SPECIAL;
            default:
                return null;
        }
    }
}
