package com.googlecode.android4cs2.dieroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class DieRoller extends Activity implements OnClickListener{
	
	private Die d;
	private Button rollButton;
	private ImageView imagedie;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        imagedie = (ImageView) findViewById(R.id.imagedie);
        Object o = getLastNonConfigurationInstance();
        if (o != null && o instanceof Die) {
        	d = (Die)o;
        } else {
        	d = new Die();
        }
		setDieImage(d.getTopFace());
      
        rollButton = (Button) findViewById(R.id.roll);
        rollButton.setOnClickListener(this);
        
    }

    @Override  
    public Object onRetainNonConfigurationInstance() 
    {   
        return(d);   
    }
    
    public void setDieImage(int top) {
		switch (top) {
		case 1: imagedie.setImageResource(R.drawable.die1);
		break;
		case 2: imagedie.setImageResource(R.drawable.die2);
		break;
		case 3: imagedie.setImageResource(R.drawable.die3);
		break;
		case 4: imagedie.setImageResource(R.drawable.die4);
		break;
		case 5: imagedie.setImageResource(R.drawable.die5);
		break;
		case 6: imagedie.setImageResource(R.drawable.die6);
		break;
		default: imagedie.setImageResource(R.drawable.die1);
		break;
		}
    }
    
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		d.roll();
		setDieImage(d.getTopFace());
	}
}