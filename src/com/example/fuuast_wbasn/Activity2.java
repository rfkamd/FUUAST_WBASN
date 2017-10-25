package com.example.fuuast_wbasn;

import java.io.DataOutputStream;
import java.util.UUID;

import android.os.Bundle;
import android.widget.*;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Activity2 extends Activity implements OnClickListener{

	DataLayer db;
	Button btnOpenBP;
	Button btnStartTempSvc;
	Button btnViewSensor;
	Button btnSaveNumber;
	EditText txtNumber;
	
	BluetoothAdapter adapter;
	BluetoothServerSocket serverSocket;
	BluetoothSocket socket;
	BluetoothDevice device;
	public final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
	broadcastReciever bReceiver = new broadcastReciever();
	IntentFilter filter = new IntentFilter();
	
	//String bpRefreshed = "false";
	public static final String DBNAME = "androidNin1.DB";
	String DB_PATH = "/data/data/androidNin1.Start/databases/";
	String DB_PATH2 = "/sdcard/Download/databases/";
	boolean retval;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity2);
		
		db = new DataLayer(getApplicationContext());
		btnOpenBP = (Button) findViewById(R.id.button1);
		btnStartTempSvc = (Button) findViewById(R.id.button2);
//		btnViewSensor = (Button) findViewById(R.id.button3);
		btnSaveNumber = (Button) findViewById(R.id.button4);
		btnSaveNumber.setText("Settings");
		txtNumber = (EditText) findViewById(R.id.editText1);
		//db.setRefresh(0);
		btnOpenBP.setOnClickListener(this);
		btnStartTempSvc.setOnClickListener(this);
//		btnViewSensor.setOnClickListener(this);
		btnSaveNumber.setOnClickListener(this);
		
		
		
		
		filter.addAction("ALERT_ACTION");
		registerReceiver(bReceiver, filter);
		
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		
		
		
		if(db.getRefreshState() == 0)
		{
			db.setRefresh("0");
		}
		
		if(db.getRefresh().equals("1"))
		{
			//copy db here 
			copyFile();
			db.setRefresh("0");
		}
		
//		txtNumber.setText(db.getNumber());
	}
	
	
	private void copyFile()
	{
		try{
			String comando = "cp -r "+DB_PATH+" /sdcard/Download/";
			//this.getApplicationInfo().dataDir; //" /sdcard/Download/";
			Process suProcess = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
			os.writeBytes(comando + "\n");
			os.flush();
			os.writeBytes("exit\n");
			os.flush();
			try
			{
			 int suProcessRetval = suProcess.waitFor();
			 
			if (255 != suProcessRetval)
			 {
			  // Acceso Root concedido
			  retval = true;
			 }else
			 {
			  // Acceso Root denegado
			  retval = false;
			 }
			}
			catch (Exception ex)
			{
			 Log.w("rfk", ex);
			}
			
		}catch (Exception ex)
		{
			 Log.w("rfk", ex);
		}
	}
	
	public void openBPApp()
	{
		Intent i;
		PackageManager manager = getPackageManager();
		try {
		    i = manager.getLaunchIntentForPackage("androidNin1.Start");
		    if (i == null)
		        throw new PackageManager.NameNotFoundException();
		    i.addCategory(Intent.CATEGORY_LAUNCHER);
		    startActivity(i);
		} catch (PackageManager.NameNotFoundException e) {

		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity2, menu);
		 menu.add("View BP Chart");
		 menu.add("View Temp Chart");
		 menu.add("View Temp");
		 menu.add("View BP");
		 
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
     //TextView txt=(TextView)findViewById(R.id.txt);
//     switch(item.getTitle())
//     {
//     case :
//      Log.d("rfk","you clicked on item "+item.getTitle());
//      
//      
//      return true;
//     case 2:
//    	 Log.d("rfk","you clicked on item "+item.getTitle());
//      return true;
//     
//
//     }
		
		
		if(item.getTitle().equals("View BP"))
		{
			//open new activity
			Toast.makeText(this, "BP option selected", 3000).show();

			Intent intent = new Intent(Activity2.this, BPlist.class);
			intent.putExtra("Type", "BP");
			startActivity(intent);
			
			
		}else if(item.getTitle().equals("View Temp"))
		{
			Toast.makeText(this, "Temp option selected", 3000).show();
			Intent intent = new Intent(Activity2.this, listActivity.class);
			intent.putExtra("Type", "Temp");
			startActivity(intent);
			
			
		}else if(item.getTitle().equals("View BP Chart"))
		{
			Toast.makeText(this, "BP CHART option selected", 3000).show();
			Intent intent = new Intent(Activity2.this, ChartActivity.class);
//			intent.putExtra("Type", "Temp");
			startActivity(intent);
			
			
		}else if(item.getTitle().equals("View Temp Chart"))
		{
			Toast.makeText(this, "BP CHART option selected", 3000).show();
			Intent intent = new Intent(Activity2.this, TempChartActivity.class);
//			intent.putExtra("Type", "Temp");
			startActivity(intent);
			
			
		}
		
		
		
		
		
		
		
		
     return super.onOptionsItemSelected(item);

    }

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		switch(view.getId())
		{
		case R.id.button1:
			
		//	bpRefreshed = true;//keep this in a service
			db.setRefresh("1");
			Log.d("rfk","bp app started");
			openBPApp();
			break;
			
		case R.id.button2:
			Log.d("rfk","svc started");
			Intent service = new Intent(Activity2.this, MyService.class);
			this.startService(service);
			//startService();
			break;
			
//		case R.id.button3:
//			//see sensors list
//			b/reak;	
			
		case R.id.button4:
//			Log.d("rfk","nmber saved");
//			String str = txtNumber.getText().toString();
//			db.setNumber(str);
//			Toast.makeText(getApplicationContext(), "Number Saved", 3000).show();
			Intent intent = new Intent(Activity2.this, Settings.class);
			startActivity(intent);
			break;	
			
		default:
			break;
		}
		
		
	}

}
