package attendee.impl;

import adapter.MessageThreadAdapter;
import adapter.UserAdapter;
import attendee.IMessagingPresenter;
import attendee.IMessagingView;
import common.UserAccountHolder;
import controllers.AttendeeController;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Message;
import model.MessageThread;
import model.User;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;
import util.TextResultUtil;
import java.util.Arrays;
import java.util.List;

/**
 * Presenter class for messaging functionality screen
 */
public class MessagingPresenter implements IMessagingPresenter {
    private final IMessagingView view;
    private final AttendeeController ac;
    private ObservableList<User> users;
    private MessageThread selectedPrimaryMessageThread;
    private MessageThread selectedArchivedMessageThread;
    private MessageThread selectedTrashMessageThread;
    private CheckBox selectAll;
    private TextField senderField;
    private TextField recipientsField;
    private TextField subjectField;
    private TextArea contentArea;

    /**
     * Initialises a MessagingPresenter object with given view and new AttendeeController,
     * gets and sets current session's user information
     * @param view IMessagingView interface implementation
     */
    public MessagingPresenter(IMessagingView view) {
        this.view = view;
        getUserData();
        this.ac = new AttendeeController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs reply to message button action and displays the result, refreshes message thread
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void replyButtonAction(ActionEvent actionEvent) {
        clearResultText();

        JSONObject responseJson = ac.replyMessage(this.selectedPrimaryMessageThread.getMessageThreadId(),
                this.view.getContent());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
        if (responseJson.get("status").equals("success")) {
            JSONObject updatedJson = ac.getMessageThreadJSON(this.selectedPrimaryMessageThread.getMessageThreadId());
            MessageThread messageThread = MessageThreadAdapter.getInstance()
                    .adaptData((JSONArray) updatedJson.get("data")).get(0);
            displayMessageThread(messageThread, "primary");
        }
    }

    /**
     * Performs send new message button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void sendButtonAction(ActionEvent actionEvent) {
        JSONObject queryJson = constructMessageJson();
        JSONObject responseJson = ac.sendMessage(queryJson);
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
    }

    /**
     * Performs write new message button action and creates a new tab with message draft layout
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void newMessageButtonAction(ActionEvent actionEvent) {
        SplitPane splitPane = new SplitPane();
        splitPane.prefHeight(900);
        splitPane.prefWidth(1600);

        TableView<User> userTable = new TableView<>();
        this.selectAll = new CheckBox();
        this.selectAll.setOnAction(this::selectAllAction);
        TableColumn<User, Boolean> checkedColumn = new TableColumn<>();
        checkedColumn.setGraphic(this.selectAll);
        TableColumn<User, String> firstNameColumn = new TableColumn<>("First Name");
        TableColumn<User, String> lastNameColumn = new TableColumn<>("Last Name");
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        checkedColumn.setCellValueFactory(param -> param.getValue().getSelected());
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        checkedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkedColumn));
        checkedColumn.setEditable(true);
        userTable.getColumns().add(checkedColumn);
        userTable.getColumns().add(firstNameColumn);
        userTable.getColumns().add(lastNameColumn);
        userTable.getColumns().add(usernameColumn);

        GridPane gridPane = new GridPane();
        Label senderLabel = new Label("From:");
        this.senderField = new TextField(this.view.getSessionUsername());
        this.senderField.setEditable(false);
        Label recipientsLabel = new Label("To:");
        this.recipientsField = new TextField();
        this.recipientsField.setEditable(false);
        Label subjectLabel = new Label("Subject:");
        this.subjectField = new TextField();
        Label contentLabel = new Label("Message:");
        this.contentArea = new TextArea();
        Button sendButton = new Button("Send Message");
        sendButton.setOnAction(this::sendButtonAction);

        JSONObject responseJson = ac.getFriends();
        List<User> users = UserAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
        this.users = FXCollections.observableArrayList(
                user -> new Observable[]{user.getSelected()}
        );
        this.users.addAll(users);
        this.users.addListener((ListChangeListener<User>) change -> {
            while (change.next()) {
                if (change.wasUpdated()) updateRecipientList(this.recipientsField);
            }
        });
        userTable.setItems(this.users);
        userTable.setEditable(true);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.addRow(0, senderLabel, this.senderField);
        gridPane.addRow(1, recipientsLabel, this.recipientsField);
        gridPane.addRow(2, subjectLabel, this.subjectField);
        gridPane.addRow(3, contentLabel, this.contentArea);
        gridPane.addRow(5, sendButton);

        splitPane.getItems().add(userTable);
        splitPane.getItems().add(gridPane);
        Tab newMessageTab = new Tab("New Message Thread", splitPane);
        this.view.getTabPane().getTabs().add(newMessageTab);
        this.view.getTabPane().getSelectionModel().selectLast();
    }

    /**
     * Performs move primary message to trash button action and refreshes all inboxes
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void moveToTrashButtonAction(ActionEvent actionEvent) {
        ac.moveToTrash(this.selectedPrimaryMessageThread.getMessageThreadId());
        refreshAllInboxes();
    }

    /**
     * Performs move primary message to archived button action and refreshes all inboxes
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void moveToArchivedButtonAction(ActionEvent actionEvent) {
        ac.moveToArchive(this.selectedPrimaryMessageThread.getMessageThreadId());
        refreshAllInboxes();
    }

    /**
     * Performs move archived message to primary button action and refreshes all inboxes
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void moveToPrimaryFirstButtonAction(ActionEvent actionEvent) {
        ac.moveToPrimary(this.selectedArchivedMessageThread.getMessageThreadId());
        refreshAllInboxes();
    }

    /**
     * Performs move trash message to primary button action and refreshes all inboxes
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void moveToPrimarySecondButtonAction(ActionEvent actionEvent) {
        ac.moveToPrimary(this.selectedTrashMessageThread.getMessageThreadId());
        refreshAllInboxes();
    }

    /**
     * Performs mark unread primary message button action and refreshes all inboxes
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void unreadPrimaryButtonAction(ActionEvent actionEvent) {
        ac.changeMessageStatus(this.selectedPrimaryMessageThread.getMessageThreadId());
        refreshAllInboxes();
    }

    /**
     * Performs mark archived primary message button action and refreshes all inboxes
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void unreadArchivedButtonAction(ActionEvent actionEvent) {
        ac.changeMessageStatus(this.selectedArchivedMessageThread.getMessageThreadId());
        refreshAllInboxes();
    }

    /**
     * Performs mark trash primary message button action and refreshes all inboxes
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void unreadTrashButtonAction(ActionEvent actionEvent) {
        ac.changeMessageStatus(this.selectedTrashMessageThread.getMessageThreadId());
        refreshAllInboxes();
    }

    /**
     * Sets the result of the action given status
     * @param resultText String object describing the result
     * @param status String object representing the status of the controller method call
     */
    @Override
    public void setResultText(String resultText, String status) {
        this.view.setResultText(resultText);
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getResultTextControl());
    }

