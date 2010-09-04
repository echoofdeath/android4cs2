package com.googlecode.android4cs2.stringlights;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
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

	private LinearLayout linLay;
	private Button random;
	private ToggleButton blink;
	private ArrayList<BulbView> lights;

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
        
        blink = (ToggleButton) findViewById(R.id.blink);
        
    }
    
    public void randomize() {
		for (BulbView v: lights) {
			v.randomize();
		}
    }
}
