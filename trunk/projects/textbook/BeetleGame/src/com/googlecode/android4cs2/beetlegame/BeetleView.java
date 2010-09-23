package com.googlecode.android4cs2.beetlegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

/** A subclass of ImageView which contains a Beetle and displays an image of a Beetle */
public class BeetleView extends ImageView {

	/** The data structure which contains what body parts this BeetleView should display */
	private Beetle b;
	
	/** An array of the resource IDs of the leg images */
	private int[] legRes = { R.drawable.leg1, R.drawable.leg2, R.drawable.leg3, R.drawable.leg4, R.drawable.leg5, R.drawable.leg6 };
	
	/** An array of the resource IDs of the feeler images */
	private int[] feelRes = { R.drawable.ant1, R.drawable.ant2 };
	
	/** An array of resource IDs of the eyes */
	private int[] eyeRes = { R.drawable.eye1, R.drawable.eye2 };
	
	/** An array of Bitmap images of the legs */
	private Bitmap[] legMaps = new Bitmap[legRes.length];
	
	/** An array of Bitmap images of the eyes */
	private Bitmap[] eyeMaps = new Bitmap[eyeRes.length];
	
	/** An array of Bitmap images of the feelers */
	private Bitmap[] feelMaps = new Bitmap[feelRes.length];
	
	/** A Bitmap image of the head */
	private Bitmap headMap;
	
	/** A Bitmap image of the body */
	private Bitmap bodyMap;
	
	// Constructors
	public BeetleView(Context context) {
		super(context);
		init();
	}

	/** Default constructor */
	public BeetleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BeetleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	/** Initializes all the pertinent information needed by this class and converts the PNG images into Bitmaps */
	public void init() {
		// Initialize a new Beetle object
		this.b = new Beetle();
		setFocusable(true);
		
		// Convert each leg image into a Bitmap
		for (int i = 0; i < legRes.length; i++) {
			legMaps[i] = BitmapFactory.decodeResource(getResources(), legRes[i]);
		}
		
		// Convert each eye image into a Bitmap
		for (int i = 0; i < eyeRes.length; i++) {
			eyeMaps[i] = BitmapFactory.decodeResource(getResources(), eyeRes[i]);
		}
		
		// Convert each feeler image into a Bitmap
		for (int i = 0; i < feelRes.length; i++) {
			feelMaps[i] = BitmapFactory.decodeResource(getResources(), feelRes[i]);
		}
		
		// Convert head and body images into Bitmaps
		headMap = BitmapFactory.decodeResource(getResources(), R.drawable.head);
		bodyMap = BitmapFactory.decodeResource(getResources(), R.drawable.body);
		
	}

	/** Returns a reference to the BeetleView's Beetle object
	 * @return Beetle */
	public Beetle getBug() {
		return b;
	}
	
	/** Draws the appropriate parts to a composite Bitmap and displays it */
	public void updateImages() {
		// Create our output Bitmap
		Bitmap output = Bitmap.createBitmap(200, 200, bodyMap.getConfig());
		Canvas c = new Canvas(output);
		
		// Find out the current state of the Beetle
		int legs = b.getLegs();
		int feelers = b.getFeelers();
		int eyes = b.getEyes();
		
		// Draw the legs to the output Bitmap...
		if (legs > 0) {
			for (int i = 0; i < legs; i++) {
				c.drawBitmap(legMaps[i], new Matrix(), null);
			}
		}
		
		// ...then feelers...
		if (feelers > 0) {
			for (int i = 0; i < feelers; i++) {
				c.drawBitmap(feelMaps[i], new Matrix(), null);
			}
		}
		
		// ...then the head...
		if (b.getHead()) {
			c.drawBitmap(headMap, new Matrix(), null);
		}
		
		// ...then the eyes...
		if (eyes > 0) {
			for (int i = 0; i < eyes; i++) { // lol
				c.drawBitmap(eyeMaps[i], new Matrix(), null);
			}
		}
		
		// ...then the body...
		if (b.getBody()) {
			c.drawBitmap(bodyMap, new Matrix(), null);
		}
		
		// ...then the funky tail
		if (b.getTail()) {
			// c.drawBitmap(tailMap, new Matrix(), null);
		}
		
		// ...and finally display the composite image
		this.setImageBitmap(output);
	}
	
	/** Initializes a new Beetle object and updates this ImageView accordingly */
	public void newGame() {
		this.b = new Beetle();
		updateImages();
	}
}
