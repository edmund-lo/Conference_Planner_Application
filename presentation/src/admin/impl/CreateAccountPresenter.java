package admin.impl;

import common.UserAccountHolder;
import controllers.AdminController;
import javafx.event.ActionEvent;
import admin.ICreateAccountPresenter;
import admin.ICreateAccountView;
import model.UserAccount;
import org.json.simple.JSONObject;
import util.TextResultUtil;

/**
 * Presenter class for create new account screen
 */
public class CreateAccountPresenter implements ICreateAccountPresenter {
    private final ICreateAccountView view;
    private final AdminController ac;

    /**
     * Initialises a CreateAccountPresenter object with given view and new AdminController,
     * gets and sets current session's user information
     * @param view ICreateAccountView interface implementation
     */
    public CreateAccountPresenter(ICreateAccountView view) {
        this.view = view;
        getUserData();
        this.ac = new AdminController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs create new account button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void createAccountButtonAction(ActionEvent actionEvent) {
        clearResultText();

        JSONObject queryJson = constructAccountJson();
        JSONObject responseJson = ac.createAccount(queryJson);
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
        if (status.equals("error") || status.equals("warning")) {
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getUsernameField());
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getPasswordField());
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getConfirmPasswordField());
        }
    }

    /**
     * Init method which sets all the button actions
     */
    @Override
    public void init() {
        this.view.setCreateAccountButtonAction(this::createAccountButtonAction);
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
     * Helper method to encode JSON object for creating a new user
     * @return JSONObject object representing a user's new account form
     */
    @SuppressWarnings("unchecked")
    private JSONObject constructAccountJson() {
        JSONObject queryJson = new JSONObject();
        queryJson.put("username", this.view.getUsername());
        queryJson.put("userType", this.view.getUserType().toLowerCase());
        queryJson.put("password", this.view.getPassword());
        queryJson.put("confirmPassword", this.view.getConfirmPassword());
        queryJson.put("setup", Boolean.FALSE);
        return queryJson;
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText() {
        this.view.setResultText("");
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getUsernameField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getPasswordField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getConfirmPasswordField());
    }
}
