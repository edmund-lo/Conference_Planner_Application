package controllers;

import gateways.LoginLogGateway;
import gateways.UserAccountGateway;
import gateways.UserGateway;
import org.json.simple.JSONObject;
import presenters.AdminPresenter;
import presenters.LoginPresenter;
import usecases.LoginLogManager;
import usecases.UserManager;

import java.util.ArrayList;

/**
 * A Controller class representing an AdminController which inherits from UserController.
 *
 * @author Edmund Lo
 *
 */
public class AdminController extends UserController{
    private LoginLogManager llm;
    private final AdminPresenter ap;
    private LoginLogGateway llg = new LoginLogGateway();

    /**
     * Constructor for AdminController object. Uses constructor from UserController
     *
     * @param username current logged in user's username
     */
    public AdminController(String username) {
        super(username);
        this.ap = new AdminPresenter();
        this.llm = llg.deserializeData();
    }

    /**
     * Creates a new account for the specified user type
     *
     * @param register JSONObject containing user info
     * @return a JSON object containing the status and description of the action
     */
    public JSONObject createAccount(JSONObject register) {
        this.deserializeData();
        LoginPresenter lp = new LoginPresenter();
        UserAccountGateway uag = new UserAccountGateway();

        String username = String.valueOf(register.get("username"));
        String firstName = String.valueOf(register.get("firstName"));
        String lastName = String.valueOf(register.get("lastName"));
        String password = String.valueOf(register.get("password"));
        String type = String.valueOf(register.get("userType"));
        String confirmPassword = String.valueOf(register.get("confirmPassword"));

        //Check for whitespace
        if (username.contains(" "))
            return lp.noWhiteSpace();
        //Check Username Minimum Length
        if (username.length() < 1)
            return lp.EmptyName();

        //If the counter is 0, that means the username isn't taken and can be set.
        if (usernameExists(username))
            return lp.UsernameTaken();

        //Check minimum password length for security
        if(password.length() < 6)
            return lp.EmptyPassword();

        if (!confirmPassword.equals(password))
            return lp.passwordsDontMatch();

        //Add account to the user manager and update the Accounts Arraylist
        uam.addAccount(username, password, type);
        uag.serializeData(uam);
        //this.accounts = uam.getAccountInfo();

        UserGateway ug = new UserGateway();
        UserManager um = ug.deserializeData();

        switch (type) {
            case "attendee":
                um.createNewAttendee(username, firstName, lastName, false);
                ug.serializeData(um);
                return lp.AccountMade();
            case "organizer":
                um.createNewOrganizer(username, firstName, lastName);
                ug.serializeData(um);
                return lp.AccountMade();
            case "speaker":
                um.createNewSpeaker(username, firstName, lastName);
                ug.serializeData(um);
                return lp.AccountMade();
            case "administrator":
                um.createNewAdmin(username, firstName, lastName);
                ug.serializeData(um);
                return lp.AccountMade();
            case "vip":
                um.createNewAttendee(username, firstName, lastName, true);
                ug.serializeData(um);
                return lp.AccountMade();
            default:
                return lp.IncorrectCredentials();
        }
    }

    /**
     * checks if username already exists
     * @param username the username
     * @return True iff username exists
     */
    public boolean usernameExists(String username){
        ArrayList<String[]> accounts = uam.getAccountInfo();
        UserAccountGateway uag = new UserAccountGateway();

        //Update accounts list
        uam = uag.deserializeData();

        int UsernameCheck = 0;
        //Loops through all existing usernames.
        for (String[] users : accounts){
            if (users[0].toLowerCase().equals(username.toLowerCase())){
                //If the Username the user entered already exists then UsernameCheck counter is increased.
                UsernameCheck++;
            }
        }
        return UsernameCheck != 0;
    }

    /**
     * Lists the names of all attendees
     *
     * @return a JSON object containing all attendees
     */
    public JSONObject viewAllAttendees() {
        this.deserializeData();
        return ap.viewAllAttendees(um.getAllAttendeesJson());
    }

    /**
     * Sets an attendee as a vip
     *
     * @param username the username of the attendee to be set as a VIP
     * @return a JSON object containing the status and description of the action
     */
    public JSONObject setAttendeeAsVip(String username) {
        this.deserializeData();

        if (!um.isVip(username)) {
            um.setAttendeeAsVip(username);
            this.saveData();
            return ap.setVipResult();
        } else if (um.isVip(username)) {
            return ap.alreadyVipError();
        }
        return ap.invalidAttendeeNameError();
    }

    /**
     * Sets an attendee as not a vip
     *
     * @param username the username of the attendee to be set as not a VIP
     * @return a JSON object containing the status and description of the action
     */
    public JSONObject setAttendeeAsNotVip(String username) {
        this.deserializeData();

        if (um.isVip(username)) {
            um.setAttendeeAsNotVip(username);
            this.saveData();
            return ap.setNotVipResult();
        } else if (!um.isVip(username)) {
            return ap.alreadyNotVipError();
        }
        return ap.invalidAttendeeNameError();
    }

    /**
     * Removes an event with no attendees, no speakers and room from the system. Note that this should be called ONLY
     * after an Organizer has called cancelEvent() to safely delete an Event from the system.
     *
     * @param eventID the event ID
     * @return a JSON object containing the status and description of the action
     */
    public JSONObject removeEvent(String eventID) {
        this.deserializeData();

        if(em.isEventEmpty(eventID)) {
            String name = em.getEventName(eventID);
            em.removeEvent(eventID);
            this.saveData();
            return ap.removeEventResult(name);
        } else {
            return ap.eventNotEmptyError();
        }
    }

    /**
     * Gets all message threads.
     *
     * @return JSONObject containing all message threads
     */
    public JSONObject getAllMessageThreads() {
        this.deserializeData();
        return ap.getAllMessageThreads(mm.getAllMessageThreadsJson());
    }

    /**
     * Delete a message thread given it's ID.
     *
     * Precondition: the messageThread exist.
     * @return a JSON object containing the status and description of the action
     */
    public JSONObject deleteMessageThread(String messageThreadID) {
        this.deserializeData();

        mm.deleteMessage(messageThreadID);
        um.deleteMessageFromUsers(messageThreadID);
        this.saveData();
        return ap.deleteMessageResult();
    }

    /**
     * Gets all user login logs
     *
     * @return a JSONObject containing all user login logs
     */
    public JSONObject getAllUserLogs() {
        this.deserializeData();
        this.llm = llg.deserializeData();
        return ap.getAllUserLogs(llm.getAllLogsJson());
    }

    /**
     * Gets a user's login logs
     *
     * @param username the username
     * @return a JSONObject containing the user's login logs
     */
    public JSONObject getLoginLogs(String username) {
        this.deserializeData();
        this.llm = llg.deserializeData();
        return ap.getLoginLogs(llm.getLoginLogJSON(username));
    }

    /**
     * Gets all user accounts
     *
     * @return a JSONObject containing all user accounts
     */
    public JSONObject getAllAccounts() {
        this.deserializeData();
        return ap.getAllAccounts(uam.getAllAccountsJSON());
    }

    /**
     * Unlocks an user's account
     *
     * @param username the username
     * @return a JSON object containing the status and description of the action
     */
    public JSONObject unlockAccount(String username){
        this.deserializeData();
        uam.unlockAccount(username);
        this.saveData();
        return ap.accountUnlocked();
    }
}
