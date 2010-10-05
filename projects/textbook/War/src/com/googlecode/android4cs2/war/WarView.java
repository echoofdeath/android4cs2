package com.googlecode.android4cs2.war;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class WarView extends ImageView {

	private Queue<Card> q;
	
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
	
	public void setQ(Queue<Card> q) {
		this.q = q;
	}
	
	public void clear() {
		setImageResource(R.drawable.background);
	}
	
	public void updateImages() {
		if (q == null || q.isEmpty()) {
			setImageResource(R.drawable.background);
		} else {
			Bitmap output = Bitmap.createBitmap(102, 96, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(output);
			
			reverse();
			
			int offset = 0;
			for (int i = 0; i < 3; i++) {
				Card k = q.remove();
				Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.background);

				if (k.getRank() == 1) {
					switch (k.getSuit()) {
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
					b = BitmapFactory.decodeResource(getResources(), CardView.cardIDs[52 - (k.getRank() - 1)*4 + (k.getSuit()+1)%4]);
				}
				
				c.drawBitmap(b, offset, 0, null);
				offset += 10;
				q.add(k);
			}
			
			reverse();
			setImageBitmap(output);
		}
	}
	
	private void reverse() {
		Card c1 = q.remove();
		Card c2 = q.remove();
		Card c3 = q.remove();
		q.add(c3);
		q.add(c2);
		q.add(c1);
	}

}
