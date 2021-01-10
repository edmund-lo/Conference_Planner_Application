package admin;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.LoginLog;
import model.UserAccount;

import java.time.LocalDateTime;

public interface IUnlockAccountsView extends ILoggedInView {
    TableView<UserAccount> getUserTable();
    TableColumn<UserAccount, String> getUsernameColumn();
    TableColumn<UserAccount, String> getUserTypeColumn();
    TableColumn<UserAccount, Boolean> getLockedColumn();
    TableView<LoginLog> getLogsTable();
    TableColumn<LoginLog, LocalDateTime> getLoginTimeColumn();
    TableColumn<LoginLog, Boolean> getSuccessColumn();
    Button getUnlockButton();

    String getUsername();
    void setUsername(String username);
    String getUserType();
    void setUserType(String userType);
    void setResultText(String resultText);

    void setUnlockButtonAction(EventHandler<ActionEvent> eventHandler);
}
