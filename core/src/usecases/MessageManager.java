package usecases;

import entities.Message;
import entities.MessageThread;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.UUID;


/**
 * Helper that manages all interaction with the Entities.Message classes and ensures no rules are broken.
 */
public class MessageManager implements Serializable {
    private HashMap<String, MessageThread> allMessageThreads;

    /**
     * Constructor for UseCases.MessageManager
     *
     */
    public MessageManager(){
        this.allMessageThreads = new HashMap<>();
    }

    /**
     * A helper to check if the messageThread is in allMessageThreads by its Id
     *
     * @param messageThreadId the Id of the messageThread that we are checking
     * @return True iff the messageThread is in allMessageThreads
     */
    public boolean messageThreadExists(String messageThreadId){
        return this.allMessageThreads.containsKey(messageThreadId);
    }

    /**
     * getter for one messageThread given it's Id
     *
     * @return The messageThread variable
     */
    public MessageThread getMessageThread(String messageThreadId){
        return this.allMessageThreads.get(messageThreadId);
    }

    public List<String> getReceivers(String messageThreadID){
        return this.allMessageThreads.get(messageThreadID).getReceivers();
    }

    /**
     * Delete a message
     *
     * @param messageThreadId the messageThreadID
     */
    public void deleteMessage(String messageThreadId){
        this.allMessageThreads.remove(messageThreadId);
    }

    /**
     * Creator for a new Entities.Message and the MessageThread it belongs to
     *
     * Precondition: the sender and receivers exist in users
     *
     * @param content The text of this message
     * @param sender The username of the sender for this message
     * @param receivers The list of usernames for the receivers of this message
     * @param subject The subject of MessageThread this message belongs to
     *
     * return the created messageThreadId
     */
    public String createMessage(String content, String sender, List<String> receivers, String subject) {
        String messageThreadId;
        do {
            messageThreadId = UUID.randomUUID().toString();
        } while(this.messageThreadExists(messageThreadId));
        LocalDateTime messageTime = LocalDateTime.now();
        Message newMessage = new Message(sender, messageTime, content);
        MessageThread newMessageThread = new MessageThread(sender, receivers, messageThreadId, subject);
        newMessageThread.getMessages().add(newMessage);
        this.allMessageThreads.put(newMessageThread.getMessageThreadId(), newMessageThread);
        //this.allContents.add(newMessage.toString());
        return messageThreadId;
    }

    /**
     * Reply to a MessageThread, add on the replied message to it and return the sender of this messageThread
     *
     * Precondition: the messageThread exist in allMessageThreads
     *
     * @param content The text of this message
     * @param username The username of the sender for this message
     * @param messageThreadId The list of usernames for the receivers of this message
     *
     * return the username for sender of this messageThreadId
     */

    public String reply(String username, String messageThreadId, String content){
        MessageThread newMessageThread = this.allMessageThreads.get(messageThreadId);
        Message newMessage = new Message(username, LocalDateTime.now(), content);
        newMessageThread.addMessages(newMessage);
        return newMessageThread.getSender();
    }

    /**
     * Checks if a message fulfils all the requirements to be sent.
     *
     * Precondition: the sender and receivers exist in users
     *
     * @param content The text of this message
     * @param sender The username of the sender for this message
     * @param receivers The username of the receiver for this message
     *
     * @return boolean value that signifies the result of the check.
     */
    public boolean messageCheck(String content, String sender, List<String> receivers) {
        if (content.equals("")){
            return false;
        }
        for(String receiver : receivers) {
            if (receiver.equals(sender)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets JSONObject for one message thread
     *
     * @param messageThreadId message thread id
     * @return a JSONObject of the message thread
     */
    @SuppressWarnings("unchecked")
    public JSONObject getMessageThreadJson(String messageThreadId){
        return allMessageThreads.get(messageThreadId).convertToJSON();
    }

    /**
     * Gets all message threads as JSONObject
     *
     * @return a JSONObject of all message threads
     */
    @SuppressWarnings("unchecked")
    public JSONArray getAllMessageThreadsJson(){
        JSONArray array = new JSONArray();

        for(String ID: allMessageThreads.keySet())
            array.add(allMessageThreads.get(ID).convertToJSON());

        return array;
    }
}