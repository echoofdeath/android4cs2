package edu.centenary.drawtouch;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.Toast;

/**
 * Much of this code is thanks to the tutorial at http://www.tutorialforandroid.com/2009/06/drawing-with-canvas-in-android.html
 *
 */
public class DrawTouchActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new Panel(this));
	}

	class TutorialThread extends Thread {
		private SurfaceHolder _surfaceHolder;
		private Panel _panel;
		private boolean _run = false;

		public TutorialThread(SurfaceHolder surfaceHolder, Panel panel) {
			_surfaceHolder = surfaceHolder;
			_panel = panel;
		}

		public void setRunning(boolean run) {
			_run = run;
		}

		@Override
		public void run() {
			Canvas c;
			while (_run) {
				c = null;
				try {
					c = _surfaceHolder.lockCanvas(null);
					synchronized (_surfaceHolder) {
						_panel.onDraw(c);
					}
				} finally {
					// do this in a finally so that if an exception is thrown
					// during the above, we don't leave the Surface in an
					// inconsistent state
					if (c != null) {
						_surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}

		public SurfaceHolder getSurfaceHolder() {
			// TODO Auto-generated method stub
			return _surfaceHolder;
		}
	}

	class Panel extends SurfaceView implements SurfaceHolder.Callback {
		private TutorialThread _thread;
		private ArrayList<MyCircle> _graphics = new ArrayList<MyCircle>();
		private Paint mPaintf;
		private Paint mPaintb;

		public Panel(Context context) {
			super(context);
			getHolder().addCallback(this);
			_thread = new TutorialThread(getHolder(), this);
			setFocusable(true);
			mPaintf = new Paint();
			mPaintf.setDither(true);
			mPaintf.setColor(0x77FFFFFF);
			mPaintf.setStyle(Paint.Style.FILL);
			mPaintf.setStrokeJoin(Paint.Join.ROUND);
			mPaintf.setStrokeCap(Paint.Cap.ROUND);
			mPaintf.setStrokeWidth(3);

			mPaintb = new Paint();
			mPaintb.setDither(true);
			mPaintb.setColor(0xFFFF0044);
			mPaintb.setStyle(Paint.Style.STROKE);
			mPaintb.setStrokeJoin(Paint.Join.ROUND);
			mPaintb.setStrokeCap(Paint.Cap.ROUND);
			mPaintb.setStrokeWidth(3);

		}

		@Override
		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.BLACK);
			for (MyCircle p : _graphics) {
				canvas.drawOval(p.getBoundingBox(), mPaintf);
				canvas.drawOval(p.getBoundingBox(), mPaintb);
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			synchronized (_thread.getSurfaceHolder()) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					_graphics.add(new MyCircle(event.getX(), event.getY()));
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					MyCircle c = _graphics.get(_graphics.size() - 1);
					c.setRadius(event.getX(), event.getY());
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					MyCircle c = _graphics.get(_graphics.size() - 1);
					Toast.makeText(DrawTouchActivity.this, 
							"Area = " + c.getArea(), 
							Toast.LENGTH_SHORT).show();
					Toast.makeText(DrawTouchActivity.this, 
							"Perimeter = " + c.getPerimeter(), 
							Toast.LENGTH_SHORT).show();
				}
				return true;
			}

		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub

		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			_thread.setRunning(true);
			_thread.start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// simply copied from sample application LunarLander:
			// we have to tell thread to shut down & wait for it to finish, or
			// else
			// it might touch the Surface after we return and explode
			boolean retry = true;
			_thread.setRunning(false);
			while (retry) {
				try {
					_thread.join();
					retry = false;
				} catch (InterruptedException e) {
					// we will try it again and again...
				}
			}
		}
	}
}