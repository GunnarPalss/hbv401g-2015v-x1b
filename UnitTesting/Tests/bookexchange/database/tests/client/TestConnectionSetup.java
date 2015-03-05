package bookexchange.database.tests.client;

import org.junit.Test;

import bookexchange.database.client.DataPort;

public class TestConnectionSetup
{
	@Test
	public void SetupConnection()
	{
		// Connection is tested
		DataPort conn = new DataPort("127.0.0.1", 6996);
		
		conn.WaitAndConnect();
	}
}
