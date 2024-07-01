package model.leader;

import main.CardCreator;
import model.card.Card;
import model.card.special.spell.Spell;
import model.game.Game;

import java.util.ArrayList;
import java.util.Random;

public class SpellLeader extends Leader {

	private final boolean useDeck;
	private final int spaceNumber;
	private final String[] spellNames;
	private final Random random;

	public SpellLeader(String name, String description, boolean useDeck, int spaceNumber, String[] spellNames) {
		super(name, description, true);
		this.useDeck = useDeck;
		this.spaceNumber = spaceNumber;
		this.spellNames = spellNames;
		random = new Random();
	}

	@Override
	public void act() {
		Spell spell;
		ArrayList<Spell> availableSpells = new ArrayList<>();
		if (useDeck) {
			for (Card card : Game.getCurrentGame().getCurrentDeck().getCards()) {
				for (String spellName : spellNames) {
					if (card.getName().equals(spellName)) availableSpells.add((Spell) card);
				}
			}
			spell = availableSpells.get(random.nextInt(availableSpells.size()));
			Game.getCurrentGame().getCurrentDeck().getCards().remove(spell);
		} else {
			for (String spellName : spellNames)
				availableSpells.add((Spell) CardCreator.getCard(spellName));
			spell = availableSpells.get(random.nextInt(availableSpells.size()));
		}
		try {
			spell.put(spaceNumber);
		} catch (Exception ignored) {
		}
		super.act();
	}

	@Override
	public Leader clone() {
		return new SpellLeader(name, getDescription(), useDeck, spaceNumber, spellNames);
	}
}
