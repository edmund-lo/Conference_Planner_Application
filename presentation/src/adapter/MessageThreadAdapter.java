package adapter;

import model.Message;
import model.MessageThread;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for mapping MessageThread entities to models
 */
public class MessageThreadAdapter {
    private static final MessageThreadAdapter Instance = new MessageThreadAdapter();

    /**
     * Gets current instance of a MessageThreadAdapter
     * @return An instance of a MessageThreadAdapter object
     */
    public static MessageThreadAdapter getInstance() { return Instance; }

    /**
     * Empty MessageThreadAdapter constructor
     */
    private MessageThreadAdapter() {}

    /**
     * Adapts data into a list of message threads
     * @param data JSONArray of JSON formatted MessageThread entities
     * @return List of MessageThread models
     */
    public List<MessageThread> adaptData(JSONArray data) {
        List<MessageThread> messageThread = new ArrayList<>();
        for (Object datum : data) {
            JSONObject jsonObject = (JSONObject) datum;
            messageThread.add(mapMessageThread(jsonObject));
        }
        return messageThread;
    }

    /**
     * Maps the jsonObject into a MessageThread model
     * @param jsonObject JSONObject of JSON formatted MessageThread entity
     * @return MessageThread model with mapped attributes
     */
    private MessageThread mapMessageThread(JSONObject jsonObject) {
        String messageId = String.valueOf(jsonObject.get("messageThreadId"));
        String senderName = String.valueOf(jsonObject.get("sender"));
        JSONArray recipientsArray = (JSONArray) jsonObject.get("receivers");
        List<String> recipientNames = new ArrayList<>();
        for (Object o : recipientsArray) recipientNames.add(String.valueOf(o));
        String subject = String.valueOf(jsonObject.get("subject"));
        boolean unread = jsonObject.get("read").equals(false);
        List<Message> messageHistory = MessageAdapter.getInstance().adaptData((JSONArray) jsonObject.get("messages"));

        return new MessageThread(messageId, senderName, recipientNames, subject, unread, messageHistory);
    }
}
