package com.ys.peeraxproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewSubjectsActivity extends Activity {

	Button addBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subjectviewscreen);
		addBtn = (Button) findViewById(R.id.subjectaddbtn);
		addBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), CriteriaSelectActivity.class);
				startActivity(i);
			}
			
		});
	}
	
	
}
