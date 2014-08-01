package com.ys.peeraxproject.location;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.ys.peeraxproject.helper.DatabaseHandler;
import com.ys.peeraxproject.helper.JSONParser;

public class NearbyUsersService extends Service{
	private DatabaseHandler db;
	private JSONParser jsonParser;
	
	private static String latitude;
	private static String longitude;
	
	private final String LOG_TAG = "NearbyUsersService";
	
	private static final String NEARBY_TAG = "nearby";
	private static final String loginURL = "http://104.131.141.54/lny_project/get_nearby.php";
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onStart(Intent intent, int startId){
		Log.d(LOG_TAG, "onStart()");
	    
	    db = new DatabaseHandler(getApplicationContext());
	    jsonParser = new JSONParser();
	    new CoordinatesReceiver();
	    new GetNearbyUsers();
	}
	
	@Override
	public void onDestroy(){
		Log.d(LOG_TAG, "onDestroy()");
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	//=============================================================================================
	protected class GetNearbyUsers extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         }
 
        @Override
		protected String doInBackground(String... args) {
        	String phonenumber= db.getPhoneNumber();
        	Log.d(LOG_TAG, "Phone number: " + phonenumber + " latidute: " + latitude + " longitude " + longitude);
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
            params.add(new BasicNameValuePair("latitude", latitude));
            params.add(new BasicNameValuePair("longitude", longitude));
            params.add(new BasicNameValuePair("tag",NEARBY_TAG));
           
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(loginURL, "POST", params);
            // check log cat for response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	Log.d(LOG_TAG, json.toString());
                } else {
                    Log.d(LOG_TAG, "failed to retrieve users");
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
	public static class CoordinatesReceiver extends BroadcastReceiver {

	   @Override
	   public void onReceive(Context context, Intent intent) {
	      double[] coordinates = intent.getDoubleArrayExtra(NEARBY_TAG);
	      latitude = Double.toString(coordinates[0]);
	      longitude = Double.toString(coordinates[1]);
	   }
	}
	//=============================================================================================
}
