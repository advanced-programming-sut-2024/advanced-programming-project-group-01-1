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
	private final int myRoundScore, opponentRoundScore;
	private final String result;

	public GameInfo(User opponent, int myLife, int opponentLife, ArrayList<Integer> opponentScores, ArrayList<Integer> myScores, User winner) {
		this.opponentId = opponent.getId();
		this.date = new Date();
		this.opponentScores = opponentScores;
		myRoundScore = 2 - myLife;
		opponentRoundScore = 2 - opponentLife;
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

	public int getOpponentRoundScore() {
		return opponentRoundScore;
	}

	public int getMyRoundScore() {
		return myRoundScore;
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
				"My Round Score: " + this.getMyRoundScore() + "\n" +
				"Opponent Round Score: " + this.getOpponentRoundScore() + "\n" +
				"Winner: " + User.getUserById(this.winnerId).getNickname();
	}
}
