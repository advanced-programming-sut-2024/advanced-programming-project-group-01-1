package server.main;

import server.controller.JsonController;

public class Main {
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
					@Override
					public void run() {
						JsonController.save();
					}
				}, "Shutdown-thread"));
		JsonController.load();
	}
}