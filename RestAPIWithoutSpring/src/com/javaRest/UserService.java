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

@Path("/users")
public class UserService {

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
		LoadQueryValues.loadPropertyValues();
		String getQuery = LoadQueryValues.GET_SPECIFIC_USER + "'" + userName + "'";
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

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public UserHandler updateUser(@QueryParam("userId") String userId, User user) {
		UserHandler userHandler = new UserHandler();
		LoadQueryValues.loadPropertyValues();
		String getQuery = LoadQueryValues.UPDATE_SPECIFIC_USER + " name='" + user.getUserName() + "' where userid="
				+ userId;
		System.out.println(getQuery);
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

	@DELETE
	@Path("/{userId}")
	@Produces("application/json")
	public UserHandler createUser(@PathParam("userId") String userName) {
		UserHandler userHandler = new UserHandler();
		LoadQueryValues.loadPropertyValues();
		String getQuery = LoadQueryValues.DELETE_SPECIFIC_USER + "'" + userName + "'";
		List<User> userList = new ArrayList<User>();
		try {
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
