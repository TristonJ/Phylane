package com.lvadt.phylane.model;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by Triston on 4/29/2014.
 * Less resource heavy "sprite" class, which has no actual graphics
 * processing, mainly used in physics engine
 */
public class WorldObject {
    public float x, y;
    public Rect bounds = new Rect();
    public int ref;

    public WorldObject(float X, float Y, float width, float height, int reference) {
        x = X;
        y = Y;
        bounds.left = (int) x;
        bounds.top = (int) y;
        bounds.right = (int) (x + width);
        bounds.bottom = (int) (y + height);
        ref = reference;
    }

    public Bitmap getBmp(){
        return null;
    }
}
