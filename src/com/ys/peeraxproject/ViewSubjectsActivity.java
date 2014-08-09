package com.ys.peeraxproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ys.peeraxproject.ProfilePageActivity.GetInfo;
import com.ys.peeraxproject.helper.ActionBarHelper;
import com.ys.peeraxproject.helper.DatabaseHandler;
import com.ys.peeraxproject.helper.JSONParser;
import com.ys.peeraxproject.view.SubjectListItem;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSubjectsActivity extends Activity {
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		overridePendingTransition(0, 0);
		super.onDestroy();
		
	}

	DatabaseHandler db;
	JSONParser jsonParser = new JSONParser();
	Button addBtn;
	private static final String TAG_SUCCESS = "success";

	ListView list;
	LayoutInflater inflater;

    static Bitmap profile_picture;
	Dialog dil;

	AlertDialog.Builder abuilder;
	AlertDialog adialog;
	ArrayList<HashMap<String, String>> subjectList;
    
    private static String getsubjectURL = "http://104.131.141.54/lny_project/get_subjects.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subjectviewscreen);
		
		db = new DatabaseHandler(getApplicationContext());
		addBtn = (Button) findViewById(R.id.subjectaddbtn);
		new GetSubject().execute();
		subjectList = new ArrayList<HashMap<String, String>>();
		list = (ListView) findViewById(R.id.subjectlist);

		addBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				
				Intent i = new Intent(getApplicationContext(), CriteriaSelectActivity.class);
				
				startActivity(i);
				finish();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		final Context cont = ViewSubjectsActivity.this;
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
				dil.dismiss();
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
				Intent i = new Intent(cont, ChatActivity.class);
				dil.dismiss();
				startActivity(i);
			}
			
		});

		Button settingButton = (Button)dil.findViewById(R.id.optionsetting);
		settingButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(cont, SettingStatusActivity.class);
				dil.dismiss();
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

	class GetSubject extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         }
 
        /**
         * Creating product
         * */
        @Override
		protected String doInBackground(String... args) {
            JSONArray subjectarray = null;
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String phonenumber = db.getPhoneNumber();
            params.add(new BasicNameValuePair("tag", "about"));
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
           // getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(getsubjectURL,
                    "POST", params);
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                		
                    //finish();
                	subjectarray = json.getJSONArray("subjects");
                	 
                    // looping through All Products
                    for (int i = 0; i < subjectarray.length(); i++) {
                        JSONObject c = subjectarray.getJSONObject(i);
 
                        // Storing each json item in variable
                        String cri = c.getString("criteria");
                        String subj = c.getString("subject");
                        String price = c.getString("price");
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put("criteria", cri);
                        
                        map.put("subject", subj);
                        map.put("price", price);
                        // adding HashList to ArrayList
                        subjectList.add(map);
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
                	 SubjectAdapter adapter = new SubjectAdapter(subjectList);
                 	list.setAdapter(adapter);
                      }
             });
        	}

	}
	
	class SubjectAdapter extends BaseAdapter{

		ArrayList<HashMap<String, String>> sList;
		public SubjectAdapter(ArrayList<HashMap<String, String>> subjectList) {
			// TODO Auto-generated constructor stub
			sList = subjectList;
		}

		//ArrayList<HashMap<String,String>> slist;
		//SubjectAdapter(ArrayList<HashMap<String, String>> subjectList){
		//	this.slist = subjectList;
		//}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return sList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return sList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
			
		}
 
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
		
			SubjectListItem list = new SubjectListItem(ViewSubjectsActivity.this,sList.get(arg0).get("criteria"), sList.get(arg0).get("subject"),sList.get(arg0).get("price"), db.getPhoneNumber());
			list.setText1(sList.get(arg0).get("subject"));

			list.setText2(sList.get(arg0).get("price"));
			return list;
		
		}
		
		
	}

    
	
	
}
