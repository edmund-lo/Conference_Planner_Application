package adapter;

import model.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for mapping Message entities to models
 */
public class MessageAdapter {
    private static final MessageAdapter Instance = new MessageAdapter();

    /**
     * Gets current instance of a MessageAdapter
     * @return An instance of a MessageAdapter object
     */
    public static MessageAdapter getInstance() { return Instance; }

    /**
     * Empty MessageAdapter constructor
     */
    private MessageAdapter() {}

    /**
     * Adapts data into a list of messages
     * @param data JSONArray of JSON formatted Message entities
     * @return List of Message models
     */
    public List<Message> adaptData(JSONArray data) {
        List<Message> messageThread = new ArrayList<>();
        for (Object datum : data) {
            JSONObject jsonObject = (JSONObject) datum;
            messageThread.add(mapMessage(jsonObject));
        }
        return messageThread;
    }

    /**
     * Maps the jsonObject into a Message model
     * @param jsonObject JSONObject of JSON formatted Message entity
     * @return Message model with mapped attributes
     */
    private Message mapMessage(JSONObject jsonObject) {
        String senderName = String.valueOf(jsonObject.get("sender"));
        String content = String.valueOf(jsonObject.get("content"));
        LocalDateTime messageTime = (LocalDateTime) jsonObject.get("time");

        return new Message(senderName, content, messageTime);
    }
}
