package com.googlecode.android4cs2.gofish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/** The controller of this game */
public class GoFishActivity extends Activity {
	
	/** The deck */
	private Deck deck;
	
	/** The DeckView */
	private DeckView dv;
	
	/** The hands */
	private GoFishHand[] hands = new GoFishHand[2];
	
	/** The scores*/
	private int[] scores = new int[2];
	
	/** What to do */
	private TextView instructions;
	
	/** The computer's hand view */
	private CompHandView chv;
	
	/** The gallery of available choices for your turn */
	private Gallery choiceGal;
	
	/** The player's hand gallery */
	private Gallery yourHand;
	
	/** The listener for the Go Fish deck. Only active when the user can't get cards from the computer. */
	private OnClickListener deckListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!deck.isEmpty()) {
				// Animate this card down to your hand, and upon completion of the animation, call the notifyDatSetChanged method
				hands[0].add(deck.deal());
				((CardAdapter) yourHand.getAdapter()).notifyDataSetChanged();
				player = 1-player;
			} else {
				Toast.makeText(getApplicationContext(), "No cards left!", Toast.LENGTH_SHORT).show();
			}
			dv.setOnClickListener(null);
			player = 1 - player;
			computerTurn();
		}
	};
	
	/** Listener for the Gallery of available cards */
	private OnItemClickListener choiceListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// If the computer had at least one card for the user, give it to him
			if (hands[player].give(arg2+1, hands[1-player])) {
				// Animate cards sliding from computer to player
				((CardAdapter) yourHand.getAdapter()).notifyDataSetChanged();
				player = 1 - player;
				computerTurn();
				return;
			} else {
				// Otherwise, go fish!
				instructions.setText(R.string.gofish);
				dv.setOnClickListener(deckListener);
			}
		}
		
	};
	
	/** Possible choices when asking for a card, corresponding to the ranks in a deck of cards (Ace to King) */
	boolean[] choices = { true, true, true, true, true, true, true, true, true, true, true, true, true };
	
	/**  Current player */
	private int player = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dv = (DeckView) findViewById(R.id.deck);
        chv = (CompHandView) findViewById(R.id.computerHand);
        instructions = (TextView) findViewById(R.id.instructions);
        
        choiceGal = (Gallery) findViewById(R.id.choices);
        yourHand = (Gallery) findViewById(R.id.yourHand);
        
        newGame();
        
        yourHand.setAdapter(new CardAdapter(this, hands[0]));
        choiceGal.setAdapter(new ChoiceAdapter(choices, this));
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
    		Intent i = new Intent(this, HelpActivity.class);
    		startActivity(i);
    		return true;
    	default:
    		return false;
    	}
    }

    public void newGame() {
    	deck = new Deck();
    	deck.shuffle();
    	
    	dv.setDeck(deck);
    	
    	for (int i = 0; i < 2; i++) {
    		hands[i] = new GoFishHand();
    	}
        
    	for (int i = 0; i < 7; i++) {
    		for (int j = 0; j < 2; j++) {
    			hands[j].add(deck.deal());
    		}
    	}
    	
    	chv.setHand(hands[1]);
    	
    	for (int i = 0; i < choices.length; i++) {
    		choices[i] = true;
    	}
    	
    	instructions.setText(R.string.choiceLabel);
    	choiceGal.setOnItemClickListener(choiceListener);
    	player = 0;
    }
    
    public void computerTurn() {}
}