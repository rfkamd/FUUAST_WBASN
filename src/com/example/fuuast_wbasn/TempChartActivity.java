package com.example.fuuast_wbasn;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TempChartActivity extends Activity {

	
	private GraphicalView mChart;
	DataLayer db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp_chart);
		
		db = new DataLayer(this);
		openChart();	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.temp_chart, menu);
		return true;
	}

	
	 private void openChart(){    	
	    	
	    	int count = 5;
//	    	Date[] dt = new Date[5];
	    	Date[] dt = db.getTempDateTime();
//	    	for(int i=0;i<count;i++){
//	    		GregorianCalendar gc = new GregorianCalendar(2012, 10, i+1);
//	    		dt[i] = gc.getTime();
//	    	}
	    	
	    	/*
	    	 * i will need a Date Array of size of total records..
	    	 * also will need an int array of readings ..
	    	 * dats it ..
	    	 * 
	    	 *visits = dia
	    	 *views= sys
	    	 * 
	    	 * */
	    	
	    	
	    	
	    	
	    	
	    	int[] temp = db.getTemp4Chart();
//	    	int[] sys = db.getSys();    	
//	    	int[] pulse = db.getPulse();
	    	
	    	// Creating TimeSeries for Visits
	    	TimeSeries tempSeries = new TimeSeries("Temperature");    	
	    	
	    	// Creating TimeSeries for Views
//	    	TimeSeries sysSeries = new TimeSeries("Systolic");    	
//	    	
//	    	
//	    	TimeSeries pulseSeries = new TimeSeries("Pulse");  
//	    	
	    	// Adding data to Visits and Views Series
	    	for(int i=0;i<dt.length;i++){
	    		tempSeries.add(dt[i], temp[i]);
//	    		sysSeries.add(dt[i],sys[i]);
//	    		pulseSeries.add(dt[i],pulse[i]);
	    	}
	    	
	    	// Creating a dataset to hold each series
	    	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	    	
	    	// Adding Visits Series to the dataset
	    	dataset.addSeries(tempSeries);
	    	
	    	// Adding Visits Series to dataset
//	    	dataset.addSeries(sysSeries);    
	    	
	    	
//	    	dataset.addSeries(pulseSeries);    
	    	
	    	
	    	// Creating XYSeriesRenderer to customize visitsSeries  	
	    	XYSeriesRenderer tempRenderer = new XYSeriesRenderer();
	    	tempRenderer.setColor(Color.RED);
	    	tempRenderer.setPointStyle(PointStyle.CIRCLE);
	    	tempRenderer.setFillPoints(true);
	    	tempRenderer.setLineWidth(2);
	    	tempRenderer.setDisplayChartValues(true);
	    	
	    	
	    	// Creating XYSeriesRenderer to customize viewsSeries
//	    	XYSeriesRenderer sysRenderer = new XYSeriesRenderer();
//	    	sysRenderer.setColor(Color.GREEN);
//	    	sysRenderer.setPointStyle(PointStyle.CIRCLE);
//	    	sysRenderer.setFillPoints(true);
//	    	sysRenderer.setLineWidth(2);
//	    	sysRenderer.setDisplayChartValues(true);
	    	
	    	
	    	
	    	
//	    	XYSeriesRenderer pulseRenderer = new XYSeriesRenderer();
//	    	pulseRenderer.setColor(Color.BLUE);
//	    	pulseRenderer.setPointStyle(PointStyle.CIRCLE);
//	    	pulseRenderer.setFillPoints(true);
//	    	pulseRenderer.setLineWidth(2);
//	    	pulseRenderer.setDisplayChartValues(true);
	    	
	    	
	    	
	    	// Creating a XYMultipleSeriesRenderer to customize the whole chart
	    	XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
	    	
	    	multiRenderer.setChartTitle("Temperature");
	    	multiRenderer.setXTitle("Date/Time");
	    	multiRenderer.setYTitle("Temp");
	    	multiRenderer.setZoomButtonsVisible(true);    	
	    	
	    	// Adding visitsRenderer and viewsRenderer to multipleRenderer
	    	// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
	    	// should be same
	    	multiRenderer.addSeriesRenderer(tempRenderer);
//	    	multiRenderer.addSeriesRenderer(sysRenderer);
//	    	multiRenderer.addSeriesRenderer(pulseRenderer);
	    	
	    	// Getting a reference to LinearLayout of the MainActivity Layout
	    	LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
	    	
	   		// Creating a Time Chart
	   		mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, multiRenderer,"dd-MMM-yyyy");   		
	   		
	   		multiRenderer.setClickEnabled(true);
	     	multiRenderer.setSelectableBuffer(10);
	     	
	     	// Setting a click event listener for the graph
	     	mChart.setOnClickListener(new View.OnClickListener() {
	     		@Override
	     	    public void onClick(View v) {
	     			Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
	     			
	     			SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();

	     			if (seriesSelection != null) {     				
	     				int seriesIndex = seriesSelection.getSeriesIndex();
	            	  	String selectedSeries="Visits";
	            	  	if(seriesIndex==0)
	            	  		selectedSeries = "Visits";
	            	  	else
	            	  		selectedSeries = "Views";
	            	  	
	            	  	// Getting the clicked Date ( x value )
	          			long clickedDateSeconds = (long) seriesSelection.getXValue();
	          			Date clickedDate = new Date(clickedDateSeconds);
	          			String strDate = formatter.format(clickedDate);
	          			
	          			// Getting the y value 
	          			int amount = (int) seriesSelection.getValue();
	          			
	          			// Displaying Toast Message
	          			Toast.makeText(
	                	       getBaseContext(),
	                	       selectedSeries + " on "  + strDate + " : " + amount ,
	                	       Toast.LENGTH_SHORT).show();
	     			}
	     		}
	  	
	     	});
	     	
	   		// Adding the Line Chart to the LinearLayout
	    	chartContainer.addView(mChart);
	
	
	 }
	
	
	
	
	
	
}
