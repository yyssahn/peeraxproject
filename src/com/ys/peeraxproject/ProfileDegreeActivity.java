package com.ys.peeraxproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.ys.peeraxproject.ProfileAboutActivity.GetInfo;
import com.ys.peeraxproject.helper.DatabaseHandler;
import com.ys.peeraxproject.helper.JSONParser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileDegreeActivity extends Activity {
	Spinner input;
	Button confirmBtn;
	DatabaseHandler db;
	JSONParser jsonParser = new JSONParser();
	String id;
    static Bitmap profile_picture;
    LayoutInflater inflater;
    Dialog dil;
    int dint=0;
    String degreeint;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_DEGREE = "degree";

    static String user_name;
	private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "unique_id";
    private static String KEY_NAME = "name";
    private static String KEY_phonenumber = "phonenumber";
    private static String KEY_about = "about";
    
    private static final String KEY_ABOUT = "about";
    private static final String KEY_DEGREE = "degree";
    private static String about_tag = "about";
    
    private static String loginURL = "http://104.131.141.54/lny_project/change_info.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profiledegreescreen);
		//id = (String)savedInstanceState.get(KEY_ID);
		//Log.d("something", id);
		db = new DatabaseHandler(getApplicationContext());
		Intent i = getIntent();
		String about_text = i.getStringExtra(TAG_DEGREE);
		switch(about_text){
		case "0":
			dint=0;			
			break;
case "1":		
		dint=1;
break;
		
case "2":dint=2;
break;

case "3":
	dint=3;
	break;

case "4":

	dint=4;

break;

case "5":

	dint=5;
	
break;

case "6":

	dint=6;
	break;
case "7":

dint =7;
break;


		}

		input = (Spinner) findViewById(R.id.degreespinner);
		
		input.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		input.setSelection(dint);
		confirmBtn = (Button) findViewById(R.id.degreeconfirmbtn);
		
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
        	//String degree= input.getText().toString();
        	String phonenumber= db.getPhoneNumber();
        	
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
          
            params.add(new BasicNameValuePair("degree", degreeint));
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
            params.add(new BasicNameValuePair("tag", KEY_DEGREE));
           
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(loginURL, "POST", params);
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                //	db.updateAbout(phonenumber, about);
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
				R.layout.actionbar,
				null);
		// Set up your ActionBar

        inflater = getLayoutInflater();
		final LinearLayout mainpage = (LinearLayout)findViewById(R.id.tempmainscreen);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
        actionBar.setBackgroundDrawable(new ColorDrawable(00000000));
        actionBarLayout.setBackgroundResource(R.drawable.optionbg2);
		final Button actionBarBack = (Button) findViewById(R.id.optionbackbutton);
        actionBarBack.setBackgroundResource(R.drawable.pillplainalt);
        actionBarBack.setText("Back");

        final Context con = ProfileDegreeActivity.this;
		
		actionBarBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

	        	Toast.makeText(con, "Refresh", Toast.LENGTH_SHORT).show();
	           
			}
			
			
		});

		final Button actionBarInflate = (Button) findViewById(R.id.createoptionbutton);
        actionBarInflate.setBackgroundResource(R.drawable.pillalt);

		actionBarInflate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			dil = new Dialog(con);
			dil.setTitle(null);
            dil.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

			dil.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dil.setContentView(R.layout.optionbar);

            new GetInfo().execute();

            Button homeButton = (Button)dil.findViewById(R.id.optionhomebtn);
			homeButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i  = new Intent(con, HomePageActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
					finish();
				}
				
			});
			Button profileButton = (Button)dil.findViewById(R.id.optionprofilebtn);
			profileButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(con, ProfilePageActivity.class);
					startActivity(i);
					finish();
				}
				
			});
			Button convButton = (Button)dil.findViewById(R.id.optionconversationbtn);
			convButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
				
			});
			Button sesButton = (Button)dil.findViewById(R.id.optionsessionbtn);
			sesButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
				
			});

			Button settingButton = (Button)dil.findViewById(R.id.optionsetting);
			settingButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
				
			});

			dil.show();

            Window window = dil.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            DisplayMetrics dimension = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dimension);
            int height = dimension.heightPixels - actionBar.getHeight();
            int actionBarHeight = actionBar.getHeight() + 30;

            wlp.y = actionBarHeight;
            wlp.gravity=Gravity.TOP | Gravity.RIGHT;
            wlp.height = height - actionBar.getHeight();

            window.setAttributes(wlp);
            }


        });

		final TextView actionBarStaff = (TextView) findViewById(R.id.optiontitle);
		actionBarStaff.setText("Add or Edit");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.optionbackbutton:
            // search action
        	Log.d("back","back");
        	Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.createoptionbutton:
            // refresh

        	Log.d("back","create");
        	Toast.makeText(getApplicationContext(), "create", Toast.LENGTH_SHORT).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	class GetInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            user_name = db.getPhoneNumber();

            try {
                profile_picture = BitmapFactory.decodeStream((InputStream) new URL("http://104.131.141.54/lny_project/" + user_name + ".jpg").getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            if(profile_picture != null) {
                ImageView picture = (ImageView) dil.findViewById(R.id.optionpicture);
                picture.setImageBitmap(profile_picture);
            }

            TextView about = (TextView) dil.findViewById(R.id.optionid);
            about.setText(user_name);
        }
    }
	public class CustomOnItemSelectedListener implements OnItemSelectedListener {
		 
		  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
			degreeint= ""+pos;

		  }
		 
		  @Override
		  public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		  }
		 
		}

}
