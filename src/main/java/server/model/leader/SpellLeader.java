package server.model.leader;

import server.main.CardCreator;
import server.model.card.Card;
import server.model.card.ability.Cleanse;
import server.model.card.ability.Scorch;
import server.model.card.special.spell.Spell;
import server.model.game.Game;

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
			for (Card card : this.game.getCurrentDeck().getCards()) {
				for (String spellName : spellNames) {
					if (card.getName().equals(spellName)) availableSpells.add((Spell) card);
				}
			}
			spell = availableSpells.get(random.nextInt(availableSpells.size()));
			this.game.getCurrentDeck().getCards().remove(spell);
		} else {
			for (String spellName : spellNames)
				availableSpells.add((Spell) CardCreator.getCard(spellName));
			spell = availableSpells.get(random.nextInt(availableSpells.size()));
			spell.setSpace(this.game.getCurrentHand());
		}
		if (spell.getAbility() instanceof Scorch) ((Scorch) spell.getAbility()).killRow(this.game, spaceNumber);
		else {
			try {
				spell.put(spaceNumber);
				if (spell.getAbility() instanceof Cleanse) {
					spell.getSpace().getCards().remove(spell);
					spell.setSpace(null);
				}
			} catch (Exception ignored) {
			}
		}
		super.act();
	}

	@Override
	public Leader clone() {
		return new SpellLeader(name, getDescription(), useDeck, spaceNumber, spellNames);
	}
}
