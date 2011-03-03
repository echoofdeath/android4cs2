package my.topspin;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class AboutActivity extends Activity {
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
	}
}
