package com.ys.peeraxproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.ys.peeraxproject.ProfileAboutActivity.UpdateUser;
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
import android.widget.TextView;

public class ProfilePageActivity extends Activity {
	JSONParser jsonParser = new JSONParser();
	Button pictureBtn;
	Button aboutBtn;
	Button subjectsBtn;
	Button educationBtn;
	TextView username;
	DatabaseHandler db;
	String KEY_ID= "id";
	static String about_text;
	static String subject_text;
	static String degree_text;

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ABOUT = "about";
	private static final String TAG_SUBJECT = "subject";
	private static final String TAG_DEGREE = "degree";
	private static String loginURL = "http://104.131.141.54/lny_project/change_info.php";
	private static String info_tag = "info";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilescreen);
		db = new DatabaseHandler(getApplicationContext());
		
		username = (TextView) findViewById(R.id.profilename);
		username.setText(db.getPhoneNumber());
		
		new GetInfo().execute();
		
		aboutBtn = (Button)findViewById(R.id.profileaboutbtn);
		aboutBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), ProfileAboutActivity.class);
				
				startActivity(i);
			}
		});
		
		subjectsBtn = (Button) findViewById(R.id.profilesubjectbtn);
		subjectsBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ViewSubjectsActivity.class);
				startActivity(i);
			}
			
			
		});
		
		educationBtn = (Button) findViewById(R.id.profileeducationbtn);
		educationBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), ProfileDegreeActivity.class);
				startActivity(i);
				
			}
		}
		);
	}
	
	class GetInfo extends AsyncTask<String, String, String> {
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         }
 
          @Override
		protected String doInBackground(String... args) {
        	String phonenumber= db.getPhoneNumber();
        	Log.d("debug", phonenumber);
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
            params.add(new BasicNameValuePair("tag",info_tag));
           
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(loginURL, "POST", params);
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	about_text = json.getString(TAG_ABOUT);
                	subject_text = json.getString(TAG_SUBJECT);
                	degree_text = json.getString(TAG_DEGREE);
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
        	TextView about = (TextView) findViewById(R.id.profileabouttext);
    		about.setText(about_text);
    		
    		TextView subject = (TextView) findViewById(R.id.profilesubject);
    		subject.setText(subject_text);
    		
    		TextView degree = (TextView) findViewById(R.id.profileeducation);
    		degree.setText(degree_text);
        }
	}
}
