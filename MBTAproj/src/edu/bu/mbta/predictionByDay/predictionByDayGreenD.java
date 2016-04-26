package edu.bu.mbta.predictionByDay;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import edu.bu.mbta.predictionTable.createTable;
import edu.bu.mbta.predictionTable.predictionByDate;

public class predictionByDayGreenD extends Thread{
	
	AmazonDynamoDBClient dynamoDB;
	public predictionByDayGreenD(AmazonDynamoDBClient DB) {
		dynamoDB = DB;
	}

	public void run() {
		try {
			execute(dynamoDB);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void execute(AmazonDynamoDBClient dynamoDB) throws Exception {
		
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(yourmilliseconds);
		String date = sdf.format(resultdate);
		System.out.println(date);
		date = date.substring(0, date.indexOf(":"));
		String month = date.substring(0,date.indexOf(" "));
		System.out.println(month);
		date = date.substring(date.indexOf(" ")+1);
		String day = date.substring(0, date.indexOf(","));
		System.out.println(day);
		String year = date.substring(date.lastIndexOf(",")+1, date.lastIndexOf(" "));
		System.out.println(year);
		String hour = date.substring(date.lastIndexOf(" ")+1);
		System.out.println(hour);
		
		
		
		String tableName = "PredictionsByStop_GreenD_"+month + "."+ day +"."+year;		
		createTable.create(dynamoDB, tableName);
		while (true) {
			predictionByDate.update(dynamoDB, tableName, "Green-D");
			Thread.sleep(1000L * 60L *30L);
		}
	}
}
