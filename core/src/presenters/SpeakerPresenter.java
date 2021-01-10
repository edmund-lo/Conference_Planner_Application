package presenters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Presenter class that outputs speaker attributes that client-users can view
 * Displays menu options
 * Displays list of speaker's events
 * Prompts request to send message to event attendees
 * confirms and rejects success of sending messages to event attendees based on invalid credentials
 */
public class SpeakerPresenter extends UserPresenter {

    private PresenterUtil<String> pu;

    public SpeakerPresenter(){
        pu = new PresenterUtil<>();
    }

    /**
     * Notifies the user that all they're sent messages were sent successfully
     */
    public JSONObject messageEventAttendeesMultiEventsResult(){
        return pu.createJSON("success", "Successfully sent messages to all specified event(s)");
    }


    /**
     * getter for all the speaker events in the system and formats it properly for the presentation module
     * @param data all the speaker events
     * @return JSONObject properly formatted for the presentation module containing the data
     */
    public JSONObject getSpeakerEvents(JSONArray data){
        return pu.createJSON("success", "returning speaker events", data);
    }
}
