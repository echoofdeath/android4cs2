package edu.centenary.topspin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TopSpinActivity extends Activity {
	
	private Button three;
	private Button four;
	private Button five;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        three = (Button) findViewById(R.id.nine);
        three.setOnClickListener(new GameClickListener(3));
        four = (Button) findViewById(R.id.sixteen);
        four.setOnClickListener(new GameClickListener(4));
        five = (Button) findViewById(R.id.twentyfive);
        five.setOnClickListener(new GameClickListener(5));
   }
    
    private class GameClickListener implements OnClickListener {

    	private int size;
    	
    	public GameClickListener(int size) {
    		this.size = size;
    	}
    	
		@Override
		public void onClick(View arg0) {
    		Intent i = new Intent(TopSpinActivity.this, GameActivity.class);
    		Bundle bundle = new Bundle();
    		bundle.putInt("size", size);
    		i.putExtras(bundle);
    		TopSpinActivity.this.startActivity(i);			
		}
    }
}