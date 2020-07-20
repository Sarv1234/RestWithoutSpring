package com.javaRest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.java.DB.configuration.HSQLConfig;
import com.java.DB.configuration.LoadQueryValues;
import com.java.Model.Accounts;
import com.java.Model.AccountsHandler;
import com.java.Model.User;
import com.java.Model.UserHandler;
import com.java.Utility.Utility;

//To Fetch Accounts Details
@Path("/accounts")
public class AccountsService {

	@GET
	@Path("/createTable")
	@Produces("application/json")
	public AccountsHandler createAccountsTable() {
		AccountsHandler accountsHandler = new AccountsHandler();
		List<Accounts> valueList = new ArrayList<Accounts>();
		try {
			LoadQueryValues.loadPropertyValues();
			String checkIfUserPresentQuery = LoadQueryValues.CHECK_IF_TABLE_EXISTS+"ACCOUNTS'";
			
			valueList = HSQLConfig.executeQueryForAccounts(checkIfUserPresentQuery);
			if(valueList.size()>0) {
				accountsHandler.setErrorCode("200");
				accountsHandler.setErrorMsg("Table Already Exists");
			}else {
				String createAccountsQuery = LoadQueryValues.CREATE_NEW_ACCOUNTS_TABLE;
				HSQLConfig.executeQueryForAccounts(createAccountsQuery);
				accountsHandler.setErrorCode("200");
				accountsHandler.setErrorMsg("Table Created!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accountsHandler;

	}

	@GET
	@Path("/all")
	@Produces("application/json")
	public AccountsHandler getAllAccounts() {
		AccountsHandler accountsHandler = new AccountsHandler();
		LoadQueryValues.loadPropertyValues();
		String getQuery = LoadQueryValues.GET_ALL_ACCOUNTS;
		List<Accounts> valueList = new ArrayList<Accounts>();
		try {
			valueList = HSQLConfig.executeQueryForAccounts(getQuery);
			if (valueList.size() > 0) {
				accountsHandler.setAccountsList(valueList);
				accountsHandler.setErrorCode("200");
				accountsHandler.setErrorMsg("OK");
			} else {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("List Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountsHandler;
	}

	@PUT
	@Path("/{uuid}")
	@Produces("application/json")
	public AccountsHandler getSpecificAccount(@PathParam("uuid") int uuid) {
		List<Accounts> valueList = new ArrayList<Accounts>();
		AccountsHandler accountsHandler = new AccountsHandler();
		try {
			if(uuid==0) {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("Account Id Not Present");
				return accountsHandler;
			}
			LoadQueryValues.loadPropertyValues();
			String getQuery = LoadQueryValues.GET_SPECIFIC_ACCOUNT + uuid;
			valueList = HSQLConfig.executeQueryForAccounts(getQuery);
			if (valueList.size() > 0) {
				accountsHandler.setAccountsList(valueList);
				accountsHandler.setErrorCode("200");
				accountsHandler.setErrorMsg("OK");
			} else {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("List Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountsHandler;
	}

	@PUT
	@Path("/create")
	@Produces("application/json")
	@Consumes("application/json")
	public AccountsHandler createAccount(Accounts accounts) {
		LoadQueryValues.loadPropertyValues();
		AccountsHandler accountsHandler = new AccountsHandler();
		Utility utility = new Utility();
		List<Accounts> valueList = new ArrayList<Accounts>();
		try {
			if (!(new Utility().checkValidEmail(accounts.getEmailId()))) {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("Invalid Email-Id");
				return accountsHandler;
			}
			String getQuery = LoadQueryValues.CREATE_NEW_ACCOUNT + utility.generateSerialId() + ",'"
					+ accounts.getEmailId() + "','" + accounts.getUserId() + "'," + accounts.getBalance() + ")";
			System.out.println(getQuery);
			valueList = HSQLConfig.executeQueryForAccounts(getQuery);
			if (valueList.size() > 0) {
				accountsHandler.setAccountsList(valueList);
				accountsHandler.setErrorCode("200");
				accountsHandler.setErrorMsg("OK");
			} else {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("List Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountsHandler;
	}

	@DELETE
	@Path("/{accountId}")
	@Produces("application/json")
	public AccountsHandler deleteAccount(@PathParam("accountId") int accountId) {
		LoadQueryValues.loadPropertyValues();
		AccountsHandler accountsHandler = new AccountsHandler();
		List<Accounts> valueList = new ArrayList<Accounts>();
		try {
			if(accountId==0) {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("Account Id Not Present");
				return accountsHandler;
			}
			String getQuery = LoadQueryValues.DELETE_SPECIFIC_ACCOUNT + accountId;
			System.out.println(getQuery);
			valueList = HSQLConfig.executeQueryForAccounts(getQuery);
			if (valueList.size() > 0) {
				accountsHandler.setAccountsList(valueList);
				accountsHandler.setErrorCode("200");
				accountsHandler.setErrorMsg("OK");
			} else {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("List Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountsHandler;
	}

	@PUT
	@Path("/deposit")
	@Produces("application/json")
	@Consumes("application/json")
	public AccountsHandler DepositToAccount(Accounts accounts) {
		LoadQueryValues.loadPropertyValues();
		AccountsHandler accountsHandler = new AccountsHandler();
		List<Accounts> valueList = new ArrayList<Accounts>();
		try {
			if(accounts.getUuid()==0) {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("Account Id Not Present");
				return accountsHandler;
			}
			String getQuery = LoadQueryValues.GET_SPECIFIC_ACCOUNT + accounts.getUuid();
			System.out.println(getQuery);
			valueList = HSQLConfig.executeQueryForAccounts(getQuery);
			int depositAmount = accounts.getBalance() + valueList.get(0).getBalance();

			if (accounts.getBalance() < 0) {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("Please Enter a Positive Value");
				return accountsHandler;
			}

			String updateQuery = LoadQueryValues.UPDATE_SPECIFIC_ACCOUNT + " balance =" + depositAmount + " where uuid="
					+ accounts.getUuid();
			System.out.println(updateQuery);
			valueList = HSQLConfig.executeQueryForAccounts(updateQuery);
			if (valueList.size() > 0) {
				valueList.get(0).setBalance(depositAmount);
				valueList.get(0).setUuid(accounts.getUuid());
				accountsHandler.setAccountsList(valueList);
				accountsHandler.setAccountsList(valueList);
				accountsHandler.setErrorCode("200");
				accountsHandler.setErrorMsg("OK");
			} else {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("List Empty");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return accountsHandler;
	}

	@PUT
	@Path("/withdraw")
	@Produces("application/json")
	public AccountsHandler WithdrawToAccount(Accounts accounts) {
		LoadQueryValues.loadPropertyValues();
		AccountsHandler accountsHandler = new AccountsHandler();
		List<Accounts> valueList = new ArrayList<Accounts>();
		try {
			if(accounts.getUuid()==0) {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("Account Id Not Present");
				return accountsHandler;
			}
			String getQuery = LoadQueryValues.GET_SPECIFIC_ACCOUNT + accounts.getUuid();
			valueList = HSQLConfig.executeQueryForAccounts(getQuery);
			if (accounts.getBalance() < 0) {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("Please Enter a Positive Value");
				return accountsHandler;
			}
			int withDrawAmount = valueList.get(0).getBalance() - accounts.getBalance();
			if (withDrawAmount < 0) {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("Balance is less than withdrawal amount,current balance is Rs"
						+ valueList.get(0).getBalance() + "/-");
				return accountsHandler;
			}
			String updateQuery = LoadQueryValues.UPDATE_SPECIFIC_ACCOUNT + " balance =" + withDrawAmount
					+ " where uuid=" + accounts.getUuid();
			System.out.println(updateQuery);
			valueList = HSQLConfig.executeQueryForAccounts(updateQuery);
			if (valueList.size() > 0) {
				valueList.get(0).setBalance(withDrawAmount);
				valueList.get(0).setUuid(accounts.getUuid());
				accountsHandler.setAccountsList(valueList);
				accountsHandler.setErrorCode("200");
				accountsHandler.setErrorMsg("OK");
			} else {
				accountsHandler.setErrorCode("400");
				accountsHandler.setErrorMsg("List Empty");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return accountsHandler;
	}
}
