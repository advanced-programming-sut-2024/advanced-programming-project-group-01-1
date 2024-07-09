package server.model.tournament;

import message.Result;
import server.model.user.User;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class Bracket {

	public static int[] opponent = new int[37];
	public static int[][] nextSeed = {{8, 12}, {9, 13}, {10, 14}, {11, 15}, {20, 18}, {21, 16}, {17, 28}, {19, 29},
			{22, 30}, {23, 31}, {26, 24}, {25, 32}, {27, 34}, {35, 34}};

	static {
		for (int i = 0; i < 28; i++) opponent[i] = i ^ 1;
		for (int i = 28 ; i < 37; i++) opponent[i] = 36;
	}

	User[] players;
	User[] seed = new User[37];
	BracketMatch[] matches = new BracketMatch[14];
	boolean[] matchDone = new boolean[14];
	int[] currentSeed = new int[8];
	User[] placement = new User[8];
	Thread matchThread;

	public Bracket(User[] players) {
		if (players.length != 8) {
			throw new IllegalArgumentException("Bracket must have 8 players");
		}
		List<User> list = Arrays.asList(players);
		Collections.shuffle(list);
		this.players = list.toArray(new User[0]);
		for (int i = 0; i < 8; i++) {
			seed[i] = this.players[i];
			currentSeed[i] = i;
		}
		this.players = players;
	}

	public void start() {
		matchThread = new Thread(() -> {
			while (seed[35] == null) {
				updateMatches();
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {}
			}
			for (int i = 0; i < 8; i++) {
				placement[i] = seed[35 - i];
			}
			endBracket();
		});
		matchThread.start();
	}

	private void updateMatches() {
		for (int i = 0; i < 14; i++) {
			if (matches[i] != null) {
				if (matchDone[i]) continue;
				if (matches[i].winner != null) {
					matchDone[i] = true;
					seed[nextSeed[i][0]] = matches[i].winner;
					seed[nextSeed[i][1]] = matches[i].loser;
				}
			} else {
				if (seed[i * 2] != null && seed[i * 2 + 1] != null)
					matches[i] = new BracketMatch(seed[i * 2], seed[i * 2 + 1]);
			}
		}
	}

	private Result endBracket() {
		matchThread.interrupt();
		String placementString = "";
		for (int i = 1; i <= 4; i++){
			placementString += i + ". " + placement[i - 1].getUsername() + "\n";
		}
		for (int i = 5; i <= 7; i += 2) {
			placementString += i + ". " + placement[i - 1].getUsername() + "\n";
			placementString += i + ". " + placement[i].getUsername() + "\n";
		}
		return new Result(placementString, true);
	}


}
