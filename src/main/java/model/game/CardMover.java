package model.game;

import controller.game.MatchMenuController;
import model.card.Card;
import model.card.unit.Unit;
import model.game.space.Space;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class CardMover implements Serializable {

	private final int originId, destinationId;
	private final boolean isRandom;
	private final Random random;
	private final int count;
	private final boolean onlyUnit;
	private final boolean ignoreHero;

	public CardMover(int originId, int destinationId, boolean isRandom, int count, boolean onlyUnit, boolean ignoreHero){
		this.originId = originId;
		this.destinationId = destinationId;
		this.isRandom = isRandom;
		this.count = count;
		this.onlyUnit = onlyUnit;
		this.ignoreHero = ignoreHero;
		random = new Random();
	}

	public void move() {
		Space originSpace = Game.getCurrentGame().getSpaceById(originId);
		Space destinationSpace = Game.getCurrentGame().getSpaceById(destinationId);
		if (destinationSpace != null) {
			ArrayList<Card> availableCards = new ArrayList<>();
			for (Card card : originSpace.getCards()) {
				if ((!(card instanceof Unit) || !((Unit) card).isHero()) || !ignoreHero)
					availableCards.add(card);
			}
			int toBeMoved = (count == -1 || count > availableCards.size()) ? availableCards.size() : count;
			for (int i = 0; i < toBeMoved; i++) {
				Card card;
				if (isRandom) {
					int randomIndex = random.nextInt(availableCards.size());
					card = availableCards.get(randomIndex);
				} else card = MatchMenuController.askSpace(originSpace, onlyUnit);
				card.updateSpace(destinationSpace);
				availableCards.remove(card);
			}
		} else show(originSpace, destinationSpace);
		System.out.println(destinationSpace.getCards().size() + "\n--------------");
	}

	private void show(Space originSpace, Space destinationSpace){
		ArrayList<Card> cardsToBeShown = new ArrayList<>();
		boolean[] shown = new boolean[originSpace.getCards().size()];
		for (int i = 0; i < count; i++) {
			Card card;
			int randomIndex = random.nextInt(originSpace.getCards().size() - i);
			int j = 0;
			for (int k = 0; k < originSpace.getCards().size(); k++) {
				if (!shown[k]) {
					if (j == randomIndex) {
						card = originSpace.getCards().get(k);
						cardsToBeShown.add(card);
						shown[k] = true;
						break;
					}
					j++;
				}
			}
		}
		Space tmp = new Space(cardsToBeShown);
		MatchMenuController.showSpace(tmp);
	}

}
