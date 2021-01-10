package entities;

import org.json.simple.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageThread implements Serializable {
    private String sender;
    private List<String> receivers;
    private String messageThreadId;
    private String subject;
    private List<Message> messages;

    /**
     * Constructor for Entities.MessageThread
     *
     * @param sender The username for the sender of these messages
     * @param messageThreadId The id of this collection of messages
     * @param subject The subject line of this collection of messages
     */

    public MessageThread(String sender, List<String> receivers, String messageThreadId, String subject){
        this.sender = sender;
        this.receivers = receivers;
        this.messageThreadId = messageThreadId;
        this.subject = subject;
        this.messages = new ArrayList<>();
    }

    /**
     * getter for username of the sender of these messages
     *
     * @return The sender's username
     */
    public String getSender() { return this.sender; }

    /**
     * getter for usernames of the receivers of these messages
     *
     * @return The a list of receivers' usernames
     */
    public List<String> getReceivers() { return this.receivers; }

    /**
     * getter for messageThreadId of these messages
     *
     * @return The Id of this collection of messages
     */
    public String getMessageThreadId() { return this.messageThreadId; }

    /**
     * getter for subject line of these messages
     *
     * @return The subject/title of these messages
     */
    public String getSubject() { return this.subject; }

    /**
     * getter for all the messages stored in this thread
     *
     * @return List of Messages
     */
    public List<Message> getMessages() {
        return this.messages;
    }

    /**
     * adds message to this threads messages
     * @param message the message
     */
    public void addMessages(Message message) {
        this.messages.add(message);
    }

    /**
     * converts all of this thread's messages to JSON
     * @return a JSONArray where each entry is a message in JSON format
     */
    public JSONArray messagesToJSON(){
        JSONArray a = new JSONArray();
        for (Message m: messages)
            a.add(m.convertToJSON());

        return a;
    }
    /**
     * For converting this class to a JSON format
     * @return a JSONObject that describes this entity
     */
    @SuppressWarnings("unchecked")
    public JSONObject convertToJSON() {
        JSONObject item = new JSONObject();

        item.put("sender", getSender());
        item.put("receivers", getReceivers());
        item.put("subject", getSubject());
        item.put("messageThreadId", getMessageThreadId());
        item.put("messages", messagesToJSON());
        item.put("read", false);
        return item;
    }
}