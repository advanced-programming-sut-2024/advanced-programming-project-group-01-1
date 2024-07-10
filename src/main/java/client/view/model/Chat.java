package client.view.model;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        if (replyMessage != null && !replyMessage.isEmpty()) {
            return "reply to:\n--------------------\n" + replyMessage + "--------------------\n" + username + ":\n" + message + "\n" + dateFormat.format(time);
        }
        return username + ":\n" + message + "\n" + dateFormat.format(time);
    }
}
