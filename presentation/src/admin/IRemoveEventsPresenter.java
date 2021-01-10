package admin;

import common.ILoggedInPresenter;
import javafx.event.ActionEvent;
import model.ScheduleEntry;
import java.util.List;

public interface IRemoveEventsPresenter extends ILoggedInPresenter {
    void removeButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status);
    List<ScheduleEntry> getEvents();
    void displayEvents(List<ScheduleEntry> schedule);
    void displayEventDetails(ScheduleEntry event);
}
