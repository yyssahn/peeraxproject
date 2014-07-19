package com.ys.peeraxproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PriceSelectScreen extends Activity {
	Button confirmbtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subjectpricescreen);
		
		confirmbtn = (Button) findViewById(R.id.sessionconfirmbtn);
		confirmbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(PriceSelectScreen.this, ProfilePageActivity.class);
				startActivity(i);
				finish();
			}
			
		});
	}

}
