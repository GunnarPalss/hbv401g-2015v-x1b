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
	public Order CreateSetSQLOrder(String sql)
	{
		return new Order("SQLSet", sql);
	}
	
	public Order CreateGetSQLOrder(String sql)
	{
		return new Order("SQLGet", sql);
	}
}
