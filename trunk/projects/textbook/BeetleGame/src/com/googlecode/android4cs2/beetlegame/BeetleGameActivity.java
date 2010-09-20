package com.googlecode.android4cs2.beetlegame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BeetleGameActivity extends Activity {
    /** Called when the activity is first created. */
	
	private TextView turn;
	private BeetleGame game;
	private DieView dv;
	private BeetleSurface surface1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        turn = (TextView) findViewById(R.id.turn);
        surface1 = (BeetleSurface) findViewById(R.id.bug_one);
        dv = (DieView) findViewById(R.id.die);
        
        game = dv.getGame();
        surface1.init(game.getLeft(), game.getDie());
        
    }
}