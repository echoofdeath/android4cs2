package com.googlecode.android4cs2.beetlegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BeetleSurface extends SurfaceView implements SurfaceHolder.Callback {

	private Beetle b;
	private BeetleThread thread;
	
	public BeetleSurface(Context context, Beetle b) {
		super(context);
		init(b);
	}

	public BeetleSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BeetleSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void init(Beetle b) {
		this.b = b;
		getHolder().addCallback(this);
		thread = new BeetleThread(getHolder(), this);
		setFocusable(true);
	}

	public void onDraw(Canvas c) {
		int legs = b.getLegs();
		int[] legRes = { R.drawable.leg1, R.drawable.leg2, R.drawable.leg3, R.drawable.leg4, R.drawable.leg5, R.drawable.leg6 };
		
		int feelers = b.getFeelers();
		int[] feelRes = { R.drawable.ant1, R.drawable.ant2 };
		
		int eyes = b.getEyes();
		int[] eyeRes = { R.drawable.eye1, R.drawable.eye2 };
		
		if (legs > 0) {
			for (int i = 0; i < legs; i++) {
				Bitmap l = BitmapFactory.decodeResource(getResources(), legRes[i]);
				c.drawBitmap(l, 0, 0, null);
			}
		}
		
		if (feelers > 0) {
			for (int i = 0; i < feelers; i++) {
				Bitmap f = BitmapFactory.decodeResource(getResources(), feelRes[i]);
				c.drawBitmap(f, 0, 0, null);
			}
		}
		
		if (b.getHead()) {
			Bitmap head = BitmapFactory.decodeResource(getResources(), R.drawable.head);
			c.drawBitmap(head, 0, 0, null);
		}
		
		if (eyes > 0) {
			for (int i = 0; i < eyes; i++) { // lol
				Bitmap eye = BitmapFactory.decodeResource(getResources(), eyeRes[i]);
				c.drawBitmap(eye, 0, 0, null);
			}
		}
		
		if (b.getBody()) {
			Bitmap body = BitmapFactory.decodeResource(getResources(), R.drawable.body);
			c.drawBitmap(body, 0, 0, null);
		}
		
		/* Beetles don't have tails. ARGH!
		if (b.getTail()) {
			
		} */
	}
	
	public void onMeasure(int width, int height) {
		super.onMeasure(width / 3, height);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// Just keep trying
			}
		}
	}
	
	class BeetleThread extends Thread {
		private SurfaceHolder sh;
		private BeetleSurface bs;
		private boolean run = false;
		
		public BeetleThread(SurfaceHolder sh, BeetleSurface bs) {
			this.sh = sh;
			this.bs = bs;
		}
		
		public void setRunning(boolean run) {
			this.run = run;
		}
		
		public void run() {
			Canvas c;
			while (run) {
				c = null;
				try {
					c = sh.lockCanvas(null);
					synchronized (sh) {
						bs.onDraw(c);
						
					}
				} finally {
					if (c != null) {
						sh.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}
}
