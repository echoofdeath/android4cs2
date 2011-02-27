package edu.centenary.topspin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameActivity extends Activity {
	/** This is the graphical representation of the gameboard. */
	private GridView boardView;
	
	/** The title bar, where solved will be displayed */
	private TextView title;
	
	/** Tells the grid what images are in which squares */
	private ImageAdapter ia;
	
	/** The model object for TopSpin games */
	private TopSpin ts;
	
	/** Chosen size of the grid */
	private int size;
	
	/** Width of the screen */
	private int width;
	
	/** Height of the screen */
	private int height;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_main);
		
		// Load the chosen screen size from the passed Bundle
		Bundle bundle = this.getIntent().getExtras();
		size = bundle.getInt("size");
		
		// Find the views and set the grid size
		title = (TextView) this.findViewById(R.id.title);		
		boardView = (GridView) this.findViewById(R.id.board);
		boardView.setNumColumns(size);

		// Get the height and width of the screen
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();
        
        // Take the smaller of the two and use it as the dimensions of the gameboard
        if (height < width) {
        	width = height;
        }
        
        // Create the TopSpin object (length, spinsize) and mix it up
        ts = new ArrayTopSpin((size - 1) * 4, size);
        ts.mixup(100);
        
        // An adapter so the grid is displayed programmatically
		ia = new ImageAdapter(getApplicationContext(), width, ts, size);
		boardView.setAdapter(ia);
		
		// The buttons to shift clockwise, counterclockwise, and spin
		ImageButton b1 = (ImageButton)findViewById(R.id.left);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ts.shiftRight();
				updateViews();
			}
			
		});
		ImageButton b2 = (ImageButton)findViewById(R.id.right);
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ts.shiftLeft();
				updateViews();
			}
			
		});
		ImageButton b3 = (ImageButton)findViewById(R.id.rotate);
		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ts.spin();
				updateViews();
			}
		});		
	}
	
	/**
	 * General notification that models have been changed. Used to 
	 * reset grid images and ask if the game is solved
	 */
	public void updateViews() {
		ia.notifyDataSetChanged();				
		if (ts.isSolved()) {
			title.setText(R.string.solved);
		} else {
			title.setText(R.string.gamelabel);
		}	
	}
}
