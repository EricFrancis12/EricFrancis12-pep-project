package Util;

import Model.Message;

public class MessageUtil {
    public static final int MAX_MESSAGE_TEXT_LENGTH = 255;

    public static boolean isValidMessageText(String msgText) {
        return !msgText.isEmpty() && msgText.length() <= MAX_MESSAGE_TEXT_LENGTH;
    }

    public static boolean isValidMessage(Message msg) {
        return msg != null && isValidMessageText(msg.getMessage_text());
    }
}
