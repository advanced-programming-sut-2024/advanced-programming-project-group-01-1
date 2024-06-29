package model.game;

import model.card.Card;
import model.card.unit.Unit;
import model.game.space.Row;
import model.game.space.Space;
import model.leader.Leader;
import model.user.User;

import java.util.ArrayList;
import java.util.Random;

public class Game {

	public static final int SIEGE_ROW_NUMBER = 0;
	public static final int RANGED_ROW_NUMBER = 1;
	public static final int MELEE_ROW_NUMBER = 2;
	public static final int CURRENT_DECK = 6, OPPONENT_DECK = 9;
	public static final int CURRENT_HAND = 7, OPPONENT_HAND = 10;
	public static final int CURRENT_DISCARD_PILE = 8, OPPONENT_DISCARD_PILE = 11;
	private static Game currentGame;



	User current, opponent;
	int roundNumber = 1;
	Row[] rows = new Row[6];
	Space currentWeatherSystem = new Space(), opponentWeatherSystem = new Space();
	Space currentDiscardPile = new Space(), opponentDiscardPile = new Space();
	Space currentDeck, opponentDeck;
	Space currentHand = new Space(), opponentHand = new Space();
	int currentLife = 2, opponentLife = 2;
	String currentFaction, opponentFaction;
	boolean hasOpponentPassed;
	Leader currentLeader, opponentLeader;
	private boolean isLeaderAbilityDisabled = false, isSpyPowerDoubled = false,
					isDebuffWeakened = false, isMedicRandom = false;

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

	public Space getSpaceById(int id) {
		switch (id) {
			case CURRENT_DECK:
				return currentDeck;
			case OPPONENT_DECK:
				return opponentDeck;
			case CURRENT_HAND:
				return currentHand;
			case OPPONENT_HAND:
				return opponentHand;
			case CURRENT_DISCARD_PILE:
				return currentDiscardPile;
			case OPPONENT_DISCARD_PILE:
				return opponentDiscardPile;
			case -1:
				return null;
			default:
				return rows[id];
		}
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

	public Space getCurrentDeck() {
		return currentDeck;
	}

	public Space getCurrentHand() {
		return currentHand;
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

	public boolean isLeaderAbilityDisabled() {
		return isLeaderAbilityDisabled;
	}

	public boolean isSpyPowerDoubled() {
		return isSpyPowerDoubled;
	}

	public boolean isDebuffWeakened() {
		return isDebuffWeakened;
	}

	public boolean isMedicRandom() {
		return isMedicRandom;
	}

	public void setLeaderAbilityDisabled(boolean leaderAbilityDisabled) {
		isLeaderAbilityDisabled = leaderAbilityDisabled;
	}

	public void setSpyPowerDoubled(boolean spyPowerDoubled) {
		isSpyPowerDoubled = spyPowerDoubled;
	}

	public void setDebuffWeakened(boolean debuffWeakened) {
		isDebuffWeakened = debuffWeakened;
	}

	public void setMedicRandom(boolean medicRandom) {
		isMedicRandom = medicRandom;
	}

	public void vetoCard(Card card) {
		// TODO:
		return;
	}

	public void placeCard(Card card, int spaceId) {
		try {
			card.put(spaceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void useLeaderAbility() {
		if (isLeaderAbilityDisabled) return;
	}


	public void changeTurn() {
		if (hasOpponentPassed) return;
		for (int i = 0; i < 3; i++) {
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
		roundNumber++;
		int roundResult = getRoundResult();
		if (roundResult <= 0) currentLife--;
		if (roundResult >= 0) opponentLife--;
		if (roundResult == 1 && currentFaction.equals("Northern Realms")) drawFromDeck(currentDeck, currentHand);
		else if (roundResult == -1 && opponentFaction.equals("Northern Realms")) drawFromDeck(opponentDeck, opponentHand);
		if (roundNumber == 3) skelligeAbility();
		Unit currentUnit = currentFaction.equals("Monsters") ? keepUnit(true) : null;
		Unit opponentUnit = opponentFaction.equals("Monsters") ? keepUnit(false) : null;
		for (int i = 0; i < 6; i++) {
			try {
				rows[i].clear(i < 3 ? currentDiscardPile : opponentDiscardPile, i < 3 ? currentUnit : opponentUnit);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (currentUnit != null) {
			try {
				currentUnit.put(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			currentWeatherSystem.clear(currentDiscardPile, null);
			opponentWeatherSystem.clear(opponentDiscardPile, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (currentLife == 0 || opponentLife == 0) endGame();
		else {
			hasOpponentPassed = false;
			changeTurn();
		}
	}

	private void skelligeAbility() {
		if (currentFaction.equals("Skellige")) {
			drawFromDeck(currentDeck, currentHand);
			drawFromDeck(currentDeck, currentHand);
		}
		if (opponentFaction.equals("Skellige")) {
			drawFromDeck(opponentDeck, opponentHand);
			drawFromDeck(opponentDeck, opponentHand);
		}
	}

	private int getRoundResult() {
		int currentPower = getCurrentPower(), opponentPower = getOpponentPower();
		int roundResult; // -1 for lose, 0 for draw, 1 for win
		if (currentPower < opponentPower) roundResult = -1;
		else if (currentPower > opponentPower) roundResult = 1;
		else {
			if (currentFaction.equals("Nilfgaard") && !opponentFaction.equals("Nilfgaard")) roundResult = 1;
			else if (!currentFaction.equals("Nilfgaard") && opponentFaction.equals("Nilfgaard")) roundResult = -1;
			else roundResult = 0;
		}
		return roundResult;
	}

	private void drawFromDeck(Space deck, Space hand){
		Random random = new Random();
		int randomIndex = random.nextInt(deck.getCards().size());
		hand.getCards().add(deck.getCards().get(randomIndex));
		deck.getCards().remove(randomIndex);
	}

	private Unit keepUnit(boolean isCurrentPlayer) {
		Random random = new Random();
		int size = 0;
		for (int i = 0; i < 3; i++)
			for (Card card : rows[isCurrentPlayer ? i : i + 3].getCards())
				if (card instanceof Unit && !((Unit) card).isHero()) size++;
		int randomIndex = random.nextInt(size);
		int counter = 0;
		for (int i = 0; i < 3; i++)
			for (Card card : rows[isCurrentPlayer ? i : i + 3].getCards())
				if (card instanceof Unit && !((Unit) card).isHero()) {
					if (counter == randomIndex) return (Unit) card;
					counter++;
				}
		return null;
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
