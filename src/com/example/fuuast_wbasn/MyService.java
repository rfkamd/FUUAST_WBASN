package com.example.fuuast_wbasn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	final Context mCtx = this;
	Handler handler1;
	DataLayer db;
	
	BluetoothAdapter adapter;
	BluetoothServerSocket serverSocket;
	BluetoothSocket socket;
	BluetoothDevice device;
	public static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
	
	
    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart(Intent intent, int startid) {
    	
    	Log.d("rfk","in service");
    	handler1 = new Handler();
    	db = new DataLayer(getApplicationContext());
    	
    	adapter = BluetoothAdapter.getDefaultAdapter();
		adapter.enable();
		
		new connect().execute();
		Toast.makeText(mCtx, "Service Started For Temperature Monitoring", 3000).show();
    	
    }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class connect extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try
			{
        		device = adapter.getRemoteDevice("00:12:10:12:10:57");
    			socket = device.createRfcommSocketToServiceRecord(uuid);
    			socket.connect();
    			
			}catch(Exception ex)
			{
				Log.d("rfk",ex.getMessage());
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void params)
		{
			new readThread().start();
		}
		
	}
	
	private class readThread extends Thread
	{
		public void run()
		{
			try
			{
				OutputStream out = socket.getOutputStream();
//				int interval = Integer.valueOf(db.getTempInterval());
    			while(true)
    			{
    				out.write("TEMP".getBytes());
    				out.flush();
    				BufferedReader reder = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    				String incomingMsg= reder.readLine();
//    				incomingMsg = incomingMsg+ 35;
//    				double temperature = calc(incomingMsg);
    				Log.d("rfk",""+incomingMsg);
    				db.setTemp(incomingMsg);
    				try {
    					
    					
    				    Thread.sleep(2 * 60 * 1000);
    				    
    				} catch(InterruptedException ex) {
    					
    				    Thread.currentThread().interrupt();
    				}
    			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("rfk",e.getMessage());
		}
		}
	}
	
		
	
	public double calc(double temp)
	{
		double y = temp-32;
		double z = (y*(5/9))*2.045;
		double tempC = z*3.22;
		
		return tempC;
	}
		
}

