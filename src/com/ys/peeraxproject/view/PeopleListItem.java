package com.ys.peeraxproject.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PeopleListItem extends LinearLayout {

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ABOUT = "about";
	private static final String TAG_SUBJECT = "subject";
	private static final String TAG_DEGREE = "degree";
	JSONParser jsonParser = new JSONParser();
	private static String loginURL = "http://104.131.141.54/lny_project/delete_subject.php";
	private static String info_tag = "info";
    private static final int GET_LOCAL_IMAGE = 8;
	TextView abouttext;
	TextView nametext;
	TextView pricetext;
	ImageView image;
	String pnumber;
	String price;
	String criteria;
	String subject;
	String phonenumber;

    static Bitmap profile_picture;
	public PeopleListItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context con) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) con.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.people, this, true);
		abouttext= (TextView) findViewById(R.id.peopleabout);
		nametext=(TextView) findViewById(R.id.peoplename);
		image = (ImageView)findViewById(R.id.pid);
	}
	
	public void setAbout(String str){
		abouttext.setText(str);
	}
	
	public void setName(String str){
		nametext.setText(str);
	}
	public void setPhonenumber(String str){
		phonenumber =str;
		new GetInfo().execute();
		
	}
	class GetInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            try {
                profile_picture = BitmapFactory.decodeStream((InputStream) new URL("http://104.131.141.54/lny_project/" + phonenumber + ".jpg").getContent());
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
                image.setImageBitmap(profile_picture);
            }

        }
    }
	
}
