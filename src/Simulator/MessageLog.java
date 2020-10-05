package Simulator;

import java.util.ArrayList;

public class MessageLog {

    private ArrayList<String> messages;

    private static MessageLog messageLog;

    private MessageLog() {
        messages = new ArrayList<>();
    }

    public static MessageLog getInstance() {
        if (messageLog == null) {
            messageLog = new MessageLog();
        }
        return messageLog;
    }

    public void addMessage(String message) {
        messages.add(message);

        if (messages.size() > 5) {
            messages.remove(0);
        }
    }

    public ArrayList<String> getMessages() {
        return messages;
    }


    public String getMessagesString() {
        String out = "";
        for (String message : messages) {
            out += message + "\n";

        }
        return out;
    }
}
