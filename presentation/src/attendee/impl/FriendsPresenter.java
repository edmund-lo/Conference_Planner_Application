package attendee.impl;

import adapter.ScheduleAdapter;
import adapter.UserAdapter;
import attendee.IFriendsPresenter;
import attendee.IFriendsView;
import common.UserAccountHolder;
import controllers.AttendeeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.ScheduleEntry;
import model.User;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;
import util.TextResultUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Presenter class for friends functionality screen
 */
public class FriendsPresenter implements IFriendsPresenter {
    private final IFriendsView view;
    private final AttendeeController ac;
    private User selectedFriend;
    private User selectedUser;
    private User selectedPending;

    /**
     * Initialises a FriendsPresenter object with given view and new AttendeeController,
     * gets and sets current session's user information
     * @param view IFriendsView interface implementation
     */
    public FriendsPresenter(IFriendsView view) {
        this.view = view;
        getUserData();
        this.ac = new AttendeeController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs remove friend button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void removeFriendButtonAction(ActionEvent actionEvent) {
        clearResultText(1);

        JSONObject responseJson = ac.removeFriend(selectedFriend.getUsername());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 1);
        init();
    }

    /**
     * Performs rescind friend request button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void removeRequestButtonAction(ActionEvent actionEvent) {
        clearResultText(2);

        JSONObject responseJson = ac.removeFriendRequest(selectedUser.getUsername());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 1);
        init();
    }

    /**
     * Performs send friend request button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void addFriendButtonAction(ActionEvent actionEvent) {
        clearResultText(2);

        JSONObject responseJson = ac.sendFriendRequest(selectedUser.getUsername());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 2);
        init();
    }

    /**
     * Performs accept friend request button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void acceptButtonAction(ActionEvent actionEvent) {
        clearResultText(3);

        JSONObject responseJson = ac.addFriend(selectedPending.getUsername());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 1);
        init();
    }

    /**
     * Performs decline friend request button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void declineButtonAction(ActionEvent actionEvent) {
        clearResultText(3);

        JSONObject responseJson = ac.declineRequest(selectedPending.getUsername());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 1);
        init();
    }

    /**
     * Gets list of User entities based on type and adapts them to User models
     * @param type String object representing which type of Users to get
     * @return List of User models
     */
    @Override
    public List<User> getUsers(String type) {
        JSONObject responseJson = new JSONObject();
        switch (type) {
            case "nonFriends":
                responseJson = ac.getNonFriends();
                break;
            case "friends":
                responseJson = ac.getFriends();
                break;
            case "sent":
                responseJson = ac.getSentRequests();
                break;
            case "received":
                responseJson = ac.getFriendRequests();
                break;
        }
        return UserAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
    }

    /**
     * Gets common Event entities between yourself and another user and adapts them to ScheduleEntry models
     * @param username String object representing other user's username
     * @return List of ScheduleEntry models
     */
    @Override
    public List<ScheduleEntry> getCommonEvents(String username) {
        JSONObject responseJson = ac.getCommonEvents(username);
        return ScheduleAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
    }

