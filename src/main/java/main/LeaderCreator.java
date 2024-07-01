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

	private static void createSpellLeader(Faction faction, String name, String description, boolean useDeck, int spaceNumber, String... spellNumbers) {
		try {
			SpellLeader spellLeader = new SpellLeader(name, description, useDeck, spaceNumber, spellNumbers);
			writeLeader(faction, spellLeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createMoverLeader(Faction faction, String name, String description, boolean isManual, CardMover... cardMovers) {
		try {
			MoverLeader moverLeader = new MoverLeader(name, description, isManual, cardMovers);
			writeLeader(faction, moverLeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createPassiveLeader(Faction faction, String name, String description, String setterName) {
		try {
			PassiveLeader passiveLeader = new PassiveLeader(name, description, setterName);
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
		createSpellLeader(Faction.NORTHERN_REALMS, "Foltest the SiegeMaster",
				"Pick an Impenetrable Fog from your deck and play it instantly",
				true, -1, "Impenetrable Fog");
		createSpellLeader(Faction.NORTHERN_REALMS, "Foltest the Steel-Forged",
				"Clear any weather effects active",
				false, -1, "Clear Weather");
		createSpellLeader(Faction.NORTHERN_REALMS, "Foltest King of Temeria",
				"Doubles the strength of all your Siege units",
				false, 0, "Commander's Horn");
		createSpellLeader(Faction.NORTHERN_REALMS, "Foltest Lord Commander of the North",
				"Destroy your opponent's strongest siege unit(s) if the combined strength of all his or her close combat units is 10 or more",
				false, 0, "Scorch");
		createSpellLeader(Faction.NORTHERN_REALMS, "Foltest Son of Medell",
				"Destroy your opponent's strongest ranged unit(s) if the combined strength of all his or her close combat units is 10 or more",
				false, 1, "Scorch");
		createSpellLeader(Faction.NILFGAARDIAN_EMPIRE, "Emhyr var Emreis the White Flame",
				"Pick a Torrential Rain from your deck and play it instantly",
				true, -1, "Torrential Rain");
		createSpellLeader(Faction.MONSTERS, "Eredin Bringer of Death",
				"Doubles the strength of all your Close Combat units",
				false, 2, "Commander's Horn");
		createSpellLeader(Faction.MONSTERS, "Eredin Commander of the Red Riders",
				"Pick any weather card from your deck and play it instantly",
				true, -1,
				"Biting Frost", "Clear Weather", "Impentrable Fog", "Skellige Storm", "Torrential Rain");
		createSpellLeader(Faction.SCOIATAEL, "Francesca Findabair Queen of Dol Blathanna",
				"Destroy your opponent's strongest close combat unit(s) if the combined strength of all his or her close combat units is 10 or more",
				false, 2, "Scorch");
		createSpellLeader(Faction.SCOIATAEL, "Francesca Findabair the Beautiful",
				"Doubles the strength of all your Ranged units",
				false, 1, "Commander's Horn");
		createSpellLeader(Faction.SCOIATAEL, "Francesca Findabair Pureblood Elf",
				"Pick a Biting Frost from your deck and play it instantly",
				true, -1, "Biting Frost");
	}

	private static void createMoverLeaders() {
		createMoverLeader(Faction.NILFGAARDIAN_EMPIRE, "Emhyr var Emreis His Imperial Majesty",
				"Look at 3 random cards from your opponent's hand",
				true, new CardMover(Game.OPPONENT_HAND, -1, true, 3, false));
		createMoverLeader(Faction.NILFGAARDIAN_EMPIRE, "Emhyr var Emreis the Relentless",
				"Return a card from your opponent's graveyard to your hand",
				true, new CardMover(Game.OPPONENT_DISCARD_PILE, Game.CURRENT_HAND, false, 1, true));
		createMoverLeader(Faction.MONSTERS, "Eredin King of the Wild Hunt",
				"Return a card from your graveyard to your hand",
				true, new CardMover(Game.CURRENT_DISCARD_PILE, Game.CURRENT_HAND, false, 1, true));
		createMoverLeader(Faction.MONSTERS, "Eredin Destroyer of Worlds",
				"Discard 2 cards from your hand and draw 1 card of your choice from your deck",
				true, new CardMover(Game.CURRENT_HAND, Game.CURRENT_DISCARD_PILE, false, 2, false), new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, false, 1, false));
		createMoverLeader(Faction.SCOIATAEL, "Francesca Findabair Daisy of the Valley",
				"Draw one extra card at the beginning of the battle",
				false, new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, true, 1, false));
		createMoverLeader(Faction.SKELLIGE, "Crach an Craite",
				"Shuffles all non-hero cards from each player's graveyard back into their respective decks", true,
				new CardMover(Game.CURRENT_DISCARD_PILE, Game.CURRENT_DECK, false, -1, false),
				new CardMover(Game.OPPONENT_DISCARD_PILE, Game.OPPONENT_DECK, false, -1, false));
	}

	private static void createPassiveLeaders() {
		createPassiveLeader(Faction.NILFGAARDIAN_EMPIRE, "Emhyr var Emreis Emperor of Nilfgaard",
				"Cancel your opponent's leader ability",
				"disableLeaderAbilities");
		createPassiveLeader(Faction.NILFGAARDIAN_EMPIRE, "Emhyr var Emreis Invader of the North",
				"Abilities that restore a unit back in the battlefield, restore a randomly-chosen unit. Affects both players",
				"randomizeMedic");
		createPassiveLeader(Faction.MONSTERS, "Eredin Breacc Glas The Treacherous",
				"Doubles the strength of spy cards. Affects both players",
				"doubleSpyPower");
		createPassiveLeader(Faction.SKELLIGE, "King Bran",
				"Units only lose half their strength in bad weather condition",
				"weakenDebuff");
	}

}
