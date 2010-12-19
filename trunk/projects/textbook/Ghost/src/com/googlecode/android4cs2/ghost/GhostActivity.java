package com.googlecode.android4cs2.ghost;

import java.util.Scanner;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class GhostActivity extends Activity {
	
	/** A pointer to the word we're making */
	private TextView wordView;
	
	/** A pointer to the instructions */
	private TextView instructions;
	
	/** A pointer to the Ghost */
	private ImageView ghost;
	
	/** The word we're making */
	private String word;
	
	/** The word tree */
	private DigitalNode<Boolean> root;
	
	/** Our pointer to the current node in the word tree */
	private DigitalNode<Boolean> node;
	
	private static int USER = 0;
	
	private static int COMPUTER = 1;
	
	/** Int to keep track of whose turn it is */
	private int player = USER;
	
	/** A set of Animations for the ghost */
	private AnimationSet[] set = new AnimationSet[2];
	
	/** Animation Listener for when the Ghost stops moving */
	private AnimationListener animListener = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation animation) {
			computerTurn();
			ghost.startAnimation(set[1]);
			if (player == 0) {
				ghost.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
						
		}

		@Override
		public void onAnimationStart(Animation animation) {
			
		}
		
	};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().takeKeyEvents(true);
        
        wordView = (TextView) findViewById(R.id.word);
        instructions = (TextView) findViewById(R.id.instructions);
        ghost = (ImageView) findViewById(R.id.ghost);
        
        for (int i = 0; i < 2; i++) {
        	set[i] = new AnimationSet(true);
        }
        
        set[0].setAnimationListener(animListener);
        set[0].addAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        set[1].addAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));      
        
        newGame();
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.d("onKeyUp", "Key code: " + (char)event.getUnicodeChar());
		if (player == USER) {
			if (event.getUnicodeChar() >= 97 && event.getUnicodeChar() <= 122) {
				word += (char)event.getUnicodeChar();
			} else {
				Log.d("onKeyUp", "Not a valid character");
				return false;
			}
			
			if (word.length() == 1) {
				root = loadNodes();
				node = root;
			}
			player = COMPUTER;
			node = node.getChild((char)event.getUnicodeChar());
			wordView.setText(word);
			
			// **********Test
			ghost.setVisibility(View.VISIBLE);
			ghost.startAnimation(set[0]);
			
		}
		return false;
	}
    
    public void newGame() {
    	word = new String("");
    	wordView.setText(word);
    	
    	node = null;
    	root = null;
    	
    	ghost.setVisibility(View.GONE);
    }
    
    public void computerTurn() {
    	if (node.isLeaf()) {
    		ghost.setImageResource(R.drawable.ghostdead);
    		ghost.startAnimation(set[0]);
    		instructions.setText("Player wins!");
    		return;
    	}
    	char letter = node.randomLetter();
    	word += letter;
    	wordView.setText(word);
    	node = node.getChild(letter);
    	
    	player = USER;
    }
    
    public DigitalNode<Boolean> loadNodes() {
    	DigitalNode<Boolean> words = new DigitalNode<Boolean>(false);
    	try {
    		AssetManager assman = getAssets();
    		Scanner input = new Scanner(assman.open(word.charAt(0) + ".txt"));
    		while (input.hasNextLine()) {
    			String line = input.nextLine();
    			addWord(line, words);
    		}
    		input = null;
    		assman.close();
    		assman = null;
    	} catch (Exception e) {
    		Log.d("loadNodes: ", "Problem with words.txt!");
    	}
    	
    	return words;
    }
    
    public void addWord(String toAdd, DigitalNode<Boolean> addTo) {
    	for (char c: toAdd.toCharArray()) {
    		DigitalNode<Boolean> child = addTo.getChild(c);
    		if (child == null) {
    			child = new DigitalNode<Boolean>(false);
    			addTo.setChild(c, child);
    		}
    		addTo = child;
    	}
    	addTo.setItem(true);
    }
}