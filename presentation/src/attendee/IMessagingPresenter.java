package attendee;

import common.ILoggedInPresenter;
import javafx.event.ActionEvent;
import model.MessageThread;

import java.util.List;

public interface IMessagingPresenter extends ILoggedInPresenter {
    void replyButtonAction(ActionEvent actionEvent);
    void sendButtonAction(ActionEvent actionEvent);
    void newMessageButtonAction(ActionEvent actionEvent);
    void moveToTrashButtonAction(ActionEvent actionEvent);
    void moveToArchivedButtonAction(ActionEvent actionEvent);
    void moveToPrimaryFirstButtonAction(ActionEvent actionEvent);
    void moveToPrimarySecondButtonAction(ActionEvent actionEvent);
    void unreadPrimaryButtonAction(ActionEvent actionEvent);
    void unreadArchivedButtonAction(ActionEvent actionEvent);
    void unreadTrashButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status);
    List<MessageThread> getInbox(String type);
    void displayInbox(List<MessageThread> messageThreads, String type);
    void displayMessageThread(MessageThread messageThread, String type);
}
