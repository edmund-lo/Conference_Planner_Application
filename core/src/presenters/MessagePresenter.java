package presenters;
import org.json.simple.*;

import java.util.List;

/**
 * A Presenter class that prints Message related functionality to the user's screen
 */
public class MessagePresenter {

    private PresenterUtil<String> pu;

    public MessagePresenter(){
        pu = new PresenterUtil<>();
    }

    /**
     * Informs the user that they cannot message an organizer.
     */
    public JSONObject cannotMessageOrganizerError() {
        return pu.createJSON("error", "You cannot message an Organizer!");
    }

    /**
     * Informs the user that have either entered an invalid user to message, or that their message was incorrectly
     * formatted.
     */
    public JSONObject invalidMessageError() {return pu.createJSON("warning", "Invalid user or message format!");
    }

    /**
     * Informs the user that the message he wants to reply to does not exist in allMessageThreads.
     */
    public JSONObject invalidMessageIdError() {return pu.createJSON("warning", "This message does not exist!");
    }

    /**
     * Informs the user that their message was sent successfully to name
     * @param names name of the receiver of the message
     */
    public JSONObject messageSent(List<String> names){
        return pu.createJSON("success", "Message sent to "+names+" successfully!");
    }

    /**
     * Informs the user that their message was replied successfully to name
     * @param name name of the receiver of the message
     */
    public JSONObject messageReplied(String name){
        return pu.createJSON("success", "Message replied to "+name+" successfully!");
    }
}