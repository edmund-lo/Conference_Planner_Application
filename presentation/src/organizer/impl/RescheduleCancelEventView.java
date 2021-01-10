package organizer.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import model.ScheduleEntry;
import organizer.IRescheduleCancelEventView;
import util.DateTimePicker;
import util.DateTimeUtil;
import util.NumberTextField;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * View class for rescheduling and cancelling events scene
 */
public class RescheduleCancelEventView implements IRescheduleCancelEventView {
    @FXML
    private TableView<ScheduleEntry> eventTable;
    @FXML
    private TableColumn<ScheduleEntry, String> eventNameColumn;
    @FXML
    private TableColumn<ScheduleEntry, String> roomNameColumn;
    @FXML
    private TableColumn<ScheduleEntry, LocalDateTime> eventStartColumn;
    @FXML
    private TableColumn<ScheduleEntry, LocalDateTime> eventEndColumn;
    @FXML
    private TableColumn<ScheduleEntry, Integer> remainingSpotsColumn;
    @FXML
    private TableColumn<ScheduleEntry, Boolean> cancelledColumn;
    @FXML
    private Text summaryEventName;
    @FXML
    private NumberTextField summaryCapacity;
    @FXML
    private Text summaryRemainingSpots;
    @FXML
    private ChoiceBox<String> summaryRoomName;
    @FXML
    private DateTimePicker summaryStart;
    @FXML
    private DateTimePicker summaryEnd;
    @FXML
    private Text summaryDuration;
    @FXML
    private Text summaryAmenities;
    @FXML
    private Text summarySpeakers;
    @FXML
    private Text summaryAttendees;
    @FXML
    private ChoiceBox<String> summaryVip;
    @FXML
    private Text resultText;
    @FXML
    private Button rescheduleButton;
    @FXML
    private Button cancelButton;

    @FXML
    public void executeAddCancel(ActionEvent event) {
        if (cancelButtonAction != null) cancelButtonAction.handle(event);
    }
    @FXML
    public void executeAddReschedule(ActionEvent event) {
        if (rescheduleButtonAction != null) rescheduleButtonAction.handle(event);
    }
    @FXML
    public void initialize() {
        new RescheduleCancelEventPresenter(this);
    }

    private EventHandler<ActionEvent> cancelButtonAction;
    private EventHandler<ActionEvent> rescheduleButtonAction;
    private String sessionUsername;
    private String sessionUserType;

    @Override
    public TableView<ScheduleEntry> getEventsTable() {
        return this.eventTable;
    }

    @Override
    public TableColumn<ScheduleEntry, String> getEventNameColumn() {
        return this.eventNameColumn;
    }

    @Override
    public TableColumn<ScheduleEntry, String> getRoomNameColumn() {
        return this.roomNameColumn;
    }

    @Override
    public TableColumn<ScheduleEntry, LocalDateTime> getEventStartColumn() {
        return this.eventStartColumn;
    }

    @Override
    public TableColumn<ScheduleEntry, LocalDateTime> getEventEndColumn() {
        return this.eventEndColumn;
    }

    @Override
    public TableColumn<ScheduleEntry, Integer> getRemainingSpotsColumn() {
        return this.remainingSpotsColumn;
    }

    @Override
    public TableColumn<ScheduleEntry, Boolean> getCancelledColumn() {
        return this.cancelledColumn;
    }

    @Override
    public NumberTextField getSummaryCapacityField() {
        return this.summaryCapacity;
    }

    @Override
    public ChoiceBox<String> getSummaryRoomsChoiceBox() {
        return this.summaryRoomName;
    }

    @Override
    public DateTimePicker getSummaryStart() {
        return this.summaryStart;
    }

    @Override
    public DateTimePicker getSummaryEnd() {
        return this.summaryEnd;
    }

    @Override
    public ChoiceBox<String> getSummaryVipChoiceBox() {
        return this.summaryVip;
    }

    @Override
    public Button getRescheduleButton() {
        return this.rescheduleButton;
    }

    @Override
    public Button getCancelButton() {
        return this.cancelButton;
    }

    @Override
    public void setSummaryEventName(String eventName) {
        this.summaryEventName.setText(eventName);
    }

    @Override
    public void setSummaryCapacity(int capacity) {
        this.summaryCapacity.setText("" + capacity);
    }

    @Override
    public void setSummaryRemainingSpots(int remainingSpots) {
        this.summaryRemainingSpots.setText("" + remainingSpots);
    }

    @Override
    public void setSummaryRoomName(String roomName) {
        this.summaryRoomName.setValue(roomName);
    }

    @Override
    public void setSummaryStart(LocalDateTime start) {
        this.summaryStart.setDateTimeValue(start);
    }

    @Override
    public void setSummaryEnd(LocalDateTime end) {
        this.summaryEnd.setDateTimeValue(end);
    }

    @Override
    public void setSummaryDuration(Duration duration) {
        this.summaryDuration.setText(DateTimeUtil.getInstance().formatDuration(duration));
    }

    @Override
    public void setSummaryAmenities(String amenities) {
        this.summaryAmenities.setText(amenities);
    }

    @Override
    public void setSummaryAttendees(String attendees) {
        this.summaryAttendees.setText(attendees);
    }

    @Override
    public void setSummarySpeakers(String speakers) {
        this.summarySpeakers.setText(speakers);
    }

    @Override
    public void setSummaryVip(String vip) {
        this.summaryVip.setValue(vip);
    }

    @Override
    public void setResultText(String resultText) {
        this.resultText.setText(resultText);
    }

    @Override
    public void setCancelButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.cancelButtonAction = eventHandler;
    }

    @Override
    public void setRescheduleButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.rescheduleButtonAction = eventHandler;
    }

    @Override
    public String getSessionUsername() {
        return this.sessionUsername;
    }

    @Override
    public void setSessionUsername(String username) {
        this.sessionUsername = username;
    }

    @Override
    public String getSessionUserType() {
        return this.sessionUserType;
    }

    @Override
    public void setSessionUserType(String userType) {
        this.sessionUserType = userType;
    }

    @Override
    public Text getResultTextControl() {
        return this.resultText;
    }
}
