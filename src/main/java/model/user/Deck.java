package model.user;

import model.card.Card;
import model.card.special.Special;

import java.io.*;
import java.util.ArrayList;

public class Deck {

	private final ArrayList<Card> cards, availableCards;
	private int specialCount;
	private int unitCount;
	private final String faction;

	public Deck(String faction) {
		this.cards = new ArrayList<>();
		this.faction = faction;
		this.availableCards = new ArrayList<>();
		File folder = new File("src/main/resources/cards/");
		for (File file : folder.listFiles()) {
			ObjectInputStream objectInputStream = null;
			try {
				objectInputStream = new ObjectInputStream(new FileInputStream(file));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(2357);
			}
			try {
				String cardFaction = (String) objectInputStream.readObject();
				if (cardFaction.equals(faction) || cardFaction.equals("Neutral")) {
					Integer count = (Integer) objectInputStream.readObject();
					if (count == 0) continue;
					Card card = (Card) objectInputStream.readObject();
					availableCards.add(card);
					for (int i = 1; i < count; i++) {
						availableCards.add(card.clone());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(2357);
			}
		}
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

	public String getFaction() {
		return faction;
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
