package com.googlecode.android4cs2.gofish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.Button;
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
	private MyGoFishHand[] hands = new MyGoFishHand[2];
	
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
	
	/** Tell the computer to go fish*/
	private Button goFish;
	
	/** int which tells how many cards of the desired rank the user has (Keeps them from lying) */
	private int numCards = 0;
	
	/** Which card the computer wants */
	private Card wanted;
	
	/** TextViews for scores */
	private TextView[] scoreViews = new TextView[2];
	
	/** Animation sets for giving or receiving cards */
	private AnimationSet slideDown;
	
	/** Animation for cards sliding back and forth */
//	private AnimationSet[] sets = new AnimationSet[2];
	
	/** The listener for the Go Fish deck. Only active when the user can't get cards from the computer. */
	private OnClickListener deckListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!deck.isEmpty()) {
				// Animate this card down to your hand, and upon completion of the animation, call the notifyDatSetChanged method
				hands[0].add(deck.deal());
				scores[0] += score(hands[0]);
				scoreViews[0].setText("Score: " + scores[0]);
				((CardAdapter) yourHand.getAdapter()).notifyDataSetChanged();
			} else {
				Toast.makeText(getApplicationContext(), "No cards left!", Toast.LENGTH_SHORT).show();
			}
			dv.setOnClickListener(null);
			yourHand.setSelection(hands[0].size()-1, true);
			yourHand.setOnItemClickListener(giveListener);
			if (!isGameOver()) {
				computerTurn();
			}
		}
	};
	
	/** Listener for the Gallery of available cards */
	private OnItemClickListener choiceListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// If the computer had at least one card for the user, give it to him
			if (hands[1].give(arg2+1, hands[0])) {
				// Animate cards sliding from computer to player
				scores[0] += score(hands[0]);
				((CardAdapter) yourHand.getAdapter()).notifyDataSetChanged();
				chv.updateImages();
				scoreViews[0].setText("Score: " + scores[0]);
				yourHand.setSelection(hands[0].size()-1, true);
				isGameOver();
				return;
			} else {
				// Otherwise, go fish!
				instructions.setText(R.string.gofish);
				choiceGal.setOnItemClickListener(null);
				yourHand.setOnItemClickListener(null);
				dv.setOnClickListener(deckListener);
			}
		}
		
	};
	
	/** Listener for the Go Fish Button */
	private OnClickListener goFishListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (numCards > 0) {
				Toast.makeText(getApplicationContext(), getString(R.string.liar), Toast.LENGTH_SHORT).show();
				return;
			}				
			if (!deck.isEmpty()) {
				hands[1].add(deck.deal());
			}
			scores[1] += score(hands[1]);
			scoreViews[1].setText("Score: " + scores[1]);
			((CardAdapter) yourHand.getAdapter()).notifyDataSetChanged();
			chv.updateImages();
			goFish.setVisibility(View.GONE);
			choiceGal.setVisibility(View.VISIBLE);
			choiceGal.setOnItemClickListener(choiceListener);
			instructions.setText(R.string.choiceLabel);
			isGameOver();
		}
		
	};
	
	/** A listener for the Gallery to animate cards out of your hand to the computer */
	private OnItemClickListener giveListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// Animate arg1 up to the other player if its rank is the same as the desired rank
			if (wanted.getRank() == hands[0].get(arg2).getRank()) {
				// Animate card downward
				hands[0].give(hands[0].get(arg2), hands[1]);
				Log.d("giveListener: ", hands[0].toString());
				arg1.startAnimation(slideDown);
				numCards--;
				Log.d("giveListener", "Just gave one up...");
			} else {
				return;
			}
			// If the user has selected each card of the appropriate rank, update the models accordingly
			if (numCards == 0) {
				scores[1] += score(hands[1]);
				scoreViews[1].setText("Score: " + scores[1]);
				Log.d("giveListener", "The toHide = arg1;computer goes again!");
				if (!isGameOver()) {
					computerTurn();
				}
			}
		}
		
	};
	
	/** A listener for the animation */
	private AnimationListener downListener = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation animation) {
			/*
			 * When we're done animating the card down, we then slide another card across the screen in the
			 * main layout context.
			 */
			
			((CardAdapter) yourHand.getAdapter()).notifyDataSetChanged();
			chv.updateImages();
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
	
	/** Possible choices when asking for a card, corresponding to the ranks in a deck of cards (Ace to King) */
	boolean[] choices = { true, true, true, true, true, true, true, true, true, true, true, true, true };
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dv = (DeckView) findViewById(R.id.deck);
        chv = (CompHandView) findViewById(R.id.computerHand);
        instructions = (TextView) findViewById(R.id.instructions);
        goFish = (Button) findViewById(R.id.goFish);
        
        choiceGal = (Gallery) findViewById(R.id.choices);
        yourHand = (Gallery) findViewById(R.id.yourHand);
        
        Log.d("onCreate: ", "Right before new game...");
        newGame();
        Log.d("onCreate: ", "Right after new game...");
        
        yourHand.setAdapter(new CardAdapter(this, hands[0]));
        choiceGal.setAdapter(new ChoiceAdapter(choices, this));
        goFish.setOnClickListener(goFishListener);
        scoreViews[0] = (TextView) findViewById(R.id.yourScore);
        scoreViews[1] = (TextView) findViewById(R.id.compScore);
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
    		hands[i] = new MyGoFishHand();
//    		sets[i] = new AnimationSet(true);
    	}
        
/*    	sets[0].addAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
    	sets[1].addAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down)); */
    	slideDown = new AnimationSet(true);
    	slideDown.addAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
    	slideDown.setAnimationListener(downListener);
    	
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
    }
    
    public void computerTurn() {
    	choiceGal.setOnItemClickListener(null);
    	choiceGal.setVisibility(View.GONE);
    	wanted = hands[1].get((int)(Math.random()*hands[1].size()));
    	for (Card c: hands[0]) {
    		if (wanted.getRank() == c.getRank()) {
    			numCards++;
    		}
    	}
    	
    	yourHand.setOnItemClickListener(giveListener);
    	
		goFish.setVisibility(View.VISIBLE);
		String request = getString(R.string.asking);
		switch (wanted.getRank()) {
		case Card.ACE:
			request += " Aces?";
			break;
		case Card.JACK:
			request += " Jacks?";
			break;
		case Card.QUEEN:
			request += " Queens?";
			break;
		case Card.KING:
			request += " Kings?";
			break;
		default:
			request += " " + wanted.getRank() + "s?";
			break;
		}
		instructions.setText(request);
    }
    
    public int score(MyGoFishHand hand) {
    	return hand.meldSets();
    }
    
    public boolean isGameOver() {
    	if (scores[0] + scores[1] >= 13) {
			int winner = 0;
			if (scores[0] < scores[1]) {
				winner = 1;
			}
			instructions.setText("Player " + winner + " " + getString(R.string.win) + " " + scores[winner]);
			dv.setOnClickListener(null);
			choiceGal.setVisibility(View.GONE);
			yourHand.setOnItemClickListener(null);
			goFish.setOnClickListener(null);
			return true;
		}
    	return false;
    }
}