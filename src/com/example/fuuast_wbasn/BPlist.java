package com.example.fuuast_wbasn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class BPlist extends Activity {

	
	
	
	 ListView lv;
	 TextView location,user;
//	 String DBNAME = "androidNin1.db";
	 DataLayer db;
	 BPDataLayer db2;
	 String TABLE = "DeviceInfo";
//	 List<TempInfo> deviceList ;
	 List<BP> deviceList;
	// ArrayList<HashMap<String, String>> Templist;
	 ArrayList<HashMap<String, String>> list;
	 ArrayList<HashMap<String, String>> list4BP;
	 ArrayList<BP> BPlist;
	 SimpleAdapter adapter;
	 Context context = this;
//	 UdpBroadcast main;
//	 String Action  = "My_Action_Update_Table";
	// Bundle bundle;
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bplist);
//		 ProgressBar progressBar = new ProgressBar(this);
//	        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
//	        progressBar.setIndeterminate(true);
//	        getListView().setEmptyView(progressBar);
//	        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//	        root.addView(progressBar);
		
		//bundle = new Intent().getExtras();
			 
			db = new DataLayer(context);
			db2 = new BPDataLayer(context);
			
	        String type = getIntent().getStringExtra("Type");
	        
	        lv = (ListView) findViewById(R.id.listView1);
	        if(type.equals("Temp"))
	        {
	        	//call temperature list view
	        	 
	        	
	        }else if(type.equals("BP"))
	        {
	        	//call bp list view
//	        	loader4BP();
	        	
	        	loader();
	        }
	        
	        
	        
//	        location = (TextView) findViewById(R.id.textView1);
//	        user = (TextView) findViewById(R.id.textView2);
//	        main = new UdpBroadcast();
	        
	        
	       
	    
	        lv.setOnItemClickListener(new OnItemClickListener() {

	        	
	        	/*
	        	 * 
	        	 * here in this section we are showing a dialogue where we are editing value 
	        	 * 
	        	 * but xml ko edit kr k we can just display the data ..
	        	 * 
	        	 */
	        	
	        	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						final int position, long id) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "item clicked", 3000).show();
					
					final Dialog dialog = new Dialog(context);
					
					dialog.setContentView(R.layout.prompt);
					dialog.setTitle("Values");
					
					
					
					TextView txtDia = (TextView) dialog.findViewById(R.id.textView2);
					TextView txtSys = (TextView) dialog.findViewById(R.id.textView4);
					TextView txtPulse = (TextView) dialog.findViewById(R.id.textView6);
					TextView txtTime = (TextView) dialog.findViewById(R.id.textView8);
					
					txtDia.setText(list.get(position).get("dia"));
					txtSys.setText(list.get(position).get("sys"));
					txtPulse.setText(list.get(position).get("pulse"));
					txtTime.setText(list.get(position).get("DateTime"));
					
					dialog.show();

				}
				
				
			});
	        
	}

	
	private void loader()
	{
		
		
		//loader for temperature
	//	Cursor cursor = db.getTemp();
	    getRows();
//		@SuppressWarnings("deprecation")
//		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,R.layout.row, cursor, new String []{"Temp","Time"}, new int []{R.id.textView1, R.id.textView2},0);
		
	//	adapter = new SimpleAdapter(context, , resource, from, to)
        adapter = new SimpleAdapter(this, list, R.layout.row2, new String[] {"DateTime"}, new int[] {R.id.textView1});
        lv.setAdapter(adapter);
        
	}//---
	
	
//	private void loader4BP()
//	{
//		
//		
//		//loader for temperature
//	//	Cursor cursor = db.getTemp();
//	    getRows4BP();
////		@SuppressWarnings("deprecation")
////		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,R.layout.row, cursor, new String []{"Temp","Time"}, new int []{R.id.textView1, R.id.textView2},0);
//		
//	//	adapter = new SimpleAdapter(context, , resource, from, to)
//        adapter = new SimpleAdapter(this, list4BP, R.layout.row2, new String[] {"bpMeasureDate"}, new int[] {R.id.textView1});
//        lv.setAdapter(adapter);
//        
//	}//---
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	
	public void getRows()
	{
		try{
//			SQLiteDatabase  mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
//            Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  TABLE, null);
//            mydb.rawQuery("Select IP,Location,User from "+TABLE+" Where DeviceCode = ?", new String[]{"DU"});
			
		
			
			
            Log.d("rfk","COUNT : " + db.getTemp().toString());
            deviceList = new ArrayList();
            //deviceList =
            deviceList = compileList(db2.getBPData());
            
		}catch(Exception ex){}
	}
	
