package com.ys.peeraxproject;

import com.ys.peeraxproject.helper.DatabaseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ProfilePageActivity extends Activity {
	Button pictureBtn;
	Button aboutBtn;
	Button subjectsBtn;
	Button educationBtn;
	TextView username;
	DatabaseHandler db;
	String KEY_ID= "id";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilescreen);
		db = new DatabaseHandler(getApplicationContext());
		subjectsBtn = (Button) findViewById(R.id.profilesubjectbtn);
		username = (TextView) findViewById(R.id.profilename);
		username.setText(db.getPhoneNumber());
		subjectsBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ViewSubjectsActivity.class);
				startActivity(i);
			}
			
			
		});
		aboutBtn = (Button)findViewById(R.id.profileaboutbtn);
		aboutBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), ProfileAboutActivity.class);
				
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
	
}
