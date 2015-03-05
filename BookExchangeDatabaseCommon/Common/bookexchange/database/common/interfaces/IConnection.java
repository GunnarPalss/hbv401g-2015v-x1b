package bookexchange.database.common.interfaces;

import bookexchange.database.common.Message;

public interface IConnection
{
	public void SendBytes(byte[] bytes);
	
	public Message[] GetMessages();
	
	public void Close();
}
