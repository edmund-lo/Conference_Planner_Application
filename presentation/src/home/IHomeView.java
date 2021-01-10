package home;

import common.ILoggedInView;

public interface IHomeView extends ILoggedInView {
    void setTitle(String title);
    void setUnreadButtonText(String text);
    void setViewScheduleButtonText(String text);
}
