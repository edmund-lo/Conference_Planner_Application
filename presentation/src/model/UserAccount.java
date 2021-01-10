package model;

import javafx.beans.property.*;

/**
 * Model class for UserAccount object
 */
public class UserAccount {
    private final StringProperty username;
    private final BooleanProperty locked;
    private final StringProperty userType;
    private final BooleanProperty setSecurity;
    private final StringProperty securityQuestion1;
    private final StringProperty securityQuestion2;

    /**
     * Initialises a UserAccount object with default attributes
     */
    public UserAccount(String username, String userType) {
        this(username, userType, false, false, null, null);
    }

    /**
     * Initialises a UserAccount object with given parameters as attributes
     * @param username String object representing user's username
     * @param userType String object representing user's user type
     * @param locked boolean representing whether user's account is locked or not
     * @param setSecurity boolean representing whether user has set security questions or not
     */
    public UserAccount(String username, String userType, boolean locked, boolean setSecurity, String question1,
                       String question2) {
        this.username = new SimpleStringProperty(username);
        this.userType = new SimpleStringProperty(userType);
        this.locked = new SimpleBooleanProperty(locked);
        this.setSecurity = new SimpleBooleanProperty(setSecurity);
        this.securityQuestion1 = new SimpleStringProperty(question1);
        this.securityQuestion2 = new SimpleStringProperty(question2);
    }

    //region Getters and Setters
    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getUserType() {
        return userType.get();
    }

    public void setUserType(String userType) {
        this.userType.set(userType);
    }

    public boolean isLocked() {
        return locked.get();
    }

    public BooleanProperty lockedProperty() { return locked; }

    public boolean isSetSecurity() {
        return setSecurity.get();
    }

    public String getSecurityQuestion1() {
        return securityQuestion1.get();
    }

    public String getSecurityQuestion2() {
        return securityQuestion2.get();
    }
    //endregion
}
