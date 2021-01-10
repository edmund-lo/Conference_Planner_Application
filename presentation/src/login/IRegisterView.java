package login;

import common.IView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public interface IRegisterView extends IView {
    String getUserType();
    void setUserType(String userType);
    String getUsername();
    void setUsername(String username);
    String getFirstName();
    String getLastName();
    String getPassword();
    void setPassword(String password);
    String getConfirmPassword();
    void setConfirmPassword(String password);
    String getSecurityQuestion(int index);
    String getSecurityAnswer(int index);
    void setResultText(String resultText);

    TextField getUsernameField();
    TextField getFirstNameField();
    TextField getLastNameField();
    PasswordField getPasswordField();
    PasswordField getConfirmPasswordField();
    TextField getSecurityQuestionField(int index);
    TextField getSecurityAnswerField(int index);

    void setBackButtonAction(EventHandler<ActionEvent> eventHandler);
    void setRegisterButtonAction(EventHandler<ActionEvent> eventHandler);
}
