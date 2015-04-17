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
	
	private int telephone;
	private String name;
	private String email;
	
	

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
	
	UserAccount(String UN, String PW, int telephone, String name, String email)
	{
		accountID = id;
		username = UN;
		password = PW;
		this.telephone = telephone;
		this.name = name;
		this.email = email;
	}
	
	UserAccount(int accountID, String UN, String PW, int telephone, String name, String email)
	{
		this.accountID = accountID;
		this.username = UN;
		this.password = PW;
		this.telephone = telephone;
		this.name = name;
		this.email = email;
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
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		return UserAccountTable.get().stringToUA(temp.get(0)).password.equals(PW);
	}
	
	public void editName(String newName){
		this.name = newName;
		String SQL = "UPDATE accounts SET name = '" + newName + "' WHERE accountid = " + this.accountID;
		DataPort.get().execute(SQL);
	}
	
	public void editPhone(int newPhone){
		this.telephone = newPhone;
		String SQL = "UPDATE accounts SET telephone = " + newPhone + " WHERE accountid = " + this.accountID;
		DataPort.get().execute(SQL);
	}
	
	public void editEmail(String newEmail){
		this.email = newEmail;
		String SQL = "UPDATE accounts SET email = '" + newEmail + "' WHERE accountid = " + this.accountID;
		DataPort.get().execute(SQL);
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getPhone(){
		return this.telephone;
	}
	
	public String getEmail(){
		return this.email;
	}

	public void print()
	{
		System.out.println("username: " + this.username + " id: " + this.accountID);
	}
}
