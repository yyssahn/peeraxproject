package com.ys.peeraxproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SubjectSelectActivity extends Activity {
	Button subject1;
	Button subject2;
	Button subject3;
	Button subject4;
	String criteria;
	private static final String KEY_CRITERIA = "criteria";
	private static final String KEY_SUBJECTS = "subject";

	private static final String TAG_CRITERIA = "criteria";
	private static final String TAG_SUBJECT = "subject";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subjectchoicescreen);
		subject1 = (Button)findViewById(R.id.subject1);
		Intent i = getIntent();
		criteria = i.getExtras().getString(TAG_CRITERIA);
		Log.d("criteria", criteria);
		subject1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startPrice(criteria, "subject1");
			}
			
			
		});
		subject2 = (Button)findViewById(R.id.subject2);
		subject2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startPrice(criteria, "subject2");
				
			}
			
			
		});
		subject3 = (Button)findViewById(R.id.subject3);
		subject3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				startPrice(criteria, "subject3");
				
			}
			
			
		});
		subject4 = (Button)findViewById(R.id.subject4);
		subject4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startPrice(criteria, "subject4");
				
			}
			
			
		});
		
	}
	public void startPrice(String criteria, String subject){		
		Intent i = new Intent(SubjectSelectActivity.this, PriceSelectScreen.class);
		i.putExtra(TAG_CRITERIA,criteria);
		i.putExtra(TAG_SUBJECT, subject);
		startActivity(i);
		finish();
	}

}
