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
import android.widget.Button;
import android.widget.EditText;
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
	private TableRow controls;
	
	/** Tells us whether the user has submitted an answer */
	private boolean haveAnswer = false;
	
	/** The answer */
	private String answer;
	
	/** The question to elicit "answer" */
	private String question;
	
	/** Button listener */
	private OnClickListener buttonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.yes:
				// Win!
				if (!haveAnswer) {
					haveAnswer = true;
					questions.setText(getString(R.string.win) + " " + getString(R.string.again));
				} else {
					newGame();
				}
				break;
			case R.id.no:
				if (!haveAnswer) {
					// Go down the other branch of the BinaryNode
					if (node.getRight() == null) {
						// Animate a change in text with opacity shift
						user.setVisibility(View.VISIBLE);
						submit.setVisibility(View.VISIBLE);
						yes.setVisibility(View.GONE);
						no.setVisibility(View.GONE);
						questions.setText(getString(R.string.giveUp) + " " + getString(R.string.what));

					} else {
						node = node.getRight();
						if (node.getItem().equals("a giraffe")) {
							questions.setText(getString(R.string.isIt) + " " + node.getItem() + "?");
						} else {
							questions.setText(node.getItem() + "?");
						}
					}
				} else {
					questions.setText(R.string.thanks);
					yes.setOnClickListener(null);
					no.setOnClickListener(null);
				}
				break;
			case R.id.submit:
				// Submit the text in the user EditText
				// Set the answer the first time around, then ask for the question 
				if (!haveAnswer) {
					answer = user.getText().toString();
					user.setText("", TextView.BufferType.EDITABLE);
					haveAnswer = !haveAnswer;
					questions.setText(getString(R.string.distinguish) + " " + node.getItem());
				// Set the question and ask to play again
				} else {
					question = user.getText().toString();
					user.setText("", TextView.BufferType.EDITABLE);
					node.setLeft(new BinaryNode<String>(answer));
					node.setRight(new BinaryNode<String>(node.getItem()));
					node.setItem(question);
					questions.setText(R.string.again);
					yes.setVisibility(View.VISIBLE);
					no.setVisibility(View.VISIBLE);
					user.setVisibility(View.GONE);
					submit.setVisibility(View.GONE);
				}
				break;
			default:
				// We shouldn't ever get here, so if we do, log it
				Log.d("buttonListener: ", "Shouldn't be here! Ahhh!");
				break;
			}
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
        controls = (TableRow) findViewById(R.id.controlPanel);
    	
        load(this);
        newGame();
        
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
    	haveAnswer = false;
    	if (node.getItem().equals("a giraffe")) {
    		questions.setText(getString(R.string.isIt) + " " + node.getItem() + "?");
    	} else {
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