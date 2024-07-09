package model.leader;

import model.game.CardMover;

public class MoverLeader extends Leader {

	private final CardMover[] cardMovers;

	public MoverLeader(String name, String description, boolean isManual, CardMover[] cardMovers) {
		super(name, description, isManual);
		this.cardMovers = cardMovers;
	}

	@Override
	public void act() {
		for (CardMover cardMover : cardMovers) cardMover.move();
		super.act();
	}

	@Override
	public Leader clone() {
		return new MoverLeader(name, getDescription(), isManual, cardMovers);
	}

}