//	public void getRows4BP()
//	{
//		try{
////			SQLiteDatabase  mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
////            Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  TABLE, null);
////            mydb.rawQuery("Select IP,Location,User from "+TABLE+" Where DeviceCode = ?", new String[]{"DU"});
//			
//		
//			
//			
//            Log.d("rfk","COUNT : " + db2.getBPData().toString());
//            deviceList4BP = new ArrayList();
//            //deviceList =
//            deviceList4BP = compileList4BP(db2.getBPData());//add the bp getting data here .. 
//            
//		}catch(Exception ex){}
//	}

//	public List<BP> compileList4BP(Cursor cursor)
//	   {
//		List<BP> tempList = new ArrayList();
//		
//		 //  list =  new ArrayList<HashMap<String><String>>();
//		   
//		   list = new ArrayList<HashMap<String, String>>();
//		   if (cursor.moveToFirst()) {
//		        do {
//		        	
//		        	
//		        	HashMap<String, String> map = new HashMap<String, String>();
//		        	map.put("Temp", cursor.getString((cursor.getColumnIndex("dia"))));
//		        	map.put("DateTime", cursor.getString(cursor.getColumnIndex("sys")));
//		        	map.put("IP", cursor.getString(cursor.getColumnIndex("pulse")));
//		        	map.put("DateTime", cursor.getString(cursor.getColumnIndex("bpMeasureDate")));
//		        	
//		        	list4BP.add(map);
//		        	
//		        	BP dList = new BP();
//		        	dList.setDia(cursor.getString(cursor.getColumnIndex("dia")));
//		           	dList.setSys(cursor.getString(cursor.getColumnIndex("sys")));
//		            dList.setPulse((cursor.getString(cursor.getColumnIndex("pulse"))));
//		           	dList.setDateTime(cursor.getString(cursor.getColumnIndex("bpMeasureDate")));
//		           
//		            tempList.add(dList);
//		        } while (cursor.moveToNext());
//		    }
//		   
//		   for(BP temp:tempList)
//		   {
//			  // Log.d("rfk","ListVIEW:"+temp.getTemp()+","+temp.getDateTime());
//		   }
//		   
//		  return tempList;
//	   }
	
	
	public List<BP> compileList(Cursor cursor)
	   {
		List<BP> tempList = new ArrayList();
		
		  // list =  new ArrayList<HashMap<String>();
		   
		   list = new ArrayList<HashMap<String, String>>();
		   if (cursor.moveToFirst()) {
		        do {
		        	
		        	
		        	HashMap<String, String> map = new HashMap<String, String>();
		        	map.put("sys", cursor.getString((cursor.getColumnIndex("sys"))));
		        	map.put("dia", cursor.getString(cursor.getColumnIndex("dia")));
		        	map.put("pulse", cursor.getString(cursor.getColumnIndex("pulse")));
		        	map.put("DateTime", cursor.getString(cursor.getColumnIndex("bpMeasureDate")));
		        	
		        	
		        	list.add(map);
		        	
		        	BP dList = new BP();
		        	dList.setSys(cursor.getString(cursor.getColumnIndex("sys")));
		           	dList.setDia(cursor.getString(cursor.getColumnIndex("dia")));
		            dList.setPulse((cursor.getString(cursor.getColumnIndex("pulse"))));
		           	dList.setDateTime(cursor.getString(cursor.getColumnIndex("bpMeasureDate")));
		           
		            tempList.add(dList);
		        } while (cursor.moveToNext());
		    }
		   
		   for(BP temp:tempList)
		   {
			   Log.d("rfk","ListVIEW:"+temp.getSys()+","+temp.getDateTime());
		   }
		   
		  return tempList;
	   }
	
	
	
	
	
	
}
