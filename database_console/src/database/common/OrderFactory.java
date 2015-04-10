/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.common;

/**
 *
 * @author Wolf
 */
public class OrderFactory
{
	public Order CreateSQLOrder(String target)
	{
		return new Order("SQL", target);
	}
}
