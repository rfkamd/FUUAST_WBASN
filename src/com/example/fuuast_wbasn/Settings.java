package com.example.fuuast_wbasn;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity implements OnClickListener {

	
	EditText txtPhone;
	EditText txtTempInterVal;
	EditText txtTempLimit;
	EditText txtDiaHigh;
	EditText txtDiaLow;
	EditText txtSysHigh;
	EditText txtSysLow;
	Button SaveNumber;
	Button SaveSettings;
	DataLayer db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		db = new DataLayer(getApplicationContext());
		txtPhone = (EditText) findViewById(R.id.editText1);
		txtTempInterVal = (EditText) findViewById(R.id.editText2);
		txtTempLimit = (EditText) findViewById(R.id.editText3);
		txtDiaHigh = (EditText) findViewById(R.id.editText4);
		txtDiaLow = (EditText) findViewById(R.id.editText5);
		txtSysHigh = (EditText) findViewById(R.id.editText6);
		txtSysLow = (EditText) findViewById(R.id.editText7);
		SaveNumber = (Button) findViewById(R.id.button1);
		SaveSettings = (Button) findViewById(R.id.button2);
		
		SaveNumber.setOnClickListener(this);
		SaveSettings.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String TempLimit, diaHighLimit, diaLowLimit, sysHighLimit, sysLowLimit;
		
		switch(v.getId())
		{
		case R.id.button1:
			db.setNumber(txtPhone.getText().toString());
			Toast.makeText(getApplicationContext(), " Saved ", 3000).show();
			break;
			
		case R.id.button2:
			
			TempLimit = txtPhone.getText().toString();
			diaHighLimit = txtDiaHigh.getText().toString();
			diaLowLimit = txtDiaLow.getText().toString();
			sysHighLimit = txtSysHigh.getText().toString();
			sysLowLimit = txtSysLow.getText().toString();
			db.Setting(TempLimit, diaHighLimit, diaLowLimit, sysHighLimit, sysLowLimit);
			Toast.makeText(getApplicationContext(), " Saved ", 3000).show();
			break;
		}
	}

}
