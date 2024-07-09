package client.view.animation;

import javafx.animation.Transition;
import client.view.model.SmallCard;

public class CardMoving extends Transition {

    SmallCard card;
    double x;
    double y;
    double x0;
    double y0;

    public CardMoving(SmallCard card, double x, double y) {
        this.card = card;
        this.x = x;
        this.y = y;
        this.x0 = card.getLayoutX();
        this.y0 = card.getLayoutY();
        setCycleDuration(javafx.util.Duration.millis(500));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double frac) {
        card.setLayoutX(x0 + (x - x0) * frac);
        card.setLayoutY(y0 + (y - y0) * frac);
    }
}
