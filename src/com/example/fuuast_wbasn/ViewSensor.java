package com.example.fuuast_wbasn;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ViewSensor extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_sensor);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_sensor, menu);
		return true;
	}

}
