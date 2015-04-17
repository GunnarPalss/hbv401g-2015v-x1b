/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.client;

public class UserBook
{

	public final int instanceID;
	public final int accountID;
	public final int ISBN;
	private int userPrice;
	private String condition;

	UserBook(int instanceID, int accountID, int ISBN, int userPrice, String condition)
	{
		this.instanceID = instanceID;
		this.accountID = accountID;
		this.ISBN = ISBN;
		this.userPrice = userPrice;
		this.condition = condition;
	}

	public void editPrice(int price)
	{
		this.userPrice = price;
		String SQL = "UPDATE userbook SET userprice = " + Integer.toString(price) + " WHERE instanceid = " + this.instanceID;
		DataPort.get().execute(SQL);
	}

	public void editCondition(String condition)
	{
		this.condition = condition;
		String SQL = "UPDATE userbook SET condition = '" + condition + "' WHERE instanceid = " + this.instanceID;
		DataPort.get().execute(SQL);
	}

	public int getPrice()
	{
		return this.userPrice;
	}

	public String getCondition()
	{
		return this.condition;
	}

	public void print()
	{
		System.out.println("instanceID: " + this.instanceID + " accountID: " + this.accountID + " ISBN: " + this.ISBN + " userPrice: " + this.userPrice + " condition: " + this.condition);
	}
}
