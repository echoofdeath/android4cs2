package edu.centenary.topspin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class GameActivity extends Activity {
	/** This is the graphical representation of the gameboard. */
	private GridView boardView;
	private TextView title;
	private ImageAdapter ia;
	private TopSpin ts;
	private int size;
	private int width;
	private int height;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = this.getIntent().getExtras();
		size = bundle.getInt("size");
		Log.d("GameActivity", "found size = " + size);
		switch (size) {
		case 3:
			setContentView(R.layout.three_main);
			break;
		case 4:
			setContentView(R.layout.four_main);
			break;
		case 5:
			setContentView(R.layout.five_main);
			break;
		}
		
		title = (TextView) this.findViewById(R.id.title);		
		boardView = (GridView) this.findViewById(R.id.board);
		
        // Get the height and width of the screen
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();
        
        // Take the smaller of the two and use it as the dimensions of the gameboard
        if (height < width) {
        	width = height;
        }
        
        ts = new ArrayTopSpin((size - 1) * 4, size);
        ts.mixup(100);
        
		ia = new ImageAdapter(getApplicationContext(), width, ts, size);
		boardView.setAdapter(ia);
		
		Button b1 = (Button)findViewById(R.id.left);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ts.shiftRight();
				updateViews();
			}
			
		});
		Button b2 = (Button)findViewById(R.id.right);
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ts.shiftLeft();
				updateViews();
			}
			
		});
		Button b3 = (Button)findViewById(R.id.rotate);
		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ts.spin();
				updateViews();
			}
		});		
	}
	
	public void updateViews() {
		ia.notifyDataSetChanged();				
		if (ts.isSolved()) {
			title.setText(R.string.solved);
		} else {
			title.setText(R.string.gamelabel);
		}
		
	}
}
