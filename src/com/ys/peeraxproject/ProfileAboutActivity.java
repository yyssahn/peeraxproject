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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ProfileAboutActivity extends Activity {
	EditText input;
	Button confirmBtn;
	DatabaseHandler db;
	JSONParser jsonParser = new JSONParser();
	String id;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "unique_id";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_about = "about";
    
    private static final String KEY_ABOUT = "about";;
    private static final String KEY_DEGREE = "degree";;
    private static String about_tag = "about";
    
    private static String loginURL = "http://104.131.141.54/lny_project/change_info.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profileaboutscreen);
		//id = (String)savedInstanceState.get(KEY_ID);
		//Log.d("something", id);
		db = new DatabaseHandler(getApplicationContext());
		input = (EditText) findViewById(R.id.aboutinput);
		confirmBtn = (Button) findViewById(R.id.aboutconfirmbtn);
		
		confirmBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new UpdateUser().execute();
			}
			
		});
		
	}
	
	class UpdateUser extends AsyncTask<String, String, String> {
		 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         }
 
          @Override
		protected String doInBackground(String... args) {
        	String about= input.getText().toString();
        	String email= db.getPhoneNumber();
        	
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("about", about));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("tag",about_tag));
           
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(loginURL, "POST", params);
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                //	db.updateAbout(email, about);
                	Intent i = new Intent(getApplicationContext(), ProfilePageActivity.class);
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
 
        @Override
		protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
        //    pDialog.dismiss();
        }
        

	}

}
