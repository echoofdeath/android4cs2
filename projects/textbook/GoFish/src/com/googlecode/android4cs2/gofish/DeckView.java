package com.googlecode.android4cs2.gofish;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DeckView extends ImageView {

	private Deck d;
	
	public DeckView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DeckView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DeckView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void updateImages() {
		if (!d.isEmpty()) {
			setImageResource(R.drawable.xb1fv);
		} else {
			setImageResource(R.drawable.blank);
		}
	}
	
	public void setDeck(Deck d) {
		this.d = d;
		updateImages();
	}
}
