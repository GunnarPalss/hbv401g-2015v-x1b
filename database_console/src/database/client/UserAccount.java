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
	
	private int telephone;
	private String name;
	private String email;
	
	//The instance generated in this constructor is never returned to anyone. It is used for creating an account in UserAccountTable.
	UserAccount(String UN, String PW, int telephone, String name, String email)
	{
		accountID = -1;
		username = UN;
		password = PW;
		this.telephone = telephone;
		this.name = name;
		this.email = email;
	}
	
	//This is used to return an instance of UserAccount from the database.
	UserAccount(int accountID, String UN, String PW, int telephone, String name, String email)
	{
		this.accountID = accountID;
		this.username = UN;
		this.password = PW;
		this.telephone = telephone;
		this.name = name;
		this.email = email;
	}

	//Usage: UA.editPW(oldPassword, newPassword);
    //Pre: Nothing.
    //Post: If the UserAccount UA's current password matches oldPassword and newPassword is not null or the empty string,
	//		UA's current password becomes newPassword.
	public void editPW(String oldPW, String newPW) throws IllegalArgumentException
	{
		if (!this.isPW(oldPW))
			throw new IllegalArgumentException("Old password does not match given input.");

		String SQL = "UPDATE accounts SET password = '" + newPW + "' WHERE username = '" + this.username + "'";
		DataPort.get().execute(SQL);
		System.out.println("Password updated.");
	}

	//Usage: p = UA.isPW(password);
    //Pre: Nothing.
    //Post: Returns true if the UserAccount UA has password PW, false otherwise.
	public boolean isPW(String PW)
	{
		String SQL = "SELECT * FROM accounts WHERE username = '" + this.username + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		return UserAccountTable.get().stringToUA(temp.get(0)).password.equals(PW);
	}
	
	//Usage: ua.editName(newName);
	//Pre: Nothing.
	//Post: The user account ua now has the new name newName.
	public void editName(String newName){
		this.name = newName;
		String SQL = "UPDATE accounts SET name = '" + newName + "' WHERE accountid = " + this.accountID;
		DataPort.get().execute(SQL);
	}
	
	//Usage: ua.editPhone(newPhone);
	//Pre: Nothing.
	//Post: The user account ua now has the new phone number newPhone.
	public void editPhone(int newPhone){
		this.telephone = newPhone;
		String SQL = "UPDATE accounts SET telephone = " + newPhone + " WHERE accountid = " + this.accountID;
		DataPort.get().execute(SQL);
	}
	
	//Usage: ua.editPhone(newPhone);
	//Pre: Nothing.
	//Post: The user account ua now has the the new email address newEmail.
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
