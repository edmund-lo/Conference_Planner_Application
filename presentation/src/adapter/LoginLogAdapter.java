package adapter;

import model.LoginLog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for mapping LoginLog entities to models
 */
public class LoginLogAdapter {
    private static final LoginLogAdapter Instance = new LoginLogAdapter();

    /**
     * Gets current instance of a LoginLogAdapter
     * @return An instance of a LoginLogAdapter object
     */
    public static LoginLogAdapter getInstance() { return Instance; }

    /**
     * Empty LoginLogAdapter constructor
     */
    private LoginLogAdapter() {}

    /**
     * Adapts data into a list of login logs
     * @param data JSONArray of JSON formatted LoginLog entities
     * @return List of LoginLog models
     */
    public List<LoginLog> adaptData(JSONArray data) {
        List<LoginLog> users = new ArrayList<>();
        for (Object datum : data) {
            JSONObject jsonObject = (JSONObject) datum;
            users.add(mapLoginLog(jsonObject));
        }
        return users;
    }

    /**
     * Maps the jsonObject into a LoginLog model
     * @param jsonObject JSONObject of JSON formatted LoginLog entity
     * @return LoginLog model with mapped attributes
     */
    private LoginLog mapLoginLog(JSONObject jsonObject) {
        String username = String.valueOf(jsonObject.get("username"));
        LocalDateTime loginTime = (LocalDateTime) jsonObject.get("loginTime");
        boolean success = jsonObject.get("condition").equals(true);

        return new LoginLog(username, loginTime, success);
    }
}
