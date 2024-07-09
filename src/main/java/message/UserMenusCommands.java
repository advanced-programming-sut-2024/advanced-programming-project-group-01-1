package message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserMenusCommands {
    CHANGE_USERNAME("change username -u (?<username>\\S+)"),
    CHANGE_NICKNAME("change nickname -n (?<nickname>\\S+)"),
    CHANGE_EMAIL("change email -e (?<email>\\S+)"),
    CHANGE_PASSWORD("change password -p (?<newPassword>\\S+) -o (?<oldPassword>\\S+)"),
    SAVE_CHANGES( "save changes -u (?<username>\\S+) -n (?<nickname>\\S+) -e (?<email>\\S+) -p (?<oldPassword>\\S+)" +
            " -np (?<newPassword>\\S+) -cp (?<confirmNewPassword>\\S+)"),
    GET_USERNAME("get username"),
    GET_NICKNAME("get nickname"),
    GET_EMAIL("get email"),
    GET_MAX_RATING("get max rating"),
    GET_RANK("get rank"),
    GET_PLAYED_MATCHES("get number of played matches"),
    GET_WINS("get number of wins"),
    GET_DRAWS("get number of draws"),
    GET_LOSSES("get number of losses"),
    ENTER_USER_INFO("menu enter user info menu"),
    SHOW_CURRENT_MENU("show current menu"),
    GO_TO_HISTORY_MENU("menu enter history menu"),
    GAME_HISTORY("game history( -n (?<numberOfGames>\\d+))?"),
    NEXT_PAGE("next page"),
    PREVIOUS_PAGE("previous page"),
    SHOW_PAGE_INFO("show page info -n (?<pageNumber>\\d+)"),
    GET_PAGE_COUNT("get page count"),
    CHECK_ONLINE("check online (?<username>.*)"),
    SHOW_FRIENDS("show friends"),
    SHOW_RECEIVED_FRIEND_REQUESTS("show received friend requests"),
    SHOW_SENT_FRIEND_REQUESTS("show sent friend requests"),
    ACCEPT_FRIEND_REQUEST("accept friend request (?<username>.*)"),
    DECLINE_FRIEND_REQUEST("decline friend request (?<username>.*)"),
    REMOVE_FRIEND("remove friend (?<username>.*)"),
    SEND_FRIEND_REQUEST("send friend request (?<username>.*)"),
    UNSEND_FRIEND_REQUEST("unsend friend request (?<username>.*)"),
    SHOW_PLAYERS_INFO("show players info"),
    EXIT("menu exit");

	private final Pattern pattern;

    UserMenusCommands(String regex) {
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
