package toolbar;

import common.ILoggedInPresenter;
import javafx.event.ActionEvent;

public interface IToolbarPresenter extends ILoggedInPresenter {
    void homeButtonAction(ActionEvent actionEvent);
    void viewScheduleButtonAction(ActionEvent actionEvent);
    void viewEventsButtonAction(ActionEvent actionEvent);
    void messagingButtonAction(ActionEvent actionEvent);
    void friendsButtonAction(ActionEvent actionEvent);
    void createRoomButtonAction(ActionEvent actionEvent);
    void createEventButtonAction(ActionEvent actionEvent);
    void scheduleSpeakerButtonAction(ActionEvent actionEvent);
    void cancelEventButtonAction(ActionEvent actionEvent);
    void messageSpeakersButtonAction(ActionEvent actionEvent);
    void messageAttendeesButtonAction(ActionEvent actionEvent);
    void speakerEventsButtonAction(ActionEvent actionEvent);
    void createAccountButtonAction(ActionEvent actionEvent);
    void unlockAccountsButtonAction(ActionEvent actionEvent);
    void setVipButtonAction(ActionEvent actionEvent);
    void deleteMessagesButtonAction(ActionEvent actionEvent);
    void removeEventsButtonAction(ActionEvent actionEvent);
    void logoutButtonAction(ActionEvent actionEvent);

    void filterAccess();
}
