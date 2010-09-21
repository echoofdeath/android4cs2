package com.googlecode.android4cs2.beetlegame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class BeetleGameActivity extends Activity {
    /** Called when the activity is first created. */
	
	private TextView turn;
	private DieView dv;
	private BeetleView surface1;
	private BeetleView surface2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        turn = (TextView) findViewById(R.id.turn);
        surface1 = (BeetleView) findViewById(R.id.bug_one);
        surface2 = (BeetleView) findViewById(R.id.bug_two);
        dv = (DieView) findViewById(R.id.die);
        
        surface1.init(dv.getDie());
        surface2.init(dv.getDie());
        
        dv.setBugSurfaces(surface1, surface2);
    }
}