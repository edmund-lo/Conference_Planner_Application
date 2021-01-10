package toolbar.impl;

import common.UserAccountHolder;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import model.UserAccount;
import toolbar.IToolbarPresenter;
import toolbar.IToolbarView;
import util.ComponentFactory;

/**
 * Presenter class for the toolbar
 */
public class ToolbarPresenter implements IToolbarPresenter {
    private final IToolbarView view;

    /**
     * Initialises a ToolbarPresenter object with given view
     * @param view IToolbarView interface implementation
     */
    public ToolbarPresenter(IToolbarView view) {
        this.view = view;
        getUserData();
        init();
    }

    //region Button Actions that redirect the user to specified scene
    @Override
    public void homeButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(actionEvent, "home.fxml");
    }

    @Override
    public void viewScheduleButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "viewSchedule.fxml");
    }

    @Override
    public void viewEventsButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "viewEvents.fxml");
    }

    @Override
    public void messagingButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "messaging.fxml");
    }

    @Override
    public void friendsButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "friends.fxml");
    }

    @Override
    public void createAccountButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "createAccount.fxml");
    }

    @Override
    public void createRoomButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "createRoom.fxml");
    }

    @Override
    public void createEventButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "createEvent.fxml");
    }

    @Override
    public void scheduleSpeakerButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "scheduleSpeaker.fxml");
    }

    @Override
    public void cancelEventButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "rescheduleCancelEvent.fxml");
    }

    @Override
    public void messageSpeakersButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "messageSpeakers.fxml");
    }

    @Override
    public void messageAttendeesButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "messageAttendees.fxml");
    }

    @Override
    public void speakerEventsButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(actionEvent, "speakerEvents.fxml");
    }

    @Override
    public void unlockAccountsButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "unlockAccounts.fxml");
    }

    @Override
    public void setVipButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "setVip.fxml");
    }

    @Override
    public void deleteMessagesButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "deleteMessages.fxml");
    }

    @Override
    public void removeEventsButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedInComponent(this.view.getToolBar(), "removeEvents.fxml");
    }
    //endregion

    /**
     * Performs log out button action and removes user account information for the static holder
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void logoutButtonAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            UserAccountHolder holder = UserAccountHolder.getInstance();
            holder.setUserAccount(null);
            ComponentFactory.getInstance().createLoggedOutComponent(actionEvent, "login.fxml");
        }

    }

    /**
     * Removes access to button options based on user type
     */
    @Override
    public void filterAccess() {
        switch (this.view.getSessionUserType()) {
            case "Organizer":
                this.view.getAdminGroup().getChildren().clear();
                this.view.getSpeakerGroup().getChildren().clear();
                break;
            case "Speaker":
                this.view.getAdminGroup().getChildren().clear();
                this.view.getOrganizerGroup().getChildren().clear();
                break;
            case "Administrator":
                this.view.getOrganizerGroup().getChildren().clear();
                this.view.getSpeakerGroup().getChildren().clear();
                break;
            default:
                this.view.getAdminGroup().getChildren().clear();
                this.view.getSpeakerGroup().getChildren().clear();
                this.view.getOrganizerGroup().getChildren().clear();
                break;
        }
    }

    /**
     * Init method which limits access and sets all the button actions
     */
    @Override
    public void init() {
        filterAccess();

        this.view.setHomeButtonAction(this::homeButtonAction);
        this.view.setViewScheduleButtonAction(this::viewScheduleButtonAction);
        this.view.setViewEventsButtonAction(this::viewEventsButtonAction);
        this.view.setMessagingButtonAction(this::messagingButtonAction);
        this.view.setFriendsButtonAction(this::friendsButtonAction);
        this.view.setCreateRoomButtonAction(this::createRoomButtonAction);
        this.view.setCreateEventButtonAction(this::createEventButtonAction);
        this.view.setScheduleSpeakerButtonAction(this::scheduleSpeakerButtonAction);
        this.view.setCancelEventButtonAction(this::cancelEventButtonAction);
        this.view.setMessageSpeakersButtonAction(this::messageSpeakersButtonAction);
        this.view.setMessageAttendeesButtonAction(this::messageAttendeesButtonAction);
        this.view.setSpeakerEventsButtonAction(this::speakerEventsButtonAction);
        this.view.setCreateAccountButtonAction(this::createAccountButtonAction);
        this.view.setUnlockAccountsButtonAction(this::unlockAccountsButtonAction);
        this.view.setSetVipButtonAction(this::setVipButtonAction);
        this.view.setDeleteMessagesButtonAction(this::deleteMessagesButtonAction);
        this.view.setRemoveEventsButtonAction(this::removeEventsButtonAction);
        this.view.setLogoutButtonAction(this::logoutButtonAction);
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
}
