package com.googlecode.android4cs2.beetlegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
        
        dv.setBugSurfaces(surface1, surface2);
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
    
    public void newGame() {
    	dv.newGame();
    }
}