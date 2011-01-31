package com.googlecode.android4cs2.walldrug;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WallDrug extends Activity implements SensorEventListener {
	
	private TextView tv;
	private ImageView iv;
	private SensorManager sensorManager;
    private LocationManager lm;
    private LocationListener locationListener;
    private GeoPoint wallGP;
	private GeoPoint myGP;
	private int minTime = 10000;
	private int minDistance = 5;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	// Set up the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		tv = (TextView) this.findViewById(R.id.distance);
		iv = (ImageView) this.findViewById(R.id.imagearrow);

		// Set Location of Wall Drug
		wallGP = new GeoPoint(43.993231, -102.241795);
		
		// Request updates from the LocationManager for GPS changes
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);    
        locationListener = new MyLocationListener();
        lm.requestLocationUpdates(
        		LocationManager.GPS_PROVIDER, 
        		minTime, 
        		minDistance, 
        		locationListener);
        Log.d("LocListener", "Created Listener");

        // Request updates from the SensorManager for compass changes
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		if (sensorManager == null) {
			Toast.makeText(getBaseContext(), "No Sensor Manager",
						   Toast.LENGTH_SHORT).show();
			return;
		}
		Sensor compass = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		if (compass == null) {
			Toast.makeText(getBaseContext(), "No Compass", 
					       Toast.LENGTH_SHORT).show();
			return;
		}
		sensorManager.registerListener(this, compass,
				SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onDestroy() {
		super.onDestroy();
		
		// Remove all Listeners
    	lm.removeUpdates(locationListener);
		if (sensorManager != null) {
			sensorManager.unregisterListener(this);
		}
    }
    
	@Override
	public void onSensorChanged(SensorEvent arg0) {
		if (myGP == null) {
			iv.setImageResource(R.drawable.wallq);
		} else {
			synchronized (this) {
				
				// Calculate bearing from here to Wall Drug, and calibrate
				double magneticHeading = arg0.values[0];
				double bearing = myGP.bearing(wallGP);
				double diff = bearing - magneticHeading;
				if (bearing < magneticHeading) {
					diff = 360 + diff;
				}
	
				// If within 1 mile, we're here!
				if (myGP.greatCircle(wallGP) < 1) {
					iv.setImageResource(R.drawable.wallarrowhere);
				} else {
					
					// Otherwise swap in the image for the appropriate bearing
					int tmp = (int) Math.round(diff / 45);
					switch (tmp) {
					case 1: {
						iv.setImageResource(R.drawable.wallarrowne);
						break;
					}
					case 2: {
						iv.setImageResource(R.drawable.wallarrowe);
						break;
					}
					case 3: {
						iv.setImageResource(R.drawable.wallarrowse);
						break;
					}
					case 4: {
						iv.setImageResource(R.drawable.wallarrows);
						break;
					}
					case 5: {
						iv.setImageResource(R.drawable.wallarrowsw);
						break;
					}
					case 6: {
						iv.setImageResource(R.drawable.wallarroww);
						break;
					}
					case 7: {
						iv.setImageResource(R.drawable.wallarrownw);
						break;
					}
					default: {
						iv.setImageResource(R.drawable.wallarrown);
						break;
					}
					}
				}
			}
		}		
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) { }

	/**
	 * Standard class to listen for changes in GPS Location.
	 */
    private class MyLocationListener implements LocationListener {
    	
        @Override
        public void onLocationChanged(Location loc) {
            myGP = new GeoPoint(loc.getLatitude(), loc.getLongitude());
            tv.setText("" + (int)myGP.greatCircle(wallGP) + " Miles");
            Log.d("LocListener", "Found the loc!");
        }

        @Override
        public void onProviderDisabled(String provider) {
        	myGP = null;
        }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onStatusChanged(String provider, int status, 
            Bundle extras) { }
    }
}