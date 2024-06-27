package main;

import model.card.Card;
import model.card.ability.*;
import model.card.special.Decoy;
import model.card.special.spell.Buffer;
import model.card.special.spell.Weather;
import model.card.unit.Melee;
import model.card.unit.Unit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CardCreator {

	private static void writeCard(String faction, int count, Card card) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/cards/" + card.getName() + ".json"));
		oos.writeObject(faction);
		oos.writeObject(count);
		oos.writeObject(card);
		oos.close();
	}

	public static void createUnit(Class clazz, String faction, int count, String name, Ability ability, int basePower, boolean isHero) {
		try {
			Unit unit = (Unit) clazz.getConstructor(String.class, Ability.class, int.class, boolean.class).newInstance(name, ability, basePower, isHero);
			writeCard(faction, count, unit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createWeather(int count, String name, boolean effectsMelee, boolean effectsRanged, boolean effectsSiege) {
		try {
			Weather weather = new Weather(name, effectsMelee, effectsRanged, effectsSiege);
			writeCard("Neutral", count, weather);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createBuffer(int count, String name, Ability ability) {
		try {
			Buffer buffer = new Buffer(name, ability);
			writeCard("Neutral", count, buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createDecoy() {
		try {
			writeCard("Neutral", 3, new Decoy());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createSkelligeCards() {
		createUnit(Melee.class, "Skellige", 1, "Berserker", Berserker.INSTANCE, 4, false);
		createUnit(Melee.class, "Skellige", 0, "Vidkaarl", MoralBooster.INSTANCE, 14, false);
		createUnit(Melee.class, "Skellige", 1, "Svanberg", null, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Udalryk", null, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Donar an Hindar", null, 4, false);
		createUnit(Melee.class, "Skellige", 3, "Clan an Craite", TightBond.INSTANCE, 6, false);
		createUnit(Melee.class, "Skellige", 1, "Blueboy Lugos", null, 6, false);
		createUnit(Melee.class, "Skellige", 1, "Madman Lugos", null, 6, false);
		createUnit(Melee.class, "Skellige", 1, "Cerys", Muster.INSTANCE, 10, true);
		createUnit(Melee.class, "Skellige", 1, "Kambi", Transformer.INSTANCE, 11, true);
		createUnit(Melee.class, "Skellige", 1, "Birna Bran", Medic.INSTANCE, 2, false);
		createUnit(Melee.class, "Skellige", 3, "Clan Drummond Shieldmaiden", TightBond.INSTANCE, 4, false);
		createUnit(Melee.class, "Skellige", 1, "Clan Tordarroch Armorsmith", null, 4, false);

	}

	public static void main(String[] args) {
	}
}
