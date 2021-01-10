package organizer;

import common.ILoggedInPresenter;
import javafx.event.ActionEvent;
import model.ScheduleEntry;
import java.util.List;

public interface IScheduleSpeakerPresenter extends ILoggedInPresenter {
    void scheduleSpeakerButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status);
    List<ScheduleEntry> getAllEvents();
    void displayEvents(List<ScheduleEntry> schedule);
    void displayEventDetails(ScheduleEntry event);
    void displayAvailableSpeakers(ScheduleEntry event);
}
