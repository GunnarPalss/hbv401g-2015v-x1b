/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.server;

import database.common.Order;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolf
 */
class DatabaseController
{
	/**
	 * @param args the command line arguments
	 */
	private static final DatabaseController INSTANCE = new DatabaseController();

	private static final String host = "jdbc:sqlite:db/database.sqlite";
	private static final String uName = "administrator";
	private static final String uPass = "administrator";

	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
	/*private PreparedStatement prepStmt;

	 private final String add = "INSERT INTO ? VALUES(?)";
	 private final String selectAll = "SELECT * FROM ?"; 
	 private final String selectSome = "SELECT * FROM ? WHERE ?";
	 private final String update = "UPDATE ? SET ? WHERE ?";
	 private final String delete = "DELETE FROM ? WHERE ?";*/

	private DatabaseController()
	{
	}

	public static DatabaseController get()
	{
		return INSTANCE;
	}

	public void connect()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(host, uName, uPass);
			stmt = con.createStatement();
			this.initializeDatabase();
		} catch (SQLException | ClassNotFoundException ex)
		{
			Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void disconnect()
	{
		try
		{
			if (stmt != null)
				stmt.close();
			con.close();
		} catch (SQLException err)
		{
			System.out.println(err.getMessage());
		}
	}

	private void initializeDatabase()
	{
		try
		{
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS DATABASEBOOK ("
					+ "ISBN INTEGER PRIMARY KEY NOT NULL, "
					+ "TITLE VARCHAR(100) NOT NULL, "
					+ "AUTHORS VARCHAR(100) NOT NULL, "
					+ "PRICE INTEGER NOT NULL, "
					+ "DESCRIPTION VARCHAR(500), "
					+ "CATEGORY VARCHAR(30), "
					+ "SUBCATEGORY VARCHAR(30)"
					+ ");");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USERBOOK ("
					+ "INSTANCEID INTEGER PRIMARY KEY NOT NULL, "
					+ "ACCOUNTID INTEGER NOT NULL, "
					+ "ISBN INTEGER NOT NULL, "
					+ "USERPRICE INTEGER NOT NULL, "
					+ "CONDITION VARCHAR(15) NOT NULL"
					+ ");");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USERACCOUNT ("
					+ "ACCOUNTID INTEGER PRIMARY KEY NOT NULL, "
					+ "USERNAME VARCHAR(20) NOT NULL, "
					+ "PASSWORD VARCHAR(30) NOT NULL"
					+ ");");

		} catch (SQLException ex)
		{
			Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*
	 public static boolean isWhiteListed(String s){
	 String acceptableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789- .";
	 for(int i = 0; i < s.length(); i++){
	 if(!acceptableChars.contains(Character.toString(s.charAt(i)))){return false;}
	 }
	 return true;
	 }

	 public static ArrayList arrayFromResult(String SQL, int size){
	 ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, size);
	 ArrayList myArray = new ArrayList();
	 for(String[] s: temp){
	 //myArray.add(stringToItem(s));
	 }
	 return myArray;
	 }
	 */
	
	public PreparedStatement createStatement(String sql)
	{
		try
		{
			return con.prepareStatement(sql);
		} catch (SQLException ex)
		{
			Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	//Notað til að fá upplýsingar úr gagnagrunni:
	public ArrayList<String[]> executeAndReturn(String SQL, int size)
	{
		try
		{
			String[] data = new String[size];
			ArrayList<String[]> list = new ArrayList();
			this.rs = stmt.executeQuery(SQL);
			while (rs.next())
			{
				for (int i = 0; i < size; i++)
					data[i] = rs.getString(i + 1);
				list.add(data);
			}
			return list;
		} catch (SQLException err)
		{
			System.out.println(err.getMessage());
			return null;
		}
	}

	//Notað til að setja upplýsingar inn í gagnagrunn/breyta upplýsingum í gagnagrunni:
	public void execute(String SQL)
	{
		try
		{
			stmt.executeUpdate(SQL);
		} catch (SQLException err)
		{
			System.out.println(err.getMessage());
		}
	}

	public static void main(String[] args)
	{
		// TODO code application logic here
		DatabaseController port = DatabaseController.get();
		port.connect();

		port.disconnect();

		System.out.println("This is DatabaseController");
	}
}
