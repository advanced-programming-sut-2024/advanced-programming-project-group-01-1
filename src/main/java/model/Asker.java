package model;

import model.card.Card;
import model.game.space.Space;
import view.game.SelectionHandler;

import java.util.ArrayList;

public class Asker implements Runnable {

	private static final ArrayList<Asker> askers = new ArrayList<>();
	private static Asker running;
	private final Space space;
	private final boolean onlyUnit;
	private final boolean ignoreHero;
	private ArrayList<Card> cards;
	private final boolean isRandom;
	private final SelectionHandler selectionHandler;
	private final boolean isOptional;
	private final int ptr;
	private final boolean isFast;

	public Asker(Space space, boolean onlyUnit, boolean ignoreHero, boolean isRandom, SelectionHandler selectionHandler, boolean isOptional, int ptr) {
		this.space = space;
		this.onlyUnit = onlyUnit;
		this.ignoreHero = ignoreHero;
		this.isRandom = isRandom;
		this.selectionHandler = selectionHandler;
		this.isOptional = isOptional;
		this.ptr = ptr;
		this.isFast = false;
		askers.add(this);
		this.run();
	}

	public Asker(Space space, boolean onlyUnit, boolean ignoreHero, boolean isRandom, SelectionHandler selectionHandler, boolean isOptional, int ptr, boolean isFast) {
		this.space = space;
		this.onlyUnit = onlyUnit;
		this.ignoreHero = ignoreHero;
		this.isRandom = isRandom;
		this.selectionHandler = selectionHandler;
		this.isOptional = isOptional;
		this.ptr = ptr;
		this.isFast = isFast;
		boolean added = false;
		if (isFast) {
			for (int i = 0; i < askers.size(); i++) {
				if (!askers.get(i).isFast) {
					askers.add(i, this);
					added = true;
					break;
				}
			}
		}
		if (!added) askers.add(this);
		this.run();
	}

	private Asker(Asker asker, SelectionHandler selectionHandler) {
		this.space = asker.space;
		this.onlyUnit = asker.onlyUnit;
		this.ignoreHero = asker.ignoreHero;
		this.isRandom = asker.isRandom;
		this.selectionHandler = selectionHandler;
		this.isOptional = asker.isOptional;
		this.ptr = asker.ptr;
		this.isFast = asker.isFast;
	}

	public static boolean isAsking() {
		return running != null;
	}

	public static Asker getRunning() {
		return running;
	}

	public static boolean select(int index) {
		if (running == null) return false;
		return running.selectCard(index);
	}

	public Space getSpace() {
		return space;
	}

	public ArrayList<Card> getCards() {
		return cards = space.getCards(onlyUnit, ignoreHero);
	}

	public boolean isRandom() {
		return isRandom;
	}

	public boolean isOptional() {
		return isOptional;
	}

	public int getPtr() {
		return ptr;
	}

	private boolean selectCard(int index) {
		if (index < -1 || index >= cards.size()) return false;
		if (index == -1 && !isOptional) return false;
		selectionHandler.handle(index);
		return true;
	}

	@Override
	public void run() {
		if (running != null || askers.indexOf(this) != 0) {
			if (isRandom && isFast) {
				cards = space.getCards(onlyUnit, ignoreHero);
				int randomIndex = (int) (Math.random() * cards.size());
				selectionHandler.handle(randomIndex);
				askers.remove(this);
			}
			return;
		}
		askers.remove(this);
		cards = space.getCards(onlyUnit, ignoreHero);
		if (cards.isEmpty()) {
			if (!askers.isEmpty()) askers.get(0).run();
		} else if (isRandom) {
			int randomIndex = (int) (Math.random() * cards.size());
			selectionHandler.handle(randomIndex);
			if (!askers.isEmpty()) askers.get(0).run();
		} else {
			final SelectionHandler selectionHandler = this.selectionHandler;
			running = new Asker(this, index -> {
				selectionHandler.handle(index);
				running = null;
				if (!askers.isEmpty()) askers.get(0).run();
			});
		}
	}

}
