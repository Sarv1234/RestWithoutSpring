package com.javaRest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.java.DB.configuration.HSQLConfig;
import com.java.DB.configuration.LoadQueryValues;
import com.java.Model.User;
import com.java.Model.UserHandler;
import com.java.Utility.Utility;

@Path("/users")
public class UserService {

	@GET
	@Path("/createTable")
	@Produces("application/json")
	public UserHandler createUserTable() {
		UserHandler userHandler = new UserHandler();
		List<User> userList = new ArrayList<User>();
		try {
			LoadQueryValues.loadPropertyValues();
			String checkIfUserPresentQuery = LoadQueryValues.CHECK_IF_TABLE_EXISTS + "USER'";
			userList = HSQLConfig.executeQueryForUser(checkIfUserPresentQuery);
			if (userList.size() > 0) {
				userHandler.setErrorCode("200");
				userHandler.setErrorMsg("Table Already Exists!!!");
			} else {
				String createAccountsQuery = LoadQueryValues.CREATE_NEW_USER_TABLE;
				HSQLConfig.executeQueryForUser(createAccountsQuery);
				userHandler.setErrorCode("200");
				userHandler.setErrorMsg("Table Created!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userHandler;

	}

	@GET
	@Path("/all")
	@Produces("application/json")
	public UserHandler getAllUsers() {
		UserHandler userHandler = new UserHandler();
		LoadQueryValues.loadPropertyValues();
		String getQuery = LoadQueryValues.GET_ALL_USERS;
		List<User> userList = new ArrayList<User>();
		try {
			userList = HSQLConfig.executeQueryForUser(getQuery);

			if (userList.size() > 0) {
				userHandler.setUserList(userList);
				userHandler.setErrorCode("200");
				userHandler.setErrorMsg("OK");
			} else {
				userHandler.setErrorCode("400");
				userHandler.setErrorMsg("List Empty");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return userHandler;
	}

	@PUT
	@Path("/{userName}")
	@Produces("application/json")
	public UserHandler getAllSpecificUser(@PathParam("userName") String userName) {
		UserHandler userHandler = new UserHandler();
		List<User> userList = new ArrayList<User>();
		try {
			if (new Utility().testIfDigitPresent(userName)) {
				userHandler.setErrorCode("400");
				userHandler.setErrorMsg("UserName Invalid");
				return userHandler;
			}
			LoadQueryValues.loadPropertyValues();
			String getQuery = LoadQueryValues.GET_SPECIFIC_USER + "'" + userName + "'";
			userList = HSQLConfig.executeQueryForUser(getQuery);
			if (userList.size() > 0) {
				userHandler.setUserList(userList);
				userHandler.setErrorCode("200");
				userHandler.setErrorMsg("OK");
			} else {
				userHandler.setErrorCode("400");
				userHandler.setErrorMsg("List Empty");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return userHandler;

	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public UserHandler updateUser(@QueryParam("userId") String userId, User user) {
		UserHandler userHandler = new UserHandler();
		List<User> userList = new ArrayList<User>();
		try {
			if (new Utility().testIfDigitPresent(user.getUserName())) {
				userHandler.setErrorCode("400");
				userHandler.setErrorMsg("UserName Invalid");
				return userHandler;
			}
			if(userId==null || userId=="") {
				userHandler.setErrorCode("400");
				userHandler.setErrorMsg("UserId Not Present");
				return userHandler;
			}
			LoadQueryValues.loadPropertyValues();
			String getQuery = LoadQueryValues.UPDATE_SPECIFIC_USER + " name='" + user.getUserName() + "' where userid="
					+ userId;
			userList = HSQLConfig.executeQueryForUser(getQuery);
			if (userList.size() > 0) {
				userHandler.setUserList(userList);
				userHandler.setErrorCode("200");
				userHandler.setErrorMsg("OK");
			} else {
				userHandler.setErrorCode("400");
				userHandler.setErrorMsg("List Empty");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return userHandler;

	}

	@DELETE
	@Path("/{userId}")
	@Produces("application/json")
	public UserHandler deleteUser(@PathParam("userId") String userName) {
		UserHandler userHandler = new UserHandler();
		List<User> userList = new ArrayList<User>();
		try {
			if (new Utility().testIfDigitPresent(userName)) {
				userHandler.setErrorCode("400");
				userHandler.setErrorMsg("UserName Invalid");
				return userHandler;
			}
			LoadQueryValues.loadPropertyValues();
			String getQuery = LoadQueryValues.DELETE_SPECIFIC_USER + "'" + userName + "'";
			userList = HSQLConfig.executeQueryForUser(getQuery);
			if (userList.size() > 0) {
				userHandler.setUserList(userList);
				userHandler.setErrorCode("200");
				userHandler.setErrorMsg("OK");
			} else {
				userHandler.setErrorCode("400");
				userHandler.setErrorMsg("Fail");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return userHandler;

	}

}
