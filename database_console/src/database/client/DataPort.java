/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
//import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//The class DataPort is a singleton.
class DataPort
{

	/**
	 * @param args the command line arguments
	 */
	private static final DataPort INSTANCE = new DataPort();

	private final String host = "jdbc:sqlite:db/database.sqlite";
	private final String uName = "administrator";
	private final String uPass = "administrator";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	/*private PreparedStatement prepStmt;

	 private final String add = "INSERT INTO ? VALUES(?)";
	 private final String selectAll = "SELECT * FROM ?"; 
	 private final String selectSome = "SELECT * FROM ? WHERE ?";
	 private final String update = "UPDATE ? SET ? WHERE ?";
	 private final String delete = "DELETE FROM ? WHERE ?";*/

	private DataPort()
	{
		connect();
	}

	//Usage: instance = DataPort.get();
	//Pre: Nothing.
	//Post: Returns the instance variable INSTANCE.
	public static DataPort get()
	{
		return INSTANCE;
	}

	//Usage: instance.connect();
	//Pre: Nothing.
	//Post: The program has connected to its database.
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
			Logger.getLogger(DataPort.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//Usage: instance.connect();
	//Pre: Nothing.
	//Post: The program has disconnected from its database.
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

	//Usage: instance.initializeDatabase();
	//Pre: Nothing.
	//Post: The tables DATABASEBOOK, USERBOOK and ACCOUNTS have been created in the database if they did not exist.
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
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ACCOUNTS ("
					+ "ACCOUNTID INTEGER PRIMARY KEY NOT NULL, "
					+ "USERNAME VARCHAR(20) NOT NULL, "
					+ "PASSWORD VARCHAR(30) NOT NULL,"
					+ "TELEPHONE INT,"
					+ "NAME VARCHAR(50) NOT NULL,"
					+ "EMAIL VARCHAR(50) NOT NULL"
					+ ");");

		} catch (SQLException ex)
		{
			Logger.getLogger(DataPort.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*
	 public static boolean isWhiteListed(String s){
	 String acceptableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789- .";
	 for(int i = 0; i < s.length(); i++){
	 if(!acceptableChars.contains(Character.toString(s.charAt(i)))){return false;}
	 }
	 return true;
	 }*/


	//Usage: list = instance.executeAndReturn(SQL, size);
	//Pre: SQL must be a valid SQL query that does *not* edit data, i.e. only accesses it.
	//		Size must not exceed the number of columns of the table returned from the SQL query.
	//Post: Each String[] in list has the same length as the argument size.
	//		Each String[] in list includes data from one row of the table returned from the SQL query. If the argument size is
	//		less than the number of columns of the table returned from the SQL query, each String[] only includes data from the left-most columns of the result.
	//		list has the same length as the number of rows in the table returned from the SQL query.
	public ArrayList<String[]> executeAndReturn(String SQL, int size)
	{

		try
		{
			
			ArrayList<String[]> list = new ArrayList();
			this.rs = stmt.executeQuery(SQL);
			if (rs == null)
				return list;
			while (rs.next())
			{
				String[] data = new String[size];
				for (int i = 0; i < size; i++)
					data[i] = rs.getString(i + 1);
				list.add(data.clone()); //list.add(data);
			}
			return list;
		} catch (SQLException err)
		{
			System.out.println(err.getMessage());
			return null;
		}
	}

	//Usage: instance.execute(SQL);
	//Pre: SQL must be a valid SQL query that *edits* or *adds* data to the databse.
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
		//DataPort port = DataPort.get();
		//port.connect();

		//DatabaseBookScraper.get().createBook(1011, uName, host, 1000, host, host, uName);

		/*try
		 {
		 stmt.executeUpdate("drop table if exists school;");
		 stmt.executeUpdate("create table school (name, state);");
		 PreparedStatement prep = con.prepareStatement(
		 "insert into school values (?, ?);");
		 prep.setString(1, "UTD");
		 prep.setString(2, "texas");
		 prep.addBatch();
		 prep.setString(1, "USC");
		 prep.setString(2, "california");
		 prep.addBatch();
		 prep.setString(1, "MIT");
		 prep.setString(2, "massachusetts");
		 prep.addBatch();
		 con.setAutoCommit(false);
		 prep.executeBatch();
		 con.setAutoCommit(true);

		 ResultSet ss = stmt.executeQuery("select * from school;");
		 while (ss.next())
		 {
		 System.out.print("Namechool = " + ss.getString("name") + " ");
		 System.out.println("state" + ss.getString("state"));
		 }
		 ss.close();
			
			
		 } catch (SQLException ex)
		 {
		 Logger.getLogger(DataPort.class.getName()).log(Level.SEVERE, null, ex);
		 }
		port.disconnect();

		System.out.println("This is DataPort");*/
	}
}
