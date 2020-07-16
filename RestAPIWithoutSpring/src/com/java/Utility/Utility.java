package com.java.Utility;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Utility {

	@SuppressWarnings("unused")
	public Properties getProperty() throws SecurityException {
		Properties property = new Properties();
		try {

			InputStream inputStream = null;
			inputStream = getClass().getClassLoader().getResourceAsStream("query.properties");
			//inputStream = getClass().getResource(com.java.util\\query.properties);
			//inputStream=new FileInputStream("C:\\Users\\Acetr\\git\\myGitRepository\\RestAPIWithoutSpring\\resource\\query.properties");
			if (inputStream != null) {
				property.load(inputStream);
				System.out.println(inputStream);
			}else {
				System.out.println("InputStream is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return property;
	}
	
	public Integer generateSerialId() {
		Integer uniqueNumber=0;
		try {
			String timestamp = new SimpleDateFormat("ddMMyyyy").format(new Date());
			uniqueNumber = (int) (Integer.parseInt(timestamp) + Math.floor(Math.random() * 100)); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return uniqueNumber;
		
	}
}
