package com.ys.peeraxproject.gcm;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

public class GCMHelper extends GoogleCloudMessaging {
	
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private Context cont;
	public static final String SENDER_ID = "1029755535216";
	public String regid;

    private static final String PROPERTY_APP_VERSION = "appVersion";
	public GoogleCloudMessaging gcm;
	 AtomicInteger msgId = new AtomicInteger();
	    SharedPreferences prefs;
	    Context context;

	    public static final String PROPERTY_REG_ID = "registration_id";
	
	public GCMHelper(Context context){
		cont = context;
		
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
		public String registerInBackground() {
		    new AsyncTask<Object, Object, Object>() {
		       


				@Override
				protected Object doInBackground(Object... params) {
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
		               // sendRegistrationIdToBackend();
		                Log.d("something",regid);
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
		    }.execute(null, null, null);
		    return regid;
		}

}
