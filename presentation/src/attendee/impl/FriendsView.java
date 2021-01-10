package attendee.impl;

import attendee.IFriendsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.User;

/**
 * View class for friends functionality screen
 */
public class FriendsView implements IFriendsView {
    @FXML
    private TableView<User> friendTable;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableView<User> pendingTable;
    @FXML
    private TableColumn<User, String> firstNameFriendColumn;
    @FXML
    private TableColumn<User, String> lastNameFriendColumn;
    @FXML
    private TableColumn<User, String> usernameFriendColumn;
    @FXML
    private TableColumn<User, String> userTypeFriendColumn;
    @FXML
    private TableColumn<User, String> firstNameUserColumn;
    @FXML
    private TableColumn<User, String> lastNameUserColumn;
    @FXML
    private TableColumn<User, String> usernameUserColumn;
    @FXML
    private TableColumn<User, String> userTypeUserColumn;
    @FXML
    private TableColumn<User, Boolean> pendingUserColumn;
    @FXML
    private TableColumn<User, String> firstNamePendingColumn;
    @FXML
    private TableColumn<User, String> lastNamePendingColumn;
    @FXML
    private TableColumn<User, String> usernamePendingColumn;
    @FXML
    private TableColumn<User, String> userTypePendingColumn;
    @FXML
    private Text usernameFriend;
    @FXML
    private Text firstNameFriend;
    @FXML
    private Text lastNameFriend;
    @FXML
    private Text userTypeFriend;
    @FXML
    private Text usernameUser;
    @FXML
    private Text firstNameUser;
    @FXML
    private Text lastNameUser;
    @FXML
    private Text userTypeUser;
    @FXML
    private Text usernamePending;
    @FXML
    private Text firstNamePending;
    @FXML
    private Text lastNamePending;
    @FXML
    private Text userTypePending;
    @FXML
    private Text resultText1;
    @FXML
    private Text resultText2;
    @FXML
    private Text resultText3;
    @FXML
    private VBox commonEventTableContainer;
    @FXML
    private Button removeRequestButton;

    @FXML
    public void executeAddRemoveFriend(ActionEvent event) {
        if (removeFriendButtonAction != null) removeFriendButtonAction.handle(event);
    }
    @FXML
    public void executeAddRemoveRequest(ActionEvent event) {
        if (removeRequestButtonAction != null) removeRequestButtonAction.handle(event);
    }
    @FXML
    public void executeAddAddFriend(ActionEvent event) {
        if (addFriendButtonAction != null) addFriendButtonAction.handle(event);
    }
    @FXML
    public void executeAddAccept(ActionEvent event) {
        if (acceptButtonAction != null) acceptButtonAction.handle(event);
    }
    @FXML
    public void executeAddDecline(ActionEvent event) {
        if (declineButtonAction != null) declineButtonAction.handle(event);
    }
    @FXML
    public void initialize() {
        new FriendsPresenter(this);
    }

    private EventHandler<ActionEvent> removeFriendButtonAction;
    private EventHandler<ActionEvent> removeRequestButtonAction;
    private EventHandler<ActionEvent> addFriendButtonAction;
    private EventHandler<ActionEvent> acceptButtonAction;
    private EventHandler<ActionEvent> declineButtonAction;
    private String sessionUsername;
    private String sessionUserType;

    @Override
    public TableView<User> getFriendTable() {
        return this.friendTable;
    }

    @Override
    public TableColumn<User, String> getFirstNameFriendColumn() {
        return this.firstNameFriendColumn;
    }

    @Override
    public TableColumn<User, String> getLastNameFriendColumn() {
        return this.lastNameFriendColumn;
    }

    @Override
    public TableColumn<User, String> getUsernameFriendColumn() {
        return this.usernameFriendColumn;
    }

    @Override
    public TableColumn<User, String> getUserTypeFriendColumn() {
        return this.userTypeFriendColumn;
    }

    @Override
    public TableView<User> getUserTable() {
        return this.userTable;
    }

