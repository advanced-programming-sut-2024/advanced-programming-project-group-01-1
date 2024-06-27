package main;

import model.card.Card;
import model.card.ability.Ability;
import model.card.special.spell.Spell;
import model.card.unit.Unit;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class CardCreator {

	private static void writeCard(String faction, int count, Card card) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/cards/" + card.getName() + ".json"));
			oos.writeObject(faction);
			oos.writeObject(count);
			oos.writeObject(card);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void creatUnit(Class clazz, String faction, int count, String name, Ability ability, int basePower, boolean isHero) {
		try {
			Unit unit = (Unit) clazz.getConstructor(String.class, Ability.class, int.class, boolean.class).newInstance(name, ability, basePower, isHero);
			writeCard(faction, count, unit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createSpell(Class clazz, int count, String name, Ability ability) {
		try {
			Spell spell = (Spell) clazz.getConstructor(String.class, Ability.class).newInstance(name, ability);
			writeCard("Neutral", count, spell);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}
}
