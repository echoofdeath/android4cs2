package com.googlecode.android4cs2.stringlights;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * BulbView is a subclass of ImageView which is an implementation of the OnGestureListener interface.
 * It contains information about the light bulb and handles all changes to the bulb's state through
 * the user interface.
 * @author jdjennin
 * @see ImageView
 * @see GestureDetector
 */
public class BulbView extends ImageView implements GestureDetector.OnGestureListener  {
	
	/** 
	 * The light bulb
	 */
	private ColoredLight bulb;
	
	/**
	 * Allows us to detect gestures
	 */
	private GestureDetector detector;

	/**
	 * Default constructor for a BulbView. Initializes a ColoredLight and the GestureDetector.
	 * @param context
	 * @see Context
	 */
	public BulbView(Context context) {
		super(context);
		bulb = new ColoredLight();
		detector = new GestureDetector(this);
		this.setImageResource(R.drawable.bulboff);
	}

	/**
	 * This constructor is never explicitly called.
	 * @param context
	 * @param attrs
	 * @see Context
	 * @see AttributeSet
	 */
	public BulbView(Context context, AttributeSet attrs) {
		super(context, attrs);
		bulb = new ColoredLight();
		detector = new GestureDetector(this);
		this.setImageResource(R.drawable.bulboff);
	}

	/**
	 * This constructor is never explicitly called.
	 * @param context
	 * @param attrs
	 * @param defStyle
	 * @see Context
	 * @see AttributeSet
	 */
	public BulbView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		bulb = new ColoredLight();
		detector = new GestureDetector(this);
		this.setImageResource(R.drawable.bulboff);
	}
	
	/**
	 * Defines what to do in the case of a Touch Event. In this View, we pass the event to our
	 * GestureDetector.
	 * @param event The MotionEvent which triggered this callback.
	 * @see MotionEvent
	 * @return boolean
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	/**
	 * Defines what to do when one's finger touches the screen. It must return true if one hopes
	 * to accomplish anything when touch events happen.
	 * @param event The MotionEvent which triggered this callback--here, a finger touching the screen.
	 * @see MotionEvent
	 * @return boolean
	 */
	@Override
	public boolean onDown(MotionEvent event) {
		return true;
	}

	/**
	 * Defines what to do when a finger is flung across the screen. We don't care about that here.
	 */
	@Override
	public boolean onFling(MotionEvent event, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Defines what to do when a finger is held down for a prolonged period. Don't care.
	 */
	@Override
	public void onLongPress(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Defines what to do when a finger scrolls on the screen. Don't care.
	 */
	@Override
	public boolean onScroll(MotionEvent event, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Defines what to do when the user puts a finger on the screen.
	 */
	@Override
	public void onShowPress(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Defines what to do when a single touch was performed by the user on the screen.
	 * In this case, we turn the bulb off and on based on its current status.
	 * @param event The tap which triggered this callback.
	 * @see MotionEvent
	 * @return boolean
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		// If the bulb is on...
		if (bulb.isOn()) {
			// ...turn it off
			turnOff();
		} else {
			// ...otherwise, it was off and must be turned on
			turnOn();
		}
		return true;
	}
	
	/**
	 * Creates a new ColoredLight with the same state as the original but with a random color.
	 * @return void
	 */
	public void randomize() {
		// Save the previous state of the bulb
		boolean state = bulb.isOn();
		
		// Create a new random ColoredLight
		bulb = new ColoredLight();
		
		// If the old bulb was on...
		if (state) {
			// ...turn the new one on
			turnOn();
		} else {
			// ...otherwise, keep it off
			turnOff();
		}
	}
	
	/**
	 * Turns on the bulb associated with the current view. Sets its image resource accordingly,
	 * then invalidates the View so that it is redrawn.
	 * @return void
	 */
	public void turnOn() {
		// Set the state of the bulb to on
		bulb.setOn(true);
		// If the bulb's color is...
		switch (bulb.getColor()) {
		// ...red...
		case 'R':
			// ...change the Image Resource of the bulb to the red image
			this.setImageResource(R.drawable.bulbred);
			break;
		// ...or if green...
		case 'G':
			// ...change the Image Resource to the green image
			this.setImageResource(R.drawable.bulbgreen);
			break;
		// ...or if blue...
		case 'B':
			// ...change the Image Resource to the blue image
			this.setImageResource(R.drawable.bulbblue);
			break;
		default:
			// And if something crazy happened and the bulb has no color, just keep it off
			this.setImageResource(R.drawable.bulboff);
			break;
		}
		// Invalidate the View so that it is redrawn with the updated Image Resource 
		this.invalidate();
	}
	
	/**
	 * Turns off the bulb associated with the current view. Sets its image resource accordingly,
	 * then invalidates the View so that it is redrawn.
	 * @return void
	 */
	public void turnOff() {
		// Set the bulb's state to off, change the image, and redraw the View
		bulb.setOn(false);
		this.setImageResource(R.drawable.bulboff);
		this.invalidate();
	}
	
	/**
	 * Returns the current status of the bulb associated with this View.
	 * @return boolean
	 */
	public boolean isOn() {
		return bulb.isOn();
	}
}

