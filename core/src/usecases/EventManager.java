package usecases;

import entities.Event;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 * Helper that manages all interaction with the Entities. Event classes and ensures no rules are broken.
 * @author Sam De Abreu
 */
public class EventManager implements Serializable {
    private HashMap<String, Event> allEvents;

    /**
     * Constructor for UseCases.EventManager. Just initializes an empty hashmap
     *
     */
    public EventManager(){
        this.allEvents = new HashMap<>();
    }

    /**
     * A helper to check if the event is in allEvents
     *
     * @param eventID the ID of the event we are checking
     * @return True iff the event is in allEvents
     */
    public boolean eventExists(String eventID){
        return allEvents.containsKey(eventID);
    }

    /**
     * For creating a new event
     * @param eventName name of the event
     * @param startTime start time of the event
     * @param endTime end time of the event
     * @param roomName name of the room
     * @param chairs whether the event needs chairs
     * @param tables whether the event needs tables
     * @param projector whether the event needs a projector
     * @param speakers the speakers speaking at the event
     * @param capacity the capacity of the event
     * @param vipEvent whether the vent is a VIP event or not
     * @return the event ID
     */
    public String createNewEvent(String eventName, LocalDateTime startTime, LocalDateTime endTime, String roomName,
                               boolean chairs, boolean tables, boolean projector, boolean speakers, int capacity,
                               boolean vipEvent) {
        Event newEvent = new Event(UUID.randomUUID().toString(), eventName, startTime, endTime, roomName, chairs,
                tables, projector, speakers, capacity, vipEvent);
        allEvents.put(newEvent.getEventID(), newEvent);
        return newEvent.getEventID();
    }

    /**
     * remove event with ID eventID from allEvents
     *
     * @param eventID the ID of the event that wishes to be removed
     * @return True iff the event was removed successfully
     */
    public boolean removeEvent(String eventID){
        if(eventExists(eventID)){
            allEvents.remove(eventID);
            return true;
        }else{
            return false;
        }
    }

    /**
     * adds user with ID userID to event with ID eventID
     *
     * @param eventID the ID of the event
     * @param userID the ID of the user
     */
    public void addUserToEvent(String eventID, String userID){
        Event currentEvent = allEvents.get(eventID);
        currentEvent.addUserToEvent(userID);
    }

    /**
     * Checks whether event with ID eventID is "empty"
     * @param eventID the ID of the event
     * @return True iff the event has no attending users, no speakers and no room
     */
    public boolean isEventEmpty(String eventID){
        Event event = allEvents.get(eventID);
        return event.getAttendingUsers().size() == 0 &&
                event.getSpeakerNames().size() == 0 && event.getRoomName().equals("Unassigned");
    }

    /**
     * changes the status of event with ID eventID of whether it is a VIP event or not
     * @param eventID the ID of the event
     * @param isVip True iff this event is a VIP event now
     */
    public void changeVipStatus(String eventID, boolean isVip){
        allEvents.get(eventID).changeVipStatus(isVip);
    }

    /**
     * checks to see if user with ID userID can be added to event with ID eventID
     *
     * @param eventID ID of the event
     * @param userID ID of the user
     * @return True iff there is enough space for the user, if the event exists and if the user is already in the event
     */
    public boolean canAddUserToEvent(String eventID, String userID){
        if(eventExists(eventID)){
            Event currentEvent = allEvents.get(eventID);
            return currentEvent.getAttendingUsers().size() < currentEvent.getCapacity()
                    && !currentEvent.getAttendingUsers().contains(userID);
        }
        return false;
    }

    /**
     * removes user with ID userID from event with ID eventID. The event must exist
     *
     * @param eventID the ID of the event
     * @param userID the ID of the user
     * @return True iff the user with ID userID was removed successfully from event with ID eventID
     */
    public boolean removeUserFromEvent(String eventID, String userID){
        Event currentEvent = allEvents.get(eventID);
        if(eventExists(eventID)){
            return currentEvent.removeUserFromEvent(userID);
        }else{
            return false;
        }
    }

    /**
     * cancels event with ID eventID. This removes all users and speakers from attending in the event and changes
     * the time and room to null and sets capacity to 0. Note that this method is not enough to entirely cancel
     * the event in the system. To do so, call cancelEvent in OrganizerController.
     * @param eventID the ID of the event you wish to cancel.
     */
    public void cancelEvent(String eventID){
        Event event = allEvents.get(eventID);
        event.removeAllUsersAndSpeakersFromEvent();
        event.changeTime(null, null);
        event.setCancelled(true);
        event.setCapacity(0);
        changeEventRoom(eventID, "Unassigned");
    }

    public boolean isEventCancelled(String eventID){
        return allEvents.get(eventID).isCancelled();
    }

    /**
     * changes the status of the cancellation of the event. Note that this should be
     * called in Organizer controller's rescheduleEvent
     * @param eventID ID of the event
     */
    public void rescheduleEvent(String eventID){
        allEvents.get(eventID).setCancelled(false);
    }

