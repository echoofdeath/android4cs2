package com.googlecode.android4cs2.beetlegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class BeetleView extends ImageView {

	private Beetle b;
	private int[] legRes = { R.drawable.leg1, R.drawable.leg2, R.drawable.leg3, R.drawable.leg4, R.drawable.leg5, R.drawable.leg6 };
	private int[] feelRes = { R.drawable.ant1, R.drawable.ant2 };
	private int[] eyeRes = { R.drawable.eye1, R.drawable.eye2 };
	private Bitmap[] legMaps = new Bitmap[legRes.length];
	private Bitmap[] eyeMaps = new Bitmap[eyeRes.length];
	private Bitmap[] feelMaps = new Bitmap[feelRes.length];
	private Bitmap headMap;
	private Bitmap bodyMap;
	
	public BeetleView(Context context) {
		super(context);
		Log.d("Con1: ", "Here.");
		init();
	}

	public BeetleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("Con2: ", "Here.");
		init();
	}

	public BeetleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Log.d("Con3: ", "Here.");
		init();
	}
	
	public void init() {
		this.b = new Beetle();
		setFocusable(true);
		
		for (int i = 0; i < legRes.length; i++) {
			legMaps[i] = BitmapFactory.decodeResource(getResources(), legRes[i]);
		}
		
		for (int i = 0; i < eyeRes.length; i++) {
			eyeMaps[i] = BitmapFactory.decodeResource(getResources(), eyeRes[i]);
		}
		
		for (int i = 0; i < feelRes.length; i++) {
			feelMaps[i] = BitmapFactory.decodeResource(getResources(), feelRes[i]);
		}
		
		headMap = BitmapFactory.decodeResource(getResources(), R.drawable.head);
		bodyMap = BitmapFactory.decodeResource(getResources(), R.drawable.body);
		
	}

	public Beetle getBug() {
		return b;
	}
	
	public void updateImages() {
		Bitmap output = Bitmap.createBitmap(200, 200, bodyMap.getConfig());
		Canvas c = new Canvas(output);
		
		int legs = b.getLegs();
		int feelers = b.getFeelers();
		int eyes = b.getEyes();
		
		if (legs > 0) {
			for (int i = 0; i < legs; i++) {
				c.drawBitmap(legMaps[i], new Matrix(), null);
			}
		}
		
		if (feelers > 0) {
			for (int i = 0; i < feelers; i++) {
				c.drawBitmap(feelMaps[i], new Matrix(), null);
			}
		}
		
		if (b.getHead()) {
			c.drawBitmap(headMap, new Matrix(), null);
		}
		
		if (eyes > 0) {
			for (int i = 0; i < eyes; i++) { // lol
				c.drawBitmap(eyeMaps[i], new Matrix(), null);
			}
		}
		
		if (b.getBody()) {
			c.drawBitmap(bodyMap, new Matrix(), null);
		}
		
		if (b.getTail()) {
			// c.drawBitmap(tailMap, new Matrix(), null);
		}
		
		this.setImageBitmap(output);
	}
	
	public void newGame() {
		this.b = new Beetle();
		updateImages();
	}
}
