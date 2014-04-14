package com.lvadt.phylane.utils;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Triston on 4/13/2014.
 * Framework to handle swipe events.
 */
public class OnSwipeTouchListener implements View.OnTouchListener{

    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

    public boolean onTouch(final View v, final MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }


    private final class GestureListener extends GestureDetector.SimpleOnGestureListener{
        private static final int THRESHOLD = 100;
        private static final int VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try{
                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();
                if(Math.abs(diffX) > Math.abs(diffY)){
                    if(Math.abs(diffX) > THRESHOLD && Math.abs(velocityX) > VELOCITY_THRESHOLD){
                        if(diffX > 0){
                            onSwipeRight();
                        }
                        else{
                            onSwipeLeft();
                        }
                    }
                }
                else{
                    if(Math.abs(diffY) > THRESHOLD && Math.abs(velocityY) > VELOCITY_THRESHOLD){
                        if(diffY > 0){
                            onSwipeBottom();
                        }
                        else{
                            onSwipeTop();
                        }
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight(){

    }

    public void onSwipeLeft(){

    }

    public void onSwipeTop(){

    }

    public void onSwipeBottom(){

    }
}
