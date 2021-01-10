package usecases;

import entities.Room;

import org.json.simple.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A Use Case class that stores the rooms of the conference and updates the appropriate attributes of the rooms
 * to reflect their current state.
 *
 * @author Keegan McGonigal
 * @version 2.0
 *
 */

public class RoomManager implements Serializable {
    private HashMap<String, Room> allRooms;

    /**
     * Constructs a new empty RoomManager object containing no rooms.
     */
    public RoomManager() {
        this.allRooms = new HashMap<>();
    }

    /**
     * Creates a new Room object with an empty schedule and adds it into this RoomManager.
     * @param name  the name of the new room.
     * @return      a boolean value of true if the room was successfully created, false otherwise.
     */
    public boolean createRoom(String name, int capacity, boolean hasChairs, boolean hasTables, boolean hasProjector,
                              boolean hasSoundSystem) {
        if (!this.allRooms.containsKey(name) && !name.equals("")){
            this.allRooms.put(name, new Room(name, capacity, hasChairs, hasTables, hasProjector, hasSoundSystem));
            return true;
        }
        return false;
    }

    private Room getRoom(String roomName){
        return this.allRooms.get(roomName);
    }

    public int getRoomCapacity(String name){
        return this.getRoom(name).getCapacity();
    }

    /**
     * Adds an event to the schedule of a given Room in this RoomManager.
     * @param startTime      the start time of the event to add
     * @param endTime        the end time of the event to add
     * @param roomName       the name of the room to add the event to.
     * @return               a boolean value of true if the event was successfully added to the room, false otherwise.
     */
    public boolean addToRoomSchedule(LocalDateTime startTime, LocalDateTime endTime, String roomName,
                                     String eventName) {
        Room room = getRoom(roomName);
        if (room.canBook(startTime, endTime)){ // && eventCap < room.getCapacity()){
            room.addEvent(startTime, endTime, eventName);
            return true;
        }
        return false;
    }

    /**
     * Checks to see if an event can be booked at the specified time
     * @param roomName name of the room
     * @param startTime start time
     * @param endTime end time
     * @return True iff the event can be booked at the specified time
     */
    public boolean canBook(String roomName, LocalDateTime startTime, LocalDateTime endTime){
        return getRoom(roomName).canBook(startTime, endTime);
    }

    /**
     * Removes an event to the schedule of a given Room in this RoomManager.
     *
     * @param roomName  the name of the room to remove the event from.
     * @param eventID   the ID of the event to be removed
     * @return          a boolean value of true if the event was successfully removed from the room, false otherwise.
     */
    public boolean removeFromRoomSchedule(String roomName, String eventID){
        Room room = getRoom(roomName);
        if (room.hasEvent(eventID)) {
            room.removeEvent(eventID);
            return true;
        }
        return false;
    }

    /**
     * Gets a list of all the room names in the system.
     *
     * @return  a set containing all of the room names
     */
    public Set<String> getAllRooms(){
        return this.allRooms.keySet();
    }

    /**
     * Gets a list of all the room names in the system that meet specific requirements.
     *
     * @return  a set containing all of the room names
     */
    public List<String> getAllRoomsWith(List<Boolean> constraints, int eventCap){
        List<String> possibleRooms = new ArrayList<>();
        for (Map.Entry<String, Room> room : this.allRooms.entrySet()){
            boolean roomHasConstraints = true;
            Room thisRoom = room.getValue();
            List<Boolean> roomAmenities = getRoomAmenities(thisRoom);
            for (int i = 0; i < constraints.size(); i ++) {
                if (constraints.get(i)) {
                    if (!roomAmenities.get(i)) {
                        roomHasConstraints = false;
                    }
                }
            }
            if (roomHasConstraints && room.getValue().getCapacity() >= eventCap) {
                possibleRooms.add(room.getKey());
            }
        }
        return possibleRooms;
    }

    /**
     * helper method for getting the amenities of the room
     * @param thisRoom the room
     * @return a list of booleans where each corresponds to whether the event needs the amenity or not. The order is
     * chairs, tables, projector and sound system.
     */
    private List<Boolean> getRoomAmenities(Room thisRoom) {
        List<Boolean> roomAmenities = new ArrayList<>();
        roomAmenities.add(thisRoom.hasChairs());
        roomAmenities.add(thisRoom.hasTables());
        roomAmenities.add(thisRoom.hasProjector());
        roomAmenities.add(thisRoom.hasSoundSystem());
        return roomAmenities;
    }

    public List<String> getEventsInRoomAfter(String roomName, LocalDateTime time){
        return getRoom(roomName).eventsOnDay(time);
    }

    /**
     * Gets JSONObject for a room
     * @param roomID the room ID
     * @return A JSONObject that contains the JSON representation of this class
     */
    public JSONObject getRoomJson(String roomID) {
        return allRooms.get(roomID).convertToJSON();
    }

    /**
     * @return A JSONObject that contains the JSON representation of this class
     */
    @SuppressWarnings("unchecked")
    public JSONArray getAllRoomsJson(){
        JSONArray array = new JSONArray();

        for(String ID: allRooms.keySet())
            array.add(allRooms.get(ID).convertToJSON());

        return array;
    }
}
