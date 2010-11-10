package com.googlecode.android4cs2.war;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class CardView extends ImageView {

	private ArrayList<Card> cards;
	
	public static int[] cardIDs = { R.drawable.x1, R.drawable.x2, R.drawable.x3, R.drawable.x4, R.drawable.x5, R.drawable.x6, R.drawable.x7, R.drawable.x8, R.drawable.x9, R.drawable.x10, R.drawable.x11, R.drawable.x12, R.drawable.x13, R.drawable.x14, R.drawable.x15, R.drawable.x16, R.drawable.x17, R.drawable.x18, R.drawable.x19, R.drawable.x20, R.drawable.x21, R.drawable.x22, R.drawable.x23, R.drawable.x24, R.drawable.x25, R.drawable.x26, R.drawable.x27, R.drawable.x28, R.drawable.x29, R.drawable.x30, R.drawable.x31, R.drawable.x32, R.drawable.x33, R.drawable.x34, R.drawable.x35, R.drawable.x36, R.drawable.x37, R.drawable.x38, R.drawable.x39, R.drawable.x40, R.drawable.x41, R.drawable.x42, R.drawable.x43, R.drawable.x44, R.drawable.x45, R.drawable.x46, R.drawable.x47, R.drawable.x48, R.drawable.x49, R.drawable.x50, R.drawable.x51, R.drawable.x52 };
	
	public CardView(Context context) {
		super(context);
		cards = new ArrayList<Card>();
	}

	public CardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		cards = new ArrayList<Card>();
	}

	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		cards = new ArrayList<Card>();
	}
	
	public void addCard(Card c) {
		this.cards.add(c);
		updateImages();
	}
	
	public void clear() {
		this.cards.clear();
		updateImages();
	}
	
	public void updateImages() {
		// Aces are at the beginning of this deck, so we treat them separately
		if (cards.size() == 0) {
			setImageResource(R.drawable.background);
		} else {
			Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.xb1pl);
			Bitmap cardForSize = BitmapFactory.decodeResource(getResources(), R.drawable.x2);
			Bitmap output = Bitmap.createBitmap(b.getWidth()*(cards.size()-1)+cardForSize.getWidth(), cardForSize.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas cvs = new Canvas(output);
			for (int i = 0; i < cards.size(); i++) {
				Card c = cards.get(i);
				
				if (c.getRank() == 1) {
					Bitmap toDraw = null;
					switch (c.getSuit()) {
					case Card.SPADES:
						toDraw = BitmapFactory.decodeResource(getResources(), R.drawable.x2);
						break;
					case Card.HEARTS:
						toDraw = BitmapFactory.decodeResource(getResources(), R.drawable.x3);
						break;
					case Card.DIAMONDS:
						toDraw = BitmapFactory.decodeResource(getResources(), R.drawable.x4);
						break;
					case Card.CLUBS:
						toDraw = BitmapFactory.decodeResource(getResources(), R.drawable.x1);
						break;
					default:
						setImageResource(R.drawable.background);
						break;
					}
					cvs.drawBitmap(toDraw, b.getWidth()*i, 0, null);
				} else {
					Log.d("Updating images...", "" + (52 - (c.getRank() - 1)*4 + (c.getSuit()+1)%4));
					Bitmap toDraw = BitmapFactory.decodeResource(getResources(), cardIDs[52 - (c.getRank() - 1)*4 + (c.getSuit()+1)%4]);
					cvs.drawBitmap(toDraw, b.getWidth()*i, 0, null);
				}
			}
			setImageBitmap(output);
		}
	}
}