package usecases;

import entities.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Helper that manages all interaction with the Entities.UserAccountEntity classes and
 * ensures no rules are broken.
 * @author dylan, Edmund
 * @version 1.0
 */
public class UserAccountManager implements Serializable {
    private HashMap<String, UserAccount> allUserAccounts;

    /**
     * Constructor for UserAccountManager object. Initializes empty HashMap.
     *
     */
    public UserAccountManager(){
        this.allUserAccounts = new HashMap<>();
    }

    /**
     * Adds new user account
     *
     * @param username the username
     * @param password the password
     * @param type the user's type
     * @param security whether security questions have been set
     * @param q1 security question 1
     * @param q2 security question 2
     * @param ans1 security answer 1
     * @param ans2 security answer 2
     */
    public void addAccount(String username, String password, String type, boolean security, String q1,
                           String q2, String ans1, String ans2) {

        UserAccount account = new UserAccount(username, password, type, false, security,
                q1, q2, ans1, ans2);

        allUserAccounts.put(username, account);
    }

    /**
     * Adds new user account
     *
     * @param username the username
     * @param password the password
     * @param type the user's type
     */
    public void addAccount(String username, String password, String type) {
        UserAccount account = new UserAccount(username, password, type);
        allUserAccounts.put(username, account);
    }

    /**
     * Set new username
     *
     * @param username the username
     * @param newUsername the new username
     */
    public void setUsername(String username, String newUsername) {
        allUserAccounts.get(username).setUsername(newUsername);
    }

    /**
     * Set new password
     *
     * @param username the username
     * @param newPassword the new password
     */
    public void setPassword(String username, String newPassword) {
        allUserAccounts.get(username).setPassword(newPassword);
    }

    /**
     * Set new user type
     *
     * @param username the username
     * @param newUserType the new user type
     */
    public void setUserType(String username, String newUserType) {
        allUserAccounts.get(username).setUserType(newUserType);
    }

    /**
     * Check if account is locked
     *
     * @param username the username
     * @return true iff the account is locked
     */
    public boolean isLocked(String username) {
        return allUserAccounts.get(username).isLocked();
    }

    /**
     * Locks the account
     *
     * @param username the username
     */
    public void lockAccount(String username) {
        allUserAccounts.get(username).lock();
    }

    /**
     * Unlocks the account
     *
     * @param username the username
     */
    public void unlockAccount(String username) {
        allUserAccounts.get(username).unlock();
    }

    /**
     * Sets security questions and answers
     *
     * @param username the username
     * @param Q1 question 1
     * @param A1 answer 1
     * @param Q2 question 2
     * @param A2 answer 2
     */
    public void setSecurityQuestions(String username, String Q1, String A1, String Q2, String A2) {
        allUserAccounts.get(username).setSecurityQuestions(Q1, A1, Q2, A2);
    }

    /**
     * Sets setSecurity
     *
     * @param username the username
     * @param setSecurity the new setSecurity
     */
    public void setSetSecurity(String username, boolean setSecurity) {
        allUserAccounts.get(username).setSetSecurity(setSecurity);
    }

    public boolean getSecurity(String username) {
        return allUserAccounts.get(username).getSetSecurity();
    }

    /**
     * Gets the security answers
     *
     * @param username the username
     * @return a string array containing security answers
     */
    public String[] getSecurityAns(String username) {
        return allUserAccounts.get(username).getSecurityAns();
    }

    /**
     * Gets info on all users' username and password.
     *
     * @return An arraylist of Strings containing each user's username and password
     */
    public ArrayList<String[]> getAccountInfo() {
        ArrayList<String[]> accountInfo = new ArrayList<>();
        for (UserAccount userAccount : allUserAccounts.values()) {
            String[] info = {userAccount.getUsername(), userAccount.getPassword()};
            accountInfo.add(info);
        }
        return accountInfo;
    }

    /**
     * Gets the user account's json
     *
     * @param username the username
     * @return a JSONArray of the user account
     */
    public JSONArray getAccountJSON(String username) {
        JSONArray array = new JSONArray();
        JSONObject json = allUserAccounts.get(username).convertToJSON();
        array.add(json);

        return array;
    }

    /**
     * Gets all user accounts' json
     *
     * @return a JSONObject containing all user accounts
     */
    @SuppressWarnings("unchecked")
    public JSONArray getAllAccountsJSON() {
        JSONArray array = new JSONArray();

        for(String ID: allUserAccounts.keySet()) {
            array.add(allUserAccounts.get(ID).convertToJSON());
        }

        return array;
    }
}
