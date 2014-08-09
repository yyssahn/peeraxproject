package com.ys.peeraxproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.ys.peeraxproject.SettingStatusActivity.GetInfo;
import com.ys.peeraxproject.helper.ActionBarHelper;
import com.ys.peeraxproject.helper.DatabaseHandler;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingOptionActivity extends Activity {
	Dialog dil;
	LayoutInflater inflater;
	DatabaseHandler db;
	Bitmap profile_picture;
	String user_name;
	Switch freeSwitch;
	Button toDegreeBtn;
	EditText minPrice;
	EditText maxPrice;
	EditText minRating;
	EditText maxRating;
	EditText minHour;
	Context cont;
	Button toCriteriaBtn;


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		final Context cont = SettingOptionActivity.this;
		// Set up your ActionBar

        inflater = getLayoutInflater();

		final ActionBar actionBar = getActionBar();
		
		dil = new Dialog(cont);
		dil.setTitle(null);
        dil.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		dil.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dil.setContentView(R.layout.optionbar);

		
        new GetInfo().execute();

        Button homeButton = (Button)dil.findViewById(R.id.optionhomebtn);
		homeButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(cont, HomePageActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            	startActivity(i);
                dil.dismiss();
                finish();
				// TODO Auto-generated method stub
			}
			
		});
		Button profileButton = (Button)dil.findViewById(R.id.optionprofilebtn);
		profileButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(cont, ProfilePageActivity.class);
				startActivity(i);
				dil.dismiss();
			}
			
		});
		Button convButton = (Button)dil.findViewById(R.id.optionconversationbtn);
		convButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
			
		});
		Button sesButton = (Button)dil.findViewById(R.id.optionsessionbtn);
		sesButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(cont, ChatActivity.class);
				dil.dismiss();
				startActivity(i);
			}
			
		});

		Button settingButton = (Button)dil.findViewById(R.id.optionsetting);
		settingButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				dil.dismiss();
				finish();
				// TODO Auto-generated method stub
			}
			
		});


        Window window = dil.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);
        int height = dimension.heightPixels - actionBar.getHeight();
        int actionBarHeight = actionBar.getHeight() + 30;

        wlp.y = actionBarHeight;
        wlp.gravity=Gravity.TOP | Gravity.RIGHT;
        wlp.height = height - actionBar.getHeight();

        window.setAttributes(wlp);
        
		final ActionBarHelper ahelper = new ActionBarHelper(cont, inflater, actionBar, db.getPhoneNumber(), dil);
    
        ahelper.setBackGround(R.drawable.optionbg2);
        ahelper.setButton1BackGround(R.drawable.pillplainalt);
        ahelper.setButton1Text("back");
        ahelper.setactionBarBackListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
        	
        });
        ahelper.setButton2BackGround(R.drawable.pillalt);
       ahelper.setTitle("Your Profile");
 
		return super.onCreateOptionsMenu(menu);
	}
	


	class GetInfo extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... args) {

			user_name = db.getPhoneNumber();

			try {
				profile_picture = BitmapFactory.decodeStream((InputStream) new URL("http://104.131.141.54/lny_project/" + user_name + ".jpg").getContent());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String file_url) {
			if(profile_picture != null) {
				ImageView picture = (ImageView) dil.findViewById(R.id.optionpicture);
				picture.setImageBitmap(profile_picture);
			}

			TextView about = (TextView) dil.findViewById(R.id.optionid);
			about.setText(user_name);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settingoptionscreen);
		db = new DatabaseHandler(SettingOptionActivity.this);
		toCriteriaBtn = (Button) findViewById(R.id.settingsubjectbtn);
		toCriteriaBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SettingOptionActivity.this, SettingCriteriaActivity.class);
				startActivity(i);
			}

		});
		toDegreeBtn = (Button)findViewById(R.id.settingdegreebtn);
		toDegreeBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SettingOptionActivity.this, SettingDegreeActivity.class);
				startActivity(i);
			}

		});
		freeSwitch = (Switch) findViewById(R.id.togglebutton);
		minPrice = (EditText) findViewById(R.id.minpriceedittext);
		minPrice.setText(db.getMinMoney());
		maxPrice = (EditText) findViewById(R.id.maxpriceedittext);
		maxPrice.setText(db.getMaxMoney());
		minPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if (Float.valueOf(minPrice.getText().toString())>Float.valueOf(maxPrice.getText().toString())){
						maxPrice.setText(minPrice.getText().toString());

					}

					return true;
				}
				return false;
			}});
		minPrice.setOnFocusChangeListener(new OnFocusChangeListener() {          
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus)
					if (Float.valueOf(minPrice.getText().toString())>Float.valueOf(maxPrice.getText().toString())){
						maxPrice.setText(minPrice.getText().toString());

					} 
			}
		});
		maxPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if (Float.valueOf(minPrice.getText().toString())>Float.valueOf(maxPrice.getText().toString())){
						minPrice.setText(maxPrice.getText().toString());

					}

					return true;
				}
				return false;
			}});
		maxPrice.setOnFocusChangeListener(new OnFocusChangeListener() {          
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus)
					if (Float.valueOf(minPrice.getText().toString())>Float.valueOf(maxPrice.getText().toString())){
						minPrice.setText(maxPrice.getText().toString());

					} 
			}
		});



				
		freeSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1){
					minPrice.setText("0");

					maxPrice.setText("0");
				}
			}


		});
		minRating = (EditText) findViewById(R.id.minrating);
		minRating.setText(db.getMinRating());
		maxRating = (EditText) findViewById(R.id.maxratingedittext);
		maxRating.setText(db.getMaxRating());
		minHour = (EditText) findViewById(R.id.houredittext);
		minHour.setText(db.getMinHour());


		minRating.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if (Float.valueOf(minRating.getText().toString())>Float.valueOf(maxRating.getText().toString())){
						maxRating.setText(minRating.getText().toString());

					}

					return true;
				}
				return false;
			}});
		minRating.setOnFocusChangeListener(new OnFocusChangeListener() {          
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus)
					if (Float.valueOf(minRating.getText().toString())>Float.valueOf(maxRating.getText().toString())){
						maxRating.setText(minRating.getText().toString());

					} 
			}
		});
		maxRating.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if (Float.valueOf(minRating.getText().toString())>Float.valueOf(maxRating.getText().toString())){
						minRating.setText(maxRating.getText().toString());

					}

					return true;
				}
				return false;
			}});
		maxRating.setOnFocusChangeListener(new OnFocusChangeListener() {          
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus)
					if (Float.valueOf(minRating.getText().toString())>Float.valueOf(maxRating.getText().toString())){
						minRating.setText(maxRating.getText().toString());

					} 
			}
		});

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		db.setMinHour(minHour.getText().toString());
		db.setMaxMoney(maxPrice.getText().toString());
		db.setMinMoney(minPrice.getText().toString());
		db.setMinRating(minRating.getText().toString());
		db.setMaxRating(maxRating.getText().toString());
		super.onPause();
	}



}
