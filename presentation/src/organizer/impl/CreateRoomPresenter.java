package organizer.impl;

import common.UserAccountHolder;
import controllers.OrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import model.UserAccount;
import org.json.simple.JSONObject;
import organizer.ICreateRoomPresenter;
import organizer.ICreateRoomView;
import util.TextResultUtil;

/**
 * Presenter class for creating a new room scene
 */
public class CreateRoomPresenter implements ICreateRoomPresenter {
    private final ICreateRoomView view;
    private final ObservableSet<CheckBox> selectedAmenities = FXCollections.observableSet();
    private final OrganizerController oc;

    /**
     * Initialises a CreateRoomPresenter object with given view and new OrganizerController,
     * gets and sets current session's user information
     * @param view ICreateRoomView interface implementation
     */
    public CreateRoomPresenter(ICreateRoomView view) {
        this.view = view;
        getUserData();
        this.oc = new OrganizerController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs create a new room button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void createRoomButtonAction(ActionEvent actionEvent) {
        clearResultText();

        JSONObject queryJson = constructRoomJson();
        JSONObject responseJson = oc.createRoom(queryJson);
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
    }

    /**
     * Sets the result of the action given status
     * @param resultText String object describing the result
     * @param status String object representing the status of the controller method call
     */
    @Override
    public void setResultText(String resultText, String status) {
        this.view.setResultText(resultText);
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getResultTextControl());
        if (status.equals("warning") || status.equals("error")) {
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getRoomNameField());
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getCapacityField());
        }
    }

    /**
     * Configures all four amenity checkboxes
     */
    @Override
    public void observeAmenities() {
        configureCheckBox(this.view.getAmenityBox(1));
        configureCheckBox(this.view.getAmenityBox(2));
        configureCheckBox(this.view.getAmenityBox(3));
        configureCheckBox(this.view.getAmenityBox(4));
    }

    /**
     * Init method which sets all the button actions, observe all amenity checkboxes
     */
    @Override
    public void init() {
        observeAmenities();
        this.view.setCreateRoomButtonAction(this::createRoomButtonAction);
    }

    /**
     * Helper method to get and set current user's information to the view class variable
     */
    @Override
    public void getUserData() {
        UserAccountHolder holder = UserAccountHolder.getInstance();
        UserAccount account = holder.getUserAccount();
        this.view.setSessionUsername(account.getUsername());
        this.view.setSessionUserType(account.getUserType());
    }

    /**
     * Helper method to add checkbox listeners
     * @param checkBox JavaFX Checkbox object
     */
    private void configureCheckBox(CheckBox checkBox) {
        if (checkBox.isSelected())
            selectedAmenities.add(checkBox);

        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected)
                selectedAmenities.add(checkBox);
            else
                selectedAmenities.remove(checkBox);
        });
    }

    /**
     * Helper method to encode a JSONObject for a new room form
     * @return JSONObject object representing a new room form
     */
    @SuppressWarnings("unchecked")
    private JSONObject constructRoomJson() {
        JSONObject queryJson = new JSONObject();
        queryJson.put("roomName", this.view.getRoomName());
        queryJson.put("capacity", this.view.getCapacity());
        queryJson.put("chairs", this.view.getAmenityBox(1).isSelected());
        queryJson.put("tables", this.view.getAmenityBox(2).isSelected());
        queryJson.put("projector", this.view.getAmenityBox(3).isSelected());
        queryJson.put("sound", this.view.getAmenityBox(4).isSelected());
        return queryJson;
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText() {
        this.view.setResultText("");
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getRoomNameField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getCapacityField());
    }
}
