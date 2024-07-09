package view.model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.Constants;

import java.util.HashMap;

public class PreviewCard extends Pane {

    private static HashMap<String, ImagePattern> imagePatterns = new HashMap<>();

    private final String name;
    private final int count;

    public PreviewCard(String name, int count) {
        super();
        Rectangle picture = new Rectangle(Constants.PREVIEW_CARD_WIDTH.getValue(), Constants.PREVIEW_CARD_HEIGHT.getValue());
        ImagePattern imagePattern = imagePatterns.get(name);
        if (imagePattern == null) {
            imagePattern = new ImagePattern(new Image(getClass().getResourceAsStream("/images/largecards/" + name + ".jpg")));
            imagePatterns.put(name, imagePattern);
        }
        picture.setFill(imagePattern);
        this.getChildren().add(picture);
        Rectangle countIcon = new Rectangle(Constants.PREVIEW_COUNT_ICON_WIDTH.getValue(), Constants.PREVIEW_COUNT_ICON_HEIGHT.getValue());
        countIcon.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/images/icons/preview_count.png"))));
        countIcon.setLayoutX(Constants.PREVIEW_CARD_WIDTH.getValue() * 0.7);
        countIcon.setLayoutY(Constants.PREVIEW_CARD_HEIGHT.getValue() * 0.8);
        this.getChildren().add(countIcon);
        this.name = name;
        this.count = count;
        Label countLabel = new Label(String.valueOf(count));
        countLabel.setStyle("-fx-text-fill: black");
        countLabel.setLayoutX(countIcon.getLayoutX() + Constants.PREVIEW_COUNT_ICON_WIDTH.getValue());
        countLabel.setLayoutY(countIcon.getLayoutY() + Constants.PREVIEW_COUNT_ICON_HEIGHT.getValue() / 4);
        this.getChildren().add(countLabel);
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
