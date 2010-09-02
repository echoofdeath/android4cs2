package com.googlecode.android4cs2.dominoflip;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

/**
 * An Activity subclass which displays a domino and allows the user to flip it, change
 * the backend implementation, or generate a random domino.
 * @author jdjennin
 */
public class DominoFlip extends Activity {
	
	// Data members
	
	private Domino d;
	private Button random;

	private TouchFlipListener tfl;
	
	private RadioButton array;
	private RadioButton field;
	
	private ImageView left;
	private ImageView right;
	
	/* Unlike regular Java programs which start in the main method,
	 * onCreate is where the program begins for Android applications. */
	
	/**
	 * This is the entry point for any Android Activity.
	 * @param savedInstanceState contains data supplied in onSaveInstanceState(Bundle) (null here)
	 * @return void
	 * @see Activity
	 * @see Bundle
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set our layout up
        setContentView(R.layout.main);
        
        left = (ImageView) findViewById(R.id.left);
        right = (ImageView) findViewById(R.id.right);
        
        tfl = new TouchFlipListener();
        
        // Attach our listeners for touch events to the domino images
        left.setOnTouchListener(tfl);
        right.setOnTouchListener(tfl);
        
        array = (RadioButton) findViewById(R.id.radio_array);
        field = (RadioButton) findViewById(R.id.radio_field);
        
        // Attach our listeners for the radio button clicks to the buttons
        array.setOnClickListener(radioListener);
        field.setOnClickListener(radioListener);
        
        // If the orientation changed while this app was open...
        if (getLastNonConfigurationInstance() != null) {
        	// ...get whatever data was saved before it changed
        	Object o = getLastNonConfigurationInstance();
        	
        	// If the previous implementation was the ArrayDomino...
        	if (o.getClass().toString().contains("ArrayDomino")) {
        		// ...keep it an ArrayDomino
        		d = (ArrayDomino) o;
        		array.toggle();
        	} else if (o.getClass().toString().contains("FieldDomino")) {
        		// ...ohterwise (if it was a FieldDomino), keep it that way
        		d = (FieldDomino) o;
        		field.toggle();
        	}

        	// Redraw the images appropriately
        	redraw();
        	
        } else {
        	// ...otherwise, just put out a new domino
            randomize();
        }

        random = (Button) findViewById(R.id.random);
        
        // Attach a listener for the Random button as an anonymous class which has no name
        random.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				randomize();
			}
        	
        });
    }
    
    /**
     * This method allows you to save any data you need when the orientation changes.
     * The Activity is destroyed and completely recreated, so this is necessary if we want to
     * keep the same domino as before the orientation change.
     * @return Object
     * @see Activity
     *  */
    
    public Object onRetainNonConfigurationInstance() {
    	return d;
    }
    
    /**
     * Redraws the images to the screen with their updated values
     * @return void
     * 
     */
    public void redraw() {
    	draw(left, d.getLeft());
    	draw(right, d.getRight());   	
    }
    
    /**
     * This method randomly generates a new domino using the selected implementation
     * @return void
     * 
     */
    public void randomize() {
    	d = new ArrayDomino((int)(Math.random()*6)+1, (int)(Math.random()*6)+1);
    	redraw();
    }

    /**
     * This method draws the domino images to the screen in the specified view.
     * @param view the view to which the domino image should be drawn
     * @param number the numeric value of the particular side of the domino
     * @return void
     * @see ImageView
     * 
     */
    public void draw(ImageView view, int number) {
		switch (number) {
		case 1: view.setImageResource(R.drawable.die1);
		break;
		case 2: view.setImageResource(R.drawable.die2);
		break;
		case 3: view.setImageResource(R.drawable.die3);
		break;
		case 4: view.setImageResource(R.drawable.die4);
		break;
		case 5: view.setImageResource(R.drawable.die5);
		break;
		case 6: view.setImageResource(R.drawable.die6);
		break;
		default: view.setImageResource(R.drawable.die1);
		break;
		}
    }
    
    /* We use a private class here because OnTouchListener is only an interface,
     * not a class. Thus, we must CREATE a class to use the functionality of the interface. */
    /**
     * An implementation of View.OnTouchListener, this class flips the currently displayed domino
     * when the user touches the domino.
     * @see View
     * @see View.OnTouchListener
     */
    class TouchFlipListener implements View.OnTouchListener {

    	@Override
    	/**
    	 * @param v the View which received the MotionEvent event
    	 * @param event the event which triggered this callback
    	 * @return boolean
    	 * @see View
    	 * @see MotionEvent
    	 */
    	public boolean onTouch(View v, MotionEvent event) {
    		// If the touch event which just happened was the finger pressing down on the screen...
    		if ((event.getAction()  & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
    			// ...then flip the domino's left and right sides and redraw the images
    			d.flip();
    			redraw();
    		}
    		return true;
    	}
    	
    }
    
    
    /* Declaring this listener as a data member is expedient 
     * because we will have to use this data member for each RadioButton in the
     * RadioGroup, and declaring it this way lets us use less space in our code.
     * We just simply override the default onClick method with our own code to make it do
     * what we want. */
    RadioButton.OnClickListener radioListener = new RadioButton.OnClickListener() {

		@Override
		public void onClick(View v) {
			// If the array implementation is selected...
			if (array.isChecked()) {
				// ...create a new ArrayDomino object with the same values as before
				d = new ArrayDomino(d.getLeft(), d.getRight());
			} else {
				// ...otherwise, create a FieldDomino object with the same values as before...
				d = new FieldDomino(d.getLeft(), d.getRight());
			}
			// ...and then redraw it all!
			redraw();
		}
    };
}