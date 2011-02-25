package edu.centenary.topspin;

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
	private TopSpin ts;
	
	/** The width of the gameboard. */
	private int width;
	
	private int squares;

	private int[] pIDs = { R.drawable.x1, R.drawable.x2, R.drawable.x3, R.drawable.x4, R.drawable.x5, R.drawable.x6, R.drawable.x7, R.drawable.x8, R.drawable.x9, R.drawable.x10, R.drawable.x11, R.drawable.x12, R.drawable.x13, R.drawable.x14, R.drawable.x15, R.drawable.x16}; //, R.drawable.x17, R.drawable.x18, R.drawable.x19, R.drawable.x20, R.drawable.x21, R.drawable.x22, R.drawable.x23, R.drawable.x24, R.drawable.x25, R.drawable.x26, R.drawable.x27, R.drawable.x28, R.drawable.x29, R.drawable.x30, R.drawable.x31, R.drawable.x32, R.drawable.x33, R.drawable.x34, R.drawable.x35, R.drawable.x36, R.drawable.x37, R.drawable.x38, R.drawable.x39, R.drawable.x40, R.drawable.x41, R.drawable.x42, R.drawable.x43, R.drawable.x44, R.drawable.x45, R.drawable.x46, R.drawable.x47, R.drawable.x48, R.drawable.x49, R.drawable.x50, R.drawable.x51, R.drawable.x52 };
	/**
	 * Constructor for an ImageAdapter. This class maps images to the GridView based on the squares array in the Domineering object.
	 * @param c The context in which the GridView is placed.
	 * @param w The width of the screen and the gameboard.
	 * @param dom The Domineering object with which we are concerned.
	 * @see BaseAdapter
	 */
	public ImageAdapter(Context c, int w, TopSpin top, int n) {
		context = c;
		this.ts = top;
		this.width = w;
		this.squares = n;
	}

	/** Returns the number of squares. */
	@Override
	public int getCount() {
		return squares * squares;
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
			view.setLayoutParams(new GridView.LayoutParams(width/squares, width/squares));
			view.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view.setPadding(2,2,2,2);
		} else {
			view = (ImageView) convertView;
		}
		int row = position / squares;
		int column = position % squares;
		if (row == 0 || column == 0 || row == squares - 1 || column == squares - 1) {
			int where = 0;
			if (row == 0) {
				where += column;
			} else if (row == squares - 1) {
				where = 2 * (squares - 1) + (squares - 1 - column);
			} else if (column == 0) {
				where = 3 * (squares - 1) + (squares - 1 - row);
			} else {
				where = (squares - 1) + row;
			}
			view.setImageResource(pIDs[ts.get(where) - 1]);
		} 
		return view;
	}

}
