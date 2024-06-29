package main;

import model.game.CardMover;
import model.game.Game;
import model.leader.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LeaderCreator {

	private static void writeLeader(String faction, Leader leader) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/src/main/resources/leaders/" + leader.getName() + ".json"));
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

	private static void createPassiveLeader(String faction, String name, String setterName) {
		try {
			PassiveLeader passiveLeader = new PassiveLeader(name, setterName);
			writeLeader(faction, passiveLeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createAgileOptimizer() {
		try {
			writeLeader("Scoia'tael", new AgileOptimizer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		createSpellLeaders();
		createMoverLeaders();
		createPassiveLeaders();
		createAgileOptimizer();
	}

	private static void createSpellLeaders() {
		createSpellLeader("Northern Realms", "Foltest the SiegeMaster", true, -1, "Impenetrable Fog");
		createSpellLeader("Northern Realms", "Foltest the Steel-Forged", false, -1, "Clear Weather");
		createSpellLeader("Northern Realms", "Foltest King of Temeria", false, 0, "Commander's Horn");
		createSpellLeader("Northern Realms", "Foltest Lord Commander of the North", false, 0, "Scorch");
		createSpellLeader("Northern Realms", "Foltest Son of Medell", false, 1, "Scorch");
		createSpellLeader("Nilfgaardian Empire", "Emhyr var Emreis the White Flame", true, -1, "Torrential Rain");
		createSpellLeader("Monsters", "Eredin Bringer of Death", false, 2, "Commander's Horn");
		createSpellLeader("Monsters", "Eredin Commander of the Red Riders", true, -1,
				"Biting Frost", "Clear Weather", "Impentrable Fog", "Skellige Storm", "Torrential Rain");
		createSpellLeader("Scoia'tael", "Francesca Findabair Queen of Dol Blathanna", false, 2, "Scorch");
		createSpellLeader("Scoia'tael", "Francesca Findabair the Beautiful", false, 1, "Commander's Horn");
		createSpellLeader("Scoia'tael", "Francesca Findabair Pureblood Elf", true, -1, "Biting Frost");
	}

	private static void createMoverLeaders() {
		createMoverLeader("Nilfgaard", "Emhyr var Emreis His Imperial Majesty", true, new CardMover(Game.OPPONENT_HAND, -1, true, 3));
		createMoverLeader("Nilfgaard", "Emhyr var Emreis the Relentless", true, new CardMover(Game.OPPONENT_DISCARD_PILE, Game.CURRENT_HAND, false, 1));
		createMoverLeader("Monsters", "Eredin King of the Wild Hunt", true, new CardMover(Game.CURRENT_DISCARD_PILE, Game.CURRENT_HAND, false, 1));
		createMoverLeader("Monsters", "Eredin Destroyer of Worlds", true, new CardMover(Game.CURRENT_HAND, Game.CURRENT_DISCARD_PILE, false, 2), new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, false, 1));
		createMoverLeader("Scoia'tael", "Francesca Findabair Daisy of the Valley", false, new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, true, 1));
		createMoverLeader("Skellige", "Crach an Craite", true, new CardMover(Game.CURRENT_DISCARD_PILE, Game.CURRENT_DECK, false, -1), new CardMover(Game.OPPONENT_DISCARD_PILE, Game.OPPONENT_DECK, false, -1));
	}

	private static void createPassiveLeaders() {
		createPassiveLeader("Nilfgaard", "Emhyr var Emreis Emperor of Nilfgaard", "setLeaderAbilityDisabled");
		createPassiveLeader("Nilfgaard", "Emhyr var Emreis Invader of the North", "setMedicRandom");
		createPassiveLeader("Monsters", "Eredin Breacc Glas The Treacherous", "setSpyPowerDoubled");
		createPassiveLeader("Skellige", "King Bran", "setDebuffWeakened");
	}

}
