package speaker.impl;

import adapter.ScheduleAdapter;
import common.UserAccountHolder;
import controllers.SpeakerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.ScheduleEntry;
import model.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import speaker.ISpeakerEventsPresenter;
import speaker.ISpeakerEventsView;
import util.DateTimeUtil;
import util.TextResultUtil;
import java.util.List;

/**
 * Presenter class for viewing speaker's own events plus messaging events' attendees screen
 */
public class SpeakerEventsPresenter implements ISpeakerEventsPresenter {
    private final ISpeakerEventsView view;
    private final SpeakerController sc;
    private final ObservableSet<CheckBox> selectedRecipients = FXCollections.observableSet();

    /**
     * Initialises a SpeakerEventsPresenter object with given view and new AdminController,
     * gets and sets current session's user information
     * @param view ISpeakerEventsView interface implementation
     */
    public SpeakerEventsPresenter(ISpeakerEventsView view) {
        this.view = view;
        getUserData();
        this.sc = new SpeakerController(this.view.getSessionUsername());
        init();
    }

    /**
     * Performs send message button action and displays the result
     * @param actionEvent JavaFX ActionEvent object representing the event of the button press
     */
    @Override
    public void sendButtonAction(ActionEvent actionEvent) {
        clearResultText();

        JSONObject queryJson = constructMessageJson();
        JSONObject responseJson = sc.messageEventsAttendeesCmd(queryJson);
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
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getSubjectField());
            TextResultUtil.getInstance().addPseudoClass(status, this.view.getContentArea());
        }
    }

    /**
     * Gets all Event entities that this speaker is speaking at and converts them into ScheduleEntry models
     * @return List of ScheduleEntry models
     */
    @Override
    public List<ScheduleEntry> getAllSpeakerEvents() {
        JSONObject responseJson = sc.getSpeakerEvents();
        if (!String.valueOf(responseJson.get("status")).equals("success"))
            setResultText(String.valueOf(responseJson.get("result")), String.valueOf(responseJson.get("status")));
        return ScheduleAdapter.getInstance().adaptData((JSONArray) responseJson.get("data"));
    }

    /**
     * Displays speakerSchedule in the TableView and adds listeners
     * @param speakerSchedule List of ScheduleEntry models
     */
    @Override
    public void displaySpeakerEvents(List<ScheduleEntry> speakerSchedule) {
        DateTimeUtil.getInstance().setScheduleDateTimeCellFactory(this.view.getEventStartColumn());
        DateTimeUtil.getInstance().setScheduleDateTimeCellFactory(this.view.getEventEndColumn());
        this.view.getEventNameColumn().setCellValueFactory(new PropertyValueFactory<>("eventName"));
        this.view.getRoomNameColumn().setCellValueFactory(new PropertyValueFactory<>("roomName"));
        this.view.getEventStartColumn().setCellValueFactory(new PropertyValueFactory<>("start"));
        this.view.getEventEndColumn().setCellValueFactory(new PropertyValueFactory<>("end"));

        ObservableList<ScheduleEntry> observableSchedule = FXCollections.observableArrayList(speakerSchedule);
        this.view.getEventsTable().setItems(observableSchedule);
        this.view.getEventsTable().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleSelect(newValue));
    }

    /**
     * Displays event's attributes
     * @param event ScheduleEntry model that has been selected
     */
    @Override
    public void displayEventDetails(ScheduleEntry event) {
        this.view.setSummaryEventName(event.getEventName());
        this.view.setSummaryRoomName(event.getRoomName());
        this.view.setSummaryAttendees(event.getAttendees());
        this.view.setSummaryAmenities(event.getAmenities());
        this.view.setSummaryDuration(event.getDuration());
        this.view.setSummaryStart(event.getStart());
        this.view.setSummaryEnd(event.getEnd());
        this.view.setSummarySpeakers(event.getSpeakers());
        this.view.setSummaryCapacity(event.getCapacity());
    }

    /**
     * Displays event's recipient list as checkboxes to choose from
     * @param event ScheduleEntry model
     */
    @Override
    public void displayRecipients(ScheduleEntry event) {
        this.view.getRecipientFlowPane().getChildren().clear();

        if (!event.getAttendees().equals("")) {
            String[] attendees = event.getAttendees().split(", ");
            for (String attendee : attendees) {
                CheckBox cb = new CheckBox(attendee);
                cb.setSelected(true);
                configureCheckBox(cb);
                this.view.getRecipientFlowPane().getChildren().add(cb);
            }
        } else {
            Text noAttendeesText = new Text("This event has no attendees yet");
            this.view.getRecipientFlowPane().getChildren().add(noAttendeesText);
        }
    }

    /**
     * Init method which sets all the button actions, gets and displays all events
     */
    @Override
    public void init() {
        this.view.setSendButtonAction(this::sendButtonAction);
        this.view.setSender(this.view.getSessionUsername());
        List<ScheduleEntry> speakerEvents = getAllSpeakerEvents();
        displaySpeakerEvents(speakerEvents);
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
     * Helper method to encode JSON object for creating a new message
     * @return JSONObject object representing a new message form
     */
    @SuppressWarnings("unchecked")
    private JSONObject constructMessageJson() {
        JSONObject queryJson = new JSONObject();
        JSONArray recipients = new JSONArray();
        for (CheckBox cb : selectedRecipients) {
            if (cb.isSelected())
                recipients.add(cb.getText());
        }
        queryJson.put("sender", this.view.getSender());
        queryJson.put("recipients", recipients);
        queryJson.put("subject", this.view.getSubject());
        queryJson.put("content", this.view.getContent());
        return queryJson;
    }

    /**
     * Helper method to clear all result text and affected form fields
     */
    private void clearResultText() {
        this.view.setResultText("");
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getResultTextControl());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getSubjectField());
        TextResultUtil.getInstance().removeAllPseudoClasses(this.view.getContentArea());
    }

    /**
     * Helper method to handle select event
     * @param event ScheduleEntry model that has been selected
     */
    private void handleSelect(ScheduleEntry event) {
        displayEventDetails(event);
        displayRecipients(event);
    }

    /**
     * Helper method to add checkbox listeners
     * @param checkBox JavaFX Checkbox object
     */
    private void configureCheckBox(CheckBox checkBox) {
        if (checkBox.isSelected())
            selectedRecipients.add(checkBox);

        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected)
                selectedRecipients.add(checkBox);
            else
                selectedRecipients.remove(checkBox);
        });
    }
}
