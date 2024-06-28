package model.game;

import model.card.Card;
import model.card.Leader;
import model.card.special.spell.Weather;
import model.game.space.Row;
import model.game.space.Space;
import model.user.User;

import java.util.ArrayList;

public class Game {

	public static final int SIEGE_ROW_NUMBER = 0;
	public static final int RANGED_ROW_NUMBER = 1;
	public static final int MELEE_ROW_NUMBER = 2;
	private static Game currentGame;
	User current, opponent;
	Row[] rows = new Row[6];
	Space currentWeatherSystem = new Space(), opponentWeatherSystem = new Space();
	Space currentDiscardPile = new Space(), opponentDiscardPile = new Space();
	Space currentDeck, opponentDeck;
	Space currentHand = new Space(), opponentHand = new Space();
	int currentLife = 2, opponentLife = 2;
	String currentFaction, opponentFaction;
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
		int currentPower = 0;
		for (int i = 0; i < 3; i++) currentPower += rows[i].getSumOfPowers();
		return currentPower;
	}

	public int getOpponentPower() {
		int opponentPower = 0;
		for (int i = 3; i < 6; i++) opponentPower += rows[i].getSumOfPowers();
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

	public Space getCurrentDiscardPile() {
		return currentDiscardPile;
	}

	public Space getOpponentDiscardPile() {
		return opponentDiscardPile;
	}

	public Space getCurrentWeatherSystem() {
		return currentWeatherSystem;
	}

	public Space getOpponentWeatherSystem() {
		return opponentWeatherSystem;
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

	public void setCurrentLeader(Leader currentLeader) {
		this.currentLeader = currentLeader;
	}

	public void setCurrentDeck(Space currentDeck) {
		this.currentDeck = currentDeck;
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

	}

	public void useLeaderAbility() {
		// TODO:
		return;
	}


	public void changeTurn() {
		if (hasOpponentPassed) return;
		for (int i = 0; i < 3; i++){
			Row temp = rows[i];
			rows[i] = rows[5 - i];
			rows[5 - i] = temp;
		}
		Space temp = currentHand;
		currentHand = opponentHand;
		opponentHand = temp;
		temp = currentDeck;
		currentDeck = opponentDeck;
		opponentDeck = temp;
		temp = currentDiscardPile;
		currentDiscardPile = opponentDiscardPile;
		opponentDiscardPile = temp;
		temp = currentWeatherSystem;
		currentWeatherSystem = opponentWeatherSystem;
		opponentWeatherSystem = temp;
		int tempInt = currentLife;
		currentLife = opponentLife;
		opponentLife = tempInt;
		String tempString = currentFaction;
		currentFaction = opponentFaction;
		opponentFaction = tempString;
		Leader tempLeader = currentLeader;
		currentLeader = opponentLeader;
		opponentLeader = tempLeader;
		User tempUser = current;
		current = opponent;
		opponent = tempUser;
	}

	public void passTurn() {
		changeTurn();
		hasOpponentPassed = true;
	}

	public void endRound() {
		if (getCurrentPower() <= getOpponentPower()) currentLife--;
		if (getCurrentPower() >= getOpponentPower()) opponentLife--;
		for (int i = 0 ; i < 6; i++) {
			try {
				rows[i].clear(i < 3 ? currentDiscardPile : opponentDiscardPile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			currentWeatherSystem.clear(currentDiscardPile);
			opponentWeatherSystem.clear(opponentDiscardPile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (currentLife == 0 || opponentLife == 0) endGame();
		else {
			hasOpponentPassed = false;
			changeTurn();
		}
	}

	private void endGame() {
		if (currentLife == 0 && opponentLife == 0) {
			// Draw
		} else if (currentLife == 0) {
			// Lose
		} else {
			// Win
		}
	}

}
