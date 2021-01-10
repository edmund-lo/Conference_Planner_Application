package adapter;

import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for mapping User entities to models
 */
public class UserAdapter {
    private static final UserAdapter Instance = new UserAdapter();

    /**
     * Gets current instance of a UserAdapter
     * @return An instance of a UserAdapter object
     */
    public static UserAdapter getInstance() { return Instance; }

    /**
     * Empty UserAdapter constructor
     */
    private UserAdapter() {}

    /**
     * Adapts data into a list of users
     * @param data JSONArray of JSON formatted User entities
     * @return List of User models
     */
    public List<User> adaptData(JSONArray data) {
        List<User> users = new ArrayList<>();
        for (Object datum : data) {
            JSONObject jsonObject = (JSONObject) datum;
            users.add(mapUser(jsonObject));
        }
        return users;
    }

    /**
     * Maps the jsonObject into a User model
     * @param jsonObject JSONObject of JSON formatted User entity
     * @return User model with mapped attributes
     */
    private User mapUser(JSONObject jsonObject) {
        String username = String.valueOf(jsonObject.get("username"));
        String firstName = String.valueOf(jsonObject.get("firstName"));
        String lastName = String.valueOf(jsonObject.get("lastName"));
        String userType = String.valueOf(jsonObject.get("type"));
        boolean vip = jsonObject.get("vip").equals(true);

        return new User(username, firstName, lastName, userType, vip);
    }
}
