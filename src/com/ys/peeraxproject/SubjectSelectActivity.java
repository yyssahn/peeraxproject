package com.ys.peeraxproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.ys.peeraxproject.CriteriaSelectActivity.GetInfo;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SubjectSelectActivity extends Activity {
	Button subject1;
	Button subject2;
	Button subject3;
	Button subject4;
	String criteria;
	private static final String KEY_CRITERIA = "criteria";
	private static final String KEY_SUBJECTS = "subject";
	LayoutInflater inflater;

    static Bitmap profile_picture;
	Dialog dil;
	DatabaseHandler db;
	private static final String TAG_CRITERIA = "criteria";
	private static final String TAG_SUBJECT = "subject";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subjectchoicescreen);
		subject1 = (Button)findViewById(R.id.subject1);
		Intent i = getIntent();
		db = new DatabaseHandler(getApplicationContext());
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
	}@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		final Context cont = SubjectSelectActivity.this;
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
				finish();
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
			}
			
		});

		Button settingButton = (Button)dil.findViewById(R.id.optionsetting);
		settingButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(cont, SettingStatusActivity.class);
				startActivity(i);
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
        ahelper.setButton2BackGround(R.drawable.pillalt);
       ahelper.setTitle("Add Session");
 
		return super.onCreateOptionsMenu(menu);
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.optionbackbutton:
            // search action
        	Log.d("back","back");
        	Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.createoptionbutton:
            // refresh

        	Log.d("back","create");
        	Toast.makeText(getApplicationContext(), "create", Toast.LENGTH_SHORT).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	class GetInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {


            try {
                profile_picture = BitmapFactory.decodeStream((InputStream) new URL("http://104.131.141.54/lny_project/" + db.getPhoneNumber() + ".jpg").getContent());
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
            about.setText(db.getPhoneNumber());
        }
    }

}
