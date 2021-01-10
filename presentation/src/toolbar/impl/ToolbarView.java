package toolbar.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import toolbar.IToolbarView;

/**
 * View class for the toolbar
 */
public class ToolbarView implements IToolbarView {
    @FXML
    private HBox organizerGroup;
    @FXML
    private HBox speakerGroup;
    @FXML
    private HBox adminGroup;
    @FXML
    private ToolBar toolbar;

    //region Adds button handlers to JavaFX buttons
    @FXML
    public void executeAddHome(ActionEvent event) {
        if (homeButtonAction != null) homeButtonAction.handle(event);
    }
    @FXML
    public void executeAddViewSchedule(ActionEvent event) {
        if (viewScheduleButtonAction != null) viewScheduleButtonAction.handle(event);
    }
    @FXML
    public void executeAddViewEvents(ActionEvent event) {
        if (viewEventsButtonAction != null) viewEventsButtonAction.handle(event);
    }
    @FXML
    public void executeAddMessaging(ActionEvent event) {
        if (messagingButtonAction != null) messagingButtonAction.handle(event);
    }
    @FXML
    public void executeAddFriends(ActionEvent event) {
        if (friendsButtonAction != null) friendsButtonAction.handle(event);
    }
    @FXML
    public void executeAddCreateRoom(ActionEvent event) {
        if (createRoomButtonAction != null) createRoomButtonAction.handle(event);
    }
    @FXML
    public void executeAddCreateEvent(ActionEvent event) {
        if (createEventButtonAction != null) createEventButtonAction.handle(event);
    }
    @FXML
    public void executeAddScheduleSpeaker(ActionEvent event) {
        if (scheduleSpeakerButtonAction != null) scheduleSpeakerButtonAction.handle(event);
    }
    @FXML
    public void executeAddCancelEvent(ActionEvent event) {
        if (rescheduleCancelEventButtonAction != null) rescheduleCancelEventButtonAction.handle(event);
    }
    @FXML
    public void executeAddMessageSpeakers(ActionEvent event) {
        if (messageSpeakersButtonAction != null) messageSpeakersButtonAction.handle(event);
    }
    @FXML
    public void executeAddMessageAttendees(ActionEvent event) {
        if (messageAttendeesButtonAction != null) messageAttendeesButtonAction.handle(event);
    }
    @FXML
    public void executeAddSpeakerEvents(ActionEvent event) {
        if (speakerEventsButtonAction != null) speakerEventsButtonAction.handle(event);
    }
    @FXML
    public void executeAddUnlockAccounts(ActionEvent event) {
        if (unlockAccountsButtonAction != null) unlockAccountsButtonAction.handle(event);
    }
    @FXML
    public void executeAddCreateAccount(ActionEvent event) {
        if (createAccountButtonAction != null) createAccountButtonAction.handle(event);
    }
    @FXML
    public void executeAddSetVip(ActionEvent event) {
        if (setVipButtonAction != null) setVipButtonAction.handle(event);
    }
    @FXML
    public void executeAddDeleteMessages(ActionEvent event) {
        if (deleteMessagesButtonAction != null) deleteMessagesButtonAction.handle(event);
    }
    @FXML
    public void executeAddRemoveEvents(ActionEvent event) {
        if (removeEventsButtonAction != null) removeEventsButtonAction.handle(event);
    }
    @FXML
    public void executeAddLogout(ActionEvent event) {
        if (logoutButtonAction != null) logoutButtonAction.handle(event);
    }
    //endregion
    @FXML
    public void initialize() {
        new ToolbarPresenter(this);
    }

    private EventHandler<ActionEvent> homeButtonAction;
    private EventHandler<ActionEvent> viewScheduleButtonAction;
    private EventHandler<ActionEvent> viewEventsButtonAction;
    private EventHandler<ActionEvent> messagingButtonAction;
    private EventHandler<ActionEvent> friendsButtonAction;
    private EventHandler<ActionEvent> createRoomButtonAction;
    private EventHandler<ActionEvent> createEventButtonAction;
    private EventHandler<ActionEvent> scheduleSpeakerButtonAction;
    private EventHandler<ActionEvent> rescheduleCancelEventButtonAction;
    private EventHandler<ActionEvent> messageSpeakersButtonAction;
    private EventHandler<ActionEvent> messageAttendeesButtonAction;
    private EventHandler<ActionEvent> speakerEventsButtonAction;
    private EventHandler<ActionEvent> createAccountButtonAction;
    private EventHandler<ActionEvent> unlockAccountsButtonAction;
    private EventHandler<ActionEvent> setVipButtonAction;
    private EventHandler<ActionEvent> deleteMessagesButtonAction;
    private EventHandler<ActionEvent> removeEventsButtonAction;
    private EventHandler<ActionEvent> logoutButtonAction;
    private String sessionUsername;
    private String sessionUserType;

    @Override
    public HBox getOrganizerGroup() {
        return this.organizerGroup;
    }

    @Override
    public HBox getSpeakerGroup() {
        return this.speakerGroup;
    }

    @Override
    public HBox getAdminGroup() {
        return this.adminGroup;
    }

    @Override
    public ToolBar getToolBar() {
        return this.toolbar;
    }

    @Override
    public void setHomeButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.homeButtonAction = eventHandler;
    }

    @Override
    public void setViewScheduleButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.viewScheduleButtonAction = eventHandler;
    }

    @Override
    public void setViewEventsButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.viewEventsButtonAction = eventHandler;
    }

    @Override
    public void setMessagingButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.messagingButtonAction = eventHandler;
    }

    @Override
    public void setFriendsButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.friendsButtonAction = eventHandler;
    }

    @Override
    public void setCreateAccountButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.createAccountButtonAction = eventHandler;
    }

    @Override
    public void setCreateRoomButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.createRoomButtonAction = eventHandler;
    }

    @Override
    public void setCreateEventButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.createEventButtonAction = eventHandler;
    }

    @Override
    public void setScheduleSpeakerButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.scheduleSpeakerButtonAction = eventHandler;
    }

    @Override
    public void setCancelEventButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.rescheduleCancelEventButtonAction = eventHandler;
    }

    @Override
    public void setMessageSpeakersButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.messageSpeakersButtonAction = eventHandler;
    }

    @Override
    public void setMessageAttendeesButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.messageAttendeesButtonAction = eventHandler;
    }

    @Override
    public void setSpeakerEventsButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.speakerEventsButtonAction = eventHandler;
    }

    @Override
    public void setUnlockAccountsButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.unlockAccountsButtonAction = eventHandler;
    }

    @Override
    public void setSetVipButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.setVipButtonAction = eventHandler;
    }

    @Override
    public void setDeleteMessagesButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.deleteMessagesButtonAction = eventHandler;
    }

    @Override
    public void setRemoveEventsButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.removeEventsButtonAction = eventHandler;
    }

    @Override
    public void setLogoutButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.logoutButtonAction = eventHandler;
    }

    @Override
    public Text getResultTextControl() {
        return null;
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
}
