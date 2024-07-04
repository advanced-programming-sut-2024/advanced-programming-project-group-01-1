package view.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.Constants;

import java.util.HashMap;

public class LargeLeader extends Rectangle {

    private static HashMap<String, ImagePattern> imagePatterns = new HashMap<>();

    private final String name;
    private final String description;

    public LargeLeader(String name, String description) {
        super(Constants.LARGE_CARD_WIDTH.getValue(), Constants.LARGE_CARD_HEIGHT.getValue());
        ImagePattern imagePattern = imagePatterns.get(name);
        if (imagePattern == null) {
            System.out.println("Loading image: " + name + ".jpg");
            imagePattern = new ImagePattern(new Image(getClass().getResourceAsStream("/images/largecards/" + name + ".jpg")));
            imagePatterns.put(name, imagePattern);
        }
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
