package com.googlecode.android4cs2.beetlegame;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.view.MotionEvent;

public class DieView extends ImageView {

	private Die d;
	private BeetleSurface bs;
	
	public DieView(Context context) {
		super(context);
		setImageResource(R.drawable.die4);
		d = new Die();
		Log.d("Con1.", "");
	}

	public DieView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setImageResource(R.drawable.die4);
		d = new Die();
		Log.d("Con2.", "");
	}

	public DieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setImageResource(R.drawable.die4);
		d = new Die();
		Log.d("Con3.", "");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
			d.roll();
			// Problem here: null pointer exception. Need pointer to BeetleSurface
			Beetle current = bs.getBug();
			switch (d.getTopFace()) {
			case 1:
				setImageResource(R.drawable.die1);
				current.addBody();
				break;
			case 2:
				setImageResource(R.drawable.die2);
				current.addHead();
				break;
			case 3:
				setImageResource(R.drawable.die3);
				current.addLeg();
				break;
			case 4:
				setImageResource(R.drawable.die4);
				current.addEye();
				break;
			case 5:
				setImageResource(R.drawable.die5);
				current.addFeeler();
				break;
			case 6:
				setImageResource(R.drawable.die6);
				// current.addTail();
				break;
			default:
				break;
			}
			invalidate();
			bs.invalidate();
			
			return true;
		}
		return false;
	}
	
	public Die getDie() {
		return d;
	}
}
