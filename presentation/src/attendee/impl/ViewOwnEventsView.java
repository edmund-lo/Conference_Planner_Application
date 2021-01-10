package attendee.impl;

import attendee.IViewEventsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import model.ScheduleEntry;
import util.DateTimeUtil;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * View class for viewing own schedule screen
 */
public class ViewOwnEventsView implements IViewEventsView {
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
    private TableColumn<ScheduleEntry, Boolean> vipColumn;
    @FXML
    private TableColumn<ScheduleEntry, Integer> remainingSpotsColumn;
    @FXML
    private Text summaryEventName;
    @FXML
    private Text summaryCapacity;
    @FXML
    private Text summaryRemainingSpots;
    @FXML
    private Text summaryRoomName;
    @FXML
    private Text summaryStart;
    @FXML
    private Text summaryEnd;
    @FXML
    private Text summaryDuration;
    @FXML
    private Text summaryAmenities;
    @FXML
    private Text summarySpeakers;
    @FXML
    private Text summaryAttendees;
    @FXML
    private Text summaryVip;
    @FXML
    private Text resultText;

    @FXML
    public void executeAddCancel(ActionEvent event) {
        if (cancelButtonAction != null) cancelButtonAction.handle(event);
    }
    @FXML
    public void initialize() {
        new ViewOwnEventsPresenter(this);
    }

    private EventHandler<ActionEvent> cancelButtonAction;
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
    public TableColumn<ScheduleEntry, Boolean> getVipColumn() {
        return this.vipColumn;
    }


    @Override
    public TableColumn<ScheduleEntry, Integer> getRemainingSpotsColumn() {
        return this.remainingSpotsColumn;
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
        this.summaryRoomName.setText(roomName);
    }

    @Override
    public void setSummaryStart(LocalDateTime start) {
        this.summaryStart.setText(DateTimeUtil.getInstance().format(start));
    }

    @Override
    public void setSummaryEnd(LocalDateTime end) {
        this.summaryEnd.setText(DateTimeUtil.getInstance().format(end));
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
        this.summaryVip.setText(vip);
    }

    @Override
    public void setResultText(String resultText) {
        this.resultText.setText(resultText);
    }

    @Override
    public void setPressButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.cancelButtonAction = eventHandler;
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
