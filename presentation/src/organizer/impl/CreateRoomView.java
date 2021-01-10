package organizer.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import organizer.ICreateRoomView;
import util.NumberTextField;

import java.math.BigDecimal;

/**
 * View class for creating a new room scene
 */
public class CreateRoomView implements ICreateRoomView {
    @FXML
    private TextField roomName;
    @FXML
    private NumberTextField capacity;
    @FXML
    private CheckBox amenity1;
    @FXML
    private CheckBox amenity2;
    @FXML
    private CheckBox amenity3;
    @FXML
    private CheckBox amenity4;
    @FXML
    private Text resultText;

    @FXML
    public void executeAddCreateRoom(ActionEvent event) {
        if (createRoomButtonAction != null) createRoomButtonAction.handle(event);
    }
    @FXML
    public void initialize() {
        new CreateRoomPresenter(this);
    }

    private EventHandler<ActionEvent> createRoomButtonAction;
    private String sessionUsername;
    private String sessionUserType;

    @Override
    public String getRoomName() {
        return this.roomName.getText();
    }

    @Override
    public void setRoomName(String roomName) {
        this.roomName.setText(roomName);
    }

    @Override
    public int getCapacity() {
        return this.capacity.getNumber().intValue();
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity.setNumber(BigDecimal.valueOf(capacity));
    }

    @Override
    public boolean getAmenity(int index) {
        return getAmenityBox(index).isSelected();
    }

    @Override
    public void setAmenity(int index, boolean checked) {
        CheckBox amenity = new CheckBox();

        amenity.setSelected(checked);
    }

    @Override
    public void setResultText(String resultText) {
        this.resultText.setText(resultText);
    }

    @Override
    public TextField getRoomNameField() {
        return this.roomName;
    }

    @Override
    public NumberTextField getCapacityField() {
        return this.capacity;
    }

    @Override
    public CheckBox getAmenityBox(int index) {
        CheckBox amenity = null;
        if (index == 1) amenity = this.amenity1;
        else if (index == 2) amenity =  this.amenity2;
        else if (index == 3) amenity =  this.amenity3;
        else if (index == 4) amenity =  this.amenity4;
        return amenity;
    }

    @Override
    public void setCreateRoomButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.createRoomButtonAction = eventHandler;
    }

    @Override
    public Text getResultTextControl() {
        return this.resultText;
    }

    @Override
    public String getSessionUsername() {
        return this.sessionUsername;
    }

    @Override
    public void setSessionUsername(String username) {
        this.sessionUsername = username;
    }

    @Override
    public String getSessionUserType() {
        return this.sessionUserType;
    }

    @Override
    public void setSessionUserType(String userType) {
        this.sessionUserType = userType;
    }
}
