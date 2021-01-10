package organizer.impl;

import adapter.ScheduleAdapter;
import adapter.UserAdapter;
import common.UserAccountHolder;
import controllers.OrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ScheduleEntry;
import model.User;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import organizer.IScheduleSpeakerPresenter;
import organizer.IScheduleSpeakerView;
import util.DateTimeUtil;
import util.TextResultUtil;
import java.util.List;

/**
 * Presenter class for scheduling speakers scene
 */
public class ScheduleSpeakerPresenter implements IScheduleSpeakerPresenter {
    private final OrganizerController oc;
    private final IScheduleSpeakerView view;
    private ScheduleEntry selectedEvent;

    /**
     * Initialises a ScheduleSpeakerPresenter object with given view and new OrganizerController,
     * gets and sets current session's user information
     * @param view IScheduleSpeakerView interface implementation
     */
    public ScheduleSpeakerPresenter(IScheduleSpeakerView view) {
        this.view = view;
        getUserData();
        this.oc = new OrganizerController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs schedule speaker button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void scheduleSpeakerButtonAction(ActionEvent actionEvent) {
        clearResultText();
        if(selectedEvent == null){
            return;
        }
        String speaker = this.view.getAvailableSpeakerChoiceBox().getValue();
        JSONObject responseJson = oc.scheduleSpeaker(selectedEvent.getEventId(), speaker);
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
        if (String.valueOf(responseJson.get("status")).equals("success"))
            handleSelect(this.selectedEvent);
    }

    /**
     * Sets the result of the action given status
     * @param resultText String object describing the result
     * @param status String object representing the status of the controller method call
     */
    @Override
    public void setResultText(String resultText, String status) {
        this.view.setResultText(resultText);
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getResultTextControl());
    }

    /**
     * Gets all Event entities occurring in the future and converts them into ScheduleEntry models
     * @return List of ScheduleEntry models
     */
    @Override
    public List<ScheduleEntry> getAllEvents() {
        JSONObject responseJson = oc.getAllEvents();
        return ScheduleAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
    }

    /**
     * Displays schedule in the TableView and adds listeners
     * @param schedule List of ScheduleEntry models
     */
    @Override
    public void displayEvents(List<ScheduleEntry> schedule) {
        DateTimeUtil.getInstance().setScheduleDateTimeCellFactory(this.view.getEventStartColumn());
        DateTimeUtil.getInstance().setScheduleDateTimeCellFactory(this.view.getEventEndColumn());
        this.view.getEventNameColumn().setCellValueFactory(new PropertyValueFactory<>("eventName"));
        this.view.getRoomNameColumn().setCellValueFactory(new PropertyValueFactory<>("roomName"));
        this.view.getEventStartColumn().setCellValueFactory(new PropertyValueFactory<>("start"));
        this.view.getEventEndColumn().setCellValueFactory(new PropertyValueFactory<>("end"));

        ObservableList<ScheduleEntry> observableSchedule = FXCollections.observableArrayList(schedule);
        this.view.getEventsTable().setItems(observableSchedule);
        this.view.getEventsTable().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleSelect(newValue));
    }

    /**
     * Displays event's attributes
     * @param event ScheduleEntry model that has been selected
     */
    @Override
    public void displayEventDetails(ScheduleEntry event) {
        this.view.setSummaryEventName(event.getEventName());
        this.view.setSummaryRoomName(event.getRoomName());
        this.view.setSummaryAmenities(event.getAmenities());
        this.view.setSummaryDuration(event.getDuration());
        this.view.setSummaryStart(event.getStart());
        this.view.setSummaryEnd(event.getEnd());
        this.view.setSummarySpeakers(event.getSpeakers());
        this.view.setSummaryCapacity(event.getCapacity());
    }

    /**
     * Displays event's available speakers list in a dropdown choice box to choose from
     * @param event ScheduleEntry model
     */
    @Override
    public void displayAvailableSpeakers(ScheduleEntry event) {
        clearResultText();

        JSONObject responseJson = oc.listAvailableSpeakers(this.selectedEvent.getEventId());
        List<User> speakerList = UserAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
        if (!String.valueOf(responseJson.get("status")).equals("success"))
            setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
        this.view.getAvailableSpeakerChoiceBox().getItems().clear();
        for (User speaker : speakerList){
            this.view.getAvailableSpeakerChoiceBox().getItems().add(speaker.getUsername());
        }
    }

    /**
     * Init method which sets all the button actions, gets and displays all events
     */
    @Override
    public void init() {
        this.view.setScheduleSpeakerButtonAction(this::scheduleSpeakerButtonAction);
        List<ScheduleEntry> schedule = getAllEvents();
        displayEvents(schedule);
    }

    /**
     * Helper method to get and set current user's information to the view class variable
     */
    @Override
    public void getUserData() {
        UserAccountHolder holder = UserAccountHolder.getInstance();
        UserAccount account = holder.getUserAccount();
        this.view.setSessionUsername(account.getUsername());
        this.view.setSessionUserType(account.getUserType());
    }

    /**
     * Helper method to handle select event
     * @param event ScheduleEntry model that has been selected
     */
    private void handleSelect(ScheduleEntry event) {
        this.selectedEvent = event;
        displayEventDetails(event);
        displayAvailableSpeakers(event);
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText() {
        this.view.setResultText("");
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl());
    }
}
