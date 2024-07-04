package client.view;

import java.util.Scanner;

public class Terminal extends Thread {

	@Override
	public void run () {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			String input = scanner.nextLine();
			System.out.println(ClientAppview.getMenu().run(input));
		}
	}

}
