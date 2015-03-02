package bookexchange.database.client;

class BEConnection
{
	
	public BEConnection(BEConnectionInfo info)
	{
		// TODO Auto-generated constructor stub
	}
	
	public void Close()
	{
		// TODO Auto-generated method stub
		
	}
}

class BEConnectionInfo
{
	public final String IP;
	public final int PORT;
	
	public BEConnectionInfo(String ip, int port)
	{
		IP = ip;
		PORT = port;
	}
}
