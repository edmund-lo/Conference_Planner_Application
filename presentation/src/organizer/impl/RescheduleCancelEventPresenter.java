package organizer.impl;

import adapter.ScheduleAdapter;
import common.UserAccountHolder;
import controllers.OrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ScheduleEntry;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import organizer.IRescheduleCancelEventPresenter;
import organizer.IRescheduleCancelEventView;
import util.DateTimePicker;
import util.DateTimeUtil;
import util.TextResultUtil;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Presenter class for rescheduling and cancelling events scene
 */
public class RescheduleCancelEventPresenter implements IRescheduleCancelEventPresenter {
    private final IRescheduleCancelEventView view;
    private final OrganizerController oc;
    private ScheduleEntry selectedEvent;

    /**
     * Initialises a RescheduleCancelEventPresenter object with given view and new OrganizerController,
     * gets and sets current session's user information
     * @param view IRescheduleCancelEventView interface implementation
     */
    public RescheduleCancelEventPresenter(IRescheduleCancelEventView view) {
        this.view = view;
        getUserData();
        this.oc = new OrganizerController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs cancel event button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void cancelButtonAction(ActionEvent actionEvent) {
        clearResultText();
        if (selectedEvent == null) return;
        JSONObject responseJson = oc.cancelEvent(this.selectedEvent.getEventId());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
        if (String.valueOf(responseJson.get("status")).equals("success")) {
            clearForm();
            List<ScheduleEntry> allEvents = getEvents();
            displayEvents(allEvents);
        }
    }

    /**
     * Performs reschedule event button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void rescheduleButtonAction(ActionEvent actionEvent) {
        clearResultText();
        if (this.selectedEvent == null) return;
        JSONObject queryJson = constructEventJson();
        JSONObject responseJson = oc.rescheduleEvent(queryJson);
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
        if (String.valueOf(responseJson.get("status")).equals("success")) {
            List<ScheduleEntry> allEvents = getEvents();
            displayEvents(allEvents);
        }
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
     * Gets all Event entities that can be rescheduled and converts them into ScheduleEntry models
     * @return List of ScheduleEntry models
     */
    @Override
    public List<ScheduleEntry> getEvents() {
        JSONObject responseJson = oc.getAllEventsIncludingCancelled();
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
        this.view.getRemainingSpotsColumn().setCellValueFactory(new PropertyValueFactory<>("remainingSpots"));
        this.view.getCancelledColumn().setCellValueFactory(param -> param.getValue().cancelledProperty());
        this.view.getCancelledColumn().setCellFactory(CheckBoxTableCell.forTableColumn(this.view.getCancelledColumn()));

        ObservableList<ScheduleEntry> observableSchedule = FXCollections.observableArrayList(schedule);
        this.view.getEventsTable().setItems(observableSchedule);
        this.view.getEventsTable().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayEventDetails(newValue));
    }

    /**
     * Displays event's attributes and sets certain fields to editable based on whether event is cancelled or not
     * @param event ScheduleEntry model that has been selected
     */
    @Override
    public void displayEventDetails(ScheduleEntry event) {
        if (event == null) return;
        this.selectedEvent = event;
        JSONObject queryJson = constructRoomRequestJson(event);
        JSONObject responseJson = oc.listPossibleRooms(queryJson);
        displayPossibleRooms((JSONArray) responseJson.get("data"));
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
        setEditableFields(event.isCancelled());
    }

