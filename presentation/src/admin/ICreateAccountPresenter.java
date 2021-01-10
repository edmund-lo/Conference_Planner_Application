package admin;

import common.ILoggedInPresenter;
import javafx.event.ActionEvent;

public interface ICreateAccountPresenter extends ILoggedInPresenter {
    void createAccountButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status);
}
