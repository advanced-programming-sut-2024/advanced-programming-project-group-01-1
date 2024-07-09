package server.model.game;

import server.model.Asker;
import server.model.card.Card;
import server.model.game.space.Space;
import server.model.user.User;

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

	public CardMover(int originId, int destinationId, boolean isRandom, int count, boolean onlyUnit, boolean ignoreHero) {
		this.originId = originId;
		this.destinationId = destinationId;
		this.isRandom = isRandom;
		this.count = count;
		this.onlyUnit = onlyUnit;
		this.ignoreHero = ignoreHero;
		random = new Random();
	}

	public void move(User player) {
		Space originSpace = player.getCurrentGame().getSpaceById(originId);
		Space destinationSpace = player.getCurrentGame().getSpaceById(destinationId);
		if (destinationSpace != null) {
			ArrayList<Card> availableCards = originSpace.getCards(onlyUnit, ignoreHero);
			int toBeMoved = (count == -1 || count > availableCards.size()) ? availableCards.size() : count;
			for (int i = 0; i < toBeMoved; i++) {
				new Asker(player, originSpace, onlyUnit, ignoreHero, isRandom, index ->
						originSpace.getCards(onlyUnit, ignoreHero).get(index).updateSpace(destinationSpace), false, 0);
			}
		} else show(player, originSpace);
	}

	private void show(User player, Space originSpace) {
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
		new Asker(player, tmp, false, false, false, index -> {}, true, 1);
	}

}
