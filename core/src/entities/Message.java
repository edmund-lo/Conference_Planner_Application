package entities;

import org.json.simple.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Implementation of a message and all details about it.
 */
public class Message implements Serializable {
    private String senderName;
    private LocalDateTime messageTime;
    private String content;

    /**
     * Constructor for Entities.Message
     *
     * @param content The text of this message
     * @param senderName The id of the sender of this message
     * @param messageTime The time this message is sent and received
     */

    public Message( String senderName, LocalDateTime messageTime, String content){
        this.senderName = senderName;
        this.messageTime = messageTime;
        this.content = content;
    }

    /**
     * getter for the sender's username of this message
     *
     * @return The username of the sender
     */
    public String getSenderName() { return this.senderName; }

    /**
     * getter for the sent time of this message
     *
     * @return the messaging time in LocalDataTime format
     */
    public LocalDateTime getMessageTime() {
        return this.messageTime;
    }

    /**
     * getter for the content of this message
     *
     * @return the content of this message
     */
    public String getContent() {
        return this.content;
    }

    /**
     * For converting this class to a JSON format
     * @return a JSONObject that describes this entity
     */
    @SuppressWarnings("unchecked")
    public JSONObject convertToJSON() {
        JSONObject item = new JSONObject();

        item.put("sender", getSenderName());
        item.put("time", getMessageTime());
        item.put("content", getContent());

        return item;
    }
}
