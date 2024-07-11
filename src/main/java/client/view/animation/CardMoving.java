package client.view.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import client.view.model.SmallCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class CardMoving {

    private static final double V = 5;
    private final Timeline timeline;
    SmallCard card;
    double x;
    double y;

    public CardMoving(SmallCard card, double x, double y) {
        this.card = card;
        this.x = x - this.card.getLayoutX();
        this.y = y - this.card.getLayoutY();
        timeline = new Timeline(new KeyFrame(Duration.millis(18), actionEvent -> {
            this.card.setLayoutX(this.card.getLayoutX() + this.x / 28);
            this.card.setLayoutY(this.card.getLayoutY() + this.y / 28);
        }));
        timeline.setCycleCount(28);
    }

    public void setOnFinished(EventHandler<ActionEvent> eventHandler) {
        timeline.setOnFinished(eventHandler);
    }

    public void play() {
        timeline.play();
    }
}
