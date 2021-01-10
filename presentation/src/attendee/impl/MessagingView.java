package attendee.impl;

import attendee.IMessagingView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.MessageThread;

import java.util.List;

/**
 * View class for messaging functionality screen
 */
public class MessagingView implements IMessagingView {
    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<MessageThread> primaryInbox;
    @FXML
    private TableView<MessageThread> archivedInbox;
    @FXML
    private TableView<MessageThread> trashInbox;
    @FXML
    private TableColumn<MessageThread, String> primaryMembersColumn;
    @FXML
    private TableColumn<MessageThread, String> primarySubjectColumn;
    @FXML
    private TableColumn<MessageThread, Boolean> primaryUnreadColumn;
    @FXML
    private TableColumn<MessageThread, String> archivedMembersColumn;
    @FXML
    private TableColumn<MessageThread, String> archivedSubjectColumn;
    @FXML
    private TableColumn<MessageThread, Boolean> archivedUnreadColumn;
    @FXML
    private TableColumn<MessageThread, String> trashMembersColumn;
    @FXML
    private TableColumn<MessageThread, String> trashSubjectColumn;
    @FXML
    private TableColumn<MessageThread, Boolean> trashUnreadColumn;
    @FXML
    private ScrollPane primaryThreadContainer;
    @FXML
    private ScrollPane archivedThreadContainer;
    @FXML
    private ScrollPane trashThreadContainer;
    @FXML
    private Text primarySender;
    @FXML
    private Text primaryRecipients;
    @FXML
    private Text primarySubject;
    @FXML
    private TextArea content;
    @FXML
    private Text archivedSender;
    @FXML
    private Text archivedRecipients;
    @FXML
    private Text archivedSubject;
    @FXML
    private Text trashSender;
    @FXML
    private Text trashRecipients;
    @FXML
    private Text trashSubject;
    @FXML
    private Text resultText;

    @FXML
    public void executeAddReply(ActionEvent event) {
        if (replyButtonAction != null) replyButtonAction.handle(event);
    }
    @FXML
    public void executeAddNewMessage(ActionEvent event) {
        if (newMessageButtonAction != null) newMessageButtonAction.handle(event);
    }
    @FXML
    public void executeAddArchive(ActionEvent event) {
        if (moveToArchiveButtonAction != null) moveToArchiveButtonAction.handle(event);
    }
    @FXML
    public void executeAddDelete(ActionEvent event) {
        if (moveToTrashButtonAction != null) moveToTrashButtonAction.handle(event);
    }
    @FXML
    public void executeAddPrimaryFirst(ActionEvent event) {
        if (moveToPrimaryFirstButtonAction != null) moveToPrimaryFirstButtonAction.handle(event);
    }
    @FXML
    public void executeAddPrimarySecond(ActionEvent event) {
        if (moveToPrimarySecondButtonAction != null) moveToPrimarySecondButtonAction.handle(event);
    }
    @FXML
    public void executeAddUnreadPrimary(ActionEvent event) {
        if (unreadPrimaryButtonAction != null) unreadPrimaryButtonAction.handle(event);
    }
    @FXML
    public void executeAddUnreadArchived(ActionEvent event) {
        if (unreadArchivedButtonAction != null) unreadArchivedButtonAction.handle(event);
    }
    @FXML
    public void executeAddUnreadTrash(ActionEvent event) {
        if (unreadTrashButtonAction != null) unreadTrashButtonAction.handle(event);
    }
    @FXML
    public void initialize() {
        new MessagingPresenter(this);
    }

    private EventHandler<ActionEvent> replyButtonAction;
    private EventHandler<ActionEvent> newMessageButtonAction;
    private EventHandler<ActionEvent> moveToArchiveButtonAction;
    private EventHandler<ActionEvent> moveToTrashButtonAction;
    private EventHandler<ActionEvent> moveToPrimaryFirstButtonAction;
    private EventHandler<ActionEvent> moveToPrimarySecondButtonAction;
    private EventHandler<ActionEvent> unreadPrimaryButtonAction;
    private EventHandler<ActionEvent> unreadArchivedButtonAction;
    private EventHandler<ActionEvent> unreadTrashButtonAction;
    private String sessionUsername;
    private String sessionUserType;

