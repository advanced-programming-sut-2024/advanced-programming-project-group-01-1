package main;

import model.game.CardMover;
import model.game.Game;
import model.leader.Leader;
import model.leader.MoverLeader;
import model.leader.SpellLeader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LeaderCreator {

	private static void writeLeader(String faction, Leader leader) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/leaders/" + leader.getName() + ".json"));
		oos.writeObject(faction);
		oos.writeObject(leader);
		oos.close();
	}

	private static void createSpellLeader(String faction, String name, boolean useDeck, int spaceNumber, String... spellNumbers) {
		try {
			SpellLeader spellLeader = new SpellLeader(name, useDeck, spaceNumber, spellNumbers);
			writeLeader(faction, spellLeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createMoverLeader(String faction, String name, boolean isManual, CardMover... cardMovers) {
		try {
			MoverLeader moverLeader = new MoverLeader(name, isManual, cardMovers);
			writeLeader(faction, moverLeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {

	}

	private static void createMoverLeaders() {
		createMoverLeader("Nilfgaard", "Emhyr var Emreis His Imperial Majesty", true,
				new CardMover(Game.OPPONENT_HAND, -1, true, 3));
		createMoverLeader("Nilfgaard", "Emhyr var Emreis the Relentless", true,
				new CardMover(Game.OPPONENT_DISCARD_PILE, Game.CURRENT_HAND, false, 1));
		createMoverLeader("Monsters", "Eredin King of the Wild Hunt", true,
				new CardMover(Game.CURRENT_DISCARD_PILE, Game.CURRENT_HAND, false, 1));
		createMoverLeader("Monsters", "Eredin Destroyer of Worlds", true,
				new CardMover(Game.CURRENT_HAND, Game.CURRENT_DISCARD_PILE, false, 2),
				new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, false, 1));
		createMoverLeader("Scoia'tael", "Francesca Findabair Daisy of the Valley", false,
				new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, true, 1));
		createMoverLeader("Skellige", "Crach an Craite", true,
				new CardMover(Game.CURRENT_DISCARD_PILE, Game.CURRENT_DECK, false, -1),
				new CardMover(Game.OPPONENT_DISCARD_PILE, Game.OPPONENT_DECK, false, -1));
	}

}
