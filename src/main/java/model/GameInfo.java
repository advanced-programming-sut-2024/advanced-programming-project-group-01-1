package model;

import model.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class GameInfo implements Serializable {

	private int opponentId;
	private Date date;
	private ArrayList<Integer> opponentScores, myScores;
	private int opponentFinalScore, myFinalScore;
	private int winnerId;

	public GameInfo(User opponent, ArrayList<Integer> opponentScores, ArrayList<Integer> myScores, int opponentFinalScore, int myFinalScore, User winner) {
		this.opponentId = opponent.getId();
		this.date = new Date();
		this.opponentScores = opponentScores;
		this.myScores = myScores;
		this.opponentFinalScore = opponentFinalScore;
		this.myFinalScore = myFinalScore;
		this.winnerId = winner.getId();
	}

	public int getOpponentId() {
		return opponentId;
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
		return this.opponentFinalScore;
	}

	public int getMyFinalScore() {
		return this.myFinalScore;
	}

	public int getWinnerId() {
		return this.winnerId;
	}
}
