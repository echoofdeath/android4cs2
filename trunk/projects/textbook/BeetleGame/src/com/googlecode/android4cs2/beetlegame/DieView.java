package com.googlecode.android4cs2.beetlegame;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.view.MotionEvent;

public class DieView extends ImageView {

	private Die d;
	private Beetle[] bugs = new Beetle[2];
	private BeetleView[] surfaces = new BeetleView[2];
	private int player = 0;
	
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
				bugs[player].addBody();
				break;
			case 2:
				setImageResource(R.drawable.die2);
				bugs[player].addHead();
				break;
			case 3:
				setImageResource(R.drawable.die3);
				bugs[player].addLeg();
				break;
			case 4:
				setImageResource(R.drawable.die4);
				bugs[player].addEye();
				break;
			case 5:
				setImageResource(R.drawable.die5);
				bugs[player].addFeeler();
				break;
			case 6:
				setImageResource(R.drawable.die6);
				// bugs[player].addTail();
				break;
			default:
				break;
			}
			invalidate();
			
			surfaces[player].updateImages();
			// player++;
			
			return true;
		}
		return false;
	}
	
	public Die getDie() {
		return d;
	}
	
	private void setBugs(Beetle one, Beetle two) {
		bugs[0] = one;
		bugs[1] = two;
	}
	
	public void setBugSurfaces(BeetleView one, BeetleView two) {
		surfaces[0] = one;
		surfaces[1] = two;
		setBugs(one.getBug(), two.getBug()); // must change null back to two.getBug()
	}
}
