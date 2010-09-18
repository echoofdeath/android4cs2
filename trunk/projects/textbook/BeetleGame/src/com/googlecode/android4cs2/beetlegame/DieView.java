package com.googlecode.android4cs2.beetlegame;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.view.MotionEvent;

public class DieView extends ImageView {

	private Die d;
	
	public DieView(Context context) {
		super(context);
		setImageResource(R.drawable.die4);
		d = new Die();
	}

	public DieView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setImageResource(R.drawable.die4);
		d = new Die();
	}

	public DieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setImageResource(R.drawable.die4);
		d = new Die();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
			d.roll();
			switch (d.getTopFace()) {
			case 1:
				setImageResource(R.drawable.die1);
				break;
			case 2:
				setImageResource(R.drawable.die2);
				break;
			case 3:
				setImageResource(R.drawable.die3);
				break;
			case 4:
				setImageResource(R.drawable.die4);
				break;
			case 5:
				setImageResource(R.drawable.die5);
				break;
			case 6:
				setImageResource(R.drawable.die6);
				break;
			default:
				break;
			}
			invalidate();
			return true;
		}
		return false;
	}
	
	public Die getDie() {
		return d;
	}

}
