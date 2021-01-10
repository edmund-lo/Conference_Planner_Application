package admin;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.MessageThread;

import java.time.LocalDateTime;
import java.util.List;

public interface IDeleteMessagesView extends ILoggedInView {
    TableView<MessageThread> getDeleteInbox();
    TableColumn<MessageThread, String> getDeleteMembersColumn();
    TableColumn<MessageThread, String> getDeleteSubjectColumn();
    TableColumn<MessageThread, LocalDateTime> getDeleteMessageTimeColumn();
    ScrollPane getDeleteThreadContainer();

    void setDeleteSender(String senderName);
    void setDeleteRecipients(List<String> recipientNames);
    void setDeleteSubject(String subject);

    void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler);
}
