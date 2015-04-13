/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.client;

import java.util.ArrayList;

/*
 Scraper team, note that there is a specific DatabaseBook class for you
 called DatabaseBookScraper.java. This class is intended for the UI team.
 */
public class DatabaseBook
{

	public final int ISBN;
	public final String title;
	public final String authors;
	private final int price;
	private final String description;
	private final String category;
	private final String subcategory;

	private DatabaseBook(int ISBN, String title, String authors, int price, String description, String category, String subcategory)
	{
		this.ISBN = ISBN;
		this.title = title;
		this.authors = authors;
		this.price = price;
		this.description = description;
		this.category = category;
		this.subcategory = subcategory;
	}

	private DatabaseBook()
	{
		this.ISBN = this.price = 0;
		this.title = this.authors = this.description = this.category = this.subcategory = null;
	}

	private DatabaseBook(int ISBN, String title, String authors)
	{
		this.ISBN = ISBN;
		this.title = title;
		this.authors = authors;
		this.price = -1;
		this.description = this.category = this.subcategory = null;
	}

	private static DatabaseBook stringToDBB(String[] data) throws IllegalArgumentException
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

	public static DatabaseBook getBook(int ISBN)
	{
		String SQL = "SELECT * FROM databasebook WHERE isbn = " + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		return stringToDBB(temp.get(0));
	}

	public static DatabaseBook getBook(String title)
	{
		String SQL = "SELECT * FROM databasebook WHERE title = '" + title + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		return stringToDBB(temp.get(0));
	}

	public static ArrayList<DatabaseBook> searchTitle(String title)
	{
		String SQL = "SELECT * FROM databasebook WHERE title LIKE '%" + title + "%'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<DatabaseBook>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}

	public static ArrayList<DatabaseBook> searchAuthor(String author)
	{
		String SQL = "SELECT * FROM databasebook WHERE authors LIKE '%" + author + "%'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<DatabaseBook>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}

	//Þetta er nákvæm leit og skilar aðeins flokki sem heitir nákvæmlega leitarorðinu.
	public static ArrayList<DatabaseBook> searchCategory(String category)
	{
		String SQL = "SELECT * FROM databasebook WHERE category = '" + category + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<DatabaseBook>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}

	//Þetta er nákvæm leit og skilar aðeins undirflokki sem heitir nákvæmlega leitarorðinu.
	public static ArrayList<DatabaseBook> searchSubcategory(String subcategory)
	{
		String SQL = "SELECT * FROM databasebook WHERE subcategory = '" + subcategory + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 7);
		ArrayList<DatabaseBook> dbbArray = new ArrayList<DatabaseBook>();
		for (String[] DBB : temp)
			dbbArray.add(stringToDBB(DBB));

		return dbbArray;
	}

	public static boolean existsISBN(int ISBN)
	{
		String SQL = "SELECT * FROM databasebook WHERE isbn =" + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		return !temp.isEmpty();
	}

	public static boolean existsTitle(String title)
	{
		String SQL = "SELECT * FROM databasebook WHERE title ='" + title + "'";
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		return !temp.isEmpty();
	}
	/*
	 public int getPrice() {
	 return this.price;
	 }

	 public String getDescription() {
	 return this.description;
	 }

	 public String getCategory(){
	 return this.category;
	 }
        
	 public String getSubcategory(){
	 return this.subcategory;
	 }
	 */

	public void print()
	{
		System.out.println("ISBN: " + Integer.toString(this.ISBN) + " title: "
				+ this.title + " author(s): " + this.authors + " price: " + Integer.toString(this.price)
				+ " description: " + this.description + " category: " + this.category + " subcategory: " + this.subcategory);
	}

	public static void main(String[] args)
	{
		System.out.println("This is DatabaseBook");
		DataPort.get().connect();

		//Tests
		DatabaseBook.getBook(9999).print();
		DatabaseBook.getBook("test_title").print();
		DatabaseBook.searchTitle("titl").get(0).print();
		DatabaseBook.searchAuthor("auth").get(0).print();
		DatabaseBook.searchCategory("test_category").get(0).print();
		DatabaseBook.searchSubcategory("test_subcategory").get(0).print();
		System.out.println(DatabaseBook.existsISBN(1000));
		System.out.println(DatabaseBook.existsISBN(9999));
		System.out.println(DatabaseBook.existsTitle("test_title"));
		System.out.println(DatabaseBook.existsTitle("asdf"));

		DataPort.get().disconnect();
	}
}
