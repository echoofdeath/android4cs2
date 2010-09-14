package com.android4cs2.googlecode.domineering;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	/** The Context in which the GridView will be placed. */
	private Context context;
	
	/** A pointer to the game in DomineeringActivity. */
	private Domineering d;
	
	/** The width of the gameboard. */
	private int width;
	
	/**
	 * Constructor for an ImageAdapter. This class maps images to the GridView based on the squares array in the Domineering object.
	 * @param c The context in which the GridView is placed.
	 * @param w The width of the screen and the gameboard.
	 * @param dom The Domineering object with which we are concerned.
	 * @see BaseAdapter
	 */
	public ImageAdapter(Context c, int w, Domineering dom) {
		context = c;
		this.d = dom;
		this.width = w;
	}

	/** Returns the number of squares. */
	@Override
	public int getCount() {
		return 64;
	}

	/** Returns the item at the specified position. Irrelevant in our case. */
	@Override
	public Object getItem(int position) {
		return null;
	}

	/** Returns the ID of the item at the specified position. Irrelevant in our case. */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * This method is called in the mapping of images to the GridView.
	 * @param position The position of the current image in the grid.
	 * @param convertView If this isn't null, its contents are used in lieu of creating a new ImageView.
	 * @param parent The ViewGroup which will hold the ImageView.
	 * @return View
	 * @see View
	 * @see ViewGroup
	 * @see ImageView
	 */
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
		if (d.isCovered(position / 8, position % 8)) {
			view.setImageResource(R.drawable.blue);			
		} else {
			view.setImageResource(R.drawable.blank);
		}
		return view;
	}

}
