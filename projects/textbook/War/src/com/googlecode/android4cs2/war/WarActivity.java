package com.googlecode.android4cs2.war;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class WarActivity extends Activity {
	
	/** Image of players' decks */
	private DeckView[] dv = new DeckView[2];
	
	/** Helper array of resource IDs for the DeckViews */
	private int[] dvIDs = { R.id.p1deck, R.id.p2deck };
	
	/** Players' decks */
	private Queue<Card>[] decks = new ArrayQueue[2];
	
	/** Card that just came off the top of each player's deck */
	private CardView[] cv = new CardView[2];
	
	/** Helper array of resource IDs for the CardViews */
	private int[] cvIDs = { R.id.p1card, R.id.p2card };
	
	/** The areas for three War cards if War is declared */
	private WarView[] warzones = new WarView[2];
	
	/** Helper array of resource IDs for the War zones */
	private int[] zoneIDs = { R.id.p1zone, R.id.p2zone };
	
	/** Deck of cards */
	private Deck d;
	
	/** Current player */
	private int player = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        for (int i = 0; i < 2; i++) {
        	cv[i] = (CardView) findViewById(cvIDs[i]);
        	dv[i] = (DeckView) findViewById(dvIDs[i]);
        	warzones[i] = (WarView) findViewById(zoneIDs[i]);
        }
        
        newGame();
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
    		Intent i = new Intent(WarActivity.this, HelpActivity.class);
    		startActivity(i);
    		return true;
    	default:
    		return false;
    	}
    }
    
    public void newGame() {
    	d = new Deck();
    	d.shuffle();
    	
    	// Initialize the Queues
    	for (int i = 0; i < 2; i++) {
    		decks[i] = new ArrayQueue<Card>();
    	}
    	
    	// Deal out the cards equally to both players
    	while (!d.isEmpty()) {
    		decks[0].add(d.deal());
    		decks[1].add(d.deal());
    	}
    	
    	
    	// Update the Views
    	for (int i = 0; i < 2; i++) {
    		// First the decks...
    		dv[i].setQ(decks[i]);
    		dv[i].updateImages();
    		
    		// Then the cards...
    		cv[i].setCard(null);
    		cv[i].updateImages();
    		
    		// Finally the war zones
    		warzones[i].clear();
    	}
    }
}