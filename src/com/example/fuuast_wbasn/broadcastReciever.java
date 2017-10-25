package com.example.fuuast_wbasn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class broadcastReciever extends BroadcastReceiver{

	SmsManager sms;
	DataLayer db;
	Bundle bundle;
	public broadcastReciever()
	{
		sms = SmsManager.getDefault();
	}
	
	public void sendSms(String text)
	{
//		sms.sendTextMessage(db.getNumber(), null, text, null, null);
		sms.sendTextMessage("03473097143", null, text, null, null);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		db = new DataLayer(context);
        
		if(intent.getAction().equals("ALERT_ACTION"))
		{
			bundle = intent.getExtras();
			String sensor = bundle.getString("SensorType");
			if(sensor.equals("Temp"))
			{
				String Text = "Temperature Alert!!!"+"\n"+"Temperature:"+bundle.getString("Temp")+" F, \n"+"Time"+bundle.getString("Time");
				sendSms(Text);
				Toast.makeText(context, "Sms Alert Sent", 3000).show();
				
			}else if(sensor.equals("BP"))
			{
				String value = "Blood Pressure Alert!!!"+"\n"+"Diastolic:" + bundle.getString("Dia") +",\n"+ "Systolic:" + bundle.getString("Sys") +",\n"+ "Pulse:" + bundle.getString("Pulse")+",\n"+ "Time:" + bundle.getString("Time");
				sendSms(value);
				Toast.makeText(context, "Sms Alert Sent", 3000).show();
				
			}
		}
		
		
	}


}

