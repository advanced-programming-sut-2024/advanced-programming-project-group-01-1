package client.view.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import client.view.model.SmallCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.util.Duration;

public class CardMoving extends Transition{

    SmallCard card;
    double x;
    double y;
    double x0;
    double y0;
    public CardMoving(SmallCard card, double x, double y) {
        this.card = card;
        this.x0 = card.getLayoutX();
        this.y0 = card.getLayoutY();
        this.x = x;
        this.y = y;
        this.setCycleCount(1);
        this.setCycleDuration(Duration.millis(500));
    }


    @Override
    public void interpolate(double v) {
        card.setLayoutX(x0 + v * (x - x0));
        card.setLayoutY(y0 + v * (y - y0));
    }
}
