package bookexchange.database.common;

public class Message
{
	public final int ID;
	public final byte[] INFO;
	
	public Message(int id, byte[] info)
	{
		ID = id;
		INFO = info;
	}
}
