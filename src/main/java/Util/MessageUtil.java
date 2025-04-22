package Util;

import Model.Message;

public class MessageUtil {
    public static final int MAX_MESSAGE_TEXT_LENGTH = 255;

    public static boolean isValidMessage(Message msg) {
        return msg != null && !msg.getMessage_text().isEmpty()
                && msg.getMessage_text().length() <= MAX_MESSAGE_TEXT_LENGTH;
    }
}
