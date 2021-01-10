package organizer.impl;

import adapter.UserAdapter;
import common.UserAccountHolder;
import controllers.OrganizerController;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import organizer.IMessageUsersPresenter;
import organizer.IMessageUsersView;
import util.TextResultUtil;
import java.util.Arrays;
import java.util.List;

/**
 * Presenter class for messaging speakers scene
 */
public class MessageSpeakersPresenter implements IMessageUsersPresenter {
    private final IMessageUsersView view;
    private final OrganizerController oc;
    private ObservableList<User> users;

    /**
     * Initialises a MessageSpeakersPresenter object with given view and new OrganizerController,
     * gets and sets current session's user information
     * @param view IMessageUsersView interface implementation
     */
    public MessageSpeakersPresenter(IMessageUsersView view) {
        this.view = view;
        getUserData();
        this.oc = new OrganizerController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs send message button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void sendButtonAction(ActionEvent actionEvent) {
        clearResultText();

        JSONObject queryJson = constructMessageJson();
        JSONObject responseJson = oc.messageAllSpeakers(queryJson);
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
    }

    /**
     * Performs select all checkbox action and updates the recipient list
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void selectAllAction(ActionEvent actionEvent) {
        boolean checked = this.view.getSelectAll().isSelected();
        for (User u : this.users)
            u.setChecked(checked);
        updateRecipientList();
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
        if (status.equals("warning") || status.equals("error")) {
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getRecipientsField());
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getContentArea());
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getSubjectField());
        }
    }

    /**
     * Gets all Speaker entities stored in the database and converts them into User models
     * @return List of User models
     */
    @Override
    public List<User> getAllUsers() {
        JSONObject responseJson = oc.getAllSpeakers();
        if (!String.valueOf(responseJson.get("status")).equals("success"))
            setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
        return UserAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
    }

    /**
     * Displays users in the TableView and adds listeners
     * @param users List of User models
     */
    @Override
    public void displayUserList(List<User> users) {
        this.users = FXCollections.observableArrayList(
                user -> new Observable[]{user.getSelected()}
        );
        this.users.addAll(users);
        this.users.addListener((ListChangeListener<User>) change -> {
            while (change.next()) {
                if (change.wasUpdated()) updateRecipientList();
            }
        });
        //This callback tell the cell how to bind the user model 'selected' property to the cell, itself
        this.view.getCheckedColumn().setCellValueFactory(param -> param.getValue().getSelected());
        this.view.getFirstNameColumn().setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.view.getLastNameColumn().setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.view.getUsernameColumn().setCellValueFactory(new PropertyValueFactory<>("username"));
        this.view.getCheckedColumn().setCellFactory(CheckBoxTableCell.forTableColumn(this.view.getCheckedColumn()));
        this.view.getCheckedColumn().setEditable(true);
        this.view.getUserTable().setItems(this.users);
        this.view.getUserTable().setEditable(true);
    }

    /**
     * Updates the recipient field with the checked rows in the user table
     */
    @Override
    public void updateRecipientList() {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (User u : this.users) {
            if (u.isChecked()) {
                sb.append(prefix);
                prefix = ", ";
                sb.append(u.getUsername());
            }
        }
        this.view.setRecipients(sb.toString());
    }

    /**
     * Init method which sets all the button actions, gets and displays all speakers
     */
    @Override
    public void init() {
        List<User> users = getAllUsers();
        displayUserList(users);
        this.view.setSender(this.view.getSessionUsername());
        this.view.setSendButtonAction(this::sendButtonAction);
        this.view.setSelectAllAction(this::selectAllAction);
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
     * Helper method to encode a JSONObject for a message form
     * @return JSONObject object representing a message form
     */
    @SuppressWarnings("unchecked")
    private JSONObject constructMessageJson() {
        JSONObject queryJson = new JSONObject();
        JSONArray recipients = new JSONArray();
        recipients.addAll(Arrays.asList(this.view.getRecipients().split(", ")));
        queryJson.put("sender", this.view.getSender());
        queryJson.put("recipients", recipients);
        queryJson.put("subject", this.view.getSubject());
        queryJson.put("content", this.view.getContent());
        return queryJson;
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText() {
        this.view.setResultText("");
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getContentArea());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getRecipientsField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getSubjectField());
    }
}
