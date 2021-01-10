package login.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import login.IForgotView;

public class ForgotView implements IForgotView {
    @FXML
    private TextField username;
    @FXML
    private TextField securityQuestion1;
    @FXML
    private PasswordField securityAnswer1;
    @FXML
    private TextField securityQuestion2;
    @FXML
    private PasswordField securityAnswer2;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Text resultText1;
    @FXML
    private Text resultText2;
    @FXML
    private Text resultText3;
    @FXML
    private TitledPane step1;
    @FXML
    private TitledPane step2;
    @FXML
    private TitledPane step3;

    @FXML
    public void executeAddFirstGoBack(ActionEvent event) {
        if (firstBackButtonAction != null) firstBackButtonAction.handle(event);
    }
    @FXML
    public void executeAddSecondGoBack(ActionEvent event) {
        if (secondBackButtonAction != null) secondBackButtonAction.handle(event);
    }
    @FXML
    public void executeAddReset(ActionEvent event) {
        if (resetButtonAction != null) resetButtonAction.handle(event);
    }
    @FXML
    public void executeAddConfirm(ActionEvent event) {
        if (confirmButtonAction != null) confirmButtonAction.handle(event);
    }
    @FXML
    public void executeAddDisplaySecurity(ActionEvent event) {
        if (securityButtonAction != null) securityButtonAction.handle(event);
    }
    @FXML
    public void executeAddPrevious(ActionEvent event) {
        if (previousButtonAction != null) previousButtonAction.handle(event);
    }
    @FXML
    public void initialize() {
        new ForgotPresenter(this);
    }

    private EventHandler<ActionEvent> resetButtonAction;
    private EventHandler<ActionEvent> firstBackButtonAction;
    private EventHandler<ActionEvent> secondBackButtonAction;
    private EventHandler<ActionEvent> confirmButtonAction;
    private EventHandler<ActionEvent> previousButtonAction;
    private EventHandler<ActionEvent> securityButtonAction;

    @Override
    public String getUsername() {
        return this.username.getText();
    }

    @Override
    public void setUsername(String username) {
        this.username.setText(username);
    }

    @Override
    public String getSecurityQuestion(int index) {
        String question = "";
        if (index == 1) question = this.securityQuestion1.getText();
        else if (index == 2) question = this.securityQuestion2.getText();
        return question;
    }

    @Override
    public void setSecurityQuestion(String securityQuestion, int index) {
        if (index == 1) this.securityQuestion1.setText(securityQuestion);
        else if (index == 2)  this.securityQuestion2.setText(securityQuestion);
    }

    @Override
    public String getSecurityAnswer(int index) {
        return getSecurityAnswerField(index).getText();
    }

    @Override
    public void setSecurityAnswer(String securityAnswer, int index) {
        getSecurityAnswerField(index).setText(securityAnswer);
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
    public String getConfirmPassword() {
        return this.confirmPassword.getText();
    }

    @Override
    public void setConfirmPassword(String password) {
        this.confirmPassword.setText(password);
    }

    @Override
    public void setResultText(String resultText, int index) {
        getResultTextControl(index).setText(resultText);
    }

    @Override
    public TextField getUsernameField() {
        return this.username;
    }

    @Override
    public PasswordField getSecurityAnswerField(int index) {
        PasswordField securityAnswer = new PasswordField();
        if (index == 1) securityAnswer = this.securityAnswer1;
        else if (index == 2) securityAnswer = this.securityAnswer2;
        return securityAnswer;
    }

    @Override
    public PasswordField getPasswordField() {
        return this.password;
    }

    @Override
    public PasswordField getConfirmPasswordField() {
        return this.confirmPassword;
    }

    @Override
    public Text getResultTextControl(int index) {
        Text resultText = new Text();
        if (index == 1) resultText = this.resultText1;
        if (index == 2) resultText = this.resultText2;
        if (index == 3) resultText = this.resultText3;
        return resultText;
    }

    @Override
    public TitledPane getTitledPane(int index) {
        TitledPane tp = new TitledPane();
        if (index == 1) tp = this.step1;
        if (index == 2) tp = this.step2;
        if (index == 3) tp = this.step3;
        return tp;
    }

    @Override
    public void setFirstBackButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.firstBackButtonAction = eventHandler;
    }

    @Override
    public void setSecondBackButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.secondBackButtonAction = eventHandler;
    }

    @Override
    public void setPreviousButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.previousButtonAction = eventHandler;
    }

    @Override
    public void setSecurityButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.securityButtonAction = eventHandler;
    }

    @Override
    public void setConfirmButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.confirmButtonAction = eventHandler;
    }

    @Override
    public void setResetButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.resetButtonAction = eventHandler;
    }

    @Override
    public Text getResultTextControl() {
        return null;
    }
}
