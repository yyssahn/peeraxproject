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

public class FirstChoiceActivity extends Activity {
	Button toCreateSessionBtn;
	Button toHomeBtn;
	DatabaseHandler db;
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";

    private static String loginURL = "http://104.131.141.54/lny_project/change_info.php";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		db = new DatabaseHandler(getApplicationContext());
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.firstchoicescreen);
 
        toCreateSessionBtn = (Button) findViewById(R.id.tutorbutton);
        toHomeBtn = (Button) findViewById(R.id.homebutton);
        
        toCreateSessionBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new UpdateUser().execute();
				Intent i = new Intent(FirstChoiceActivity.this, CriteriaSelectActivity.class);
				startActivity(i);
				finish();
			}
        	
        });
        
        toHomeBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				new UpdateUser().execute();
				Intent i = new Intent(FirstChoiceActivity.this, HomePageActivity.class);
				startActivity(i);
				finish();
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
        	
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", "seen"));
            params.add(new BasicNameValuePair("phonenumber",db.getPhoneNumber()));
            Log.d("firstchoice",db.getPhoneNumber());
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(loginURL, "POST", params);
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	
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
