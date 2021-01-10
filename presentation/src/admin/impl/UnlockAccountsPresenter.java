package admin.impl;

import adapter.LoginLogAdapter;
import adapter.UserAccountAdapter;
import admin.IUnlockAccountsPresenter;
import admin.IUnlockAccountsView;
import common.UserAccountHolder;
import controllers.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.LoginLog;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DateTimeUtil;
import util.TextResultUtil;
import java.util.List;

/**
 * Presenter class for unlocking user accounts screen
 */
public class UnlockAccountsPresenter implements IUnlockAccountsPresenter {
    private final IUnlockAccountsView view;
    private final AdminController ac;
    private UserAccount selectedAccount;

    /**
     * Initialises a UnlockAccountsPresenter object with given view and new AdminController,
     * gets and sets current session's user information
     * @param view IUnlockAccountsView interface implementation
     */
    public UnlockAccountsPresenter(IUnlockAccountsView view) {
        this.view = view;
        getUserData();
        this.ac = new AdminController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs unlock account button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void unlockButtonAction(ActionEvent actionEvent) {
        clearResultText();

        JSONObject responseJson = ac.unlockAccount(selectedAccount.getUsername());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
        if (String.valueOf(responseJson.get("status")).equals("success")) {
            this.view.getUnlockButton().setDisable(true);
            List<UserAccount> accounts = getUserAccounts();
            displayUserAccounts(accounts);
        }
    }

    /**
     * Sets the result of the action given status
     * @param resultText String object describing the result
     * @param status String object representing the status of the controller method call
     */
    @Override
    public void setResultText(String resultText, String status) {
        this.view.setResultText(resultText);
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getResultTextControl());
    }

    /**
     * Gets all UserAccount entities stored in the database and converts them into UserAccount models
     * @return List of UserAccount models
     */
    @Override
    public List<UserAccount> getUserAccounts() {
        JSONObject responseJson = ac.getAllAccounts();
        System.out.println(responseJson.toJSONString());
        return UserAccountAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
    }

    /**
     * Displays accounts in the TableView and adds listeners
     * @param accounts List of UserAccount models
     */
    @Override
    public void displayUserAccounts(List<UserAccount> accounts) {
        this.view.getUsernameColumn().setCellValueFactory(new PropertyValueFactory<>("username"));
        this.view.getUserTypeColumn().setCellValueFactory(new PropertyValueFactory<>("userType"));
        this.view.getLockedColumn().setCellValueFactory(param -> param.getValue().lockedProperty());
        this.view.getLockedColumn().setCellFactory(CheckBoxTableCell.forTableColumn(this.view.getLockedColumn()));
        this.view.getUserTable().setItems(FXCollections.observableArrayList(accounts));
        this.view.getUserTable().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayUserAccountDetails(newValue));
    }

    /**
     * Displays account's attributes
     * @param account UserAccount model that has been selected
     */
    @Override
    public void displayUserAccountDetails(UserAccount account) {
        if (account == null) return;
        this.selectedAccount = account;
        this.view.setUsername(account.getUsername());
        this.view.setUserType(account.getUserType());
        this.view.getUnlockButton().setDisable(!account.isLocked());
        List<LoginLog> logs = getUserLoginLogs(account.getUsername());
        displayUserLoginLogs(logs);
    }

    /**
     * Gets all LoginLog entities stored in the database and converts them into LoginLog models
     * @param username String object representing selected user's username
     * @return List of LoginLog models
     */
    @Override
    public List<LoginLog> getUserLoginLogs(String username) {
        JSONObject responseJson = ac.getLoginLogs(username);
        return LoginLogAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
    }

    /**
     * Displays logs in the TableView
     * @param logs List of LoginLog models
     */
    @Override
    public void displayUserLoginLogs(List<LoginLog> logs) {
        DateTimeUtil.getInstance().setLoginDateTimeCellFactory(this.view.getLoginTimeColumn());
        this.view.getLoginTimeColumn().setCellValueFactory(new PropertyValueFactory<>("loginTime"));
        this.view.getSuccessColumn().setCellValueFactory(param -> param.getValue().successProperty());
        this.view.getSuccessColumn().setCellFactory(CheckBoxTableCell.forTableColumn(this.view.getSuccessColumn()));
        this.view.getLogsTable().setItems(FXCollections.observableList(logs));
    }

    /**
     * Init method which sets all the button actions, gets and displays all events
     */
    @Override
    public void init() {
        this.view.setUnlockButtonAction(this::unlockButtonAction);
        List<UserAccount> accounts = getUserAccounts();
        displayUserAccounts(accounts);
    }

    /**
     * Helper method to get and set current user's information to the view class variable
     */
    @Override
    public void getUserData() {
        UserAccountHolder holder = UserAccountHolder.getInstance();
        UserAccount account = holder.getUserAccount();
        this.view.setSessionUsername(account.getUsername());
        this.view.setSessionUserType(account.getUserType());
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText() {
        this.view.setResultText("");
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl());
    }
}