    @Override
    public void setPrimarySender(String sender) {
        this.primarySender.setText(sender);
    }

    @Override
    public void setPrimaryRecipientNames(List<String> recipientNames) {
        this.primaryRecipients.setText(recipientListToString(recipientNames));
    }

    @Override
    public void setPrimarySubject(String subject) {
        this.primarySubject.setText(subject);
    }

    @Override
    public String getContent() {
        return this.content.getText();
    }

    @Override
    public void setArchivedSender(String sender) {
        this.archivedSender.setText(sender);
    }

    @Override
    public void setArchivedRecipientNames(List<String> recipientNames) {
        this.archivedRecipients.setText(recipientListToString(recipientNames));
    }

    @Override
    public void setArchivedSubject(String subject) {
        this.archivedSubject.setText(subject);
    }

    @Override
    public void setTrashSender(String sender) {
        this.trashSender.setText(sender);
    }

    @Override
    public void setTrashRecipientNames(List<String> recipientNames) {
        this.trashRecipients.setText(recipientListToString(recipientNames));
    }

    @Override
    public void setTrashSubject(String subject) {
        this.trashSubject.setText(subject);
    }

    @Override
    public void setResultText(String resultText) {
        this.resultText.setText(resultText);
    }

    @Override
    public TableView<MessageThread> getPrimaryInbox() {
        return this.primaryInbox;
    }

    @Override
    public TableColumn<MessageThread, String> getPrimaryMembersColumn() {
        return this.primaryMembersColumn;
    }

    @Override
    public TableColumn<MessageThread, String> getPrimarySubjectColumn() {
        return this.primarySubjectColumn;
    }

    @Override
    public TableColumn<MessageThread, Boolean> getPrimaryUnreadColumn() {
        return this.primaryUnreadColumn;
    }

    @Override
    public ScrollPane getPrimaryThreadContainer() {
        return this.primaryThreadContainer;
    }

    @Override
    public TableView<MessageThread> getArchivedInbox() {
        return this.archivedInbox;
    }

    @Override
    public TableColumn<MessageThread, String> getArchivedMembersColumn() {
        return this.archivedMembersColumn;
    }

    @Override
    public TableColumn<MessageThread, String> getArchivedSubjectColumn() {
        return this.archivedSubjectColumn;
    }

    @Override
    public TableColumn<MessageThread, Boolean> getArchivedUnreadColumn() {
        return this.archivedUnreadColumn;
    }

    @Override
    public ScrollPane getArchivedThreadContainer() {
        return this.archivedThreadContainer;
    }

    @Override
    public TableView<MessageThread> getTrashInbox() {
        return this.trashInbox;
    }

    @Override
    public TableColumn<MessageThread, String> getTrashMembersColumn() {
        return this.trashMembersColumn;
    }

    @Override
    public TableColumn<MessageThread, String> getTrashSubjectColumn() {
        return this.trashSubjectColumn;
    }

    @Override
    public TableColumn<MessageThread, Boolean> getTrashUnreadColumn() {
        return this.trashUnreadColumn;
    }

    @Override
    public ScrollPane getTrashThreadContainer() {
        return this.trashThreadContainer;
    }

    @Override
    public TabPane getTabPane() {
        return this.tabPane;
    }

    @Override
    public void setNewMessageButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.newMessageButtonAction = eventHandler;
    }

    @Override
    public void setReplyButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.replyButtonAction = eventHandler;
    }

    @Override
    public void setMoveToPrimaryFirstButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.moveToPrimaryFirstButtonAction = eventHandler;
    }

    @Override
    public void setMoveToPrimarySecondButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.moveToPrimarySecondButtonAction = eventHandler;
    }

    @Override
    public void setMoveToTrashButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.moveToTrashButtonAction = eventHandler;
    }

    @Override
    public void setMoveToArchivedButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.moveToArchiveButtonAction = eventHandler;
    }

    @Override
    public void setUnreadPrimaryButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.unreadPrimaryButtonAction = eventHandler;
    }

    @Override
    public void setUnreadArchivedButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.unreadArchivedButtonAction = eventHandler;
    }

    @Override
    public void setUnreadTrashButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.unreadTrashButtonAction = eventHandler;
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
        return this.resultText;
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
