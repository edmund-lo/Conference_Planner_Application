package login.impl;

import adapter.UserAccountAdapter;
import common.UserAccountHolder;
import controllers.LoginController;
import javafx.event.ActionEvent;
import login.ILoginPresenter;
import login.ILoginView;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.ComponentFactory;
import util.TextResultUtil;

/**
 * Presenter class for the login screen
 */
public class LoginPresenter implements ILoginPresenter {
    private final ILoginView view;
    private final LoginController lc;

    /**
     * Initialises a LoginPresenter object with given view and new LoginController
     * @param view ILoginView interface implementation
     */
    public LoginPresenter(ILoginView view) {
        this.view = view;
        this.lc = new LoginController();
        init();
    }

    /**
     * Performs login button action and displays result of action in the UI or changes the scene
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void loginButtonAction(ActionEvent actionEvent) {
        clearResultText();

        JSONObject responseJson = lc.login(this.view.getUsername(), this.view.getPassword());
        if (String.valueOf(responseJson.get("status")).equals("success")) {
            UserAccount userAccount = UserAccountAdapter.getInstance()
                    .adaptData((JSONArray) responseJson.get("data")).get(0);
            UserAccount account = new UserAccount(userAccount.getUsername(), userAccount.getUserType());
            UserAccountHolder holder = UserAccountHolder.getInstance();
            holder.setUserAccount(account);
            if (!userAccount.isSetSecurity())
                ComponentFactory.getInstance().createLoggedInComponent(actionEvent, "setup.fxml");
            else
                ComponentFactory.getInstance().createLoggedInComponent(actionEvent, "home.fxml");
        } else
            setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
    }

    /**
     * Performs register button action and changes scene to register scene
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void registerButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedOutComponent(actionEvent, "register.fxml");
    }

    /**
     * Performs forgot password button action and changes scene to register scene
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void forgotPasswordButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedOutComponent(actionEvent, "forgot.fxml");
    }

    /**
     * Sets the result of the action given status
     * @param resultText String object describing the result
     * @param status String object representing the status of the controller method call
     */
    @Override
    public void setResultText(String resultText, String status) {
        this.view.setUsername("");
        this.view.setPassword("");
        this.view.setResultText(resultText);
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getUsernameField());
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getPasswordField());
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getResultTextControl());
    }

    /**
     * Init method which sets all the button actions
     */
    @Override
    public void init() {
        this.view.setLoginButtonAction(this::loginButtonAction);
        this.view.setRegisterButtonAction(this::registerButtonAction);
        this.view.setForgotPasswordButtonAction(this::forgotPasswordButtonAction);
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText() {
        this.view.setResultText("");
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getUsernameField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getPasswordField());
    }
}
