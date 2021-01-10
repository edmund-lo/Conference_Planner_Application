package login.impl;

import controllers.LoginController;
import javafx.event.ActionEvent;
import login.IRegisterPresenter;
import login.IRegisterView;
import org.json.simple.JSONObject;
import util.ComponentFactory;
import util.TextResultUtil;

/**
 * Presenter class for the register new user screen
 */
public class RegisterPresenter implements IRegisterPresenter {
    private final IRegisterView view;
    private final LoginController lc;

    /**
     * Initialises a RegisterPresenter object with given view and new LoginController
     * @param view IRegisterView interface implementation
     */
    public RegisterPresenter(IRegisterView view) {
        this.view = view;
        this.lc = new LoginController();
        init();
    }

    /**
     * Performs back button action and changes scene to login screen
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void backButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedOutComponent(actionEvent, "login.fxml");
    }

    /**
     * Performs register button action and displays result of action
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void registerButtonAction(ActionEvent actionEvent) {
        clearResultText();

        JSONObject queryJson = constructRegisterJson();
        JSONObject responseJson = lc.createAccount(queryJson, false);
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
    }

    /**
     * Sets the result of the action given status
     * @param resultText String object describing the result
     * @param status String object representing the status of the controller method call
     */
    @Override
    public void setResultText(String resultText, String status) {
        if (status.equals("error") || status.equals("warning")) {
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getUsernameField());
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getPasswordField());
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getConfirmPasswordField());
            if (status.equals("warning")) {
                TextResultUtil.getInstance().addPseudoClass(status, this.view.getFirstNameField());
                TextResultUtil.getInstance().addPseudoClass(status, this.view.getLastNameField());
                TextResultUtil.getInstance().addPseudoClass(status, this.view.getSecurityQuestionField(1));
                TextResultUtil.getInstance().addPseudoClass(status, this.view.getSecurityAnswerField(1));
                TextResultUtil.getInstance().addPseudoClass(status, this.view.getSecurityQuestionField(2));
                TextResultUtil.getInstance().addPseudoClass(status, this.view.getSecurityAnswerField(2));
            }
        }
        this.view.setResultText(resultText);
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getResultTextControl());
    }

    /**
     * Init method which sets all the button actions
     */
    @Override
    public void init() {
        this.view.setBackButtonAction(this::backButtonAction);
        this.view.setRegisterButtonAction(this::registerButtonAction);
    }

    /**
     * Helper method to encode JSON object for registering a user
     * @return JSONObject object representing a user's registration form
     */
    @SuppressWarnings("unchecked")
    private JSONObject constructRegisterJson() {
        JSONObject queryJson = new JSONObject();
        queryJson.put("username", this.view.getUsername());
        queryJson.put("userType", this.view.getUserType().toLowerCase());
        queryJson.put("firstName", this.view.getFirstName());
        queryJson.put("lastName", this.view.getLastName());
        queryJson.put("password", this.view.getPassword());
        queryJson.put("confirmPassword", this.view.getConfirmPassword());
        queryJson.put("securityQuestion1", this.view.getSecurityQuestion(1));
        queryJson.put("securityAnswer1", this.view.getSecurityAnswer(1));
        queryJson.put("securityQuestion2", this.view.getSecurityQuestion(2));
        queryJson.put("securityAnswer2", this.view.getSecurityAnswer(2));
        queryJson.put("setup", Boolean.TRUE);
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
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getFirstNameField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getLastNameField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getSecurityQuestionField(1));
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getSecurityAnswerField(1));
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getSecurityQuestionField(2));
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getSecurityAnswerField(2));
    }
}
