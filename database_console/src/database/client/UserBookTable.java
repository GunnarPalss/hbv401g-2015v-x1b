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
public class UserBookTable
{
	private static final UserBookTable INSTANCE = new UserBookTable();

	private UserBookTable()
	{
	}

	public static UserBookTable get()
	{
		return INSTANCE;
	}

	public UserBook stringToUB(String[] data) throws IllegalArgumentException
	{
		if (data.length == 6)
			return new UserBook(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), data[4], data[5]);

		throw new IllegalArgumentException("Input, String[], must have length 6.");
	}

	public void createBook(int accountID, int ISBN)
	{
		//Ekki gá hvort að eintak með sama ISBN sé nú þegar til. Notandi má vera að selja tvö eintök af sömu bók.
		DatabaseBook copy = DatabaseBookTable.get().getBook(ISBN);
		String SQL = "INSERT INTO UserBook VALUES('"
				+ Integer.toString(accountID) + "','"
				+ Integer.toString(ISBN) + "','"
				+ Integer.toString(0) + "','" //Látum verðið vera 0 sem default. Það er svosem hægt að láta töfluna UserBook gera það sjálfkrafa.
				+ "','" //Tómur strengur fyrir condition
				+ "'"; //Tómur strengur fyrir pictureURL
		DataPort.get().execute(SQL);
	}

	public ArrayList<UserBook> getBooks(int ISBN)
	{
		String SQL = "SELECT * FROM UserBook WHERE isbn =" + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}

	public ArrayList<UserBook> getBooks(String title)
	{
		String SQL = "SELECT instanceid, accountid, isbn, userprice, condition, pictureurl FROM UserBook NATURAL JOIN DatabaseBook WHERE title ='" + title + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}

	//Ath. deleteBook eyðir einu eintaki af UserBook með ákv. ISBN, eraseBook eyðir öllum eintökum af UserBook með ákveðið ISBN.
	//Klasinn DatabaseBook notar fallið eraseBook.
	public void deleteBook(int accountID, int ISBN)
	{
		String SQL = "DELETE FROM UserBook WHERE accountID = " + Integer.toString(accountID) + " AND isbn = " + Integer.toString(ISBN);
		DataPort.get().execute(SQL);
	}

	public void deleteBook(int instanceID)
	{
		String SQL = "DELETE FROM UserBook WHERE instanceID = " + Integer.toString(instanceID);
		DataPort.get().execute(SQL);
	}

	//Ath. deleteBook eyðir einu eintaki af UserBook með ákv. ISBN, eraseBook eyðir öllum eintökum af UserBook með ákveðið ISBN.
	//Klasinn DatabaseBook notar fallið eraseBook.
	public void eraseBook(int ISBN)
	{
		String SQL = "DELETE FROM UserBook WHERE isbn = " + Integer.toString(ISBN);
		DataPort.get().execute(SQL);
	}

	public ArrayList<UserBook> searchAuthor(String author)
	{
		String SQL = "SELECT instanceid, accountid, isbn, userprice, condition, pictureurl FROM databasebook NATURAL JOIN userbook WHERE authors Like '%" + author + "%'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}

	public ArrayList<UserBook> searchTitle(String title)
	{
		String SQL = "SELECT instanceid, accountid, isbn, userprice, condition, pictureurl FROM databasebook NATURAL JOIN userbook WHERE title LIKE '%" + title + "%'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}
	
	public ArrayList<UserBook> searchCategory(String category)
	{
		String SQL = "SELECT instanceid, accountid, isbn, userprice, condition, pictureurl FROM databasebook NATURAL JOIN userbook WHERE category = '" + category + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}
	
	public ArrayList<UserBook> searchSubategory(String subcategory)
	{
		String SQL = "SELECT instanceid, accountid, isbn, userprice, condition, pictureurl FROM databasebook NATURAL JOIN userbook WHERE subcategory = '" + subcategory + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}

	public boolean existsISBN(int ISBN)
	{
		String SQL = "SELECT * FROM UserBook WHERE isbn =" + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		return !temp.isEmpty();
	}

	public boolean existsTitle(String title)
	{
		String SQL = "SELECT * FROM UserBook WHERE title ='" + title + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		return !temp.isEmpty();
	}
	
	public ArrayList<UserBook> searchTitleAndAuthor(String title, String author){
		String SQL = "SELECT instanceid, accountid, isbn, userprice, condition, pictureurl FROM databasebook NATURAL JOIN usebook WHERE authors LIKE '%" + author + "%' AND title LIKE '%" + title + "%'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		ArrayList<UserBook> ubArray = new ArrayList<UserBook>();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}
	
	public ArrayList<UserBook> searchEverything(String title, String author, String category, String subcategory){
		
		boolean p = false;
		String SQL = "SELECT * FROM databasebook NATURAL JOIN userbook WHERE ";
		
		if(title != null){
			SQL = SQL.concat("title LIKE '%" + title + "%'");
			p = true;
		}
		if(author != null){
			if(p == true){
				SQL = SQL.concat(" AND ");
				}
				SQL = SQL.concat("author LIKE '%" + author + "%'");
				p = true;
		}
		if(category != null){
			if(p == true){
				SQL = SQL.concat(" AND ");
				}
				SQL = SQL.concat("category = '" + category + "'");
				p = true;
		}
		if(subcategory != null){
			if(p == true){
				SQL = SQL.concat(" AND ");
				}
				SQL = SQL.concat("subcategory = '" + subcategory + "'");
		}
		
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		ArrayList<UserBook> ubArray = new ArrayList<>();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}
	
	public ArrayList<UserBook> getBooksByAccountID(int accountID){
		String SQL = "SELECT * FROM UserBook WHERE accountID =" + Integer.toString(accountID);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 6);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}
}
