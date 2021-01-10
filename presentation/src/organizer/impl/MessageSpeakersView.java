package organizer.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.User;
import organizer.IMessageUsersView;

/**
 * View class for messaging speakers scene
 */
public class MessageSpeakersView implements IMessageUsersView {
    @FXML
    private TableView<User> userTable;
    @FXML
    private CheckBox selectAll;
    @FXML
    private TableColumn<User, Boolean> checkedColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TextField sender;
    @FXML
    private TextField recipients;
    @FXML
    private TextField subject;
    @FXML
    private TextArea content;
    @FXML
    private Text resultText;

    @FXML
    public void executeAddSend(ActionEvent event) {
        if (createAccountButtonAction != null) createAccountButtonAction.handle(event);
    }
    @FXML
    public void executeAddSelectAll(ActionEvent event) {
        if (selectAllAction != null) selectAllAction.handle(event);
    }
    @FXML
    public void initialize() {
        new MessageSpeakersPresenter(this);
    }

    private EventHandler<ActionEvent> createAccountButtonAction;
    private EventHandler<ActionEvent> selectAllAction;
    private String sessionUsername;
    private String sessionUserType;

    @Override
    public TableView<User> getUserTable() {
        return this.userTable;
    }

    @Override
    public TableColumn<User, Boolean> getCheckedColumn() {
        return this.checkedColumn;
    }

    @Override
    public TableColumn<User, String> getFirstNameColumn() {
        return this.firstNameColumn;
    }

    @Override
    public TableColumn<User, String> getLastNameColumn() {
        return this.lastNameColumn;
    }

    @Override
    public TableColumn<User, String> getUsernameColumn() {
        return this.usernameColumn;
    }

    @Override
    public CheckBox getSelectAll() {
        return this.selectAll;
    }

    @Override
    public String getSender() {
        return this.sender.getText();
    }

    @Override
    public void setSender(String sender) {
        this.sender.setText(sender);
    }

    @Override
    public String getRecipients() {
        return this.recipients.getText();
    }

    @Override
    public void setRecipients(String recipients) {
        this.recipients.setText(recipients);
    }

    @Override
    public String getContent() {
        return this.content.getText();
    }

    @Override
    public void setContent(String content) {
        this.content.setText(content);
    }

    @Override
    public String getSubject() {
        return this.subject.getText();
    }

    @Override
    public void setSubject(String subject) {
        this.subject.setText(subject);
    }

    @Override
    public void setResultText(String resultText) {
        this.resultText.setText(resultText);
    }

    @Override
    public TextField getRecipientsField() {
        return this.recipients;
    }

    @Override
    public TextArea getContentArea() {
        return this.content;
    }

    @Override
    public TextField getSubjectField() {
        return this.subject;
    }

    @Override
    public void setSendButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.createAccountButtonAction = eventHandler;
    }

    @Override
    public void setSelectAllAction(EventHandler<ActionEvent> eventHandler) {
        this.selectAllAction = eventHandler;
    }

    @Override
    public Text getResultTextControl() {
        return this.resultText;
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
