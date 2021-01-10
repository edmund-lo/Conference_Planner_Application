package model;

import javafx.beans.property.*;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Model class for ScheduleEntry object
 */
public class ScheduleEntry {
    private final ObjectProperty<LocalDateTime> start;
    private final ObjectProperty<LocalDateTime> end;
    private final StringProperty eventId;
    private final StringProperty eventName;
    private final StringProperty roomName;
    private final StringProperty amenities;
    private final StringProperty attendees;
    private final StringProperty speakers;
    private final ObjectProperty<Duration> duration;
    private final IntegerProperty remainingSpots;
    private final IntegerProperty capacity;
    private final BooleanProperty vip;
    private final BooleanProperty cancelled;

    /**
     * Initialises a ScheduleEntry object with default attributes
     */
    public ScheduleEntry() {
        this(null, null, null,null, null, null,
                null, null, null, 0, 0, false, false);
    }

    /**
     * Initialises a ScheduleEntry object with given parameters as attributes
     * @param start LocalDateTime object representing event's start date and time
     * @param end LocalDateTime object representing event's end date and time
     * @param eventId String object representing event's unique ID
     * @param eventName String object representing event's name
     * @param roomName String object representing event's room name
     * @param amenities String object representing event's required amenities
     * @param attendees String object representing event's attendees
     * @param speakers String object representing event's speakers
     * @param duration Duration object representing event's duration
     * @param remainingSpots int representing remaining spots for the event
     * @param capacity int representing event's maximum capacity
     * @param vip boolean representing whether event has VIP status or not
     * @param cancelled boolean representing whether event has been cancelled or not
     */
    public ScheduleEntry(LocalDateTime start, LocalDateTime end, String eventId, String eventName, String roomName,
                         String amenities, String attendees, String speakers, Duration duration, int remainingSpots,
                         int capacity, boolean vip, boolean cancelled) {
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
        this.eventId = new SimpleStringProperty(eventId);
        this.eventName = new SimpleStringProperty(eventName);
        this.roomName = new SimpleStringProperty(roomName);
        this.amenities = new SimpleStringProperty(amenities);
        this.attendees = new SimpleStringProperty(attendees);
        this.speakers = new SimpleStringProperty(speakers);
        this.duration = new SimpleObjectProperty<>(duration);
        this.remainingSpots = new SimpleIntegerProperty(remainingSpots);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.vip = new SimpleBooleanProperty(vip);
        this.cancelled = new SimpleBooleanProperty(cancelled);
    }

    //region Getters and Setters
    public LocalDateTime getStart() {
        return start.get();
    }

    public void setStart(LocalDateTime start) {
        this.start.set(start);
    }

    public LocalDateTime getEnd() {
        return end.get();
    }

    public void setEnd(LocalDateTime end) {
        this.end.set(end);
    }

    public String getEventId() {
        return eventId.get();
    }

    public String getEventName() {
        return eventName.get();
    }

    public void setEventName(String eventName) {
        this.eventName.set(eventName);
    }

    public String getRoomName() {
        return roomName.get();
    }

    public void setRoomName(String roomName) {
        this.roomName.set(roomName);
    }

    public String getAmenities() {
        return amenities.get();
    }

    public void setAmenities(String amenities) {
        this.amenities.set(amenities);
    }

    public String getAttendees() {
        return attendees.get();
    }

    public void setAttendees(String attendees) {
        this.attendees.set(attendees);
    }

    public Duration getDuration() {
        return duration.get();
    }

    public void setDuration(Duration duration) {
        this.duration.set(duration);
    }

    public int getRemainingSpots() {
        return remainingSpots.get();
    }

    public void setRemainingSpots(int remainingSpots) {
        this.remainingSpots.set(remainingSpots);
    }

    public String getSpeakers() {
        return speakers.get();
    }

    public void setSpeakers(String speakers) {
        this.speakers.set(speakers);
    }

    public int getCapacity() {
        return capacity.get();
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public boolean isVip() {
        return vip.get();
    }

    public BooleanProperty vipProperty() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip.set(vip);
    }

    public boolean isCancelled() {
        return cancelled.get();
    }

    public BooleanProperty cancelledProperty() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled.set(cancelled);
    }
    //endregion
}
