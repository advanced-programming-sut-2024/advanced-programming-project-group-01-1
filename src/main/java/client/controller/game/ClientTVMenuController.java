package client.controller.game;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.ClientMainMenu;
import client.view.game.ClientStreamMenu;
import message.GameMenusCommands;
import message.Result;

public class ClientTVMenuController {

    public static Result getGames() {
        String command = GameMenusCommands.GET_GAMES.getPattern();
        return TCPClient.send(command);
    }

    public static Result spectate(String username1, String username2) {
        String command = GameMenusCommands.SPECTATE.getPattern();
        command = command.replace("<(?<username1>.+)", username1);
        command = command.replace("(?<username2>.+)", username2);
        Result result = TCPClient.send(command);
        if (result.isSuccessful()) {
            ClientAppview.setMenu(new ClientStreamMenu());
        }
    }

    public static Result back() {
        String command = GameMenusCommands.BACK.getPattern();
        Result result = TCPClient.send(command);
        ClientAppview.setMenu(new ClientMainMenu());
    }
}
