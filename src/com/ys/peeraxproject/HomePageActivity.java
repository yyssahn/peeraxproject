package com.ys.peeraxproject;

import com.ys.peeraxproject.helper.ActionBarHelper;
import com.ys.peeraxproject.helper.DatabaseHandler;
import com.ys.peeraxproject.location.LocationService;
import com.ys.peeraxproject.view.PeopleListItem;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomePageActivity extends FragmentActivity {

    private LayoutInflater inflater;
    private DatabaseHandler db;
    private Dialog dil;
    private Button locationBtn;
    private Button stopLocationBtn;
    private static Context homeContext;
    private static ListView peopleList;
    private static ArrayList<HashMap<String, String>> pList = new ArrayList<HashMap<String,String>>();
	private static JSONArray peopleArray;
    private static Bitmap profile_picture;
    private static String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempmainscreen);
        db = new DatabaseHandler(getApplicationContext());
        
        // Temporary for location
        locationBtn = (Button)findViewById(R.id.locationbutton);
		locationBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startService(new Intent(getApplicationContext(), LocationService.class));
			}
		});
		stopLocationBtn = (Button)findViewById(R.id.stoplocationbutton);
		stopLocationBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				stopService(new Intent(getApplicationContext(), LocationService.class));
			}
		});
		
		homeContext = getApplicationContext();
		
		startService(new Intent(getApplicationContext(), LocationService.class));
        peopleList = (ListView) findViewById(R.id.peoplelist);
        peopleList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), ((PeopleListItem) arg1).getPhonenumber(), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(HomePageActivity.this, GetUserActivity.class);
				i.putExtra("userphone", ((PeopleListItem)arg1).getPhonenumber());
				startActivity(i);			
			}
        });
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Set up ActionBar
        inflater = getLayoutInflater();

		final ActionBar actionBar = getActionBar();
		
		dil = new Dialog(HomePageActivity.this);
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
				Intent i = new Intent(HomePageActivity.this, ProfilePageActivity.class);
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
				Intent i = new Intent(HomePageActivity.this, SettingStatusActivity.class);
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
        
		final ActionBarHelper ahelper = new ActionBarHelper(HomePageActivity.this, inflater, actionBar, db.getPhoneNumber(), dil);
    
        ahelper.setBackGround(R.drawable.optionbar1);
        ahelper.setButton1BackGround(R.drawable.pill2);

        ahelper.setButton2BackGround(R.drawable.pill);
        ahelper.setTitle("Home Page");
 
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
	
	public static void addToMap(){
		Log.d("HomePageActivity", "In addToMap");
		try {
			for(int i=0; i<peopleArray.length(); i++){
				JSONObject p;
			
				p = peopleArray.getJSONObject(i);
				String name = p.getString("name");
				String about = p.getString("about");
				String phone = p.getString("phonenumber");
				
				HashMap<String, String> map = new HashMap<String, String>();
				
		        // adding each child node to HashMap key => value
		        map.put("about", about);
		        map.put("phonenumber", phone);
		        map.put("name", name);
		        // adding HashList to ArrayList
		        pList.add(map);

           	 	PeopleAdapter adapter = new PeopleAdapter(homeContext, pList);
            	peopleList.setAdapter(adapter);
			}
			
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	//=============================================================================================
	public class GetInfo extends AsyncTask<String, String, String> {

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
	//=============================================================================================
	public static class UsersReceiver extends BroadcastReceiver {

	    @Override
	    public void onReceive(Context context, Intent intent) {
		   Log.d("HomePageActivity", "In UsersReceiver");
		   String jsonArray = intent.getStringExtra("jsonArray");
		   try{
			   peopleArray = new JSONArray(jsonArray);
			   addToMap();
		   }catch(JSONException e){
			   e.printStackTrace();
		   }
	    }
	}
	//=============================================================================================
}
