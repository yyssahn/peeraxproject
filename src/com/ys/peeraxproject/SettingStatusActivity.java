package com.ys.peeraxproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.ys.peeraxproject.ProfileAboutActivity.GetInfo;
import com.ys.peeraxproject.helper.ActionBarHelper;
import com.ys.peeraxproject.helper.DatabaseHandler;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingStatusActivity extends Activity {
	Dialog dil;
	LayoutInflater inflater;
	DatabaseHandler db;
	Bitmap profile_picture;
	String user_name;
	Context cont;
	Button toOptionButton;
	Spinner statusSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.settingstatusscreen);
        db = new DatabaseHandler(SettingStatusActivity.this);
        toOptionButton = (Button)findViewById(R.id.settingoptionbtn);
        toOptionButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SettingStatusActivity.this,SettingOptionActivity.class);
				startActivity(i);
				
				
			}
        	
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		final Context cont = SettingStatusActivity.this;
		// Set up your ActionBar

        inflater = getLayoutInflater();

		final ActionBar actionBar = getActionBar();
		
		dil = new Dialog(cont);
		dil.setTitle(null);
        dil.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		dil.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dil.setContentView(R.layout.optionbar);

		
        new GetInfo().execute();

        Button homeButton = (Button)dil.findViewById(R.id.optionhomebtn);
		homeButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
                finish();
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
				dil.dismiss();
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
				Intent i = new Intent(cont, ChatActivity.class);
				dil.dismiss();
				startActivity(i);
			}
			
		});

		Button settingButton = (Button)dil.findViewById(R.id.optionsetting);
		settingButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				dil.dismiss();
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
    
        ahelper.setBackGround(R.drawable.optionbg2);
        ahelper.setButton1BackGround(R.drawable.pillplainalt);
        ahelper.setButton1Text("back");
        ahelper.setactionBarBackListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
        	
        });
        ahelper.setButton2BackGround(R.drawable.pillalt);
       ahelper.setTitle("Your Profile");
 
		return super.onCreateOptionsMenu(menu);
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
	
}
