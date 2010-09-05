package com.googlecode.android4cs2.stringlights;

import java.util.ArrayList;
import java.util.HashMap;
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

	final Handler hand = new Handler();
	
	final Runnable updateLights = new Runnable() {
		public void run() {
			updateLightImages();
		}
	};
	
	private LinearLayout linLay;
	private Button random;
	private Button allOn;
	private Button allOff;
	private ToggleButton blink;
	private ArrayList<BulbView> lights;
	private Timer timer;
	private boolean isBlinking = false;

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
        
        timer = new Timer();
        lights = new ArrayList<BulbView>();
        
        linLay = (LinearLayout) findViewById(R.id.linLay);
        
        for (int i = 0; i < 8; i++) {
        	lights.add(new BulbView(this));
        	linLay.addView(lights.get(i));
        }

        random = (Button) findViewById(R.id.random);
        random.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				randomize();
			}
        	
        });
        
        allOn = (Button) findViewById(R.id.allOn);
        allOn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopBlinking();
				for (BulbView b: lights) {
					b.turnOn();
				}
			}
		});
        
        allOff = (Button) findViewById(R.id.allOff);
        allOff.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				stopBlinking();
				for (BulbView b: lights) {
					b.turnOff();
				}
			}
        	
        });
        
        blink = (ToggleButton) findViewById(R.id.blink);
        blink.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((ToggleButton)v).isChecked()) {
					startBlinking();
				} else {
					stopBlinking();
				}
			}
        	
        });
        
    }
    
    public Object onRetainNonConfigurationInstance() {
    	HashMap<Character, Boolean> oldLights = new HashMap<Character, Boolean>();
    	for (BulbView v: lights) {
    		oldLights.put(v.getColor(), v.isOn());
    	}
    	oldLights.put(new Character('X'), isBlinking);
    	return oldLights;
    }
    
    public void randomize() {
		for (BulbView b: lights) {
			b.randomize();
		}
    }
    
    public void startBlinking() {
    	isBlinking = true;
    	timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				hand.post(updateLights);
			}
    		
    	}, 0, 1000);
    }
    
    public void stopBlinking() {
    	isBlinking = false;
    	timer.cancel();
    	timer = new Timer();
    }
    
    public void updateLightImages() {
    	for (BulbView v: lights) {
			if (v.isOn()) {
				v.turnOff();
			} else {
				v.turnOn();
			}
		}
    }
}
