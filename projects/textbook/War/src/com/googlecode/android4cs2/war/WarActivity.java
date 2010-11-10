package com.googlecode.android4cs2.war;

import java.util.Stack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

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
	
	/** The piles of cards in the War zones if War is declared */
	private Stack<Card>[] warCards = new Stack[2];
	
	/** Helper array of resource IDs for the War zones */
	private int[] zoneIDs = { R.id.p1zone, R.id.p2zone };
	
	/** Reference to the TextView title */
	private TextView tv;
	
	/** Helper array for the winners */
	private int[] winIDs = { R.string.p1win, R.string.p2win };
	
	/** Deck of cards */
	private Deck d;
	
	AnimationSet set[] = new AnimationSet[2];

	/** OnClickListener for the DeckViews */
	private OnClickListener deckListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				for (int i = 0; i < 2; i++) {
					cv[i].clear();
				}
				
				int winner = -1;
				int numWars = 0;
				warCards[0].push(decks[0].remove());
				warCards[1].push(decks[1].remove());
				do {
					warzones[0].updateImages(numWars);
					warzones[1].updateImages(numWars);
					
					Card p1 = warCards[0].peek();
					Card p2 = warCards[1].peek();
					
					cv[0].addCard(p1);
					cv[1].addCard(p2);
					
					if (p1.getRank() > p2.getRank()) {
						// Player 1 gets this trick
						winner = 0;
						give(warCards[0], warCards[1], decks[0]);
					} else if (p1.getRank() < p2.getRank()) {
						// Player 2 gets this trick
						winner = 1;
						give(warCards[0], warCards[1], decks[1]);
					}
					
					if (winner > -1) {
						for (int i = 0; i < 2; i++) {
							warzones[i].startAnimation(set[winner]);
							cv[i].startAnimation(set[winner]);
						}
						break;
					}
					numWars++;
				} while (!settledByWar(warCards[0], warCards[1]));
			} catch (EmptyStructureException e) {}
			isGameOver();
		}

		private boolean settledByWar(Stack<Card> x, Stack<Card> y) {
			for (int i = 0; i < 4; i++) {
				if (x.isEmpty()) {
					// Give all cards to player 2
					give(x,y,decks[1]);
					return true;
				}
				x.push(decks[0].remove());
				
				if (y.isEmpty()) {
					// Give all cards to player 1
					give(x,y,decks[0]);
					return true;
				}
				y.push(decks[1].remove());
			}
			Toast.makeText(getApplicationContext(), "War!", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		private void give(Stack<Card> x, Stack<Card> y, Queue<Card> player) {
			while (!x.isEmpty()) {
				player.add(x.pop());
			}
			while (!y.isEmpty()) {
				player.add(y.pop());
			}
		}
		
	};
	
    private AnimationListener animListener = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation animation) {
			for (int i = 0; i < 2; i++) {
				cv[i].setImageResource(R.drawable.background);
				warzones[i].clear();
				dv[i].setOnClickListener(deckListener);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			for (int i = 0; i < dv.length; i++) {
				dv[i].setOnClickListener(null);
			}
			
		}
    	
    };
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        for (int i = 0; i < 2; i++) {
        	cv[i] = (CardView) findViewById(cvIDs[i]);
        	dv[i] = (DeckView) findViewById(dvIDs[i]);
        	warzones[i] = (WarView) findViewById(zoneIDs[i]);
        	set[i] = new AnimationSet(true);
        	set[i].setAnimationListener(animListener);
        }
        
        tv = (TextView) findViewById(R.id.title);
        
        set[0].addAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left));
        set[1].addAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right));
        
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
    		warCards[i] = new Stack<Card>();
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
    		
    		// And the Lord said unto his devices, "Let there be touch!"
    		dv[i].setOnClickListener(deckListener);
    		
    		// Then the CardViews...
    		cv[i].clear();
    		
    		// Finally the war zones
    		warzones[i].clear();
    	}
    	
    	tv.setText(R.string.app_name);
    }
    
    public boolean isGameOver() {
		for (int i = 0; i < 2; i++) {
			if (decks[i].isEmpty()) {
				// One player just lost, meaning the other just won
				tv.setText(winIDs[(i+1)%2]);
				dv[0].setOnClickListener(null);
				dv[1].setOnClickListener(null);
				dv[i].updateImages();
				return true;
			}
		}
		return false;
    }
}