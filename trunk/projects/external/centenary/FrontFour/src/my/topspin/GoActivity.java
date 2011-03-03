package my.topspin;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.Window;
/* import android.view.Menu;
import android.view.MenuItem; */



@SuppressWarnings("unused")
public class GoActivity extends Activity {
	
	private FrontFourView view;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Intent i = getIntent();
		
		if (i.getIntExtra("initPerms", -1) == -1) {
			view = new FrontFourView(this);
			view.resume(this);
		} else {
			view = new FrontFourView(this, i.getIntExtra("initPerms", 1));
		}
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(view);
	}
	
	public void onRestart() {
		super.onRestart();
		view = new FrontFourView(this);
		view.resume(this);
		setContentView(view);
	}
	
	public void onPause() {
		super.onPause();
		view.pause(getApplicationContext());
	}
	
	public void onStop() {
		super.onStop();
		view.pause(getApplicationContext());
	}
	
	public void onDestroy () {
		super.onDestroy();
		view.pause(getApplicationContext());
		view = null;
	}
}