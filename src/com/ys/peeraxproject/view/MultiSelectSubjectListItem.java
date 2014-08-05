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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MultiSelectSubjectListItem extends LinearLayout {

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ABOUT = "about";
	private static final String TAG_SUBJECT = "subject";
	private static final String TAG_DEGREE = "degree";
	JSONParser jsonParser = new JSONParser();
	TextView subjectName;
	ToggleButton toggle;
	Boolean checked;
	String subjname;
	
    static Bitmap profile_picture;
	public MultiSelectSubjectListItem(Context context, String subject) {
		super(context);
		subjname = subject;
		checked = false;
		// TODO Auto-generated constructor stub
		init(context, subject);
	}

	private void init(final Context con, final String subj) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) con.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.multi_subject, this, true);
		
		subjectName = (TextView) findViewById(R.id.multisubjecttext);
		subjectName.setText(subj);
		toggle = (ToggleButton) findViewById(R.id.subjecttoggle);
		toggle.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1 == true){
					checked = true;
				}else
				{
					checked = false;
				}
			}
			
		});
	}
	public String getname(){
		return subjname;
		
	}
	public String getnameifchecked(){
		if (checked){
		return subjname;
		}else return null;
	}
	
	
	
	
}
