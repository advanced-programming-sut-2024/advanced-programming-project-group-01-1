package view.model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.Constants;

public class SmallUnit extends SmallCard {

    int power;
    boolean hero;
    Label powerField;

    SmallUnit(String name, String description, String type, String ability, int power, String currentPower, boolean hero) {

        super(name, description, type, ability);
        this.power = power;
        this.hero = hero;

        ImageView powerIcon = new ImageView();
        if (hero) {
            powerIcon.setImage(new Image(SmallCard.class.getResourceAsStream("/images/icons/power_hero.png")));
        } else {
            powerIcon.setImage(new Image(SmallCard.class.getResourceAsStream("/images/icons/power_normal.png")));
        }
        powerIcon.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue() / 2);
        powerIcon.setFitHeight(Constants.SMALL_CARD_WIDTH.getValue() / 2);
        powerIcon.setPreserveRatio(false);
        powerIcon.setLayoutX(-2);
        powerIcon.setLayoutY(-2);
        this.getChildren().add(powerIcon);

        powerField = new Label(currentPower);
        powerField.setLayoutX(0);
        powerField.setLayoutY(0);
        powerField.setPrefWidth(Constants.SMALL_CARD_WIDTH.getValue() / 4);
        powerField.setPrefHeight(Constants.SMALL_CARD_WIDTH.getValue() / 4);
        powerField.setAlignment(javafx.geometry.Pos.CENTER);
        powerField.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        this.getChildren().add(powerField);
        setCurrentPower(currentPower);

        ImageView typeIcon = new ImageView();
        typeIcon.setImage(new Image(SmallCard.class.getResourceAsStream("/images/icons/card_row_" + type.toLowerCase() + ".png")));
        typeIcon.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue() / 4);
        typeIcon.setFitHeight(Constants.SMALL_CARD_WIDTH.getValue() / 4);
        typeIcon.setLayoutX(Constants.SMALL_CARD_WIDTH.getValue() * 3 / 4);
        typeIcon.setLayoutY(Constants.SMALL_CARD_HEIGHT.getValue() - Constants.SMALL_CARD_WIDTH.getValue() / 4);
        typeIcon.setPreserveRatio(false);
        this.getChildren().add(typeIcon);

        if (ability.equals("None")) return;
        ImageView abilityIcon = new ImageView();
       // System.out.println("loading ability: " + ability.toLowerCase()  + ".png");
        abilityIcon.setImage(new Image(SmallCard.class.getResourceAsStream("/images/icons/card_ability_" + ability.toLowerCase() + ".png")));
        abilityIcon.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue() / 4);
        abilityIcon.setFitHeight(Constants.SMALL_CARD_WIDTH.getValue() / 4);
        abilityIcon.setLayoutX(Constants.SMALL_CARD_WIDTH.getValue() / 2);
        abilityIcon.setLayoutY(Constants.SMALL_CARD_HEIGHT.getValue() - Constants.SMALL_CARD_WIDTH.getValue() / 4);
        abilityIcon.setPreserveRatio(false);
        this.getChildren().add(abilityIcon);
    }

    public void setCurrentPower(String currentPower) {
        powerField.setText(currentPower);
        int power = Integer.parseInt(currentPower);
        if (power < this.power) {
            powerField.setStyle("-fx-text-fill: red; -fx-font-size: 10");
        } else if (power > this.power) {
            powerField.setStyle("-fx-text-fill: green; -fx-font-size: 10");
        } else {
            if (hero) powerField.setStyle("-fx-text-fill: white; -fx-font-size: 10");
            else powerField.setStyle("-fx-text-fill: black; -fx-font-size: 10");
        }
    }

    public int getPower() {
        return power;
    }

    public int getCurrentPower() {
        return Integer.parseInt(powerField.getText());
    }

    public static SmallUnit getInstance(String name, String description, String type, String Ability, int power, String currentPower, boolean hero, String uniqueCode) {
        SmallUnit smallUnit = (SmallUnit) smallCards.get(uniqueCode);
        if (smallUnit == null) {
            smallUnit = new SmallUnit(name, description, type, Ability, power, currentPower, hero);
            smallCards.put(uniqueCode, smallUnit);
        }  else {
            smallUnit.setCurrentPower(currentPower);
        }
        return smallUnit;
    }
}
