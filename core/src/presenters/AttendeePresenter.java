package presenters;

import org.json.simple.JSONObject;

/**
 * A Presenter class representing an AttendeePresenter which inherits from UserPresenter
 *
 * @author Edmund Lo
 *
 */
public class AttendeePresenter extends UserPresenter{

    public AttendeePresenter() {
        super();
    }

    /**
     * Prints message that attendee is not a vip
     */
    public JSONObject notVipError() {
        return pu.createJSON("error", "Attendee is not a VIP");
    }

    /**
     * Confirm sign up to vip event
     *
     * @param eventName the event name
     */
    public JSONObject signUpVipResult(String eventName) {
        return pu.createJSON("success", "You have signed up for the VIP event: " + eventName);
    }

    /**
     * Confirms cancellation of attendance in vip event
     *
     * @param eventName the event name
     */
    public JSONObject cancelVipResult(String eventName) {
        return pu.createJSON("success", "You have cancelled your attendance in the VIP event: " + eventName);
    }

    /**
     * Prints message about the number of events they are attending the next day
     *
     * @param numEvents the number of events they have the next day
     */
    public JSONObject numberNextDayEvents(int numEvents) {
        return pu.createJSON("success", "You have " + numEvents + " events tomorrow");
    }
}
