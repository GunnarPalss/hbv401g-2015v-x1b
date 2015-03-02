package bookexchange.database.client;

import bookexchange.database.common.interfaces.IConnection;
import bookexchange.database.common.interfaces.IConnectionInfo;

public class DataPort
{
	IConnectionInfo info;
	IConnection conn;
	
	public DataPort(String ip, int port)
	{
		info = new IConnectionInfo(ip, port);
	}
	
	// Return when the Dataport has an active connection
	// with the database or when it has timed out.
	public boolean WaitAndConnect()
	{
		conn = new BEConnection(info);
		// TODO Auto-generated method stub
		return false;
	}
	
	// Close the active connection
	public void CloseConnection()
	{
		conn.Close();
	}
}
