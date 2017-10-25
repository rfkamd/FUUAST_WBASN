package com.example.fuuast_wbasn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataLayer extends SQLiteOpenHelper{

//	SQLiteDatabase db;
	
	private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "WBASN";
    Context context;
	
	public DataLayer(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = ctx;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String query = "CREATE TABLE IF NOT EXISTS Temperature (ID INTEGER PRIMARY KEY AUTOINCREMENT, Temp TEXT, Time TEXT"+")";
		
		String query2 = "CREATE TABLE IF NOT EXISTS Flag (ID INTEGER PRIMARY KEY AUTOINCREMENT, flag TEXT)";
		String query3 = "CREATE TABLE IF NOT EXISTS Phone_Number (Number TEXT)";
		String query4 = "CREATE TABLE IF NOT EXISTS Settings (ID INTEGER PRIMARY KEY AUTOINCREMENT, Interval4Temp INTEGER, TempAlertLimit TEXT, BP_DIA_HIGH TEXT, BP_DIA_LOW TEXT,BP_SYS_HIGH TEXT,BP_SYS_LOW TEXT)";
		
		
		db.execSQL(query);
		db.execSQL(query2);
		db.execSQL(query3);
		db.execSQL(query4);
		//setRefresh(0);
		//db.rawQuery("insert into Flag (flag)values(0)", null);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("DROP TABLE IF EXISTS Number");
		 db.execSQL("DROP TABLE IF EXISTS Flag");
		 db.execSQL("DROP TABLE IF EXISTS Temperature");
		 db.execSQL("DROP TABLE IF EXISTS Settings");
	        // create fresh books table
	        this.onCreate(db);
	}
	
	public void Setting(String TempLimit, String diaHighLimit, String diaLowLimit, String sysHighLimit, String sysLowLimit)
	{
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor crsr = db.rawQuery("Select * from Settings", null);
			
			ContentValues values = new ContentValues();
			values.put("TempAlertLimit", TempLimit);
			values.put("BP_DIA_HIGH", diaHighLimit);
			values.put("BP_DIA_LOW", diaLowLimit);
			values.put("BP_SYS_HIGH", sysHighLimit);
			values.put("BP_SYS_LOW", sysLowLimit);
			
			if(!crsr.moveToNext())
			{
				db.insert("Settings", null, values);
				
			}else
			{
				db.update("Settings", values, "ID = ?", new String[] {"1"});
			}
			
		}catch(Exception ex)
		{
			Log.d("rfk",ex.getMessage());
		}
	}
	
	public String getTempInterval()
	{
		Cursor cursor = null;
		String interval = null;
		try
		{
			SQLiteDatabase db = this.getReadableDatabase();
			cursor = db.rawQuery("Select * from Settings", null);
			if(cursor.moveToNext())
			{
				interval = cursor.getString(cursor.getColumnIndex("Interval4Temp"));
			}
			
		}catch(Exception ex)
		{
			Log.d("rfk",ex.getMessage());
		}
		
		return interval;
	}
	
	
	public Cursor getSettings()
	{
		Cursor cursor = null;
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			cursor = db.rawQuery("Select * from Settings", null);
			
			
		}catch(Exception ex)
		{
			Log.d("rfk",ex.getMessage());
		}
		
		return cursor;
	}
	
	
	
	
	
	public Cursor getTemp(){
		
		ArrayList list = null;
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	 
	    Cursor cursor = db.rawQuery("Select * from Temperature", null);
	            
	 
	    while(cursor.moveToNext())
	    {
	    	//list.add(cursor.getString(0));
	    	Log.d("rfk",cursor.getString(1)+":"+cursor.getString(2));
	    }    
	    db.close();
	        
	    
	   // temp = cursor.getString(0);
	    
	    return cursor;
	 
	   
	}
	
	private String getTime()
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss");
		String strDate = sdf.format(c.getTime());
		return strDate;
	}
	

	public void setTemp(String temp){
    //  Log.d("addBook", book.toString());
      // 1. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();
      String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
      // 2. create ContentValues to add key "column"/value
      ContentValues values = new ContentValues();
      values.put("Temp", temp);
      values.put("Time", getTime());
      //values.put(NUMBER, nmbr); // get title 
     // values.put(KEY_AUTHOR, book.getAuthor()); // get author

      // 3. insert
      db.insert("Temperature", // table
              null, //nullColumnHack
              values); // key/value -> keys = column names/ values = column values
      
      if(Float.valueOf(temp) > 99.0 )
      {
    	  Intent intent = new Intent();
    	  intent.setAction("ALERT_ACTION");
    	  intent.putExtra("SensorType", "Temp");
    	  intent.putExtra("Temp", temp);
    	  intent.putExtra("Time", getTime());
    	  context.sendBroadcast(intent);
      }
      
      
      Cursor c = db.rawQuery("select * from Temperature", null);
      while(c.moveToNext())
      {
    	  Log.d("rfk",c.getString(c.getColumnIndex("Temp"))+":"+c.getString(c.getColumnIndex("Time")));
    	  
      }
      
      
      // 4. close
      db.close(); 
  }
	
	
	public void setRefresh(String refresh){
	    
		Log.d("rfk","in setRefresh");
	      SQLiteDatabase db1 = this.getWritableDatabase();

	      
	      ContentValues values = new ContentValues();
	      values.put("flag", refresh);
	      
//	      db.rawQuery("insert into Flag(flag) values ("+refresh+")", null);
	      
	      db1.insert("Flag", // table
	              null, //nullColumnHack
	              values); // key/value -> keys = column names/ values = column values
	     Cursor sr =  db1.rawQuery("select * from Flag ORDER BY ID DESC LIMIT 1", null);
	     if(sr != null)
	     { 
	    	 sr.moveToNext();
	    	 Log.d("rfk",sr.getString(1));
	     }
	      
	      // 4. close
	      db1.close(); 
	  }
	
	
	public String getRefresh(){
		
		String refresh = "0";
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	 
	    Cursor cursor = db.rawQuery("Select * from Flag ORDER BY ID DESC LIMIT 1", null);
	            
	 
	    if (cursor.moveToNext())
	    {
	    	//refresh = Integer.valueOf(cursor.getString(0));
	    	refresh = cursor.getString(1);
	    	Log.d("rfk",refresh);
	    }
	        
	    db.close();
	   // temp = cursor.getString(0);
	    
	    return refresh;
	 
	   
	}
	
		
	public int getRefreshState(){
			
			int refresh = 0;
		 
		    SQLiteDatabase db = this.getReadableDatabase();
		 
		 
		    Cursor cursor = db.rawQuery("Select * from Flag", null);
		            
		 
		    if (cursor.moveToNext())
		    {
		    	//refresh = Integer.valueOf(cursor.getString(0));
		    	db.close();
		    	return 1;
		    	
		    }else 
		    {
		    	db.close();
		    	return 0;
		    }
		        
		    
		   // temp = cursor.getString(0);
		    
		    //return refresh;
		 
		   
		}
	
	public void setNumber(String number)
	{
		SQLiteDatabase db = this.getWritableDatabase();

	      // 2. create ContentValues to add key "column"/value
	      ContentValues values = new ContentValues();
	      values.put("Number", number);
	      //values.put(NUMBER, nmbr); // get title 
	     // values.put(KEY_AUTHOR, book.getAuthor()); // get author

	      // 3. insert
	      db.insert("Phone_Number", // table
	              null, //nullColumnHack
	              values); // key/value -> keys = column names/ values = column values
	      Log.d("rfk", getNumber());
	      // 4. close
	      db.close(); 
	}
	
	
	public String getNumber()
	{
		String number = null;
		 
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	 
	    Cursor cursor = db.rawQuery("Select * from Phone_Number", null);
	            
	 
	    if (cursor.moveToNext())
	    {
	    	number = cursor.getString(0);
	    }    
	    db.close();
	        
	    
	   // temp = cursor.getString(0);
	    
	    return number;
	}
	
	
	
	
	//----
	
	
		public int[] getTemp4Chart(){
			
			int[] temp = null;
		 
		    try
		    {
		    	
		    	SQLiteDatabase db = this.getReadableDatabase();
				 
				 
			    Cursor cursor = db.rawQuery("Select Temp from Temperature", null);
			    temp = new int[cursor.getCount()];       
			    for(int i = 0;i<temp.length;i++)
				{
			    	cursor.moveToNext();
					
					temp[i] = cursor.getInt(0);
					

				}
			        
			    db.close();
			 
		    	
		    	
		    }catch(Exception ex){}
		    
		   // temp = cursor.getString(0);
		    
		    return temp;
		 
		   
		}	
	
	
		public Date[] getTempDateTime(){
			
			Date[] date = null;
			
			try{
				
				 
			    SQLiteDatabase db = this.getReadableDatabase();
			 
			 
			    Cursor cursor = db.rawQuery("Select * from Temperature", null);
			    date = new Date[cursor.getCount()];        
			 
			    for(int i = 0;i<date.length;i++)
				{	
			    	cursor.moveToNext();
					
						String string = cursor.getString(cursor.getColumnIndex("Time"));
						//string = string.replace("January", "Jan").replace("PM", "");
						
						date[i] = (Date) new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss").parse(string);
						
					
				}    
//			    db.close();
			}catch(Exception ex)
			{
				Log.d("rfk",ex.getMessage());
			}
		        
		    
		  
		    
		    return date;
		 
		   
		}



}
