package com.java.DB.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.java.Model.Accounts;
import com.java.Model.User;

public class HSQLConfig {

	@SuppressWarnings("static-access")
	public static List<User> executeQueryForUser(String getQuery) throws SQLException {
		List<User> usersList = new ArrayList<User>();
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			Connection c = DriverManager.getConnection("jdbc:hsqldb:file:e:\\HSQL\\myDB;hsqldb.lock_file=false", "SA", "SA");
			PreparedStatement preparedStatement  = c.prepareStatement(getQuery);
			if (getQuery.contains("select")) {
				ResultSet s = preparedStatement.executeQuery();
				while (s.next()) {
					User user = new User();
					user.setUserName(s.getString(1));
					user.setUserId(s.getString(2));
					usersList.add(user);
				}
				s.close();
			} else {
				User user = new User();
				int update = preparedStatement.executeUpdate();
				user.setStatus("Success");
				usersList.add(user);
				System.out.println(update);
			}

			preparedStatement.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usersList;
	}

	@SuppressWarnings("static-access")
	public static List<Accounts> executeQueryForAccounts(String getQuery) throws SQLException {
		List<Accounts> accountsList = new ArrayList<Accounts>();
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			
			Connection c = DriverManager.getConnection("jdbc:hsqldb:file:e:\\HSQL\\myDB;hsqldb.lock_file=false", "SA", "SA");
			PreparedStatement preparedStatement  = c.prepareStatement(getQuery);
			
			if (getQuery.contains("select")) {
				ResultSet s = preparedStatement.executeQuery();
				while (s.next()) {
					Accounts account = new Accounts();
					account.setBalance(Integer.parseInt(s.getString(4)));
					account.setEmailId(s.getString(2));
					account.setUserId(s.getString(3));
					account.setUuid(Integer.parseInt(s.getString(1)));
					accountsList.add(account);
				}
				s.close();
			} else {
				Accounts account = new Accounts();
				int update = preparedStatement.executeUpdate();
				account.setStatus("Success");
				accountsList.add(account);
			}
			preparedStatement.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accountsList;
	}

}
