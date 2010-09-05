package com.googlecode.android4cs2.stringlights;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;


public class BulbView extends ImageView implements GestureDetector.OnGestureListener  {
	
	private ColoredLight bulb;
	private GestureDetector detector;

	public BulbView(Context context) {
		super(context);
		bulb = new ColoredLight();
		detector = new GestureDetector(this);
		this.setImageResource(R.drawable.bulboff);
	}

	public BulbView(Context context, AttributeSet attrs) {
		super(context, attrs);
		bulb = new ColoredLight();
		detector = new GestureDetector(this);
		this.setImageResource(R.drawable.bulboff);
	}

	public BulbView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		bulb = new ColoredLight();
		detector = new GestureDetector(this);
		this.setImageResource(R.drawable.bulboff);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onFling(MotionEvent event, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent event, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		if (bulb.isOn()) {
			turnOff();
		} else {
			turnOn();
		}
		return true;
	}
	
	public void randomize() {
		boolean state = bulb.isOn();
		bulb = new ColoredLight();
		
		if (state) {
			turnOn();
		} else {
			turnOff();
		}
	}
	
	public void turnOn() {
		bulb.setOn(true);
		switch (bulb.getColor()) {
		case 'R':
			this.setImageResource(R.drawable.bulbred);
			break;
		case 'G':
			this.setImageResource(R.drawable.bulbgreen);
			break;
		case 'B':
			this.setImageResource(R.drawable.bulbblue);
			break;
		default:
			this.setImageResource(R.drawable.bulboff);
			break;
		}
		this.invalidate();
	}
	
	public void turnOff() {
		bulb.setOn(false);
		this.setImageResource(R.drawable.bulboff);
		this.invalidate();
	}
	
	public boolean isOn() {
		return bulb.isOn();
	}
	
	public Character getColor() {
		return bulb.getColor();
	}
}

