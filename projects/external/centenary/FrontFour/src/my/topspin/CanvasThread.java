package my.topspin;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class CanvasThread extends Thread {

	private SurfaceHolder _surfaceHolder;
	private FrontFourView _ffv;
	private boolean _run = false;
	
	public CanvasThread(SurfaceHolder surfaceHolder, FrontFourView ffv) {
		_surfaceHolder = surfaceHolder;
		_ffv = ffv;
	}
	
	public void setRunning(boolean run) {
		_run = run;
	}
	
	public void run() {
		Canvas c;
		while (_run) {
			c = null;
			try {
				c = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {
					_ffv.onDraw(c);
				}
			} finally {
				if (c != null) {
					_surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}
