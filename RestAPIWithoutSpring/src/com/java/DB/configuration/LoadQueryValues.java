package com.java.DB.configuration;

import java.util.Arrays;
import java.util.Properties;

import com.java.Utility.*;

public class LoadQueryValues {
	public static String GET_ALL_USERS = null;
	public static String GET_SPECIFIC_USER = null;
	public static String DELETE_SPECIFIC_USER = null;
	public static String UPDATE_SPECIFIC_USER = null;
	public static String GET_ALL_ACCOUNTS = null;
	public static String GET_SPECIFIC_ACCOUNT = null;
	public static String DELETE_SPECIFIC_ACCOUNT = null;
	public static String UPDATE_SPECIFIC_ACCOUNT = null;
	public static String CREATE_NEW_ACCOUNT=null;
	public static String CREATE_NEW_USER=null;
	public static void loadPropertyValues() {
		try {
			Properties properties = new Properties();
			properties = new Utility().getProperty();
			if (properties != null) {
				Arrays.asList(properties);
			}
			GET_ALL_USERS = properties.getProperty("GET_ALL_USERS");
			GET_SPECIFIC_USER = properties.getProperty("GET_SPECIFIC_USER");
			DELETE_SPECIFIC_USER = properties.getProperty("DELETE_SPECIFIC_USER");
			UPDATE_SPECIFIC_USER = properties.getProperty("UPDATE_SPECIFIC_USER");
			GET_ALL_ACCOUNTS = properties.getProperty("GET_ALL_ACCOUNTS");
			GET_SPECIFIC_ACCOUNT = properties.getProperty("GET_SPECIFIC_ACCOUNT");
			DELETE_SPECIFIC_ACCOUNT = properties.getProperty("DELETE_SPECIFIC_ACCOUNT");
			UPDATE_SPECIFIC_ACCOUNT = properties.getProperty("UPDATE_SPECIFIC_ACCOUNT");
			CREATE_NEW_ACCOUNT = properties.getProperty("CREATE_NEW_ACCOUNT");
			CREATE_NEW_USER = properties.getProperty("CREATE_NEW_USER");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
