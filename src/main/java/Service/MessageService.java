package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private final MessageDAO msgDAO = new MessageDAO();

    public Message addMessage(Message msg) {
        return msgDAO.insertMessage(msg);
    }

    public List<Message> getAllMessages() {
        return msgDAO.getAllMessages();
    }

    public Message getMessageById(int msgId) {
        return msgDAO.getMessageById(msgId);
    }

    public List<Message> getMessagesByAccountId(int acctId) {
        return msgDAO.getMessagesByAccountId(acctId);
    }
}
