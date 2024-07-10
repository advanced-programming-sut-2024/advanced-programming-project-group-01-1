package client.view.model;

import java.util.Date;

public class Chat {

    private String message, replyMessage, username;
    private Date time;

    public Chat(String message, String replyMessage, String username) {
        this.message = message;
        this.replyMessage = replyMessage;
        this.username = username;
        time = new Date();
    }

    @Override
    public String toString() {
        if (replyMessage != null && !replyMessage.isEmpty()) {
            return "reply to:\n" + replyMessage + "\n--------------------\n" + username + ":\n" + message + "\n" + time;
        }
        return username + ":\n" + message + "\n" + time;
    }
}
