/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.client;

import java.util.ArrayList;

/**
 *
 * @author Wolf
 */
public class UserAccountTable
{
	private static final UserAccountTable INSTANCE = new UserAccountTable();

	private UserAccountTable()
	{
	}

	//Usage: instance = UserAccountTable.get();
	//Pre: Nothing:
	//Post: Returns instance variable INSTANCE.
	public static UserAccountTable get()
	{
		return INSTANCE;
	}

	//Usage: ua = instance.stringToUA(data);
	//Pre: data has length 6.
	//Post: ua has has info as if the contents of data were entered into the UserAcount constructor.
	public UserAccount stringToUA(String[] data) throws IllegalArgumentException
	{
		if (data.length == 6)
			return new UserAccount(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), data[4], data[5]);

		throw new IllegalArgumentException("Input, String[], must have length 6.");
	}
	
	//Usage: instance.createAccount(username, password, telephone, name, email);
    //Pre: username and password are not empty strings. username has length < 20, password has length < 30.
    //Post: A user account has been created with the parameters given.
	public void createAccount(String UN, String PW, int telephone, String name, String email) throws IllegalArgumentException
	{
		if (UN.length() > 20)
			throw new IllegalArgumentException("Username is too long.");
		else if (PW.length() > 30)
			throw new IllegalArgumentException("Password is too long.");
		else if (existsUN(UN))
			throw new IllegalArgumentException("Username already exists.");
		else if (UN.equals(""))
			throw new IllegalArgumentException("Username cannot be the empty string.");
		else if (PW.equals(""))
			throw new IllegalArgumentException("Password cannot be the empty string.");

		UserAccount temp = new UserAccount(UN, PW, telephone, name, email);
		
		String SQL = "INSERT INTO accounts(username, password, telephone, name, email) VALUES('"
				+ temp.username + "','" 
				+ temp.password + "',"
				+ Integer.toString(temp.getPhone()) + ",'"
				+ temp.getName() + "','"
				+ temp.getEmail() + "')";
		DataPort.get().execute(SQL);
	}

	//Usage: UA = instance.getAccount(UN);
    //Pre: Nothing.
    //Post: A user account with username UN is returned if it exists, else null is returned.
	public UserAccount getAccount(String UN)
	{
		String SQL = "SELECT * FROM accounts WHERE username = '" + UN + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		return temp.isEmpty() ? null : stringToUA(temp.get(0));
	}

	//Usage: UA = instance.getAccount(accountID);
    //Pre: Nothing.
    //Post: A user account with ID accountID is returned if it exists, else null is returned.
	public UserAccount getAccount(int id)
	{
		String s = Integer.toString(id);
		String SQL = "SELECT * FROM accounts WHERE accountid = " + s;
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		return temp.isEmpty() ? null : stringToUA(temp.get(0));
	}

	//Usage: p = instance.existsUN(UN);
    //Pre: Nothing.
    //Post: Returns true if a user account with username UN exists, false otherwise.
	public boolean existsUN(String UN)
	{
		String SQL = "SELECT * FROM accounts WHERE username = '" + UN + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		return !temp.isEmpty();
	}

	//Usage: p = instance.existsID(accountID);
    //Pre: Nothing.
    //Post: Returns true if an account with given account ID exists, false otherwise.
	public boolean existsID(int ID)
	{
		String SQL = "SELECT * FROM accounts WHERE accountid = " + Integer.toString(ID);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		return (!temp.isEmpty());
	}
	
	public static void main(String[] args){
		//get().createAccount("username", "password", 1234567, "nafn", "email");
		//get().getAccount("username").print();
		//get().getAccount("asdf");
	}
}
