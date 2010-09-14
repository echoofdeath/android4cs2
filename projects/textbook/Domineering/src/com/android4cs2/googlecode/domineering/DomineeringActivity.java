package com.android4cs2.googlecode.domineering;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class DomineeringActivity extends Activity {
	
	private GridView boardView;
	private Button newGame;
	private TextView playerTurn;
	private Domineering d;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        d = new Domineering();
        
        boardView = (GridView) findViewById(R.id.board);
        newGame = (Button) findViewById(R.id.newGame);
        playerTurn = (TextView) findViewById(R.id.turn);
        
        boardView.setAdapter(new ImageAdapter(this, getWindowManager().getDefaultDisplay(), d));
        boardView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				d.playAt(position / 8, position % 8, d.getPlayer());
				ImageAdapter ia = (ImageAdapter) boardView.getAdapter();
				if (d.getPlayer() == Domineering.HORIZONTAL) {
					playerTurn.setText(R.string.red);
				} else {
					playerTurn.setText(R.string.blue);
				}	
				d.nextPlayer();
				ia.notifyDataSetChanged();
				if (!d.hasLegalMoveFor(d.getPlayer())) {
					playerTurn.setText("GAME OVER!");
				}
			}
        });
        
        newGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Ask if the user is sure he wants to create a new game; Yes: do it. No: don't.
				boardView.setAdapter(new ImageAdapter(getApplicationContext(), getWindowManager().getDefaultDisplay(), d));
				playerTurn.setText(R.string.red);
				if (d.getPlayer() == Domineering.VERTICAL) {
					d.nextPlayer();
				}
			}
        	
        });
    }
}