package view.game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenusCommands {
    CREATE_GAME("create game -p2 (?<opponent>\\S+)"),
    SHOW_FACTIONS("show factions"),
    SELECT_FACTION("select faction -f (?<faction>\\S+)"),
    SHOW_CARDS("show cards"),
    SHOW_DECK("show deck"),
    SHOW_INFORMATION_CURRENT_USER("show information current user"),
    SAVE_DECK_WITH_FILE_ADDRESS("save deck -f (?<fileAddress>\\S+)"),
    SAVE_DECK_WITH_NAME("save deck -n (?<name>\\S+)"),
    LOAD_DECK_WITH_FILE_ADDRESS("load deck -f (?<fileAddress>\\S+)"),
    LOAD_DECK_WITH_NAME("load deck -n (?<name>\\S+)"),
    SHOW_LEADERS("show leaders"),
    SHOW_HAND("show hand"),
    SELECT_LEADER("select leader -l (?<leaderNumber>\\d+)"),
    ADD_TO_DECK("add to deck -c (?<cardName>\\S+) (?<count>\\d+)"),
    REMOVE_FROM_DECK("remove from deck -n (?<cardNumber>\\d+) -c (?<count>\\d+)"),
    CHANGE_TURN("change turn"),
    START_GAME("start game"),
    VETO_CARD("veto card (?<cardNumber>\\d+)"),
    IN_HAND_DECK("in hand deck(?: (?<option>-option) (?<cardNumber>\\d+))?"),
    REMAINING_CARDS_TO_PLAY("remaining cards to play"),
    OUT_OF_PLAY_CARDS("out of play cards"),
    CARDS_IN_ROW("cards in row (?<rowNumber>\\d+)"),
    SPELLS_IN_PLAY("spells in play"),
    PLACE_CARD("place card (?<cardNumber>\\d+)(?:(?<inRow> in row )(?<rowNumber>\\d+))"),
    SHOW_COMMANDER("show commander"),
    COMMANDER_POWER_PLAY("commander power play"),
    SHOW_PLAYERS_INFO("show players info"),
    SHOW_PLAYERS_LIVES("show players lives"),
    SHOW_NUMBER_OF_CARDS_IN_HAND("show number of cards in hand"),
    SHOW_TURN_INFO("show turn info"),
    SHOW_TOTAL_SCORE("show total score"),
    SHOW_TOTAL_SCORE_OF_ROW("show total score of row (?<rowNumber>\\d+)"),
    PASS_ROUND("pass round");

    private final Pattern pattern;

    GameMenusCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
