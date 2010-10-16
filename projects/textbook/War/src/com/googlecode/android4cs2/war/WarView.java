package com.googlecode.android4cs2.war;

import java.util.Stack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WarView extends ImageView {

	private Card card;
	
	public WarView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public WarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public WarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void setCard(Card c) {
		this.card = c;
	}
	
	public void clear() {
		setImageResource(R.drawable.background);
	}
	
	public void updateImages(int numCards) {
		if (card == null) {
			setImageResource(R.drawable.background);
		} else {
			Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.xb1pl);
			Bitmap output = Bitmap.createBitmap(b.getWidth()*(numCards-1)+72, 96, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(output);
			
			int offset = 0;
			for (int i = 0; i < numCards-1; i++) {
				c.drawBitmap(b, offset, 0, null);
				offset += b.getWidth();
			}
			
			b = BitmapFactory.decodeResource(getResources(), R.drawable.background);

			if (card.getRank() == 1) {
				switch (card.getSuit()) {
				case Card.SPADES:
					b = BitmapFactory.decodeResource(getResources(), R.drawable.x2);
					break;
				case Card.HEARTS:
					b = BitmapFactory.decodeResource(getResources(), R.drawable.x3);
					break;
				case Card.DIAMONDS:
					b = BitmapFactory.decodeResource(getResources(), R.drawable.x4);
					break;
				case Card.CLUBS:
					b = BitmapFactory.decodeResource(getResources(), R.drawable.x1);
					break;
				default:
					setImageResource(R.drawable.background);
					break;
				}
			} else {
				b = BitmapFactory.decodeResource(getResources(), CardView.cardIDs[52 - (card.getRank() - 1)*4 + (card.getSuit()+1)%4]);
			}
			
			c.drawBitmap(b, offset, 0, null);
			setImageBitmap(output);
		}
	}
	
/*	private void reverse() {
		Stack<Card> newQ = new Stack<Card>();
		while (!q.isEmpty()) {
			newQ.push(q.remove());
		}
		
		while (!newQ.isEmpty()) {
			q.add(newQ.pop());
		}
	} */

}
