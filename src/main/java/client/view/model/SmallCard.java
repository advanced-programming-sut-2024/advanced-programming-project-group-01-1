package client.view.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import client.view.Constants;

import java.util.HashMap;

public class SmallCard extends Pane {


    static HashMap<String, SmallCard> smallCards = new HashMap<>();
    static HashMap<String, String> iconNames = new HashMap<>();

    static {
        iconNames.put("Decoy", "power_decoy");
        iconNames.put("Commander's Horn", "power_horn");
        iconNames.put("Scorch", "power_scorch");
        iconNames.put("Biting Frost", "power_frost");
        iconNames.put("Impenetrable Fog", "power_fog");
        iconNames.put("Torrential Rain", "power_rain");
        iconNames.put("Clear Weather", "power_clear");
        iconNames.put("Skellige Storm", "power_storm");
        iconNames.put("Mardroeme", "power_mardroeme");
    }

    String name;
    String description;
    String type;
    String ability;
    Image front;
    ImageView picture;

    SmallCard(String name, String description, String type, String ability) {
        super();
        this.setPrefWidth(Constants.SMALL_CARD_WIDTH.getValue());
        this.setPrefHeight(Constants.SMALL_CARD_HEIGHT.getValue());
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.name = name;
        this.description = description;
        this.type = type;
        this.ability = ability;
        front = new Image(SmallCard.class.getResourceAsStream("/images/smallcards/" + name + ".jpg"));
        picture = new ImageView(front);
        picture.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue());
        picture.setFitHeight(Constants.SMALL_CARD_HEIGHT.getValue());
        picture.setPreserveRatio(false);
        this.getChildren().add(picture);

        if (type.equals("faction") || type.equals("leader")) return;
        if (iconNames.get(name) != null) {
            ImageView icon = new ImageView(new Image(SmallCard.class.getResourceAsStream("/images/icons/" + iconNames.get(name) + ".png")));
            icon.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue() / 2);
            icon.setFitHeight(Constants.SMALL_CARD_WIDTH.getValue() / 2);
            icon.setLayoutX(0);
            icon.setLayoutY(0);
            icon.setPreserveRatio(false);
            this.getChildren().add(icon);
        }
    }

    public static SmallCard getInstance(String name, String description, String type, String Ability, String uniqueCode) {
        SmallCard smallCard = smallCards.get(uniqueCode);
        if (smallCard == null) {
            smallCard = new SmallCard(name, description, type, Ability);
            smallCards.put(uniqueCode, smallCard);
        }
        return smallCard;
    }

    public static void clearCache() {
        smallCards.clear();
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
