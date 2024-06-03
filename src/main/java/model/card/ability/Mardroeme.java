package model.card.ability;

import model.card.Card;
import model.card.special.spell.Buffer.Buffer;
import model.card.special.spell.Spell;
import model.game.space.Row;

public enum Mardroeme implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) throws Exception {
		Row row = (Row) card.getSpace();
		if (card instanceof Spell) row.setBuffer((Buffer) card);
		row.setMardroeme(true);
		for(Card cardInRow : row.getCards()){
			Ability cardAbility = cardInRow.getAbility();
			if(cardAbility instanceof Berserker) ((Berserker) cardAbility).act(cardInRow);
		}
	}

	@Override
	public void undo(Card card) throws Exception {
		Row row = (Row) card.getSpace();
		if(card instanceof Spell) row.setBuffer(null);
		row.setMardroeme(false);
	}
}
