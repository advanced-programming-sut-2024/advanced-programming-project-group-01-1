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
    GET_MAX_SCORE("get max Score"),
    GET_RANK("get rank"),
    GET_PLAYED_MATCHES("get number of played matches"),
    GET_WINS("get number of wins"),
    GET_DRAWS("get number of draws"),
    GET_LOSSES("get number of losses"),
    ENTER_USER_INFO("menu enter user info menu"),
    SHOW_CURRENT_MENU("show current menu"),
    GAME_HISTORY("game history( -n (?<numberOfGames>\\d+))?"),
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
