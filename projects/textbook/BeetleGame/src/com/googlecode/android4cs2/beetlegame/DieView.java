package com.googlecode.android4cs2.beetlegame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MotionEvent;

/** A subclass of ImageView which contains a Die object and acts as the controller for this game */
public class DieView extends ImageView {

	/** Controls who plays and what parts are displayed */
	private Die d;
	
	/** An array containing references to the Beetle objects of the BeetleViews */
	private Beetle[] bugs = new Beetle[2];
	
	/** An array containing references to the BeetleView objects */
	private BeetleView[] surfaces = new BeetleView[2];
	
	/** A reference to the TextView object which displays the current turn */
	private TextView turn;
	
	/** An array containing resource IDs for the player labels */
	private int[] labels = { R.string.playerone, R.string.playertwo };
	
	/** An array containing resource IDs for the winner labels*/
	private int[] winners = { R.string.playerone_win, R.string.playertwo_win };
	
	/** An integer (0 or 1) which keeps track of the current player */
	private int player = 0;
	
	public DieView(Context context) {
		super(context);
		setImageResource(R.drawable.die4);
		d = new Die();
	}

	/** Default constructor. Sets the image resource to the four face of the Die and then initializes a new Die object */
	public DieView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setImageResource(R.drawable.die4);
		d = new Die();
	}

	public DieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setImageResource(R.drawable.die4);
		d = new Die();
	}

	/** Overrides the default onTouchEvent method and causes the Die object to be rolled if the game
	 * isn't over and the action which triggered this callback is a finger down on the screen. 
	 * @return boolean
	 * @param event The MotionEvent which triggered this callback 
	 * @see MotionEvent */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// If the user's finger touched the screen AND the game is still going...
		if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN && !bugs[player].isComplete()) {
			// ...roll the die!
			d.roll();
			// This flag lets us know when to change players
			boolean breakOut = false;
			
			// If the player rolls such that he cannot add the body part he rolled, break out of this loop and switch players
			switch (d.getTopFace()) {
			case 1:
				setImageResource(R.drawable.die1);
				if (!bugs[player].addBody()) {
					breakOut = true;
				}
				break;
			case 2:
				setImageResource(R.drawable.die2);
				if (!bugs[player].addHead()) {
					breakOut = true;
				}
				break;
			case 3:
				setImageResource(R.drawable.die3);
				if (!bugs[player].addLeg()) {
					breakOut = true;
				}
				break;
			case 4:
				setImageResource(R.drawable.die4);
				if (!bugs[player].addEye()) {
					breakOut = true;
				}
				break;
			case 5:
				setImageResource(R.drawable.die5);
				if (!bugs[player].addFeeler()) {
					breakOut = true;
				}
				break;
			case 6:
				setImageResource(R.drawable.die6);
				if (!bugs[player].addTail()) {
					breakOut = true;
				}
				break;
			default:
				break;
			}
			
			invalidate(); // Update the DieView image...
			surfaces[player].updateImages(); // Update the current player's BeetleView image...
			
			// If the player won, say so
			if (bugs[player].isComplete()) {
				turn.setText(winners[player]);
				return true;
			}
			
			// Otherwise, if the player can't add anything to his bug this turn, move on to the next player
			if (breakOut) {
				surfaces[player].setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
				player = 1 - player;
				surfaces[player].clearColorFilter();
				turn.setText(labels[player]);
				return true;
			}
		}
		
		return true;
	}
	
	/** Sets up references to the Beetle objects in the BeetleView objects 
	 * @param one Player one's Beetle
	 * @param two Player two's Beetle
	 * @see Beetle
	 * @see BeetleView */
	private void setBugs(Beetle one, Beetle two) {
		bugs[0] = one;
		bugs[1] = two;
	}
	
	/** Sets up references to the BeetleView objects 
	 * @param one ImageView containing player one's Beetle
	 * @param two ImageView containing player two's Beetle 
	 * @see Beetle
	 * @see BeetleView
	 * @see BeetleGameActivity */
	public void setBugSurfaces(BeetleView one, BeetleView two) {
		surfaces[0] = one;
		surfaces[1] = two;
		setBugs(one.getBug(), two.getBug());
	}
	
	/** Sets up a reference to the TextView which displays the current turn 
	 * @param tv the TextView from BeetleGameActivity 
	 * @see BeetleGameActivity */
	public void setTextLabel(TextView tv) {
		turn = tv;
	}
	
	/** Starts a new game by initializing a new Die, going back to player one, and calling the BeetleView objects' newGame method
	 * @see Die
	 * @see BeetleView
	 * @see Beetle
	 * */
	public void newGame() {
		setImageResource(R.drawable.die4);
		d = new Die();
		player = 0;
		turn.setText(labels[player]);
		surfaces[0].newGame();
		surfaces[1].newGame();
		setBugs(surfaces[0].getBug(), surfaces[1].getBug());
		invalidate();
	}
}
