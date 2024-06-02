package view;

import java.util.Scanner;

public class Terminal {

	public void run () {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			String input = scanner.nextLine();
			Appview.getMenu().run(input);
		}
	}
}
