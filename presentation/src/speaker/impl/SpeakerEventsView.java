package speaker.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import model.ScheduleEntry;
import speaker.ISpeakerEventsView;
import util.DateTimeUtil;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * View class for viewing speaker's own events plus messaging events' attendees screen
 */
public class SpeakerEventsView implements ISpeakerEventsView {
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
    private Text summaryEventName;
    @FXML
    private Text summaryCapacity;
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
    private Text resultText;
    @FXML
    private TextField sender;
    @FXML
    private FlowPane recipients;
    @FXML
    private TextField subject;
    @FXML
    private TextArea content;

    @FXML
    public void executeAddSend(ActionEvent event) {
        if (sendButtonAction != null) sendButtonAction.handle(event);
    }
    @FXML
    public void initialize() { new SpeakerEventsPresenter(this); }

    private EventHandler<ActionEvent> sendButtonAction;
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
    public FlowPane getRecipientFlowPane() {
        return this.recipients;
    }

    @Override
    public TextArea getContentArea() {
        return this.content;
    }

    @Override
    public TextField getSubjectField() {
        return this.subject;
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
        String otherSpeakers = speakers.replace(getSessionUsername() + ",", "");
        if (otherSpeakers.equals(getSessionUsername())) otherSpeakers = "";
        this.summarySpeakers.setText(otherSpeakers);
    }

    @Override
    public String getSender() {
        return this.sender.getText();
    }

    @Override
    public void setSender(String sender) {
        this.sender.setText(sender);
    }

    @Override
    public String getContent() {
        return this.content.getText();
    }

    @Override
    public void setContent(String content) {
        this.content.setText(content);
    }

    @Override
    public String getSubject() {
        return this.subject.getText();
    }

    @Override
    public void setSubject(String subject) {
        this.subject.setText(subject);
    }

    @Override
    public void setResultText(String resultText) {
        this.resultText.setText(resultText);
    }

    @Override
    public void setSendButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.sendButtonAction = eventHandler;
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
