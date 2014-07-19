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
				startActivity(i);
				finish();
			}
			
		});
		busBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject();
			}
			
		});
		langBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject();
			}
			
		});
		sciBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject();
			}
			
		});
		engiBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject();
			}
			
		});
		mathBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject();
			}
			
		});
		sportBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject();
			}
			
		});
		instBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject();
			}
			
		});
		gameBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSubject();
			}
			
		});
       
	}
	
	public void startSubject(){		
		Intent i = new Intent(CriteriaSelectActivity.this, SubjectSelectActivity.class);
		startActivity(i);
		finish();
	}

}
