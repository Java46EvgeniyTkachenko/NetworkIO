package telran.util;

import java.io.PrintStream;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TcpClientHandler implements Handler {
	private PrintStream stream;
	public TcpClientHandler(PrintStream stream) {
		super();
		this.stream = stream;
	}

	@Override
	public void publish(LoggerRecord loggerRecord) {
		LocalDateTime ldt = LocalDateTime.ofInstant(loggerRecord.timestamp,
				ZoneId.of(loggerRecord.zoneId));
		stream.printf("log# %s %s %s %s\n", ldt, loggerRecord.level,
				loggerRecord.loggerName, loggerRecord.message);

	}

	@Override
	public void close() {
		this.stream.close();

	}


}
