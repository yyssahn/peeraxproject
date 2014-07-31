package com.ys.peeraxproject.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.ys.peeraxproject.ProfilePageActivity;
import com.ys.peeraxproject.R;
import com.ys.peeraxproject.ViewSubjectsActivity;
import com.ys.peeraxproject.helper.JSONParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SubjectListItem extends LinearLayout {

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ABOUT = "about";
	private static final String TAG_SUBJECT = "subject";
	private static final String TAG_DEGREE = "degree";
	JSONParser jsonParser = new JSONParser();
	private static String loginURL = "http://104.131.141.54/lny_project/delete_subject.php";
	private static String info_tag = "info";
    private static final int GET_LOCAL_IMAGE = 8;
	TextView text1;
	TextView text2;
	Button button;
	String price;
	final Context cont;
	String criteria;
	String subject;
	String phonenumber;
	public SubjectListItem(Context context, String cri, String sub,  String pri, String phone) {
		super(context);
		criteria = cri;
		
		cont = context;
		
		
		subject = sub;
		price = pri;
		phonenumber = phone;
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context con) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) con.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.subject, this, true);

		text1 =(TextView) findViewById(R.id.subjecttext);
		text2=(TextView) findViewById(R.id.subjecttext2);


		button  = (Button) findViewById(R.id.subjectbutton1);


		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//adialog.show();
				AlertDialog.Builder abuilder = new AlertDialog.Builder(cont);
				abuilder.setTitle("Delete Confirmation");
				abuilder.setMessage("You sure about deleting thig course?");
				abuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						new DeleteSubject().execute();
						Intent i = new Intent(cont, ViewSubjectsActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
						cont.startActivity(i);
					}
				});
				abuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				AlertDialog adialog = abuilder.create();
				
				adialog.show();
			}


		});
	}

	public void setText1(String str){
		text1.setText(str);
	}

	public void setText2(String str){
		text2.setText(str);
	}

	class DeleteSubject extends AsyncTask<String, String, String> {

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
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
           
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
            params.add(new BasicNameValuePair("price", price));
            params.add(new BasicNameValuePair("criteria", criteria));
            params.add(new BasicNameValuePair("subject", subject));
            // getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(loginURL,
                   "POST", params);
          //   check log cat fro response
          Log.d("Create Response", json.toString());
 
           //  check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	
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
        }

	}

}