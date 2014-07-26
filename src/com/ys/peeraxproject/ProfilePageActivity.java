package com.ys.peeraxproject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.ys.peeraxproject.ProfileAboutActivity.UpdateUser;
import com.ys.peeraxproject.helper.DatabaseHandler;
import com.ys.peeraxproject.helper.JSONParser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfilePageActivity extends Activity {
	JSONParser jsonParser = new JSONParser();
	Button pictureBtn;
	Button aboutBtn;
	Button subjectsBtn;
	Button educationBtn;
	TextView username;
	DatabaseHandler db;
	String KEY_ID= "id";
	static String about_text;
	static String subject_text;
	static String degree_text;
    static Bitmap profile_picture;

    InputStream inputStream;

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ABOUT = "about";
	private static final String TAG_SUBJECT = "subject";
	private static final String TAG_DEGREE = "degree";
	private static String loginURL = "http://104.131.141.54/lny_project/change_info.php";
	private static String info_tag = "info";
    private static final int GET_LOCAL_IMAGE = 8;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilescreen);
		db = new DatabaseHandler(getApplicationContext());
		
		username = (TextView) findViewById(R.id.profilename);
		username.setText(db.getPhoneNumber());
		
		new GetInfo().execute();

        pictureBtn = (Button)findViewById(R.id.profilepicturebtn);
        pictureBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, GET_LOCAL_IMAGE);
            }

        });

		aboutBtn = (Button)findViewById(R.id.profileaboutbtn);
		aboutBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), ProfileAboutActivity.class);
				i.putExtra(TAG_ABOUT,about_text);
				startActivity(i);
			}
		});
		
		subjectsBtn = (Button) findViewById(R.id.profilesubjectbtn);
		subjectsBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ViewSubjectsActivity.class);
				startActivity(i);
			}
			
			
		});
		
		educationBtn = (Button) findViewById(R.id.profileeducationbtn);
		educationBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), ProfileDegreeActivity.class);
				i.putExtra(TAG_DEGREE,degree_text);
				startActivity(i);
			}
		});
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_LOCAL_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap = android.provider.MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                if(bitmap.getWidth() > bitmap.getHeight()){
                    int halfDifference = Math.round((bitmap.getWidth()-bitmap.getHeight())/2);
                    bitmap = Bitmap.createBitmap(bitmap, halfDifference, 0, bitmap.getWidth()-halfDifference, bitmap.getHeight());
                }
                else if (bitmap.getWidth() < bitmap.getHeight()){
                    int halfDifference = Math.round((bitmap.getHeight()-bitmap.getWidth())/2);
                    bitmap = Bitmap.createBitmap(bitmap, 0, halfDifference, bitmap.getWidth(), bitmap.getHeight()-(halfDifference*2));
                }

                if(bitmap.getWidth() >= 640 && bitmap.getHeight() >= 640){
                    bitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, true);
                }

                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, bos);

                byte[] byte_arr = bos.toByteArray();
                final String image_str = Base64.encodeToString(byte_arr, 0);

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("image", image_str));
                            nameValuePairs.add(new BasicNameValuePair("phonenumber",db.getPhoneNumber()));

                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://104.131.141.54/lny_project/upload_image.php");
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            final String the_string_response = convertResponseToString(response);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ProfilePageActivity.this, the_string_response, Toast.LENGTH_LONG).show();
                                }
                            });

                        }catch(Exception e){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    Toast.makeText(ProfilePageActivity.this, "ERROR ", Toast.LENGTH_LONG).show();
                                }
                            });
                            System.out.println("Error in http connection "+e.toString());
                        }
                    }
                });
                t.start();

            } catch (java.io.FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException {

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
//                Toast.makeText(ProfilePageActivity.this, "contentLength : ", Toast.LENGTH_LONG).show();
            }
        });

        if (contentLength < 0){
        }
        else{
            byte[] data = new byte[512];
            int len = 0;
            try
            {
                while (-1 != (len = inputStream.read(data)) )
                {
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inputStream.close(); // closing the stream…..
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            res = buffer.toString();     // converting stringbuffer to string…..

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
//                    Toast.makeText(ProfilePageActivity.this, "Result : " , Toast.LENGTH_LONG).show();
                }
            });
            //System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
    }
	
	class GetInfo extends AsyncTask<String, String, String> {
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         }
 
        @Override
		protected String doInBackground(String... args) {
        	String phonenumber= db.getPhoneNumber();
        	Log.d("debug", phonenumber);
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
            params.add(new BasicNameValuePair("tag",info_tag));

            try {
                profile_picture = BitmapFactory.decodeStream((InputStream) new URL("http://104.131.141.54/lny_project/" + db.getPhoneNumber() + ".jpg").getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(loginURL, "POST", params);
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	about_text = json.getString(TAG_ABOUT);
                	subject_text = json.getString(TAG_SUBJECT);
                	degree_text = json.getString(TAG_DEGREE);
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        @Override
		protected void onPostExecute(String file_url) {
            if(profile_picture != null){
                ImageView picture = (ImageView) findViewById(R.id.userpicture);
                picture.setImageBitmap(profile_picture);
            }

        	TextView about = (TextView) findViewById(R.id.profileabouttext);
    		about.setText(about_text);
    		
    		TextView subject = (TextView) findViewById(R.id.profilesubject);
    		subject.setText(subject_text);
    		
    		TextView degree = (TextView) findViewById(R.id.profileeducation);
    		degree.setText(degree_text);
        }
	}
}
