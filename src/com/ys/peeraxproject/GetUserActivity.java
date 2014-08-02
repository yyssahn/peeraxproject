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

import com.ys.peeraxproject.ViewSubjectsActivity.GetInfo;
import com.ys.peeraxproject.helper.ActionBarHelper;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GetUserActivity extends Activity {
	LayoutInflater inflater;
	Dialog dil;
	String user_phone;
	String degree_text;
	String about_text;
	String name_text;
	String  phonenumber;
	JSONParser jsonParser = new JSONParser();
	DatabaseHandler db;
	Bitmap userimage;
	TextView name;
	Bitmap profile_picture;
	TextView about;
	TextView degree;
	private static String info_tag = "info";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ABOUT = "about";
	private static final String TAG_SUBJECT = "subject";
	private static final String TAG_DEGREE = "degree";
	private static String loginURL = "http://104.131.141.54/lny_project/change_info.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		user_phone = i.getExtras().getString("userphone");
		Toast.makeText(GetUserActivity.this, user_phone, Toast.LENGTH_LONG).show();
        setContentView(R.layout.getuserprofilescreen);
		db = new DatabaseHandler(GetUserActivity.this);
		name = (TextView) findViewById(R.id.getusernametextview);
		name.setText(user_phone);
		about = (TextView) findViewById(R.id.getuserabouttextview);
		degree =(TextView) findViewById(R.id.getusereducationtextview);
		new GetInfo().execute();
		
       
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		final Context cont = GetUserActivity.this;
		// Set up your ActionBar

        inflater = getLayoutInflater();

		final ActionBar actionBar = getActionBar();
		
		dil = new Dialog(cont);
		dil.setTitle(null);
        dil.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		dil.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dil.setContentView(R.layout.optionbar);

		
        new GetOptionInfo().execute();

        Button homeButton = (Button)dil.findViewById(R.id.optionhomebtn);
		homeButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
			
		});
		Button profileButton = (Button)dil.findViewById(R.id.optionprofilebtn);
		profileButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(cont, ProfilePageActivity.class);
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
				Intent i = new Intent(cont, SettingStatusActivity.class);
				startActivity(i);
				// TODO Auto-generated method stub
			}
			
		});


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
        
		final ActionBarHelper ahelper = new ActionBarHelper(cont, inflater, actionBar, db.getPhoneNumber(), dil);
    
        ahelper.setBackGround(R.drawable.getuseroptionbg);
        ahelper.setButton1BackGround(R.drawable.getuserpill1);
        ahelper.setButton1Text("back");
        ahelper.setButton2BackGround(R.drawable.getuserpill2);
       ahelper.setTitle("Tutor Profile");
 
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

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("phonenumber", user_phone));
            params.add(new BasicNameValuePair("tag",info_tag));

            try {
                profile_picture = BitmapFactory.decodeStream((InputStream) new URL("http://104.131.141.54/lny_project/" + user_phone + ".jpg").getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

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
                	degree_text = json.getString(TAG_DEGREE);
                	
                	if(about_text == "null")
                		about_text = "";
//                	if(subject_text == "null")
//                		subject_text = "";
               	if(degree_text  == "null")
                		
                		
                		degree_text = "";
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
            if(profile_picture != null){
                ImageView picture = (ImageView) findViewById(R.id.userpicture);
                picture.setImageBitmap(profile_picture);
            }

        	about.setText(about_text);
    		
 //   		TextView subject = (TextView) findViewById(R.id.profilesubject);
 //   		subject.setText(subject_text);
    		
//    		TextView degree = (TextView) findViewById(R.id.profileeducation);
    		String degreet = null;

    		switch(degree_text){
    		case "0":
    			degreet ="Secondary School";		
    		    			
    			break;
case "1":
	degreet ="Post Secondary - 1st Year";		
    		
	break;
    		
case "2":
	degreet = "Post Secondary - 2nd Year";		
    
	break;

case "3":

	degreet = "Post Secondary - 3rd Year";
	break;

case "4":

	degreet = "Post Secondary - 4th Year";
	
	break;

case "5":

	degreet = "Bachelor Degree";
	break;

case "6":

	degreet = "Master student/ Master Degree";	
	break;
case "7":

	degreet = "PHD student / PHD Degree";
	break;

	
    		}
    		degree.setText(degreet);
  
        }
	}
	
	class GetOptionInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            
            try {
                profile_picture = BitmapFactory.decodeStream((InputStream) new URL("http://104.131.141.54/lny_project/" + db.getPhoneNumber() + ".jpg").getContent());
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
            about.setText(db.getPhoneNumber());
        }
    }
}
