package common;

/**
 * Presenter interface for logged in presenter implementations
 */
public interface ILoggedInPresenter extends IPresenter {
    /**
     * Gets user data from static UserAccountHolder and sets it to the view controller
     */
    void getUserData();
}
