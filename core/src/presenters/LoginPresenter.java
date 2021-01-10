package presenters;
import org.json.simple.*;

/**
 * A Presenter class that provides output to the user at time of login.
 */

public class LoginPresenter {
    private PresenterUtil<String> pu;

    public LoginPresenter(){
        pu = new PresenterUtil<>();
    }

    /**
     * Lets the user know that the username entered was taken.
     */
    public JSONObject UsernameTaken(){
        return pu.createJSON("error", "Username is taken!");
    }

    /**
     * Lets user know that the username or password that they entered was incorrect.
     */
    public JSONObject IncorrectCredentials(){
        return pu.createJSON("warning", "Incorrect Username or Password.");
    }

    /**
     * Lets the user know that their account has been successfully created.
     */
    public JSONObject AccountMade() {
        return pu.createJSON("success", "Account has been made!");
    }

    /**
     * Username cannot be empty.
     */
    public JSONObject EmptyName() {
       return pu.createJSON("warning", "Username cannot be empty!");
    }

    /**
     * Notifies the user that their password should have at least 6 characters
     */
    public JSONObject EmptyPassword() {
        return pu.createJSON("warning", "Password should have at least 6 characters");
    }

    /**
     * Notifies the user that their account is locked
     */
    public JSONObject AccountLocked() {
        return pu.createJSON("warning", "Your account has been locked due to suspicious activity. " +
                "\nPlease contact an Admin to get help.");
    }

    /**
     * Notifies the user that their login is successfully
     * @param data the user data
     */
    public JSONObject SuccessfulLogin(JSONArray data) {
        return pu.createJSON("success", "Login Successful!", data);
    }

    /**
     * Notifies the user that their username cannot contain whitespaces
     */
    public JSONObject noWhiteSpace() {
        return pu.createJSON("warning", "Username cannot contain whitespaces");
    }

    /**
     * Notifies the user that their password has been successfully changed
     */
    public JSONObject passwordChanged() {
        return pu.createJSON("success", "Your password has successfully been changed");
    }

    /**
     * Notifies hte user that their answer to the security question is incorrect
     */
    public JSONObject incorrectAnswers() {
        return pu.createJSON("warning", "The answers to the security questions were incorrect");
    }

    /**
     * Notifies the user that their answer to the security question was correct
     */
    public JSONObject correctAnswers() {
        return pu.createJSON("success", "The answers to the security questions are correct");
    }

    /**
     * method for sending the account login logs to the presentation module
     * @param json account logs in JSONArray format
     */
    public JSONObject accountLogs(JSONArray json) {
        return pu.createJSON("success", "Here are the logs:", json);
    }

    /**
     * Notifies the user that their passwords do not match
     */
    public JSONObject passwordsDontMatch() {
        return pu.createJSON("warning", "Passwords do not match");
    }

    /**
     * Notifies the user that their security questions cant be empty
     */
    public JSONObject emptyQuestion() {
        return pu.createJSON("warning", "Security questions cannon be empty.");
    }

    /**
     * Notifies the user that their security answers cannot be empty
     */
    public JSONObject emptyAnswer() {
        return pu.createJSON("warning", "Security answers cannot be empty.");
    }

    /**
     * Notifies the user that the username does not exist
     * @return JSONObject properly formatted for the presentation module containing the notice
     */
    public JSONObject usernameDoesntExist() {
        return pu.createJSON("error", "The username you entered does not exist.");
    }

    /**
     * Notifies the user that they don't have security set for this account
     * @return JSONObject properly formatted for the presentation module containing the notice
     */
    public JSONObject noSecurity() {
        return pu.createJSON("warning", "You don't have security set for this account. Register your" +
                " account to set your security.");
    }

    /**
     * Notifies the user that their account has been updated
     * @return JSONObject properly formatted for the presentation module containing the notice
     */
    public JSONObject AccountUpdated() {
        return pu.createJSON("success", "Your account has updated");
    }

    /**
     * Notifies the user that their first or last name cannot be empty
     * @return JSONObject properly formatted for the presentation module containing the notice
     */
    public JSONObject emptyName() {
        return pu.createJSON("warning", "First or last name cannot be empty.");
    }
}