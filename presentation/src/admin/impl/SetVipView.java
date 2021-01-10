package admin.impl;

import admin.ISetVipView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.User;

/**
 * View class for setting attendees' VIP status screen
 */
public class SetVipView implements ISetVipView {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, Boolean> vipColumn;
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private ChoiceBox<String> vip;
    @FXML
    private Text resultText;

    @FXML
    public void executeAddChangeVip(ActionEvent event) {
        if (changeVipButtonAction != null) changeVipButtonAction.handle(event);
    }
    @FXML
    public void initialize() {
        new SetVipPresenter(this);
    }

    private EventHandler<ActionEvent> changeVipButtonAction;
    private String sessionUsername;
    private String sessionUserType;

    @Override
    public TableView<User> getUserTable() {
        return this.userTable;
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
    public TableColumn<User, Boolean> getVipColumn() {
        return this.vipColumn;
    }

    @Override
    public String getUsername() {
        return this.username.getText();
    }

    @Override
    public void setUsername(String username) {
        this.username.setText(username);
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }

    @Override
    public String getVip() {
        return this.vip.getValue();
    }

    @Override
    public void setVip(String vip) {
        this.vip.setValue(vip);
    }

    @Override
    public void setResultText(String resultText) {
        this.resultText.setText(resultText);
    }

    @Override
    public void setChangeVipButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.changeVipButtonAction = eventHandler;
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
}
