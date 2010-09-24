package com.googlecode.android4cs2.beetlegame;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DieView extends ImageView {

	private Die die;
	
	public DieView(Context context) {
		super(context);
	}

	public DieView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setDie(Die d) {
		this.die = d;
	}
	
	public void updateImages() {
		switch (die.getTopFace()) {
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
	}

}
