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
public class DatabaseBookTable
{
	private static final DatabaseBookTable INSTANCE = new DatabaseBookTable();

	private DatabaseBookTable()
	{
	}

	//Usage: instance = DatabaseBookTable.get();
	//Pre: Nothing.
	//Post: Returns instance variable INSTANCE.
	public static DatabaseBookTable get()
	{
		return INSTANCE;
	}

	//Usage: dbb = instance.stringToDBB(data);
	//Pre: data has length 7, 6, 5 or 4.
	//Post: dbb has has info as if the contents of data were entered into the DatabaseBook constructor.
	private DatabaseBook stringToDBB(String[] data) throws IllegalArgumentException
	{
		if (data.length == 7)
			return new DatabaseBook(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), data[4], data[5], data[6]);
		else if (data.length == 6)
			return new DatabaseBook(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), data[4], data[5], null);
		else if (data.length == 5)
			return new DatabaseBook(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), data[4], null, null);
		else if (data.length == 4)
			return new DatabaseBook(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), null, null, null);
		else
			throw new IllegalArgumentException("Input, String[], must have length 4 or 7.");
	}
	
	//Usage: databasebook = instance.getBook(ISBN);
	//Pre: Nothing.
	//Post: Returns a DatabaseBook with the specified ISBN.
	public DatabaseBook getBook(int ISBN)
	{
		String SQL = "SELECT * FROM databasebook WHERE isbn = " + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		return temp.isEmpty() ? null : stringToDBB(temp.get(0));
	}

	//Usage: list = instance.searchCategory(category);
	//Pre: Nothing.
	//Post: list is a list of all books in the DatabaseBook database that belong to a category matching the argument category. The parameter must match the category *exactly*.
	public ArrayList<DatabaseBook> searchCategory(String category)
	{
		String SQL = "SELECT * FROM databasebook WHERE category = '" + category + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}

	//Usage: list = instance.searchSubategory(subcategory);
	//Pre: Nothing.
	//Post: list is a list of all books in the DatabaseBook database that belong to a subcategory matching the argument subcategory. The parameter must match the subcategory *exactly*.
	public ArrayList<DatabaseBook> searchSubcategory(String subcategory)
	{
		String SQL = "SELECT * FROM databasebook WHERE subcategory = '" + subcategory + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}

	//Usage: p = instance.exists(ISBN);
	//Pre: Nothing.
	//Post: Returns true if a database book entry exists in the database with an ISBN matching the argument, false otherwise.
	public boolean existsISBN(int ISBN)
	{
		String SQL = "SELECT * FROM databasebook WHERE isbn =" + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		return !temp.isEmpty();
	}

	//Usage: p = instance.exists(title);
	//Pre: Nothing.
	//Post: Returns true if a database book entry exists in the database with a title matching the argument, false otherwise.
	public boolean existsTitle(String title)
	{
		String SQL = "SELECT * FROM databasebook WHERE title ='" + title + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		return !temp.isEmpty();
	}
	
	//Usage: list = instance.getCategories();
	//Pre: Nothing.
	//Post: list is a list of all different categories that exist in the DatabaseBook database.
	public ArrayList<String> getCategories(){
		String SQL = "SELECT DISTINCT category FROM databasebook";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		ArrayList<String> myArray = new ArrayList<String>();
		for (String[] DBB : temp)
			myArray.add(DBB[0]);
		
		return myArray;
	}
	
	//Usage: list = instance.getSubcategories();
	//Pre: Nothing.
	//Post: list includes all different subcategories that exist in the DatabaseBook database.
	public ArrayList<String> getSubcategories(){
		String SQL = "SELECT DISTINCT subcategory FROM databasebook";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		ArrayList<String> myArray = new ArrayList<String>();
		for (String[] DBB : temp)
			myArray.add(DBB[0]);
		
		return myArray;
	}
	
	//Usage: list = instance.getSubFromSuper(category);
	//Pre: Nothing.
	//Post: list includes all subcategories of the category matching the argument.
	public ArrayList<String> getSubFromSuper(String category){
		String SQL = "SELECT DISTINCT subcategory FROM databasebook WHERE category ='" + category + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		ArrayList<String> myArray = new ArrayList<String>();
		for (String[] DBB : temp)
			myArray.add(DBB[0]);
		
		return myArray;
	}
	
	//Usage: list = instance.searchEveryThing(title, author, category, subcategory);
	//Pre: At least one argument must not be null.
	//Post: list includes all database books with title and author similar to parameters, and category and subcategory exactly like paramters.
	public ArrayList<DatabaseBook> searchEverything(String title, String author, String category, String subcategory){
		
		boolean p = false;
		String SQL = "SELECT * FROM databasebook WHERE ";
		
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
		
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}
	
	public static void main(String[] args){
		//get().getBook(1234);
		//get().getBook(1234);
		//get().searchEverything("tit", "auth", "category", null);
	}
}
