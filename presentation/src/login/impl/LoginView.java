package login.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import login.ILoginView;

public class LoginView implements ILoginView {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text resultText;

    @FXML
    public void executeAddLogin(ActionEvent event) {
        if (loginButtonAction != null) loginButtonAction.handle(event);
    }
    @FXML
    public void executeAddRegister(ActionEvent event) {
        if (registerButtonAction != null) registerButtonAction.handle(event);
    }
    @FXML
    public void executeAddForgotPassword(ActionEvent event) {
        if (forgotPasswordButtonAction != null) forgotPasswordButtonAction.handle(event);
    }
    @FXML
    public void initialize() {
        new LoginPresenter(this);
    }

    private EventHandler<ActionEvent> registerButtonAction;
    private EventHandler<ActionEvent> loginButtonAction;
    private EventHandler<ActionEvent> forgotPasswordButtonAction;
    @Override
    public TextField getUsernameField() {
        return this.username;
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
    public PasswordField getPasswordField() {
        return this.password;
    }

    @Override
    public String getPassword() {
        return this.password.getText();
    }

    @Override
    public void setPassword(String password) {
        this.password.setText(password);
    }

    @Override
    public Text getResultTextControl() {
        return this.resultText;
    }

    @Override
    public void setResultText(String resultText) {
        this.resultText.setText(resultText);
    }

    @Override
    public void setLoginButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.loginButtonAction = eventHandler;
    }

    @Override
    public void setRegisterButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.registerButtonAction = eventHandler;
    }

    @Override
    public void setForgotPasswordButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.forgotPasswordButtonAction = eventHandler;
    }
}
