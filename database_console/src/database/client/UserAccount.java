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
	private final String password;
	private static int id = 0;

	private UserAccount(int id, String UN, String PW)
	{
		accountID = id;
		username = UN;
		password = PW;
	}

	private UserAccount(String UN, String PW)
	{
		accountID = id;
		username = UN;
		password = PW;
		id++;
	}

	private static UserAccount stringToUA(String[] data) throws IllegalArgumentException
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
		else if (UserAccount.existsUN(UN))
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
		return stringToUA(temp.get(0)).password.equals(PW);
	}

	public void print()
	{
		System.out.println("username: " + this.username + " id: " + this.accountID);
	}

	public static void main(String[] args)
	{

		DataPort.get().connect();
		//DataPort port = new DataPort();
		//port.connect();

		System.out.println("This is UserAccount");

		//Prófum að sækja account með því að nota username:
		UserAccount bla = UserAccount.getAccount("shs");
		System.out.print("User caught with username: ");
		bla.print();

		//Prófum að sækja account með þvi að nota accountid:
		UserAccount bla2 = UserAccount.getAccount(1002);
		System.out.print("User caught with accountid: ");
		bla2.print();

		//Prófum lykilorð:
		boolean blabla = UserAccount.getAccount("shs").isPW("sblla");
		System.out.println("Is password correct: " + blabla);

		//Gáum hvort að notandi sé til:
		boolean blabla2 = UserAccount.existsUN("shs");
		System.out.println("Does user exist: " + blabla2);

		//Gáum hvort að id sé til:
		boolean blabla3 = UserAccount.existsID(4356456);
		System.out.println("Does ID exist: " + blabla3);

		//Prófum að uppfæra lykilorð:
		getAccount("shs").editPW("shs", "shs");

		System.out.println("Create new account: ");
		createAccount("bbbb", "bbbb");

		DataPort.get().disconnect();

	}
}
