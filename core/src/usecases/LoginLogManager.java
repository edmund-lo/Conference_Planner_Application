package usecases;

import entities.LoginLog;
import org.json.simple.JSONArray;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Use Case class that stores the login logs with key as username and values
 * as an extendable Array List
 *
 * @author Dylan Baptist
 * @version 1.0
 *
 */
public class LoginLogManager implements Serializable {
    private HashMap<String, ArrayList<LoginLog>> allLogs;

    /**
     * Constructs a new empty LoginLogManager object containing no logs.
     */
    public LoginLogManager() {
        this.allLogs = new HashMap<>();
    }

    /**
     * Creates a new LoginLog object with an empty log and adds it into this LoginLogManager.
     * @param condition  the condition of the login log, describes the success/failure of login.
     * @param username the username of the login user
     * @param time the time of login for this user
     */
    @SuppressWarnings("unchecked")
    public void addToLoginLogSet(boolean condition, String username, LocalDateTime time) {

        //important note: this does not handle changes to the condition
        //updates to conditions are handled elsewhere (Controllers)
        LoginLog ll = new LoginLog(condition, username, time);

        if (!this.allLogs.containsKey(username)){
            ArrayList<LoginLog> logArray = new ArrayList<>();
            logArray.add(ll);
            this.allLogs.put(username, logArray);
        } else {
            if (this.allLogs.get(username).size() == 3) {
                this.allLogs.get(username).remove(0);
                this.allLogs.get(username).add(ll);
            } else if (this.allLogs.get(username).size() < 3) {
                this.allLogs.get(username).add(ll);
            } else {
                while (this.allLogs.get(username).size() > 3) {
                    this.allLogs.get(username).remove(0);
                }
            }
        }
    }

    /**
     * Checks if login logs exist for a user
     * @param username the username
     * @return true iff login logs exist
     */
    public boolean checkLogExists(String username){
        return this.allLogs.containsKey(username);
    }

    /**
     * Checks if login logs are suspicious
     * @param username the username
     * @return true iff login logs are suspicious
     */
    public boolean suspiciousLogs(String username) {
        int strike = 0;

        for (LoginLog log : allLogs.get(username)) {
            if (!log.getCondition()) {
                strike++;
            }
        }

        return strike == 3;
    }

    /**
     * Gets all logs JSON
     * @return A JSONObject that contains the JSON representation of this class
     */
    @SuppressWarnings("unchecked")
    public JSONArray getAllLogsJson(){
        JSONArray array = new JSONArray();

        for(String ID: allLogs.keySet()){
            for(LoginLog l: allLogs.get(ID)){
                array.add(l.convertToJSON());
            }
        }

        return array;
    }

    /**
     * Gets login log json for a user
     * @param username the username
     * @return  A JSONArray that contains the JSON representation of this class
     */
    @SuppressWarnings("unchecked")
    public JSONArray getLoginLogJSON(String username) {
        JSONArray array = new JSONArray();

        if (allLogs.containsKey(username)) {
            for(LoginLog l: allLogs.get(username)){
                array.add(l.convertToJSON());
            }
        }

        return array;
    }
}
