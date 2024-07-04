package server.main;

import server.controller.JsonController;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
					@Override
					public void run() {
						JsonController.save();
					}
				}, "Shutdown-thread"));
		JsonController.load();
		ServerWorker.setupServer(2357, 5);
		try {
			ServerWorker.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}