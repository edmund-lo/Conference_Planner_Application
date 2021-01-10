package entities;

import org.json.simple.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of an event with all details pertaining to it stored inside.
 * @author Sam De Abreu
 */
public class Event implements Serializable {
    private final String eventName;
    private final String eventID;
    private List<String> speakerNames;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<String> attendingUsers;
    private String roomName;
    private boolean vipEvent;
    private int capacity;
    private boolean needsChairs;
    private boolean needsTables;
    private boolean needsProjector;
    private boolean needsSoundSystem;
    private boolean cancelled;

    /**
     * constructor for the event entity
     *
     * @param eventID ID of the event
     * @param eventName name of the event
     * @param startTime start time of the event
     * @param endTime end time of the event
     * @param roomName name of the room for the event
     * @param needsChairs whether this event needs chairs
     * @param needsTables whether this event needs tables
     * @param needsProjector whether this event needs a projector
     * @param needsSoundSystem whether this event needs a sound system
     * @param capacity capacity of this event
     * @param vipEvent whether this event is a VIP event
     */
    public Event(String eventID, String eventName, LocalDateTime startTime, LocalDateTime endTime, String roomName,
                 boolean needsChairs, boolean needsTables, boolean needsProjector, boolean needsSoundSystem,
                 int capacity, boolean vipEvent){
        this.eventID = eventID;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomName = roomName;
        this.capacity = capacity;
        this.speakerNames = new ArrayList<>();
        this.attendingUsers = new ArrayList<>();
        this.needsChairs = needsChairs;
        this.needsTables = needsTables;
        this.needsProjector = needsProjector;
        this.needsSoundSystem = needsSoundSystem;
        this.vipEvent = vipEvent;
        this.cancelled = false;
    }

    /**
     * Getter for ID of the speaker
     *
     * @return The ID of the speaker speaking at this event
     */
    public List<String> getSpeakerNames() {
        return speakerNames;
    }

    /**
     * getter for ID of the event
     *
     * @return The ID of this event
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * getter for the list of attending users
     *
     * @return An arraylist with IDs of all users attending this event
     */
    public List<String> getAttendingUsers() {
        return attendingUsers;
    }

    /**
     * getter for the name of this event
     *
     * @return the name of this event
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * getter for the start time of this event
     *
     * @return the start time of this event
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * check for if this event is a vip event
     * @return True iff this event is a vip event
     */
    public boolean isVipEvent(){
        return this.vipEvent;
    }

    /**
     * getter for the end time of this event
     *
     * @return the end time of this event
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * set if the event is cancelled or not
     * @param isCancelled whether the event is cancelled or not
     */
    public void setCancelled(boolean isCancelled){
        this.cancelled = isCancelled;
    }

    /**
     * getter for whether the event is cancelled or not
     * @return True iff this event has been cancelled
     */
    public boolean isCancelled(){
        return this.cancelled;
    }

    public void changeRoomName(String roomName){
        this.roomName = roomName;
    }

    /**
     * getter for the room name of this event
     *
     * @return the room name of this event
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * the toString method for Entities.Event
     *
     * @return a String representation of Entities.Event that contains the event name, time and number of attending users
     */
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        DateTimeFormatter hourMin = DateTimeFormatter.ofPattern("HH:mm");
        StringBuilder speakers = new StringBuilder();
        if(speakerNames.size() == 0) {
            speakers.append("No speakers");
        }else{
            String prefix = "";
            for(String name: this.speakerNames){
                speakers.append(prefix);
                prefix = ",";
                speakers.append(name);
            }
        }
        return "Event Name: "+this.eventName+"\n" +
                "Speaker(s): "+speakers.toString()+"\n" +
                "Time: "+dtf.format(this.startTime)+" - "+hourMin.format(this.endTime)+"\n" +
                "Number of Attending Users: "+this.attendingUsers.size() + "\n" +
                "Room Name: " + this.roomName;
    }

    /**
     * Adds the user with ID userID to the attending users list for this event
     *
     * @param userID the ID of the user that wants to be added to this event
     */
    public void addUserToEvent(String userID){
        attendingUsers.add(userID);
    }

    /**
     * Removes the user with ID userID from the attending users list for this event. The user must be in the attending
     * users list.
     *
     * @param userID the ID of the user that wants to be removed from this event
     * @return True iff the user was removed successfully and false if not
     */
    public boolean removeUserFromEvent(String userID){
        if(attendingUsers.contains(userID)){
            attendingUsers.remove(userID);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * removes all users and speakers from event
     */
    public void removeAllUsersAndSpeakersFromEvent(){
        this.attendingUsers.clear();
        this.speakerNames.clear();
    }

    /**
     * Changes the time of when the event occurs. That is, changing the start and end time of the event.
     *
     * @param startTime the new start time of the event
     * @param endTime the new end time of the event
     */
    public void changeTime(LocalDateTime startTime, LocalDateTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * setter for the speakerID
     *
     * @param speakerName the ID of the speaker that wants to be added to Entities.Event
     */
    public void addSpeaker(String speakerName){
        this.speakerNames.add(speakerName);
    }

    /**
     * removes speaker with name speakerName from this event. Note that this method assumes you have already checked if
     * the speaker exists in this event.
     * @param speakerName the name of the speaker
     */
    public void removeSpeaker(String speakerName){
        this.speakerNames.remove(speakerName);
    }

    /**
     * changes the capacity of this event to capacity
     * @param capacity the new capacity of the event
     */
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    /**
     * getter for the capacity of this event
     * @return the capacity of this event
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * changes the status of whether this event is a vip even tor not
     * @param isVip True iff this event is a VIP event
     */
    public void changeVipStatus(boolean isVip){
        this.vipEvent = isVip;
    }

    /**
     * @return A JSONObject that contains the JSON representation of this class
     */
    @SuppressWarnings("unchecked")
    public JSONObject convertToJSON() {
        JSONObject item = new JSONObject();

        item.put("eventName", eventName);
        item.put("id", eventID);
        item.put("speakers", speakerNames);
        item.put("start", startTime);
        item.put("end", endTime);
        item.put("users", attendingUsers);
        item.put("roomName", roomName);
        item.put("capacity", capacity);
        item.put("remainingSpots", capacity - attendingUsers.size());
        item.put("Chairs", needsChairs);
        item.put("Tables", needsTables);
        item.put("Projector", needsProjector);
        item.put("SoundSystem", needsSoundSystem);
        item.put("vip", vipEvent);
        item.put("cancelled", cancelled);

        return item;
    }
}
