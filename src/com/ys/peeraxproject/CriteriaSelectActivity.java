package com.ys.peeraxproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CriteriaSelectActivity extends Activity {
	Button artsBtn;
	Button busBtn;
	Button langBtn;
	Button sciBtn;
	Button engiBtn;
	Button mathBtn;
	Button sportBtn;
	Button instBtn;
	Button gameBtn;
	private static final String TAG_CRITERIA = "criteria";
	private static final String TAG_SUBJECT = "subject";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.criteriachoicescreen);
		artsBtn = (Button)findViewById(R.id.criteriaarts);
		busBtn = (Button) findViewById(R.id.criteriabusiness);
		langBtn = (Button) findViewById(R.id.criterialanguage);
		sciBtn = (Button) findViewById(R.id.criteriascience);
		engiBtn = (Button) findViewById(R.id.criteriaengineering);
		mathBtn = (Button) findViewById(R.id.criteriamath);
		sportBtn = (Button) findViewById(R.id.criteriasports);
		instBtn = (Button) findViewById(R.id.criteriainstrument);
		gameBtn = (Button) findViewById(R.id.criteriagame);
		
		artsBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(CriteriaSelectActivity.this, SubjectSelectActivity.class);
				i.putExtra(TAG_CRITERIA, "arts");
				startActivity(i);
				finish();
			}
			
		});
		busBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject("bus");
			}
			
		});
		langBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject("lang");
			}
			
		});
		sciBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject("sci");
			}
			
		});
		engiBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject("engl");
			}
			
		});
		mathBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject("math");
			}
			
		});
		sportBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject("sport");
			}
			
		});
		instBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject("instrum");
				
				
			}
			
		});
		gameBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject("game");
			}
			
		});
       
	}
	
	public void startSubject(String criteria){		
		Intent i = new Intent(CriteriaSelectActivity.this, SubjectSelectActivity.class);
		i.putExtra(TAG_CRITERIA,criteria);
		startActivity(i);
		finish();
	}

}
