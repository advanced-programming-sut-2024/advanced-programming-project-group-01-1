package model;

import model.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class GameInfo implements Serializable {

	private final int opponentId;
	private final Date date;
	private final ArrayList<Integer> opponentScores, myScores;
	private final int winnerId;
	private final String result;

	public GameInfo(User opponent, ArrayList<Integer> opponentScores, ArrayList<Integer> myScores, User winner) {
		this.opponentId = opponent.getId();
		this.date = new Date();
		this.opponentScores = opponentScores;
		this.myScores = myScores;
		if (winner != null) this.winnerId = winner.getId();
		else this.winnerId = -1;
		if (this.winnerId == -1) this.result = "Draw";
		else if (this.winnerId == opponentId) this.result = "Lose";
		else this.result = "Win";
	}

	public String getOpponentUsername() {
		return User.getUserById(this.opponentId).getUsername();
	}

	public Date getDate() {
		return new Date(String.valueOf(this.date));
	}

	public ArrayList<Integer> getOpponentScores() {
		return new ArrayList<>(this.opponentScores);
	}

	public ArrayList<Integer> getMyScores() {
		return new ArrayList<>(this.myScores);
	}

	public int getOpponentFinalScore() {
		return this.opponentScores.get(0) + this.opponentScores.get(1) + this.opponentScores.get(2);
	}

	public int getMyFinalScore() {
		return this.myScores.get(0) + this.myScores.get(1) + this.myScores.get(2);
	}

	public int getWinnerId() {
		return this.winnerId;
	}

	@Override
	public String toString() {
		return "Opponent: " + User.getUserById(this.opponentId).getNickname() + "\n" +
				"Date: " + this.date + "\n" +
				"Opponent Scores: " + this.opponentScores + "\n" +
				"My Scores: " + this.myScores + "\n" +
				"Opponent Final Score: " + this.getOpponentFinalScore() + "\n" +
				"My Final Score: " + this.getMyFinalScore() + "\n" +
				"Winner: " + User.getUserById(this.winnerId).getNickname();
	}
}