    /**
     * checks to see if speaker with ID speakerID can be added to event with ID eventID
     *
     * @param eventID the ID of the event
     * @return True iff the speaker was successfully added to the event with ID eventID
     */
    public boolean canAddSpeakerToEvent(String eventID, String speakerID){
        return eventExists(eventID) && !allEvents.get(eventID).getSpeakerNames().contains(speakerID);
    }

    /**
     * adds speaker with ID speakerID to event with ID eventID
     *
     * @param speakerID ID of the speaker
     * @param eventID ID of the event
     */
    public void addSpeakerToEvent(String speakerID, String eventID){
        allEvents.get(eventID).addSpeaker(speakerID);
    }

    /**
     * removes speaker with ID speakerID from speaking at event with ID eventID
     * @param speakerID the ID of the speaker you wish to remove
     * @param eventID the ID of the event you wish to remove the speaker from
     * @return True iff the speaker was successfully removed
     */
    public boolean removeSpeakerFromEvent(String speakerID, String eventID){
        if(eventExists(eventID) && allEvents.get(eventID).getSpeakerNames().contains(speakerID)){
            allEvents.get(eventID).removeSpeaker(speakerID);
            return true;
        }
        return false;
    }

    /**
     * Getter for a list of IDs of all events
     *
     * @return An arraylist with all of the IDs of the events in allEvents
     */
    public List<String> getAllEventIds() {
        return new ArrayList<>(allEvents.keySet());
    }

    /**
     * To return the event description of event with ID eventID
     *
     * @param eventID the ID of the event to which we want the description of
     * @return the description of the event with ID eventID
     */
    public String getEventDescription(String eventID){
        return allEvents.get(eventID).toString();
    }

    /**
     * getter for the room name at which the event with ID eventID is held in
     * @param eventID the ID of the event
     * @return the room name the event is in
     */
    public String getEventRoom(String eventID){
        return allEvents.get(eventID).getRoomName();
    }



    /**
     * changes the time of event with ID eventID to start at startTime and to end at endTime
     * @param eventID the ID of the event
     * @param startTime the new start time
     * @param endTime the new end time
     */
    public void changeEventTime(String eventID, LocalDateTime startTime, LocalDateTime endTime){
        allEvents.get(eventID).changeTime(startTime, endTime);
    }

    /**
     * changes the room of event with ID eventID to roomName
     * @param eventID the ID of the event
     * @param roomName the new room name
     */
    public void changeEventRoom(String eventID, String roomName){
        allEvents.get(eventID).changeRoomName(roomName);
    }
    /**
     * getter for the start time for event with ID eventID
     *
     * @param eventID ID of the event
     * @return the start time of event with ID eventID
     */
    public LocalDateTime getEventStartTime(String eventID){
        return allEvents.get(eventID).getStartTime();
    }

    /**
     * getter for the end time for event with ID eventID
     *
     * @param eventID ID of the event
     * @return the end time of event with ID eventID
     */
    public LocalDateTime getEventEndTime(String eventID){
        return allEvents.get(eventID).getEndTime();
    }

    /**
     * getter for event Id by knowing its name
     * @param eventName name of the event
     * @return ID of the event in string format
     */
    public String getEventIDByName(String eventName){
        for(String eventID: allEvents.keySet()){
            if(allEvents.get(eventID).getEventName().equals(eventName)){
                return eventID;
            }
        }
        return null;
    }

    /**
     * getter for the event name of event with ID eventID
     *
     * @param eventID the ID of the eventID
     * @return the name of the event wth ID eventID
     */
    public String getEventName(String eventID){
        return allEvents.get(eventID).getEventName();
    }

    /**
     * getter for the list of attending users for event with ID eventID
     *
     * @param eventID ID of the event
     * @return the list of attending users for event with ID eventID
     */
    public List<String> getAttendingUsers(String eventID){
        return allEvents.get(eventID).getAttendingUsers();
    }

    /**
     * getter for the list of names of the speakers speaking at event with ID eventID
     * @param eventID the ID of the event
     * @return an arraylist of the names of the speakers
     */
    public List<String> getSpeakers(String eventID){
        return allEvents.get(eventID).getSpeakerNames();
    }

    /**
     * changes the event capacity to capacity of event with ID eventID
     * @param eventID the ID of the event
     * @param capacity the new capacity of the event
     */
    public void changeEventCap(String eventID, int capacity){
        this.allEvents.get(eventID).setCapacity(capacity);
    }

    /**
     * getter for getting the JSON representation of an event
     * @param ID id of the event
     * @return the JSONObject representation of the event
     */
    public JSONObject getEventJson(String ID){
        return allEvents.get(ID).convertToJSON();
    }

    /**
     * @return A JSONArray that contains the JSON representation of this class
     */
    @SuppressWarnings("unchecked")
    public JSONArray getAllEventsJson(){
        JSONArray array = new JSONArray();

        for(String eventID: allEvents.keySet())
            array.add(allEvents.get(eventID).convertToJSON());

        return array;
    }
}