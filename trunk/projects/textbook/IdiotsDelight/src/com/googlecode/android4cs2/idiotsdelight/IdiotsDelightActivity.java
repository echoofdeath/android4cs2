package com.googlecode.android4cs2.idiotsdelight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class IdiotsDelightActivity extends Activity {
	
	/** The deck of cards for the game */
	private Deck d;
	
	/** Four stacks of cards */
	private Stack<Card>[] stacks;
	
	/** Four references to the CardViews */
	private CardView[] cv;
	
	/** Assists stackListener and removeListener by storing pointers to the currently selected CardViews. */
	private java.util.Stack<CardView> clicked = new java.util.Stack<CardView>();
	
	/** A reference to the DeckView */
	private DeckView dv;
	
	/** A reference to the Remove button */
	private Button rm;
	
	/** A reference to the TextView */
	private TextView tv;
	
	/** Array containing the resource IDs of the CardView stacks. */
	private int[] CVIDs = { R.id.stack1, R.id.stack2, R.id.stack3, R.id.stack4 };
	
	/** Listens for clicks on the deck to deal cards. */
	private OnClickListener deckListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				deal();
			} catch (IllegalMoveException e) {
				Toast.makeText(getApplicationContext(), "No cards left!", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	
	/** Listens for clicks on each of the four stacks. */
	private OnClickListener stackListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			CardView selected = (CardView)v;
			
			if (selected.getStack().isEmpty()) {
				return;
			}
			
			if (clicked.size() == 2 && !selected.isSelected()) {
				clicked.pop().toggleSelected();
				selected.toggleSelected();
				clicked.push(selected);
			} else if (clicked.size() == 2) {
				selected.toggleSelected();
				if (clicked.peek() == selected) {
					clicked.pop();
				} else {
					CardView temp = clicked.pop();
					clicked.pop();
					clicked.push(temp);
				}
			} else if (clicked.size() > 0 && selected.isSelected()) { 
				selected.toggleSelected();
				clicked.pop();
			} else {
				selected.toggleSelected();
				clicked.push(selected);
			}
			
			Log.d("stackListener: ", "Number of selected stacks: " + clicked.size());
		}
		
	};
	
    /** Listens for clicks on the remove buttons. */
	private OnClickListener removeListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (clicked.size() == 2) {
				try {
			    	
					CardView one = clicked.pop();
			    	CardView two = clicked.pop();
			    	
					removeLowCard(one, two);
					
					one.toggleSelected();
					two.toggleSelected();
					
					one.updateImages();
					two.updateImages();
					
				} catch (IllegalMoveException e1) {
					Log.d("removeListener: ", "Couldn't remove low card!");
					try {
						CardView one = clicked.pop();
						CardView two = clicked.pop();
						
						removePair(one, two);
						
						one.toggleSelected();
						two.toggleSelected();
						
						one.updateImages();
						two.updateImages();
						
					} catch (IllegalMoveException e2) {
						Toast.makeText(getApplicationContext(), "You can't do that!", Toast.LENGTH_SHORT).show();
					}
				}
				
				if (d.isEmpty()) {
					boolean win = true;
					for (CardView c: cv) {
						if (!c.getStack().isEmpty()) {
							win = false;
						}
					}
					
					if (win) {
						tv.setText(R.string.win);
						return;
					}
					
					for (int i = 0; i < cv.length-1; i++) {
						for (int j = i; j <= cv.length; j++) {
							try {
								if (cv[i].getStack().peek().getRank() == cv[j].getStack().peek().getRank()
									|| cv[i].getStack().peek().getSuit() == cv[j].getStack().peek().getSuit()) {
									return;
								}
							} catch (EmptyStructureException e) {
								
							}
						}
					}
					tv.setText(R.string.fail);
				}
			} else {
				Toast.makeText(getApplicationContext(), "Not enough cards selected!", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        cv = new CardView[4];
        for (int i = 0; i < 4; i++) {
        	cv[i] = (CardView) findViewById(CVIDs[i]);
        	cv[i].setOnClickListener(stackListener);
        }
        
        newGame();
        
        dv = (DeckView) findViewById(R.id.deal);
        dv.setOnClickListener(deckListener);
        
        rm = (Button) findViewById(R.id.remove);
        rm.setOnClickListener(removeListener);
        
        tv = (TextView) findViewById(R.id.title);
        
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
    		cv[i].setStack(stacks[i]);
    		cv[i].updateImages();
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
    		if (v.isSelected()) {
    			v.toggleSelected();
    			clicked.pop();
    		}
    	}
    }
    
    public void removeLowCard(CardView one, CardView two) throws IllegalMoveException {

    	Card lowCard = one.getStack().peek();
    	Card highCard = two.getStack().peek();
    	CardView removal = one;
    	
    	if (lowCard.getRank() > highCard.getRank()) {
    		Card temp = new Card(lowCard.getRank(), lowCard.getSuit());
    		lowCard = new Card(highCard.getRank(), highCard.getSuit());
    		highCard = temp;
    		removal = two;
    	}
    	
    	if ((lowCard.getSuit() != highCard.getSuit()) || (lowCard.getRank() > highCard.getRank())) {
    		clicked.push(two);
    		clicked.push(one);
    		throw new IllegalMoveException();
    	}
    	
    	Log.d("lowCard: ", lowCard.toString());
    	Log.d("highCard: ", highCard.toString());
    	
    	Log.d("removeLowCard: ", "Removed " + removal.getStack().pop().toString());
    	
    }
    
    public void removePair(CardView one, CardView two) throws IllegalMoveException {
    	Card uno = one.getStack().peek();
    	Card dos = two.getStack().peek();
    	
    	if (!uno.equals(dos)) {
    		clicked.push(two);
    		clicked.push(one);
    		throw new IllegalMoveException();
    	}
    	
    	Log.d("Card One: ", uno.toString());
    	Log.d("Card Two:", dos.toString());
    	
    	one.getStack().pop();
    	two.getStack().pop();
    }
}