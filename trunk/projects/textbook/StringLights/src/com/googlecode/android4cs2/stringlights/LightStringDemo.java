package com.googlecode.android4cs2.stringlights;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;

/** 
 * LightStringDemo is an Activity which displays four light bulbs which the user can
 * turn on, off, make blink, and randomize.
 * @author jdjennin
 *
 */
public class LightStringDemo extends Activity {
	
	private Button random;
	private ToggleButton blink;

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

        random = (Button) findViewById(R.id.random);
        
        blink = (ToggleButton) findViewById(R.id.blink);
        
    }
}
