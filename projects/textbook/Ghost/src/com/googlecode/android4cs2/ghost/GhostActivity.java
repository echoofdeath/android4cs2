package com.googlecode.android4cs2.ghost;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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
	
	/** Int to keep track of whose turn it is */
	private int player = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().takeKeyEvents(true);
        
        wordView = (TextView) findViewById(R.id.word);
        instructions = (TextView) findViewById(R.id.instructions);
        ghost = (ImageView) findViewById(R.id.ghost);
        
        newGame();
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.d("onKeyUp", "Key code: " + (char)event.getUnicodeChar());
		if (player == 0) {
			word += (char)event.getUnicodeChar();
			if (word.length() == 1) {
				root = loadNodes();
				node = root;
			}
			player = 1 - player;
			wordView.setText(word);
			computerTurn();
//			wordView.startAnimation(fadeOut); When this is done, setText of wordView to new word and fade back in
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
    	
    	player = 1 - player;
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
    		node = child;
    	}
    	node.setItem(true);
    }
}