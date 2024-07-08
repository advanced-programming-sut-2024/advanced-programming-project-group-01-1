package model.game;

import controller.game.MatchMenuController;
import model.GameInfo;
import model.card.Card;
import model.card.unit.Ranged;
import model.card.unit.Siege;
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


	ArrayList<Integer> currentScores = new ArrayList<>(), opponentScores = new ArrayList<>();
	User current, opponent;
	int roundNumber = 1;
	Row[] rows = new Row[6];
	Space currentWeatherSystem = new Space(), opponentWeatherSystem = new Space();
	Space currentDiscardPile = new Space(), opponentDiscardPile = new Space();
	Space currentDeck, opponentDeck;
	Space currentHand = new Space(), opponentHand = new Space();
	int currentLife = 2, opponentLife = 2;
	Faction currentFaction, opponentFaction;
	boolean hasOpponentPassed;
	Leader currentLeader, opponentLeader;
	boolean isSpyPowerDoubled = false, isDebuffWeakened = false, isMedicRandom = false;
	boolean gameEnded = false;
	int currentCheatPower = 0, opponentCheatPower = 0;

	private Game(User player1, User player2) {
		this.current = player1;
		this.opponent = player2;
		this.currentFaction = player1.getDeck().getFaction();
		this.opponentFaction = player2.getDeck().getFaction();
		this.currentLeader = player1.getDeck().getLeader();
		this.opponentLeader = player2.getDeck().getLeader();
		this.currentLeader.enable();
		this.opponentLeader.enable();
		for (int i = 0; i < 6; i++) rows[i] = new Row();
		this.currentDeck = new Space(player1.getDeck().getCards());
		this.opponentDeck = new Space(player2.getDeck().getCards());
		for (Card card : this.currentDeck.getCards()) card.setSpace(currentDeck);
		for (Card card : this.opponentDeck.getCards()) card.setSpace(opponentDeck);
	}

	public static Game createGame(User player1, User player2) {
		currentGame = new Game(player1, player2);
		new CardMover(CURRENT_DECK, CURRENT_HAND, true, 10, false, false).move();
		new CardMover(OPPONENT_DECK, OPPONENT_HAND, true, 10, false, false).move();
//		currentGame.veto();
		// TODO: fix veto
		if (currentGame.getCurrentLeader().getName().equals("Emhyr var Emreis Emperor of Nilfgaard")) currentGame.getCurrentLeader().act();
		else if (currentGame.getOpponentLeader().getName().equals("Emhyr var Emreis Emperor of Nilfgaard")) currentGame.getOpponentLeader().act();
		if (!currentGame.getCurrentLeader().isDisable() && !currentGame.getCurrentLeader().isManual()) currentGame.getCurrentLeader().act();
		if (!currentGame.getOpponentLeader().isDisable() && !currentGame.getOpponentLeader().isManual()) currentGame.getOpponentLeader().act();
		return currentGame;
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

	public User getCurrent() {
		return current;
	}

	public User getOpponent() {
		return opponent;
	}

	public Faction getCurrentFaction() {
		return currentFaction;
	}

	public Faction getOpponentFaction() {
		return opponentFaction;
	}

	public int getCurrentLife() {
		return currentLife;
	}

	public void setCurrentLife(int currentLife) {
		this.currentLife = currentLife;
	}

	public int getOpponentLife() {
		return opponentLife;
	}

	public void addCheatPower(int cheatPower) {
		currentCheatPower += cheatPower;
	}

	public int getCurrentPower() {
		int currentPower = 0;
		for (int i = 0; i < 3; i++) currentPower += rows[i].getSumOfPowers();
		return currentPower + currentCheatPower;
	}

	public int getOpponentPower() {
		int opponentPower = 0;
		for (int i = 3; i < 6; i++) opponentPower += rows[i].getSumOfPowers();
		return opponentPower + opponentCheatPower;
	}

	public int getRowPower(int rowNumber) {
		return rows[rowNumber].getSumOfPowers();
	}

	public Space getCurrentDeck() {
		return currentDeck;
	}

	public Space getOpponentDeck() {
		return opponentDeck;
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

	public Leader getOpponentLeader() {
		return opponentLeader;
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

	public int getRowNumber(Row row) {
		for (int i = 0; i < 6; i++) {
			if (row == rows[i]) return i;
		}
		return -1;
	}

	public void setCurrentLeader(Leader currentLeader) {
		this.currentLeader = currentLeader;
	}

	public void setCurrentDeck(Space currentDeck) {
		this.currentDeck = currentDeck;
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

	public void disableLeaderAbilities() {
		currentLeader.disable();
		opponentLeader.disable();
	}

	public void doubleSpyPower() {
		isSpyPowerDoubled = true;
	}

	public void weakenDebuff() {
		isDebuffWeakened = true;
	}

	public void randomizeMedic() {
		isMedicRandom = true;
	}

	public void veto() {
		for (int i = 0; i < 2; i++) {
			new CardMover(CURRENT_HAND, CURRENT_DECK, false, 1, false, false).move();
			new CardMover(CURRENT_DECK, CURRENT_HAND, true, 1, false, false).move();
		}
	}

	public void placeCard(Card card, int spaceId) throws Exception {
		if (spaceId >= 3) throw new Exception("Can't place card for enemy");
		try {
			card.put(spaceId);
			changeTurn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void useLeaderAbility() throws Exception {
		if (currentLeader.isDisable()) {
			if (!currentLeader.isManual()) throw new Exception("Leader ability is not manual!");
			throw new Exception("Leader ability is disabled!");
		}
		currentLeader.act();
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
		tempInt = currentCheatPower;
		currentCheatPower = opponentCheatPower;
		opponentCheatPower = tempInt;
		Faction tempFaction = currentFaction;
		currentFaction = opponentFaction;
		opponentFaction = tempFaction;
		Leader tempLeader = currentLeader;
		currentLeader = opponentLeader;
		opponentLeader = tempLeader;
		User tempUser = current;
		current = opponent;
		opponent = tempUser;
		ArrayList<Integer> tempScores = currentScores;
		currentScores = opponentScores;
		opponentScores = tempScores;
	}

	public void passTurn() {
		if (hasOpponentPassed) endRound();
		else {
			changeTurn();
			hasOpponentPassed = true;
		}
	}

	public void endRound() {
		currentScores.add(getCurrentPower());
		opponentScores.add(getOpponentPower());
		currentCheatPower = 0;
		opponentCheatPower = 0;
		roundNumber++;
		int roundResult = getRoundResult();
		if (roundResult <= 0) currentLife--;
		if (roundResult >= 0) opponentLife--;
		if (roundResult == 1 && currentFaction.equals(Faction.NORTHERN_REALMS))
			new CardMover(CURRENT_DECK, CURRENT_HAND, true, 1, false, false).move();
		else if (roundResult == -1 && opponentFaction.equals(Faction.NORTHERN_REALMS))
			new CardMover(OPPONENT_DECK, OPPONENT_HAND, true, 1, false, false).move();
		Unit currentUnit = currentFaction.equals(Faction.MONSTERS) ? keepUnit(true) : null;
		Unit opponentUnit = opponentFaction.equals(Faction.MONSTERS) ? keepUnit(false) : null;
		for (int i = 2; i >= 0; i--) {
			try {
				rows[i].clear(currentDiscardPile, currentUnit);
				rows[5 - i].clear(opponentDiscardPile, opponentUnit);
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
		if (roundNumber == 3) skelligeAbility();
		if (currentLife == 0 || opponentLife == 0) endGame();
		else {
			hasOpponentPassed = false;
			changeTurn();
		}
	}

	public void putRevived(Unit unit, boolean isOpponent) {
		try {
			if (unit instanceof Siege) unit.put(isOpponent ? 5 - Game.SIEGE_ROW_NUMBER : Game.SIEGE_ROW_NUMBER);
			else if (unit instanceof Ranged) unit.put(isOpponent ? 5 - Game.RANGED_ROW_NUMBER : Game.RANGED_ROW_NUMBER);
			else unit.put(isOpponent ? 5 - Game.MELEE_ROW_NUMBER : Game.MELEE_ROW_NUMBER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void skelligeAbility() {
		if (currentFaction.equals(Faction.SKELLIGE)) {
			for (int i = 0; i < 2; i++) {
				ArrayList<Card> discardPile = currentDiscardPile.getCards(true, false);
				MatchMenuController.askCards(discardPile, true, index -> {
					Unit unit = (Unit) discardPile.get(index);
					putRevived(unit, false);
					currentDiscardPile.getCards().remove(unit);
				}, false, 0);
			}
		}
		if (opponentFaction.equals(Faction.SKELLIGE)) {
			for (int i = 0; i < 2; i++) {
				ArrayList<Card> discardPile = opponentDiscardPile.getCards(true, false);
				MatchMenuController.askCards(discardPile, true, index -> {
					Unit unit = (Unit) discardPile.get(index);
					putRevived(unit, true);
					opponentDiscardPile.getCards().remove(unit);
				}, false, 0);
			}
		}
	}

	private int getRoundResult() {
		int currentPower = getCurrentPower(), opponentPower = getOpponentPower();
		int roundResult; // -1 for lose, 0 for draw, 1 for win
		if (currentPower < opponentPower) roundResult = -1;
		else if (currentPower > opponentPower) roundResult = 1;
		else {
			if (currentFaction.equals(Faction.NILFGAARDIAN_EMPIRE) && !opponentFaction.equals(Faction.NILFGAARDIAN_EMPIRE))
				roundResult = 1;
			else if (!currentFaction.equals(Faction.NILFGAARDIAN_EMPIRE) && opponentFaction.equals(Faction.NILFGAARDIAN_EMPIRE))
				roundResult = -1;
			else roundResult = 0;
		}
		return roundResult;
	}

	private Unit keepUnit(boolean isCurrentPlayer) {
		Random random = new Random();
		int size = 0;
		for (int i = 0; i < 3; i++)
			for (Card card : rows[isCurrentPlayer ? i : i + 3].getCards())
				if (card instanceof Unit && !((Unit) card).isHero()) size++;
		if (size == 0) return null;
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
		hasOpponentPassed = false;
		if (!current.getUsername().equals(User.getLoggedInUser().getUsername())) changeTurn();
		gameEnded = true;
		//MatchMenuController.endGame();
		double currentElo = current.getElo(), opponentElo = opponent.getElo();
		if (currentLife == 0 && opponentLife == 0) {
			current.setElo(User.calculateElo(currentElo, opponentElo, 0));
			opponent.setElo(User.calculateElo(opponentElo, currentElo, 0));
			current.getHistory().add(new GameInfo(opponent, currentLife, opponentLife, opponentScores, currentScores, null));
			opponent.getHistory().add(new GameInfo(current, currentLife, opponentLife, currentScores, opponentScores, null));
			// Draw
		} else if (currentLife == 0) {
			current.setElo(User.calculateElo(currentElo, opponentElo, -1));
			opponent.setElo(User.calculateElo(opponentElo, currentElo, 1));
			current.getHistory().add(new GameInfo(opponent, currentLife, opponentLife, opponentScores, currentScores, opponent));
			opponent.getHistory().add(new GameInfo(current, currentLife, opponentLife, currentScores, opponentScores, opponent));
			// Lose
		} else {
			current.setElo(User.calculateElo(currentElo, opponentElo, 1));
			opponent.setElo(User.calculateElo(opponentElo, currentElo, -1));
			current.getHistory().add(new GameInfo(opponent, currentLife, opponentLife, opponentScores, currentScores, current));
			opponent.getHistory().add(new GameInfo(current, currentLife, opponentLife, currentScores, opponentScores, current));
			// Win
		}
	}

	public boolean isGameOver() {
		return gameEnded;
	}

	public boolean isGameWin() {
		return opponentLife == 0 && currentLife != 0;
	}

	public boolean isGameDraw() {
		return opponentLife == 0 && currentLife == 0;
	}

	public ArrayList<Integer> getCurrentScores() {
		return currentScores;
	}

	public ArrayList<Integer> getOpponentScores() {
		return opponentScores;
	}

}
