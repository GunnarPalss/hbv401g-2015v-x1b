package bookexchange.database.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import bookexchange.database.tests.client.TestConnection;
import bookexchange.database.tests.client.TestConnectionSetup;

public class Tester
{
	public static void main(String[] args)
	{
		Result result = JUnitCore.runClasses(AllTests.class);
		for (Failure failure : result.getFailures())
		{
			System.out.println(failure.toString());
		}
	}
}

@RunWith(Suite.class)
@SuiteClasses(
{ TestConnectionSetup.class, TestConnection.class })
class AllTests
{
	
}
