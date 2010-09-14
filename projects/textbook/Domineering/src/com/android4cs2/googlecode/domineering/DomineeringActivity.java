package com.android4cs2.googlecode.domineering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class DomineeringActivity extends Activity {
	
	private GridView boardView;
	private TextView playerTurn;
	private Domineering d;
	private int width;
	private int height;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if (getLastNonConfigurationInstance() != null) {
        	d = (Domineering) getLastNonConfigurationInstance();
        } else {
        	d = new Domineering();
        }
        
        boardView = (GridView) findViewById(R.id.board);
        playerTurn = (TextView) findViewById(R.id.turn);
        
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();
        
        if (height < width) {
        	width = height;
        }
        
        boardView.setAdapter(new ImageAdapter(this, width, d));
        boardView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if (d.getPlayer() == Domineering.HORIZONTAL && position % 8 == 7) {
					playerTurn.setText("Invalid move. Still on horizontal's turn.");
					return;
				} else if (d.getPlayer() == Domineering.VERTICAL && position / 8 == 7) {
					playerTurn.setText("Invalid move. Still on vertical's turn.");
					return;
				}
				
				d.playAt(position / 8, position % 8, d.getPlayer());
				ImageAdapter ia = (ImageAdapter) boardView.getAdapter();
				if (d.getPlayer() == Domineering.HORIZONTAL) {
					playerTurn.setText(R.string.blue);
				} else {
					playerTurn.setText(R.string.red);
				}	
				d.nextPlayer();
				ia.notifyDataSetChanged();
				if (!d.hasLegalMoveFor(d.getPlayer())) {
					playerTurn.setText("GAME OVER!");
					boardView.setClickable(false);
				}
			}
        });
    }
    
    public Object onRetainNonConfigurationInstance() {
    	return d;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.newGame:
    		d = new Domineering();
    		boardView.setAdapter(new ImageAdapter(getApplicationContext(), width, d));
			boardView.setClickable(true);
			playerTurn.setText(R.string.blue);
			if (d.getPlayer() == Domineering.HORIZONTAL) {
				d.nextPlayer();
			}
    		return true;
    	case R.id.help:
    		Intent i = new Intent(DomineeringActivity.this, HelpActivity.class);
    		startActivity(i);
    		return true;
    	default:
    		return false;
    	}
    }
}