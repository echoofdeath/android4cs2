package com.googlecode.android4cs2.ghost;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.TextView;

public class GhostActivity extends Activity {
	
	/** A pointer to the word we're making */
	private TextView word;
	
	/** A pointer to the instructions */
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().takeKeyEvents(true);
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.d("onKeyUp", "Key code: " + keyCode);
		return false;
	}
}