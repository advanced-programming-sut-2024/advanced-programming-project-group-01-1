package server.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import message.Command;
import message.Result;

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

	private static boolean setupServer(int port, int workersCount) {
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
			Result result = Appview.getMenu().run(gson.fromJson(receiveBuffer.readUTF(), Command.class));
			sendBuffer.writeUTF(gson.toJson(result));
			connection.close();
			receiveBuffer.close();
			sendBuffer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		setupServer(2357, 5);
		try {
			listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
