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
	
	/** The number of squares in the grid */
	private int squares;
	
	/** Maximum number of pieces, from 5x5 board */
	public static final int MAXPIECES = 16;

	/** References to the images for the pieces */
	private int[] pIDs = { R.drawable.x1, R.drawable.x2, R.drawable.x3, 
			R.drawable.x4, R.drawable.x5, R.drawable.x6, R.drawable.x7, 
			R.drawable.x8, R.drawable.x9, R.drawable.x10, R.drawable.x11, 
			R.drawable.x12, R.drawable.x13, R.drawable.x14, R.drawable.x15, 
			R.drawable.x16, R.drawable.r1, R.drawable.r2, R.drawable.r3, 
			R.drawable.r4, R.drawable.r5, R.drawable.r6, R.drawable.r7, 
			R.drawable.r8, R.drawable.r9, R.drawable.r10, R.drawable.r11, 
			R.drawable.r12, R.drawable.r13, R.drawable.r14, R.drawable.r15, R.drawable.r16}; 

	/**
	 * Constructor for an ImageAdapter. This class maps images to the GridView based 
	 * on the pieces in the TopSpin object.
	 * @param c The context in which the GridView is placed.
	 * @param w The width of the screen and the gameboard.
	 * @param top The TopSpin object with which we are concerned.
	 * @param n total number of squares
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
			view.setLayoutParams(new GridView.LayoutParams(width/(squares + 1), width/(squares + 1)));
			view.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view.setPadding(3,3,3,3);
		} else {
			view = (ImageView) convertView;
		}
		int row = position / squares;
		int column = position % squares;
		
		// only draw pieces in the outside box, make them clockwise
		if (row == 0 || column == 0 || row == squares - 1 || column == squares - 1) {
			int where = 0;
			int red = 0;
			if (row == 0) {
				where = 2 * (squares - 1) + column;
			} else if (row == squares - 1) {
				where = (squares - 1 - column);
				// The bottom row should be red pieces
				red = MAXPIECES;
			} else if (column == 0) {
				where = (squares - 1) + (squares - 1 - row);
			} else {
				where = 3 * (squares - 1) + row;
			}
			view.setImageResource(pIDs[red + ts.get(where) - 1]);
		} 
		return view;
	}
}
