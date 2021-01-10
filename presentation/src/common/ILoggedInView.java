package common;

/**
 * View interface of logged in view implementations
 */
public interface ILoggedInView extends IView {
    /**
     * Gets the current session's logged in user's username
     * @return String object representing the user's username
     */
    String getSessionUsername();

    /**
     * Sets the current session's logged in user's username
     * @param username String object representing the user's username
     */
    void setSessionUsername(String username);

    /**
     * Gets the current session's logged in user's user type
     * @return String object representing the user's user type
     */
    String getSessionUserType();

    /**
     * Sets the current session's logged in user's user type
     * @param userType String object representing the user's user type
     */
    void setSessionUserType(String userType);
}
