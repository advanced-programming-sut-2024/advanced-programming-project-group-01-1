package server.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import message.Command;
import message.Result;
import server.model.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerWorker extends Thread {
	private static ServerSocket server;
	private static final ArrayList<Socket> connections = new ArrayList<>();
	private static final Gson gson = new GsonBuilder().create();

	static boolean setupServer(int port, int workersCount) {
		try {
			server = new ServerSocket(port);
			for (int i = 0; i < workersCount; i++)
				new ServerWorker().start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void listen() throws IOException {
		while (true) {
			Socket connection = server.accept();
			synchronized (connections) {
				connections.add(connection);
				connections.notify();
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			Socket connection;
			synchronized (connections) {
				while (connections.isEmpty()) {
					try {
						connections.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				connection = connections.remove(0);
			}
			handleConnection(connection);
		}
	}

	private void handleConnection(Socket connection) {
		try {
			DataInputStream receiveBuffer = new DataInputStream(connection.getInputStream());
			DataOutputStream sendBuffer = new DataOutputStream(connection.getOutputStream());
			Command command = gson.fromJson(receiveBuffer.readUTF(), Command.class);
			Result result = null;
			if (command.getToken() == null && command.getCommand() == null)
				result = new Result(new Client().getToken(), true);
			else {
				Client client = Client.getClient(command.getToken());
				System.out.println((client.getIdentity() != null ? client.getIdentity().getUsername() : null) + " " +
						client.getMenu().getClass().getSimpleName() + " " + command.getCommand());
				if (client != null) {
					if (command.getCommand().equals("remove token")) Client.remove(client);
					else result = client.getMenu().run(client, command.getCommand());
				}
			}
			sendBuffer.writeUTF(gson.toJson(result));
			connection.close();
			receiveBuffer.close();
			sendBuffer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
