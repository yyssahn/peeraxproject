package com.ys.peeraxproject.location;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.ys.peeraxproject.helper.DatabaseHandler;
import com.ys.peeraxproject.helper.JSONParser;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class LocationService extends Service{
	private LocationManager locationManager;
	private MyLocationListener locationListener;
	private Location location;
	private String locationProvider;
	private DatabaseHandler db;
	private JSONParser jsonParser;
	private final String LOG_TAG = "LocationService";
	
	private static final int TIME = 900000; //15min
	private static final int DISTANCE = 25;	//25m
	private static final String LOCATION_TAG = "location";
	private static final String loginURL = "http://104.131.141.54/lny_project/update_location.php";
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onStart(Intent intent, int startId){
		Log.d(LOG_TAG, "onStart()");
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    locationProvider = LocationManager.NETWORK_PROVIDER;
	    locationListener = new MyLocationListener();
	    
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TIME, DISTANCE, locationListener);
	 
	    db = new DatabaseHandler(getApplicationContext());
	    jsonParser = new JSONParser();
	}
	
	@Override
	public void onDestroy(){
		Log.d(LOG_TAG, "onDestroy()");
		locationManager.removeUpdates(locationListener);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void getLastLocation(){
		Log.d(LOG_TAG, "getLastLocation()");
		location = locationManager.getLastKnownLocation(locationProvider);
	}
	//=============================================================================================
	class UpdateLocation extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         }
 
        @Override
		protected String doInBackground(String... args) {
        	getLastLocation();
        	String phonenumber= db.getPhoneNumber();
        	String latitude = Double.toString(location.getLatitude());
        	String longitude = Double.toString(location.getLongitude());
        	Log.d(LOG_TAG, "Phone number: " + phonenumber + " Latitude: " + latitude + "Longitude: " + longitude);
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("latitude", latitude));
            params.add(new BasicNameValuePair("longitude", longitude));
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
            params.add(new BasicNameValuePair("tag",LOCATION_TAG));
           
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(loginURL, "POST", params);
            // check log cat for response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	Log.d(LOG_TAG, "location updated");
                } else {
                    Log.d(LOG_TAG, "location failed to update");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        @Override
		protected void onPostExecute(String file_url) {

        }
	}
	//=============================================================================================
	private class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			Log.d("GPS", "location changed: lat="+location.getLatitude()+", lon="+location.getLongitude());
			new UpdateLocation().execute();
		}

		@Override
		public void onProviderDisabled(String provider) {
			Log.d("GPS", "GPS diabled");
		}

		@Override
		public void onProviderEnabled(String provider) {
			Log.d("GPS", "GPS enabled");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
	}
	//=============================================================================================
}