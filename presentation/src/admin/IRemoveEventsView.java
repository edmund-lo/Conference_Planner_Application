package admin;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ScheduleEntry;
import java.time.Duration;
import java.time.LocalDateTime;

public interface IRemoveEventsView extends ILoggedInView {
    TableView<ScheduleEntry> getEventsTable();
    TableColumn<ScheduleEntry, String> getEventNameColumn();
    TableColumn<ScheduleEntry, String> getRoomNameColumn();
    TableColumn<ScheduleEntry, LocalDateTime> getEventStartColumn();
    TableColumn<ScheduleEntry, LocalDateTime> getEventEndColumn();

    void setSummaryEventName(String eventName);
    void setSummaryCapacity(int capacity);
    void setSummaryRoomName(String roomName);
    void setSummaryStart(LocalDateTime start);
    void setSummaryEnd(LocalDateTime end);
    void setSummaryDuration(Duration duration);
    void setSummaryAmenities(String amenities);
    void setSummaryAttendees(String attendees);
    void setSummarySpeakers(String speakers);
    void setResultText(String resultText);

    void setRemoveButtonAction(EventHandler<ActionEvent> eventHandler);
}
