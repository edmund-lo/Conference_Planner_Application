package entities;

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An abstract Entity class representing a user.
 *
 * @author Edmund Lo
 *
 */
public abstract class User implements Serializable {
    private final String username;
    private String firstName;
    private String lastName;
    private String userType;
    private HashMap<String, LocalDateTime[]> schedule;
    private List<String> sentRequest;
    private List<String> friendRequest;
    private List<String> friendsList;
    private HashMap<String, Boolean> primaryInbox;
    private HashMap<String, Boolean> archivedInbox;
    private HashMap<String, Boolean> trashInbox;
    private boolean vip;

    /**
     * Constructor for User object. Initializes an empty hashmap for a user's schedule and
     * an empty arraylist for a user's sent message IDs and an empty arraylist for a user's received message IDs.
     *
     * @param username the user's username
     * @param firstName the user's firstName
     * @param lastName the user's lastName
     */
    public User(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.schedule = new HashMap<>();
        this.sentRequest = new ArrayList<>();
        this.friendRequest = new ArrayList<>();
        this.friendsList = new ArrayList<>();
        this.primaryInbox = new HashMap<>();
        this.archivedInbox = new HashMap<>();
        this.trashInbox = new HashMap<>();
        this.vip = true;
        this.userType = this.getClass().getSimpleName();
    }

    /**
     * Getter for user's username.
     *
     * @return user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter for user's first name
     * @return user's firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * getter for user's last name
     * @return user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for user's first name.
     */
    public void setFirstName(String fn) {
        this.firstName = fn;
    }

    /**
     * Setter for user's last name.
     */
    public void setLastName(String ln) {
        this.lastName = ln;
    }

    /**
     * Getter for a user's schedule of events that they are attending.
     *
     * @return A hashmap of the containing event IDs as keys and start and end times as values
     */
    public HashMap<String, LocalDateTime[]> getSchedule() {
        return schedule;
    }

    /**
     * Sets the vip status of the attendee
     *
     * @param vip the vip status to be set
     */
    public void setVipStatus(boolean vip) {
        this.vip = vip;
    }

    /**
     * Checks whether attendee is a vip
     *
     * @return true iff the attendee is a vip
     */
    public boolean isVip() {
        return vip;
    }

    /**
     * Getter for a user's primary inbox.
     *
     * @return An arraylist containing messageThread IDs of all primary messageThreads
     */
    public HashMap<String, Boolean> getPrimaryInbox() {
        return primaryInbox;
    }

    /**
     * Getter for a user's archived inbox.
     *
     * @return An arraylist containing messageThread IDs of all archived messageThreads
     */
    public HashMap<String, Boolean> getArchivedInbox() {
        return archivedInbox;
    }


    /**
     * Getter for a user's trash inbox.
     *
     * @return An arraylist containing messageThread IDs of all trash messageThreads
     */
    public HashMap<String, Boolean> getTrashInbox() {
        return trashInbox;
    }

    /**
     * Getter for the current status of a messageThread given its inbox and id.
     *
     * @param inbox the inbox where this messageThread is in.
     * @param messageThreadId the id of this messageThread.
     *
     * @return the boolean value of the message has been read or not, true iff read, false otherwise.
     */

    public boolean getRead(HashMap<String, Boolean> inbox, String messageThreadId){
        return inbox.get(messageThreadId);
    }

    /**
     * Getter for the inbox of where this messageThread is in.
     *
     * Precondition: this messageThread is in one of the user's inboxes.
     *
     * @param messageThreadId the id of this messageThread.
     *
     * @return the boolean value of the message has been read or not, true iff read, false otherwise.
     */

    public HashMap<String, Boolean> getInboxOfMessage(String messageThreadId){
        if(this.primaryInbox.containsKey(messageThreadId)){
            return this.primaryInbox;
        }else if(this.archivedInbox.containsKey(messageThreadId)) {
            return this.archivedInbox;
        }else{
            return this.trashInbox;
        }
    }

    /**
     * Setter for the current status of a messageThread given its id.
     * Change to true if was false, vis versa
     *
     * @param messageThreadId the id of this messageThread.
     *
     */

    public void changeRead(String messageThreadId){
        HashMap<String, Boolean> inbox = this.getInboxOfMessage(messageThreadId);
        if(getRead(inbox, messageThreadId)) {
            inbox.replace(messageThreadId, false);
        }else{inbox.replace(messageThreadId, true);
        }
    }

    /**
     * Setter for the current status of a messageThread given its id.
     * Change to false for all users.
     *
     * @param messageThreadId the id of this messageThread.
     *
     */

    public void unread(String messageThreadId){
        HashMap<String, Boolean> inbox = this.getInboxOfMessage(messageThreadId);
        inbox.replace(messageThreadId, false);
    }

    /**
     * removes messageThread from user's inbox
     *
     * @param messageThreadId id of the message thread
     */
    public void deleteMessageFromInboxes(String messageThreadId){
        if(this.getPrimaryInbox().containsKey(messageThreadId)){
            this.primaryInbox.remove(messageThreadId);
        }else if(this.getArchivedInbox().containsKey(messageThreadId)){
            this.archivedInbox.remove(messageThreadId);
        }else if(this.getTrashInbox().containsKey(messageThreadId)){
            this.trashInbox.remove(messageThreadId);
        }
    }

