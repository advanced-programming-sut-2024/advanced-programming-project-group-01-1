package server.model.tournament;

import server.controller.game.TournamentMenuController;
import server.model.game.Game;
import server.model.user.User;

public class BracketMatch {

	User player1, player2;
	Game game;
	boolean isPlayer1Ready = false, isPlayer2Ready = false;
	User winner = null, loser = null;
	Thread matchThread;

	public void setPlayer1(User player1) {
		this.player1 = player1;
	}

	public void setPlayer2(User player2) {
		this.player2 = player2;
	}

	public void setPlayer1Ready() {
		isPlayer1Ready = true;
	}

	public void setPlayer2Ready() {
		isPlayer2Ready = true;
	}

	public void start() {
		matchThread = new Thread(() -> {
			while (true) {
				while (!isPlayer1Ready || !isPlayer2Ready) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
					}
				}
				TournamentMenuController.startBracketMatch(player1, player2);
				while (game == null) {
					game = player1.getCurrentGame();
				}
				while (!game.isGameOver()) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
					}
				}
				isPlayer1Ready = false;
				isPlayer2Ready = false;
				if (game.getWinner() != null) {
					winner = game.getWinner();
					loser = winner == player1 ? player2 : player1;
					break;
				}
			}
		});
		matchThread.start();
	}

	@Override
	public String toString() {
		if (game == null) {
			String result = "";
			if (player1 != null) result += player1.getUsername() + " -1\n";
			else result += " \n";
			if (player2 != null) result += player2.getUsername() + " -1\n";
			else result += " \n";
			return result;
		}
		User current = game.getCurrent();
		return player1.getUsername() + " " + (current == player1 ? 2 - game.getOpponentLife() : 2 - game.getCurrentLife()) +
				"\n" + player2.getUsername() + " " + (current == player2 ? 2 - game.getOpponentLife() : 2 - game.getCurrentLife()) + "\n";
	}

}
