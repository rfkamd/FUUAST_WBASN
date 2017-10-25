package com.example.fuuast_wbasn;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	Intent intent;
	BluetoothAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);	// Removes title bar
      	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      	
      	
		setContentView(R.layout.activity_main);
		
		intent = new Intent(MainActivity.this, Activity2.class);
		adapter = BluetoothAdapter.getDefaultAdapter();
		adapter.enable();
		IntentLauncher launcher = new IntentLauncher();
        launcher.start();
	}
	
	private class IntentLauncher extends Thread {
	    
		@Override
    	/**
    	 * Sleep for some time and than start new activity.
    	 */
		public void run() {
    		try {
            	// Sleeping
    			Thread.sleep(3500);
            } catch (Exception e) {
            	Log.e("rfk", e.getMessage());
            }
            
            // Start main activity
          	// intent = new Intent(MainActivity.this, Activity2.class);
          	MainActivity.this.startActivity(intent);
          	MainActivity.this.finish();
    	}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
