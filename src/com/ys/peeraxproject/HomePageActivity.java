package com.ys.peeraxproject;


import com.ys.peeraxproject.helper.DatabaseHandler;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageActivity extends Activity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
				R.layout.actionbar,
				null);
		// Set up your ActionBar

	    inflater = getLayoutInflater();
		final LinearLayout mainpage = (LinearLayout)findViewById(R.layout.tempmainscreen);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		final RelativeLayout layout = (RelativeLayout) findViewById(R.id.actionbar);
		layout.setBackgroundResource(R.drawable.optionbar1);
		final Button actionBarBack = (Button) findViewById(R.id.optionbackbutton);
		actionBarBack.setText("back");
		
		actionBarBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

	        	Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
	           
			}
			
			
		});

		final Button actionBarInflate = (Button) findViewById(R.id.createoptionbutton);
		actionBarInflate.setText("inflate");

		actionBarInflate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Dialog dil = new Dialog(HomePageActivity.this);
			dil.setTitle(null);
			
			DisplayMetrics dimension = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(dimension);
	        int height = dimension.heightPixels - actionBarInflate.getBottom();
			Window window = dil.getWindow();
			WindowManager.LayoutParams wlp = window.getAttributes();
			Log.d("something",""+actionBarInflate.getBottom());
			wlp.y = actionBarInflate.getBottom();
			Log.d("", ""+ height);
			wlp.gravity=Gravity.TOP | Gravity.RIGHT;
			
			
			
			window.setAttributes(wlp);
			
			dil.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dil.setContentView(R.layout.optionbar);
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
					Intent i = new Intent(HomePageActivity.this, ProfilePageActivity.class);
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
			
			}
			
			
		});

		

		final TextView actionBarStaff = (TextView) findViewById(R.id.optiontitle);
		actionBarStaff.setText("Home Page");

		return super.onCreateOptionsMenu(menu);
	}
	Button homeBtn;
	Button proBtn;
	Button conBtn;
	Button sesBtn;
	Button usrBtn;
	Button logoutBtn;
	LayoutInflater inflater;
	DatabaseHandler db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tempmainscreen);
		homeBtn = (Button) findViewById(R.id.temphomebtn);
		proBtn = (Button) findViewById(R.id.tempprofilebtn);
		conBtn = (Button) findViewById(R.id.tempconversationbtn);
		sesBtn = (Button) findViewById(R.id.tempsessionbtn);
		usrBtn = (Button) findViewById(R.id.tempuserbtn);
		logoutBtn = (Button) findViewById(R.id.templogout);
		db = new DatabaseHandler(getApplicationContext());
		/*
		if (db.getRowCount()==0){
        	Intent i = new Intent(HomePageActivity.this, StartScreenActivity.class);
        	startActivity(i);


        }
		 */
		homeBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HomePageActivity.this, HomePageActivity.class);
				startActivity(i);
			}

		});

		proBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HomePageActivity.this, ProfilePageActivity.class);
				startActivity(i);
				//	finish();
			}

		});


		logoutBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				db.resetTables();
				Intent i = new Intent(HomePageActivity.this, StartScreenActivity.class);
				startActivity(i);
				//	finish();
			}

		});

	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.optionbackbutton:
            // search action
        	Log.d("back","back");
        	Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
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
}
