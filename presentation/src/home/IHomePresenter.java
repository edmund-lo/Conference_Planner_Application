package home;

import common.ILoggedInPresenter;

public interface IHomePresenter extends ILoggedInPresenter {
    void setGreeting();
    void setUnreadMessages();
    void setAttendingEvents();
}