    @Override
    public TableColumn<User, String> getFirstNameUserColumn() {
        return this.firstNameUserColumn;
    }

    @Override
    public TableColumn<User, String> getLastNameUserColumn() {
        return this.lastNameUserColumn;
    }

    @Override
    public TableColumn<User, String> getUsernameUserColumn() {
        return this.usernameUserColumn;
    }

    @Override
    public TableColumn<User, String> getUserTypeUserColumn() {
        return this.userTypeUserColumn;
    }

    @Override
    public TableColumn<User, Boolean> getPendingUserColumn() {
        return this.pendingUserColumn;
    }

    @Override
    public TableView<User> getPendingTable() {
        return this.pendingTable;
    }

    @Override
    public TableColumn<User, String> getFirstNamePendingColumn() {
        return this.firstNamePendingColumn;
    }

    @Override
    public TableColumn<User, String> getLastNamePendingColumn() {
        return this.lastNamePendingColumn;
    }

    @Override
    public TableColumn<User, String> getUsernamePendingColumn() {
        return this.usernamePendingColumn;
    }

    @Override
    public TableColumn<User, String> getUserTypePendingColumn() {
        return this.userTypePendingColumn;
    }

    @Override
    public Text getResultTextControl(int index) {
        Text resultText = new Text();
        if (index == 1) resultText = this.resultText1;
        if (index == 2) resultText = this.resultText2;
        if (index == 3) resultText = this.resultText3;
        return resultText;
    }

    @Override
    public VBox getCommonEventTableContainer() {
        return this.commonEventTableContainer;
    }

    @Override
    public Button getRemoveRequestButton() {
        return this.removeRequestButton;
    }

    @Override
    public void setUsernameFriend(String username) {
        this.usernameFriend.setText(username);
    }

    @Override
    public void setFirstNameFriend(String firstName) {
        this.firstNameFriend.setText(firstName);
    }

    @Override
    public void setLastNameFriend(String lastName) {
        this.lastNameFriend.setText(lastName);
    }

    @Override
    public void setUserTypeFriend(String userType) {
        this.userTypeFriend.setText(userType);
    }

    @Override
    public void setUsernameUser(String username) {
        this.usernameUser.setText(username);
    }

    @Override
    public void setFirstNameUser(String firstName) {
        this.firstNameUser.setText(firstName);
    }

    @Override
    public void setLastNameUser(String lastName) {
        this.lastNameUser.setText(lastName);
    }

    @Override
    public void setUserTypeUser(String userType) {
        this.userTypeUser.setText(userType);
    }

    @Override
    public void setUsernamePending(String username) {
        this.usernamePending.setText(username);
    }

    @Override
    public void setFirstNamePending(String firstName) {
        this.firstNamePending.setText(firstName);
    }

    @Override
    public void setLastNamePending(String lastName) {
        this.lastNamePending.setText(lastName);
    }

    @Override
    public void setUserTypePending(String userType) {
        this.userTypePending.setText(userType);
    }

    @Override
    public void setResultText(String resultText, int index) {
        getResultTextControl(index).setText(resultText);
    }

    @Override
    public void setAddFriendButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.addFriendButtonAction = eventHandler;
    }

    @Override
    public void setRemoveRequestButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.removeRequestButtonAction = eventHandler;
    }

    @Override
    public void setRemoveFriendButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.removeFriendButtonAction = eventHandler;
    }

    @Override
    public void setDeclineButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.declineButtonAction = eventHandler;
    }

    @Override
    public void setAcceptButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.acceptButtonAction = eventHandler;
    }

    @Override
    public String getSessionUsername() {
        return this.sessionUsername;
    }

    @Override
    public void setSessionUsername(String username) {
        this.sessionUsername = username;
    }

    @Override
    public String getSessionUserType() {
        return this.sessionUserType;
    }

    @Override
    public void setSessionUserType(String userType) {
        this.sessionUserType = userType;
    }

    @Override
    public Text getResultTextControl() {
        return null;
    }
}
