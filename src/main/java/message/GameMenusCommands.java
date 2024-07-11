package message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenusCommands {
    SEND_MATCH_REQUEST("send match request -p2 (?<opponent>\\S+)"),
    GET_MATCH_REQUESTS("get all match requests"),
    HANDLE_MATCH_REQUEST("(?<handle>(accept|reject)) match request (?<sender>\\S+)"),
    CHECK_REQUEST("check request"),
    STOP_WAIT("stop waiting"),
    IS_WAITING("is still waiting"),
    ENTER_TOURNAMENT("enter tournament"),
    CHECK_TOURNAMENT("check tournament"),
    GET_TOURNAMENT_INFO("get tournament info"),
    READY("ready to play"),
    CANCEL_READY("cancel ready"),
    CHECK_OPPONENT_READY("check opponent being ready"),
    IS_DECK_VALID("is deck valid"),
    SHOW_FACTIONS("show factions"),
    SELECT_FACTION("select faction -f (?<faction>.+)"),
    SHOW_CARDS("show cards"),
    SHOW_DECK("show deck"),
    SHOW_INFORMATION_CURRENT_USER("show information current user"),
    SAVE_DECK_WITH_FILE_ADDRESS("save deck -f (?<fileAddress>\\S+)"),
    SAVE_DECK_WITH_NAME("save deck -n (?<name>\\S+)"),
    SAVE_DECK("save deck"),
    LOAD_DECK_WITH_FILE_ADDRESS("load deck -f (?<fileAddress>\\S+)"),
    LOAD_DECK_WITH_NAME("load deck -n (?<name>\\S+)"),
    LOAD_DECK("load deck -f (?<deckFson>\\S+)"),
    SHOW_LEADERS("show leaders"),
    SELECT_LEADER("select leader -l (?<leaderNumber>\\d+)"),
    ADD_TO_DECK("add to deck -c (?<cardName>.+) (?<count>\\d+)"),
    REMOVE_FROM_DECK("remove from deck -n (?<cardNumber>\\d+) -c (?<count>\\d+)"),
    PREFER_FIRST("prefer first"),
    PREFER_SECOND("prefer second"),
    GET_PREFERRED_TURN("get preference"),
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
    SHOW_CURRENT_HAND("show current hand"),
    SHOW_CARDS_GRAPHIC("show cards for graphic"),
    SHOW_DECK_GRAPHIC("show deck for graphic"),
    SHOW_ROW_GRAPHIC("show row (?<rowNumber>\\d+) for graphic"),
    SHOW_PILE_GRAPHIC("show discarded pile for graphic"),
    SHOW_LEADER_GRAPHIC("show current leader for graphic"),
    SHOW_FACTION_GRAPHIC("show current faction for graphic"),
    SHOW_WEATHER_GRAPHIC("show weather for graphic"),
    GET_USERNAMES("get usernames"),
    GET_DESCRIPTION("get description (?<cardName>.+)"),
    PASSED_STATE("passed state"),
    IS_LEADERS_DISABLE("is leaders disable"),
    IS_GAME_OVER("is game over"),
    IS_GAME_WIN("is game win"),
    IS_GAME_DRAW("is game draw"),
    IS_OPPONENT_ONLINE("is opponent online"),
    IS_ROW_DEBUFFED("is row debuffed (?<rowNumber>\\d+)"),
    IS_MY_TURN("is my turn"),
    GET_POWERS("get powers"),
    GET_SCORES("get scores"),
    SEND_MESSAGE("send message (?<message>(?:.|\n)+)"),
    SEND_REACTION("send reaction (?<reaction>.+)"),
    GET_REACTION("get reaction"),
    GET_CHATS("get messages"),
    PASS_ROUND("pass round"),
    GET_OPPONENT_MOVE("get opponent move (?<number>\\d+)"),
    EXIT_MATCH_FINDER("menu exit"),
    CREATE_GAME("create game -p2 (?<opponent>\\S+)"),
    SHOW_HAND("show hand"),
    SELECT_CARD("select card (?<cardNumber>(-)?\\d+)"),
    END_GAME("end game"),

    IS_ASKING("is asking"),
    GET_ASKER_CARDS("get asker cards"),
    GET_ASKER_PTR("get asker ptr"),
    IS_ASKER_OPTIONAL("is asker optional"),
    CHEAT_MENU("cheat"),
    CHEAT_DEBUFF_ROW("debuff row (?<rowNumber>(0|1|2|3|4|5))"),
    CHEAT_CLEAR_ROW("clear row (?<rowNumber>(0|1|2|3|4|5))"),
    CHEAT_ADD_CARD("add card (?<cardName>.+)"),
    CHEAT_ADD_POWER("add power (?<power>\\d+)"),
    CHEAT_MOVE_FROM_DECK("move from deck to hand"),
    CHEAT_HEAL("heal"),
    CHEAT_CLEAR_WEATHER("clear weather"),
    GO_QUICK_MATCH("go to quick match menu"),
    QUICK_MATCH_LIST("get quick matches list"),
    START_QUICK_MATCH("start quick match with (?<opponent>\\S+)"),
    NEW_QUICK_MATCH("create new quick match"),
    CHECK_MATCH_READY("check whether quick match is ready to play"),
    CANCEL_QUICK_MATCH("cancel quick match"),
    BACK("back");

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

    public String getPattern() {
        return pattern.pattern();
    }
}
