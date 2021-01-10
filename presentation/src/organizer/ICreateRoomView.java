package organizer;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import util.NumberTextField;

public interface ICreateRoomView extends ILoggedInView {
    String getRoomName();
    void setRoomName(String roomName);
    int getCapacity();
    void setCapacity(int capacity);
    boolean getAmenity(int index);
    void setAmenity(int index, boolean checked);
    void setResultText(String resultText);

    TextField getRoomNameField();
    NumberTextField getCapacityField();
    CheckBox getAmenityBox(int index);

    void setCreateRoomButtonAction(EventHandler<ActionEvent> eventHandler);
}
