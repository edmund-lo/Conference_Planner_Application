package admin.impl;

import adapter.UserAdapter;
import admin.ISetVipPresenter;
import admin.ISetVipView;
import common.UserAccountHolder;
import controllers.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.TextResultUtil;
import java.util.List;

/**
 * Presenter class for setting attendees' VIP status screen
 */
public class SetVipPresenter implements ISetVipPresenter {
    private final ISetVipView view;
    private final AdminController ac;
    private User selectedUser;

    /**
     * Initialises a SetVipPresenter object with given view and new AdminController,
     * gets and sets current session's user information
     * @param view ISetVipView interface implementation
     */
    public SetVipPresenter(ISetVipView view) {
        this.view = view;
        getUserData();
        this.ac = new AdminController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs change attendee's VIP status button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void changeVipButtonAction(ActionEvent actionEvent) {
        clearResultText();

        JSONObject responseJson;
        if (this.view.getVip().equals("Yes"))
            responseJson = ac.setAttendeeAsVip(this.selectedUser.getUsername());
        else
            responseJson = ac.setAttendeeAsNotVip(this.selectedUser.getUsername());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
        if (String.valueOf(responseJson.get("status")).equals("success")) init();
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
     * Gets all Attendee entities stored in the database and converts them into User models
     * @return List of User models
     */
    @Override
    public List<User> getAttendeeUsers() {
        JSONObject responseJson = ac.viewAllAttendees();
        return UserAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));

    }

    /**
     * Displays attendees in the TableView and adds listeners
     * @param attendees List of User models
     */
    @Override
    public void displayUsers(List<User> attendees) {
        this.view.getUsernameColumn().setCellValueFactory(new PropertyValueFactory<>("username"));
        this.view.getFirstNameColumn().setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.view.getLastNameColumn().setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.view.getVipColumn().setCellValueFactory(param -> param.getValue().vipProperty());
        this.view.getVipColumn().setCellFactory(CheckBoxTableCell.forTableColumn(this.view.getVipColumn()));
        this.view.getUserTable().setItems(FXCollections.observableArrayList(attendees));
        this.view.getUserTable().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayUserDetails(newValue));
    }

    /**
     * Displays attendee's attributes
     * @param attendee User model that has been selected
     */
    @Override
    public void displayUserDetails(User attendee) {
        if (attendee == null) return;
        this.selectedUser = attendee;
        this.view.setUsername(attendee.getUsername());
        this.view.setFirstName(attendee.getFirstName());
        this.view.setLastName(attendee.getLastName());
        this.view.setVip(attendee.isVip() ? "Yes" : "No");
    }

    /**
     * Init method which sets all the button actions
     */
    @Override
    public void init() {
        this.view.setChangeVipButtonAction(this::changeVipButtonAction);
        List<User> attendees = getAttendeeUsers();
        displayUsers(attendees);
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
