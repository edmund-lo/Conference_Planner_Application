package admin;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public interface ICreateAccountView extends ILoggedInView {
    String getUserType();
    void setUserType(String userType);
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    String getConfirmPassword();
    void setConfirmPassword(String password);
    void setResultText(String resultText);

    TextField getUsernameField();
    PasswordField getPasswordField();
    PasswordField getConfirmPasswordField();

    void setCreateAccountButtonAction(EventHandler<ActionEvent> eventHandler);
}
