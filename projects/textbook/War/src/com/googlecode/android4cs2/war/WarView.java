package com.googlecode.android4cs2.war;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WarView extends ImageView {

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
	
	public void clear() {
		setImageResource(R.drawable.background);
	}

}
