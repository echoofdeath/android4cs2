package com.googlecode.android4cs2.beetlegame;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MotionEvent;

public class DieView extends ImageView {

	private Die d;
	private Beetle[] bugs = new Beetle[2];
	private BeetleView[] surfaces = new BeetleView[2];
	private TextView turn;
	private int[] labels = { R.string.playerone, R.string.playertwo };
	private int[] winners = { R.string.playerone_win, R.string.playertwo_win };
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
			boolean breakOut = false;
			
			switch (d.getTopFace()) {
			case 1:
				setImageResource(R.drawable.die1);
				if (!bugs[player % 2].addBody()) {
					breakOut = true;
				}
				break;
			case 2:
				setImageResource(R.drawable.die2);
				if (!bugs[player % 2].addHead()) {
					breakOut = true;
				}
				break;
			case 3:
				setImageResource(R.drawable.die3);
				if (!bugs[player % 2].addLeg()) {
					breakOut = true;
				}
				break;
			case 4:
				setImageResource(R.drawable.die4);
				if (!bugs[player % 2].addEye()) {
					breakOut = true;
				}
				break;
			case 5:
				setImageResource(R.drawable.die5);
				if (!bugs[player % 2].addFeeler()) {
					breakOut = true;
				}
				break;
			case 6:
				setImageResource(R.drawable.die6);
				// bugs[player % 2].addTail();
				break;
			default:
				break;
			}
			
			invalidate();
			
			if (breakOut) {
				surfaces[player % 2].setColorFilter(Color.GRAY);
				surfaces[++player % 2].clearColorFilter();
				turn.setText(labels[player % 2]);
				return true;
			}
			
			if (bugs[player % 2].isComplete()) {
				turn.setText(winners[player % 2]);
				
			}
			
			surfaces[player % 2].updateImages();
		}
		
		return true;
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
		setBugs(one.getBug(), two.getBug());
	}
	
	public void setTextLabel(TextView tv) {
		turn = tv;
	}
}
