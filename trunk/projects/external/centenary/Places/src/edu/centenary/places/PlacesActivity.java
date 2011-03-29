package edu.centenary.places;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesActivity extends Activity { //implements SensorEventListener{
	
	private float magcompass;
	private boolean gpsfound;
	private Sensor compass;
	private Location myloc;
	private TextView tv;
	private ListView pList;
	private SensorManager sensorManager;
	private ArrayList<QTLocation<String>> list;
	private SpatialDB<String> db;
	private MyLocationListener locationListener;
	private int minTime = 10000;
	private int minDistance = 5;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlist);
        
		db = loadPlaces();
        
	    LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locMan.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    		Builder adb = new AlertDialog.Builder(this);
    		adb.setIcon(R.drawable.icon);
        	adb.setTitle("GPS Required");
        	adb.setMessage("Places requires you to have" +
        			" your GPS activated. Click OK to go to " +
        			"the screen for activation, otherwise exit" +
        			" with Nevermind.");
        	adb.setPositiveButton("OK", new OnClickListener() { 

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					getGPS();
				}});
        	adb.setNegativeButton("Nevermind", new OnClickListener() { 

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
				}});
        	adb.show();
        }       
        
		locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);    
        locationListener = new MyLocationListener();
        locMan.requestLocationUpdates(
        		LocationManager.GPS_PROVIDER, 
        		minTime, 
        		minDistance, 
        		locationListener); 
        
		tv = (TextView) this.findViewById(R.id.headertext);
		pList = (ListView) this.findViewById(R.id.list_places);
		list = new ArrayList<QTLocation<String>>();
		list.add(new QTLocation<String>(new GeoPoint(0, 0), "Hello"));
		pList.setAdapter(new LocationAdapter(PlacesActivity.this, R.layout.row, list, magcompass));
		pList.setTextFilterEnabled(true);

//		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//
//		if (sensorManager == null) {
//			if (!gpsfound) {
//				Toast.makeText(getBaseContext(), "No Sensor Manager",
//						Toast.LENGTH_SHORT).show();
//			}
//			return;
//		}
//		compass = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//		if (compass == null) {
//			if (!gpsfound) {
//				Toast.makeText(getBaseContext(), "No Compass", Toast.LENGTH_SHORT)
//				.show();
//			}
//			return;
//		}
//		sensorManager.registerListener(this, compass,
//				SensorManager.SENSOR_DELAY_UI);
//		
    }

    /**
	 * 
	 */
	public void onDestroy() {
		super.onDestroy();
//		if (sensorManager != null) {
//			sensorManager.unregisterListener(this);
//		}
		Log.d("Places", "Destroying the Activity");
	}
    
	/**
	 * Start a new activity letting the user turn on the GPS
	 */
	public void getGPS() {
        Intent gpsOptionsIntent = new Intent(  
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
        startActivity(gpsOptionsIntent);  
	}

    public SpatialDB<String> loadPlaces() {
    	SpatialDB<String> places = new QuadTree<String>(new GeoPoint(-90, -180), new GeoPoint(90, 180));
    	try {
    		AssetManager am = getAssets();
    		Scanner input = new Scanner(am.open("centenary.txt"));
   			Log.d("loadNodes: ", "opened file");
    		while (input.hasNextLine()) {
    			double lat = input.nextDouble();
    			double lon = input.nextDouble();
    			String line = input.nextLine();
    			Log.d("loadNodes: ", "lat" + lat + "lon" + lon + line);
    			places.add(new GeoPoint(lat, lon), line);
    		}
    		input = null;
    		am.close();
    		am = null;
    	} catch (IOException e) {
    		Log.d("loadNodes: ", "Problem with words.txt!\n");
    	}
    	
    	return places;
    }

	
	/**
	 * Makes the list of sniffs look much better, icon to the left and two text
	 * fields, one large for message, one small and green for intensity
	 * 
	 */
	private class LocationAdapter extends ArrayAdapter<QTLocation<String>> implements Filterable{

		private ArrayList<QTLocation<String>> items;
		private float magcompass;

		public LocationAdapter(Context context, int textViewResourceId,
				ArrayList<QTLocation<String>> items, float magcompass) {
			super(context, textViewResourceId, items);
			setList(items);
			setMagCompass(magcompass);
		}

		synchronized public void setMagCompass(float magcompass) {
			this.magcompass = magcompass;
			Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
					.getDefaultDisplay();

			/* Now we can retrieve all display-related infos */
			int orientation = display.getOrientation();
			if (compass != null) {
				if (orientation == Surface.ROTATION_90) {
					this.magcompass = (this.magcompass + 90) % 360;
				}
			} else {
				this.magcompass = (this.magcompass + 90) % 360;
			}
		}

		synchronized public void setList(ArrayList<QTLocation<String>> items) {
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.d("Adapter", "Is this called?");
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			QTLocation<String> o = items.get(position);
			if (o != null) {
				// Also use sensor bearing to rotate appropriately???
				double bearing = o.getLoc().bearing(new GeoPoint(myloc.getLatitude(), myloc.getLongitude()));
				double diff = bearing - this.magcompass;
				if (bearing < this.magcompass) {
					diff = 360 + diff;
				}
				ImageView iv = (ImageView) v.findViewById(R.id.icon);

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
				

				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				String m = o.getItem();
				if (tt != null) {
					tt.setText(m);
				}
				if (bt != null) {
					bt.setText("Distance: "
							+ String.format("%.4f", o.getDistance()));
				}
			}
			return v;
		}
	}
	
	public void resetListAdapter() {
		synchronized (this) {
//			LocationAdapter da = (LocationAdapter) pList.getAdapter();
//			da.setMagCompass(magcompass);
//			da.notifyDataSetChanged();
//			da.notifyDataSetInvalidated();
//			pList.invalidateViews();
			
			pList.setAdapter(new LocationAdapter(PlacesActivity.this, R.layout.row, list, magcompass)); 
			pList.setTextFilterEnabled(true);
			 
		}
	}

//	@Override
//	public void onSensorChanged(SensorEvent se) {
//		synchronized (this) {
//			float magneticHeading = se.values[0];
//			magcompass = magneticHeading;
//			resetListAdapter();
//		}
//	}
//
//	@Override
//	public void onAccuracyChanged(Sensor arg0, int arg1) {
//		// TODO Auto-generated method stub
//	}
//	
    /**
	 * Standard class to listen for changes in GPS Location.
	 */
    private class MyLocationListener implements LocationListener 
    {
        @Override
        public void onLocationChanged(Location loc) {
        	if (loc != null) {
                myloc = loc;
                list = db.proximitySearch(new GeoPoint(myloc.getLatitude(), myloc.getLongitude()), 5);
    			LocationAdapter da = (LocationAdapter) pList.getAdapter();
    			da.setList(list);
    			resetListAdapter();
    			tv.setText("Local Places: " + list.size());
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        	myloc = null;
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider, int status, 
            Bundle extras) {
            // TODO Auto-generated method stub
        }
    }
}