package admin.impl;

import adapter.MessageThreadAdapter;
import admin.IDeleteMessagesPresenter;
import admin.IDeleteMessagesView;
import common.UserAccountHolder;
import controllers.AdminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Message;
import model.MessageThread;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;

import java.util.List;

/**
 * Presenter class for delete message threads screen
 */
public class DeleteMessagesPresenter implements IDeleteMessagesPresenter {
    private final IDeleteMessagesView view;
    private final AdminController ac;
    private MessageThread selectedMessageThread;

    /**
     * Initialises a DeleteMessagesPresenter object with given view and new AdminController,
     * gets and sets current session's user information
     * @param view IDeleteMessagesView interface implementation
     */
    public DeleteMessagesPresenter(IDeleteMessagesView view) {
        this.view = view;
        getUserData();
        this.ac = new AdminController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs delete message thread button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void deleteButtonAction(ActionEvent actionEvent) {
        JSONObject responseJson = ac.deleteMessageThread(this.selectedMessageThread.getMessageThreadId());
        if (String.valueOf(responseJson.get("status")).equals("success")) init();
    }

    /**
     * Gets all MessageThread entities stored in the database and adapts them to MessageThread models
     * @return List of MessageThread models
     */
    @Override
    public List<MessageThread> getAllMessages() {
        JSONObject responseJson = ac.getAllMessageThreads();
        return MessageThreadAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
    }

    /**
     * Displays messageThreads in the TableView and adds a listener
     * @param messageThreads List of MessageThread models
     */
    @Override
    public void displayInbox(List<MessageThread> messageThreads) {
        ObservableList<MessageThread> observableInbox = FXCollections.observableArrayList(messageThreads);
        DateTimeUtil.getInstance().setMessageDateTimeCellFactory(this.view.getDeleteMessageTimeColumn());
        this.view.getDeleteMembersColumn().setCellValueFactory(cdf ->
                new SimpleStringProperty(getMessageMembers(cdf.getValue().getRecipientNames())));
        this.view.getDeleteSubjectColumn().setCellValueFactory(new PropertyValueFactory<>("subject"));
        this.view.getDeleteMessageTimeColumn().setCellValueFactory(new PropertyValueFactory<>("messageTime"));
        this.view.getDeleteInbox().setItems(observableInbox);
        this.view.getDeleteInbox().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayMessageThread(newValue));
    }

    /**
     * Displays messageThread in the ScrollPane
     * @param messageThread Selected MessageThread model
     */
    @Override
    public void displayMessageThread(MessageThread messageThread) {
        if (messageThread == null) return;
        this.selectedMessageThread = messageThread;
        this.view.setDeleteSender(messageThread.getSenderName());
        this.view.setDeleteRecipients(messageThread.getRecipientNames());
        this.view.setDeleteSubject(messageThread.getSubject());
        displayMessageHistory(messageThread, this.view.getDeleteThreadContainer());
    }

    /**
     * Init method which sets all the button actions, gets and displays all message threads
     */
    @Override
    public void init() {
        this.view.setDeleteButtonAction(this::deleteButtonAction);
        List<MessageThread> deleteInbox = getAllMessages();
        displayInbox(deleteInbox);
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
     * Helper method to display messageThread's messageHistory attribute in the pane
     * @param messageThread MessageThread model
     * @param pane JavaFX ScrollPane object
     */
    private void displayMessageHistory(MessageThread messageThread, ScrollPane pane) {
        pane.setContent(null);
        VBox messageTiles = new VBox();
        for (Message message : messageThread.getMessageHistory()) {
            String messageString = message.getSenderName() + ": " + message.getContent() + " (" +
                    DateTimeUtil.getInstance().format(message.getMessageTime()) + ")";
            Text messageText = new Text(messageString);
            HBox tile = new HBox(messageText);
            messageTiles.getChildren().add(tile);
        }
        pane.setContent(messageTiles);
    }

    /**
     * Helper method to convert a list of recipient usernames into one concatenated string
     * @param recipients List of String object representing message thread's recipients
     * @return String object of concatenated recipients' usernames
     */
    private String getMessageMembers(List<String> recipients) {
        StringBuilder sb = new StringBuilder();
        for (String name : recipients) {
            sb.append(name);
            sb.append(", ");
        }
        sb.append("me");
        return sb.toString();
    }
}
