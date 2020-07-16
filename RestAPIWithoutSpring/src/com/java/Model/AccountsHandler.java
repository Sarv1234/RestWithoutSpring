package com.java.Model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "accountsResponse")
public class AccountsHandler {
	private List<Accounts> accountsList;
	private String errorCode;
	private String errorMsg;

	public List<Accounts> getAccountsList() {
		return accountsList;
	}

	public void setAccountsList(List<Accounts> accountsList) {
		this.accountsList = accountsList;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
