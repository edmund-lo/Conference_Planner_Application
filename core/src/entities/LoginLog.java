package entities;

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * An Entity class for a Login Log.
 * @author dylanbaptist
 * @version 1.0
 */
public class LoginLog implements Serializable {
    public boolean condition;
    public String username;
    public LocalDateTime time;

    /**
     * Constructor for a LoginLog.
     *
     * @param condition the type and condition of the log
     * @param username the username of the User
     * @param time the time of login
     */
    public LoginLog(boolean condition, String username, LocalDateTime time) {
        this.condition = condition;
        this.username = username;
        this.time = time;
    }

    /**
     * gets the condition of the login logs
     * Example: "Successful Login"
     * @return type of login condition
     */
    public boolean getCondition() {
        return this.condition;
    }

    /**
     * gets the username stored in the login log
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * gets the time of login
     * @return password
     */
    public LocalDateTime getTime() {
        return this.time;
    }

    /**
     * toString for the login log
     * @return a string of the login log
     */
    public String toString() {
        return "The condition of this is " + this.getCondition() + ". The username is " + this.getUsername() +
                ". The time of login is " + this.getTime() + ".";
    }

    /**
     * @return A JSONObject that contains the JSON representation of this class
     */
    @SuppressWarnings("unchecked")
    public JSONObject convertToJSON() {
        JSONObject item = new JSONObject();

        item.put("condition", this.getCondition());
        item.put("username", this.getUsername());
        item.put("loginTime", this.getTime());

        return item;
    }
}
