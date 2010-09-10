package com.android4cs2.googlecode.domineering;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class DomineeringActivity extends Activity {
	
	private GridView boardView;
	private Button newGame;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        boardView = (GridView) findViewById(R.id.board);
        newGame = (Button) findViewById(R.id.newGame);
        
        boardView.setAdapter(new ImageAdapter(this, getWindowManager().getDefaultDisplay()));
        boardView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// if player 1's turn, use red image. else use blue image. To come later.
				ImageAdapter ia = (ImageAdapter) boardView.getAdapter();
				ia.setItem(position, R.drawable.blue);
				ia.setItem(position+1, R.drawable.blue);
				ia.notifyDataSetChanged();
				
			}
        });
        
        newGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Ask if the user is sure he wants to create a new game; Yes: do it. No: don't.
			}
        	
        });
    }
}