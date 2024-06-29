package model.user;

import model.card.Card;
import model.card.special.Special;
import model.leader.Leader;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Deck {

	private final String faction;
	private final ArrayList<Leader> availableLeaders;
	private Leader leader;
	private final ArrayList<Card> cards, availableCards;
	private int specialCount = 0;
	private int unitCount = 0;

	public Deck(String faction) {
		this.faction = faction;
		this.availableLeaders = new ArrayList<>();
		File folder = new File("src/main/resources/leaders/");
		for (File file : folder.listFiles()) {
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
				String leaderFaction = (String) objectInputStream.readObject();
				if (leaderFaction.equals(faction))
					availableLeaders.add((Leader) objectInputStream.readObject());
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
				String cardFaction = (String) objectInputStream.readObject();
				if (cardFaction.equals(faction) || cardFaction.equals("Neutral")) {
					Integer count = (Integer) objectInputStream.readObject();
					Card card = (Card) objectInputStream.readObject();
					if (count == 0) continue;
					availableCards.add(card);
					for (int i = 1; i < count; i++) {
						availableCards.add(card.clone());
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


	public String getFaction() {
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

	public boolean isValid() {
		return unitCount >= 22 && specialCount <= 10;
	}

	public ArrayList<Card> getCards() {
		return new ArrayList<>(this.cards);
	}

	public boolean add(Card card) {
		if (availableCards.contains(card)) {
			if (card instanceof Special) specialCount++;
			else unitCount++;
			availableCards.remove(card);
			return cards.add(card);
		}
		return false;
	}

	public boolean remove(Card card) {
		if (cards.remove(card)) {
			if (card instanceof Special) specialCount--;
			else unitCount--;
			return true;
		}
		return false;
	}

	public void reset() {
		this.cards.clear();
		specialCount = 0;
		unitCount = 0;
	}
}
