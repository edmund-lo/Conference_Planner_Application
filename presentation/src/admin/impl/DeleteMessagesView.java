package admin.impl;

import admin.IDeleteMessagesView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import model.MessageThread;

import java.time.LocalDateTime;
import java.util.List;

/**
 * View class for delete message threads screen
 */
public class DeleteMessagesView implements IDeleteMessagesView {
    @FXML
    private TableView<MessageThread> deleteInbox;
    @FXML
    private TableColumn<MessageThread, String> deleteMembersColumn;
    @FXML
    private TableColumn<MessageThread, String> deleteSubjectColumn;
    @FXML
    private TableColumn<MessageThread, LocalDateTime> deleteMessageTimeColumn;
    @FXML
    private ScrollPane deleteThreadContainer;
    @FXML
    private Text deleteSender;
    @FXML
    private Text deleteRecipients;
    @FXML
    private Text deleteSubject;


    @FXML
    public void executeAddDelete(ActionEvent event) {
        if (deleteButtonAction != null) deleteButtonAction.handle(event);
    }
    @FXML
    public void initialize() {
        new DeleteMessagesPresenter(this);
    }

    private EventHandler<ActionEvent> deleteButtonAction;
    private String sessionUsername;
    private String sessionUserType;

    @Override
    public TableView<MessageThread> getDeleteInbox() {
        return this.deleteInbox;
    }

    @Override
    public TableColumn<MessageThread, String> getDeleteMembersColumn() {
        return this.deleteMembersColumn;
    }

    @Override
    public TableColumn<MessageThread, String> getDeleteSubjectColumn() {
        return this.deleteSubjectColumn;
    }

    @Override
    public TableColumn<MessageThread, LocalDateTime> getDeleteMessageTimeColumn() {
        return this.deleteMessageTimeColumn;
    }

    @Override
    public ScrollPane getDeleteThreadContainer() {
        return this.deleteThreadContainer;
    }

    @Override
    public void setDeleteSender(String senderName) {
        this.deleteSender.setText(senderName);
    }

    @Override
    public void setDeleteRecipients(List<String> recipientNames) {
        this.deleteRecipients.setText(recipientListToString(recipientNames));
    }

    @Override
    public void setDeleteSubject(String subject) {
        this.deleteSubject.setText(subject);
    }

    @Override
    public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.deleteButtonAction = eventHandler;
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

    private String recipientListToString(List<String> recipientNames) {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (String name : recipientNames) {
            sb.append(prefix);
            sb.append(name);
            prefix = ", ";
        }
        return sb.toString();
    }
}
