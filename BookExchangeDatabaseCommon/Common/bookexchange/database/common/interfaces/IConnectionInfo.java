package bookexchange.database.common.interfaces;

public class IConnectionInfo
{
	public final String IP;
	public final int PORT;
	
	public IConnectionInfo(String ip, int port)
	{
		IP = ip;
		PORT = port;
	}
}
