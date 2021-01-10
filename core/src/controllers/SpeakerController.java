package controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import presenters.SpeakerPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * A Controller class representing a SpeakerController which inherits from UserController.
 *
 * @author Echo Li
 * @version 1.0
 *
 */
public class SpeakerController extends UserController {
    private final SpeakerPresenter sp;

    /**
     * Constructor for SpeakerController object. Uses constructor from UserController.
     *
     * @param username current logged in user's username.
     */
    public SpeakerController(String username) {
        super(username);
        sp = new SpeakerPresenter();
    }

    /**
     * Called when user chooses to message one or more events' attendees.
     */
    public JSONObject messageEventsAttendeesCmd(JSONObject register) {
        this.deserializeData();
        this.sendMessage(register);
        this.saveData();
        return sp.messageEventAttendeesMultiEventsResult();
    }


    /**
     * Gets all events that current speaker is speaking at.
     * @return List of Strings representing the events the current speaker is speaking at.
     */
    public JSONObject getSpeakerEvents() {
        this.deserializeData();

        JSONArray array = new JSONArray();

        for (String eventID: um.getSpeakerSchedule(this.username).keySet()){
            array.add(em.getEventJson(eventID));
        }
        return sp.getSpeakerEvents(array);
    }
}
