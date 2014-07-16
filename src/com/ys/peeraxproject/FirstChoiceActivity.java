package com.ys.peeraxproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstChoiceActivity extends Activity {
	Button toCreateSessionBtn;
	Button toHomeBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.firstchoicescreen);
       
        
        toCreateSessionBtn = (Button) findViewById(R.id.tutorbutton);
        toHomeBtn = (Button) findViewById(R.id.homebutton);
        
        toCreateSessionBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent i = new Intent(FirstChoiceActivity.this, CreateTutorSession.class);
//				startActivity(i);
//				finish();
			}
        	
        });
        
        toHomeBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent i = new Intent(ChoiceActivity.this, MainPageActivity.class);
//				startActivity(i);
//				finish();
			}
        	
        });
	}
}
