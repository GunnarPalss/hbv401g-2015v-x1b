/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.client;

import java.util.ArrayList;

public class DatabaseBookScraper
{
	private static final DatabaseBookScraper INSTANCE = new DatabaseBookScraper();

	private DatabaseBookScraper()
	{
	}

	//Usage: instance = DatabaseBookScreaper.get();
	//Pre: Nothing:
	//Post: Returns instance variable INSTANCE.
	public static DatabaseBookScraper get()
	{
		return INSTANCE;
	}

	//Usage: instance.createBook(ISBN, title. authors, price, description, category, subcategory);
	//Pre: description is not null.
	//Post: A database book with info from parameters has been inserted into the database.
	public void createBook(int ISBN, String title, String authors, int price, String description, String category, String subcategory)
	{
		if (existsISBN(ISBN))
			deleteBook(ISBN);

		String SQL = "INSERT INTO databasebook VALUES("
				+ Integer.toString(ISBN) + " ,'"
				+ title + "','"
				+ authors + "',"
				+ Integer.toString(price) + ",'"
				+ description + "','"
				+ category + "','"
				+ subcategory + "')";
		DataPort.get().execute(SQL);
	}

	//Usage: instance.deleteBook(ISBN);
	//Pre: Nothing.
	//Post: The book with the given ISBN has been removed from the table DatabaseBook.
	private void deleteBook(int ISBN)
	{
		String SQL = "DELETE FROM databasebook WHERE isbn = " + Integer.toString(ISBN);
		DataPort.get().execute(SQL);
	}

	//Usage: instance.deleteBook(ISBN);
	//Pre: Nothing.
	//Post: The book with the given title has been removed from the table DatabaseBook.
	private void deleteBook(String title)
	{
		String SQL = "DELETE FROM databasebook WHERE title = '" + title + "'";
		DataPort.get().execute(SQL);
	}

	//Usage: instance.eraseBook(ISBN);
	//Pre: Nothing.
	//Post: The book with the given title has been removed from the table DatabaseBook. All instances of the book have also been removed from the table UserBook.
	public void eraseBook(int ISBN)
	{
		deleteBook(ISBN);
		UserBookTable.get().eraseBook(ISBN);
	}

	//Usage: p = instance.existsISBN(ISBN);
	//Pre: Nothing.
	//Post: Returns true if a book with the given ISBN exists in the table DatabaseBook.
	private boolean existsISBN(int ISBN)
	{
		String SQL = "SELECT * FROM databasebook WHERE isbn =" + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		return !temp.isEmpty();
	}
	
	public static void main(String[] args){
		//get().createBook(1234, "title", "author", 5000, "description", "category", "subcategory");
		//get().createBook((int)0000555F, "title2", "author2", 5000, "description2", "category2", "subcategory2");
		//System.out.println(01234);
	}
}
