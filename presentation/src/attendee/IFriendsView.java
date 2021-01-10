package attendee;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.User;

public interface IFriendsView extends ILoggedInView {
    TableView<User> getFriendTable();
    TableColumn<User, String> getFirstNameFriendColumn();
    TableColumn<User, String> getLastNameFriendColumn();
    TableColumn<User, String> getUsernameFriendColumn();
    TableColumn<User, String> getUserTypeFriendColumn();
    TableView<User> getUserTable();
    TableColumn<User, String> getFirstNameUserColumn();
    TableColumn<User, String> getLastNameUserColumn();
    TableColumn<User, String> getUsernameUserColumn();
    TableColumn<User, String> getUserTypeUserColumn();
    TableColumn<User, Boolean> getPendingUserColumn();
    TableView<User> getPendingTable();
    TableColumn<User, String> getFirstNamePendingColumn();
    TableColumn<User, String> getLastNamePendingColumn();
    TableColumn<User, String> getUsernamePendingColumn();
    TableColumn<User, String> getUserTypePendingColumn();
    Text getResultTextControl(int index);
    VBox getCommonEventTableContainer();
    Button getRemoveRequestButton();

    void setUsernameFriend(String username);
    void setFirstNameFriend(String firstName);
    void setLastNameFriend(String lastName);
    void setUserTypeFriend(String userType);
    void setUsernameUser(String username);
    void setFirstNameUser(String firstName);
    void setLastNameUser(String lastName);
    void setUserTypeUser(String userType);
    void setUsernamePending(String username);
    void setFirstNamePending(String firstName);
    void setLastNamePending(String lastName);
    void setUserTypePending(String userType);
    void setResultText(String resultText, int index);

    void setAddFriendButtonAction(EventHandler<ActionEvent> eventHandler);
    void setRemoveRequestButtonAction(EventHandler<ActionEvent> eventHandler);
    void setRemoveFriendButtonAction(EventHandler<ActionEvent> eventHandler);
    void setDeclineButtonAction(EventHandler<ActionEvent> eventHandler);
    void setAcceptButtonAction(EventHandler<ActionEvent> eventHandler);
}
