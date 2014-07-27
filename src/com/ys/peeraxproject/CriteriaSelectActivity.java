package com.ys.peeraxproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.ys.peeraxproject.ViewSubjectsActivity.GetInfo;
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

public class CriteriaSelectActivity extends Activity {
	Button artsBtn;
	Button busBtn;
	Button langBtn;
	Button sciBtn;
	Button engiBtn;
	Button mathBtn;
	Button sportBtn;
	LayoutInflater inflater;

    static Bitmap profile_picture;
	Dialog dil;
	DatabaseHandler db;
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
		db = new DatabaseHandler(getApplicationContext());
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
				R.layout.actionbar,
				null);
		// Set up your ActionBar

        inflater = getLayoutInflater();
		final LinearLayout mainpage = (LinearLayout)findViewById(R.id.tempmainscreen);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		final RelativeLayout layout = (RelativeLayout) findViewById(R.id.actionbar);
		layout.setBackgroundResource(R.drawable.optionbg2);
		final Button actionBarBack = (Button) findViewById(R.id.optionbackbutton);
        actionBarBack.setBackgroundResource(R.drawable.optionbutton7);
       
        actionBarBack.setText("Refresh");
        final Context con = CriteriaSelectActivity.this;
		
		actionBarBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

	        	Toast.makeText(con, "Refresh", Toast.LENGTH_SHORT).show();
	           
			}
			
			
		});

		final Button actionBarInflate = (Button) findViewById(R.id.createoptionbutton);
        actionBarInflate.setBackgroundResource(R.drawable.optionbutton7);
        actionBarInflate.setText("Menu");

		actionBarInflate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			dil = new Dialog(con);
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
					Intent i  = new Intent(con, HomePageActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
					finish();
				}
				
			});
			Button profileButton = (Button)dil.findViewById(R.id.optionprofilebtn);
			profileButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(con, ProfilePageActivity.class);
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
					// TODO Auto-generated method stub
				}
				
			});

			dil.show();

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
            }


        });

		final TextView actionBarStaff = (TextView) findViewById(R.id.optiontitle);
		actionBarStaff.setText("Home Page");

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
