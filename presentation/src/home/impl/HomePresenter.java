package home.impl;

import common.UserAccountHolder;
import controllers.AttendeeController;
import home.IHomePresenter;
import home.IHomeView;
import model.UserAccount;
import org.json.simple.JSONObject;

/**
 * Presenter class for the home scene
 */
public class HomePresenter implements IHomePresenter {
    private final IHomeView view;
    private final AttendeeController ac;

    /**
     * Constructs a HomePresenter object with given view and new AttendeeController instance
     * @param view IHomeView implementation object associated with the presenter
     */
    public HomePresenter(IHomeView view) {
        this.view = view;
        getUserData();
        this.ac = new AttendeeController(this.view.getSessionUsername());
        init();
    }

    /**
     * Sets user's greeting text with personalised name
     */
    @Override
    public void setGreeting() {
        JSONObject responseJson = ac.getUser();
        this.view.setTitle(String.valueOf(responseJson.get("result")));
    }

    /**
     * Sets user's personalised unread messages text
     */
    @Override
    public void setUnreadMessages() {
        JSONObject responseJson = ac.getUnreadMessages();
        this.view.setUnreadButtonText(String.valueOf(responseJson.get("result")));
    }

    /**
     * Sets user's personalised attending events text
     */
    @Override
    public void setAttendingEvents() {
        JSONObject responseJson = ac.getNextDayEvents();
        this.view.setViewScheduleButtonText(String.valueOf(responseJson.get("result")));
    }

    /**
     * Init method which gets all the home screen texts
     */
    @Override
    public void init() {
        setGreeting();
        setUnreadMessages();
        setAttendingEvents();
    }

    @Override
    public void getUserData() {
        UserAccountHolder holder = UserAccountHolder.getInstance();
        UserAccount account = holder.getUserAccount();
        this.view.setSessionUsername(account.getUsername());
        this.view.setSessionUserType(account.getUserType());
    }
}
