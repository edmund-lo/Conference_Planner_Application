package organizer;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.DateTimePicker;
import java.time.LocalDateTime;

public interface ICreateEventView extends ILoggedInView {
    String getEventName();
    void setEventName(String eventName);
    int getCapacity();
    void setCapacity(int capacity);
    boolean getAmenity(int index);
    void setAmenity(int index, boolean checked);
    String getVipEvent();
    void setVipEvent(String vipEvent);
    String getRoomName();
    void setRoomName(String roomName);
    LocalDateTime getStart();
    void setStart(LocalDateTime start);
    LocalDateTime getEnd();
    void setEnd(LocalDateTime end);
    void setSummaryEventName(String eventName);
    void setSummaryCapacity(int capacity);
    void setSummaryRoomName(String roomName);
    void setSummaryStart(LocalDateTime start);
    void setSummaryEnd(LocalDateTime end);
    void setSummaryAmenities(String amenities);
    void setSummaryVipEvent(String vipEvent);
    void setResultText(String resultText, int index);

    ComboBox<String> getRoomComboBox();
    DateTimePicker getStartPicker();
    DateTimePicker getEndPicker();
    CheckBox getAmenityBox(int index);
    TitledPane getTitledPane(int index);
    VBox getTableContainer();
    Text getResultTextControl(int index);

    void setFindRoomsButtonAction(EventHandler<ActionEvent> eventHandler);
    void setPreviousFirstButtonAction(EventHandler<ActionEvent> eventHandler);
    void setPreviewRoomButtonAction(EventHandler<ActionEvent> eventHandler);
    void setSummaryButtonAction(EventHandler<ActionEvent> eventHandler);
    void setPreviousSecondButtonAction(EventHandler<ActionEvent> eventHandler);
    void setCreateEventButtonAction(EventHandler<ActionEvent> eventHandler);
}
