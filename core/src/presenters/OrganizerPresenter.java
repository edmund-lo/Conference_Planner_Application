package presenters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Presenter prints attributes that user of program (if organizer) can do/see.
 */
public class OrganizerPresenter extends UserPresenter {

    private final PresenterUtil<String> pu;

    public OrganizerPresenter(){
        pu = new PresenterUtil<>();
    }

    /**
     * Outputs the label that indicates of a successful creation of a room
     */
    public JSONObject roomCreationResult() {
        return pu.createJSON("Success", "Successfully created new room.");
    }

    /**
     * Outputs the label that indicates the success of messaging all speakers
     */
    public JSONObject messagedAllSpeakersResult() {
        return pu.createJSON("success", "Successfully sent message to all speakers.");
    }

    /**
     * Outputs the label that indicates the success of messaging all attendees
     */
    public JSONObject messagedAllAttendeesResult() {
        return pu.createJSON("success", "Successfully sent message to all attendees.");
    }

    /**
     * Outputs the label that indicates the success of adding a speaker to the selected event
     */
    public JSONObject scheduleSpeakerResult() {
        return pu.createJSON("success", "This speaker was successfully added to selected event!");
    }

    /**
     * Outputs the label that indicates the failure of creating a new room
     */
    public JSONObject invalidRoomNameError() {
        return pu.createJSON("error", "Unable to create new room: room's name was not unique.");
    }

    /**
     * Outputs the label that indicates that a speaker is already speaking at this event
     */
    public JSONObject existingSpeakerAtEventError() {
        return pu.createJSON("error", "Another speaker is already speaking at this event.");
    }

    /**
     * Outputs the label that indicates the a speaker is already speaking at another event
     */
    public JSONObject speakerUnavailableError() {
        return pu.createJSON("error", "This speaker is already speaking at another event.");
    }

//    /**
//     * Outputs a list of all speaker names in numbered order
//     *
//     * @param speakerNames the list of all speaker names
//     */
//    public JSONObject listSpeakers(List<String> speakerNames){
//        return pu.createJSON("success", "Speakers have been listed", "List of Speakers", speakerNames);
//    }

    /**
     * Outputs the label that indicates there are no rooms created
     */
    public JSONObject noRoomError() {
        return pu.createJSON("error", "There are no rooms in the system. You cannot create an event.");
    }

    /**
     * Outputs a label indicating a field is empty
     */
    public JSONObject emptyFieldError() {
        return pu.createJSON("warning", "Try again: cannot leave field empty!");
    }

    /**
     * Outputs a label indicating event is created successfully
     */
    public JSONObject eventCreationResult() {
        return pu.createJSON("success", "Successfully created new event.");
    }

    /**
     * Outputs a label indicating event cannot be created
     */
    public JSONObject eventFailedCreationError() {
        return pu.createJSON("error", "Unable to create new event: scheduling conflict occurred.");
    }

    /**
     * Outputs a label indicating an invalid date input
     */
    public JSONObject invalidDateError() {
        return pu.createJSON("warning", "The start or end time you entered was invalid.");
    }

    /**
     * Outputs an error message telling the user that their input was incorrect
     */
    public JSONObject incorrectInputError() {
        return pu.createJSON("warning", "Input error: An input you have entered for chairs, tables, projectors or " +
                "sound system was invalid");
    }

    /**
     * Notifies the user that the room specified cannot work with this event
     */
    public JSONObject selectionNotValid(){
        return pu.createJSON("error", "This room cannot accommodate the event.");
    }

    /**
     * Notifies the user that the capacity entered is invalid
     */
    public JSONObject invalidCapacityError() {
        return pu.createJSON("warning", "The capacity you entered was invalid.");
    }
    /**
     * Success message for successfully rescheduling an event
     */
    public JSONObject rescheduleSuccess(){
        return pu.createJSON("success", "Event rescheduled successfully!");
    }

    /**
     * Notifies user of their failure to reschedule event in roomName
     * @param roomName the name of the event they wish to reschedule
     * @return method description in JSONObject format
     */
    public JSONObject rescheduleFailure(String roomName){
        return pu.createJSON("error", "Event could not be rescheduled in "+roomName);
    }

    /**
     * Notifies the user that the capacity they entered is greater than what the room can accommodate
     */
    public JSONObject cannotAccommodate() {
        return pu.createJSON("warning", "The capacity you entered is greater than the room can " +
                "accommodate.");
    }

    /**
     * Notifies the user that the event capacity was changed successfully
     */
    public JSONObject changeCapResult(){
        return pu.createJSON("success", "The event capacity has been changed successfully.");
    }

    /**
     * Notifies the user that the event has been cancelled successfully
     */
    public JSONObject cancelResult(){
        return pu.createJSON("success", "The event has been cancelled successfully");
    }

    /**
     * Lists all available speakers
     * @param availableSpeakers available speakers
     * @return a JSONObject containing all available speakers
     */
    public JSONObject listSpeakers(JSONArray availableSpeakers) {
        return pu.createJSON("success", "List of all available speakers: ", availableSpeakers);
    }

    /**
     * lists all possible rooms
     * @param possibleRooms array of all possible rooms
     * @return a JSONObject containing all possible rooms
     */
    public JSONObject listPossibleRooms(JSONArray possibleRooms){
        return pu.createJSON("success", "List of possible rooms: ", possibleRooms);
    }

    /**
     * Notifies the user that their entered capacity is invalid
     * @return a JSONObject detailing the method description
     */
    public JSONObject invalidCapacity(){
        return pu.createJSON("warning", "Your capacity must be greater than 0");
    }

    /**
     * getter for the room schedules that formats it for the presentation module
     * @param data the room schedules
     * @return JSONObject properly formatted for the presentation module containing the data
     */
    public JSONObject listRoomSchedule(JSONArray data){
        return pu.createJSON("success", "returning room schedule", data);
    }

    /**
     * getter for all users that formats it for the presentation module
     * @param data all the users
     * @return JSONObject properly formatted for the presentation module containing the data
     */
    public JSONObject getAllUsers(JSONArray data){
        return pu.createJSON("success", "returning all users", data);
    }

    /**
     * getter for all the speakers that formats it for the presentation module
     * @param data all the speakers
     * @return JSONObject properly formatted for the presentation module containing the data
     */
    public JSONObject getAllSpeakers(JSONArray data){
        return pu.createJSON("success", "returning all speakers", data);
    }

    /**
     * Notifies th user that they cannot leave the room field empty
     * @return JSONObject properly formatted for the presentation module containing the notice
     */
    public JSONObject roomIsNull(){
        return pu.createJSON("warning", "Can't leave room empty!");
    }

    /**
     * Notifies the user that that the event is already cancelled
     * @return JSONObject properly formatted for the presentation module containing the notice
     */
    public JSONObject alreadyCancelled(){
        return pu.createJSON("warning", "Event is already cancelled");
    }
}
