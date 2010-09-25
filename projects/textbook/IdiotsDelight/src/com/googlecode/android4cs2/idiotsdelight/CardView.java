package com.googlecode.android4cs2.idiotsdelight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CardView extends ImageView {

	private Stack<Card> s;
	private int stackNum;
	
	private boolean highlight = false;
	
	private int[] cardIDs = { R.drawable.x1, R.drawable.x2, R.drawable.x3, R.drawable.x4, R.drawable.x5, R.drawable.x6, R.drawable.x7, R.drawable.x8, R.drawable.x9, R.drawable.x10, R.drawable.x11, R.drawable.x12, R.drawable.x13, R.drawable.x14, R.drawable.x15, R.drawable.x16, R.drawable.x17, R.drawable.x18, R.drawable.x19, R.drawable.x20, R.drawable.x21, R.drawable.x22, R.drawable.x23, R.drawable.x24, R.drawable.x25, R.drawable.x26, R.drawable.x27, R.drawable.x28, R.drawable.x29, R.drawable.x30, R.drawable.x31, R.drawable.x32, R.drawable.x33, R.drawable.x34, R.drawable.x35, R.drawable.x36, R.drawable.x37, R.drawable.x38, R.drawable.x39, R.drawable.x40, R.drawable.x41, R.drawable.x42, R.drawable.x43, R.drawable.x44, R.drawable.x45, R.drawable.x46, R.drawable.x47, R.drawable.x48, R.drawable.x49, R.drawable.x50, R.drawable.x51, R.drawable.x52 };
	
	public CardView(Context context) {
		super(context);
	}

	public CardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setStack(Stack<Card> s, int stackNum) {
		this.s = s;
		this.stackNum = stackNum;
	}
	
	public void toggleHighlight() {
		highlight = !highlight;
	}
	
	public boolean isHighlighted() {
		return highlight;
	}
	
	public int getStackNum() {
		return stackNum;
	}
	
	public void updateImages() {
		// Aces are at the beginning of this deck, so we treat them separately
		if (s.peek().getRank() == 1) {
			switch (s.peek().getSuit()) {
			case Card.SPADES:
				setImageResource(R.drawable.x2);
				break;
			case Card.HEARTS:
				setImageResource(R.drawable.x3);
				break;
			case Card.DIAMONDS:
				setImageResource(R.drawable.x4);
				break;
			case Card.CLUBS:
				setImageResource(R.drawable.x1);
				break;
			default:
				// setImageResource(R.drawable.empty);
				break;
			}
		} else {
			setImageResource(cardIDs[52 - (s.peek().getRank() - 1)*4 + (s.peek().getSuit()+1)]);
		}
		
		invalidate();
	}
}
