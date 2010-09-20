package com.googlecode.android4cs2.beetlegame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BeetleGameActivity extends Activity {
    /** Called when the activity is first created. */
	
	private ImageView die;
	private TextView turn;
	private LinearLayout l;
	private BeetleGame bg;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        turn = (TextView) findViewById(R.id.turn);
        l = (LinearLayout) findViewById(R.id.linLay);
        
        die = new DieView(this);
        l.addView(die);
        
        
    }
}