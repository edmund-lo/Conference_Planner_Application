package login;

import common.IView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public interface ILoginView extends IView {
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    void setResultText(String resultText);

    TextField getUsernameField();
    PasswordField getPasswordField();

    void setLoginButtonAction(EventHandler<ActionEvent> eventHandler);
    void setRegisterButtonAction(EventHandler<ActionEvent> eventHandler);
    void setForgotPasswordButtonAction(EventHandler<ActionEvent> eventHandler);
}