    /**
     * Init method which sets all the button actions, gets and displays all events
     */
    @Override
    public void init() {
        this.view.setCancelButtonAction(this::cancelButtonAction);
        this.view.setRescheduleButtonAction(this::rescheduleButtonAction);
        updateDuration(this.view.getSummaryStart());
        updateDuration(this.view.getSummaryEnd());
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
     * Helper method to update the duration of an event when rescheduling start and end date/time
     * @param picker JavaFX DateTimePicker utility object
     */
    private void updateDuration(DateTimePicker picker) {
        picker.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            LocalDateTime start = this.view.getSummaryStart().getDateTimeValue();
            LocalDateTime end = this.view.getSummaryEnd().getDateTimeValue();
            if (start == null || end == null)
                this.view.setSummaryDuration(Duration.ZERO);
            else
                this.view.setSummaryDuration(Duration.between(start, end));
        }));
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText() {
        this.view.setResultText("");
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl());
    }

    /**
     * Helper method to clear all form fields affected by cancelling an event
     */
    private void clearForm() {
        this.view.setSummaryStart(null);
        this.view.setSummaryEnd(null);
        this.view.getSummaryCapacityField().setNumber(BigDecimal.ZERO);
        this.view.setSummaryDuration(Duration.ZERO);
        this.view.setSummaryAttendees("");
        this.view.setSummarySpeakers("");
        this.view.setSummaryRemainingSpots(0);
    }

    /**
     * Helper method to display all suggested rooms for rescheduling in a choice box
     * @param jsonArray JSONArray object of possible room names
     */
    private void displayPossibleRooms(JSONArray jsonArray) {
        this.view.getSummaryRoomsChoiceBox().getItems().clear();
        for (Object o : jsonArray)
            this.view.getSummaryRoomsChoiceBox().getItems().add(String.valueOf(o));
    }

    /**
     * Helper method to set editable fields to disabled or not based on cancelled
     * @param cancelled boolean representing whether event is cancelled or not
     */
    private void setEditableFields(boolean cancelled) {
        this.view.getRescheduleButton().setDisable(!cancelled);
        this.view.getCancelButton().setDisable(cancelled);
        if (cancelled) { // cancelled event
            this.view.getSummaryStart().setDisable(false);
            this.view.getSummaryEnd().setDisable(false);
            this.view.getSummaryCapacityField().setDisable(false);
            this.view.getSummaryVipChoiceBox().setDisable(false);
            this.view.getSummaryRoomsChoiceBox().setDisable(false);
        } else { // not cancelled event
            this.view.getSummaryStart().setDisable(true);
            this.view.getSummaryEnd().setDisable(true);
            this.view.getSummaryCapacityField().setDisable(true);
            this.view.getSummaryVipChoiceBox().setDisable(true);
            this.view.getSummaryRoomsChoiceBox().setDisable(true);
        }
    }

    /**
     * Helper method to encode a JSONObject for a room request form
     * @param event ScheduleEntry model that has been selected for rescheduling
     * @return JSONObject object representing a room request form
     */
    @SuppressWarnings("unchecked")
    private JSONObject constructRoomRequestJson(ScheduleEntry event) {
        JSONObject queryJson = new JSONObject();
        queryJson.put("capacity", event.getCapacity());
        queryJson.put("chairs", event.getAmenities().contains("Chairs"));
        queryJson.put("tables", event.getAmenities().contains("Tables"));
        queryJson.put("projector", event.getAmenities().contains("Projector"));
        queryJson.put("sound", event.getAmenities().contains("Sound"));
        return queryJson;
    }

    /**
     * Helper method to encode a JSONObject for an event rescheduling form
     * @return JSONObject object representing an event rescheduling form
     */
    @SuppressWarnings("unchecked")
    private JSONObject constructEventJson() {
        JSONObject queryJson = new JSONObject();
        queryJson.put("eventId", this.selectedEvent.getEventId());
        queryJson.put("capacity", this.view.getSummaryCapacityField().getNumber());
        queryJson.put("vip", this.view.getSummaryVipChoiceBox().getValue().equals("Yes"));
        queryJson.put("roomName", this.view.getSummaryRoomsChoiceBox().getValue());
        queryJson.put("start", this.view.getSummaryStart().getDateTimeValue());
        queryJson.put("end", this.view.getSummaryEnd().getDateTimeValue());
        return queryJson;
    }
}
