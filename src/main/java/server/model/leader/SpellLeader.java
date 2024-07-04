package server.model.leader;

import server.main.CardCreator;
import server.model.Client;
import server.model.card.Card;
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
	public void act(Client client) {
		Spell spell;
		ArrayList<Spell> availableSpells = new ArrayList<>();
		if (useDeck) {
			for (Card card : client.getIdentity().getCurrentGame().getCurrentDeck().getCards()) {
				for (String spellName : spellNames) {
					if (card.getName().equals(spellName)) availableSpells.add((Spell) card);
				}
			}
			spell = availableSpells.get(random.nextInt(availableSpells.size()));
			client.getIdentity().getCurrentGame().getCurrentDeck().getCards().remove(spell);
		} else {
			for (String spellName : spellNames)
				availableSpells.add((Spell) CardCreator.getCard(spellName));
			spell = availableSpells.get(random.nextInt(availableSpells.size()));
		}
		try {
			spell.put(client, spaceNumber);
		} catch (Exception ignored) {
		}
		super.act(client);
	}

	@Override
	public Leader clone() {
		return new SpellLeader(name, getDescription(), useDeck, spaceNumber, spellNames);
	}
}
