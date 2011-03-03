package my.topspin;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity {
	
	private int initPerms;
	private Dialog dialog;
	private Button okay;
	private SeekBar seeker;
	private Typeface actionis;
	private TextView newGame;
	private TextView loadGame;
	private TextView options;
	private TextView about;
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        actionis = Typeface.createFromAsset(getAssets(), "fonts/actionis.ttf");
        
        newGame = (TextView) findViewById(R.id.new_game);
        loadGame = (TextView) findViewById(R.id.load_game);
        options = (TextView) findViewById(R.id.options);
        about = (TextView) findViewById(R.id.about);
        
        newGame.setTypeface(actionis);
        loadGame.setTypeface(actionis);
        options.setTypeface(actionis);
        about.setTypeface(actionis);
        
        dialog = new Dialog(this);

        newGame.setOnTouchListener(new OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		showDialog(0);
        		return true;
        	}
        });
        
        loadGame.setOnTouchListener(new OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		Intent i = new Intent(MainActivity.this, GoActivity.class);
        		startActivity(i); 
        		return true;
        	}
        });
        
 /*       options.setOnTouchListener(new OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
       /*       Intent i = new Intent(MainActivity.this, OptionsActivity.class);
       	*		startActivity(i); 
        		return true;
        	}
        }); */
        
        about.setOnTouchListener(new OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		Intent i = new Intent(MainActivity.this, AboutActivity.class);
        		startActivity(i);
        		return true;
        	}
        });
    }
    
    public void onDestroy() {
    	super.onDestroy();
    	newGame = null;
    	loadGame = null;
    	options = null;
    	about = null;
    	dialog = null;
    	okay = null;
    	seeker = null;
    	actionis = null;
    }

    protected Dialog onCreateDialog(int id) {
    	dialog.setTitle("Difficulty");
    	dialog.setContentView(R.layout.custom_dialog);
    	
    	okay = (Button) dialog.findViewById(R.id.okay);
    	seeker = (SeekBar) dialog.findViewById(R.id.difficulty);
    	
    	seeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				initPerms = progress;
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
        okay.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {        		
        		dialog.dismiss();
        		Intent i = new Intent(MainActivity.this, GoActivity.class);
        		i.putExtra("initPerms", initPerms);
        		startActivity(i);
        	}
        });
        
    	return dialog;
    }
}