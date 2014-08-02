package com.ys.peeraxproject.helper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "android_api";
 
    // Login table name
    private static final String TABLE_LOGIN = "login";
 
    // Login Table Columns names
    private static final String KEY_PHONE = "phone";
    private static final String KEY_NAME = "name";
    private static final String KEY_ABOUT = "userabout";
    private static final String KEY_DEGREE = "userdegree";
    
    public final String LOG_TAG = "DatabaseHandler";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.d(LOG_TAG, "onCreate()");
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
        		+ KEY_PHONE + " INTEGER UNIQUE,"
                + KEY_NAME + " TEXT,"
                + KEY_ABOUT + " TEXT,"
                + KEY_DEGREE + " TEXT" + ")";
      
        db.execSQL(CREATE_LOGIN_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	Log.d(LOG_TAG, "onUpgrade()");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(int phone, String name, String about, String degree) {
    	Log.d(LOG_TAG, "addUser()");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHONE, phone);
        values.put(KEY_NAME, name); 
        values.put(KEY_ABOUT, about); 
        values.put(KEY_DEGREE, degree); 
        
        // Created At
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);

        db.close(); // Closing database connection
    }
     
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
    	Log.d(LOG_TAG, "getUserDetails()");
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
          
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        
        if(cursor.getCount() > 0){
        	user.put("phone", cursor.getString(0));
            user.put("name", cursor.getString(1));
            user.put("created_at", cursor.getString(2));
        }
        cursor.close();
   //     db.close();
        // return user
        return user;
    }
    public String getPhoneNumber(){
    	Log.d(LOG_TAG, "getPhonenumber()");
        String user;
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
          
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        
        user =cursor.getString(0);
      //  cursor.close();
      //  db.close();
        // return user
        return user;
    }
    
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
    	Log.d(LOG_TAG, "getRowCount()");
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
  //      db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
     
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables(){
    	Log.d(LOG_TAG, "resetTables()");
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
     //   db.close();
    }
    public void updateAbout(String phone, String about) {
    	Log.d(LOG_TAG, "updateAbout()");
    	SQLiteDatabase db = this.getWritableDatabase();

    ContentValues args = new ContentValues();
    args.put(KEY_ABOUT, phone);

    db.update(TABLE_LOGIN, args, "id=" + phone, null);
}
}