    /**
     * Gets list of MessageThread entities based on type and adapts them to MessageThread models
     * @param type String object representing which type of inbox to get
     * @return List of MessageThread models
     */
    @Override
    public List<MessageThread> getInbox(String type) {
        JSONObject responseJson = new JSONObject();
        switch (type) {
            case "primary":
                responseJson = ac.getAllPrimaryMessages();
                break;
            case "archived":
                responseJson = ac.getAllArchivedMessages();
                break;
            case "trash":
                responseJson = ac.getAllTrashMessages();
                break;
        }
        return MessageThreadAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
    }

    /**
     * Displays a list of MessageThread models in the TableView corresponding to type along with setting listeners
     * @param messageThreads List of MessageThread models
     * @param type String object representing which TableView to display in
     */
    @Override
    public void displayInbox(List<MessageThread> messageThreads, String type) {
        ObservableList<MessageThread> observableInbox = FXCollections.observableArrayList(messageThreads);

        switch (type) {
            case "primary":
                this.view.getPrimaryMembersColumn().setCellValueFactory(cdf ->
                        new SimpleStringProperty(getMessageMembers(cdf.getValue().getRecipientNames())));
                this.view.getPrimarySubjectColumn().setCellValueFactory(new PropertyValueFactory<>("subject"));
                this.view.getPrimaryUnreadColumn().setCellValueFactory(param -> param.getValue().unreadProperty());
                this.view.getPrimaryUnreadColumn()
                        .setCellFactory(CheckBoxTableCell.forTableColumn(this.view.getPrimaryUnreadColumn()));
                this.view.getPrimaryInbox().setItems(observableInbox);
                this.view.getPrimaryInbox().getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> displayMessageThread(newValue, type));
                break;
            case "archived":
                this.view.getArchivedMembersColumn().setCellValueFactory(cdf ->
                        new SimpleStringProperty(getMessageMembers(cdf.getValue().getRecipientNames())));
                this.view.getArchivedSubjectColumn().setCellValueFactory(new PropertyValueFactory<>("subject"));
                this.view.getArchivedUnreadColumn().setCellValueFactory(param -> param.getValue().unreadProperty());
                this.view.getArchivedUnreadColumn()
                        .setCellFactory(CheckBoxTableCell.forTableColumn(this.view.getArchivedUnreadColumn()));
                this.view.getArchivedInbox().setItems(observableInbox);
                this.view.getArchivedInbox().getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> displayMessageThread(newValue, type));
                break;
            case "trash":
                this.view.getTrashMembersColumn().setCellValueFactory(cdf ->
                        new SimpleStringProperty(getMessageMembers(cdf.getValue().getRecipientNames())));
                this.view.getTrashSubjectColumn().setCellValueFactory(new PropertyValueFactory<>("subject"));
                this.view.getTrashUnreadColumn().setCellValueFactory(param -> param.getValue().unreadProperty());
                this.view.getTrashUnreadColumn()
                        .setCellFactory(CheckBoxTableCell.forTableColumn(this.view.getTrashUnreadColumn()));
                this.view.getTrashInbox().setItems(observableInbox);
                this.view.getTrashInbox().getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> displayMessageThread(newValue, type));
                break;
        }
    }

    /**
     * Displays a MessageThread model's attributes in the tab corresponding to type
     * @param messageThread User model that has been selected
     * @param type String object representing which tab to display in
     */
    @Override
    public void displayMessageThread(MessageThread messageThread, String type) {
        if (messageThread == null) return;
        if (messageThread.isUnread()) ac.changeMessageStatus(messageThread.getMessageThreadId());
        switch (type) {
            case "primary":
                this.selectedPrimaryMessageThread = messageThread;
                this.view.setPrimarySender(messageThread.getSenderName());
                this.view.setPrimaryRecipientNames(messageThread.getRecipientNames());
                this.view.setPrimarySubject(messageThread.getSubject());
                displayMessageHistory(messageThread, this.view.getPrimaryThreadContainer());
                break;
            case "archived":
                this.selectedArchivedMessageThread = messageThread;
                this.view.setArchivedSender(messageThread.getSenderName());
                this.view.setArchivedRecipientNames(messageThread.getRecipientNames());
                this.view.setArchivedSubject(messageThread.getSubject());
                displayMessageHistory(messageThread, this.view.getArchivedThreadContainer());
                break;
            case "trash":
                this.selectedTrashMessageThread = messageThread;
                this.view.setTrashSender(messageThread.getSenderName());
                this.view.setTrashRecipientNames(messageThread.getRecipientNames());
                this.view.setTrashSubject(messageThread.getSubject());
                displayMessageHistory(messageThread, this.view.getTrashThreadContainer());
                break;
        }
    }

    /**
     * Init method which sets all the button actions, displays all inboxes
     */
    @Override
    public void init() {
        this.view.setMoveToArchivedButtonAction(this::moveToArchivedButtonAction);
        this.view.setMoveToTrashButtonAction(this::moveToTrashButtonAction);
        this.view.setMoveToPrimaryFirstButtonAction(this::moveToPrimaryFirstButtonAction);
        this.view.setMoveToPrimarySecondButtonAction(this::moveToPrimarySecondButtonAction);
        this.view.setUnreadPrimaryButtonAction(this::unreadPrimaryButtonAction);
        this.view.setUnreadArchivedButtonAction(this::unreadArchivedButtonAction);
        this.view.setUnreadTrashButtonAction(this::unreadTrashButtonAction);
        this.view.setReplyButtonAction(this::replyButtonAction);
        this.view.setNewMessageButtonAction(this::newMessageButtonAction);
        refreshAllInboxes();
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
     * Helper method to refresh all three inboxes
     */
    private void refreshAllInboxes() {
        List<MessageThread> primaryInbox = getInbox("primary");
        displayInbox(primaryInbox, "primary");
        List<MessageThread> archivedInbox = getInbox("archived");
        displayInbox(archivedInbox, "archived");
        List<MessageThread> trashInbox = getInbox("trash");
        displayInbox(trashInbox, "trash");
    }

    /**
     * Gets a string that represents the list of participants in the message thread
     * @param recipients List of String objects representing recipients' usernames
     * @return String object representing members' string
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
        pane.setVvalue(1D);
    }

    /**
     * Helper method that performs select all checkbox action and updates the recipient list
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    private void selectAllAction(ActionEvent actionEvent) {
        boolean checked = this.selectAll.isSelected();
        for (User u : this.users)
            u.setChecked(checked);
        updateRecipientList(this.recipientsField);
    }

    /**
     * Updates recipientField with the checked rows in the user table
     * @param recipientField JavaFX TextField object where recipient list is shown
     */
    private void updateRecipientList(TextField recipientField) {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (User u : this.users) {
            if (u.isChecked()) {
                sb.append(prefix);
                prefix = ", ";
                sb.append(u.getUsername());
            }
        }
        recipientField.setText(sb.toString());
    }

    /**
     * Helper method to encode a JSONObject for a message form
     * @return JSONObject object representing a message form
     */
    @SuppressWarnings("unchecked")
    private JSONObject constructMessageJson() {
        JSONObject queryJson = new JSONObject();
        JSONArray recipients = new JSONArray();
        recipients.addAll(Arrays.asList(this.recipientsField.getText().split(", ")));
        queryJson.put("sender", this.senderField.getText());
        queryJson.put("recipients", recipients);
        queryJson.put("subject", this.subjectField.getText());
        queryJson.put("content", this.contentArea.getText());
        return queryJson;
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText() {
        this.view.setResultText("");
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl());
    }
}
