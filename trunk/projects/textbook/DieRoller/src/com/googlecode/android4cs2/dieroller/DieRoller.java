package com.googlecode.android4cs2.dieroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
/**
 * DieRoller is an activity which allows a user to press a button to 
 * roll a Die. For simplicity, the Activity implements the OnClickListener
 * interface, reacting to the press and swapping in the appropriate image.
 *
 * @author mgoadric
 *
 */
public class DieRoller extends Activity implements OnClickListener {
	
	/** A Die object for rolling and tracking state. */
	private Die d;
	
	/** The reference to our GUI Button. */
	private Button rollButton;
	
	/** The reference to the image of the Die. */
	private ImageView imagedie;
	 
     /**
	 * This is the entry point for any Android Activity.
	 * @param savedInstanceState contains data supplied in onSaveInstanceState(Bundle) (null here)
	 * @return void
	 * @see Activity
	 * @see Bundle
	 */ 
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	// Standard setup for Android Activity, call the 
    	// super class and create the layout from main.xml
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // if o is not null, then we're recovering from active
        // Activity, otherwise we're starting fresh
        Object o = getLastNonConfigurationInstance();
        if (o != null && o instanceof Die) {
        	d = (Die)o;
        } else {
        	d = new Die();
        }
        
        // Save reference to GUI ImageView and set the image
        imagedie = (ImageView) findViewById(R.id.imagedie);
        setDieImage(d.getTopFace());
      
        // Save reference to GUI Button, and setup callback function
        rollButton = (Button) findViewById(R.id.roll);
        rollButton.setOnClickListener(this);
        
    }

    /**
     * Retains the state of the Die d when rotated or paused.
     * @return Object
     */
    @Override  
    public Object onRetainNonConfigurationInstance() 
    {   
        return(d);   
    }
    
    /**
     * Swaps out the image according to the topFace of Die.
     * @param top the integer representation of the top of a die
     */
    public void setDieImage(int top) {
		switch (top) {
		case 1: imagedie.setImageResource(R.drawable.die1);
		break;
		case 2: imagedie.setImageResource(R.drawable.die2);
		break;
		case 3: imagedie.setImageResource(R.drawable.die3);
		break;
		case 4: imagedie.setImageResource(R.drawable.die4);
		break;
		case 5: imagedie.setImageResource(R.drawable.die5);
		break;
		case 6: imagedie.setImageResource(R.drawable.die6);
		break;
		default: imagedie.setImageResource(R.drawable.die1);
		break;
		}
    }
    
    /**
     * The callback for the Roll Button. This rolls the die and sets the image.
     * @param v the view in which the callback was triggered
     * @see View
     */
	@Override
	public void onClick(View v) {
		d.roll();
		setDieImage(d.getTopFace());
	}
}