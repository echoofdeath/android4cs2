package com.googlecode.android4cs2.beetlegame;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BeetleGameActivity extends Activity {
    /** Called when the activity is first created. */
	
	private ImageView die;
	private TextView turn;
	private OnClickListener dieListener;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        die = (ImageView) findViewById(R.id.die);
        turn = (TextView) findViewById(R.id.turn);
        
        dieListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch ((int)(Math.random()*6)+1) {
				case 1:
					die.setImageResource(R.drawable.die1);
					break;
				case 2:
					die.setImageResource(R.drawable.die2);
					break;
				case 3:
					die.setImageResource(R.drawable.die3);
					break;
				case 4:
					die.setImageResource(R.drawable.die4);
					break;
				case 5:
					die.setImageResource(R.drawable.die5);
					break;
				case 6:
					die.setImageResource(R.drawable.die6);
					break;
				default:
					break;
				}
			}
        	
        };
        
        die.setOnClickListener(dieListener);
        
    }
}