package org.simplejavamail.internal.clisupport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GenerateCliHelpTest {

	private PrintStream sysOut;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@BeforeEach
	public void setUpStreams() {
		System.out.println("GenerateCliHelpTest.setUpStreams(): DISABLING System.out during CLI debug logging");
		sysOut = System.out;
		System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	public void revertStreams() {
		System.setOut(sysOut);
	}

	@Test
	public void testListAllUsagesWithoutError() {
		CliSupport.listUsagesForAllOptions();
	}

	@Test
	public void testListHelpForSendWithoutError() {
		CliSupport.runCLI(new String[]{"send", "--help"});
	}

	@Test
	public void testUsagesThatContainsPercentS() {
		CliSupport.runCLI(new String[] {"send", "--email:replyingTo--help",});
	}

	@Test
	public void testListHelpForConnectWithoutError() {
		CliSupport.runCLI(new String[]{"connect", "--help"});
	}

	@Test
	public void testListHelpForValidateWithoutError() {
		CliSupport.runCLI(new String[]{"validate", "--help"});
	}

	@Test
	public void testListHelpForForwardingWithoutError() {
		CliSupport.runCLI(new String[] {"send", "--email:forwarding--help",});
	}

	@Test
	public void testListHelpForClearProxyWithoutError() {
		CliSupport.runCLI(new String[] {"send", "--mailer:clearProxy--help",});
	}

	@Test
	public void testListHelpForAsyncWithoutError() {
		CliSupport.runCLI(new String[] {"send", "--mailer:async--help",});
	}

	@Test
	public void testListRootHelpWithoutError() {
		CliSupport.runCLI(new String[]{ "--help" });
	}
}