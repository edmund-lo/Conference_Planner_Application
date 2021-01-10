package login.impl;

import adapter.UserAccountAdapter;
import controllers.LoginController;
import javafx.event.ActionEvent;
import login.IForgotPresenter;
import login.IForgotView;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.ComponentFactory;
import util.TextResultUtil;

/**
 * Presenter class for the forgot password screen
 */
public class ForgotPresenter implements IForgotPresenter {
    private final IForgotView view;
    private final LoginController lc;

    /**
     * Initialises a ForgotPresenter object with given view and new LoginController
     * @param view IForgotView interface implementation
     */
    public ForgotPresenter(IForgotView view) {
        this.view = view;
        this.lc = new LoginController();
        init();
    }

    /**
     * Performs back button action to go back to the login scene
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void backButtonAction(ActionEvent actionEvent) {
        ComponentFactory.getInstance().createLoggedOutComponent(actionEvent, "login.fxml");
    }

    /**
     * Performs previous button action to go back to Step 1
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void previousButtonAction(ActionEvent actionEvent) {
        this.view.getTitledPane(1).setDisable(false);
        this.view.getTitledPane(2).setDisable(true);
        this.view.getTitledPane(1).setExpanded(true);
        this.view.getTitledPane(2).setExpanded(false);
    }

    /**
     * Performs reset button action to reset password
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void resetButtonAction(ActionEvent actionEvent) {
        clearResultText(3);

        JSONObject responseJson = lc.resetPassword(this.view.getUsername(), this.view.getPassword(),
                this.view.getConfirmPassword());
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 3);
    }

    /**
     * Performs button action to confirm security answers and change to Step 3 if correct
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void confirmButtonAction(ActionEvent actionEvent) {
        clearResultText(2);

        JSONObject responseJson = lc.verifySecurityAnswers(this.view.getUsername(), this.view.getSecurityAnswer(1),
            this.view.getSecurityAnswer(2));
        if (String.valueOf(responseJson.get("status")).equals("success")) {
            this.view.getTitledPane(2).setDisable(true);
            this.view.getTitledPane(3).setDisable(false);
            this.view.getTitledPane(2).setExpanded(false);
            this.view.getTitledPane(3).setExpanded(true);
        } else
            setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 2);
    }

    /**
     * Performs button action to check if user exists and if yes, display their security questions
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void displaySecurityButtonAction(ActionEvent actionEvent) {
        clearResultText(1);

        JSONObject responseJson = lc.validateUsername(this.view.getUsername());
        if (String.valueOf(responseJson.get("status")).equals("success")) {
            UserAccount account = UserAccountAdapter.getInstance()
                    .adaptData((JSONArray) responseJson.get("data")).get(0);
            this.view.setSecurityQuestion(account.getSecurityQuestion1(), 1);
            this.view.setSecurityQuestion(account.getSecurityQuestion2(), 2);
            this.view.getTitledPane(1).setDisable(true);
            this.view.getTitledPane(2).setDisable(false);
            this.view.getTitledPane(1).setExpanded(false);
            this.view.getTitledPane(2).setExpanded(true);
        } else
            setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 1);
    }

    /**
     * Sets the result of the action given status
     * @param resultText String object describing the result
     * @param status String object representing the status of the controller method call
     */
    @Override
    public void setResultText(String resultText, String status, int index) {
        switch (index) {
            case 1:
                if (status.equals("error") || status.equals("warning"))
                    TextResultUtil.getInstance().addPseudoClass(status, this.view.getUsernameField());
                break;
            case 2:
                if (status.equals("error") || status.equals("warning")) {
                    TextResultUtil.getInstance().addPseudoClass(status, this.view.getSecurityAnswerField(1));
                    TextResultUtil.getInstance().addPseudoClass(status, this.view.getSecurityAnswerField(2));
                }
                break;
            case 3:
                if (status.equals("error") || status.equals("warning")) {
                    TextResultUtil.getInstance().addPseudoClass(status, this.view.getPasswordField());
                    TextResultUtil.getInstance().addPseudoClass(status, this.view.getConfirmPasswordField());
                }
        }
        this.view.setResultText(resultText, index);
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getResultTextControl(index));
    }

    /**
     * Init method which sets all the button actions
     */
    @Override
    public void init() {
        this.view.setFirstBackButtonAction(this::backButtonAction);
        this.view.setSecondBackButtonAction(this::backButtonAction);
        this.view.setPreviousButtonAction(this::previousButtonAction);
        this.view.setResetButtonAction(this::resetButtonAction);
        this.view.setConfirmButtonAction(this::confirmButtonAction);
        this.view.setSecurityButtonAction(this::displaySecurityButtonAction);
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText(int index) {
        this.view.setResultText("", index);
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl(index));
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getPasswordField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getConfirmPasswordField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getUsernameField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getSecurityAnswerField(1));
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getSecurityAnswerField(2));
    }
}
