package com.ys.peeraxproject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class SignUpActivity extends Activity {
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	DatabaseHandler db;
	private static String TAG = "SignUpActivity";
    private EditText idInput;
    private EditText pwInput;
	
    private EditText pnInput;
	private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String register_tag = "register";
    
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

				new CreateNewUser().execute();
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
        	Log.d("some error", "place 1");
            String name = idInput.getText().toString();
            String phonenumber = pnInput.getText().toString();
            String password = pwInput.getText().toString();
            //Log.d("some error", email);
            Log.d("some error", phonenumber);
            
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
            params.add(new BasicNameValuePair("password", password));
            Log.d("signup", name);
            Log.d("signup", phonenumber);
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(registerURL,
                    "POST", params);
            
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	db.addUser(json.getInt("uid"), json.getString("unique_id"), json.getString("name"), json.getInt("phonenumber"), json.getString("about"), json.getString("degree"));
                    if(json.getInt("seen")== 0)
                    {
                	Intent i = new Intent(getApplicationContext(), FirstChoiceActivity.class);
                    startActivity(i);
 
                    finish();
                    }else{
                    	Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                        startActivity(i);
     
                        finish();
                        	
                    	
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
