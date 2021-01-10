package admin.impl;

import adapter.ScheduleAdapter;
import admin.IRemoveEventsPresenter;
import admin.IRemoveEventsView;
import common.UserAccountHolder;
import controllers.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ScheduleEntry;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;
import util.TextResultUtil;
import java.util.List;

/**
 * Presenter class for removing events screen
 */
public class RemoveEventsPresenter implements IRemoveEventsPresenter {
    private final IRemoveEventsView view;
    private final AdminController ac;
    private ScheduleEntry selectedEvent;

    /**
     * Initialises a RemoveEventsPresenter object with given view and new AdminController,
     * gets and sets current session's user information
     * @param view IRemoveEventsView interface implementation
     */
    public RemoveEventsPresenter(IRemoveEventsView view) {
        this.view = view;
        getUserData();
        this.ac = new AdminController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs remove event button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void removeButtonAction(ActionEvent actionEvent) {
        clearResultText();

        JSONObject responseJson = ac.removeEvent(this.selectedEvent.getEventId());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
        if (String.valueOf(responseJson.get("status")).equals("success")) init();
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
     * Gets all Event entities stored in the database and converts them into ScheduleEntry models
     * @return List of ScheduleEntry models
     */
    @Override
    public List<ScheduleEntry> getEvents() {
        JSONObject responseJson = ac.getAllEventsIncludingCancelled();
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
                (observable, oldValue, newValue) -> displayEventDetails(newValue));
    }

    /**
     * Displays event's attributes
     * @param event ScheduleEntry model that has been selected
     */
    @Override
    public void displayEventDetails(ScheduleEntry event) {
        this.selectedEvent = event;
        this.view.setSummaryEventName(event.getEventName());
        this.view.setSummaryRoomName(event.getRoomName());
        this.view.setSummaryAttendees(event.getAttendees());
        this.view.setSummaryAmenities(event.getAmenities());
        this.view.setSummaryDuration(event.getDuration());
        this.view.setSummaryStart(event.getStart());
        this.view.setSummaryEnd(event.getEnd());
        this.view.setSummarySpeakers(event.getSpeakers());
        this.view.setSummaryCapacity(event.getCapacity());
    }

    /**
     * Init method which sets all the button actions, gets and displays all events
     */
    @Override
    public void init() {
        this.view.setRemoveButtonAction(this::removeButtonAction);
        List<ScheduleEntry> allEvents = getEvents();
        displayEvents(allEvents);
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
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText() {
        this.view.setResultText("");
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl());
    }
}
