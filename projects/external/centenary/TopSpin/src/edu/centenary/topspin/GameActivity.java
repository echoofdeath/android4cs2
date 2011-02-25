package edu.centenary.topspin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GameActivity extends Activity {

	private TextView title;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = this.getIntent().getExtras();
		int size = bundle.getInt("size");
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
//		title = (TextView)findViewById(R.id.title);
//		title.setText(param1);
	}
}
