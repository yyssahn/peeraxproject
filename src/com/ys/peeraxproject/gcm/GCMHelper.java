package com.ys.peeraxproject.gcm;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ys.peeraxproject.FirstChoiceActivity;
import com.ys.peeraxproject.HomePageActivity;
import com.ys.peeraxproject.SignUpActivity;
import com.ys.peeraxproject.helper.DatabaseHandler;
import com.ys.peeraxproject.helper.JSONParser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;


public class GCMHelper extends GoogleCloudMessaging {
	private SignUpActivity sa;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private Context cont;
	public static final String SENDER_ID = "1029755535216";
	public String regid;
	private registerinbg something;
	public String id;
	public String pw;
	public String name;
	private DatabaseHandler db;

	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	

	private final String LOG_TAG = "GCMHelper";

    private static String registerURL = "http://104.131.141.54/lny_project/create_user.php";
    private static final String PROPERTY_APP_VERSION = "appVersion";
	public GoogleCloudMessaging gcm;
	 AtomicInteger msgId = new AtomicInteger();
	    SharedPreferences prefs;
	    Context context;

	    public static final String PROPERTY_REG_ID = "registration_id";
	
	public GCMHelper(Context context, String uname, String phonenumber, String password){
		cont = context;
		id = phonenumber;
		name = uname;
		pw= password;
		db = new DatabaseHandler(context);
		
		
	}
	
	
	public String regid(){
		return regid;
	}
	public boolean checkPlayServices() {
		    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(cont);
		    if (resultCode != ConnectionResult.SUCCESS) {
		        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
		            GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) cont,
		                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
		        } else {
		            Log.i("something", "This device is not supported.");
		            //finish();
		        }
		        return false;
		    }
		    return true;
		}
		public void registerInBackground() {
		   
			new registerinbg().execute();
		    
		}
		public boolean ended(){
			
			if (something.getStatus() == AsyncTask.Status.FINISHED){
				return true;
			}else return false;
			
		}
		
		public String getRegID(){
			return regid;
		}

		class registerinbg extends AsyncTask<String, String, String> {

	        /**
	         * Creating product
	         * @return 
	         * */
	        @Override
			protected String doInBackground(String... args) {
	        	String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(cont);
	                }
	                regid = gcm.register(GCMHelper.SENDER_ID);
	                msg = "Device registered, registration ID=" + regid;

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.
	                // The request to your server should be authenticated if your app
	                // is using accounts.
	                new CreateNewUser().execute();
	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the regID - no need to register again.
	          //      storeRegistrationId(context, regid);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
	        }
	 
	    }
		
		
		class CreateNewUser extends AsyncTask<String, String, String> {
			 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	         }
	 
	        /**
	         * Creating product
	         * */
	        @Override
			protected String doInBackground(String... args) {
	        	
	            
	            
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("regid", regid));
	            params.add(new BasicNameValuePair("name", name));
	            params.add(new BasicNameValuePair("phonenumber", id));
	            params.add(new BasicNameValuePair("password", pw));
	            
	            // getting JSON Object
	            // Note that create product url accepts POST method
	            JSONObject json = jsonParser.makeHttpRequest(registerURL,
	                    "POST", params);
	            
	            // check log cat for response
	            Log.d("Create Response", json.toString());
	 
	            // check for success tag
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                	
	                	if(json.getInt("seen")== 0)
	                    {
	                    	
	                    }else{
	                    	
	                    	
	                    }
	                    
	                } else {
	                    // failed to create product
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	 
	            return null;
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        @Override
			protected void onPostExecute(String file_url) {
	            // dismiss the dialog once done
	        //    pDialog.dismiss();
	        }
	 
	    }
		
}
