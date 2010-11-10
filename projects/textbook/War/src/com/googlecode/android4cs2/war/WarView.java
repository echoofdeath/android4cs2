package com.googlecode.android4cs2.war;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WarView extends ImageView {

	private Bitmap output;
	private Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.xb1pl);
	private Bitmap back = BitmapFactory.decodeResource(getResources(), R.drawable.xb1fv);
	
	public WarView(Context context) {
		super(context);
	}

	public WarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void clear() {
		setImageResource(R.drawable.background);
	}
	
	public void updateImages(int numWars) {
		if (numWars == 0) {
			setImageResource(R.drawable.background);
		} else {
			output = Bitmap.createBitmap(b.getWidth()*(numWars*4-1)+72, 96, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(output);
			
			int offset = 0;
			for (int j = 0; j < numWars; j++) {
				for (int i = 0; i < 3; i++) {
					if (j+1 == numWars && i == 2) {
						c.drawBitmap(back, offset, 0, null);
					} else {
						c.drawBitmap(b, offset, 0, null);
						offset += b.getWidth();
					}
				}				
				
			}
			setImageBitmap(output);
		}
	}
}
