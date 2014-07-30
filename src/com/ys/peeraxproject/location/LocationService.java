package com.ys.peeraxproject.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service{
	private LocationManager locationManager;
	private MyLocationListener locationListener;
	private Location location;
	private String locationProvider;
	
	@Override
	public void onStart(Intent intent, int startId){
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    locationProvider = LocationManager.NETWORK_PROVIDER;
	    locationListener = new MyLocationListener();
	    
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}
	
	public void getLastLocation(){
		location = locationManager.getLastKnownLocation(locationProvider);
	}
	
	public void stopLocation(){
		locationManager.removeUpdates(locationListener);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//=============================================================================================
	private class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			Log.d("GPS", "location changed: lat="+location.getLatitude()+", lon="+location.getLongitude());
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