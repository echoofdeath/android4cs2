package com.android4cs2.googlecode.domineering;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private Integer[] tileIds = { R.drawable.red, R.drawable.blue };
	private Display display;
	private int width;
	
	public ImageAdapter(Context c, Display d) {
		context = c;
		display = d;
		width = display.getWidth();
	}

	@Override
	public int getCount() {
		return tileIds.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view;
		if (convertView == null) {
			view = new ImageView(context);
			view.setLayoutParams(new GridView.LayoutParams(width/8, width/8));
			view.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view.setPadding(2,2,2,2);
		} else {
			view = (ImageView) convertView;
		}
		
		view.setImageResource(tileIds[position]);
		return view;
	}

}
