package attendee.impl;

import adapter.ScheduleAdapter;
import attendee.IViewEventsPresenter;
import attendee.IViewEventsView;
import common.UserAccountHolder;
import controllers.AttendeeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ScheduleEntry;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;
import util.TextResultUtil;

import java.util.List;

/**
 * Presenter class for viewing all events screen
 */
public class ViewAllEventsPresenter implements IViewEventsPresenter {
    private final IViewEventsView view;
    private final AttendeeController ac;
    private ScheduleEntry selectedEvent;

    /**
     * Initialises a ViewAllEventsPresenter object with given view and new AttendeeController,
     * gets and sets current session's user information
     * @param view IViewEventsView interface implementation
     */
    public ViewAllEventsPresenter(IViewEventsView view) {
        this.view = view;
        getUserData();
        this.ac = new AttendeeController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs sign up for event button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void pressButtonAction(ActionEvent actionEvent) {
        clearResultText();
        if (this.selectedEvent == null) return;
        JSONObject responseJson = ac.signUpEventAttendance(this.selectedEvent.getEventId());
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
     * Gets all Event entities that the user can attend and converts them into ScheduleEntry models
     * @return List of ScheduleEntry models
     */
    @Override
    public List<ScheduleEntry> getEvents() {
        JSONObject responseJson = ac.getAllEventsCanSignUp();
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
        this.view.getVipColumn().setCellValueFactory(param -> param.getValue().vipProperty());
        this.view.getVipColumn().setCellFactory(CheckBoxTableCell.forTableColumn(this.view.getVipColumn()));
        this.view.getRemainingSpotsColumn().setCellValueFactory(new PropertyValueFactory<>("remainingSpots"));

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
        if(event == null){
            return;
        }
        this.view.setSummaryEventName(event.getEventName());
        this.view.setSummaryRoomName(event.getRoomName());
        this.view.setSummaryAttendees(event.getAttendees());
        this.view.setSummaryAmenities(event.getAmenities());
        this.view.setSummaryDuration(event.getDuration());
        this.view.setSummaryStart(event.getStart());
        this.view.setSummaryEnd(event.getEnd());
        this.view.setSummarySpeakers(event.getSpeakers());
        this.view.setSummaryCapacity(event.getCapacity());
        this.view.setSummaryRemainingSpots(event.getRemainingSpots());
        this.view.setSummaryVip(event.isVip() ? "Yes" : "No");
    }

    /**
     * Init method which sets all the button actions, gets and displays all events
     */
    @Override
    public void init() {
        this.view.setPressButtonAction(this::pressButtonAction);
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
