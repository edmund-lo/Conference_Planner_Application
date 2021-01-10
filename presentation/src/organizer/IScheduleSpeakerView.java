package organizer;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ScheduleEntry;
import java.time.Duration;
import java.time.LocalDateTime;

public interface IScheduleSpeakerView extends ILoggedInView {
    TableView<ScheduleEntry> getEventsTable();
    TableColumn<ScheduleEntry, String> getEventNameColumn();
    TableColumn<ScheduleEntry, String> getRoomNameColumn();
    TableColumn<ScheduleEntry, LocalDateTime> getEventStartColumn();
    TableColumn<ScheduleEntry, LocalDateTime> getEventEndColumn();
    ChoiceBox<String> getAvailableSpeakerChoiceBox();

    String getSummaryEventName();
    void setSummaryEventName(String eventName);
    void setSummaryCapacity(int capacity);
    void setSummaryRoomName(String roomName);
    void setSummaryStart(LocalDateTime start);
    void setSummaryEnd(LocalDateTime end);
    void setSummaryDuration(Duration duration);
    void setSummaryAmenities(String amenities);
    void setSummarySpeakers(String speakers);
    void setResultText(String resultText);

    void setScheduleSpeakerButtonAction(EventHandler<ActionEvent> eventHandler);
}
