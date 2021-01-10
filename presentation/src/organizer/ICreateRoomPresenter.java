package organizer;

import common.ILoggedInPresenter;
import javafx.event.ActionEvent;

public interface ICreateRoomPresenter extends ILoggedInPresenter {
    void createRoomButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status);
    void observeAmenities();
}
