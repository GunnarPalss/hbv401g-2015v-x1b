package bookexchange.database.client;

public class DataPort
{
	BEConnectionInfo info;
	BEConnection conn;
	
	public DataPort(String ip, int port)
	{
		info = new BEConnectionInfo(ip, port);
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
