package com.ys.peeraxproject.helper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.ys.peeraxproject.R;
import com.ys.peeraxproject.R.id;
import com.ys.peeraxproject.R.layout;

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
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActionBarHelper extends Activity{
	Context cont;
	Bitmap profile_picture;
	String user_name;
	Button profButton;
	final ViewGroup actionBarLayout;
	 final Button actionBarBack;
	Dialog dil;
	ActionBar abar;
	RelativeLayout Rlayout;
	final Button actionBarInflate;
	final TextView actionBarStaff;
	public ActionBarHelper(Context con, LayoutInflater inflater, ActionBar acbar, String phone, Dialog dial){
		actionBarLayout = (ViewGroup) inflater.inflate(
				R.layout.actionbar,
				null);
		dil = dial;
		user_name = phone;
		cont = con;
		abar = acbar;
		abar.setDisplayShowHomeEnabled(false);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayShowCustomEnabled(true);
		abar.setCustomView(actionBarLayout);
        abar.setBackgroundDrawable(new ColorDrawable(00000000));
     //   actionBarLayout.setBackgroundResource(R.drawable.optionbar1);
       
        actionBarBack = (Button) actionBarLayout.findViewById(R.id.optionbackbutton);
        actionBarBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

	        		           
			}
			
			
		});
        actionBarInflate = (Button) actionBarLayout.findViewById(R.id.createoptionbutton);

        actionBarInflate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
			
                dil.show();
            
            }

        });

		actionBarStaff = (TextView) actionBarLayout.findViewById(R.id.optiontitle);

	}
	public void setBackGround(int id){
		actionBarLayout.setBackgroundResource(id);
		
	}
	public void setButton1BackGround(int id){
		actionBarBack.setBackgroundResource(id);
		
	}
	public void setButton1Text(String str){
		actionBarBack.setText(str);
	}
	public void setButton2BackGround(int id){
		actionBarInflate.setBackgroundResource(id);
		
	}
	public void setTitle(String title){
		actionBarStaff.setText(title);
	}
	public ActionBar getActionBar(){
		return abar;
	}
	public Button getactionBarBack(){
		return actionBarBack;
		
	}
	public void setactionBarBackListener(OnClickListener listener){
		actionBarBack.setOnClickListener(listener);
	}
	
	public Button getactionBarInflate(){
		return actionBarInflate;
		
	}
	
	public Dialog getDialog(){
		return dil;
		
	}

	public void setProfileListener(OnClickListener listen){
		profButton.setOnClickListener(listen);
	}
	public Button getProfbutton(){
		return profButton;
	}
	}
