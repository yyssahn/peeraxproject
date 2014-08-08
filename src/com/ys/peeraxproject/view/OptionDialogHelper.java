package com.ys.peeraxproject.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.ys.peeraxproject.ChatActivity;
import com.ys.peeraxproject.HomePageActivity;
import com.ys.peeraxproject.ProfilePageActivity;
import com.ys.peeraxproject.R;
import com.ys.peeraxproject.SettingStatusActivity;
import com.ys.peeraxproject.HomePageActivity.GetInfo;

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
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OptionDialogHelper extends Activity {
	Dialog dil;
	Context cont;
	ActionBar abar;
	String user_name;
	Button profileButton;
	Display dis;
	Bitmap profile_picture;
	Button homeButton;
	Button convButton;
	Button sesButton;
	Button settingButton;
	
	public OptionDialogHelper(Context context, ActionBar bar, String name ,Display disp) {
		
		cont = context;
		abar = bar;
		user_name = name;

		dis = disp;
		init(cont);
		
		// TODO Auto-generated constructor stub
	}
	private void init(Context cont2) {
		// TODO Auto-generated method stub
		dil = new Dialog(cont);
		dil.setTitle(null);
        dil.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		dil.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dil.setContentView(R.layout.optionbar);

		
        new GetInfo().execute();

        homeButton = (Button)dil.findViewById(R.id.optionhomebtn);
		
		profileButton = (Button)dil.findViewById(R.id.optionprofilebtn);
		convButton = (Button)dil.findViewById(R.id.optionconversationbtn);
		sesButton = (Button)dil.findViewById(R.id.optionsessionbtn);

		Button settingButton = (Button)dil.findViewById(R.id.optionsetting);
	


        Window window = dil.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        DisplayMetrics dimension = new DisplayMetrics();
        dis.getMetrics(dimension);
        int height = dimension.heightPixels - abar.getHeight();
        int actionBarHeight = abar.getHeight() + 30;

        wlp.y = actionBarHeight;
        wlp.gravity=Gravity.TOP | Gravity.RIGHT;
        wlp.height = height - abar.getHeight();

        window.setAttributes(wlp);
	}
	public class GetInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            
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
	public Dialog getDil(){
		return dil;
	}
	public void setonclickprofile(OnClickListener listener){
		if(listener == null)			
			dil.dismiss();
		else
		profileButton.setOnClickListener(listener);
	}
	public void setonclickhome(OnClickListener listener){
		if(listener == null)			
			dil.dismiss();
		else 
		homeButton.setOnClickListener(listener);
	}
	public void setonclickconv(OnClickListener listener){
		if(listener == null)			
			dil.dismiss();
		else
		convButton.setOnClickListener(listener);
	}
	public void setonclickses(OnClickListener listener){
		if(listener == null)			
			dil.dismiss();
		else
		sesButton.setOnClickListener(listener);
	}
	public void setonclicksetting(OnClickListener listener){
		if(listener == null)			
			dil.dismiss();
		else
		settingButton.setOnClickListener(listener);
	}
}

