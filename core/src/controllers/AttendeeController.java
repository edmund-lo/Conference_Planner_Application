package controllers;

import org.json.simple.JSONObject;
import presenters.AttendeePresenter;

import java.time.LocalDateTime;

/**
 * A Controller class representing an AttendeeController which inherits from UserController.
 *
 * @author Edmund Lo
 *
 */
public class AttendeeController extends UserController {

    private final AttendeePresenter ap;

    /**
     * Constructor for AttendeeController object. Uses constructor from UserController
     *
     * @param username current logged in user's username
     */
    public AttendeeController(String username) {
        super(username);
        this.ap = new AttendeePresenter();
    }

    /**
     * Signs up an attendee for a vip event
     *
     * @param eventID the vip event
     * @return a JSON object containing the status and description of the action
     */
    public JSONObject signUpVipEventAttendance(String eventID) {
        this.deserializeData();

        LocalDateTime start = em.getEventStartTime(eventID);
        LocalDateTime end = em.getEventEndTime(eventID);
        if (!um.isVip(username)) {
            return ap.notVipError();
        } else if (!um.canSignUp(username, eventID, start, end)) {
            return ap.alreadySignedUpError();
        } else if (!em.canAddUserToEvent(eventID,username)){
            return ap.eventFullCapacityError();
        } else {
            em.addUserToEvent(eventID,username);
            um.signUp(username, eventID, start, end);
            this.saveData();
            return ap.signUpVipResult(em.getEventName(eventID));
        }
    }

    /**
     * Cancels an attendee's attendance in a vip event
     *
     * @param eventID the vip event
     * @return a JSON object containing the status and description of the action
     */
    public JSONObject cancelVipEventAttendance(String eventID) {
        this.deserializeData();

        if(em.removeUserFromEvent(eventID, username)) {
            um.cancel(username, eventID);
            this.saveData();
            return ap.cancelVipResult(em.getEventName(eventID));
        }
        return ap.notAttendingEventError(em.getEventName(eventID));
    }

    /**
     * Gets an attendees number of next day events
     *
     * @return a JSON object of all the attendees next day events
     */
    @SuppressWarnings("unchecked")
    public JSONObject getNextDayEvents(){
        this.deserializeData();

        int numEvents = 0;

        for (String eventID: um.getSchedule(this.username).keySet()){
            int day = em.getEventStartTime(eventID).getDayOfYear();
            if (day == LocalDateTime.now().getDayOfYear() + 1) {
                numEvents += 1;
            }
        }
        return ap.numberNextDayEvents(numEvents);
    }

}
