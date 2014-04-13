package com.lvadt.phylane.model;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;

import com.lvadt.phylane.R;

import java.util.Random;

/**
 * Created by Triston on 4/13/2014.
 * Just a simple class that contains the different loading screens
 */
public class LoadingScreens {

    public static class Screen{
        public int id;
        public String message;
        //Alternate to the default white
        public int color = -1;

        Screen(int i, String m)
        {
            id = i;
            message = m;
        }
        Screen(int i, String m, int c){
            id = i;
            message = m;
            color = c;
        }
    }

    static Screen[] screens = {
            new Screen(R.drawable.load_flyerone,
                    "The first powered plane, the Flyer I, was built by the Wright Brothers in 1903. " +
                    "It weighed only 605 pounds (274kg), and had a maximum speed of 30mph(48 km/h)"),
            new Screen(R.drawable.load_flyerone,
                    "The Flyer I, or the Wright Flyer, was the first engine powered airplane. It was " +
                    "flown a total of 4 times and had its first flight on December 17th, 1903"),
            new Screen(R.drawable.load_lift,
                    "The Wright Brothers used the same formula for lift we use today: " +
                    "L = kSV^2Cl, or Lift equals coefficient of air pressure multiplied by surface area times " +
                    "velocity times the coefficient of lift. Long, I know.", Color.BLACK),
            new Screen(R.drawable.load_lift,
                    "In the lift equation, there are two coefficients, that of air pressure and of lift. " +
                    "The coefficient of air pressure is known as the Smeaton coefficient, and the coefficient " +
                    "of lift depends on the wing shape.", Color.BLACK),
    };

    public static Screen randomLoadScreen(){
        Random rand = new Random();
        int s = rand.nextInt(screens.length);
        return screens[s];
    }


}
