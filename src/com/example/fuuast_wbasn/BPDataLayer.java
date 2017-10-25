package com.example.fuuast_wbasn;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BPDataLayer  extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "androidNin1";				
    //chng db name 
    public static final String DBNAME = "androidNin1.DB";
	String DB_PATH = "/data/data/androidNin1.Start/databases/";
	String DB_PATH2 = "/sdcard/Download/databases/";
	String tag = "rfk";
	Context context;
	
	public BPDataLayer(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		context = ctx;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	//get values
	
	public Cursor getBPData()
	{
		Cursor crsr = null;
		try{
				File dbfile = new File(DB_PATH2+DBNAME);
				if(dbfile.exists())
				{
					SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
					Log.d(tag,"db opened");
					crsr = db.rawQuery("select * from tb_bpmeasureresult", null);
					Log.d(tag,"db quried");
					
					while(crsr.moveToNext())
					{
						Log.d(tag,crsr.getColumnName(2)+":"+crsr.getString(2)+","+
								  crsr.getColumnName(3)+":"+crsr.getString(3)+","+
								  crsr.getColumnName(4)+":"+crsr.getString(4));
						
					}
					
					db.close();
				}
			
		}catch(Exception ex)
		{
			Log.d("rfk",ex.getMessage());
		}
		
		
		return crsr;
	}
	
	public void get1Value()
	{
		Cursor crsr = null;
		
		
		
		try{
				File dbfile = new File(DB_PATH2+DBNAME);
				if(dbfile.exists())
				{
					SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
					Log.d(tag,"db opened");
					crsr = db.rawQuery("select * from tb_bpmeasureresult order by bpMeasureID desc LIMIT 1", null);
					Log.d(tag,"db quried");
					
					if(crsr.moveToNext())
					{
						Log.d(tag,crsr.getColumnName(2)+":"+crsr.getString(2)+","+
								  crsr.getColumnName(3)+":"+crsr.getString(3)+","+
								  crsr.getColumnName(4)+":"+crsr.getString(4));
						
						if(Integer.valueOf(crsr.getString(crsr.getColumnIndex("dia"))) > 80 || Integer.valueOf(crsr.getString(crsr.getColumnIndex("dia"))) > 120)
						{
							 Intent intent = new Intent();
					    	 intent.setAction("ALERT_ACTION");
					    	 intent.putExtra("SensorType", "BP");
					    	 intent.putExtra("Dia", crsr.getString(crsr.getColumnIndex("dia")));
					    	 intent.putExtra("Sys", crsr.getString(crsr.getColumnIndex("sys")));
					    	 intent.putExtra("Pulse", crsr.getString(crsr.getColumnIndex("pulse")));
					    	 intent.putExtra("Time", crsr.getString(crsr.getColumnIndex("bpMeasureDate")));
					    	 context.sendBroadcast(intent);
						}
					}
					
					db.close();
				}
			
		}catch(Exception ex)
		{
			Log.d("rfk",ex.getMessage());
		}
		
		
		
	}
	
	
	
	
	
	
	public int[] getDia()
	{
		Cursor crsr = null;
		int[] dia = null;
		
		try{
			File dbfile = new File(DB_PATH2+DBNAME);
			if(dbfile.exists())
			{
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
				Log.d(tag,"db opened");
				crsr = db.rawQuery("select dia from tb_bpmeasureresult", null);
				Log.d(tag,"db quried");
				dia = new int[crsr.getCount()];
				for(int i = 0;i<dia.length;i++)
				{
					dia[i] = crsr.getInt(crsr.getColumnIndex("dia"));
					crsr.moveToNext();
//					if(Integer.valueOf(crsr.getString(crsr.getColumnIndex("dia"))) )
//					{
//						 Intent intent = new Intent();
//				    	 intent.setAction("ALERT_ACTION");
//				    	 intent.putExtra("SensorType", "BP");
//				    	 intent.putExtra("Dia", crsr.getString(crsr.getColumnIndex("dia")));
//				    	 intent.putExtra("Sys", crsr.getString(crsr.getColumnIndex("sys")));
//				    	 intent.putExtra("Pulse", crsr.getString(crsr.getColumnIndex("pulse")));
//				    	 intent.putExtra("Time", crsr.getString(crsr.getColumnIndex("bpMeasureDate")));
//				    	 context.sendBroadcast(intent);
//					}
				}
				
				db.close();
			}
		
	}catch(Exception ex)
	{
		Log.d("rfk",ex.getMessage());
	}
		
		
		return dia;
		
	}
	
	
	
	public int[] getSys()
	{
		Cursor crsr = null;
		int[] sys = null;
		
		try{
			File dbfile = new File(DB_PATH2+DBNAME);
			if(dbfile.exists())
			{
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
				Log.d(tag,"db opened");
				crsr = db.rawQuery("select sys from tb_bpmeasureresult", null);
				Log.d(tag,"db quried");
				sys = new int[crsr.getCount()];
				for(int i = 0;i<sys.length;i++)
				{
					crsr.moveToNext();
					
					sys[i] = crsr.getInt(crsr.getColumnIndex("sys"));
					
//					if(Integer.valueOf(crsr.getString(crsr.getColumnIndex("dia"))) )
//					{
//						 Intent intent = new Intent();
//				    	 intent.setAction("ALERT_ACTION");
//				    	 intent.putExtra("SensorType", "BP");
//				    	 intent.putExtra("Dia", crsr.getString(crsr.getColumnIndex("dia")));
//				    	 intent.putExtra("Sys", crsr.getString(crsr.getColumnIndex("sys")));
//				    	 intent.putExtra("Pulse", crsr.getString(crsr.getColumnIndex("pulse")));
//				    	 intent.putExtra("Time", crsr.getString(crsr.getColumnIndex("bpMeasureDate")));
//				    	 context.sendBroadcast(intent);
//					}
				}
				
				db.close();
			}
		
	}catch(Exception ex)
	{
		Log.d("rfk",ex.getMessage());
	}
		
		
		return sys;
		
	}
	
	public int[] getPulse()
	{
		Cursor crsr = null;
		int[] pulse = null;
		
		try{
			File dbfile = new File(DB_PATH2+DBNAME);
			if(dbfile.exists())
			{
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
				Log.d(tag,"db opened");
				crsr = db.rawQuery("select pulse from tb_bpmeasureresult", null);
				Log.d(tag,"db quried");
				pulse = new int[crsr.getCount()];
				for(int i = 0;i<pulse.length;i++)
				{
					crsr.moveToNext();
					
					pulse[i] = crsr.getInt(crsr.getColumnIndex("pulse"));

				}
				
				db.close();
			}
		
	}catch(Exception ex)
	{
		Log.d("rfk",ex.getMessage());
	}
		
		
		return pulse;
		
	}
	
	
	
	public Date[] getDateTime()
	{
		Cursor crsr = null;
		Date[] date = null;
		
		try{
			File dbfile = new File(DB_PATH2+DBNAME);
			if(dbfile.exists())
			{
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
				Log.d(tag,"db opened");
				crsr = db.rawQuery("select bpMeasureDate from tb_bpmeasureresult", null);
				Log.d(tag,"db quried");
				date = new Date[crsr.getCount()];
				for(int i = 0;i<date.length;i++)
				{	
					crsr.moveToNext();
					
						String string = crsr.getString(0);
						
						
						date[i] = (Date) new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(string);
						
					
				}
				
				db.close();
			}
		
	}catch(Exception ex)
	{
		Log.d("rfk",ex.getMessage());
	}
		
		
		return date;
		
	}
	
	
	
}
