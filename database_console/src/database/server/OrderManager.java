/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.server;

import database.common.Order;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;

/**
 *
 * @author Wolf
 */
public class OrderManager
{
	HashMap<String, PreparedStatement> sql;
	
	private OrderManager() {}
	
	public void initialize(DatabaseController dc)
	{
		sql = new HashMap<>();
		sql.put("insertDB", dc.createStatement("INSERT INTO DATABASEBOOK VALUES(?, ?, ?, ?, ?, ?, ?)"));
		sql.put("insertUA", dc.createStatement("INSERT INTO USERACCOUNT VALUES(, ?, ?)"));
		sql.put("insertUB", dc.createStatement("INSERT INTO USERBOOK VALUES(, ?, ?, ?, ?)"));
	}
	
	public void resolveOrder(Server s, Order o)
	{
		
	}
}
