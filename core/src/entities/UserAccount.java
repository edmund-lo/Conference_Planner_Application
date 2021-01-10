package entities;

import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * An entity class for a user account.
 */
public class UserAccount implements Serializable {
    private String username;
    private String password;
    private Boolean locked;
    private String userType;
    private Boolean setSecurity;

    private String SecurityQ1;
    private String SecurityQ2;

    private String SecurityA1;
    private String SecurityA2;

    /**
     * Constructor for a new user account
     *
     * @param username username
     * @param password password
     * @param userType usertype
     * @param locked whether it is locked
     * @param setSecurity whether security questions have been set
     * @param Q1 question 1
     * @param Q2 question 2
     * @param A1 answer 1
     * @param A2 answer 2
     */
    public UserAccount(String username, String password, String userType, boolean locked, boolean setSecurity,
                       String Q1, String Q2, String A1, String A2) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.locked = locked;
        this.setSecurity = setSecurity;

        this.SecurityQ1 = Q1;
        this.SecurityQ2 = Q2;

        this.SecurityA1 = A1;
        this.SecurityA2 = A2;
    }

    /**
     * Constructor for a new user account
     *
     * @param username username
     * @param password password
     * @param userType usertype
     */
    public UserAccount(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.locked = false;
        this.setSecurity = false;
    }

    /**
     * Gets username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username
     *
     * @param username new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password
     *
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets user type
     *
     * @return user type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets user type
     *
     * @param userType new user type
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Checks if user account is locked
     *
     * @return true iff account is locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Locks the account
     */
    public void lock() {
        this.locked = true;
    }

    /**
     * Unlocks the account
     */
    public void unlock() {
        this.locked = false;
    }

    /**
     * Sets the security questions and answers
     *
     * @param Q1 question 1
     * @param A1 answer 1
     * @param Q2 question 2
     * @param A2 answer 2
     */
    public void setSecurityQuestions(String Q1, String A1, String Q2, String A2){
        this.SecurityQ1 = Q1;
        this.SecurityQ2 = Q2;

        this.SecurityA1 = A1;
        this.SecurityA2 = A2;
    }

    /**
     * Gets the security answers
     *
     * @return the security answers
     */
    public String[] getSecurityAns(){
        return new String[]{this.SecurityA1, this.SecurityA2};
    }

    /**
     * Gets setSecurity
     *
     * @return setSecurity
     */
    public boolean getSetSecurity() {
        return setSecurity;
    }

    /**
     * Sets setSecurity
     *
     * @param setSecurity the new setSecurity
     */
    public void setSetSecurity(boolean setSecurity) {
        this.setSecurity = setSecurity;
    }

    /**
     * @return A JSONArray that contains the JSON representation of this class
     */
    @SuppressWarnings("unchecked")
    public JSONObject convertToJSON() {
        JSONObject json = new JSONObject();


        json.put("Username", username);
        json.put("AccountType", userType.substring(0,1).toUpperCase() + userType.substring(1));
        json.put("Locked", locked);
        json.put("Security", setSecurity);
        json.put("Security Q1", SecurityQ1);
        json.put("Security A1", SecurityA1);
        json.put("Security Q2", SecurityQ2);
        json.put("Security A2", SecurityA2);



        return json;
    }
}


