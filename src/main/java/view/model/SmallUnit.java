package view.model;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import view.Constants;

public class SmallUnit extends SmallCard {

    int power;
    boolean hero;
    Label powerField;

    SmallUnit(String name, String description, String type, String ability, int power, boolean hero) {

        super(name, description, type, ability);
        this.power = power;
        this.hero = hero;

        Rectangle rectangle = new Rectangle(Constants.SMALL_CARD_WIDTH.getValue() / 2, Constants.SMALL_CARD_WIDTH.getValue() / 2);
        if (hero) {
            rectangle.setFill(getIconImagePattern("power_hero"));
        } else {
            rectangle.setFill(getIconImagePattern("power_normal"));
        }
        rectangle.setLayoutX(-2);
        rectangle.setLayoutY(-2);
        this.getChildren().add(rectangle);

        powerField = new Label(String.valueOf(power));
        powerField.setLayoutX(0);
        powerField.setLayoutY(0);
        powerField.setPrefWidth(Constants.SMALL_CARD_WIDTH.getValue() / 4);
        powerField.setPrefHeight(Constants.SMALL_CARD_WIDTH.getValue() / 4);
        powerField.setAlignment(javafx.geometry.Pos.CENTER);
        powerField.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        if (hero) powerField.setStyle("-fx-text-fill: white; -fx-font-size: 10");
        else powerField.setStyle("-fx-text-fill: black; -fx-font-size: 10");
        this.getChildren().add(powerField);

        Rectangle typeRectangle = new Rectangle(Constants.SMALL_CARD_WIDTH.getValue() / 4, Constants.SMALL_CARD_WIDTH.getValue() / 4);
        typeRectangle.setFill(getIconImagePattern("card_row_" + type.toLowerCase()));
        typeRectangle.setLayoutX(Constants.SMALL_CARD_WIDTH.getValue() * 3 / 4);
        typeRectangle.setLayoutY(Constants.SMALL_CARD_HEIGHT.getValue() - Constants.SMALL_CARD_WIDTH.getValue() / 4);
        this.getChildren().add(typeRectangle);

        if (ability.equals("None")) return;
        Rectangle abilityRectangle = new Rectangle(Constants.SMALL_CARD_WIDTH.getValue() / 4, Constants.SMALL_CARD_WIDTH.getValue() / 4);
        abilityRectangle.setFill(getIconImagePattern("card_ability_" + ability.toLowerCase()));
        abilityRectangle.setLayoutX(Constants.SMALL_CARD_WIDTH.getValue() / 2);
        abilityRectangle.setLayoutY(Constants.SMALL_CARD_HEIGHT.getValue() - Constants.SMALL_CARD_WIDTH.getValue() / 4);
        this.getChildren().add(abilityRectangle);
    }

    public static SmallUnit getInstance(String name, String description, String type, String Ability, int power, boolean hero, String uniqueCode) {
        SmallUnit smallUnit = (SmallUnit) smallCards.get(uniqueCode);
        if (smallUnit == null) {
            smallUnit = new SmallUnit(name, description, type, Ability, power, hero);
            smallCards.put(uniqueCode, smallUnit);
        }
        return smallUnit;
    }
}
