package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * Model class for MessageThread object
 */
public class MessageThread {
    private final StringProperty messageThreadId;
    private final StringProperty senderName;
    private final ListProperty<String> recipientNames;
    private final StringProperty subject;
    private final BooleanProperty unread;
    private final ListProperty<Message> messageHistory;

    /**
     * Initialises a MessageThread object with default attributes
     */
    public MessageThread() {
        this(null, null, null, null, false, null);
    }

    /**
     * Initialises a MessageThread object with given parameters as attributes
     * @param messageThreadId String object representing message thread's unique ID
     * @param senderName String object representing message thread's original sender's username
     * @param recipientNames List of String objects representing message thread's recipients' usernames
     * @param subject String object representing message thread's subject topic
     * @param unread boolean representing whether message thread has been read or not
     * @param messageHistory List of Message objects representing the message thread's conversation history
     */
    public MessageThread(String messageThreadId, String senderName, List<String> recipientNames, String subject,
                         boolean unread, List<Message> messageHistory) {
        this.messageThreadId = new SimpleStringProperty(messageThreadId);
        this.senderName = new SimpleStringProperty(senderName);
        this.recipientNames = new SimpleListProperty<>(FXCollections.observableArrayList(recipientNames));
        this.subject = new SimpleStringProperty(subject);
        this.unread = new SimpleBooleanProperty(unread);
        this.messageHistory = new SimpleListProperty<>(FXCollections.observableArrayList(messageHistory));
    }

    //region Getters and Setters
    public String getMessageThreadId() {
        return messageThreadId.get();
    }

    public String getSenderName() {
        return senderName.get();
    }

    public ObservableList<String> getRecipientNames() {
        return recipientNames.get();
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public boolean isUnread() {
        return unread.get();
    }

    public BooleanProperty unreadProperty() { return unread; }

    public ObservableList<Message> getMessageHistory() {
        return messageHistory.get();
    }
    //endregion
}
