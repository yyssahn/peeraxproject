package com.ys.peeraxproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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

public class LoginScreenActivity extends Activity {
	Button loginButton;
  
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	DatabaseHandler db;
	private EditText idInput;
		private EditText passwordInput;
		private static String KEY_SUCCESS = "success";
	    private static String KEY_ERROR = "error";
	    private static String KEY_ERROR_MSG = "error_msg";
	    private static String KEY_UID = "uid";
	    private static String KEY_NAME = "name";
	    private static String KEY_EMAIL = "email";
	    private static String register_tag = "register";
	    
	    private static String loginURL = "http://104.131.141.54/lny_project/login_user.php";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		db = new DatabaseHandler(getApplicationContext());
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loginscreen);
        idInput = (EditText)findViewById(R.id.LoginIdInput);
        passwordInput = (EditText)findViewById(R.id.LoginPWInput);
        loginButton = (Button) findViewById(R.id.LogonButton);
        
        loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (idInput.getText().toString().length()==0 || passwordInput.getText().toString().length()==0){
		        	Toast.makeText(getApplicationContext(), "id or password is not inputted", Toast.LENGTH_SHORT).show();
		            
		        }else{

				String email = idInput.getText().toString();
				
				String password = passwordInput.getText().toString();
//				new LoginUser().execute();   
		        }
			}
        	
        });
        
	}
/*
	class LoginUser extends AsyncTask<String, String, String> {
		 
        //
        // * Before starting background thread Show Progress Dialog
        //
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         }
 
        
        //
          
        @Override
		protected String doInBackground(String... args) {
        	Log.d("some error", "place 1");
            String email = idInput.getText().toString();
            String password = passwordInput.getText().toString();
            Log.d("some error", email);
            Log.d("some error", password);
            
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            Log.d("some error", params.toString());
            
            // getting JSON Object
            // Note that create product url accepts POST method
//            JSONObject json = jsonParser.makeHttpRequest(loginURL,
//                    "POST", params);
            
            // check log cat fro response
//            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
//                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
//                	db.addUser(json.getString("email"));
//                	Intent i = new Intent(getApplicationContext(), ChoiceActivity.class);
//                    startActivity(i);
 
                    //finish();
                	
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        
     //    * After completing background task Dismiss the progress dialog
         * 
        @Override
		protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
        //    pDialog.dismiss();
        }

	}
	*/
}