    /**
     * Displays a list of User models in the TableView corresponding to type along with setting listeners
     * @param userList List of User models
     * @param type String object representing which TableView to display in
     */
    @Override
    public void displayUserList(List<User> userList, String type) {
        ObservableList<User> observableUsers = FXCollections.observableArrayList(userList);
        switch (type) {
            case "friends":
                this.view.getFirstNameFriendColumn().setCellValueFactory(new PropertyValueFactory<>("firstName"));
                this.view.getLastNameFriendColumn().setCellValueFactory(new PropertyValueFactory<>("lastName"));
                this.view.getUsernameFriendColumn().setCellValueFactory(new PropertyValueFactory<>("username"));
                this.view.getUserTypeFriendColumn().setCellValueFactory(new PropertyValueFactory<>("userType"));
                this.view.getFriendTable().setItems(observableUsers);
                this.view.getFriendTable().getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> displayUserDetails(newValue, type));
                break;
            case "nonFriends":
                this.view.getFirstNameUserColumn().setCellValueFactory(new PropertyValueFactory<>("firstName"));
                this.view.getLastNameUserColumn().setCellValueFactory(new PropertyValueFactory<>("lastName"));
                this.view.getUsernameUserColumn().setCellValueFactory(new PropertyValueFactory<>("username"));
                this.view.getUserTypeUserColumn().setCellValueFactory(new PropertyValueFactory<>("userType"));
                this.view.getPendingUserColumn().setCellValueFactory(param -> param.getValue().getSelected());
                this.view.getPendingUserColumn()
                        .setCellFactory(CheckBoxTableCell.forTableColumn(this.view.getPendingUserColumn()));
                this.view.getUserTable().setItems(observableUsers);
                this.view.getUserTable().getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> displayUserDetails(newValue, type));
                break;
            case "pending":
                this.view.getFirstNamePendingColumn().setCellValueFactory(new PropertyValueFactory<>("firstName"));
                this.view.getLastNamePendingColumn().setCellValueFactory(new PropertyValueFactory<>("lastName"));
                this.view.getUsernamePendingColumn().setCellValueFactory(new PropertyValueFactory<>("username"));
                this.view.getUserTypePendingColumn().setCellValueFactory(new PropertyValueFactory<>("userType"));
                this.view.getPendingTable().setItems(observableUsers);
                this.view.getPendingTable().getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> displayUserDetails(newValue, type));
                break;
        }
    }

    /**
     * Displays a User model's attributes in the tab corresponding to type
     * @param user User model that has been selected
     * @param type String object representing which tab to display in
     */
    @Override
    public void displayUserDetails(User user, String type) {
        switch (type) {
            case "friends":
                this.selectedFriend = user;
                this.view.setUsernameFriend(user.getUsername());
                this.view.setUserTypeFriend(user.getUserType());
                this.view.setFirstNameFriend(user.getFirstName());
                this.view.setLastNameFriend(user.getLastName());
                List<ScheduleEntry> commonEvents = getCommonEvents(user.getUsername());
                displayCommonEvents(commonEvents);
                break;
            case "nonFriends":
                this.selectedUser = user;
                this.view.setUsernameUser(user.getUsername());
                this.view.setUserTypeUser(user.getUserType());
                this.view.setFirstNameUser(user.getFirstName());
                this.view.setLastNameUser(user.getLastName());
                this.view.getRemoveRequestButton().setDisable(!user.isChecked());
                break;
            case "pending":
                this.selectedPending = user;
                this.view.setUsernamePending(user.getUsername());
                this.view.setUserTypePending(user.getUserType());
                this.view.setFirstNamePending(user.getFirstName());
                this.view.setLastNamePending(user.getLastName());
                break;
        }
    }

    /**
     * Displays commonEvents in the friends tab
     * @param commonEvents List of ScheduleEntry models
     */
    @Override
    public void displayCommonEvents(List<ScheduleEntry> commonEvents) {
        Text tableHeader = new Text("Room Schedule");
        TableView<ScheduleEntry> scheduleTable = new TableView<>();

        TableColumn<ScheduleEntry, String> column1 = new TableColumn<>("Event Name");
        TableColumn<ScheduleEntry, LocalDateTime> column2 = new TableColumn<>("Start");
        TableColumn<ScheduleEntry, LocalDateTime> column3 = new TableColumn<>("End");
        TableColumn<ScheduleEntry, Integer> column4 = new TableColumn<>("Remaining Spots");
        DateTimeUtil.getInstance().setScheduleDateTimeCellFactory(column2);
        DateTimeUtil.getInstance().setScheduleDateTimeCellFactory(column3);
        column1.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("start"));
        column3.setCellValueFactory(new PropertyValueFactory<>("end"));
        column3.setCellValueFactory(new PropertyValueFactory<>("remainingSpots"));
        scheduleTable.getColumns().add(column1);
        scheduleTable.getColumns().add(column2);
        scheduleTable.getColumns().add(column3);
        scheduleTable.getColumns().add(column4);

        ObservableList<ScheduleEntry> observableSchedule = FXCollections.observableArrayList(commonEvents);
        scheduleTable.setItems(observableSchedule);

        this.view.getCommonEventTableContainer().getChildren().add(tableHeader);
        this.view.getCommonEventTableContainer().getChildren().add(scheduleTable);
    }

    /**
     * Sets the result of the action given status
     * @param resultText String object describing the result
     * @param status String object representing the status of the controller method call
     * @param index int representing which Text object to set
     */
    @Override
    public void setResultText(String resultText, String status, int index) {
        this.view.setResultText(resultText, index);
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getResultTextControl(index));
    }

    /**
     * Init method which sets all the button actions, loads all friend related information
     */
    @Override
    public void init() {
        this.view.setAddFriendButtonAction(this::addFriendButtonAction);
        this.view.setRemoveFriendButtonAction(this::removeFriendButtonAction);
        this.view.setRemoveRequestButtonAction(this::removeRequestButtonAction);
        this.view.setAcceptButtonAction(this::acceptButtonAction);
        this.view.setDeclineButtonAction(this::declineButtonAction);

        List<User> pendingSent = getUsers("sent");
        for (User user : pendingSent)
            user.setChecked(true);
        List<User> nonFriends = getUsers("nonFriends");
        List<User> friends = getUsers("friends");
        List<User> pendingReceived = getUsers("received");
        pendingSent.addAll(nonFriends);
        displayUserList(pendingSent, "nonFriends");
        displayUserList(friends, "friends");
        displayUserList(pendingReceived, "pending");
    }

    /**
     * Helper method to get and set current user's information to the view class variable
     */
    @Override
    public void getUserData() {
        UserAccountHolder holder = UserAccountHolder.getInstance();
        UserAccount account = holder.getUserAccount();
        this.view.setSessionUsername(account.getUsername());
        this.view.setSessionUserType(account.getUserType());
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText(int index) {
        this.view.setResultText("", index);
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl(index));
    }
}
