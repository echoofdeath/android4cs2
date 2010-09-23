package com.googlecode.android4cs2.beetlegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/** The entry point for this game */
public class BeetleGameActivity extends Activity {
    
	/** A TextView which displays the current turn 
	 * @see TextView */
	private TextView turn;
	
	/** An ImageView which provides Die functionality
	 * @see Die 
	 * @see ImageView */
	private DieView dv;
	
	/** An ImageView which contains player one's Beetle 
	 * @see ImageView 
	 * @see Beetle */
	private BeetleView surface1;
	
	/** An ImageView which contains player two's beetle 
	 * @see ImageView
	 * @see Beetle */
	private BeetleView surface2;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Get references to all widgets that we must alter
        turn = (TextView) findViewById(R.id.turn);
        surface1 = (BeetleView) findViewById(R.id.bug_one);
        surface2 = (BeetleView) findViewById(R.id.bug_two);
        dv = (DieView) findViewById(R.id.die);
        
        // Give the DieView a reference to the player's Beetles
        dv.setBugSurfaces(surface1, surface2);
        
        // Give the DieView a reference to the TextView which displays the current turn
        dv.setTextLabel(turn);
    }
    
    /**
     * Inflates the Options Menu.
     * @param menu The menu which is being inflated.
     * @return boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    /**
     * Called when an item from the Options Menu is selected.
     * @param item The item which was selected.
     * @return boolean
     */
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	// If the user wants a new game, we'll give him one
    	case R.id.newGame:
    		newGame();
    		return true;
    	// Otherwise, if they need help, they can have that too
    	case R.id.help:
    		Intent i = new Intent(BeetleGameActivity.this, HelpActivity.class);
    		startActivity(i);
    		return true;
    	default:
    		return false;
    	}
    }
    
    /**
     * Starts a new game 
     */
    public void newGame() {
    	dv.newGame();
    }
}