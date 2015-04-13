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

	DatabaseBook(int ISBN, String title, String authors, int price, String description, String category, String subcategory)
	{
		this.ISBN = ISBN;
		this.title = title;
		this.authors = authors;
		this.price = price;
		this.description = description;
		this.category = category;
		this.subcategory = subcategory;
	}

	DatabaseBook()
	{
		this.ISBN = this.price = 0;
		this.title = this.authors = this.description = this.category = this.subcategory = null;
	}

	DatabaseBook(int ISBN, String title, String authors)
	{
		this.ISBN = ISBN;
		this.title = title;
		this.authors = authors;
		this.price = -1;
		this.description = this.category = this.subcategory = null;
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
}
