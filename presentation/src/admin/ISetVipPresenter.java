package admin;

import common.ILoggedInPresenter;
import javafx.event.ActionEvent;
import model.User;
import java.util.List;

public interface ISetVipPresenter extends ILoggedInPresenter {
    void changeVipButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status);
    List<User> getAttendeeUsers();
    void displayUsers(List<User> attendees);
    void displayUserDetails(User attendee);
}
