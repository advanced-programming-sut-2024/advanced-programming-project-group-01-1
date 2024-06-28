package main;

import model.card.Card;
import model.card.Leader;
import model.card.ability.*;
import model.card.special.Decoy;
import model.card.special.spell.Buffer;
import model.card.special.spell.InstantSpell;
import model.card.special.spell.Spell;
import model.card.special.spell.Weather;
import model.card.unit.*;

import java.io.*;
import java.nio.file.Paths;

public class CardCreator {

	private static void writeCard(String faction, int count, Card card) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/cards/" + card.getName() + ".json"));
		oos.writeObject(faction);
		oos.writeObject(count);
		oos.writeObject(card);
		oos.close();
	}

	private static void createLeader(String faction, String name, Ability ability) {
		try {
			Leader leader = new Leader(name, ability);
			writeCard(faction, 1, leader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createUnit(Class clazz, String faction, int count, String name, Ability ability, int basePower, boolean isHero) {
		try {
			Unit unit = (Unit) clazz.getConstructor(String.class, Ability.class, int.class, boolean.class).newInstance(name, ability, basePower, isHero);
			writeCard(faction, count, unit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createWeather(int count, String name, boolean effectsMelee, boolean effectsRanged, boolean effectsSiege) {
		try {
			Weather weather = new Weather(name, effectsMelee, effectsRanged, effectsSiege);
			writeCard("Neutral", count, weather);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createNonWeatherSpell(Class clazz, int count, String name, Ability ability) {
		try {
			Spell spell = (Spell) clazz.getConstructor(String.class, Ability.class).newInstance(name, ability);
			writeCard("Neutral", count, spell);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createDecoy() {
		try {
			writeCard("Neutral", 3, new Decoy());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		createNeutralCards();
		createSkelligeCards();
		createMonstersCards();
		createNilfgaardCards();
		createNorthernRealmsCards();
		createScoiataelCards();
	}

	private static void createLeaders() {
		createLeader("Northern Realms", "The Siege Master", null);
		createLeader("Northern Realms", "The Steel-Forged", null);
		createLeader("Northern Realms", "King of Temeria", null);
		createLeader("Northern Realms", "Lord Commander of The North", null);
		createLeader("Northern Realms", "Son of Medell", null);
		createLeader("Nilfgaardian Empire", "The White Flame", null);
		createLeader("Nilfgaardian Empire", "His Imperial Majesty", null);
		createLeader("Nilfgaardian Empire", "Emperor of Nilfgaard", null);
		createLeader("Nilfgaardian Empire", "The Relentless", null);
		createLeader("Nilfgaardian Empire", "Invader of the North", null);
		createLeader("Monsters", "Bringer of Death", null);
		createLeader("Monsters", "Destroyer of Worlds", null);
		createLeader("Monsters", "Commander of the Red Riders", null);
		createLeader("Monsters", "The Treacherous", null);
		createLeader("Scoia'tael", "Queen of Dol Blathanna", null);
		createLeader("Scoia'tael", "The Beautiful", null);
		createLeader("Scoia'tael", "Daisy of the Valley", null);
		createLeader("Scoia'tael", "Pureblood Elf", null);
		createLeader("Scoia'tael", "Hope of the Aen Seidhe", null);
		createLeader("Skellige", "Crach an Crite", null);
		createLeader("Skellige", "King Bran", null);
	}

	private static void createNeutralCards() {
		createWeather(3, "Biting Frost", true, false, false);
		createWeather(3, "Impenetrable Fog", false, true, false);
		createWeather(2, "Torrential Rain", false, false, true);
		createWeather(3, "Skellige Storm", false, true, true);
		createNonWeatherSpell(InstantSpell.class, 2, "Clear Weather", Cleanse.INSTANCE);
		createNonWeatherSpell(InstantSpell.class, 3, "Scorch", Scorch.INSTANCE);
		createNonWeatherSpell(Buffer.class, 3, "Commander's Horn", Horn.INSTANCE);
		createDecoy();
		createUnit(Melee.class, "Neutral", 1, "Geralt of Rivia", null, 15, true);
		createUnit(Melee.class, "Neutral", 0, "Bovine Defence Force", null, 8, false);
		createUnit(Melee.class, "Neutral", 1, "Triss Merigold", null, 7, true);
		createUnit(Melee.class, "Neutral", 1, "Villentretenmerth", Scorch.INSTANCE, 7, false);
		createUnit(Ranged.class, "Neutral", 1, "Yennefer of Vengerberg", Medic.INSTANCE, 7, true);
		createUnit(Melee.class, "Neutral", 1, "Vesemir", null, 6, false);
		createUnit(Agile.class, "Neutral", 1, "Olgierd von Everec", MoralBooster.INSTANCE, 6, false);
		createUnit(Melee.class, "Neutral", 1, "Zoltan Chivay", null, 5, false);
		createUnit(Melee.class, "Neutral", 1, "Emiel Regis Rohellec Terzief", null, 5, false);
		createUnit(Ranged.class, "Neutral", 3, "Gaunter O'Dimm; Darkness", Muster.INSTANCE, 4, false);
		createUnit(Siege.class, "Neutral", 1, "Gaunter O'Dimm", Muster.INSTANCE, 2, false);
		createUnit(Melee.class, "Neutral", 1, "Dandelion", Horn.INSTANCE, 2, false);
		createUnit(Melee.class, "Neutral", 1, "Mysterious Elf", Spy.INSTANCE, 0, true);
		createUnit(Ranged.class, "Neutral", 1, "Cow", Transformer.INSTANCE, 0, false);
	}

	private static void createSkelligeCards() {
		createNonWeatherSpell(Buffer.class, 3, "Mardroeme", Mardroeme.INSTANCE);
		createUnit(Melee.class, "Skellige", 0, "Vidkaarl", MoralBooster.INSTANCE, 14, false);
		createUnit(Siege.class, "Skellige", 1, "Olaf", MoralBooster.INSTANCE, 12, false);
		createUnit(Melee.class, "Skellige", 0, "Hemdall", null, 11, true);
		createUnit(Melee.class, "Skellige", 1, "Cerys", Muster.INSTANCE, 10, true);
		createUnit(Ranged.class, "Skellige", 1, "Hjamlar", null, 10, true);
		createUnit(Ranged.class, "Skellige", 1, "Ermion", Mardroeme.INSTANCE, 8, true);
		createUnit(Ranged.class, "Skellige", 0, "Young Vidkaarl", TightBond.INSTANCE, 8, false);
		createUnit(Melee.class, "Skellige", 3, "Clan an Craite", TightBond.INSTANCE, 6, false);
		createUnit(Melee.class, "Skellige", 1, "Blueboy Lugos", null, 6, false);
		createUnit(Melee.class, "Skellige", 1, "Madman Lugos", null, 6, false);
		createUnit(Ranged.class, "Skellige", 1, "Clan Dimun Pirate", Scorch.INSTANCE, 6, false);
		createUnit(Ranged.class, "Skellige", 2, "Clan Brokvar Archer", null, 6, false);
		createUnit(Siege.class, "Skellige", 2, "War Longship", TightBond.INSTANCE, 6, false);
		createUnit(Melee.class, "Skellige", 1, "Berserker", Berserker.INSTANCE, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Svanberg", null, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Udalryk", null, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Donar an Hindar", null, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Cerys; Clan Drummond Shieldmaiden0", TightBond.INSTANCE, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Cerys; Clan Drummond Shieldmaiden1", TightBond.INSTANCE, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Cerys; Clan Drummond Shieldmaiden2", TightBond.INSTANCE, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Clan Tordarroch Armorsmith", null, 4, false);
		createUnit(Ranged.class, "Skellige", 3, "Light Longship", Muster.INSTANCE, 4, false);
		createUnit(Siege.class, "Skellige", 1, "Holger Blackhand", null, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Birna Bran", Medic.INSTANCE, 2, false);
		createUnit(Ranged.class, "Skellige", 3, "Young Berserker", Berserker.INSTANCE, 2, false);
		createUnit(Siege.class, "Skellige", 1, "Draig Bon-Dhu", Horn.INSTANCE, 2, false);
		createUnit(Melee.class, "Skellige", 1, "Kambi", Transformer.INSTANCE, 0, false);
	}

	private static void createMonstersCards() {
		createUnit(Melee.class, "Monsters", 1, "Draug", null, 10, true);
		createUnit(Melee.class, "Monsters", 1, "Imlerith", null, 10, true);
		createUnit(Melee.class, "Monsters", 1, "Leshen", null, 10, true);
		createUnit(Agile.class, "Monsters", 1, "Kayran", MoralBooster.INSTANCE, 8, true);
		createUnit(Ranged.class, "Monsters", 1, "Toad", Scorch.INSTANCE, 7, false);
		createUnit(Siege.class, "Monsters", 1, "Arachas", Muster.INSTANCE, 6, false);
		createUnit(Melee.class, "Monsters", 1, "Crone; Brewess", Muster.INSTANCE, 6, false);
		createUnit(Melee.class, "Monsters", 1, "Crone; Weavess", Muster.INSTANCE, 6, false);
		createUnit(Melee.class, "Monsters", 1, "Crone; Whispess", Muster.INSTANCE, 6, false);
		createUnit(Siege.class, "Monsters", 1, "Earth Elemental", null, 6, false);
		createUnit(Siege.class, "Monsters", 1, "Fire Elemental", null, 6, false);
		createUnit(Melee.class, "Monsters", 1, "Fiend", null, 6, false);
		createUnit(Melee.class, "Monsters", 1, "Forktail", null, 5, false);
		createUnit(Melee.class, "Monsters", 1, "Frightener", null, 5, false);
		createUnit(Ranged.class, "Monsters", 1, "Grave Hag", null, 5, false);
		createUnit(Melee.class, "Monsters", 1, "Griffin", null, 5, false);
		createUnit(Siege.class, "Monsters", 1, "Ice Giant", null, 5, false);
		createUnit(Melee.class, "Monsters", 1, "Plague Maiden", null, 5, false);
		createUnit(Melee.class, "Monsters", 1, "Vampire; Katakan", Muster.INSTANCE, 5, false);
		createUnit(Melee.class, "Monsters", 1, "Werewolf", null, 5, false);
		createUnit(Melee.class, "Monsters", 1, "Arachas Behemoth0", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, "Monsters", 1, "Arachas Behemoth1", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, "Monsters", 1, "Arachas Behemoth2", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, "Monsters", 1, "Vampire; Fleder", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, "Monsters", 1, "Vampire; Garkain", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, "Monsters", 1, "Vampire; Ekimmara", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, "Monsters", 1, "Vampire; Bruxa", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, "Monsters", 1, "Botchling", null, 4, false);
		createUnit(Agile.class, "Monsters", 1, "Celaeno Harpy", null, 2, false);
		createUnit(Ranged.class, "Monsters", 1, "Cockatrice", null, 2, false);
		createUnit(Ranged.class, "Monsters", 1, "Endrega", null, 2, false);
		createUnit(Ranged.class, "Monsters", 1, "Foglet", null, 2, false);
		createUnit(Ranged.class, "Monsters", 1, "Gargoyle", null, 2, false);
		createUnit(Agile.class, "Monsters", 1, "Harpy", null, 2, false);
		createUnit(Ranged.class, "Monsters", 1, "Wyvern", null, 2, false);
		createUnit(Melee.class, "Monsters", 1, "Nekker0", Muster.INSTANCE, 2, false);
		createUnit(Melee.class, "Monsters", 1, "Nekker1", Muster.INSTANCE, 2, false);
		createUnit(Melee.class, "Monsters", 1, "Nekker2", Muster.INSTANCE, 2, false);
		createUnit(Melee.class, "Monsters", 1, "Ghoul0", Muster.INSTANCE, 1, false);
		createUnit(Melee.class, "Monsters", 1, "Ghoul1", Muster.INSTANCE, 1, false);
		createUnit(Melee.class, "Monsters", 1, "Ghoul2", Muster.INSTANCE, 1, false);
	}

	private static void createNilfgaardCards() {
		createUnit(Ranged.class, "Nilfgaard", 1, "Black Infantry Archer0", null, 10, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Black Infantry Archer1", null, 10, false);
		createUnit(Siege.class, "Nilfgaard", 1, "Heavy Zerrikanian Fire Scorpion", null, 10, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Letho of Gulet", null, 10, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Menno Coehoorn", Medic.INSTANCE, 10, true);
		createUnit(Siege.class, "Nilfgaard", 1, "Morvan Voorhis", null, 10, true);
		createUnit(Siege.class, "Nilfgaard", 1, "Tibor Eggebracht", null, 10, true);
		createUnit(Melee.class, "Nilfgaard", 1, "Stefan Skellen", Spy.INSTANCE, 9, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Shilard Fitz-Oesterlen", Spy.INSTANCE, 7, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Assire var Anahid", null, 6, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Fringilla Vigo", null, 6, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Cahir Mawr Dyffryn aep Ceallach", null, 6, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Siege Engineer", null, 6, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Renual aep Matsen", null, 5, false);
		createUnit(Siege.class, "Nilfgaard", 1, "Zerrikanian Fire Scorpion", null, 5, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Young Emissary0", TightBond.INSTANCE, 5, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Young Emissary1", TightBond.INSTANCE, 5, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Rainfarn", null, 4, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Cynthia", null, 4, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Vanhemar", null, 4, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Vattier de Rideaux", Spy.INSTANCE, 4, false);
		createUnit(Melee.class, "Nilfgaard", 4, "Impera Brigade Guard", TightBond.INSTANCE, 3, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Morteisen", null, 3, false);
		createUnit(Siege.class, "Nilfgaard", 1, "Rotten Mangonel", null, 3, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Puttkammer", null, 3, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Albrich", null, 3, false);
		createUnit(Melee.class, "Nilfgaard", 3, "Nausicaa Cavalry Rider", TightBond.INSTANCE, 2, false);
		createUnit(Melee.class, "Nilfgaard", 1, "Vreemde", null, 2, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Sweers", null, 2, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Etolian Auxiliary Archer0", Medic.INSTANCE, 1, false);
		createUnit(Ranged.class, "Nilfgaard", 1, "Etolian Auxiliary Archer1", Medic.INSTANCE, 1, false);
		createUnit(Siege.class, "Nilfgaard", 1, "Siege Technician", Medic.INSTANCE, 0, false);
	}

	private static void createNorthernRealmsCards() {
		createUnit(Melee.class, "Northern Realms", 1, "Esterad Thyssen", null, 10, true);
		createUnit(Melee.class, "Northern Realms", 1, "John Natalis", null, 10, true);
		createUnit(Melee.class, "Northern Realms", 1, "Verden Roche", null, 10, true);
		createUnit(Ranged.class, "Northern Realms", 1, "Philippa Eilhart", null, 10, true);
		createUnit(Siege.class, "Northern Realms", 1, "Catapult", TightBond.INSTANCE, 8, false);
		createUnit(Siege.class, "Northern Realms", 1, "Ballista", null, 6, false);
		createUnit(Siege.class, "Northern Realms", 1, "Siege Tower", null, 6, false);
		createUnit(Siege.class, "Northern Realms", 1, "Trebutchet0", null, 6, false);
		createUnit(Siege.class, "Northern Realms", 1, "Trebutchet1", null, 6, false);
		createUnit(Ranged.class, "Northern Realms", 1, "Dethmold", null, 6, false);
		createUnit(Ranged.class, "Northern Realms", 1, "Keira Metz", null, 5, false);
		createUnit(Ranged.class, "Northern Realms", 1, "Sile de Tansarville", null, 5, false);
		createUnit(Ranged.class, "Northern Realms", 3, "Crinfrid Reavers Dragon Hunter", TightBond.INSTANCE, 5, false);
		createUnit(Siege.class, "Northern Realms", 1, "Dun Banner Medic", Medic.INSTANCE, 5, false);
		createUnit(Melee.class, "Northern Realms", 1, "Siegfried of Denesle", null, 5, false);
		createUnit(Melee.class, "Northern Realms", 1, "Ves", null, 5, false);
		createUnit(Melee.class, "Northern Realms", 1, "Prince Stennis", Spy.INSTANCE, 5, false);
		createUnit(Melee.class, "Northern Realms", 3, "Blue Stripes Commando", TightBond.INSTANCE, 4, false);
		createUnit(Melee.class, "Northern Realms", 1, "Sigismund Dijkstra", Spy.INSTANCE, 4, false);
		createUnit(Ranged.class, "Northern Realms", 1, "Sabrina Glevissig", null, 4, false);
		createUnit(Ranged.class, "Northern Realms", 1, "Sheldon Skaggs", null, 4, false);
		createUnit(Melee.class, "Northern Realms", 1, "Yarpen Zigrin", null, 2, false);
		createUnit(Melee.class, "Northern Realms", 4, "Poor Fucking Infantry", TightBond.INSTANCE, 1, false);
		createUnit(Melee.class, "Northern Realms", 1, "Redanian Foot Soldier0", null, 1, false);
		createUnit(Melee.class, "Northern Realms", 1, "Redanian Foot Soldier1", null, 1, false);
		createUnit(Siege.class, "Northern Realms", 1, "Thaler", Spy.INSTANCE, 1, false);
		createUnit(Siege.class, "Northern Realms", 1, "Kaedweni Siege Expert0", MoralBooster.INSTANCE, 1, false);
		createUnit(Siege.class, "Northern Realms", 1, "Kaedweni Siege Expert1", MoralBooster.INSTANCE, 1, false);
		createUnit(Siege.class, "Northern Realms", 1, "Kaedweni Siege Expert2", MoralBooster.INSTANCE, 1, false);
	}

	private static void createScoiataelCards() {
		createUnit(Melee.class, "Scoia'tael", 1, "Isengrim", MoralBooster.INSTANCE, 10, true);
		createUnit(Ranged.class, "Scoia'tael", 1, "Eithne", null, 10, true);
		createUnit(Ranged.class, "Scoia'tael", 1, "Iorveth", null, 10, true);
		createUnit(Ranged.class, "Scoia'tael", 1, "Saeesenthessis", null, 10, true);
		createUnit(Ranged.class, "Scoia'tael", 1, "Milva", MoralBooster.INSTANCE, 10, false);
		createUnit(Siege.class, "Scoia'tael", 1, "Schirru", Scorch.INSTANCE, 8, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Ida Emean aep Sivney", null, 6, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Dennis Cranmer", null, 6, false);
		createUnit(Agile.class, "Scoia'tael", 1, "Dol Blathanna Scout0", null, 6, false);
		createUnit(Agile.class, "Scoia'tael", 1, "Dol Blathanna Scout1", null, 6, false);
		createUnit(Agile.class, "Scoia'tael", 1, "Dol Blathanna Scout2", null, 6, false);
		createUnit(Agile.class, "Scoia'tael", 1, "Barclay Els", null, 6, false);
		createUnit(Agile.class, "Scoia'tael", 1, "Yaevinn", null, 6, false);
		createUnit(Agile.class, "Scoia'tael", 1, "Filavandrel aen Fidhail", null, 6, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Havekar Smuggler0", Muster.INSTANCE, 5, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Havekar Smuggler1", Muster.INSTANCE, 5, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Havekar Smuggler2", Muster.INSTANCE, 5, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Mahakam Defender0", null, 5, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Mahakam Defender1", null, 5, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Mahakam Defender2", null, 5, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Mahakam Defender3", null, 5, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Mahakam Defender4", null, 5, false);
		createUnit(Agile.class, "Scoia'tael", 1, "Vrihedd Brigade Veteran0", null, 5, false);
		createUnit(Agile.class, "Scoia'tael", 1, "Vrihedd Brigade Veteran1", null, 5, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Dol Blathanna Archer", null, 4, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Virhedd Brigade Recruit", null, 4, false);
		createUnit(Agile.class, "Scoia'tael", 1, "Ciaran aep Easnillen", null, 3, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Dwarven Skirmisher0", Muster.INSTANCE, 3, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Dwarven Skirmisher1", Muster.INSTANCE, 3, false);
		createUnit(Melee.class, "Scoia'tael", 1, "Dwarven Skirmisher3", Muster.INSTANCE, 3, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Elven Skirmisher0", Muster.INSTANCE, 2, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Elven Skirmisher1", Muster.INSTANCE, 2, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Elven Skirmisher2", Muster.INSTANCE, 2, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Toruviel", null, 2, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Riordain", null, 1, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Havekar Healer0", Medic.INSTANCE, 1, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Havekar Healer1", Medic.INSTANCE, 1, false);
		createUnit(Ranged.class, "Scoia'tael", 1, "Havekar Healer2", Medic.INSTANCE, 1, false);
	}

	public static Card getCard(String name) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Paths.get(CardCreator.class.getResource("/cards/" + name + ".json").toURI()).toString()));
			ois.readObject();
			ois.readObject();
			Card card = (Card) ois.readObject();
			ois.close();
			return card;
		} catch (Exception e) {
			return null;
		}
	}
}
