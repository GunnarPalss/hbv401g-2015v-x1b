/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.client;

import java.util.ArrayList;

public class DatabaseBookScraper
{

	public static void createBook(int ISBN, String title, String authors, int price, String description, String category, String subcategory)
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

	private static void deleteBook(int ISBN)
	{
		String SQL = "DELETE FROM databasebooks WHERE isbn = " + Integer.toString(ISBN);
		DataPort.get().execute(SQL);
	}

	private static void deleteBook(String title)
	{
		String SQL = "DELETE FROM databasebook WHERE title = '" + title + "'";
		DataPort.get().execute(SQL);
	}

	//Ath. fallið createBook notar fallið deleteBook. Þess vegna er annað fall, eraseBook, til að fjarlægja einnig öll UserBook með sama ISBN.
	public static void eraseBook(int ISBN)
	{
		deleteBook(ISBN);
		UserBook.deleteBook(ISBN);
	}

	private static boolean existsISBN(int ISBN)
	{
		String SQL = "SELECT * FROM databasebook WHERE isbn =" + Integer.toString(ISBN);
		ArrayList<String[]> temp = DataPort.get().executeAndReturn(SQL, 1);
		return !temp.isEmpty();
	}

	public static void main(String args[])
	{
		System.out.println("This is DatabaseBook");
		DataPort.get().connect();

		//Gefur villu einhverra hluta vegna:
		DatabaseBookScraper.createBook(1111, "asdf", "create_author", 666, "create_descr", "create_categ", "create_subcateg");

		DataPort.get().disconnect();
	}

}
