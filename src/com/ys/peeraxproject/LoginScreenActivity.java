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
	DatabaseHandler db;
	private EditText idInput;
		private EditText passwordInput;
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";
	    
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

					new LoginUser().execute();
//				new LoginUser().execute();   
				//Intent i = new Intent(LoginScreenActivity.this, FirstChoiceActivity.class);
				//startActivity(i);
				//finish();
		        }
			}
        	
        });
        
	}
	class LoginUser extends AsyncTask<String, String, String> {
		 
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
        	String phonenumber = idInput.getText().toString();
            String password = passwordInput.getText().toString();
            Log.d("LoginScreenActivity", "Phonenumber: " + phonenumber);
            Log.d("LoginScreenActivity", "Password:" + password);
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
            params.add(new BasicNameValuePair("password", password));
           
            // getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(loginURL,
                    "POST", params);
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                //	db.addUser(json.getInt("phonenumber"), json.getString("name"), json.getString("about"), json.getString("degree") );                    
                	Log.d("login", "User added to local db");
                	if (json.getInt("seen") == 0){
                	
                	Intent i = new Intent(getApplicationContext(), FirstChoiceActivity.class);
                	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                	startActivity(i);
                    
                    finish();
                	}else{

                    	Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                    	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    	startActivity(i);
                        finish();	
                		
                	}
                	
                	
                } else {
                	String message = json.getString(TAG_MESSAGE);
                	Log.d("login", message);
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
