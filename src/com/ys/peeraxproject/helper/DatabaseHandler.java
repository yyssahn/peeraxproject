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
    private static final String DATABASE_NAME = "peeraxproject";
 
    // Login table name
    private static final String TABLE_LOGIN = "login";
 
    // Login Table Columns names
    private static final String KEY_PHONE = "phone";
    private static final String KEY_NAME = "name";
    private static final String KEY_ABOUT = "userabout";
    private static final String KEY_DEGREE = "userdegree";

    private static final String KEY_REGID = "regid";
    
    // subject table name 
    private static final String TABLE_SUBJECT = "subject";
    // subject Table Columns names
    private static final String KEY_SUBJECTNAME = "sname";
    
    // subject table name 
    private static final String TABLE_SETTING = "settings";
    // subject Table Columns names
    private static final String KEY_UNIQUE = "uniquenum";
    
    private static final String KEY_MINMONEY = "minmon";
    private static final String KEY_MAXMONEY = "maxmon";
    private static final String KEY_MINRATING = "minrat";
    private static final String KEY_MAXRATING = "maxrat";
    private static final String KEY_MINHOUR = "minhr";
    private static final String KEY_MINEDUCATION = "mined";
    
    
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
                + KEY_REGID + " TEXT,"
                + KEY_DEGREE + " TEXT" + ")";
        String CREATE_SUBJECT_TABLE = "CREATE TABLE " + TABLE_SUBJECT + "("
        		+ KEY_SUBJECTNAME + " TEXT UNIQUE" + ")";
        String CREATE_SETTING_TABLE = "CREATE TABLE " + TABLE_SETTING + "("
        		+ KEY_UNIQUE + " INTEGER UNIQUE,"
        		+ KEY_MINMONEY + " INTEGER DEFAULT 0,"
                + KEY_MAXMONEY + " INTEGER DEFAULT 0,"
                + KEY_MINRATING + " INTEGER DEFAULT 0,"
                + KEY_MAXRATING + " INTEGER DEFAULT 0,"
                + KEY_MINHOUR + " INTEGER DEFAULT 0,"
                + KEY_MINEDUCATION + " INTEGER DEFAULT 0" + ")";
                
        		
        		
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_SUBJECT_TABLE);
        db.execSQL(CREATE_SETTING_TABLE);
        ContentValues values = new ContentValues();
        values.put(KEY_UNIQUE, 0);
        
        
        db.insert(TABLE_SETTING, null, values);
        
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
    public void addUser(int phone, String name, String about, String degree, String regid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHONE, phone);
        values.put(KEY_NAME, name); 
        values.put(KEY_ABOUT, about); 
        values.put(KEY_DEGREE, degree); 
        values.put(KEY_REGID, regid);
        
        // Created At
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);

        db.close(); // Closing database connection
    }
     
    public void addsubject(String sname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(KEY_SUBJECTNAME, sname);  
        
        // Created At
        // Inserting Row
        db.insert(TABLE_SUBJECT, null, values);

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
    public String getFirstSubject(){
    	String subject;
    	String selectQuery = "SELECT  * FROM " + TABLE_SUBJECT;
        
    	  SQLiteDatabase db = this.getReadableDatabase();
          Cursor cursor = db.rawQuery(selectQuery, null);
          // Move to first row
          cursor.moveToFirst();
          
          
          subject =cursor.getString(0);
   
    	
    	return subject;
    }
    
    public void getAllSubject(){
    	String subject;
    	String selectQuery = "SELECT  * FROM " + TABLE_SUBJECT;
        
    	  SQLiteDatabase db = this.getReadableDatabase();
          Cursor cursor = db.rawQuery(selectQuery, null);
          // Move to first row
          if (cursor.moveToFirst()) {
              do {
                  Log.d("something", cursor.getString(0));
              } while (cursor.moveToNext());
          }
    }
    public String getMinMoney(){
    	String setting;
    	String selectQuery = "SELECT  * FROM " + TABLE_SETTING;
        
    	  SQLiteDatabase db = this.getReadableDatabase();
          Cursor cursor = db.rawQuery(selectQuery, null);
          // Move to first row
          cursor.moveToFirst();
          setting = cursor.getString(1);
          Log.d("minmoney", cursor.getString(1));    	
    	return setting;
    }
    public String getMaxMoney(){
    	String setting;
    	String selectQuery = "SELECT  * FROM " + TABLE_SETTING;
        
    	  SQLiteDatabase db = this.getReadableDatabase();
          Cursor cursor = db.rawQuery(selectQuery, null);
          // Move to first row
          cursor.moveToFirst();
         setting = cursor.getString(2);
          Log.d("setting", cursor.getString(2));    	
    	return setting;
    }
    public String getMinRating(){
    	String setting;
    	String selectQuery = "SELECT  * FROM " + TABLE_SETTING;
        
    	  SQLiteDatabase db = this.getReadableDatabase();
          Cursor cursor = db.rawQuery(selectQuery, null);
          // Move to first row
          cursor.moveToFirst();
          setting = cursor.getString(3);
          Log.d("minmoney", cursor.getString(3));    	
    	return setting;
    }
    public String getMaxRating(){
    	String setting;
    	String selectQuery = "SELECT  * FROM " + TABLE_SETTING;
        
    	  SQLiteDatabase db = this.getReadableDatabase();
          Cursor cursor = db.rawQuery(selectQuery, null);
          // Move to first row
          cursor.moveToFirst();
          setting = cursor.getString(4);
          Log.d("minmoney", cursor.getString(4));    	
    	return setting;
    }
    public String getMinHour(){
    	String setting;
    	String selectQuery = "SELECT  * FROM " + TABLE_SETTING;
        
    	  SQLiteDatabase db = this.getReadableDatabase();
          Cursor cursor = db.rawQuery(selectQuery, null);
          // Move to first row
          cursor.moveToFirst();
          setting = cursor.getString(5);
          Log.d("minmoney", cursor.getString(5));    	
    	return setting;
    }
    public String getMinEducation(){
    	String setting;
    	String selectQuery = "SELECT  * FROM " + TABLE_SETTING;
        
    	  SQLiteDatabase db = this.getReadableDatabase();
          Cursor cursor = db.rawQuery(selectQuery, null);
          // Move to first row
          cursor.moveToFirst();
          setting = cursor.getString(6);
          Log.d("minmoney", cursor.getString(6));    	
    	return setting;
    }
    public void setMinEducation(String str){
    	SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MINEDUCATION, str);
        
        db.update(TABLE_SETTING, contentValues, null, null);
    	
    }
    public void setMinMoney(String str){
    	SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MINMONEY, str);
        
        db.update(TABLE_SETTING, contentValues, null, null);
    	
    }
    public void setMaxMoney(String str){
    	SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MAXMONEY, str);
        
        db.update(TABLE_SETTING, contentValues, null, null);
    	
    }
    public void setMinHour(String str){
    	SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MINHOUR, str);
        
        db.update(TABLE_SETTING, contentValues, null, null);
    	
    }
    public void setMinRatin(String str){
    	SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MINRATING, str);
        
        db.update(TABLE_SETTING, contentValues, null, null);
    	
    }
    public void setMaxRating(String str){
    	SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MAXRATING, str);
        
        db.update(TABLE_SETTING, contentValues, null, null);
    	
    }
    
    
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
  //      db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
    public int getSubjectRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SUBJECT;
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
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
     //   db.close();
    }
    public void resetSubjectTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_SUBJECT, null, null);
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