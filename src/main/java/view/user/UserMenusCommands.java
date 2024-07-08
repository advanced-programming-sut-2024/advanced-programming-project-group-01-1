package view.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserMenusCommands {
    CHANGE_USERNAME("change username -u (?<username>\\S+)"),
    CHANGE_NICKNAME("change nickname -n (?<nickname>\\S+)"),
    CHANGE_EMAIL("change email -e (?<email>\\S+)"),
    CHANGE_PASSWORD("change password -p (?<newPassword>\\S+) -o (?<oldPassword>\\S+)"),
    ENTER_USER_INFO("menu enter user info menu"),
    SHOW_CURRENT_MENU("show current menu"),
    HISTORY("menu enter history menu"),
    GAME_HISTORY("game history (-n (?<numberOfGames>\\d+))?"),
    NEXT_PAGE("next page"),
    PREVIOUS_PAGE("previous page"),
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
}
