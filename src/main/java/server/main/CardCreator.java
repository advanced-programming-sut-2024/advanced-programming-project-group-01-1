package server.main;

import server.model.card.Card;
import server.model.card.ability.*;
import server.model.card.special.Decoy;
import server.model.card.special.spell.Buffer;
import server.model.card.special.spell.InstantSpell;
import server.model.card.special.spell.Spell;
import server.model.card.special.spell.Weather;
import server.model.card.unit.*;
import server.model.game.Faction;

import java.io.*;
import java.nio.file.Paths;

public class CardCreator {

	private static void writeCard(Faction faction, int count, Card card) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/cards/" + card.getName() + ".json"));
		oos.writeObject(faction);
		oos.writeObject(count);
		oos.writeObject(card);
		oos.close();
	}

	private static void createUnit(Class clazz, Faction faction, int count, String name, Ability ability, int basePower, boolean isHero) {
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
			writeCard(Faction.NEUTRAL, count, weather);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createNonWeatherSpell(Class clazz, int count, String name, Ability ability) {
		try {
			Spell spell = (Spell) clazz.getConstructor(String.class, Ability.class).newInstance(name, ability);
			writeCard(Faction.NEUTRAL, count, spell);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createDecoy() {
		try {
			writeCard(Faction.NEUTRAL, 3, new Decoy());
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
		createScoiaTaelCards();
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
		createUnit(Melee.class, Faction.NEUTRAL, 1, "Geralt of Rivia", null, 15, true);
		createUnit(Melee.class, Faction.NEUTRAL, 1, "Cirilla Fiona Elen Riannon", null, 15, true);
		createUnit(Melee.class, Faction.NEUTRAL, 0, "Bovine Defence Force", null, 8, false);
		createUnit(Melee.class, Faction.NEUTRAL, 1, "Triss Merigold", null, 7, true);
		createUnit(Melee.class, Faction.NEUTRAL, 1, "Villentretenmerth", Scorch.INSTANCE, 7, false);
		createUnit(Ranged.class, Faction.NEUTRAL, 1, "Yennefer of Vengerberg", Medic.INSTANCE, 7, true);
		createUnit(Melee.class, Faction.NEUTRAL, 1, "Vesemir", null, 6, false);
		createUnit(Agile.class, Faction.NEUTRAL, 1, "Olgierd von Everec", MoralBooster.INSTANCE, 6, false);
		createUnit(Melee.class, Faction.NEUTRAL, 1, "Zoltan Chivay", null, 5, false);
		createUnit(Melee.class, Faction.NEUTRAL, 1, "Emiel Regis Rohellec Terzieff", null, 5, false);
		createUnit(Ranged.class, Faction.NEUTRAL, 3, "Gaunter O'Dimm; Darkness", Muster.INSTANCE, 4, false);
		createUnit(Siege.class, Faction.NEUTRAL, 1, "Gaunter O'Dimm", Muster.INSTANCE, 2, false);
		createUnit(Melee.class, Faction.NEUTRAL, 1, "Dandelion", Horn.INSTANCE, 2, false);
		createUnit(Melee.class, Faction.NEUTRAL, 1, "Avallac\'h", Spy.INSTANCE, 0, true);
		createUnit(Ranged.class, Faction.NEUTRAL, 1, "Cow", Transformer.INSTANCE, 0, false);
	}

	private static void createNorthernRealmsCards() {
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 1, "Esterad Thyssen", null, 10, true);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 1, "John Natalis", null, 10, true);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 1, "Verden Roche", null, 10, true);
		createUnit(Ranged.class, Faction.NORTHERN_REALMS, 1, "Philippa Eilhart", null, 10, true);
		createUnit(Siege.class, Faction.NORTHERN_REALMS, 2, "Catapult", TightBond.INSTANCE, 8, false);
		createUnit(Siege.class, Faction.NORTHERN_REALMS, 1, "Ballista", null, 6, false);
		createUnit(Siege.class, Faction.NORTHERN_REALMS, 1, "Siege Tower", null, 6, false);
		createUnit(Siege.class, Faction.NORTHERN_REALMS, 1, "Trebutchet0", null, 6, false);
		createUnit(Siege.class, Faction.NORTHERN_REALMS, 1, "Trebutchet1", null, 6, false);
		createUnit(Ranged.class, Faction.NORTHERN_REALMS, 1, "Dethmold", null, 6, false);
		createUnit(Ranged.class, Faction.NORTHERN_REALMS, 1, "Keira Metz", null, 5, false);
		createUnit(Ranged.class, Faction.NORTHERN_REALMS, 1, "Sile de Tansarville", null, 5, false);
		createUnit(Ranged.class, Faction.NORTHERN_REALMS, 3, "Crinfrid Reavers Dragon Hunter", TightBond.INSTANCE, 5, false);
		createUnit(Siege.class, Faction.NORTHERN_REALMS, 1, "Dun Banner Medic", Medic.INSTANCE, 5, false);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 1, "Siegfried of Denesle", null, 5, false);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 1, "Ves", null, 5, false);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 1, "Prince Stennis", Spy.INSTANCE, 5, false);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 3, "Blue Stripes Commando", TightBond.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 1, "Sigismund Dijkstra", Spy.INSTANCE, 4, false);
		createUnit(Ranged.class, Faction.NORTHERN_REALMS, 1, "Sabrina Glevissig", null, 4, false);
		createUnit(Ranged.class, Faction.NORTHERN_REALMS, 1, "Sheldon Skaggs", null, 4, false);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 1, "Yarpen Zigrin", null, 2, false);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 4, "Poor Fucking Infantry", TightBond.INSTANCE, 1, false);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 1, "Redanian Foot Soldier0", null, 1, false);
		createUnit(Melee.class, Faction.NORTHERN_REALMS, 1, "Redanian Foot Soldier1", null, 1, false);
		createUnit(Siege.class, Faction.NORTHERN_REALMS, 1, "Thaler", Spy.INSTANCE, 1, false);
		createUnit(Siege.class, Faction.NORTHERN_REALMS, 1, "Kaedweni Siege Expert0", MoralBooster.INSTANCE, 1, false);
		createUnit(Siege.class, Faction.NORTHERN_REALMS, 1, "Kaedweni Siege Expert1", MoralBooster.INSTANCE, 1, false);
		createUnit(Siege.class, Faction.NORTHERN_REALMS, 1, "Kaedweni Siege Expert2", MoralBooster.INSTANCE, 1, false);
	}

	private static void createNilfgaardCards() {
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Black Infantry Archer0", null, 10, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Black Infantry Archer1", null, 10, false);
		createUnit(Siege.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Heavy Zerrikanian Fire Scorpion", null, 10, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Letho of Gulet", null, 10, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Menno Coehoorn", Medic.INSTANCE, 10, true);
		createUnit(Siege.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Morvran Voorhis", null, 10, true);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Tibor Eggebracht", null, 10, true);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Stefan Skellen", Spy.INSTANCE, 9, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Shilard Fitz-Oesterlen", Spy.INSTANCE, 7, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Assire var Anahid", null, 6, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Fringilla Vigo", null, 6, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Cahir Mawr Dyffryn aep Ceallach", null, 6, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Siege Engineer", null, 6, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Renuald aep Matsen", null, 5, false);
		createUnit(Siege.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Zerrikanian Fire Scorpion", null, 5, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Young Emissary0", TightBond.INSTANCE, 5, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Young Emissary1", TightBond.INSTANCE, 5, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Rainfarn", null, 4, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Cynthia", null, 4, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Vanhemar", null, 4, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Vattier de Rideaux", Spy.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 4, "Impera Brigade Guard", TightBond.INSTANCE, 3, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Morteisen", null, 3, false);
		createUnit(Siege.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Rotten Mangonel", null, 3, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Puttkammer", null, 3, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Albrich", null, 3, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 3, "Nausicaa Cavalry Rider", TightBond.INSTANCE, 2, false);
		createUnit(Melee.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Vreemde", null, 2, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Sweers", null, 2, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Etolian Auxiliary Archers0", Medic.INSTANCE, 1, false);
		createUnit(Ranged.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Etolian Auxiliary Archers1", Medic.INSTANCE, 1, false);
		createUnit(Siege.class, Faction.NILFGAARDIAN_EMPIRE, 1, "Siege Technician", Medic.INSTANCE, 0, false);
	}

	private static void createMonstersCards() {
		createUnit(Melee.class, Faction.MONSTERS, 1, "Draug", null, 10, true);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Imlerith", null, 10, true);
		createUnit(Ranged.class, Faction.MONSTERS, 1, "Leshen", null, 10, true);
		createUnit(Agile.class, Faction.MONSTERS, 1, "Kayran", MoralBooster.INSTANCE, 8, true);
		createUnit(Ranged.class, Faction.MONSTERS, 1, "Toad", Scorch.INSTANCE, 7, false);
		createUnit(Siege.class, Faction.MONSTERS, 1, "Arachas", Muster.INSTANCE, 6, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Crone; Brewess", Muster.INSTANCE, 6, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Crone; Weavess", Muster.INSTANCE, 6, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Crone; Whispess", Muster.INSTANCE, 6, false);
		createUnit(Siege.class, Faction.MONSTERS, 1, "Earth Elemental", null, 6, false);
		createUnit(Siege.class, Faction.MONSTERS, 1, "Fire Elemental", null, 6, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Fiend", null, 6, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Forktail", null, 5, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Frightener", null, 5, false);
		createUnit(Ranged.class, Faction.MONSTERS, 1, "Grave Hag", null, 5, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Griffin", null, 5, false);
		createUnit(Siege.class, Faction.MONSTERS, 1, "Ice Giant", null, 5, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Plague Maiden", null, 5, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Vampire; Katakan", Muster.INSTANCE, 5, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Werewolf", null, 5, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Arachas Behemoth0", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Arachas Behemoth1", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Arachas Behemoth2", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Vampire; Fleder", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Vampire; Garkain", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Vampire; Ekimmara", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Vampire; Bruxa", Muster.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Botchling", null, 4, false);
		createUnit(Agile.class, Faction.MONSTERS, 1, "Celaeno Harpy", null, 2, false);
		createUnit(Ranged.class, Faction.MONSTERS, 1, "Cockatrice", null, 2, false);
		createUnit(Ranged.class, Faction.MONSTERS, 1, "Endrega", null, 2, false);
		createUnit(Ranged.class, Faction.MONSTERS, 1, "Foglet", null, 2, false);
		createUnit(Ranged.class, Faction.MONSTERS, 1, "Gargoyle", null, 2, false);
		createUnit(Agile.class, Faction.MONSTERS, 1, "Harpy", null, 2, false);
		createUnit(Ranged.class, Faction.MONSTERS, 1, "Wyvern", null, 2, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Nekker0", Muster.INSTANCE, 2, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Nekker1", Muster.INSTANCE, 2, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Nekker2", Muster.INSTANCE, 2, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Ghoul0", Muster.INSTANCE, 1, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Ghoul1", Muster.INSTANCE, 1, false);
		createUnit(Melee.class, Faction.MONSTERS, 1, "Ghoul2", Muster.INSTANCE, 1, false);
	}

	private static void createScoiaTaelCards() {
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Isengrim Faolitarna", MoralBooster.INSTANCE, 10, true);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Eithne", null, 10, true);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Iorveth", null, 10, true);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Saesenthessis", null, 10, true);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Milva", MoralBooster.INSTANCE, 10, false);
		createUnit(Siege.class, Faction.SCOIATAEL, 1, "Schirru", Scorch.INSTANCE, 8, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Ida Emean aep Sivney", null, 6, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Dennis Cranmer", null, 6, false);
		createUnit(Agile.class, Faction.SCOIATAEL, 1, "Dol Blathanna Scout0", null, 6, false);
		createUnit(Agile.class, Faction.SCOIATAEL, 1, "Dol Blathanna Scout1", null, 6, false);
		createUnit(Agile.class, Faction.SCOIATAEL, 1, "Dol Blathanna Scout2", null, 6, false);
		createUnit(Agile.class, Faction.SCOIATAEL, 1, "Barclay Els", null, 6, false);
		createUnit(Agile.class, Faction.SCOIATAEL, 1, "Yaevinn", null, 6, false);
		createUnit(Agile.class, Faction.SCOIATAEL, 1, "Filavandrel aen Fidhail", null, 6, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Havekar Smuggler0", Muster.INSTANCE, 5, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Havekar Smuggler1", Muster.INSTANCE, 5, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Havekar Smuggler2", Muster.INSTANCE, 5, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Mahakaman Defender0", null, 5, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Mahakaman Defender1", null, 5, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Mahakaman Defender2", null, 5, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Mahakaman Defender3", null, 5, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Mahakaman Defender4", null, 5, false);
		createUnit(Agile.class, Faction.SCOIATAEL, 1, "Vrihedd Brigade Veteran0", null, 5, false);
		createUnit(Agile.class, Faction.SCOIATAEL, 1, "Vrihedd Brigade Veteran1", null, 5, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Dol Blathanna Archer", null, 4, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Vrihedd Brigade Recruit", null, 4, false);
		createUnit(Agile.class, Faction.SCOIATAEL, 1, "Ciaran aep Easnillien", null, 3, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Dwarven Skirmisher0", Muster.INSTANCE, 3, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Dwarven Skirmisher1", Muster.INSTANCE, 3, false);
		createUnit(Melee.class, Faction.SCOIATAEL, 1, "Dwarven Skirmisher2", Muster.INSTANCE, 3, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Elven Skirmisher0", Muster.INSTANCE, 2, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Elven Skirmisher1", Muster.INSTANCE, 2, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Elven Skirmisher2", Muster.INSTANCE, 2, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Toruviel", null, 2, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Riordain", null, 1, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Havekar Healer0", Medic.INSTANCE, 0, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Havekar Healer1", Medic.INSTANCE, 0, false);
		createUnit(Ranged.class, Faction.SCOIATAEL, 1, "Havekar Healer2", Medic.INSTANCE, 0, false);
	}

	private static void createSkelligeCards() {
		createNonWeatherSpell(Buffer.class, 3, "Mardroeme", Mardroeme.INSTANCE);
		createUnit(Melee.class, Faction.SKELLIGE, 0, "Transformed Vildkaarl", MoralBooster.INSTANCE, 14, false);
		createUnit(Siege.class, Faction.SKELLIGE, 1, "Olaf", MoralBooster.INSTANCE, 12, false);
		createUnit(Melee.class, Faction.SKELLIGE, 0, "Hemdall", null, 11, true);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Cerys", Muster.INSTANCE, 10, true);
		createUnit(Ranged.class, Faction.SKELLIGE, 1, "Hjalmar", null, 10, true);
		createUnit(Ranged.class, Faction.SKELLIGE, 1, "Ermion", Mardroeme.INSTANCE, 8, true);
		createUnit(Ranged.class, Faction.SKELLIGE, 0, "Transformed Young Vildkaarl", TightBond.INSTANCE, 8, false);
		createUnit(Melee.class, Faction.SKELLIGE, 3, "Clan an Craite Warrior", TightBond.INSTANCE, 6, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Blueboy Lugos", null, 6, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Madman Lugos", null, 6, false);
		createUnit(Ranged.class, Faction.SKELLIGE, 1, "Clan Dimun Pirate", Scorch.INSTANCE, 6, false);
		createUnit(Ranged.class, Faction.SKELLIGE, 2, "Clan Brokvar Archer", null, 6, false);
		createUnit(Siege.class, Faction.SKELLIGE, 2, "War Longship", TightBond.INSTANCE, 6, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Berserker", Berserker.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Svanrige", null, 4, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Udalryk", null, 4, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Donar an Hindar", null, 4, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Cerys; Clan Drummond Shield Maiden0", TightBond.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Cerys; Clan Drummond Shield Maiden1", TightBond.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Cerys; Clan Drummond Shield Maiden2", TightBond.INSTANCE, 4, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Clan Tordarroch Armorsmith", null, 4, false);
		createUnit(Ranged.class, Faction.SKELLIGE, 3, "Light Longship", Muster.INSTANCE, 4, false);
		createUnit(Siege.class, Faction.SKELLIGE, 1, "Holger Blackhand", null, 4, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Birna Bran", Medic.INSTANCE, 2, false);
		createUnit(Ranged.class, Faction.SKELLIGE, 3, "Young Berserker", Berserker.INSTANCE, 2, false);
		createUnit(Siege.class, Faction.SKELLIGE, 1, "Draig Bon-Dhu", Horn.INSTANCE, 2, false);
		createUnit(Melee.class, Faction.SKELLIGE, 1, "Kambi", Transformer.INSTANCE, 0, false);
	}

	public static Card getCard(String name) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Paths.get(CardCreator.class.getResource("/cards/" + name + ".json").toURI()).toString()));
			ois.readObject();
			ois.readObject();
			Card card = (Card) ois.readObject();
			ois.close();
			return card.clone();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
