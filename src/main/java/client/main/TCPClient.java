package client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import message.Command;
import message.Result;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCPClient {
	private static final Gson gson = new GsonBuilder().create();
	private static String token;

	static {
		try {
			Socket socket = new Socket("localhost", 2357);
			DataOutputStream sendBuffer = new DataOutputStream(socket.getOutputStream());
			sendBuffer.writeUTF(gson.toJson(new Command(null, null)));
			DataInputStream receiveBuffer = new DataInputStream(socket.getInputStream());
			token = gson.fromJson(receiveBuffer.readUTF(), Result.class).getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static Result send(String input) {
		try {
			Socket socket = new Socket("localhost", 2357);
			DataOutputStream sendBuffer = new DataOutputStream(socket.getOutputStream());
			sendBuffer.writeUTF(gson.toJson(new Command(token, input)));
			DataInputStream receiveBuffer = new DataInputStream(socket.getInputStream());
			return gson.fromJson(receiveBuffer.readUTF(), Result.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
