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
		System.out.println("wtf disable :: " + this.isDisable);
		for (CardMover cardMover : cardMovers) {
			System.out.println("kir");
			cardMover.move();
			System.out.println("nakir");
		}
		super.act();
		System.out.println("fuck disable :: " + this.isDisable);
	}

	@Override
	public Leader clone() {
		return new MoverLeader(name, getDescription(), isManual, cardMovers);
	}

}
