package model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import model.card.Card;
import view.Appview;
import view.game.MatchMenu;
import view.game.SelectPanel;
import view.game.SelectionHandler;

import java.util.ArrayList;

public class Asker implements Runnable {

	private static final ArrayList<Asker> askers = new ArrayList<>();
	private static Asker running;
	private final ArrayList<Card> cards;
	private final boolean isRandom;
	private final SelectionHandler selectionHandler;
	private final boolean isOptional;
	private final int ptr;
	private SelectPanel selectPanel;
	private boolean isFast;

	public Asker(ArrayList<Card> cards, boolean isRandom, SelectionHandler selectionHandler, boolean isOptional, int ptr) {
		this.cards = cards;
		this.isRandom = isRandom;
		this.selectionHandler = selectionHandler;
		this.isOptional = isOptional;
		this.ptr = ptr;
		this.isFast = false;
		askers.add(this);
		this.run();
	}

	public Asker(ArrayList<Card> cards, boolean isRandom, SelectionHandler selectionHandler, boolean isOptional, int ptr, boolean isFast) {
		this.cards = cards;
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

	public static boolean isAsking() {
		return running != null;
	}

	public static boolean select(int index) {
		if (askers.isEmpty()) return false;
		return askers.get(0).selectCard(index);
	}

	private boolean selectCard(int index) {
		if (index < -1 || index >= cards.size()) return false;
		return selectPanel.selectCard(index);
	}

	@Override
	public void run() {
		if (running != null || askers.indexOf(this) != 0) {
			if (isRandom && isFast){
				int randomIndex = (int) (Math.random() * cards.size());
				selectionHandler.handle(randomIndex);
				Platform.runLater(() -> ((MatchMenu) Appview.getMenu()).updateScreen());
				askers.remove(this);
			}
			return;
		}
		askers.remove(this);
		running = this;
		if (cards.isEmpty()) {
			running = null;
			if (!askers.isEmpty()) askers.get(0).run();
		} else if (isRandom) {
			int randomIndex = (int) (Math.random() * cards.size());
			selectionHandler.handle(randomIndex);
			Platform.runLater(() -> ((MatchMenu) Appview.getMenu()).updateScreen());
			running = null;
			if (!askers.isEmpty()) askers.get(0).run();
		} else {
			StringBuilder cardNames = new StringBuilder();
			for (Card card : cards)
				cardNames.append(card.getName()).append("\n").append(card.getDescription()).append("\n");
			selectPanel = new SelectPanel((Pane) Appview.getStage().getScene().getRoot(), cardNames.toString().split("\n"), ptr, index -> {
				selectionHandler.handle(index);
				Platform.runLater(() -> ((MatchMenu) Appview.getMenu()).updateScreen());
				running = null;
				if (!askers.isEmpty()) askers.get(0).run();
			}, isOptional);
			if (isOptional) selectPanel.getBackButton().setOnMouseClicked(event -> {
				selectPanel.selectCard(-1);
				running = null;
				if (!askers.isEmpty()) askers.get(0).run();
			});
		}
	}

}
