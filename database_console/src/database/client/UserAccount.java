/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.client;

/**
 *
 * @author Notandi
 */
import java.util.ArrayList;

public class UserAccount
{

	public final int accountID;
	public final String username;
	final String password;
	private static int id = 0;

	UserAccount(int id, String UN, String PW)
	{
		accountID = id;
		username = UN;
		password = PW;
	}

	UserAccount(String UN, String PW)
	{
		accountID = id;
		username = UN;
		password = PW;
		id++;
	}

	public void editPW(String oldPW, String newPW) throws IllegalArgumentException
	{
		if (!this.isPW(oldPW))
			throw new IllegalArgumentException("Old password does not match given input.");

		String SQL = "UPDATE accounts SET password = '" + newPW + "' WHERE username = '" + this.username + "'";
		DataPort.get().execute(SQL);
		System.out.println("Password updated.");
	}

	public boolean isPW(String PW)
	{
		String SQL = "SELECT * FROM accounts WHERE username = '" + this.username + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 3);
		return UserAccountTable.get().stringToUA(temp.get(0)).password.equals(PW);
	}

	public void print()
	{
		System.out.println("username: " + this.username + " id: " + this.accountID);
	}
}
