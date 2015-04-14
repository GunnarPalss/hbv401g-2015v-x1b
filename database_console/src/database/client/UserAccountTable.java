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

	public static UserAccountTable get()
	{
		return INSTANCE;
	}

	public static UserAccount stringToUA(String[] data) throws IllegalArgumentException
	{
		if (data.length == 3)
			return new UserAccount(Integer.parseInt(data[0]), data[1], data[2]);

		throw new IllegalArgumentException("Input, String[], must have length 3.");
	}

	public static void createAccount(String UN, String PW) throws IllegalArgumentException
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

		UserAccount temp = new UserAccount(UN, PW);
		String SQL = "INSERT INTO accounts VALUES(" + Integer.toString(temp.accountID) + ",'" + temp.username + "','" + temp.password + "')";
		DataPort.get().execute(SQL);
	}

	public static UserAccount getAccount(String UN)
	{
		String SQL = "SELECT * FROM accounts WHERE username = '" + UN + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 3);
		return stringToUA(temp.get(0));
	}

	public static UserAccount getAccount(int id)
	{
		String s = Integer.toString(id);
		String SQL = "SELECT * FROM accounts WHERE accountid = " + s;
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 3);
		return stringToUA(temp.get(0));
	}

	public static boolean existsUN(String UN)
	{
		String SQL = "SELECT * FROM accounts WHERE username = '" + UN + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 3);
		return !temp.isEmpty();
	}

	public static boolean existsID(int ID)
	{
		String SQL = "SELECT * FROM accounts WHERE accountid = " + Integer.toString(ID);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 3);
		return (!temp.isEmpty());
	}
}
