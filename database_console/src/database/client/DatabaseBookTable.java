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

	public static DatabaseBookTable get()
	{
		return INSTANCE;
	}

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

	public DatabaseBook getBook(int ISBN)
	{
		String SQL = "SELECT * FROM databasebook WHERE isbn = " + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		return temp.isEmpty() ? null : stringToDBB(temp.get(0));
	}

	public DatabaseBook getBook(String title)
	{
		String SQL = "SELECT * FROM databasebook WHERE title = '" + title + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		return temp.isEmpty() ? null : stringToDBB(temp.get(0));
	}

	public ArrayList<DatabaseBook> searchTitle(String title)
	{
		String SQL = "SELECT * FROM databasebook WHERE title LIKE '%" + title + "%'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}

	public ArrayList<DatabaseBook> searchAuthor(String author)
	{
		String SQL = "SELECT * FROM databasebook WHERE authors LIKE '%" + author + "%'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}

	//Þetta er nákvæm leit og skilar aðeins flokki sem heitir nákvæmlega leitarorðinu.
	public ArrayList<DatabaseBook> searchCategory(String category)
	{
		String SQL = "SELECT * FROM databasebook WHERE category = '" + category + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}

	//Þetta er nákvæm leit og skilar aðeins undirflokki sem heitir nákvæmlega leitarorðinu.
	public ArrayList<DatabaseBook> searchSubcategory(String subcategory)
	{
		String SQL = "SELECT * FROM databasebook WHERE subcategory = '" + subcategory + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}

	public boolean existsISBN(int ISBN)
	{
		String SQL = "SELECT * FROM databasebook WHERE isbn =" + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		return !temp.isEmpty();
	}

	public boolean existsTitle(String title)
	{
		String SQL = "SELECT * FROM databasebook WHERE title ='" + title + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		return !temp.isEmpty();
	}
	
	public ArrayList<String> getCategories(){
		String SQL = "SELECT DISTINCT category FROM databasebook";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		ArrayList<String> myArray = new ArrayList<String>();
		for (String[] DBB : temp)
			myArray.add(DBB[0]);
		
		return myArray;
	}
	
	public ArrayList<String> getSubcategories(){
		String SQL = "SELECT DISTINCT subcategory FROM databasebook";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		ArrayList<String> myArray = new ArrayList<String>();
		for (String[] DBB : temp)
			myArray.add(DBB[0]);
		
		return myArray;
	}
	
	public ArrayList<String> getSubFromSuper(String category){
		String SQL = "SELECT DISTINCT subcategory FROM databasebook WHERE category ='" + category + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		ArrayList<String> myArray = new ArrayList<String>();
		for (String[] DBB : temp)
			myArray.add(DBB[0]);
		
		return myArray;
	}
	
	public ArrayList<DatabaseBook> searchTitleAndAuthor(String title, String author){
		String SQL = "SELECT * FROM databasebook WHERE authors LIKE '%" + author + "%' AND title LIKE '%" + title + "%'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}
	
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
