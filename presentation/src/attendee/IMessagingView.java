package attendee;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import model.MessageThread;
import java.util.List;

public interface IMessagingView extends ILoggedInView {
    void setPrimarySender(String sender);
    void setPrimaryRecipientNames(List<String> recipientNames);
    void setPrimarySubject(String subject);
    String getContent();
    void setArchivedSender(String sender);
    void setArchivedRecipientNames(List<String> recipientNames);
    void setArchivedSubject(String subject);
    void setTrashSender(String sender);
    void setTrashRecipientNames(List<String> recipientNames);
    void setTrashSubject(String subject);
    void setResultText(String resultText);

    TableView<MessageThread> getPrimaryInbox();
    TableColumn<MessageThread, String> getPrimaryMembersColumn();
    TableColumn<MessageThread, String> getPrimarySubjectColumn();
    TableColumn<MessageThread, Boolean> getPrimaryUnreadColumn();
    ScrollPane getPrimaryThreadContainer();
    TableView<MessageThread> getArchivedInbox();
    TableColumn<MessageThread, String> getArchivedMembersColumn();
    TableColumn<MessageThread, String> getArchivedSubjectColumn();
    TableColumn<MessageThread, Boolean> getArchivedUnreadColumn();
    ScrollPane getArchivedThreadContainer();
    TableView<MessageThread> getTrashInbox();
    TableColumn<MessageThread, String> getTrashMembersColumn();
    TableColumn<MessageThread, String> getTrashSubjectColumn();
    TableColumn<MessageThread, Boolean> getTrashUnreadColumn();
    ScrollPane getTrashThreadContainer();
    TabPane getTabPane();

    void setNewMessageButtonAction(EventHandler<ActionEvent> eventHandler);
    void setReplyButtonAction(EventHandler<ActionEvent> eventHandler);
    void setMoveToPrimaryFirstButtonAction(EventHandler<ActionEvent> eventHandler);
    void setMoveToPrimarySecondButtonAction(EventHandler<ActionEvent> eventHandler);
    void setMoveToTrashButtonAction(EventHandler<ActionEvent> eventHandler);
    void setMoveToArchivedButtonAction(EventHandler<ActionEvent> eventHandler);
    void setUnreadPrimaryButtonAction(EventHandler<ActionEvent> eventHandler);
    void setUnreadArchivedButtonAction(EventHandler<ActionEvent> eventHandler);
    void setUnreadTrashButtonAction(EventHandler<ActionEvent> eventHandler);
}
