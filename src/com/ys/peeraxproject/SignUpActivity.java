package com.ys.peeraxproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ys.peeraxproject.gcm.GCMHelper;
//import com.ys.peeraxproject.gcm.GCMHelper.CreateNewUser;
import com.ys.peeraxproject.helper.DatabaseHandler;
import com.ys.peeraxproject.helper.JSONParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	private DatabaseHandler db;
	private final String LOG_TAG = "SignUpActivity";
    private EditText idInput;
    private EditText pwInput;
	private String regId;
	private GoogleCloudMessaging gcm;
    private EditText pnInput;
    private GCMHelper gcmHelper;
    private String regid;
    
    
    
    private static String registerURL = "http://104.131.141.54/lny_project/create_user.php";
	Button registerButton;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signupscreen);
        idInput = (EditText) findViewById(R.id.SignupId);
       
        db= new DatabaseHandler(getApplicationContext());
        pwInput  = (EditText) findViewById(R.id.SignupPW);
        pnInput=(EditText) findViewById(R.id.signupphonenumber);
        registerButton = (Button) findViewById(R.id.RegisterButton);
 
        registerButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

		        if (idInput.getText().toString().length()==0 || pnInput.getText().toString().length()==0 || pwInput.getText().toString().length()==0){
		        	Toast.makeText(getApplicationContext(), "id or password is not inputted", Toast.LENGTH_SHORT).show();
		            
		        }else{
		        new registerinbg().execute();
				
				
		        //new CreateNewUser().execute();
				/*
				Intent i = new Intent(SignUpActivity.this, LoginScreenActivity.class);
				startActivity(i);
				finish();
		        */
		        }
			}
        	
        	
        });
        
        
    }
	public static boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
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
        	String name = idInput.getText().toString();
            String phonenumber = pnInput.getText().toString();
            String password = pwInput.getText().toString();
            
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("regid", regid));
            
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(registerURL,
                    "POST", params);
            
            // check log cat for response
//            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	db.addUser(json.getInt("phonenumber") , json.getString("name"), json.getString("about"), json.getString("degree"), json.getString("regid"));
                    
                	Intent i = new Intent(getApplicationContext(), FirstChoiceActivity.class);
                	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                	
                	startActivity(i);
 
                    finish();
                    
                    
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
                    gcm = GoogleCloudMessaging.getInstance(SignUpActivity.this);
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
	

	
}