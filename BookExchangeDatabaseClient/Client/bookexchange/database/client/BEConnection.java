package bookexchange.database.client;

import bookexchange.database.common.Message;
import bookexchange.database.common.interfaces.IConnection;
import bookexchange.database.common.interfaces.IConnectionInfo;

class BEConnection implements IConnection
{
	
	public BEConnection(IConnectionInfo info)
	{
		// TODO Auto-generated constructor stub
	}
	
	public void SendBytes(byte[] bytes)
	{
		
	}
	
	// Returns all the messages that are waiting since the last call;
	public Message[] GetMessages()
	{
		return null;
	}
	
	public void Close()
	{
		// TODO Auto-generated method stub
		
	}
}
