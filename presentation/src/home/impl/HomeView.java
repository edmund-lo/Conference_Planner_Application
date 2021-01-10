package home.impl;

import home.IHomeView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class HomeView implements IHomeView {
    @FXML
    private Text title;
    @FXML
    private Button unreadButton;
    @FXML
    private Button attendingButton;

    @FXML
    public void initialize() {
        new HomePresenter(this);
    }

    private String sessionUsername;
    private String sessionUserType;

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setUnreadButtonText(String text) {
        this.unreadButton.setText(text);
    }

    @Override
    public void setViewScheduleButtonText(String text) {
        this.attendingButton.setText(text);
    }

    @Override
    public Text getResultTextControl() {
        return null;
    }

    @Override
    public String getSessionUsername() {
        return this.sessionUsername;
    }

    @Override
    public void setSessionUsername(String username) {
        this.sessionUsername = username;
    }

    @Override
    public String getSessionUserType() {
        return this.sessionUserType;
    }

    @Override
    public void setSessionUserType(String userType) {
        this.sessionUserType = userType;
    }
}
