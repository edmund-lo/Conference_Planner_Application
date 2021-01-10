package common;

import model.UserAccount;

/**
 * Static class for storing session's current logged in user's account
 */
public class UserAccountHolder {
    private UserAccount userAccount;
    private final static UserAccountHolder Instance = new UserAccountHolder();

    /**
     * Empty UserAccountHolder constructor
     */
    private UserAccountHolder() {}

    /**
     * Gets the current instance of a UserAccountHolder
     * @return An instance of a UserAccountHolder object
     */
    public static UserAccountHolder getInstance() { return Instance; }

    //region Getters amd Setters
    public void setUserAccount(UserAccount ua) {
        this.userAccount = ua;
    }

    public UserAccount getUserAccount() {
        return this.userAccount;
    }
    //endregion
}
