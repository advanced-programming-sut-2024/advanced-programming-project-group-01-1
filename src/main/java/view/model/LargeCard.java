package view.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.Constants;

import java.util.HashMap;

public class LargeCard extends Rectangle {

    private final String name;
    private final String description;

    public LargeCard(String name, String description) {
        super(Constants.LARGE_CARD_WIDTH.getValue(), Constants.LARGE_CARD_HEIGHT.getValue());
        ImagePattern imagePattern;
        System.out.println("Loading image: " + name + ".jpg");
        imagePattern = new ImagePattern(new Image(getClass().getResourceAsStream("/images/largecards/" + name + ".jpg")));
        this.setFill(imagePattern);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
