package model.game;

import controller.game.MatchMenuController;
import model.card.Card;
import model.card.unit.Unit;
import model.game.space.Space;

import java.util.ArrayList;
import java.util.Random;

public class CardMover {

	private Space originSpace, destinationSpace;
	private boolean isRandom;
	private Random random;
	private int count;

	public CardMover(int originId, int destinationId, boolean isRandom, int count){
		this(Game.getCurrentGame().getSpaceById(originId), Game.getCurrentGame().getSpaceById(originId), isRandom, count);
	}

	public CardMover(Space originSpace, Space destinationSpace, boolean isRandom, int count) {
		this.originSpace = originSpace;
		this.destinationSpace = destinationSpace;
		this.isRandom = isRandom;
		this.count = count;
		random = new Random();
	}

	public void move() {
		if (destinationSpace != null) {
			ArrayList<Card> nonHeroCards = new ArrayList<>();
			for (Card card : originSpace.getCards()) {
				if (!(card instanceof Unit) || !((Unit) card).isHero())
					nonHeroCards.add(card);
			}
			int toBeMoved = count == -1 ? nonHeroCards.size() : count;
			for (int i = 0; i < toBeMoved; i++) {
				Card card;
				if (isRandom) {
					int randomIndex = random.nextInt(nonHeroCards.size());
					card = nonHeroCards.get(randomIndex);
				} else card = MatchMenuController.askSpace(originSpace);
				destinationSpace.getCards().add(card);
				originSpace.getCards().remove(card);
				nonHeroCards.remove(card);
			}
		} else {
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

}
