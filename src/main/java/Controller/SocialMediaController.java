package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import Util.AccountUtil;
import Util.MessageUtil;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    private final AccountService acctService = new AccountService();
    private final MessageService msgService = new MessageService();

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::handleRegister);
        app.post("/login", this::handleLogin);

        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::handleGetAllMessages);
        app.get("/messages/{message_id}", this::handleGetMessageById);
        app.patch("/messages/{message_id}", this::handleUpdateMessageById);
        app.delete("/messages/{message_id}", this::handleDeleteMessageById);

        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByAccountId);

        return app;
    }

    private void handleRegister(Context ctx) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Account acct = mapper.readValue(ctx.body(), Account.class);
            if (!AccountUtil.isValidAccount(acct)) {
                ctx.status(400);
                return;
            }

            Account addedAcct = acctService.addAccount(acct);
            if (addedAcct == null) {
                ctx.status(400);
                return;
            }

            ctx.json(mapper.writeValueAsString(addedAcct));

        } catch (JsonProcessingException ex) {
            ctx.status(400);
        }
    }

    private void handleLogin(Context ctx) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Account acct = mapper.readValue(ctx.body(), Account.class);

            Account foundAcct = acctService.getAccountByUsernameAndPassword(
                    acct.getUsername(),
                    acct.getPassword());
            if (foundAcct == null) {
                ctx.status(401);
                return;
            }

            ctx.json(mapper.writeValueAsString(foundAcct));

        } catch (JsonProcessingException ex) {
            ctx.status(401);
        }
    }

    private void handleCreateMessage(Context ctx) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Message msg = mapper.readValue(ctx.body(), Message.class);
            if (!MessageUtil.isValidMessage(msg)) {
                ctx.status(400);
                return;
            }

            Message addedMsg = msgService.addMessage(msg);
            if (addedMsg == null) {
                ctx.status(400);
                return;
            }

            ctx.json(mapper.writeValueAsString(addedMsg));

        } catch (JsonProcessingException ex) {
            ctx.status(400);
        }
    }

    private void handleGetAllMessages(Context ctx) {
        ctx.json(msgService.getAllMessages());
    }

    private void handleGetMessageById(Context ctx) {
        try {
            int msgId = Integer.parseInt(ctx.pathParam("message_id"));
            ctx.json(msgService.getMessageById(msgId));

        } catch (NumberFormatException ex) {
            ctx.status(200);
        }
    }

    private void handleUpdateMessageById(Context ctx) {
        try {
            int msgId = Integer.parseInt(ctx.pathParam("message_id"));

            ObjectMapper mapper = new ObjectMapper();

            Message msg = mapper.readValue(ctx.body(), Message.class);
            if (!MessageUtil.isValidMessageText(msg.getMessage_text())) {
                ctx.status(400);
                return;
            }

            Message updatedMsg = msgService.updateMessageTextById(
                    msgId,
                    msg.getMessage_text());
            if (updatedMsg == null) {
                ctx.status(400);
                return;
            }

            ctx.json(mapper.writeValueAsString(updatedMsg));

        } catch (NumberFormatException ex) {
            ctx.status(200);
        } catch (JsonProcessingException ex) {
            ctx.status(400);
        }
    }

    private void handleDeleteMessageById(Context ctx) {
        try {
            int msgId = Integer.parseInt(ctx.pathParam("message_id"));

            Message msg = msgService.getMessageById(msgId);
            if (msg == null) {
                ctx.status(200);
                return;
            }

            msgService.deleteMessageById(msgId);
            ctx.json(msg);

        } catch (NumberFormatException e) {
            ctx.status(200);
        }
    }

    private void handleGetMessagesByAccountId(Context ctx) {
        try {
            int acctId = Integer.parseInt(ctx.pathParam("account_id"));
            ctx.json(msgService.getMessagesByAccountId(acctId));

        } catch (NumberFormatException e) {
            ctx.status(200);
        }
    }
}
