package presenters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Class for formatting the sent JSONObjects to the presentation module
 * @param <T> generic type for class
 */
public class PresenterUtil<T> {
    /**
     * method for creating JSONObject of the format {"status":_, "result":_, "data":_}
     * @param status status of the action. One of "success", "warning" or "error"
     * @param result The result of the action
     * @return a JSONObject of the format specified above
     */
    @SuppressWarnings("unchecked")
    public JSONObject createJSON(String status, String result, JSONArray array) {
        JSONObject json = new JSONObject();

        json.put("status", status);
        json.put("result", result);

        json.put("data", array);

        return json;
    }

    /**
     * method for creating JSONObject of the format {"status":_, "result"}
     * @param status status of the action. One of "success", "warning" or "error"
     * @param result the result of the action
     * @return a JSONObject of the format specified above
     */
    @SuppressWarnings("unchecked")
    public JSONObject createJSON(String status, String result) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        json.put("status", status);
        json.put("result", result);

        json.put("data", array);

        return json;
    }

}
