package login.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import login.IRegisterView;

public class RegisterView implements IRegisterView {
    @FXML
    private ChoiceBox<String> userType;
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField securityQuestion1;
    @FXML
    private TextField securityAnswer1;
    @FXML
    private TextField securityQuestion2;
    @FXML
    private TextField securityAnswer2;
    @FXML
    private Text resultText;

    @FXML
    public void executeAddGoBack(ActionEvent event) {
        if (backButtonAction != null) backButtonAction.handle(event);
    }
    @FXML
    public void executeAddRegister(ActionEvent event) {
        if (registerButtonAction != null) registerButtonAction.handle(event);
    }
    @FXML
    public void initialize() {
        new RegisterPresenter(this);
    }

    private EventHandler<ActionEvent> registerButtonAction;
    private EventHandler<ActionEvent> backButtonAction;

    @Override
    public String getUserType() {
        return this.userType.getValue();
    }

    @Override
    public void setUserType(String userType) {
        this.userType.setValue(userType);
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
    public String getFirstName() {
        return this.firstName.getText();
    }

    @Override
    public String getLastName() {
        return this.lastName.getText();
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
    public String getSecurityQuestion(int index) {
        return getSecurityQuestionField(index).getText();
    }

    @Override
    public String getSecurityAnswer(int index) {
        return getSecurityAnswerField(index).getText();
    }

    @Override
    public void setResultText(String resultText) {
        this.resultText.setText(resultText);
    }

    @Override
    public TextField getUsernameField() {
        return this.username;
    }

    @Override
    public TextField getFirstNameField() {
        return this.firstName;
    }

    @Override
    public TextField getLastNameField() {
        return this.lastName;
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
    public TextField getSecurityQuestionField(int index) {
        TextField questionField = new TextField();
        if (index == 1) questionField = this.securityQuestion1;
        if (index == 2) questionField = this.securityQuestion2;
        return questionField;
    }

    @Override
    public TextField getSecurityAnswerField(int index) {
        TextField answerField = new TextField();
        if (index == 1) answerField = this.securityAnswer1;
        if (index == 2) answerField = this.securityAnswer2;
        return answerField;
    }

    @Override
    public void setBackButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.backButtonAction = eventHandler;
    }

    @Override
    public void setRegisterButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.registerButtonAction = eventHandler;
    }

    @Override
    public Text getResultTextControl() {
        return this.resultText;
    }
}
