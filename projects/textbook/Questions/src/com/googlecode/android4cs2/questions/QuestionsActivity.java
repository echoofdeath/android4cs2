package com.googlecode.android4cs2.questions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
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
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class QuestionsActivity extends Activity {
	// Model
	/** Binary Tree of our questions */
	private BinaryNode<String> root;
	
	/** A pointer to the root in case we want to play multiple times */
	private BinaryNode<String> node;
	
	// Views
	/** Where the questions appear */
	private TextView questions;
	
	/** Yes button */
	private Button yes;
	
	/** No button */
	private Button no;
	
	/** The user's response */
	private EditText user;
	
	/** Submit button for the user's response */
	private Button submit;
	
	/** Container for yes/no buttons and user/submit controls */
	private TableLayout layout;
	
	private int currentState;
	
	/** The answer */
	private String answer;
	
	/** The question to elicit "answer" */
	private String question;
	
	private AnimationSet fadeUp;
	
	private AnimationSet fadeDown;
	
	private final int ASKING_QUESTION = 0;
	private final int ASKING_ITEM = 1;
	private final int ASKING_WHAT = 2;
	private final int ASKING_WHAT_QUESTION = 3;
	private final int PLAY_AGAIN = 4;
	private final int WIN = 5;
	private final int THANKS = 6;
	private final int NEW_GAME = 7;
	
	private AnimationListener animListenerDown = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation arg0) {
			switch (currentState) {
			case ASKING_QUESTION:
				questions.setText(node.getItem() + "?");
				break;
			case ASKING_ITEM:
				questions.setText(getString(R.string.isIt) + " " + node.getItem() + "?");
				break;
			case ASKING_WHAT:
				questions.setText(getString(R.string.giveUp) + " " + getString(R.string.what));
				user.setVisibility(View.VISIBLE);
				submit.setVisibility(View.VISIBLE);
				yes.setVisibility(View.GONE);
				no.setVisibility(View.GONE);
				break;
			case ASKING_WHAT_QUESTION:
				questions.setText(getString(R.string.distinguish) + " " + node.getItem());
				break;
			case PLAY_AGAIN:
				questions.setText(getString(R.string.again));
				user.setVisibility(View.GONE);
				submit.setVisibility(View.GONE);
				yes.setVisibility(View.VISIBLE);
				no.setVisibility(View.VISIBLE);
				break;
			case WIN:
				questions.setText(getString(R.string.win) + " " + getString(R.string.again));
				break;
			case THANKS:
				questions.setText(getString(R.string.thanks));
				break;
			case NEW_GAME:
				newGame();
				break;
			default:
				// Do nothing
				break;
			}
			
			layout.startAnimation(fadeUp);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private AnimationListener animListenerUp = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation animation) {
			layout.setVisibility(View.VISIBLE);
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
	
	/** Button listener */
	private OnClickListener buttonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(user.getWindowToken(), 0);
			switch (v.getId()) {
			case R.id.yes:
				
				switch (currentState) {
				case ASKING_QUESTION:
					if (node.isLeaf()) {
						currentState = ASKING_ITEM;
						break;
					}
					node = node.getLeft();
					currentState = ASKING_ITEM;
					break;
				case ASKING_ITEM:
					currentState = WIN;
					break;
				case PLAY_AGAIN:
					currentState = NEW_GAME;
					break;
				case WIN:
					currentState = NEW_GAME;
					break;
				default:
					break;
				}
				
				break;
			case R.id.no:
				
				switch (currentState) {
				case ASKING_QUESTION:
					node = node.getRight();
					if (node.isLeaf()) {
						currentState = ASKING_ITEM;
						break;
					}
					currentState = ASKING_QUESTION;
					break;
				case ASKING_ITEM:
					currentState = ASKING_WHAT;
					break;
				case PLAY_AGAIN:
					currentState = THANKS;
					break;
				case WIN:
					currentState = THANKS;
				default:
					break;
				}
				
				break;
			case R.id.submit:
				
				switch (currentState) {
				case ASKING_WHAT:
					answer = user.getText().toString();
					user.setText("", TextView.BufferType.EDITABLE);
					currentState = ASKING_WHAT_QUESTION;
					break;
				case ASKING_WHAT_QUESTION:
					question = user.getText().toString();
					user.setText("", TextView.BufferType.EDITABLE);
					node.setLeft(new BinaryNode<String>(answer));
					node.setRight(new BinaryNode<String>(node.getItem()));
					node.setItem(question);
					currentState = PLAY_AGAIN;
					break;
				default:
					break;
				}
				
				break;
			default:
				// We shouldn't ever get here, so if we do, log it
				Log.d("buttonListener: ", "Shouldn't be here! Ahhh!");
				break;
			}
			layout.startAnimation(fadeDown);
		}
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        questions = (TextView) findViewById(R.id.label);
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        user = (EditText) findViewById(R.id.user);
        submit = (Button) findViewById(R.id.submit);
        layout = (TableLayout) findViewById(R.id.layout);
    	
        load(this);
        newGame();
        
        fadeUp = new AnimationSet(true);
        fadeDown = new AnimationSet(true);
        
        fadeUp.addAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_up));
        fadeDown.addAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_down));
        
        fadeDown.setAnimationListener(animListenerDown);
        fadeUp.setAnimationListener(animListenerUp);
    }
    
    /** Called just before the activity is stopped. This gives us a chance to save our tree. */
    public void onStop() {
    	super.onStop();
    	save(this);
    }
    
    /** Called after activity is stopped and before it is started. */
    public void onRestart() {
    	super.onRestart();
    	load(this);
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
    	case R.id.clear:
    		root = new BinaryNode<String>("a giraffe");
    		newGame();
    	default:
    		return false;
    	}
    }

    
    /** Begin a new game */
    public void newGame() {
    	node = root;
    	if (node.isLeaf()) {
    		questions.setText(getString(R.string.isIt) + " " + node.getItem() + "?");
    		currentState = ASKING_ITEM;
    	} else {
    		currentState = ASKING_QUESTION;
    		questions.setText(node.getItem() + "?");
    	}
        yes.setOnClickListener(buttonListener);
        no.setOnClickListener(buttonListener);
        submit.setOnClickListener(buttonListener);
        yes.setVisibility(View.VISIBLE);
        no.setVisibility(View.VISIBLE);
        user.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
    }
    
    /** Loads in a saved binary tree */
	public void load(Context context) {
		String FILENAME = "savedGame.data";
		try {
			FileInputStream fis = context.openFileInput(FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			root = (BinaryNode<String>) ois.readObject();
			
			// Close our streams
			ois.close();
			fis.close();
			
			// Nullify them to make it easier for garbage collection
			ois = null;
			fis = null;
			
			Log.d("Load: ", "Loaded with no exceptions.");
		} catch (FileNotFoundException e) {
			Log.d("Load: ", "File not found.");
	    	root = new BinaryNode<String>("a giraffe");
		} catch (IOException e) {
			Log.d("Load: ", "IO Problem.");
	    	root = new BinaryNode<String>("a giraffe");
		} catch (ClassNotFoundException e) {
			Log.d("Load: ", "Class problem.");
	    	root = new BinaryNode<String>("a giraffe");
		}
	}
    
    /** Saves a binary tree */
    public void save(Context context) {
		String FILENAME = "savedGame.data";
		try {
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_WORLD_READABLE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(root);
			
			// Close our streams
			oos.close();
			fos.close();
			
			// Nullify them for easier garbage collection
			oos = null;
			fos = null;
			
			Log.d("Save: ", "Saved without exceptions.");
		} catch (FileNotFoundException e) {
			Log.d("Save: ", "File not found.");
		} catch (IOException e) {
			Log.d("Save: ", "IO Problem.");
		}
	}
}