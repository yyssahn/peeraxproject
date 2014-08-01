package com.ys.peeraxproject;

import com.ys.peeraxproject.helper.ActionBarHelper;
import com.ys.peeraxproject.helper.DatabaseHandler;
import com.ys.peeraxproject.helper.JSONParser;
import com.ys.peeraxproject.location.LocationService;
import com.ys.peeraxproject.location.NearbyUsersService;
import com.ys.peeraxproject.view.PeopleListItem;

import android.app.ActionBar;
import android.app.Dialog;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePageActivity extends FragmentActivity {

    private LayoutInflater inflater;
    private DatabaseHandler db;
    private Dialog dil;
    private Button locationBtn;
    private Button stopLocationBtn;
    private ListView peopleList;
    
    private static Bitmap profile_picture;
    private static String user_name;

	private static final String TAG_SUCCESS = "success";

	private ArrayList<HashMap<String, String>> pList;
    
    private static String getpeopleURL = "http://104.131.141.54/lny_project/get_people.php";
    private JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempmainscreen);
        db = new DatabaseHandler(getApplicationContext());
        //db.resetTables();
        
        // Temporary for location
        locationBtn = (Button)findViewById(R.id.locationbutton);
		locationBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startService(new Intent(getApplicationContext(), LocationService.class));
				startService(new Intent(getApplicationContext(), NearbyUsersService.class));
			}
		});
		stopLocationBtn = (Button)findViewById(R.id.stoplocationbutton);
		stopLocationBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				stopService(new Intent(getApplicationContext(), LocationService.class));
			}
		});
		
        peopleList = (ListView) findViewById(R.id.peoplelist);
        pList = new ArrayList<HashMap<String, String>>();
        Log.d("shit","eat");
        new GetPeople().execute();
    }

    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
			
		// Set up your ActionBar

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
	
	class PeopleAdapter extends BaseAdapter{

		ArrayList<HashMap<String, String>> list;
		public PeopleAdapter(ArrayList<HashMap<String, String>> pList) {
			// TODO Auto-generated constructor stub
			list = pList;
		}

		//ArrayList<HashMap<String,String>> slist;
		//SubjectAdapter(ArrayList<HashMap<String, String>> subjectList){
		//	this.slist = subjectList;
		//}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
			
		}
 
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
		
//			SubjectListItem list = new SubjectListItem(getApplicationContext(),sList.get(arg0).get("criteria"), sList.get(arg0).get("subject"),sList.get(arg0).get("price"), db.getPhoneNumber());
//			list.setText1(pList.get(arg0).get("subject"));

//			list.setText2(pList.get(arg0).get("price"));
			PeopleListItem pitem = new PeopleListItem(getApplicationContext());
			pitem.setAbout(list.get(arg0).get("about"));
			pitem.setName(list.get(arg0).get("name"));
			pitem.setPhonenumber(list.get(arg0).get("phonenumber"));
			return pitem;
		
		}
		
	}
	
	class GetPeople extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            Log.d("shit","eat2");
        	super.onPreExecute();
         }
 
        /**
         * Creating product
         * */
        @Override
		protected String doInBackground(String... args) {
            Log.d("shit","eat3");
            JSONArray peoplearray = null;
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", "about"));
           // getting JSON Object
           JSONObject json = jsonParser.makeHttpRequest(getpeopleURL,
                    "POST", params);
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
              		
                    //finish();
                	peoplearray = json.getJSONArray("people");
                	
                    // looping through All Products
    
                	for (int i = 0; i < peoplearray.length(); i++) {
                        JSONObject c = peoplearray.getJSONObject(i);
 
                        // Storing each json item in variable
                        String name = c.getString("name");
                        String about = c.getString("about");
                        String phonenumber = c.getString("phonenumber");
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put("about", about);
                        map.put("phonenumber", phonenumber);
                        map.put("name", name);
                        // adding HashList to ArrayList
                        pList.add(map);
                    }
               } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
		protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
        //    pDialog.dismiss();
        	 runOnUiThread(new Runnable() {
                 public void run() {
                     /**
                      * Updating parsed JSON data into ListView
                      * */
                	 PeopleAdapter adapter = new PeopleAdapter(pList);
                 	peopleList.setAdapter(adapter);
                 	
                      }
             });
        	}

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
}
