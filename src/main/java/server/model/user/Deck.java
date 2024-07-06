package server.model.user;

import server.main.CardCreator;
import server.model.card.Card;
import server.model.card.special.Special;
import server.model.card.unit.Unit;
import server.model.game.Faction;
import server.model.leader.Leader;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Deck implements Serializable {

	private final Faction faction;
	private final ArrayList<Leader> availableLeaders;
	private Leader leader;
	private final ArrayList<Card> cards, availableCards;
	private int specialCount = 0;
	private int unitCount = 0;

	public Deck(Faction faction) {
		this.faction = faction;
		this.availableLeaders = new ArrayList<>();
		File folder = new File("src/main/resources/leaders/");
		for (File file : folder.listFiles()) {
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
				Faction leaderFaction = (Faction) objectInputStream.readObject();
				if (leaderFaction.equals(faction)) availableLeaders.add((Leader) objectInputStream.readObject());
				objectInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(2357);
			}
		}
		this.cards = new ArrayList<>();
		this.availableCards = new ArrayList<>();
		folder = new File("src/main/resources/cards/");
		for (File file : folder.listFiles()) {
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
				Faction cardFaction = (Faction) objectInputStream.readObject();
				if (cardFaction.equals(faction) || cardFaction.equals(Faction.NEUTRAL)) {
					Integer count = (Integer) objectInputStream.readObject();
					Card card = (Card) objectInputStream.readObject();
					if (count == 0) continue;
					availableCards.add(card);
					for (int i = 1; i < count; i++) {
						Card clone = card.clone();
						availableCards.add(clone);
					}
				}
				objectInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(2357);
			}
		}
		leader = availableLeaders.get(0);
	}


	public Faction getFaction() {
		return faction;
	}

	public Leader getLeader() {
		return leader;
	}

	public boolean setLeader(Leader leader) {
		if (availableLeaders.contains(leader)) {
			this.leader = leader;
			return true;
		}
		return false;
	}

	public int getSpecialCount() {
		return this.specialCount;
	}

	public int getUnitCount() {
		return this.unitCount;
	}

	public int getHeroCount() {
		return (int) cards.stream().filter(card -> card instanceof Unit && ((Unit) card).isHero()).count();
	}

	public int getTotalPower() {
		int totalPower = 0;
		for (Card card : cards)
			if (card instanceof Unit) totalPower += ((Unit) card).getBasePower();
		return totalPower;
	}

	public boolean isValid() {
		return unitCount >= 22 && specialCount <= 10;
	}

	public ArrayList<Card> getCards() {
		return new ArrayList<>(this.cards);
	}

	public ArrayList<Leader> getAvailableLeaders() {
		return new ArrayList<>(this.availableLeaders);
	}

	public ArrayList<Card> getAvailableCards() {
		return new ArrayList<>(this.availableCards);
	}

	public int getAvailableCount(Card card) {
		return (int) availableCards.stream().filter(c -> c.toString().equals(card.toString())).count();
	}

	public int getInDeckCount(Card card) {
		return (int) cards.stream().filter(c -> c.toString().equals(card.toString())).count();
	}

	public boolean add(Card card) {
		Card cardInDeck = null;
		for (Card c : availableCards) {
			if (c.toString().equals(card.toString())) {
				cardInDeck = c;
				break;
			}
		}
		if (cardInDeck != null) {
			if (card instanceof Special) specialCount++;
			else unitCount++;
			availableCards.remove(cardInDeck);
			cards.add(cardInDeck);
			cards.sort(Card::compareTo);
			return true;
		}
		return false;
	}

	public boolean remove(Card card) {
		Card cardInDeck = null;
		for (Card c : cards) {
			if (c.toString().equals(card.toString())) {
				cardInDeck = c;
				break;
			}
		}
		if (cardInDeck != null) {
			if (cardInDeck instanceof Special) specialCount--;
			else unitCount--;
			availableCards.add(cardInDeck);
			cards.remove(cardInDeck);
			cards.sort(Card::compareTo);
			return true;
		}
		return false;
	}

	public void reset() {
		this.cards.clear();
		specialCount = 0;
		unitCount = 0;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(leader).append("\n");
		stringBuilder.append("Number of Cards: ").append(cards.size()).append("\n");
		stringBuilder.append("Unit Cards: ").append(unitCount).append("\n");
		stringBuilder.append("Special Cards: ").append(specialCount).append("\n");
		stringBuilder.append("Hero Cards: ").append(getHeroCount()).append("\n");
		stringBuilder.append("Total Power: ").append(getTotalPower()).append("\n");
		return stringBuilder.toString();
	}

	public String fuckGson() {
		StringBuilder fson = new StringBuilder();
		fson.append(this.faction.getName()).append("\n");
		fson.append(this.leader.getName()).append("\n");
		fson.append(this.cards.size()).append("\n");
		for (Card card : cards) fson.append(card.getName()).append("\n");
		return fson.toString();
	}

	public static Deck fsonReader(String fson) {
		String[] info = fson.split("\n");
		Deck deck = new Deck(Faction.getFaction(info[0]));
		for (Leader leader : deck.getAvailableLeaders()) {
			if (leader.getName().equals(info[1])) {
				deck.setLeader(leader);
				break;
			}
		}
		int size = Integer.parseInt(info[2]);
		for (int i = 0; i < size; i++) {
			Card card = CardCreator.getCard(info[i + 3]);
			deck.add(card);
		}
		return deck;
	}

}
