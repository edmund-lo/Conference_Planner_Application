package model;

import javafx.beans.property.*;
import java.time.LocalDateTime;

/**
 * Model class for LoginLog object
 */
public class LoginLog {
    private final StringProperty username;
    private final ObjectProperty<LocalDateTime> loginTime;
    private final BooleanProperty success;

    /**
     * Initialises a LoginLog object with default attributes
     */
    public LoginLog() { this(null, null, false); }

    /**
     * Initialises a LoginLog object with given parameters as attributes
     * @param username String object representing log's username
     * @param loginTime LocalDateTime object representing log's login time
     * @param success boolean representing whether login was successful
     */
    public LoginLog(String username, LocalDateTime loginTime, boolean success) {
        this.username = new SimpleStringProperty(username);
        this.loginTime = new SimpleObjectProperty<>(loginTime);
        this.success = new SimpleBooleanProperty(success);
    }

    //region Getters and Setters
    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public LocalDateTime getLoginTime() {
        return loginTime.get();
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime.set(loginTime);
    }

    public boolean isSuccess() {
        return success.get();
    }

    public BooleanProperty successProperty() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success.set(success);
    }
    //endregion
}
