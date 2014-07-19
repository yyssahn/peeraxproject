package com.ys.peeraxproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SubjectSelectActivity extends Activity {
	Button subject1;
	Button subject2;
	Button subject3;
	Button subject4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subjectchoicescreen);
		subject1 = (Button)findViewById(R.id.subject1);
		subject1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SubjectSelectActivity.this, PriceSelectScreen.class);
				startActivity(i);
				finish();
			}
			
			
		});
		subject2 = (Button)findViewById(R.id.subject1);
		subject2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SubjectSelectActivity.this, PriceSelectScreen.class);
				startActivity(i);
				finish();
			}
			
			
		});
		subject3 = (Button)findViewById(R.id.subject1);
		subject3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SubjectSelectActivity.this, PriceSelectScreen.class);
				startActivity(i);
				finish();
			}
			
			
		});
		subject4 = (Button)findViewById(R.id.subject1);
		subject4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SubjectSelectActivity.this, PriceSelectScreen.class);
				startActivity(i);
				finish();
			}
			
			
		});
		
	}

}
