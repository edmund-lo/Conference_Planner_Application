package model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

/**
 * Model class for Message object
 */
public class Message {
    private final StringProperty senderName;
    private final StringProperty content;
    private final ObjectProperty<LocalDateTime> messageTime;

    /**
     * Initialises a Message object with default attributes
     */
    public Message() {
        this(null, null, null);
    }

    /**
     * Initialises a Message object with given parameters as attributes
     * @param senderName String object representing message's sender's username
     * @param content String object representing message's content
     * @param messageTime LocalDateTime object representing the moment the message was sent
     */
    public Message(String senderName, String content, LocalDateTime messageTime) {
        this.senderName = new SimpleStringProperty(senderName);
        this.content = new SimpleStringProperty(content);
        this.messageTime = new SimpleObjectProperty<>(messageTime);
    }

    //region Getters and Setters
    public String getSenderName() {
        return senderName.get();
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public LocalDateTime getMessageTime() {
        return messageTime.get();
    }
    //endregion
}
