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
			// inputStream = getClass().getResource(com.java.util\\query.properties);
			// inputStream=new
			// FileInputStream("C:\\Users\\Acetr\\git\\myGitRepository\\RestAPIWithoutSpring\\resource\\query.properties");
			if (inputStream != null) {
				property.load(inputStream);
				System.out.println(inputStream);
			} else {
				System.out.println("InputStream is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return property;
	}

	public Integer generateSerialId() {
		Integer uniqueNumber = 0;
		try {
			String timestamp = new SimpleDateFormat("ddMMyyyy").format(new Date());
			String tempValue = timestamp.substring(2) + new Double(Math.floor(Math.random() * 100)).intValue();
			uniqueNumber = Integer.parseInt(tempValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uniqueNumber;

	}

	public boolean testIfDigitPresent(String inputString) {
		try {
			for (char val : inputString.toCharArray()) {
				if (Character.isDigit(val)) {
					return true;
				}
			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean checkValidEmail(String emailString) {
		try {
			if (emailString.contains("@") && emailString.contains(".")) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
