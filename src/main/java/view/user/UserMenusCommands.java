package view.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserMenusCommands {
    CHANGE_USERNAME("change username -u (?<username>\\w+)"),
    CHANGE_NICKNAME("change nickname -n (?<nickname>\\w+)"),
    CHANGE_EMAIL("change email -e (?<email>\\w+)"),
    CHANGE_PASSWORD("change password -p (?<newPassword>\\w+) -o (?<oldPassword>\\w+)"),
    ENTER_USER_INFO("menu enter user info menu"),
    SHOW_CURRENT_MENU("show current menu"),
    GAME_HISTORY("game history (-n (?<numberOfGames>\\d+))?"),
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
