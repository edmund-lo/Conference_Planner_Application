package organizer;

import common.ILoggedInPresenter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import model.ScheduleEntry;
import java.util.List;

public interface IRescheduleCancelEventPresenter extends ILoggedInPresenter {
    void cancelButtonAction(ActionEvent actionEvent);
    void rescheduleButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status);
    List<ScheduleEntry> getEvents();
    void displayEvents(List<ScheduleEntry> schedule);
    void displayEventDetails(ScheduleEntry event);
}
