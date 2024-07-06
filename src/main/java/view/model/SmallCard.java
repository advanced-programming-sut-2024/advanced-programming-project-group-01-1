package view.model;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.Constants;

import java.util.HashMap;

public class SmallCard extends Pane {

    static HashMap<String, ImagePattern> iconImagePatterns = new HashMap<>();
    static HashMap<String, ImagePattern> imagePatterns = new HashMap<>();
    static HashMap<String, SmallCard> smallCards = new HashMap<>();

    String name;
    String description;
    String type;
    String ability;
    ImagePattern front;
    Rectangle picture;

    public static ImagePattern getImagePattern(String name) {
        ImagePattern imagePattern = imagePatterns.get(name);
        if (imagePattern == null) {
            System.out.println("Loading image: " + name + ".jpg");
            imagePattern = new ImagePattern(new Image(SmallCard.class.getResourceAsStream("/images/smallcards/" + name + ".jpg")));
            imagePatterns.put(name, imagePattern);
        }
        return imagePattern;
    }

    public static ImagePattern getIconImagePattern(String name) {
        ImagePattern imagePattern = iconImagePatterns.get(name);
        if (imagePattern == null) {
            System.out.println("Loading image: " + name + ".png");
            imagePattern = new ImagePattern(new Image(SmallCard.class.getResourceAsStream("/images/icons/" + name + ".png")));
            iconImagePatterns.put(name, imagePattern);
        }
        return imagePattern;
    }

    SmallCard(String name, String description, String type, String ability) {
        super();
        this.setPrefWidth(Constants.SMALL_CARD_WIDTH.getValue());
        this.setPrefHeight(Constants.SMALL_CARD_HEIGHT.getValue());
        this.name = name;
        this.description = description;
        this.type = type;
        this.ability = ability;
        front = getImagePattern(name);
        picture = new Rectangle(Constants.SMALL_CARD_WIDTH.getValue(), Constants.SMALL_CARD_HEIGHT.getValue());
        picture.setFill(front);
        this.getChildren().add(picture);

        Rectangle rectangle = new Rectangle(Constants.SMALL_CARD_WIDTH.getValue() / 2, Constants.SMALL_CARD_WIDTH.getValue() / 2);
        if (type.equals("faction")){
            return;
        }
        switch (name) {
            case "Decoy":
                rectangle.setFill(getIconImagePattern("power_decoy"));
                break;
            case "Commander's Horn":
                rectangle.setFill(getIconImagePattern("power_horn"));
                break;
            case "Scorch":
                rectangle.setFill(getIconImagePattern("power_scorch"));
                break;
            case "Biting Frost":
                rectangle.setFill(getIconImagePattern("power_frost"));
                break;
            case "Impenetrable Fog":
                rectangle.setFill(getIconImagePattern("power_fog"));
                break;
            case "Torrential Rain":
                rectangle.setFill(getIconImagePattern("power_rain"));
                break;
            case "Clear Weather":
                rectangle.setFill(getIconImagePattern("power_clear"));
                break;
            case "Skellige Storm":
                rectangle.setFill(getIconImagePattern("power_storm"));
                break;
            case "Mardroeme":
                rectangle.setFill(getIconImagePattern("power_mardroeme"));
                break;
            default:
                return;
        }
        rectangle.setLayoutX(-5);
        rectangle.setLayoutY(-5);
        this.getChildren().add(rectangle);
    }

    public static SmallCard getInstance(String name, String description, String type, String Ability, String uniqueCode) {
        SmallCard smallCard = smallCards.get(uniqueCode);
        if (smallCard == null) {
            smallCard = new SmallCard(name, description, type, Ability);
            smallCards.put(uniqueCode, smallCard);
        }
        return smallCard;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
