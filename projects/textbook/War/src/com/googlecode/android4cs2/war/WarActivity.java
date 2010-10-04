package com.googlecode.android4cs2.war;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

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
	private Queue<Card>[] warCards = new ArrayQueue[2];
	
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
			int winner = -1;
			
			Card p1 = decks[0].remove();
			Card p2 = decks[1].remove();
			
			Log.d("Card 1:", p1.toString());
			Log.d("Card 2:", p2.toString());
			cv[0].setCard(p1);
			cv[1].setCard(p2);
			
			if (p1.getRank() > p2.getRank()) {
				// Player 1 gets this trick
				winner = 0;
			} else if (p1.getRank() < p2.getRank()) {
				// Player 2 gets this trick
				winner = 1;
			} else if (p1.getRank() == p2.getRank()) {
				// WAR!
				// Problem with war cards going wrong direction :(
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 2; j++) {
						// Try to deal out 3 cards to both players
						try {
							warCards[j].add(decks[j].remove());
						} catch (EmptyStructureException e) {
							// If a Queue runs out of cards, the winner is the player with cards remaining in his Queue
							winner = (j+1) % 2;
						}
					}
					// If one of the players ran out of cards, break out of this loop
					if (winner > -1) {
						isGameOver();
						return;
					}
				}
				for (int i = 0; i < warzones.length; i++) {
					warzones[i].setQ(warCards[i]);
					warzones[i].updateImages();
				}
				
				Queue<Card> residual = new ArrayQueue<Card>();
				for (int i = 0; i < 3; i++) {
					Card p3 = warCards[0].remove();
					Log.d("War Card " + i + 1, p3.toString());
					Card p4 = warCards[1].remove();
					Log.d("War Card " + i + 2, p4.toString());
					if (winner > -1) {
						while (!residual.isEmpty()) {
							decks[winner].add(residual.remove());
						}
						decks[winner].add(p3);
						decks[winner].add(p4);
					} else if (p3.getRank() > p4.getRank()) {
						winner = 0;
						residual.add(p3);
						residual.add(p4);
					} else if (p3.getRank() < p4.getRank()){
						winner = 1;
						residual.add(p3);
						residual.add(p4);
					} else if (p3.getRank() == p4.getRank()) {
						residual.add(p3);
						residual.add(p4);
					}
				}
				
				for (int i = 0; i < 2; i++) {
					warzones[i].startAnimation(set[winner]);
					cv[i].startAnimation(set[winner]);
				}

				return;
			}
			
			/* Let the players absorb what just happened for a few seconds, then animate a translation to whoever won the cards
			 * after adding the two cards to the interim winner's deck. */
			decks[winner].add(p1);
			decks[winner].add(p2);
			
			for (int i = 0; i < 2; i++) {
				cv[i].startAnimation(set[winner]);
			}
			
			isGameOver();
		}
		
	};
	
    private AnimationListener animListener = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation animation) {
			for (int i = 0; i < 2; i++) {
				cv[i].setImageResource(R.drawable.background);
				warzones[i].setImageResource(R.drawable.background);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
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
    		warCards[i] = new ArrayQueue<Card>();
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
    		
    		// Then the cards...
    		cv[i].setCard(null);
    		cv[i].updateImages();
    		
    		// Finally the war zones
    		warzones[i].clear();
    	}
    	
    	tv.setText(R.string.app_name);
    }
    
    public void isGameOver() {
		for (int i = 0; i < 2; i++) {
			if (decks[i].isEmpty()) {
				// One player just lost, meaning the other just won
				tv.setText(winIDs[(i+1)%2]);
				dv[0].setOnClickListener(null);
				dv[1].setOnClickListener(null);
			}
		}
    }
}