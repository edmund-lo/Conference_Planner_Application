package organizer;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import model.User;

public interface IMessageUsersView extends ILoggedInView {
    TableView<User> getUserTable();
    TableColumn<User, Boolean> getCheckedColumn();
    TableColumn<User, String> getFirstNameColumn();
    TableColumn<User, String> getLastNameColumn();
    TableColumn<User, String> getUsernameColumn();
    CheckBox getSelectAll();
    TextField getRecipientsField();
    TextArea getContentArea();
    TextField getSubjectField();

    String getSender();
    void setSender(String sender);
    String getRecipients();
    void setRecipients(String recipients);
    String getContent();
    void setContent(String content);
    String getSubject();
    void setSubject(String subject);
    void setResultText(String resultText);

    void setSendButtonAction(EventHandler<ActionEvent> eventHandler);
    void setSelectAllAction(EventHandler<ActionEvent> eventHandler);
}
