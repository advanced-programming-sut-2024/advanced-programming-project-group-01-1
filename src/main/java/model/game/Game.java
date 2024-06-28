package model.game;

import model.card.Card;
import model.card.Leader;
import model.game.space.Row;
import model.game.space.Space;
import model.game.space.WeatherSystem;
import model.user.User;

import java.util.ArrayList;

public class Game {

	public static final int SIEGE_ROW_NUMBER = 0;
	public static final int RANGED_ROW_NUMBER = 1;
	public static final int MELEE_ROW_NUMBER = 2;
	private static Game currentGame;
	User current, opponent;
	Row[] rows = new Row[6];
	WeatherSystem weatherSystem = new WeatherSystem();
	Space currentDiscardPile = new Space(), opponentDiscardPile = new Space();
	Space currentDeck, opponentDeck;
	Space currentHand = new Space(), opponentHand = new Space();
	int currentLife, opponentLife;
	String currentFaction, opponentFaction;
	int currentPower, opponentPower;
	boolean hasOpponentPassed;
	Leader currentLeader, opponentLeader;

	private Game(User player1, User player2) {
		this.current = player1;
		this.opponent = player2;
		this.currentFaction = player1.getDeck().getFaction();
		this.opponentFaction = player2.getDeck().getFaction();
		this.currentLeader = player1.getDeck().getLeader();
		this.opponentLeader = player2.getDeck().getLeader();
		this.currentDeck = new Space(player1.getDeck().getCards());
		this.opponentDeck = new Space(player2.getDeck().getCards());
	}

	public static Game createGame(User player1, User player2) {
		return currentGame = new Game(player1, player2);
	}

	public static Game getCurrentGame() {
		return currentGame;
	}

	public String getCurrentFaction() {
		return currentFaction;
	}

	public int getCurrentLife() {
		return currentLife;
	}

	public int getOpponentLife() {
		return opponentLife;
	}

	public int getCurrentPower() {
		return currentPower;
	}

	public int getOpponentPower() {
		return opponentPower;
	}

	public int getRowPower(int rowNumber) {
		return rows[rowNumber].getSumOfPowers();
	}

	public ArrayList<Card> getCurrentDeck() {
		return currentDeck.getCards();
	}

	public ArrayList<Card> getCurrentHand() {
		return currentHand.getCards();
	}

	public ArrayList<Card> getCurrentDiscardPile() {
		return currentDiscardPile.getCards();
	}

	public WeatherSystem getWeatherSystem() {
		return weatherSystem;
	}

	public String getCurrentUsername() {
		return current.getUsername();
	}

	public String getOpponentUsername() {
		return opponent.getUsername();
	}

	public int getCurrentNumberOfCardsInHand() {
		return currentHand.getCards().size();
	}

	public int getOpponentNumberOfCardsInHand() {
		return opponentHand.getCards().size();
	}

	public Leader getCurrentLeader() {
		return currentLeader;
	}

	public Row getRow(int index) {
		return rows[index];
	}


	public Row getEnemy(Row row) {
		for (int i = 0; i < 6; i++) {
			if (row == rows[i]) return rows[5 - i];
		}
		return null;
	}


	public void changeTurn() {
		// TODO:
		return;
	}

	public void selectFaction() {
		// TODO:
		return;
	}

	public void setCurrentLeader(Leader currentLeader) {
		this.currentLeader = currentLeader;
	}

	public void setCurrentDeck(Space currentDeck) {
		this.currentDeck = currentDeck;
	}

	public void addToDeck(Card card) {
		// TODO:
		return;
	}

	public void removeFromDeck(Card card) {
		// TODO:
		return;
	}

	public void startGame() {
		// TODO:
		return;
	}

	public void vetoCard(Card card) {
		// TODO:
		return;
	}

	public void placeCard(Card card, int spaceId) {
		// TODO:
		return;
	}

	public void useLeaderAbility() {
		// TODO:
		return;
	}

	public void passTurn() {
		endTurn();
		hasOpponentPassed = true;
		return;
	}

	public void endTurn() {
		// TODO:
		return;
	}

	public void endRound() {
		// TODO:
		return;
	}

}
