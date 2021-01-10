package login;

import common.IView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;

public interface IForgotView extends IView {
    String getUsername();
    void setUsername(String username);
    String getSecurityQuestion(int index);
    void setSecurityQuestion(String securityQuestion, int index);
    String getSecurityAnswer(int index);
    void setSecurityAnswer(String securityAnswer, int index);
    String getPassword();
    void setPassword(String password);
    String getConfirmPassword();
    void setConfirmPassword(String password);
    void setResultText(String resultText, int index);

    TextField getUsernameField();
    PasswordField getSecurityAnswerField(int index);
    PasswordField getPasswordField();
    PasswordField getConfirmPasswordField();
    Text getResultTextControl(int index);
    TitledPane getTitledPane(int index);

    void setFirstBackButtonAction(EventHandler<ActionEvent> eventHandler);
    void setSecondBackButtonAction(EventHandler<ActionEvent> eventHandler);
    void setPreviousButtonAction(EventHandler<ActionEvent> eventHandler);
    void setSecurityButtonAction(EventHandler<ActionEvent> eventHandler);
    void setConfirmButtonAction(EventHandler<ActionEvent> eventHandler);
    void setResetButtonAction(EventHandler<ActionEvent> eventHandler);
}
