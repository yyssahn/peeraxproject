package com.ys.peeraxproject;


import com.ys.peeraxproject.helper.DatabaseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomePageActivity extends Activity {
	Button homeBtn;
	Button proBtn;
	Button conBtn;
	Button sesBtn;
	Button usrBtn;
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
		db = new DatabaseHandler(getApplicationContext());
		if (db.getRowCount()==0){
        	Intent i = new Intent(HomePageActivity.this, StartScreenActivity.class);
        	startActivity(i);
        	
        	finish();
        	
        }
		homeBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HomePageActivity.this, HomePageActivity.class);
				startActivity(i);
				finish();
			}
			
		});

		proBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HomePageActivity.this, ProfilePageActivity.class);
				startActivity(i);
				finish();
			}
			
		});
		
	}

}
