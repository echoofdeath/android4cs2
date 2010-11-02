package com.googlecode.android4cs2.gofish;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ChoiceAdapter extends BaseAdapter {

	/** A reference to the array containing booleans that are true if you can ask for that card or false if all of that rank are in a trick. */
	private boolean available[];
	
	/** An array containing the resource IDs for images of the available cards */
	private int choiceRes[] = { R.drawable.ace, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten, R.drawable.jack, R.drawable.queen, R.drawable.king };
	private Context context;
	
	public ChoiceAdapter(boolean choices[], Context c) {
		this.available = choices;
		this.context = c;
	}

	@Override
	public int getCount() {
		int count = 0;
		for (boolean b: available) {
			if (b) {
				count++;
			}
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		return available[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(context);
		if (available[position]) {
			i.setImageResource(choiceRes[position]);
		}
		return i;
	}

}