    /**
     * Signs up a user for an event.
     *
     * @param eventID the event ID that they want to sign up for
     * @param startTime the event start time
     * @param endTime the event end time
     */
    public void signUp(String eventID, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime[] time = {startTime, endTime};
        schedule.put(eventID, time);
    }

    /**
     * Removes user from an event.
     *
     * @param eventID the event ID they want to cancel their attendance at
     */
    public void cancel(String eventID) {
        schedule.remove(eventID);
    }

    /**
     * Checks if a user is available at the times of the event they want to sign up for.
     *
     * @param eventID the event ID that they want to sign up for
     * @param startTime the event start time
     * @param endTime the event end time
     * @return true iff they can sign up for the event
     */
    public boolean canSignUp(String eventID, LocalDateTime startTime, LocalDateTime endTime) {
        if (schedule.containsKey(eventID)) {
            return false;
        } else {
            for (LocalDateTime[] time : schedule.values()) {
                if ((time[0].isBefore(startTime) && time[1].isAfter(startTime))) {
                    return false;
                } else if (time[0].isBefore(endTime) && time[1].isAfter(endTime)) {
                    return false;
                } else if (time[0].isEqual(startTime)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Adds messageThreadId of the sent message to user's primaryInbox.
     *
     * Precondition: the messageThreadId exist
     *
     * @param messageThreadId the messageThread Id of the sent message
     */
    public void sendMessage(String messageThreadId) {
        this.primaryInbox.put(messageThreadId, true);
    }

    /**
     * Adds messageThreadId of the received message to user's primaryInbox.
     *
     * Precondition: the messageThreadId exist
     *
     * @param messageThreadId the messageThreadId of the received message
     */
    public void receiveMessage(String messageThreadId) {
        this.primaryInbox.put(messageThreadId, false);
    }

    /**
     * Moves messageThreadId of the messageThread to user's archivedInbox.
     *
     * Precondition: the messageThreadId exist in the primary inbox
     *
     * @param messageThreadId the messageThreadId to be archived
     */
    public void archiveToInbox(String messageThreadId) {
        this.archivedInbox.put(messageThreadId, this.primaryInbox.remove(messageThreadId));
    }

    /**
     * Moves messageThreadId of the messageThread to user's trashInbox.
     *
     * Precondition: the messageThreadId exist in the primary inbox
     *
     * @param messageThreadId the messageThreadId of the messageThread they want to move to trash bin
     */
    public void moveToTrash(String messageThreadId) {
        this.trashInbox.put(messageThreadId, this.primaryInbox.remove(messageThreadId));
    }

    /**
     * Moves messageThreadId of the messageThread back to user's primaryInbox from the archivedInbox.
     *
     * Precondition: the messageThreadId exist in the archivedInbox
     *
     * @param messageThreadId the messageThreadId to be move back
     */
    public void archivedBackToPrimary(String messageThreadId) {
        this.primaryInbox.put(messageThreadId, this.archivedInbox.remove(messageThreadId));
    }

    /**
     * Moves messageThreadId of the messageThread back to user's primaryInbox from the trashInbox.
     *
     * Precondition: the messageThreadId exist in the trashInbox
     *
     * @param messageThreadId the messageThreadId of the messageThread they want to move back from the trash bin
     */
    public void trashBackToPrimary(String messageThreadId) {
        this.primaryInbox.put(messageThreadId, this.trashInbox.remove(messageThreadId));
    }

    /**
     * toString with the User's username and role
     *
     * @return String of user's username and role
     */
    public String toString() {
        return "Username: " + this.username + "\n" +  "Role: " + this.getClass().getSimpleName() + "\n";
    }

    /**
     * adds person to user's friend list
     *
     * @param username username of the person you wish to add
     */
    public void addFriend(String username){
        friendsList.add(username);
    }

    /**
     * removes person from user's friend list
     *
     * @param username username of the person you wish to remove
     */
    public void removeFriend(String username){
        friendsList.remove(username);
    }

    /**
     * getter for all friend requests
     *
     * @return a list of strings of all the friends requests user has
     */
    public List<String> getFriendRequest(){
        return friendRequest;
    }

    /**
     * getter for the friends list
     *
     * @return user's friends list
     */
    public List<String> getFriendsList() {
        return friendsList;
    }

    /**
     * getter for sent friends requests
     *
     * @return a list of strings of all the sent friend requests from user
     */
    public List<String> getSentRequest() {
        return sentRequest;
    }

    /**
     * @return A JSONObject that contains the JSON representation of this class
     */
    @SuppressWarnings("unchecked")
    public JSONObject convertToJSON() {
        JSONObject item = new JSONObject();

        item.put("username", getUsername());
        item.put("firstName", getFirstName());
        item.put("lastName", getLastName());
        item.put("vip", isVip());
        item.put("type", userType);
        return item;
    }

}
