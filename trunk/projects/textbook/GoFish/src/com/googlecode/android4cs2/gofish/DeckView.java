package com.googlecode.android4cs2.gofish;

import java.util.Queue;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DeckView extends ImageView {

	private Queue<Card> q;
	
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
		if (!q.isEmpty()) {
			setImageResource(R.drawable.xb1fv);
		} else {
			setImageResource(R.drawable.blank);
		}
	}
	
	public void setQ(Queue<Card> q) {
		this.q = q;
	}
}
