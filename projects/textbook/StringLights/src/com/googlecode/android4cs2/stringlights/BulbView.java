package com.googlecode.android4cs2.stringlights;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;


public class BulbView extends ImageView implements GestureDetector.OnGestureListener  {
	
	private ColoredLight bulb;

	public BulbView(Context context) {
		super(context);
		bulb = new ColoredLight();
	}

	public BulbView(Context context, AttributeSet attrs) {
		super(context, attrs);
		bulb = new ColoredLight();
	}

	public BulbView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		bulb = new ColoredLight();
	}

	@Override
	public boolean onDown(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
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
			bulb.setOn(false);
			this.setImageResource(R.drawable.bulboff);
		} else {
			bulb.setOn(true);
			switch (bulb.getColor()) {
			case 'R':
				// this.setImageResource(R.drawable.bulbred);
				this.invalidate();
				break;
			case 'G':
				// this.setImageResource(R.drawable.bulbgreen);
				this.invalidate();
				break;
			case 'B':
				// this.setImageResource(R.drawable.bulbblue);
				this.invalidate();
				break;
			default:
				this.setImageResource(R.drawable.bulboff);
				this.invalidate();
				break;
			}
		}
		return true;
	}
}
