package model.card.ability;

import main.CardCreator;
import model.card.Card;
import model.card.unit.Melee;
import model.card.unit.Ranged;
import model.card.unit.Unit;
import model.game.Game;
import model.game.space.Row;

public enum Berserker implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		if (!((Row) card.getSpace()).hasMardroeme()) return;
		card.pull();
		String transformedName = "Transformed " + card.getName().replace("Berserker", "Vildkaarl");
		Card transformedCard = CardCreator.getCard(transformedName);
		transformedCard.setSpace(Game.getCurrentGame().getCurrentDeck());
		try {
			if (card instanceof Melee) transformedCard.put(2);
			else if (card instanceof Ranged) transformedCard.put(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription(Card card) {
		return "Transforms into Vildkaarl when Mardroeme is played on the same row.";
	}

}
