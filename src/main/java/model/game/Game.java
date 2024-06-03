package model.game;

import model.User;
import model.card.Card;
import model.game.space.Row;
import model.game.space.Space;
import model.game.space.WeatherSystem;
import model.leader.Leader;

import java.util.ArrayList;

public class Game {

	private static Game currentGame;
	User current, opponent;
	Row[] rows = new Row[6];
	WeatherSystem weatherSystem;
	Space currentDiscordPile, opponentDiscordPile;
	Space currentDeck, opponentDeck;
	Space currentHand, opponentHand;
	int currentLife, opponentLife;
	String currentFaction, opponentFaction;
	int currentPower, opponentPower;
	boolean hasOpponentPassed;
	Leader currentLeader, opponentLeader;

	private Game(User player1, User player2) {
		this.current = player1;
		this.opponent = player2;
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
		return new ArrayList<>(currentDeck.getCards());
	}

	public ArrayList<Card> getCurrentHand() {
		return new ArrayList<>(currentHand.getCards());
	}

	public ArrayList<Card> getCurrentDiscordPile() {
		return new ArrayList<>(currentDiscordPile.getCards());
	}

	public ArrayList<Card> getWeatherSystem() {
		return new ArrayList<>(weatherSystem.getCards());
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
