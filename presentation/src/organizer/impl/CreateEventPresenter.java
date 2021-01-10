package organizer.impl;

import adapter.ScheduleAdapter;
import common.UserAccountHolder;
import controllers.OrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.ScheduleEntry;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import organizer.ICreateEventPresenter;
import organizer.ICreateEventView;
import util.DateTimeUtil;
import util.TextResultUtil;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Presenter class for creating new events scene
 */
public class CreateEventPresenter implements ICreateEventPresenter {
    private final ICreateEventView view;
    private final OrganizerController oc;
    private final ObservableSet<CheckBox> selectedAmenities = FXCollections.observableSet();

    /**
     * Initialises a CreateEventPresenter object with given view and new OrganizerController,
     * gets and sets current session's user information
     * @param view IRescheduleCancelEventView interface implementation
     */
    public CreateEventPresenter(ICreateEventView view) {
        this.view = view;
        this.oc = new OrganizerController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs first previous button action and displays the correct step accordion
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void previousFirstButtonAction(ActionEvent actionEvent) {
        this.view.getTitledPane(1).setDisable(false);
        this.view.getTitledPane(2).setDisable(true);
        this.view.getTitledPane(1).setExpanded(true);
        this.view.getTitledPane(2).setExpanded(false);
    }

    /**
     * Performs second previous button action and displays the correct step accordion
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void previousSecondButtonAction(ActionEvent actionEvent) {
        this.view.getTitledPane(2).setDisable(false);
        this.view.getTitledPane(3).setDisable(true);
        this.view.getTitledPane(2).setExpanded(true);
        this.view.getTitledPane(3).setExpanded(false);
    }

    /**
     * Performs find rooms button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void findRoomsButtonAction(ActionEvent actionEvent) {
        clearResultText(1);
        this.view.getRoomComboBox().getItems().clear();
        this.view.setStart(LocalDateTime.now());
        this.view.setEnd(LocalDateTime.now());
        this.view.getTableContainer().getChildren().clear();

        JSONObject queryJson = constructRoomRequestJson();
        JSONObject responseJson = oc.listPossibleRooms(queryJson);
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 1);
        if (String.valueOf(responseJson.get("status")).equals("success")) {
            displayPossibleRooms((JSONArray) responseJson.get("data"));
            this.view.getTitledPane(1).setDisable(true);
            this.view.getTitledPane(2).setDisable(false);
            this.view.getTitledPane(1).setExpanded(false);
            this.view.getTitledPane(2).setExpanded(true);
        }
    }

    /**
     * Performs preview room schedule button action and gets and displays the result in a table
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void previewRoomButtonAction(ActionEvent actionEvent) {
        clearResultText(2);
        this.view.getTableContainer().getChildren().clear();

        List<ScheduleEntry> roomSchedule = getRoomSchedule();
        displayRoomSchedule(roomSchedule);
    }

    /**
     * Performs view summary button action and displays the correct step accordion
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void summaryButtonAction(ActionEvent actionEvent) {
        this.view.setSummaryEventName(this.view.getEventName());
        this.view.setSummaryRoomName(this.view.getRoomName());
        this.view.setSummaryCapacity(this.view.getCapacity());
        this.view.setSummaryStart(this.view.getStart());
        this.view.setSummaryEnd(this.view.getEnd());
        this.view.setSummaryAmenities(amenitiesToString());
        this.view.setSummaryVipEvent(this.view.getVipEvent());

        this.view.getTitledPane(2).setDisable(true);
        this.view.getTitledPane(3).setDisable(false);
        this.view.getTitledPane(2).setExpanded(false);
        this.view.getTitledPane(3).setExpanded(true);
    }

    /**
     * Performs create new event button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void createEventButtonAction(ActionEvent actionEvent) {
        clearResultText(3);

        JSONObject queryJson = constructEventJson();
        JSONObject responseJson = oc.createEventCmd(queryJson);
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 3);
        if (String.valueOf(responseJson.get("status")).equals("success")) {
            this.view.getTitledPane(1).setDisable(false);
            this.view.getTitledPane(3).setDisable(true);
            this.view.getTitledPane(1).setExpanded(true);
            this.view.getTitledPane(3).setExpanded(false);
        }
    }

    /**
     * Sets the result of the action given status
     * @param resultText String object describing the result
     * @param status String object representing the status of the controller method call
     */
    @Override
    public void setResultText(String resultText, String status, int index) {
        this.view.setResultText(resultText, index);
        TextResultUtil.getInstance().addPseudoClass(status, this.view.getResultTextControl(index));
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
     * Gets all Event entities that is scheduled in the room and converts them into ScheduleEntry models
     * @return List of ScheduleEntry models
     */
    @Override
    public List<ScheduleEntry> getRoomSchedule() {
        String roomName = this.view.getRoomName();
        LocalDateTime start = this.view.getStart();
        JSONObject responseJson = oc.listRoomSchedule(roomName, start);
        setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")), 2);
        return ScheduleAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
    }

    /**
     * Displays schedule in the TableView and adds listeners
     * @param schedule List of ScheduleEntry models
     */
    @Override
    public void displayRoomSchedule(List<ScheduleEntry> schedule) {
        Text tableHeader = new Text("Room Schedule");
        TableView<ScheduleEntry> scheduleTable = new TableView<>();

        TableColumn<ScheduleEntry, String> column1 = new TableColumn<>("Event Name");
        TableColumn<ScheduleEntry, LocalDateTime> column2 = new TableColumn<>("Start");
        TableColumn<ScheduleEntry, LocalDateTime> column3 = new TableColumn<>("End");
        DateTimeUtil.getInstance().setScheduleDateTimeCellFactory(column2);
        DateTimeUtil.getInstance().setScheduleDateTimeCellFactory(column3);
        column1.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("start"));
        column3.setCellValueFactory(new PropertyValueFactory<>("end"));
        scheduleTable.getColumns().add(column1);
        scheduleTable.getColumns().add(column2);
        scheduleTable.getColumns().add(column3);

        ObservableList<ScheduleEntry> observableSchedule = FXCollections.observableArrayList(schedule);
        scheduleTable.setItems(observableSchedule);

        this.view.getTableContainer().getChildren().add(tableHeader);
        this.view.getTableContainer().getChildren().add(scheduleTable);
    }

    /**
     * Init method which sets all the button actions, observe all amenity checkboxes
     */
    @Override
    public void init() {
        observeAmenities();
        this.view.setCreateEventButtonAction(this::createEventButtonAction);
        this.view.setFindRoomsButtonAction(this::findRoomsButtonAction);
        this.view.setPreviewRoomButtonAction(this::previewRoomButtonAction);
        this.view.setPreviousFirstButtonAction(this::previousFirstButtonAction);
        this.view.setPreviousSecondButtonAction(this::previousSecondButtonAction);
        this.view.setSummaryButtonAction(this::summaryButtonAction);
        this.view.getTitledPane(1).setExpanded(true);
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
            if (isNowSelected) selectedAmenities.add(checkBox);
            else selectedAmenities.remove(checkBox);
        });
    }

    /**
     * Helper method that converts the checked amenities to a string
     * @return String object representing checked amenities
     */
    private String amenitiesToString() {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (CheckBox cb : selectedAmenities) {
            if (cb.isSelected()) {
                sb.append(prefix);
                prefix = ", ";
                sb.append(cb.getText());
            }
        }
        return sb.toString();
    }

    /**
     * Helper method to display suggested rooms in a combo box
     * @param jsonArray JSONArray object representing a List of String room names
     */
    private void displayPossibleRooms(JSONArray jsonArray) {
        this.view.getRoomComboBox().getItems().clear();
        for (Object o : jsonArray)
            this.view.getRoomComboBox().getItems().add(String.valueOf(o));
    }

    /**
     * Helper method to encode a JSONObject for a new event form
     * @return JSONObject object representing a new event form
     */
    @SuppressWarnings("unchecked")
    private JSONObject constructEventJson() {
        JSONObject queryJson = new JSONObject();
        queryJson.put("eventName", this.view.getEventName());
        queryJson.put("capacity", this.view.getCapacity());
        queryJson.put("vip", this.view.getVipEvent().equals("Yes"));
        queryJson.put("roomName", this.view.getRoomName());
        queryJson.put("start", this.view.getStart());
        queryJson.put("end", this.view.getEnd());
        queryJson.put("chairs", this.view.getAmenityBox(1).isSelected());
        queryJson.put("tables", this.view.getAmenityBox(2).isSelected());
        queryJson.put("projector", this.view.getAmenityBox(3).isSelected());
        queryJson.put("sound", this.view.getAmenityBox(4).isSelected());
        return queryJson;
    }

    /**
     * Helper method to encode a JSONObject for a message form
     * @return JSONObject object representing a message form
     */
    @SuppressWarnings("unchecked")
    private JSONObject constructRoomRequestJson() {
        JSONObject queryJson = new JSONObject();
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
    private void clearResultText(int index) {
        this.view.setResultText("", index);
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl(index));
    }
}
