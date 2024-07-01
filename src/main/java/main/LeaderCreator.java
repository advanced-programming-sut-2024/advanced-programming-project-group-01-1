package main;

import model.game.CardMover;
import model.game.Faction;
import model.game.Game;
import model.leader.*;

import java.io.*;
import java.nio.file.Paths;

public class LeaderCreator {

	private static void writeLeader(Faction faction, Leader leader) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/leaders/" + leader.getName() + ".json"));
		oos.writeObject(faction);
		oos.writeObject(leader);
		oos.close();
	}

	private static void createSpellLeader(Faction faction, String name, boolean useDeck, int spaceNumber, String... spellNumbers) {
		try {
			SpellLeader spellLeader = new SpellLeader(name, useDeck, spaceNumber, spellNumbers);
			writeLeader(faction, spellLeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createMoverLeader(Faction faction, String name, boolean isManual, CardMover... cardMovers) {
		try {
			MoverLeader moverLeader = new MoverLeader(name, isManual, cardMovers);
			writeLeader(faction, moverLeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createPassiveLeader(Faction faction, String name, String setterName) {
		try {
			PassiveLeader passiveLeader = new PassiveLeader(name, setterName);
			writeLeader(faction, passiveLeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createAgileOptimizer() {
		try {
			writeLeader(Faction.SCOIATAEL, new AgileOptimizer());
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
		createSpellLeader(Faction.NORTHERN_REALMS, "Foltest the SiegeMaster", true, -1, "Impenetrable Fog");
		createSpellLeader(Faction.NORTHERN_REALMS, "Foltest the Steel-Forged", false, -1, "Clear Weather");
		createSpellLeader(Faction.NORTHERN_REALMS, "Foltest King of Temeria", false, 0, "Commander's Horn");
		createSpellLeader(Faction.NORTHERN_REALMS, "Foltest Lord Commander of the North", false, 0, "Scorch");
		createSpellLeader(Faction.NORTHERN_REALMS, "Foltest Son of Medell", false, 1, "Scorch");
		createSpellLeader(Faction.NILFGAARDIAN_EMPIRE, "Emhyr var Emreis the White Flame", true, -1, "Torrential Rain");
		createSpellLeader(Faction.MONSTERS, "Eredin Bringer of Death", false, 2, "Commander's Horn");
		createSpellLeader(Faction.MONSTERS, "Eredin Commander of the Red Riders", true, -1,
				"Biting Frost", "Clear Weather", "Impentrable Fog", "Skellige Storm", "Torrential Rain");
		createSpellLeader(Faction.SCOIATAEL, "Francesca Findabair Queen of Dol Blathanna", false, 2, "Scorch");
		createSpellLeader(Faction.SCOIATAEL, "Francesca Findabair the Beautiful", false, 1, "Commander's Horn");
		createSpellLeader(Faction.SCOIATAEL, "Francesca Findabair Pureblood Elf", true, -1, "Biting Frost");
	}

	private static void createMoverLeaders() {
		createMoverLeader(Faction.NILFGAARDIAN_EMPIRE, "Emhyr var Emreis His Imperial Majesty", true, new CardMover(Game.OPPONENT_HAND, -1, true, 3, false));
		createMoverLeader(Faction.NILFGAARDIAN_EMPIRE, "Emhyr var Emreis the Relentless", true, new CardMover(Game.OPPONENT_DISCARD_PILE, Game.CURRENT_HAND, false, 1, true));
		createMoverLeader(Faction.MONSTERS, "Eredin King of the Wild Hunt", true, new CardMover(Game.CURRENT_DISCARD_PILE, Game.CURRENT_HAND, false, 1, true));
		createMoverLeader(Faction.MONSTERS, "Eredin Destroyer of Worlds", true, new CardMover(Game.CURRENT_HAND, Game.CURRENT_DISCARD_PILE, false, 2, false), new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, false, 1, false));
		createMoverLeader(Faction.SCOIATAEL, "Francesca Findabair Daisy of the Valley", false, new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, true, 1, false));
		createMoverLeader(Faction.SKELLIGE, "Crach an Craite", true, new CardMover(Game.CURRENT_DISCARD_PILE, Game.CURRENT_DECK, false, -1, false), new CardMover(Game.OPPONENT_DISCARD_PILE, Game.OPPONENT_DECK, false, -1, false));
	}

	private static void createPassiveLeaders() {
		createPassiveLeader(Faction.NILFGAARDIAN_EMPIRE, "Emhyr var Emreis Emperor of Nilfgaard", "disableLeaderAbilities");
		createPassiveLeader(Faction.NILFGAARDIAN_EMPIRE, "Emhyr var Emreis Invader of the North", "randomizeMedic");
		createPassiveLeader(Faction.MONSTERS, "Eredin Breacc Glas The Treacherous", "doubleSpyPower");
		createPassiveLeader(Faction.SKELLIGE, "King Bran", "weakenDebuff");
	}

}
