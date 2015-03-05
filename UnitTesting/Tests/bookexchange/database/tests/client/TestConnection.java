package bookexchange.database.tests.client;

import org.junit.BeforeClass;
import org.junit.Test;

import bookexchange.database.client.DataPort;

public class TestConnection
{
	static DataPort conn;
	
	@BeforeClass
	public static void SetupConnection()
	{
		// Connection is tested
		conn = new DataPort("127.0.0.1", 6996);
		
		conn.WaitAndConnect();
	}
	
	@Test
	public void TestSending()
	{
		
	}
	
	@Test
	public void TestRecieving()
	{
		
	}
}
