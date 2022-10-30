package telran.util;

import static org.junit.Assert.assertEquals;
import java.io.*;
import java.net.Socket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import telran.net.ServerLoggerAppl;


class LoggerServerTests {

	static PrintStream output;
	static BufferedReader input;
	static Socket socket;
	static Handler handler;
	static Logger logger;
	static BufferedReader reader;

	@BeforeAll
	static void setUp() throws Exception {
		socket = new Socket("localhost", ServerLoggerAppl.PORT);
		output = new PrintStream(socket.getOutputStream());
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		handler = new TcpClientHandler(output);
		logger = new Logger(handler, "test-logger");
		reader = new BufferedReader(input);
	}

	@AfterAll
	static void closeConnection() throws Exception {
		socket.close();
	}
	
	@Test
	@Order(1)
	void AtestWriteError() throws Exception {
		logger.setLevel(Level.ERROR);
		logger.error("error message");

		String response = input.readLine();
		assertEquals("Ok", response);

		logger.error("error message");
		response = input.readLine();
		assertEquals("Ok", response);
	}

	@Test
	@Order(2)
	void BtestWriteWarn() throws Exception {
		logger.setLevel(Level.WARN);
		logger.warn("warn message");

		String response = input.readLine();
		assertEquals("Ok", response);

	}

	@Test
	@Order(3)
	void CtestWriteInfo() throws Exception {
		logger.setLevel(Level.INFO);
		logger.info("info message");

		String response = input.readLine();
		assertEquals("Ok", response);

	}

	@Test
	@Order(4)
	void DtestWriteDebug() throws Exception {
		logger.setLevel(Level.DEBUG);
		logger.debug("debug message");

		String response = input.readLine();
		assertEquals("Ok", response);

	}

	@Test
	@Order(5)
	void EtestWriteTrace() throws Exception {
		logger.setLevel(Level.TRACE);
		logger.trace("trace message");
		String response = input.readLine();
		assertEquals("Ok", response);
	}

	@Test
	@Order(6)
	void FcountTestTRACE() throws Exception {
		output.println("counter#trace");
		String response = input.readLine();
		assertEquals("1", response);
	}

	@Test
	@Order(7)
	void GcountTestERROR() throws Exception {
		output.println("counter#error");
		String response = input.readLine();
		assertEquals("2", response);
	}

	@Test
	@Order(8)
	void HcountTestWARN() throws Exception {
		output.println("counter#warn");
		String response = input.readLine();
		assertEquals("1", response);
	}

	@Test
	@Order(9)
	void IcountTestINFO() throws Exception {
		output.println("counter#info");
		String response = input.readLine();
		assertEquals("1", response);
	}

	@Test
	@Order(10)
	void JcountTestDEBUG() throws Exception {
		output.println("counter#debug");
		String response = input.readLine();
		assertEquals("1", response);
	}

}
