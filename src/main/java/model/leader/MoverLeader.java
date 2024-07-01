package model.leader;

import model.game.CardMover;

public class MoverLeader extends Leader {

	private final CardMover[] cardMovers;

	public MoverLeader(String name, boolean isManual, CardMover[] cardMovers) {
		super(name, isManual);
		this.cardMovers = cardMovers;
	}

	@Override
	public void act() {
		for (CardMover cardMover : cardMovers)
			cardMover.move();
		super.act();
	}

	@Override
	public Leader clone() {
		return new MoverLeader(name, isManual, cardMovers);
	}

}
