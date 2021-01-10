package admin;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.User;

public interface ISetVipView extends ILoggedInView {
    TableView<User> getUserTable();
    TableColumn<User, String> getFirstNameColumn();
    TableColumn<User, String> getLastNameColumn();
    TableColumn<User, String> getUsernameColumn();
    TableColumn<User, Boolean> getVipColumn();

    String getUsername();
    void setUsername(String username);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    String getVip();
    void setVip(String vip);
    void setResultText(String resultText);

    void setChangeVipButtonAction(EventHandler<ActionEvent> eventHandler);
}
