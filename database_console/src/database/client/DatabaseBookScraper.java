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

	public static DatabaseBookScraper get()
	{
		return INSTANCE;
	}

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

	private void deleteBook(int ISBN)
	{
		String SQL = "DELETE FROM databasebook WHERE isbn = " + Integer.toString(ISBN);
		DataPort.get().execute(SQL);
	}

	private void deleteBook(String title)
	{
		String SQL = "DELETE FROM databasebook WHERE title = '" + title + "'";
		DataPort.get().execute(SQL);
	}

	//Ath. fallið createBook notar fallið deleteBook. Þess vegna er annað fall, eraseBook, til að fjarlægja einnig öll UserBook með sama ISBN.
	public void eraseBook(int ISBN)
	{
		deleteBook(ISBN);
		UserBookTable.get().eraseBook(ISBN);
	}

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
