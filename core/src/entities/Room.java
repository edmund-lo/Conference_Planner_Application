package entities;

import org.json.simple.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * An Entity class representing a conference room at the conference.
 *
 * @author Keegan McGonigal
 * @version 2.0
 *
 */

public class Room implements Serializable {
    private final String name;
    private final int capacity;
    private final boolean hasChairs;
    private final boolean hasTables;
    private final boolean hasProjector;
    private final boolean hasSoundSystem;
    private TreeMap<LocalDateTime[], String> schedule;

    /**
     * The constructor for Room
     * @param name name of the room
     * @param capacity capacity of the room
     * @param hasChairs whether the room has chairs
     * @param hasTables whether the room has tables
     * @param hasProjector whether the room has a projector
     * @param hasSoundSystem whether the room has a sound system
     */
    public Room(String name, int capacity, boolean hasChairs, boolean hasTables, boolean hasProjector,
                boolean hasSoundSystem){
        this.name = name;
        this.capacity = capacity;
        this.hasChairs = hasChairs;
        this.hasTables = hasTables;
        this.hasProjector = hasProjector;
        this.hasSoundSystem = hasSoundSystem;
        this.schedule = new TreeMap<>(new SerializableComparator<>() {
            @Override
            public int compare(LocalDateTime[] o1, LocalDateTime[] o2) {
                if (o1[0].isAfter(o2[0])){
                    return 1;
                } else if (o1[0].isBefore(o2[0])){
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    /**
     * Checks to see if this Room can be booked for an event. A room can be booked if there is a free
     * time slot and the event ends before or at 5pm.
     *
     * @return true if the room can be booked, false otherwise
     */
    public boolean canBook(LocalDateTime startTime, LocalDateTime endTime){
        for (Map.Entry<LocalDateTime[], String> times : this.schedule.entrySet()){
            LocalDateTime bookedStartTime = times.getKey()[0];
            LocalDateTime bookedEndTime = times.getKey()[1];
            if ((bookedStartTime.isBefore(startTime) && bookedEndTime.isAfter(startTime)) ||
                    (bookedStartTime.isBefore(endTime) && bookedEndTime.isAfter(endTime))) { // check for conflicts
                return false;
            } else if (startTime.isEqual(bookedStartTime)){ // starts at same time
                return false;
            }
        }
        return true;
    }

    /**
     * Adds an event to this Room at the given time.
     *
     * @param startTime the time the event starts.
     * @param endTime   the time the event ends.
     * @param eventID the name of the event to be added.
     */
    public void addEvent(LocalDateTime startTime, LocalDateTime endTime, String eventID){
        LocalDateTime[] times = new LocalDateTime[]{startTime, endTime};
        this.schedule.put(times, eventID);
    }

    /**
     * Checks to see if this Room's schedule has a specific event.
     *
     * @param eventID       the ID of the event to check for.
     * @return true if this event is scheduled in this room, false otherwise
     */
    public boolean hasEvent(String eventID){
        for (Map.Entry<LocalDateTime[], String> event : this.schedule.entrySet()){
            if (event.getValue().equals(eventID)){
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an event at a certain time from the schedule of this Room.
     *
     * @param eventID the ID of the event
     */
    public void removeEvent(String eventID) {
        for (Map.Entry<LocalDateTime[], String> event : this.schedule.entrySet()) {
            if (event.getValue().equals(eventID)) {
                this.schedule.remove(event.getKey());
                break;
            }
        }
    }

    /**
     * Gets the total number of people the room can hold.
     *
     * @return the capacity of this Room
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Finds out whether the room has chairs.
     *
     * @return true if the room has chairs, false otherwise
     */
    public boolean hasChairs(){
        return this.hasChairs;
    }

    /**
     * Finds out whether the room has tables.
     *
     * @return true if the room has chairs, false otherwise
     */
    public boolean hasTables(){
        return this.hasTables;
    }

    /**
     * Finds out whether the room has a projector.
     *
     * @return true if the room has a projector, false otherwise
     */
    public boolean hasProjector(){
        return this.hasProjector;
    }

    /**
     * Finds out whether the room has a sound system.
     *
     * @return true if the room has a sound system, false otherwise
     */
    public boolean hasSoundSystem(){
        return this.hasSoundSystem;
    }

    /**
     * Gives the String representation of this Room.
     *
     * @return the string representation of this Room
     */
    @Override
    public String toString() {
        return this.name + " Room";
    }

    /**
     * Gives the String representation of this Room's schedule.
     *
     * @return the string representation of this Room's schedule
     */
    public String roomScheduleToString() {
        StringBuilder ret = new StringBuilder(this.name + " Room's Schedule:" + "\n");
        DateTimeFormatter dayTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        DateTimeFormatter hourMin = DateTimeFormatter.ofPattern("HH:mm");
        for (Map.Entry<LocalDateTime[], String> time : this.schedule.entrySet()) {
            String eventStartTime = dayTime.format(time.getKey()[0]);
            String eventName = time.getValue();
            ret.append(eventStartTime);
            ret.append("-");
            ret.append(hourMin.format(time.getKey()[1]));
            ret.append(" -- ");
            ret.append(eventName);
            ret.append("\n");
        }
        return ret.toString();
    }

    /**
     * getter for the events on a specific time
     * @param time which day
     * @return a list of strings of the event IDs occurring on time
     */
    public List<String> eventsOnDay(LocalDateTime time) {
        List<String> events = new ArrayList<>();
        for (Map.Entry<LocalDateTime[], String> times : this.schedule.entrySet()) {
            LocalDateTime startTime = times.getKey()[0];
            LocalDateTime endTime = times.getKey()[1];
            if ((startTime.isEqual(time) | time.isAfter(startTime))){
                events.add(times.getValue());
            }
        }
        return events;
    }

    /**
     * @return A JSONObject that contains the JSON representation of this class
     */
    @SuppressWarnings("unchecked")
    public JSONObject convertToJSON() {
        JSONObject item = new JSONObject();

        item.put("name", name);
        item.put("capacity", capacity);
        item.put("Chairs", hasChairs);
        item.put("Tables", hasTables);
        item.put("Projector", hasProjector);
        item.put("SoundSystem", hasSoundSystem);
        item.put("schedule", schedule);

        return item;
    }
}