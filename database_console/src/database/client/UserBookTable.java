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

	//Usage: instance = UserBookTable.get();
	//Pre: Nothing:
	//Post: Returns instance variable INSTANCE.
	public static UserBookTable get()
	{
		return INSTANCE;
	}

	//Usage: ub = instance.stringToUB(data);
	//Pre: data has length 5.
	//Post: ub has has info as if the contents of data were entered into the UserBook constructor.
	public UserBook stringToUB(String[] data) throws IllegalArgumentException
	{
		if (data.length == 5)
			return new UserBook(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), data[4]);

		throw new IllegalArgumentException("Input, String[], must have length 5.");
	}
	
	//Usage: instance.createBook(accountID, int ISBN, int price, String condition);
	//Pre: Nothing.
	//Post: A user book has been inserted into the database with the specified parameters.
	public void createBook(int accountID, int ISBN, int price, String condition)
	{
		String myCond = condition == null ? "''" : "'" + condition + "'";
		//Ekki gá hvort að eintak með sama ISBN sé nú þegar til. Notandi má vera að selja tvö eintök af sömu bók.
		//DatabaseBook copy = DatabaseBookTable.get().getBook(ISBN);
		String SQL = "INSERT INTO userbook(accountID, isbn, userprice, condition) VALUES("
				+ Integer.toString(accountID) + ","
				+ Integer.toString(ISBN) + ","
				+ Integer.toString(price) + ", " //Látum verðið vera 0 sem default. Það er svosem hægt að láta töfluna UserBook gera það sjálfkrafa.
				+ myCond + ")"; //Tómur strengur fyrir condition
		DataPort.get().execute(SQL);
	}

	//Usage: list = instance.getBooks(ISBN);
	//Pre: Nothing.
	//Post: list includes all user books from the database that have given ISBN.
	public ArrayList<UserBook> getBooks(int ISBN)
	{
		String SQL = "SELECT * FROM UserBook WHERE isbn =" + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 5);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}
	
	//Usage: list = instance.getBooks(accountID, ISBN);
	//Pre: Nothing.
	//Post: list includes all user books from the database that have given ISBN and accountID.
	public ArrayList<UserBook> getBooks(int accountID, int ISBN)
	{
		String SQL = "SELECT * FROM UserBook WHERE accountid =" + Integer.toString(accountID) + " AND isbn = " + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 5);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}
	
	//Usage: instance.deleteBooks(accountID, ISBN);
	//Pre: Nothing.
	//Post: Deletes all user books with given ISBN from the account with accountID from the database.
	public void deleteBook(int accountID, int ISBN)
	{
		String SQL = "DELETE FROM UserBook WHERE accountID = " + Integer.toString(accountID) + " AND isbn = " + Integer.toString(ISBN);
		DataPort.get().execute(SQL);
	}

	//Usage: instance.deleteBooks(instanceID);
	//Pre: Nothing.
	//Post: Deletes user book with given instanceID from the database.
	public void deleteBook(int instanceID)
	{
		String SQL = "DELETE FROM UserBook WHERE instanceID = " + Integer.toString(instanceID);
		DataPort.get().execute(SQL);
	}

	//Usage: instance.eraseBook(ISBN);
	//Pre: Nothing.
	//Post: All user books with the given ISBN have been erased from the database.
	public void eraseBook(int ISBN)
	{
		String SQL = "DELETE FROM UserBook WHERE isbn = " + Integer.toString(ISBN);
		DataPort.get().execute(SQL);
	}
	
	//Usage: list = instance.searchCategory(category);
	//Pre: Nothing.
	//Post: list is a list of all books in the UserBook database that belong to a category matching the argument category. The parameter must match the category *exactly*.
	public ArrayList<UserBook> searchCategory(String category)
	{
		String SQL = "SELECT instanceid, accountid, isbn, userprice, condition FROM databasebook NATURAL JOIN userbook WHERE category = '" + category + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 5);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}
	
	//Usage: list = instance.searchSubategory(subcategory);
	//Pre: Nothing.
	//Post: list is a list of all books in the UserBook database that belong to a subcategory matching the argument subcategory. The parameter must match the subcategory *exactly*.
	public ArrayList<UserBook> searchSubategory(String subcategory)
	{
		String SQL = "SELECT instanceid, accountid, isbn, userprice, condition FROM databasebook NATURAL JOIN userbook WHERE subcategory = '" + subcategory + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 5);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}

	//Usage: p = instance.exists(ISBN);
	//Pre: Nothing.
	//Post: Returns true  if a user book with given ISBN exists, false otherwise.
	public boolean existsISBN(int ISBN)
	{
		String SQL = "SELECT * FROM UserBook WHERE isbn =" + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 5);
		return !temp.isEmpty();
	}

	//Usage: p = instance.exists(title);
	//Pre: Nothing.
	//Post: Returns true  if a user book with given title exists, false otherwise.
	public boolean existsTitle(String title)
	{
		String SQL = "SELECT * FROM UserBook WHERE title ='" + title + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 5);
		return !temp.isEmpty();
	}
	
	//Usage: list = instance.searchEveryThing(title, author, category, subcategory);
	//Pre: At least one argument must not be null.
	//Post: list includes all user books with title and author similar to parameters, and category and subcategory exactly like paramters.
	//		If NULL arguments are given. They are excluded from the search. 
	public ArrayList<UserBook> searchEverything(String title, String author, String category, String subcategory){
		
		boolean p = false;
		String SQL = "SELECT instanceid, accountid, isbn, userprice, condition FROM databasebook NATURAL JOIN userbook WHERE ";
		
		if(title != null){
			SQL = SQL.concat("title LIKE '%" + title + "%'");
			p = true;
		}
		if(author != null){
			if(p == true){
				SQL = SQL.concat(" AND ");
				}
			SQL = SQL.concat("authors LIKE '%" + author + "%'");
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
		
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 5);
		ArrayList<UserBook> ubArray = new ArrayList<>();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}
	
	//Usage: list = instance.getBooksByAccountID(accountID);
	//Pre: Nothing.
	//Post: list includes all user books from the database submitted by user account with accountID.
	public ArrayList<UserBook> getBooksByAccountID(int accountID){
		String SQL = "SELECT * FROM UserBook WHERE accountID =" + Integer.toString(accountID);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 5);
		ArrayList<UserBook> ubArray = new ArrayList();
		for (String[] UB : temp)
			ubArray.add(stringToUB(UB));

		return ubArray;
	}
	
	public static void main(String[] args){
		//get().createBook(0, 1234);
		//get().getBooks(1234);
		//get().searchEverything("ti", "aut", "category", "subcategory");
	}

}