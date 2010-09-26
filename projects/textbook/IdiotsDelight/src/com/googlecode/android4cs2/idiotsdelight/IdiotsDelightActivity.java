package com.googlecode.android4cs2.idiotsdelight;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class IdiotsDelightActivity extends Activity {
	
	/** The deck of cards for the game */
	private Deck d;
	
	/** Four stacks of cards */
	private Stack<Card>[] stacks;
	
	/** Four references to the CardViews */
	private CardView[] cv;
	
	/** To assist stackListener */
	private java.util.Stack<CardView> clicked = new java.util.Stack<CardView>();
	
	/** Flag which allows only 2 selections*/
	private boolean change = true;
	
	/** A reference to the DeckView */
	private DeckView dv;
	
	/** Array containing the resource IDs of the CardView stacks. */
	private int[] CVIDs = { R.id.stack1, R.id.stack2, R.id.stack3, R.id.stack4 };
	
	/** Listens for clicks on the deck to deal cards. */
	private OnClickListener deckListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				deal();
			} catch (IllegalMoveException e) {
				// Display a message about the illegal move
			}
		}
		
	};
	
	/** Listens for clicks on each of the four stacks. */
	private OnClickListener stackListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			CardView selected = (CardView)v;
			
			
			if (clicked.size() == 2) {
				if (selected.isSelected()) {
					selected.toggleSelected();
					clicked.pop();
				} else {
					clicked.pop().toggleSelected();
					selected.toggleSelected();
					clicked.push(selected);
				}
			} else if (clicked.size() < 2) {
				clicked.push(selected);
				selected.toggleSelected();
			}
			
			Log.d("stackListener: ", "Number of selected stacks: " + clicked.size());
		}
		
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        newGame();
        
        cv = new CardView[4];
        for (int i = 0; i < 4; i++) {
        	cv[i] = (CardView) findViewById(CVIDs[i]);
        	cv[i].setOnClickListener(stackListener);
        	cv[i].setStack(stacks[i]);
        }
        
        dv = (DeckView) findViewById(R.id.deal);
        dv.setOnClickListener(deckListener);
        
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
    		Intent i = new Intent(IdiotsDelightActivity.this, HelpActivity.class);
    		startActivity(i);
    		return true;
    	default:
    		return false;
    	}
    }
    
    public void newGame() {
    	d = new Deck();
    	d.shuffle();
    	
    	stacks = new ArrayStack[4];
    	for (int i = 0; i < 4; i++) {
    		stacks[i] = new ArrayStack<Card>();
    	}
    }
    
    public void deal() throws IllegalMoveException {
    	if (d.isEmpty()) {
    		throw new IllegalMoveException();
    	}
    	for (Stack<Card>s: stacks) {
    		s.push(d.deal());
    	}
    	
    	for (CardView v: cv) {
    		v.updateImages();
    	}
    }
    
    public void removeLowCard(Card a, Card b) throws IllegalMoveException {
    	
    }
    
    public void removePair(Card a, Card b) throws IllegalMoveException {
    	
    }
}