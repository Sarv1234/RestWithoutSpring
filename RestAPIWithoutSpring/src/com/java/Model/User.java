package com.java.Model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
public class User {

	private String userName;
	private String userId;
	private String status;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
