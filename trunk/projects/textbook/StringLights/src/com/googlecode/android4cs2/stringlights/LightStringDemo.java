package com.googlecode.android4cs2.stringlights;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

/** 
 * LightStringDemo is an Activity which displays four light bulbs which the user can
 * turn on, off, make blink, and randomize.
 * @author jdjennin
 *
 */
public class LightStringDemo extends Activity {

	/**
	 * This acts as an interface for making changes to the UI from outside of the UI thread.
	 */
	final Handler hand = new Handler();
	
	/**
	 * These are the changes to be made using the Handler object.
	 */
	final Runnable updateLights = new Runnable() {
		public void run() {
			for (BulbView v: lights) {
				if (v.isOn()) {
					v.turnOff();
				} else {
					v.turnOn();
				}
			}
		}
	};
	
	/**
	 * This layout holds all of the BulbViews
	 */
	private LinearLayout linLay;
	
	/**
	 * Randomizes the colors of the BulbViews
	 */
	private Button random;
	
	/**
	 * Turns on all lights
	 */
	private Button allOn;
	
	/**
	 * Turns off all lights
	 */
	private Button allOff;
	
	/**
	 * Toggles blinking
	 */
	private ToggleButton blink;
	
	/**
	 * Stores pointers to the BulbViews
	 */
	private ArrayList<BulbView> lights;
	
	/**
	 * A timer to be used when blinking is activated
	 */
	private Timer timer;

	/**
	 * This is the entry point for any Android Activity.
	 * @param savedInstanceState contains data supplied in onSaveInstanceState(Bundle) (null here)
	 * @return void
	 * @see Activity
	 * @see Bundle
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Initialize our Timer and ArrayList
        timer = new Timer();
        lights = new ArrayList<BulbView>();
        
        // Get the layout so that we can add the BulbViews to it
        linLay = (LinearLayout) findViewById(R.id.linLay);
        
        // Set up 8 BulbViews, save them to our ArrayList, and add them to the LinearLayout
        for (int i = 0; i < 8; i++) {
        	lights.add(new BulbView(this));
        	linLay.addView(lights.get(i));
        }

        // Get the randomize Button so that we can define what it's supposed to do
        random = (Button) findViewById(R.id.random);
        random.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				randomize();
			}
        	
        });
        
        // Get the allOn Button so that we can define what it does
        allOn = (Button) findViewById(R.id.allOn);
        allOn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (blink.isChecked()) {
					stopBlinking();
					blink.toggle();
				}
				
				for (BulbView b: lights) {
					b.turnOn();
				}
			}
		});
        
        // Get the allOff Button so that we can define what it does
        allOff = (Button) findViewById(R.id.allOff);
        allOff.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (blink.isChecked()) {
					stopBlinking();
					blink.toggle();
				}
				
				for (BulbView b: lights) {
					b.turnOff();
				}
			}
        	
        });
        
        // Get the blink ToggleButton so that we can set up blinking
        blink = (ToggleButton) findViewById(R.id.blink);
        blink.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// If the ToggleButton was checked...
				if (((ToggleButton)v).isChecked()) {
					// ...start blinking the lights...
					startBlinking();
				} else {
					// ...otherwise, the lights are already blinking and need to be stopped
					stopBlinking();
				}
			}
        	
        });
        
    }
    
    /** Randomizes all the BulbViews
     * @return void
     */
    public void randomize() {
		for (BulbView b: lights) {
			b.randomize();
		}
    }
    
    /** Helper method for starting blinking
     * @return void
     * @see Timer
     * @see TimerTask
     * @see Handler
     */
    public void startBlinking() {
    	// Using our Timer, we set up a TimerTask to be done without delay every 1000 milliseconds
    	timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				/* Here is where our Handler object comes in handy. Pun absolutely intended.
				 * We post the code we defined as the Runnable object updateLights to the Handler
				 * where it is executed. */
				hand.post(updateLights);
			}
    		
    	}, 0, 1000);
    }
    
    /** Helper method for stopping blinking
     * @return void
     * @see Timer
     */
    public void stopBlinking() {
    	timer.cancel();
    	timer = new Timer();
    }
}
