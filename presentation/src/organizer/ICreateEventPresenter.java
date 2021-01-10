package organizer;

import common.ILoggedInPresenter;
import javafx.event.ActionEvent;
import model.ScheduleEntry;

import java.util.List;

public interface ICreateEventPresenter extends ILoggedInPresenter {
    void createEventButtonAction(ActionEvent actionEvent);
    void previousFirstButtonAction(ActionEvent actionEvent);
    void previousSecondButtonAction(ActionEvent actionEvent);
    void findRoomsButtonAction(ActionEvent actionEvent);
    void previewRoomButtonAction(ActionEvent actionEvent);
    void summaryButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status, int index);
    void observeAmenities();
    List<ScheduleEntry> getRoomSchedule();
    void displayRoomSchedule(List<ScheduleEntry> schedule);
}
