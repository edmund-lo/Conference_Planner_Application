package speaker;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import model.ScheduleEntry;

import java.time.Duration;
import java.time.LocalDateTime;

public interface ISpeakerEventsView extends ILoggedInView {
    TableView<ScheduleEntry> getEventsTable();
    TableColumn<ScheduleEntry, String> getEventNameColumn();
    TableColumn<ScheduleEntry, String> getRoomNameColumn();
    TableColumn<ScheduleEntry, LocalDateTime> getEventStartColumn();
    TableColumn<ScheduleEntry, LocalDateTime> getEventEndColumn();
    FlowPane getRecipientFlowPane();
    TextArea getContentArea();
    TextField getSubjectField();

    void setSummaryEventName(String eventName);
    void setSummaryCapacity(int capacity);
    void setSummaryRoomName(String roomName);
    void setSummaryStart(LocalDateTime start);
    void setSummaryEnd(LocalDateTime end);
    void setSummaryDuration(Duration duration);
    void setSummaryAmenities(String amenities);
    void setSummaryAttendees(String attendees);
    void setSummarySpeakers(String speakers);
    String getSender();
    void setSender(String sender);
    String getContent();
    void setContent(String content);
    String getSubject();
    void setSubject(String subject);
    void setResultText(String resultText);

    void setSendButtonAction(EventHandler<ActionEvent> eventHandler);
}
