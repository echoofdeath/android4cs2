package com.googlecode.android4cs2.gofish;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CompHandView extends ImageView {

	private GoFishHand hand;
	
	public CompHandView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CompHandView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CompHandView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void updateImages() {
		int numCards = hand.size();
		if (numCards == 0) {
			setImageResource(R.drawable.background);
			return;
		}
		Bitmap edge = BitmapFactory.decodeResource(getResources(), R.drawable.xb1pl);
		Bitmap top = BitmapFactory.decodeResource(getResources(), R.drawable.xb1fv);
		Bitmap output = Bitmap.createBitmap(edge.getWidth()*(numCards-1)+top.getWidth(), top.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas cvs = new Canvas(output);
		for (int i = 0; i < numCards-1; i++) {
			cvs.drawBitmap(edge, edge.getWidth()*i, 0, null);
		}
		cvs.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.xb1fv), (numCards-1)*edge.getWidth(), 0, null);
		setImageBitmap(output);
	}
	
	public void setHand(GoFishHand hand) {
		this.hand = hand;
		updateImages();
	}
}
