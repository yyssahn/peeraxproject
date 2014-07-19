package com.ys.peeraxproject;


import com.ys.peeraxproject.helper.DatabaseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class StartScreenActivity extends Activity {
	Button loginBtn;
	Button signupBtn;
	DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(getApplicationContext());
        if (db.getRowCount()==1){
        	Intent i = new Intent(StartScreenActivity.this, HomePageActivity.class);
        	startActivity(i);
        	
        	finish();
        	
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start_screen);
        
       loginBtn = (Button)findViewById(R.id.tologinbtn);
        signupBtn=(Button)findViewById(R.id.tosignupbtn);
       
        loginBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(StartScreenActivity.this, LoginScreenActivity.class);
				startActivity(i);
				
				finish();
			}
        	
        });
        
       signupBtn.setOnClickListener(new OnClickListener(){
    	   @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(StartScreenActivity.this, SignUpActivity.class);
				startActivity(i);
				
				finish();
			}
       });
        
        
        
    }
}
