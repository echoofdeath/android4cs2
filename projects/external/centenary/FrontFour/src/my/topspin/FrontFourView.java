package my.topspin;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FrontFourView extends SurfaceView implements GestureDetector.OnGestureListener, SurfaceHolder.Callback {

	private GestureDetector detector;
	private Game game;
	private CanvasThread cthread;
	private Paint paint;
	private Typeface actionis;
	
	public FrontFourView(Context context, int initPerms) {
		super(context);
		detector = new GestureDetector(this);
		game = new Game(initPerms);
		paint = new Paint();
		actionis = Typeface.createFromAsset(context.getAssets(), "fonts/actionis.ttf");
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint.setTextSize(48f);
		paint.setTypeface(actionis);
		
		getHolder().addCallback(this);
		cthread = new CanvasThread(getHolder(), this);
		setFocusable(true);
	}
	
	public FrontFourView(Context context) {
		super(context);
		detector = new GestureDetector(this);
		paint = new Paint();
		actionis = Typeface.createFromAsset(context.getAssets(), "fonts/actionis.ttf");
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint.setTextSize(48f);
		paint.setTypeface(actionis);
		
		getHolder().addCallback(this);
		cthread = new CanvasThread(getHolder(), this);
		setFocusable(true);
		game = new Game();
		game.resume(context);
	}
	
	
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		if (velocityX > 0.0) {
			Log.d("Fling:", "Going right.");
		} else if (velocityX < 0.0) {
			Log.d("Fling:", "Going left.");
		}
		
		return true;
	}

	public boolean onDown(MotionEvent e) {
		// Stop all animation and follow finger movements
		Log.d("Down: ", "Finger down.");
		return true;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (e2.getAction() == MotionEvent.ACTION_MOVE) {
			float offset = e2.getX() - e2.getHistoricalX(e2.getPointerCount()-1);
			if (offset > 0.0) {
				game.shiftLeft(1);
			} else if (offset < 0.0) {
				game.shiftRight(1);
			}
			Log.d("Scroll: ", "Moved.");
		}
		return true;
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onSingleTapUp(MotionEvent e) {
		game.spin();
		return true;
	}
	
	public void onDraw(Canvas canvas) {
		canvas.drawRGB(153, 204, 51);
		int index = game.getIndex() % 20;
		
		for (int i = 0; i < 20; i++) {
			
			Node n = game.get(i);
			
			if (i == index || (i-1+20) % 20 == index || (i-2+20) % 20 == index || (i-3+20) % 20 == index) {
				paint.setTextSize(68f);
				// Need to make some adjustments to the position of the orbs
/*				canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.orb),
						(float)(300*Math.cos(4*Math.PI/3 - n.getT())+getWidth()/2),
						(float)(4*Math.PI/3 - 150*Math.sin(4*Math.PI/3 - n.getT())+getHeight()/2),
								null); */
			} else {
				paint.setTextSize(48f);
			}
			
			canvas.drawText("" + n.getValue(), (float)(300*Math.cos(4*Math.PI/3 - n.getT())+getWidth()/2), (float)(4*Math.PI/3 - 150*Math.sin(4*Math.PI/3 - n.getT())+getHeight()/2), paint);
			
			
		}
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {}


	public void surfaceCreated(SurfaceHolder holder) {
		cthread.setRunning(true);
		cthread.start();
	}


	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		cthread.setRunning(false);
		while (retry) {
			try {
				cthread.join();
				retry = false;
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	public void pause(Context context) {
		game.save(context);
	}
	
	public void resume(Context context) {
		game.resume(context);
	}

}
