package telran.net;

import java.io.*;
import java.net.*;
import java.util.HashMap;

import telran.util.Level;

public class ServerLoggerAppl {
	private static HashMap<String, Integer> tableMessage = new HashMap<>();
	public static int PORT = 3000;

	public static void main(String[] args) throws Exception {
		Integer countMessage = 0;
		for (Level value : Level.values()) {
			tableMessage.put(value.toString(), countMessage);
		}
		;
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server is listening to connections on port " + PORT);
		while (true) {
			Socket socket = serverSocket.accept();
			runProtocol(socket);
		}
	}

	private static void runProtocol(Socket socket) {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream output = new PrintStream(socket.getOutputStream());
			while (true) {
				String request = input.readLine();
				if (request == null) {
					break;
				}
				String response = getResponse(request);
				output.println(response);
			}
		} catch (IOException e) {
			System.out.println("client has closed connection improperly");
		}
	}

	private static String getResponse(String request) {
		String[] tokens = request.split("#");
		String response = "";
		if (tokens.length != 2) {
			response = "Wrong request: should be <type>#<string";
		} else if (tokens[0].equals("log")) {
			String[] typeMessage = tokens[1].split(" ");
			Integer tmpMessage = tableMessage.get(typeMessage[2]);
			if (tmpMessage != null) {
				tableMessage.put(typeMessage[2], ++tmpMessage);
				response = "Ok";
			}
			;
		} else if (tokens[0].equals("counter")) {
			String typeCase = tokens[1].toUpperCase();
			Integer tmpMessage = tableMessage.get(typeCase);
			if (tmpMessage != null) {
				response = tmpMessage.toString();
			}
			;
		} else {
			response = "Wrong request type: should be either 'log' or 'counter'";
		}
		return response;
	}
}
