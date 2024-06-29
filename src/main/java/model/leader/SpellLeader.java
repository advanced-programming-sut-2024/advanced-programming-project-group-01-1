package model.leader;

import main.CardCreator;
import model.card.Card;
import model.card.special.spell.Spell;
import model.game.Game;

import java.util.ArrayList;
import java.util.Random;

public class SpellLeader extends Leader {

	private final String[] spellNames;
	private final int spaceNumber;
	private final boolean useDeck;

	public SpellLeader(String name, String[] spellNames, int spaceNumber, boolean useDeck) {
		super(name, true);
		this.spellNames = spellNames;
		this.spaceNumber = spaceNumber;
		this.useDeck = useDeck;
	}

	@Override
	public void act() {
		Spell spell;
		ArrayList<Spell> availableSpells = new ArrayList<>();
		if (useDeck) {
			for (Card card : Game.getCurrentGame().getCurrentDeck()) {
				for (String spellName : spellNames) {
					if (card.getName().equals(spellName)) availableSpells.add((Spell) card);
				}
			}
			spell = availableSpells.get((new Random()).nextInt(availableSpells.size()));
			Game.getCurrentGame().getCurrentDeck().remove(spell);
		} else {
			for (String spellName : spellNames)
				availableSpells.add((Spell) CardCreator.getCard(spellName));
			spell = availableSpells.get((new Random()).nextInt(availableSpells.size()));
		}
		try {
			spell.put(spaceNumber);
		} catch (Exception ignored) {
		}
		this.disable();
	}
}
