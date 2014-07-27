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

public class ViewSubjectsActivity extends Activity {
	DatabaseHandler db;
	JSONParser jsonParser = new JSONParser();
	Button addBtn;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "unique_id";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static final String KEY_ID = "uid";
    private static final String KEY_ABOUT = "about";;
    private static final String KEY_DEGREE = "degree";;
    private static String register_tag = "register";

	private static final String TAG_CRITERIA = "criteria";
	private static final String TAG_SUBJECT = "subject";
	private String criteria;
	private String subject;
    
    private static String getsubjectURL = "http://104.131.141.54/lny_project/get_subjects.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subjectviewscreen);
		db = new DatabaseHandler(getApplicationContext());
		addBtn = (Button) findViewById(R.id.subjectaddbtn);
		new GetSubject().execute();
		addBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), CriteriaSelectActivity.class);
				startActivity(i);
			}
			
		});
	}
	class GetSubject extends AsyncTask<String, String, String> {
		 
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
//        	 Log.d("some error", email);
//            Log.d("some error", password);
            
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String phonenumber = db.getPhoneNumber();
            params.add(new BasicNameValuePair("tag", "about"));
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
           // getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(getsubjectURL,
                    "POST", params);
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {

                    //finish();
                	
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
