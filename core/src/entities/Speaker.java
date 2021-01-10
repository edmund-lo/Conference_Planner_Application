package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * An Entity class for a Speaker that inherits from User.
 *
 * @author Edmund Lo
 *
 */
public class Speaker extends User implements Serializable {
    private HashMap<String, LocalDateTime[]> speakerSchedule;

    /**
     * Constructor for a Speaker that inherits from User. Initializes an empty hashmap for a speaker's schedule.
     *
     * @param username the username of the User
     * @param firstName the user's firstName
     * @param lastName the user's lastName
     */
    public Speaker(String username, String firstName, String lastName) {
        super(username, firstName, lastName);
        this.speakerSchedule = new HashMap<>();
    }

    /**
     * Getter for a speaker's schedule of events they are speaking at.
     *
     * @return The speaker's schedule
     */
    public HashMap<String, LocalDateTime[]> getSpeakerSchedule() {
        return speakerSchedule;
    }

    /**
     * Assigns a speaker to speak at the event.
     *
     * @param eventID the event ID the speaker will be assigned at
     * @param startTime the event start time
     * @param endTime the event end time
     */
    public void addSpeakerEvent(String eventID, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime[] time = {startTime, endTime};
        speakerSchedule.put(eventID, time);
    }

    /**
     * Removes the speaker from speaking a the event.
     *
     * @param eventID the event ID the speaker will be removed from
     */
    public void cancelSpeakerEvent(String eventID) {
        speakerSchedule.remove(eventID);
    }

    /**
     * Checks if the speaker is available at the times of the event they are being assigned to speak at.
     *
     * @param eventID the event ID of the event they will speak at
     * @param startTime the event start time
     * @param endTime the event end time
     * @return true iff the speaker is available to speak at the event
     */
    public boolean canAddSpeakerEvent(String eventID, LocalDateTime startTime, LocalDateTime endTime) {
        if (speakerSchedule.containsKey(eventID)) {
            return false;
        } else {
            for (LocalDateTime[] time : speakerSchedule.values()) {
                if ((time[0].isBefore(startTime) && time[1].isAfter(startTime)) ||
                        (time[0].isBefore(endTime) && time[1].isAfter(endTime))) {
                    return false;
                } else if (time[0].isEqual(startTime)) {
                    return false;
                }
            }
            return true;
        }
    }
}
