package com.android4cs2.googlecode.domineering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class DomineeringActivity extends Activity {
	
	/** This is the graphical representation of the gameboard. */
	private GridView boardView;
	
	/** This displays the current player. */
	private TextView playerTurn;
	
	/** This is the actual game which keeps track of the state of the board. */
	private Domineering d;
	
	/** This is the width of the Android device on which this Activity is running. */
	private int width;
	
	/** This is the height of the Android device on which this Activity is running. */
	private int height;
	
	/** This is the listener for the GridView. */
	private OnItemClickListener listener;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // If the device was rotated, get the last gameboard configuration 
        if (getLastNonConfigurationInstance() != null) {
        	d = (Domineering) getLastNonConfigurationInstance();
        } else {
        	// otherwise, just make a new board
        	d = new Domineering();
        }
        // Get a pointer to our views so that we can alter them later!
        boardView = (GridView) findViewById(R.id.board);
        playerTurn = (TextView) findViewById(R.id.turn);
        
        // Get the height and width of the screen
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();
        
        // Take the smaller of the two and use it as the dimensions of the gameboard
        if (height < width) {
        	width = height;
        }
        
        listener = new OnItemClickListener() {

        	/**
        	 * This method is called when an item on the board is clicked.
        	 * @return void
        	 * @param parent
        	 * @param v The View that was clicked
        	 * @param position The index of the View that was clicked
        	 * @param id The id of the View that was clicked
        	 */
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// If the player is horizontal and trying to play at the right edge, tell them no
				if (d.getPlayer() == Domineering.HORIZONTAL && position % 8 == 7) {
					playerTurn.setText("Invalid move. Still on horizontal's turn.");
					return;
				// Otherwise, if the player is vertical and trying to play on the bottom edge, tell them no, too
				} else if (d.getPlayer() == Domineering.VERTICAL && position / 8 == 7) {
					playerTurn.setText("Invalid move. Still on vertical's turn.");
					return;
				}
				
				// Since they decided to pick a valid move, they play at this particular position in the Domineering board
				d.playAt(position / 8, position % 8, d.getPlayer());
				ImageAdapter ia = (ImageAdapter) boardView.getAdapter();
				if (d.getPlayer() == Domineering.HORIZONTAL) {
					playerTurn.setText(R.string.blue);
				} else {
					playerTurn.setText(R.string.red);
				}	
				d.nextPlayer();
				// Let the ImageAdapter know that its data changed, so it needs to redraw the GridView
				ia.notifyDataSetChanged();
				// And when there are no more available moves, display a game over and don't let anybody click the board
				if (!d.hasLegalMoveFor(d.getPlayer())) {
					playerTurn.setText("GAME OVER!");
					boardView.setOnItemClickListener(null);
				}
			}
        };
        
        // We must set up an Adapter which maps images onto our GridView from the state of the squares array in the Domineering object
        boardView.setAdapter(new ImageAdapter(this, width, d));
        boardView.setOnItemClickListener(listener);
    }
    
    /** 
     * Returns the Domineering object shortly before destruction so that the state won't be lost.
     * @see Domineering
     * @return Object
     */
    public Object onRetainNonConfigurationInstance() {
    	return d;
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
    		d = new Domineering();
    		boardView.setAdapter(new ImageAdapter(getApplicationContext(), width, d));
    		// Need to be able to click things...
			boardView.setOnItemClickListener(listener);
			playerTurn.setText(R.string.blue);
			if (d.getPlayer() == Domineering.HORIZONTAL) {
				d.nextPlayer();
			}
    		return true;
    	// Otherwise, if they need help, they can have that too
    	case R.id.help:
    		Intent i = new Intent(DomineeringActivity.this, HelpActivity.class);
    		startActivity(i);
    		return true;
    	default:
    		return false;
    	}
    }
}