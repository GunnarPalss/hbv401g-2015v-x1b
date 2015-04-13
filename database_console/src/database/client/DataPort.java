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

	private DataPort() {}

	public static DataPort get()
	{
		return INSTANCE;
	}

	public void connect()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(host, uName, uPass);
		} catch (SQLException | ClassNotFoundException ex)
		{
			Logger.getLogger(DataPort.class.getName()).log(Level.SEVERE, null, ex);
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
		DataPort port = new DataPort();
		port.connect();
		
		Statement stat;
		try
		{
			stat = con.createStatement();
			stat.executeUpdate("drop table if exists school;");
			stat.executeUpdate("create table school (name, state);");
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
			ResultSet ss = stat.executeQuery("select * from school;");
			while (ss.next()) {
				System.out.print("Namechool = " + ss.getString("name") + " ");
				System.out.println("state" + ss.getString("state"));
			}
			ss.close();
		} catch (SQLException ex)
		{
			Logger.getLogger(DataPort.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		port.disconnect();
		
		//connection.executeAndReturn("SELECT * FROM accounts WHERE username = 'shs'", 3);
		System.out.println("This is DataPort");
	}
}
