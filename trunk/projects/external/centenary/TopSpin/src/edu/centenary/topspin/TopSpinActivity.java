package edu.centenary.topspin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TopSpinActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Assign the onClickListeners for each button
        Button b = (Button) findViewById(R.id.nine);
        b.setOnClickListener(new GameClickListener(3));
        b = (Button) findViewById(R.id.sixteen);
        b.setOnClickListener(new GameClickListener(4));
        b = (Button) findViewById(R.id.twentyfive);
        b.setOnClickListener(new GameClickListener(5));
    }
    
    /**
     * Class to save the size and abstract the GameActivity creation
     */
    private class GameClickListener implements OnClickListener {

    	/** The size of the board */
    	private int size;
    	
    	/**
    	 * Constructs the Listener
    	 * @param size 
    	 */
    	public GameClickListener(int size) {
    		this.size = size;
    	}
    	
    	/**
    	 * Builds a Bundle with the size inside, attaches it to an activity
    	 * and starts it up.
    	 */
		@Override
		public void onClick(View arg0) {
    		Intent i = new Intent(TopSpinActivity.this, GameActivity.class);
    		Bundle bundle = new Bundle();
    		bundle.putInt("size", size);
    		i.putExtras(bundle);
    		startActivity(i);			
		}
    }
}